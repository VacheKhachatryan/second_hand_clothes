package org.example.secondhandclothes.error;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiError {

  @Schema(
      description = "The error unique identifier.",
      example = "4001",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer errorCode;

  @Schema(
      description = "The error message",
      example = "There is an invalid value in the user input.",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String message;

  @JsonInclude(NON_NULL)
  @Schema(
      description = "The error descriptions referring to request body attributes.",
      example = "{\"firstname\": [\"must not be null\"]}")
  private Map<String, List<String>> errors;
}
