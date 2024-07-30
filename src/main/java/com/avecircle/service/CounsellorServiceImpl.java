package com.avecircle.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avecircle.binding.DashboardResponse;
import com.avecircle.entity.Counsellor;
import com.avecircle.entity.StudentEnq;
import com.avecircle.repo.CounsellorRepo;
import com.avecircle.repo.StudentEnqRepo;
import com.avecircle.utils.EmailUtils;

@Service
public class CounsellorServiceImpl implements CounsellorService{
	
	@Autowired
	private CounsellorRepo counsellorRepo;
	
	@Autowired
	public StudentEnqRepo studentEnqRepo;
	
	@Autowired
	public EmailUtils emailUtils;

	@Override
	public String saveCounsellors(Counsellor counsellor) {			
		Counsellor counsellorob = counsellorRepo.findByEmail(counsellor.getEmail());		
		if (counsellorob !=null) {
			return "Duplicate Email-Id...";
		}
				
		Counsellor counsellorObj = counsellorRepo.save(counsellor);
		if (counsellorObj.getCoId()!=null) {
			return "Record saved succsessfully...";
		}
		return "Record is not saved...";
	}

	@Override
	public Counsellor loginCheck(String email, String pwd) {
		Counsellor counsellor = counsellorRepo.findByEmailAndPwd(email, pwd);
		return counsellor;		
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer coId) {
		List<StudentEnq> totalEnqs = studentEnqRepo.findByCid(coId);
		int enrollEnq = totalEnqs.stream()
				.filter(e->e.getStatus().equals("enrolled"))
				.collect(Collectors.toList())
				.size();
		
		DashboardResponse response=new DashboardResponse();
		response.setTotalEnq(totalEnqs.size());
		response.setEnrollEnq(enrollEnq);
		response.setLostEnq(totalEnqs.size()-enrollEnq);	
		return response;
	}

	@Override
	public boolean recoverPwd(String email) {		
		Counsellor counsellor = counsellorRepo.findByEmail(email);		
		if (counsellor==null) {			
			return false;
		}		
		String subject="Recover password regarding...";
		String body="<h1>Your Password: "+counsellor.getPwd()+"</h1>";
		return emailUtils.sendEmails(subject, body, email);		 
	}

}