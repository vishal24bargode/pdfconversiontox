package com.pdfconversiontox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pdfconversiontox.service.ConversionService;

@RestController
public class ConversionController {
	
	@Autowired
	ConversionService conversionService;
	
	@RequestMapping("/")
	public String index() {
		return "Conversion Service Ready for Use! Jenkin Deployment Testing 4";
	}
	
	@RequestMapping("/map1")
	public String map1() {
		return "Map-1";
	}
	
	@RequestMapping("/pdf/to/html")
	public String pdfToHtml(@RequestParam String pdfPath) {
		return conversionService.convertPdfToHtml(pdfPath);
	}
}
