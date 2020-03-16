package com.pdfconversiontox.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdfconversiontox.dto.ServiceStatus;
import com.pdfconversiontox.util.Pdf2HtmlUtil;

@Service
public class PDFConversionService {
	
	@Autowired
	Pdf2HtmlUtil pdftohtmlUtil;
	
	public ServiceStatus convertPdfToHtml(String pdfPath) {
		
		ServiceStatus serviceStatus = new ServiceStatus();
		
		try {
			Boolean success = pdftohtmlUtil.generateHTMLFromPDF(pdfPath);
			if(success) {
				serviceStatus.setStatus("success");
			}else {
				serviceStatus.setStatus("failed");
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			serviceStatus.setErrorMessage(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
				// TODO Auto-generated catch block
			serviceStatus.setErrorMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return serviceStatus;		
	}

}
