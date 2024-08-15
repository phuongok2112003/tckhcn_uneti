package com.example.tapchikhcn.services.Impl;
import com.example.tapchikhcn.constans.MessageCodes;
import com.example.tapchikhcn.dto.request.UploadFileRequestDto;
import com.example.tapchikhcn.dto.response.UploadFIleReponseDto;
import com.example.tapchikhcn.exceptions.EOException;
import com.example.tapchikhcn.services.UploadfileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class UploadFileServiceImpl implements UploadfileService {
    @Value("${file.upload-pdf-dir}")
    private String PDF_UPLOAD_DIR;

    @Value("${file.upload-image-dir}")
    private String IMAGE_UPLOAD_DIR;

    @Override
    public UploadFIleReponseDto uploadImage(MultipartFile[] files) {
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
        return uploadFIleReponseDto;
    }

    @Override
    public UploadFIleReponseDto uploadPdf(MultipartFile[] files) {
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
        return uploadFIleReponseDto;
    }

    @Override
    public byte[] getImage(String filename) {
        try {
            Path path = Paths.get(IMAGE_UPLOAD_DIR + filename);
            byte[] image = Files.readAllBytes(path);
            return image;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public byte[] getPdf(String filename) {
        try {
            Path path = Paths.get(PDF_UPLOAD_DIR + filename);
            byte[] pdf = Files.readAllBytes(path);
            return pdf;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String deleteImage(String filename) {
        try {
            Path path = Paths.get(IMAGE_UPLOAD_DIR + filename);
            Files.deleteIfExists(path);
            return "Thành công";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public String deletePdf(String filename) {
        try {
            Path path = Paths.get(PDF_UPLOAD_DIR + filename);
            Files.deleteIfExists(path);
            return "Thành công";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    public UploadFIleReponseDto updateImage(UploadFileRequestDto imageCurr, MultipartFile[] image) {

        for(String img:imageCurr.getFilename()){
            deleteImage(img);
        }
           return uploadImage(image);

    }

    @Override
    public UploadFIleReponseDto updatepdf(UploadFileRequestDto pdfCurr,  MultipartFile[]  pdf) {
        for(String p:pdfCurr.getFilename()) {
            deletePdf(p);
        }
        return uploadPdf(pdf);

    }
}
