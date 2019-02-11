package com.pershing.convert.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;

import net.bramp.ffmpeg.*;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import com.pershing.convert.service.DBService;
import com.pershing.convert.service.FTPService;
import com.pershing.convert.service.HttpService;
import com.pershing.convert.entities.ConvertData;
import com.pershing.convert.service.ConvertService;

@Service
@Scope("prototype")
public class ConvertServiceImpl implements ConvertService {

	@Autowired
	private FTPService ftpService;
	@Autowired
	private HttpService httpSevice;
	@Autowired
	private DBService dbService;
	
	@Value("${sourceDir}")
	private String sourceDir;
	@Value("${targetDir}")
	private String targetDir;
	@Value("${workingDir}")
	private String workingDir;
	@Value("${ffmpegDir}")
	private String ffmpegDir;
	@Value("${ffprobDir}")
	private String ffprobDir;
	@Value("${ffmpegComplexFilter}")
	private String ffmpegComplexFilter;
	@Value("${outputFormat}")
	private String outputFormat;
	
	@Value("${audioCodec}")
	private String audioCodec;
	@Value("${videoCodec}")
	private String videoCodec;
	
	private static final String tmpFileMark1 = "tmp1";
	private static final String tmpFileMark2 = "tmp2";
	private static final String dash = "-";
	private static final String underBar = "_";
	private static final String dot = ".";
	private static final String prime = "\'";
	private static final String colon = "\\:";
	private static final String vBar = "\\|";
	
	private Logger logger = LoggerFactory.getLogger(ConvertServiceImpl.class);
	private static  FFmpegExecutor executor;
	
	@Override
	public void ConvertThread(ConvertData convertData) {
		logger.info(convertData.getcallUuid()+": Convert starting...");
		try {
			initExecutor();
			ConvertProcess(convertData);
		}
		catch(Exception e) {
			/*
			 * http convert error
			 */
			dbService.eventError(convertData);
			logger.error(e.getMessage());
		}
	}
	
	private void deleteFile(String fileDir) {
		File file = new File(fileDir);
		if(file.delete()) {
			logger.info(fileDir + " deleted.");
		}
	}
	private void initExecutor() throws Exception {
		FFmpeg ffmpeg = new FFmpeg(ffmpegDir);
		FFprobe ffprobe = new FFprobe(ffprobDir);
		executor = new FFmpegExecutor(ffmpeg, ffprobe);
	}
	
	private void ConvertProcess(ConvertData convertData) throws Exception  {

		StringBuilder inputDir1 = new StringBuilder();
		inputDir1.append(sourceDir);
		inputDir1.append(convertData.getagentMediaFileName());
		
		StringBuilder inputDir2 = new StringBuilder();
		inputDir2.append(sourceDir);
		inputDir2.append(convertData.getmemberMediaFileName());
		
		StringBuilder tmpDir1 = new StringBuilder();
		tmpDir1.append(workingDir);
		tmpDir1.append(convertData.getcallUuid());
		tmpDir1.append(underBar);
		tmpDir1.append(tmpFileMark1);
		tmpDir1.append(dot);
		tmpDir1.append(outputFormat);
		
		StringBuilder tmpDir2 = new StringBuilder();
		tmpDir2.append(workingDir);
		tmpDir2.append(convertData.getcallUuid());
		tmpDir2.append(underBar);
		tmpDir2.append(tmpFileMark2);
		tmpDir2.append(dot);
		tmpDir2.append(outputFormat);
		
		StringBuilder outputDir = new StringBuilder();
		//String[] tmpstrs = convertData.getagentMediaFileName().split(vBar);
		String[] tmpstrs = convertData.getagentMediaFileName().split(underBar);
		outputDir.append(targetDir);
		outputDir.append(tmpstrs[0]);
		//outputDir.append(vBar);
		outputDir.append(underBar);
		outputDir.append(convertData.getcallUuid());
		outputDir.append(dot);
		outputDir.append(outputFormat);
		
		FFmpegBuilder builder1 = new FFmpegBuilder()
			.setInput(inputDir1.toString())
			.overrideOutputFiles(true)
			.addOutput(tmpDir1.toString())
				.setFormat(outputFormat)
				.disableSubtitle()
				.setAudioChannels(1)
				.setAudioCodec(audioCodec)
				.setAudioSampleRate(48_000)
				.setAudioBitRate(32768)
				.setVideoCodec(videoCodec)
				.setVideoFrameRate(24, 1)
				.setVideoResolution(240, 240)
				.setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
				.done();
		
		FFmpegBuilder builder2 = new FFmpegBuilder()
			.setInput(inputDir2.toString())
			.overrideOutputFiles(true)
			.addOutput(tmpDir2.toString())
				.setFormat(outputFormat)
				.disableSubtitle()
				.setAudioChannels(1)
				.setAudioCodec(audioCodec)
				.setAudioSampleRate(48_000)
				.setAudioBitRate(32768)
				.setVideoCodec(videoCodec)
				.setVideoFrameRate(24, 1)
				.setVideoResolution(720, 720)
				.setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
				.done();

		StringBuilder cmplxFltr = new StringBuilder();
		String[] tmp = convertData.getvideoTime().split("/|-|:");
		
		cmplxFltr.append(ffmpegComplexFilter);
		cmplxFltr.append(tmp[0]);
		cmplxFltr.append(dash);
		cmplxFltr.append(tmp[1]);
		cmplxFltr.append(dash);
		cmplxFltr.append(tmp[2]);
		cmplxFltr.append("\\ \':timecode=\'");
		cmplxFltr.append(tmp[3]);
		cmplxFltr.append(colon);
		cmplxFltr.append(tmp[4]);
		cmplxFltr.append(colon);
		cmplxFltr.append(tmp[5]);
		cmplxFltr.append(colon);
		cmplxFltr.append(tmp[6]);
		cmplxFltr.append(prime);
		//cmplxFltr.append("\"");
		
		FFmpegBuilder builder = new FFmpegBuilder()
          .addInput(tmpDir1.toString())
          .addInput(tmpDir2.toString())
          .overrideOutputFiles(true)
          .setComplexFilter(cmplxFltr.toString())
          .addOutput(outputDir.toString())
          	.setFormat(outputFormat)
			.disableSubtitle()
			.addExtraArgs("-map", "[aout]")
			.setAudioChannels(2)
			.setAudioCodec(audioCodec)
			.setAudioSampleRate(48_000)
			.setAudioBitRate(32768)
			.setVideoCodec(videoCodec)
			.setVideoFrameRate(24, 1)
			.setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
			.done();
		
		executor.createJob(builder1).run();
		executor.createJob(builder2).run();
		executor.createJob(builder).run();
		
		/*
		 * http convert success
		 */
		
		boolean tranSuc1 = ftpService.uploadOriginal(inputDir1.toString(), inputDir2.toString());
		boolean tranSuc2 = ftpService.uploadConverted(outputDir.toString());
		/*
		 * ftp send converted file 
		 */
		if(tranSuc1 && tranSuc2) {
			/*
			 * http ftp success
			 */
			dbService.eventFinish(convertData);
			logger.info(convertData.getcallUuid()+": Convert finished.");
			
			//deleteFile(inputDir1.toString());
			//deleteFile(inputDir2.toString());
			deleteFile(tmpDir1.toString());
			deleteFile(tmpDir2.toString());
			deleteFile(outputDir.toString());
		}
		else {
			logger.error("SFTP trans fail.");
			/*
			 * http: ftp error
			 */
		}
	}
	
}
