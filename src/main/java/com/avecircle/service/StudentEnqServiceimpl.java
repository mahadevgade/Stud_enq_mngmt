package com.avecircle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.avecircle.binding.SearchCriteria;
import com.avecircle.entity.StudentEnq;
import com.avecircle.repo.StudentEnqRepo;

@Service
public class StudentEnqServiceimpl implements StudentEnqService {

	@Autowired
	private StudentEnqRepo studentEnqRepo;

	@Override
	public boolean addEnq(StudentEnq studentEnq) {
		/*
		 * => enq.getEnqId() != null, means Record inserted means primary key generated,
		 * => when primary key generated, if Record saved successfully it will return
		 * true,otherwise not generated..
		 */

		StudentEnq enq = studentEnqRepo.save(studentEnq);

		return enq.getEnqId() != null;
	}

	@Override
	public List<StudentEnq> getEnquiries(Integer cid, SearchCriteria sc) {

		 StudentEnq studEnq = new StudentEnq();
		 
		  // Setting counsellor Id
		 studEnq.setCid(cid);
		 		  
		// if mode selected add to query
		if (sc.getClassMode() != null && !sc.getClassMode().equals("")) {
			studEnq.setClassMode(sc.getClassMode());
		}

		if (sc.getCourse() != null && !sc.getCourse().equals("")) {
			studEnq.setCourse(sc.getCourse());
		}
		if (sc.getStatus() != null && !sc.getStatus().equals("")) {
			studEnq.setStatus(sc.getStatus());
		}

		 Example<StudentEnq> of = Example.of(studEnq);
		 List<StudentEnq> listEnqs = studentEnqRepo.findAll(of);

		 return listEnqs;



	}

}
