package com.example.security.dtos;


import com.example.security.constant.ResponseMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO <T>{
    private Integer status;
    private ResponseMessageEnum message;
    private T result;
}
