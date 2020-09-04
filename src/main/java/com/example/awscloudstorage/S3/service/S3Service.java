package com.example.awscloudstorage.S3.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.example.awscloudstorage.S3.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class S3Service {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(MultipartFile multipartFile) throws IOException{
        String fileName = multipartFile.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, fileName).toString();
    }

    public List<File> readAll(){
        List<com.example.awscloudstorage.S3.entity.File> s3Files = new ArrayList<>();
        ObjectListing objectListing = s3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));

        for(S3ObjectSummary summary : objectListing.getObjectSummaries()){
            System.out.println(summary);
            com.example.awscloudstorage.S3.entity.File file = new com.example.awscloudstorage.S3.entity.File();
            file.setTitle(summary.getKey());
            file.setFileSize(summary.getSize());
            s3Files.add(file);
        }
        return s3Files;
    }
}
