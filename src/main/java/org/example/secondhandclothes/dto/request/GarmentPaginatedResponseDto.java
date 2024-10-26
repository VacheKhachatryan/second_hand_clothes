package org.example.secondhandclothes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.secondhandclothes.dto.response.GarmentDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GarmentPaginatedResponseDto {

  @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  private int currentPage;

  @Schema(example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
  private long totalPages;

  @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
  private List<GarmentDto> pages;
}
