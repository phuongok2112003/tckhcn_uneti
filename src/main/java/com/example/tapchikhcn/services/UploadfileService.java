package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.UploadFileRequestDto;
import com.example.tapchikhcn.dto.response.UploadFIleReponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface UploadfileService {
    UploadFIleReponseDto uploadImage( MultipartFile[] files);
    UploadFIleReponseDto uploadPdf( MultipartFile[] files);
    byte[] getImage( String filename);
    byte[] getPdf( String filename) ;
    String deleteImage(String filename);
    String deletePdf( String filename);

    String updateImage(UploadFileRequestDto imageCurr, MultipartFile[]  image);
    String updatepdf(UploadFileRequestDto pdfCurr, MultipartFile[]  pdf);

}
