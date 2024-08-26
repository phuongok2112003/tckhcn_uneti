package com.example.tapchikhcn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogResponseDto {
    private int id;
    private String author;
    private String email;
    private Date sentDate;
    private String logStatus;
    private String category;
    private String note;
}
