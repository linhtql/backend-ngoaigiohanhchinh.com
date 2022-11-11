package com.nekol.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {
    String storageFile(MultipartFile file);
}
