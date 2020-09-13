package com.cdad.project.assignmentservice.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "assignments")
@Data
@ToString
public class Assignment {
  @MongoId(FieldType.OBJECT_ID)
  private String id;
  private String title;
  private String status;
  @Indexed(unique = true)
  private String slug;
  private boolean timed;
  private Integer duration;
  private boolean hasStartTime;
  private LocalDateTime startTime;
  private boolean hasDeadline;
  private LocalDateTime deadline;
  private List<Question> questions;
}
