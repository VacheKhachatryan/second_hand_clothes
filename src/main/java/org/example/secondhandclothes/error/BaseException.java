
package org.example.secondhandclothes.error;

import lombok.Getter;
import org.slf4j.event.Level;

@Getter
public class BaseException extends RuntimeException {

  protected final ApplicationError error;
  protected final Level level;

  public BaseException(ApplicationError error, String message, Level level) {
    super(message);
    this.error = error;
    this.level = level;
  }

  public BaseException(ApplicationError error, String message) {
    super(message);
    this.error = error;
    this.level = Level.INFO;
  }
}