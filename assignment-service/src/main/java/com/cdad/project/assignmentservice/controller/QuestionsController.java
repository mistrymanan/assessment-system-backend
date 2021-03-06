package com.cdad.project.assignmentservice.controller;

import com.cdad.project.assignmentservice.dto.ErrorResponse;
import com.cdad.project.assignmentservice.dto.QuestionDTO;
import com.cdad.project.assignmentservice.entity.CurrentUser;
import com.cdad.project.assignmentservice.exceptions.AssignmentNotFoundException;
import com.cdad.project.assignmentservice.exceptions.QuestionNotFoundException;
import com.cdad.project.assignmentservice.exchanges.*;
import com.cdad.project.assignmentservice.service.AssignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionsController {

  private final AssignmentService assignmentService;

  public QuestionsController(AssignmentService assignmentService) {
    this.assignmentService = assignmentService;
  }

  @PostMapping("/add-question")
  @ResponseStatus(HttpStatus.CREATED)
  public void addQuestionToAssignment(@RequestBody AddQuestionRequest request,
                                      @AuthenticationPrincipal Jwt jwt) throws AssignmentNotFoundException {
    CurrentUser user = CurrentUser.fromJwt(jwt);
    this.assignmentService.addQuestionToAssignment(request, user);
  }

  @GetMapping
  public QuestionDTO getQuestionForAssignment(GetQuestionUsingSlugRequest request,
                                              @AuthenticationPrincipal Jwt jwt) throws AssignmentNotFoundException, QuestionNotFoundException {
    return this.assignmentService.getQuestion(
            request.getAssignmentSlug(),
            request.getQuestionSlug(),
            CurrentUser.fromJwt(jwt)
    );
  }

  @GetMapping("/id")
  public QuestionDTO getQuestionForAssignmentById(GetQuestionUsingIdRequest request,
                                                  @AuthenticationPrincipal Jwt jwt) throws AssignmentNotFoundException, QuestionNotFoundException {
    return this.assignmentService.getQuestionUsingId(
            request.getAssignmentId(),
            request.getQuestionId(),
            CurrentUser.fromJwt(jwt)
    );
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteQuestionForAssignment(DeleteQuestionRequest request,
                                          @AuthenticationPrincipal Jwt jwt) throws AssignmentNotFoundException, QuestionNotFoundException {
    this.assignmentService.deleteQuestionForAssignment(
            request.getAssignmentId(),
            request.getQuestionId(),
            CurrentUser.fromJwt(jwt)
    );
  }

  @PutMapping("/update-question")
  @ResponseStatus(HttpStatus.OK)
  public void updateQuestionForAssignment(@RequestBody UpdateAssignmentQuestionRequest updateRequest,
                                          @AuthenticationPrincipal Jwt jwt) throws AssignmentNotFoundException, QuestionNotFoundException {
    this.assignmentService.updateQuestionForAssignment(
            updateRequest.getAssignmentId(),
            updateRequest.getQuestion(),
            CurrentUser.fromJwt(jwt)
    );
  }

  @ExceptionHandler({AssignmentNotFoundException.class, QuestionNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handle(Exception e) {
    return new ErrorResponse("Not Found", e.getMessage());
  }
}
