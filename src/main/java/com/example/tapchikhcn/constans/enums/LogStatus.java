package com.example.tapchikhcn.constans.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum LogStatus {
    DANG_XU_LY("Đang xử lý"),
    CHUA_XU_LY("Chưa xử lý"),
    DA_XU_LY("Đã xử lý"),
    DA_CHAP_NHAN_DANG("Đã chấp nhận đăng");

    private String name;

    @JsonCreator
    public static LogStatus parseByCode(@JsonProperty("name") String name) {
        if (name == null) {
            return null;
        }
        for (LogStatus status : values()) {
            if (Objects.equals(status.name, name)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
