package com.avecircle.service;

import java.util.List;

import com.avecircle.binding.SearchCriteria;
import com.avecircle.entity.StudentEnq;

public interface StudentEnqService {
	
	public boolean addEnq(StudentEnq studentEnq);
	
	public List<StudentEnq>  getEnquiries(Integer cid, SearchCriteria searchCriteria);

}
