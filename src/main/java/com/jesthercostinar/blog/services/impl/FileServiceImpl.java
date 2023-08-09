package com.jesthercostinar.blog.services.impl;

import com.jesthercostinar.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // File Name
        String name = file.getOriginalFilename(); // default.png

        // Random name generate file
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        // Full path
        String filePath = path + File.separator + fileName;

        // Create folder if folder not exist
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // File copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        String fullPath = path + File.separator + filename;

        return new FileInputStream(fullPath);
    }
}
