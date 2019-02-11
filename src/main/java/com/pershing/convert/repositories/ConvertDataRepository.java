package com.pershing.convert.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pershing.convert.entities.ConvertData;

@Repository
public interface ConvertDataRepository extends JpaRepository<ConvertData, String>{
	//@Query()
	List<ConvertData> findByConvertStateLessThan(int convertState);
	List<ConvertData> findByConvertState(int convertState);
}