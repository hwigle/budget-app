package com.hwigle.budge.adapter.in.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST) // 👈 400 에러 코드를 붙여줌!
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException e) {
        return "입력 오류 발생 : " + e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    public String handleAll(Exception e) {
        return "🔥 서버에서 알 수 없는 오류가 발생했습니다: " + e.getMessage();
    }
}
