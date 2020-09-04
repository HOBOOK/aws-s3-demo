package com.example.awscloudstorage.S3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(value="File")
@NoArgsConstructor
public class File {
    @Id
    private String id;
    private String title;
    private String filePath;
    private Long fileSize;
    private Date lastModified;
}
