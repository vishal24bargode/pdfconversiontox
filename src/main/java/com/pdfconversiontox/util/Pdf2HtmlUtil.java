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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Pdf2HtmlUtil {

	public Boolean generateHTMLFromPDF(String pdfPath) throws ParserConfigurationException, IOException {
		String pdfPathSplit[] = pdfPath.split("/");
		String htmlFileName = pdfPathSplit[pdfPathSplit.length-1];
		PDDocument pdf = PDDocument.load(new File(pdfPath));
		PDFDomTree parser = new PDFDomTree();
		Writer output = new PrintWriter("src/main/resources/output/"+htmlFileName+".html", "utf-8");
		parser.writeText(pdf, output);
		
		output.close();
		if (pdf != null) {			
			pdf.close();
			return true;
		}
		
		return false;
	}

	public void generatePDFFromHTML(String filename) throws ParserConfigurationException, IOException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/output/html.pdf"));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
		document.close();
	}
}

