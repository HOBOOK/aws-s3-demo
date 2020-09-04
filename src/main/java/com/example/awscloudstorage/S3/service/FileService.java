package com.example.awscloudstorage.S3.service;

import com.example.awscloudstorage.S3.entity.File;
import com.example.awscloudstorage.S3.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public void saveFile(File file){
        fileRepository.save(file);
    }

    public File getFile(String keyName){
        return fileRepository.findByTitle(keyName);
    }
    public void deleteFile(File file){
        fileRepository.delete(file);
    }
}
