package com.cdad.project.gradingservice.dto;

import com.cdad.project.gradingservice.entity.SubmissionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class SubmissionUserDetailsDTO {
    private UUID id;
    private String email;
    private String assignmentId;
    private SubmissionStatus submissionStatus;
    private Double currentScore;
    private List<QuestionUserDetailsDTO> questionEntities;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startOn;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime completedOn;
    private Double assignmentScore;
}
