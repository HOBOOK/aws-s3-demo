package com.example.awscloudstorage.S3.controller;

import com.example.awscloudstorage.S3.entity.File;
import com.example.awscloudstorage.S3.service.FileService;
import com.example.awscloudstorage.S3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired private S3Service s3Service;
    @Autowired private FileService fileService;

    @GetMapping
    public String viewFile(){
        return "index";
    }

    @PostMapping
    public String createFile(File file, @RequestParam(value="file", required=true)MultipartFile multipartFile) throws IOException{
        String path = s3Service.upload(multipartFile);
        file.setFilePath(path);
        fileService.saveFile(file);
        return "redirect:/file";
    }



}
