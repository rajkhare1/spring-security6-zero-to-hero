package com.rajkhare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	@GetMapping("/contact")
	public String saveContanctInquiryDetails() {
		return "Inquiry details are save to the DB";
	}
}
 