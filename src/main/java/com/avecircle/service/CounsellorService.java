package com.avecircle.service;

import com.avecircle.binding.DashboardResponse;
import com.avecircle.entity.Counsellor;

public interface CounsellorService {
	
	public String saveCounsellors(Counsellor counsellor);
	
	public Counsellor loginCheck(String email,String pwd);
	
	public DashboardResponse getDashboardInfo(Integer coId);
	
	public boolean recoverPwd(String email);
	
	

}
