package com.example.tapchikhcn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WebSettingRequestDto {
    private int id;
    private String title;
    private String description;
    private String keywords;
    private String logo;
    private String icon;
    private Date createdAt;
    private Date updatedAt;
}
