package com.example.tapchikhcn.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UploadFileRequestDto {
    List<String> filename;

    public List<String> getFilename() {
        List<String> list=new ArrayList<>();
        for(String s:filename){
            String[] parts = s.split("/");
           list.add (parts[parts.length - 1]);
        }
        return list;
    }
}
