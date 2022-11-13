package com.nekol.controller;

import com.nekol.model.payload.FileUploadResponse;
import com.nekol.service.IFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class FileUploadController {

    @Autowired
    private IFileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storageFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new FileUploadResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    @PostMapping("/uploadMultipleFiles")
    public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }


}
