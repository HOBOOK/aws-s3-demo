package com.example.awscloudstorage.S3.controller;

import com.amazonaws.AmazonServiceException;
import com.example.awscloudstorage.S3.entity.File;
import com.example.awscloudstorage.S3.service.FileService;
import com.example.awscloudstorage.S3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {
    @Autowired private S3Service s3Service;
    @Autowired private FileService fileService;

    @GetMapping("/")
    public ResponseEntity<List<File>> readFile(){
        return ResponseEntity.ok().body(s3Service.readAll());
    }

    @PostMapping("/file")
    public ResponseEntity<File> createFile(@RequestParam(value="file", required=true)MultipartFile multipartFile) throws IOException{
        String path = s3Service.upload(multipartFile);
        File file = new File();
        file.setFilePath(path);
        fileService.saveFile(file);
        return ResponseEntity.ok().body(file);
    }

    @GetMapping("/file")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value="title") final String key) throws IOException{
        byte[] data = s3Service.download(key);
        ByteArrayResource byteArrayResource = new ByteArrayResource(data);
        return ResponseEntity.ok().contentLength(data.length).header("Content-type", "application/octet-stream")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .body(byteArrayResource);
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestParam(value="title") final String key) throws AmazonServiceException{

        try{
            s3Service.delete(key);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
