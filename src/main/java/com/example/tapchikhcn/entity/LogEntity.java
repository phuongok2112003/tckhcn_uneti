package com.example.tapchikhcn.entity;

import com.example.tapchikhcn.constans.enums.LogStatus;
import com.example.tapchikhcn.constans.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "logs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "author", nullable = false, columnDefinition = "TEXT")
    private String author;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "sent_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;

    @Column(name = "log_status", nullable = false, length = 255)
    private String logStatus;

    @Column(name = "category", nullable = false, columnDefinition = "TEXT")
    private String category;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    public LogStatus getStatus() {
        return LogStatus.parseByCode(logStatus);
    }

    public void setStatus(String logStatus) {
        this.logStatus = logStatus.toString();
    }
}
