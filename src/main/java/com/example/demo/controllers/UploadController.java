package com.example.demo.controllers;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.models.AccessTier;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.io.BufferedInputStream;

@Controller
public class UploadController {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container-name}")
    private String containerName;

    @GetMapping("/up")
    public String index() {
        return "upload";
    }
    
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "upload";
        }
    
        try (InputStream originalInputStream = file.getInputStream();
             BufferedInputStream inputStream = new BufferedInputStream(originalInputStream)) {
            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    
            // Create a blob client
            BlockBlobClient blobClient = new BlobClientBuilder()
                    .connectionString(connectionString)
                    .containerName(containerName)
                    .blobName(fileName)
                    .buildClient()
                    .getBlockBlobClient();
    
            // Upload the file
            blobClient.upload(inputStream, file.getSize(), true);
            blobClient.setAccessTier(AccessTier.COOL);
    
            model.addAttribute("message", "File uploaded successfully: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Failed to upload file: " + e.getMessage());
        }
    
        return "upload";
        
    }
}
