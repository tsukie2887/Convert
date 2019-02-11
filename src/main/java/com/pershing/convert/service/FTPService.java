package com.pershing.convert.service;

public interface FTPService {
	boolean uploadOriginal(String inputDir1, String inputDir2)throws Exception;
	boolean uploadConverted( String outputDir)throws Exception;
}
