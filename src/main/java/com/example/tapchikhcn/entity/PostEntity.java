package com.example.tapchikhcn.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "summary", nullable = false, columnDefinition = "TEXT")
    private String summary;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(name = "view", nullable = false)
    private int view = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private CategoryEntity category;

    @Column(name = "image", nullable = false, length = 200)
    private String image;

    @Column(name = "status", nullable = false, columnDefinition = "ENUM('disable','enable')")
    private String status = "disable";

    @Column(name = "selected", nullable = false)
    private boolean selected = true;

    @Column(name = "breaking_news", nullable = false)
    private boolean breakingNews = true;

    @Column(name = "published_at")
    private Date publishedAt;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "author_name", nullable = false, length = 200)
    private String authorName;

    @Column(name = "file", length = 200)
    private String file;

    @OneToMany(mappedBy = "post")
    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "post")
    private Set<BannerEntity> banners;

    @OneToMany(mappedBy = "post")
    private Set<FileEntity> files;


}