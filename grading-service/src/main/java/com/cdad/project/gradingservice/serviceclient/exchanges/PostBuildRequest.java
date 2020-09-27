package com.cdad.project.gradingservice.serviceclient.exchanges;

import com.cdad.project.gradingservice.dto.TestInput;
import com.cdad.project.gradingservice.entity.Language;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PostBuildRequest {
    private String sourceCode;
    private List<TestInput> inputs;
    private Language language;
}