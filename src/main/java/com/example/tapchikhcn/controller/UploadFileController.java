package com.example.tapchikhcn.controller;
import com.example.tapchikhcn.dto.request.LoginRequest;
import com.example.tapchikhcn.dto.request.UploadFileRequestDto;
import com.example.tapchikhcn.dto.response.UploadFIleReponseDto;
import com.example.tapchikhcn.services.UploadfileService;
import com.example.tapchikhcn.utils.EOResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class UploadFileController {

    private final UploadfileService uploadfileService;
    @PostMapping("/upload-image")
    public EOResponse<UploadFIleReponseDto> uploadImage(@RequestParam("files") MultipartFile[] files) {

        return EOResponse.build(uploadfileService.uploadImage(files));
    }

    @PostMapping("/upload-pdf")
    public EOResponse<UploadFIleReponseDto> uploadPdf(@RequestParam("files") MultipartFile[] files) {

        return EOResponse.build(uploadfileService.uploadPdf(files));
    }
    @GetMapping("/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
       byte[] imgBytes=uploadfileService.getImage(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", filename);
        if(imgBytes!=null)
        return ResponseEntity.ok().headers(headers).body(imgBytes);
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/pdf/{filename}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String filename) {
       byte[] pdf= uploadfileService.getPdf(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", filename);
        if(pdf!=null)
            return ResponseEntity.ok().headers(headers).body(pdf);
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/image/{filename}")
    public ResponseEntity<String> deleteImage(@PathVariable String filename) {
        String result=uploadfileService.deleteImage(filename);
        return new ResponseEntity<>(result,result.equals("Thành công")?HttpStatus.OK:HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/pdf/{filename}")
    public ResponseEntity<String> deletePdf(@PathVariable String filename) {
        String result=uploadfileService.deletePdf(filename);
        return new ResponseEntity<>(result,result.equals("Thành công")?HttpStatus.OK:HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PutMapping("/image")
    public EOResponse<UploadFIleReponseDto> putImage(@RequestParam("filename") String filename, @RequestParam("files") MultipartFile[] files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UploadFileRequestDto request = objectMapper.readValue(filename, UploadFileRequestDto.class);
        UploadFIleReponseDto result=uploadfileService.updateImage(request,files);
        return EOResponse.build(result);
    }
    @PutMapping("/pdf")
    public EOResponse<UploadFIleReponseDto> putpdf(@RequestParam("filename") String filename, @RequestParam("files") MultipartFile[] files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UploadFileRequestDto request = objectMapper.readValue(filename, UploadFileRequestDto.class);
        UploadFIleReponseDto result=uploadfileService.updatepdf(request,files);
        return  EOResponse.build(result);
    }
}
