package com.example.corpu.application;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

@Data
@Builder
public class ApplicationDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String applicationName;
    private String email;
    private String phone;
    private List<String> availability;
    private List<String> preferences;
    private String unitName;



}
