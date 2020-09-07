package com.example.awscloudstorage.S3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.example.awscloudstorage.S3.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
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

    // AWS S3 스토리지에 업로드
    public String upload(MultipartFile multipartFile) throws IOException{
        String fileName = multipartFile.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, fileName).toString();
    }

    // AWS S3 스토리지에서 키(파일이름)를 통해 파일 제거
    public void delete(String key) throws AmazonServiceException{
        s3Client.deleteObject(new DeleteObjectRequest(bucket, key));
    }

    // AWS S3 스토리지에서 키(파일이름)를 통해 다운로드
    @Async
    public byte[] download(final String key) throws IOException{
        byte[] content = null;
        S3Object s3Object = s3Client.getObject(bucket,key);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        content = IOUtils.toByteArray(s3ObjectInputStream);
        s3Object.close();
        return content;
    }

    // AWS S3 스토리지에 있는 파일 목록 불러오기
    public List<File> readAll(){
        List<com.example.awscloudstorage.S3.entity.File> s3Files = new ArrayList<>();
        ObjectListing objectListing = s3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));

        for(S3ObjectSummary summary : objectListing.getObjectSummaries()){
            System.out.println(summary);
            com.example.awscloudstorage.S3.entity.File file = new com.example.awscloudstorage.S3.entity.File();
            file.setTitle(summary.getKey());
            file.setFileSize(summary.getSize());
            file.setLastModified(summary.getLastModified());
            s3Files.add(file);
        }
        return s3Files;
    }
}
