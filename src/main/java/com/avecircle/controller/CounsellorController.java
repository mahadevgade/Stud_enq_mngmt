package com.avecircle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.avecircle.binding.DashboardResponse;
import com.avecircle.entity.Counsellor;
import com.avecircle.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	@Autowired
	private CounsellorService counsellorService;
	

	@GetMapping("/")
	public String index(Model model) {		
		model.addAttribute("counsellor", new Counsellor());
		return "loginview";
	}

	@PostMapping("/login")
	public String handleLogincheck(Counsellor counsellor, HttpServletRequest request ,Model model) {		
		Counsellor counsellorObj = counsellorService.loginCheck(counsellor.getEmail(),counsellor.getPwd());		
		if (counsellorObj==null) {		
			model.addAttribute("errmsg","Invalid Credential plz enter valid credential....");
			return "loginview";			
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("cid", counsellorObj.getCoId());
		
		return "redirect:dashboard";
	}
		
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,Model model) {	
		
		HttpSession session = request.getSession(false);
		session.invalidate();
		
		return "redirect:/";
	}

	@GetMapping("/dashboard")
	public String buildDashboard(HttpServletRequest request ,Model model) {	
		HttpSession session = request.getSession(false);
		Object object = session.getAttribute("cid");
		Integer cid=(Integer)object;
		
		if (cid == null) {
			return "redirect:/logout";
		}

		DashboardResponse dashboardInfo = counsellorService.getDashboardInfo(cid);
		model.addAttribute("dashboard", dashboardInfo);
		return "dashboardview";
	}

	@GetMapping("/register")
	public String regPage(Model model) {
		// to load counsellor Registration page..		
		model.addAttribute("counsellor", new Counsellor());
		return "registerview";
	}
	
	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor, Model model)
	{
		String msg = counsellorService.saveCounsellors(counsellor);		
		model.addAttribute("msg",msg);
		return "registerview";
	}

	@GetMapping("/forgot-pwd")
	public String recoverPwdPage(Model model) {		
		return "forgotPwdview";
	}
	
	@GetMapping("/recover-pwd")
	public String handleRecoverPwd(@RequestParam String email,Model model)
	{
		boolean status = counsellorService.recoverPwd(email);		
		if (status) {
			model.addAttribute("smsg","Pwd sent to your eamil-Id, Plz check email...");		
		} else {
			model.addAttribute("emsg","Plz, Enter vaild Email-Id...");		
		}		
		return "forgotPwdview";
	}
	
}
