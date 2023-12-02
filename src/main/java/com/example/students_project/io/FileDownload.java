package com.example.students_project.io;

import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FileDownload {
    public byte[] loadImage(String filePath) {
        try(InputStream in = new FileInputStream(filePath)) {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
