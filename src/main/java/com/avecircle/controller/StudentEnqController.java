package com.avecircle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.avecircle.binding.SearchCriteria;
import com.avecircle.entity.StudentEnq;
import com.avecircle.service.StudentEnqService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class StudentEnqController {
	
	@Autowired
	private StudentEnqService enqService;
	

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model)
	{
		model.addAttribute("studentEnq",new StudentEnq());
		return "addEnqview";
	}

	@PostMapping("/enquiry")
	public String handleAddEnquiry(@ModelAttribute("studentEnq") StudentEnq studentEnq, HttpServletRequest request,  Model model)
	{
		HttpSession session = request.getSession(false);
		Integer cId=(Integer)session.getAttribute("cid");
		
		if (cId == null) {
			return "redirect:/logout";
		}
		
		studentEnq.setCid(cId);
		
		boolean status = enqService.addEnq(studentEnq);
		if (status) {
			model.addAttribute("smsg","Enquiry added successfully...");
		} else {
			model.addAttribute("emsg","Enquiry not saved...");
		}
		return "redirect:/enquiry";
	}
	
	@GetMapping("/enquiries")
	public String viewEnquireis(HttpServletRequest request ,Model model)
	{
		HttpSession session = request.getSession(false);
		Integer cid =(Integer)session.getAttribute("cid");
		
		if (cid == null) {
			return "redirect:/logout";
		}
		
		model.addAttribute("sc",new SearchCriteria());
		
		List<StudentEnq> enquiriesList = enqService.getEnquiries(cid, new SearchCriteria());
		model.addAttribute("enquiries",enquiriesList);
		return "displayEnqview";				
	}
	
	@PostMapping("/filter")
	public String filterEnquiries(@ModelAttribute("sc") SearchCriteria sc, HttpServletRequest request, Model model)
	{
		HttpSession session = request.getSession(false);
		Object object = session.getAttribute("cid");
		Integer cid=(Integer)object;
		
		if (cid == null) {
			return "redirect:/logout";
		}
		
		List<StudentEnq> enquiriesList = enqService.getEnquiries(cid, sc);
		model.addAttribute("enquiries",enquiriesList);
		return "filterEnqView";
	}
}
