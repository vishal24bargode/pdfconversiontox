package com.pdfconversiontox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pdfconversiontox.dto.ServiceStatus;
import com.pdfconversiontox.service.PDFConversionService;

@RestController
public class ConversionController {
	
	@Autowired
	PDFConversionService conversionService;
	
	@RequestMapping("/")
	public String index() {
		return "Conversion Service Ready for Use!";
	}

	
	@RequestMapping("/pdf/to/html")
	public ResponseEntity<ServiceStatus> pdfToHtml(@RequestParam String pdfPath) {
		ServiceStatus serviceStatus = conversionService.convertPdfToHtml(pdfPath);
		System.out.println("[ConversionController][pdfToHtml][status]="+serviceStatus.toString());
		return new ResponseEntity<ServiceStatus>(serviceStatus, HttpStatus.OK);
	}
}
