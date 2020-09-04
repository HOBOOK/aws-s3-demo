package com.example.awscloudstorage.S3.repository;

import com.example.awscloudstorage.S3.entity.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, String> {
    public File findByTitle(String title);
}
