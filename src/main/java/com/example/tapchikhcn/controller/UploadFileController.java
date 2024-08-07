package com.example.tapchikhcn.controller;

import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.dto.response.UploadFIleReponseDto;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.utils.EOResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tapchikhcn.constans.ErrorCodes.ERROR_CODE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class UploadFileController {

    @Value("${file.upload-pdf-dir:uploads/pdfs/}")
    private String PDF_UPLOAD_DIR;

    @Value("${file.upload-image-dir:uploads/images/}")
    private String IMAGE_UPLOAD_DIR;

    @PostMapping("/upload-image")
    public EOResponse<UploadFIleReponseDto> uploadImage(@RequestParam("files") MultipartFile[] files) {
        UploadFIleReponseDto uploadFIleReponseDto=new UploadFIleReponseDto();
        List<String> url=new ArrayList<>();
        for(MultipartFile file:files){


        if (file.isEmpty()) {
            throw new EOException(ERROR_CODE,
                    MessageCodes.NOT_NULL,file.getName());
        }
        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
                contentType.equals(MediaType.IMAGE_GIF_VALUE))) {
            throw new EOException(ERROR_CODE,
                    MessageCodes.FILE_UPLOAD_NOT_FORMAT,file.getName());
        }
        try {
            File directory = new File(IMAGE_UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path path = Paths.get(IMAGE_UPLOAD_DIR + filename);
            Files.write(path, file.getBytes());
            url.add("./public/post-image/"+filename);

        } catch (IOException e) {
            throw new EOException(ERROR_CODE,
                    e.getMessage(),file.getName());
        }
        }
        uploadFIleReponseDto.setUrls(url);
        return EOResponse.build(uploadFIleReponseDto);
    }

    @PostMapping("/upload-pdf")
    public EOResponse<UploadFIleReponseDto> uploadPdf(@RequestParam("files") MultipartFile[] files) {
        UploadFIleReponseDto uploadFIleReponseDto=new UploadFIleReponseDto();
        List<String> url=new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new EOException(ERROR_CODE,
                        MessageCodes.NOT_NULL,file.getName());
            }

            if (!file.getContentType().equals("application/pdf")) {
                throw new EOException(ERROR_CODE,
                        MessageCodes.FILE_UPLOAD_NOT_FORMAT,file.getName());
            }

            try {
                File directory = new File(PDF_UPLOAD_DIR);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                Path path = Paths.get(PDF_UPLOAD_DIR + filename);
                Files.write(path, file.getBytes());
                url.add("./public/upload/"+filename);
            } catch (IOException e) {
                throw new EOException(ERROR_CODE,
                        e.getMessage(),file.getName());
            }
        }
        uploadFIleReponseDto.setUrls(url);
        return EOResponse.build(uploadFIleReponseDto);
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
