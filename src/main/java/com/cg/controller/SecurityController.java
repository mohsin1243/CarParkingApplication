package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.service.SecurityService;

@RestController
@RequestMapping(path = "/api/security")
public class SecurityController {

	@Autowired
	private SecurityService securityService;

	@GetMapping("/allToken")
	public String allToken() {
		return securityService.getTotalTokenCount();
	}
}
