package com.avecircle.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avecircle.entity.StudentEnq;

public interface StudentEnqRepo extends JpaRepository<StudentEnq, Integer>{
	
	public List<StudentEnq> findByCid(Integer cid);

}
