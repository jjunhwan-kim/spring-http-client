package com.example.httpclient.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestDto {
    private String url;
    private String content;
}
