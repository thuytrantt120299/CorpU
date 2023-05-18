package com.example.corpu.sessionalStaff;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SessionalStaffDTO {
    private String id;
    private String email;
//    private String hourlyRate;
    @JsonProperty("availability")
    private List<String> availability;
}
