package com.example.corpu.application;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ApplicationRequest {
    private String id;
    private String sessionalStaffId;
    private String unitId;
    private Instant applicationDate;
    private Integer status;
}
