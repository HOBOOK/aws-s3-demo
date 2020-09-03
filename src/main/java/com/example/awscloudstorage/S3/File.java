package com.example.awscloudstorage.S3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value="File")
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    private Long id;
}
