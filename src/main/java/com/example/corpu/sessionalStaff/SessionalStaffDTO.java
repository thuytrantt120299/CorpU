package com.example.corpu.sessionalStaff;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionalStaffDTO implements Serializable {
    private static final long serialVersionUID = 1;

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Integer unitCount;
    private Integer ranking;
    private String workingDays;
    @JsonProperty("availability")
    private List<String> availability;
    @JsonProperty("preference")
    private List<String> preference;
    @JsonProperty("qualification")
    private List<String> qualification;
}
