package com.pdfconversiontox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdfconversiontox.dto.ServiceStatus;
import com.pdfconversiontox.service.PDFConversionService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "src/main/resources/pdf/";
    
    @Autowired
	PDFConversionService conversionService;

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String pdfPath = UPLOADED_FOLDER + file.getOriginalFilename();
            Path path = Paths.get(pdfPath);
            Files.write(path, bytes);
            
            ServiceStatus serviceStatus = conversionService.convertPdfToHtml(pdfPath);

            if(serviceStatus.getStatus().equals("success")) {
            	redirectAttributes.addFlashAttribute("message",
                        "You successfully converted '" + file.getOriginalFilename() + "' to HTML");
            }else {
            	redirectAttributes.addFlashAttribute("message",
                        "Some error occurred while converting '" + file.getOriginalFilename() + "' to HTML");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus(Model model) {
    	model.addAttribute("htmlFiles", conversionService.listFiles());
        return "uploadStatus";
    }

}

