package com.example.tapchikhcn.dto.request;

import com.example.tapchikhcn.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private int id;
    private String title;
    private String summary;
    private String body;
    private int view = 0;
    private UserEntity user;
    private CategoryRequestDto category;
    private String image;
    private String status = "disable";
    private boolean selected = true;
    private boolean breakingNews = true;
    private Date publishedAt;
    private Date createdAt;
    private Date updatedAt;
    private String authorName;
    private String file;
}
