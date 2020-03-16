package com.pdfconversiontox.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Repository
public class Pdf2HtmlUtil {
	
	@Autowired
	private Environment env;

	public Boolean generateHTMLFromPDF(String pdfPath) throws ParserConfigurationException, IOException {
		String pdfPathSplit[] = pdfPath.split("/");
		String htmlFileName = pdfPathSplit[pdfPathSplit.length-1];
		
		if(htmlFileName.endsWith(".pdf") || htmlFileName.endsWith(".PDF")) {
			htmlFileName = htmlFileName.replace(".pdf", ".html");
			htmlFileName = htmlFileName.replace(".PDF", ".html");
		}
		
		
		String htmlOutputDir = "src/main/resources/output/";
		
		if(!env.getProperty("app.pdfconversiontox.html.output.dir").isEmpty())
		{
			htmlOutputDir = env.getProperty("app.pdfconversiontox.html.output.dir");
		}
		
		htmlFileName = htmlOutputDir+htmlFileName;
		
		System.out.println("[Pdf2HtmlUtil][generateHTMLFromPDF][htmlFileName]="+htmlFileName);
		
		PDDocument pdf = PDDocument.load(new File(pdfPath));

		PDFDomTree parser = new PDFDomTree();
		
		Writer output = new PrintWriter(htmlFileName , "UTF-16");
		
		parser.writeText(pdf, output);
	
		output.close();
		return true;		
	}

	public void generatePDFFromHTML(String filename) throws ParserConfigurationException, IOException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/output/html.pdf"));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
		document.close();
	}
}

