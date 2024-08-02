package com.example.tapchikhcn.error;



import com.example.tapchikhcn.utils.EbsI18n;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataError<T> {
    private int code;
    private String message;
    private T data;

    public DataError() {
    }

    public static <T> DataError<T> build(ErrorStatus status){
        DataError<T> response = new DataError<>();
        response.code = status.getCode();
        response.message = EbsI18n.get(status.getMessage());
        return response;
    }

    public static <T> DataError<T> build(){
        return build(CommonStatus.SUCCESS);
    }

    public static <T> DataError<T> build(int code, String message) {
        DataError<T> response = new DataError<>();
        response.code = code;
        response.message = EbsI18n.get(message);
        return response;
    }

    public static <T> DataError<T> build(T data, ErrorStatus status) {
        DataError<T> response = build(status);
        response.data = data;
        return response;
    }

    public static <T> DataError<T> build(T data) {
        DataError<T> response = build();
        response.data = data;
        return response;
    }
}
