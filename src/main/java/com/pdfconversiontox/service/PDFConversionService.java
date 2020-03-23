package com.pdfconversiontox.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pdfconversiontox.dto.HtmlFile;
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
	
	public Set<HtmlFile> listFiles(Path htmlPath){
		Set<HtmlFile> fileList = new HashSet<HtmlFile>();
		try {
			fileList = pdftohtmlUtil.listFilesUsingDirectoryStream(htmlPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileList;
	}
	

	public String getApplicationPath() throws java.io.UnsupportedEncodingException {

		String path = this.getClass().getClassLoader().getResource("").getPath();

		String fullPath = URLDecoder.decode(path, "UTF-8");

		String pathArr[] = fullPath.split("/WEB-INF/classes/");

		System.out.println("[getApplicationPath][fullPath]"+fullPath);

		System.out.println("[getApplicationPath][pathArr]"+pathArr[0]);

		fullPath = pathArr[0];

		return fullPath;

	}

}
