package org.example.secondhandclothes.util;

import static lombok.AccessLevel.PRIVATE;

import java.util.function.Supplier;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class BooleanUtils {

  public static void throwIfTrue(boolean condition, Supplier<? extends RuntimeException> ex) {
    if (condition) {
      throw ex.get();
    }
  }

  public static void throwIfFalse(boolean condition, Supplier<? extends RuntimeException> ex) {
    if (!condition) {
      throw ex.get();
    }
  }
}