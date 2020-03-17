package com.pdfconversiontox.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

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
import com.pdfconversiontox.dto.HtmlFile;

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
		
		
		String htmlOutputDir = "src/main/resources/static/html/";
		
		if(!env.getProperty("app.pdfconversiontox.html.output.dir").isEmpty())
		{
			htmlOutputDir = env.getProperty("app.pdfconversiontox.html.output.dir");
		}
		
		htmlFileName = htmlOutputDir+htmlFileName;
		
		System.out.println("[Pdf2HtmlUtil][generateHTMLFromPDF][htmlFileName]="+htmlFileName);
		
		PDDocument pdf = PDDocument.load(new File(pdfPath));

		//PDFDomTree parser = new PDFDomTree();
		CustomPDFDomTree customParser = new CustomPDFDomTree();
		Writer output = new PrintWriter(htmlFileName , "utf-8");
		
		customParser.writeText(pdf, output);
	
		output.close();
		pdf.close();
		
		return true;		
	}

	public void generatePDFFromHTML(String filename) throws ParserConfigurationException, IOException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/output/html.pdf"));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
		document.close();
	}
	
	public Set<HtmlFile> listFilesUsingDirectoryStream() throws IOException {
		String htmlOutputDir = "src/main/resources/static/";

		if (!env.getProperty("app.pdfconversiontox.html.output.dir").isEmpty()) {
			htmlOutputDir = env.getProperty("app.pdfconversiontox.html.output.dir");
		}
		System.out.println("[Pdf2HtmlUtil][listFilesUsingDirectoryStream][htmlOutputDir]=" + htmlOutputDir);
		Set<HtmlFile> fileList = new HashSet<HtmlFile>();

		DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(htmlOutputDir));
		
		Path path1 = Paths.get(htmlOutputDir);
		System.out.println("[Pdf2HtmlUtil][listFilesUsingDirectoryStream][path1]=" + path1.getFileName());
		for (Path path : stream) {
			if (!Files.isDirectory(path)) {
				HtmlFile htmlFile = new HtmlFile();
				htmlFile.setFileName(path.getFileName().toString());
				htmlFile.setFileHref(path.getFileName().toString());
				System.out.println("[Pdf2HtmlUtil][listFilesUsingDirectoryStream][htmlFile]=" + htmlFile.getFileName());
				fileList.add(htmlFile);
			}
		}

		return fileList;
	}
}

