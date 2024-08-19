package com.example.tapchikhcn.services;

import com.example.tapchikhcn.dto.request.UploadFileRequestDto;
import com.example.tapchikhcn.dto.response.UploadFIleReponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadfileService {
    List<UploadFIleReponseDto> uploadImage(MultipartFile[] files);
    List<UploadFIleReponseDto> uploadPdf( MultipartFile[] files);
    byte[] getImage( String filename);
    byte[] getPdf( String filename) ;
    String deleteImage(String filename);
    String deletePdf( String filename);

    List<UploadFIleReponseDto> updateImage(UploadFileRequestDto imageCurr, MultipartFile[]  image);
    List<UploadFIleReponseDto> updatepdf(UploadFileRequestDto pdfCurr, MultipartFile[]  pdf);

}
