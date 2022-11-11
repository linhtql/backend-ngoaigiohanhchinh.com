package com.nekol.service.impl;

import com.cloudinary.Cloudinary;
import com.nekol.service.IFileStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FileStorageService implements IFileStorageService {

    @Autowired
    Cloudinary cloudinary;

    @Override
    public String storageFile(MultipartFile file) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            Map<String, String> uploadParams = new HashMap<>();
            uploadParams.put("resource_type", "auto");
            Map uploadResult = cloudinary.uploader().uploadLarge(uploadedFile, uploadParams);

            boolean isDeleted = uploadedFile.delete();
            if (isDeleted) {
                System.out.println("File successfully deleted");
            } else System.out.println("File doesn't exist");
            return uploadResult.get("url").toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public File convertMultiPartToFile(MultipartFile file) throws IOException {

        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generatedFileName = UUID.randomUUID().toString().replace("-", "");
        generatedFileName = generatedFileName + "." + fileExtension;
        File convFile = new File(generatedFileName);

        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
