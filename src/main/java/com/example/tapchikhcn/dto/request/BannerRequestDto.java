package com.example.tapchikhcn.dto.request;

import com.example.tapchikhcn.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequestDto {
    private int id;
    private String image;
    private int type;
    private Date createdAt;
    private Date updatedAt;
    private PostRequestDto post;
}
