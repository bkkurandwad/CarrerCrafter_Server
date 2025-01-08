package com.majorproject.CareerforIT.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
public class InterviewRequest {

    @JsonProperty("mockId")
    private String mockId;  // Automatically generates a UUID

    @JsonProperty("jsonMockResp")
    private String jsonMockResp;

    @JsonProperty("jobPosition")
    private String jobPosition;

    @JsonProperty("jobDesc")
    private String jobDesc;

    @JsonProperty("jobExperience")
    private String jobExperience;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("createdAt")
    private String createdAt = new Date().toString();

}
