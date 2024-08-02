package com.example.tapchikhcn.dto.response;

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
public class PostResponseDto
{
    private int id;
    private String title;
    private String summary;
    private String body;
    private int view = 0;
    private UserResponseDto user;
    private CategoryResponseDto category;
    private String image;
    private String status = "disable";
    private boolean selected = true;
    private boolean breakingNews = true;
    private Date publishedAt;
    private Date createdAt;
    private Date updatedAt;
    private String authorName;
    private String file;
    private List<CommentResponseDto> comments;
    private List<BannerResponseDto> banners;
    private List<FileResponseDto> files;
}
