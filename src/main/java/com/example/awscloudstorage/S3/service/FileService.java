package com.example.awscloudstorage.S3.service;

import com.example.awscloudstorage.S3.entity.File;
import com.example.awscloudstorage.S3.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    // 파일 업로드시 DB에 파일 정보 저장
    public void saveFile(File file){
        fileRepository.save(file);
    }

    // 키(파일이름)을 통해 DB 조회
    public File getFile(String keyName){
        return fileRepository.findByTitle(keyName);
    }
    
    // 파일 제거시 DB에 파일 정보 제거
    public void deleteFile(File file){
        fileRepository.delete(file);
    }
}
