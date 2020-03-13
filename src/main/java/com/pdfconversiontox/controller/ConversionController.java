package com.pdfconversiontox.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionController {
	
	@RequestMapping("/")
	public String index() {
		return "Conversion Service Ready for Use! Jenkin Deployment Testing 4";
	}
	
	@RequestMapping("/map1")
	public String map1() {
		return "Map-1";
	}
	
	@RequestMapping("/pdf/to/html")
	public String pdfToHtml() {
		return "PDF To HTML Conversion!";
	}
}
