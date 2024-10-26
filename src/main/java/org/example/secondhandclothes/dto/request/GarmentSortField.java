package org.example.secondhandclothes.dto.request;

import lombok.Getter;

@Getter
public enum GarmentSortField {
  NAME("name"),
  UPDATED_AT("updatedAt"),
  CREATED_AT("createdAt"),
  PRICE("price");

  private String fieldName;

  GarmentSortField(String fieldName) {
    this.fieldName = fieldName;
  }
}
