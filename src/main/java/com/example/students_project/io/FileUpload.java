package com.example.students_project.io;

import com.example.students_project.dto.FileDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Configuration
public class FileUpload {
    private String methodThatSavesFiles(String originalFileName, String type){
        String fileName = originalFileName.substring(originalFileName.lastIndexOf("."));
        String path = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd"));

        String url = type + "/" + path + "/" + System.currentTimeMillis() + fileName;

        return url;
    }

    public String uploadFile(MultipartFile file, String type){
        String url = methodThatSavesFiles(Objects.requireNonNull(file.getOriginalFilename()), type);
        File saveFile = new File(url);
        if(!saveFile.getParentFile().exists()){
            saveFile.getParentFile().mkdirs();
        }

        try(OutputStream outputStream = new FileOutputStream(url)) {
            outputStream.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return url;
    }
}
