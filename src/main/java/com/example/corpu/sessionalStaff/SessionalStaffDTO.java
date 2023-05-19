package com.example.corpu.sessionalStaff;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionalStaffDTO {
    private String id;
    private String email;
    private Integer units;
    private String rate;
    private String workingDays;
    @JsonProperty("availability")
    private List<String> availability;
    @JsonProperty("preference")
    private List<String> preference;
}
