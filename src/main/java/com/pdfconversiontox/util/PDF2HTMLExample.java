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

public class PDF2HTMLExample {

	private static final String PDF = "src/main/resources/Feb2020.pdf";
	private static final String HTML = "src/main/resources/PR2045711B6CCB61A54219AA03F8CA1B7BF1E5.html";

	public static void main(String[] args) {
		try {
			generateHTMLFromPDF(PDF);
			//generatePDFFromHTML(HTML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private static void generateHTMLFromPDF(String filename) throws ParserConfigurationException, IOException {
		PDDocument pdf = PDDocument.load(new File(filename));
		PDFDomTree parser = new PDFDomTree();
		Writer output = new PrintWriter("src/main/resources/output/Feb2020.html", "utf-8");
		parser.writeText(pdf, output);
		output.close();
		if (pdf != null) {
			pdf.close();
		}
	}

	private static void generatePDFFromHTML(String filename) throws ParserConfigurationException, IOException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/output/html.pdf"));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
		document.close();
	}
}

