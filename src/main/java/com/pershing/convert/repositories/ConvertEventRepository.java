package com.pershing.convert.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pershing.convert.entities.ConvertEvent;

@Repository
public interface ConvertEventRepository extends JpaRepository<ConvertEvent, Long>{
	//@Query()
}