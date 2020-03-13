package com.pdfconversiontox.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;

import com.pdfconversiontox.util.Pdf2HtmlUtil;

@Service
public class ConversionService {
	
	Pdf2HtmlUtil pdftohtmlUtil = new Pdf2HtmlUtil();
	
	public String convertPdfToHtml(String pdfPath) {
		
		String status = "failed";
		
		try {
			Boolean success = pdftohtmlUtil.generateHTMLFromPDF(pdfPath);
			if(success) {
				status="success";
			}
		} catch (ParserConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;		
	}

}
