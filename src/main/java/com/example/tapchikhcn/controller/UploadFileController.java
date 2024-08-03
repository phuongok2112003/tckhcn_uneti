package com.example.tapchikhcn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class UploadFileController {

    @Value("${file.upload-pdf-dir:uploads/pdfs/}")
    private String PDF_UPLOAD_DIR;

    @Value("${file.upload-image-dir:uploads/images/}")
    private String IMAGE_UPLOAD_DIR;

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
        }
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
                contentType.equals(MediaType.IMAGE_GIF_VALUE))) {
            return new ResponseEntity<>("Invalid file type. Only JPEG, PNG, and GIF are allowed.", HttpStatus.BAD_REQUEST);
        }
        try {
            File directory = new File(IMAGE_UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path path = Paths.get(IMAGE_UPLOAD_DIR + filename);
            Files.write(path, file.getBytes());

            return new ResponseEntity<>("Image uploaded successfully: " + path.toString(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload-pdf")
    public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
        }

        if (!file.getContentType().equals("application/pdf")) {
            return new ResponseEntity<>("File must be a PDF", HttpStatus.BAD_REQUEST);
        }

        try {
            File directory = new File(PDF_UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path path = Paths.get(PDF_UPLOAD_DIR + filename);
            Files.write(path, file.getBytes());

            return new ResponseEntity<>("PDF uploaded successfully: " + path.toString(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload PDF: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(IMAGE_UPLOAD_DIR + filename);
            byte[] image = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);  // Thay đổi nếu bạn hỗ trợ các định dạng ảnh khác như PNG, GIF...
            headers.setContentDispositionFormData("inline", filename);

            return ResponseEntity.ok().headers(headers).body(image);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pdf/{filename}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String filename) {
        try {
            Path path = Paths.get(PDF_UPLOAD_DIR + filename);
            byte[] pdf = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", filename);
            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/image/{filename}")
    public ResponseEntity<String> deleteImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(IMAGE_UPLOAD_DIR + filename);
            Files.deleteIfExists(path);
            return new ResponseEntity<>("Image deleted successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/pdf/{filename}")
    public ResponseEntity<String> deletePdf(@PathVariable String filename) {
        try {
            Path path = Paths.get(PDF_UPLOAD_DIR + filename);
            Files.deleteIfExists(path);
            return new ResponseEntity<>("PDF deleted successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete PDF: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
