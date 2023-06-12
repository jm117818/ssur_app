package pl.urz.ssur_app.controller;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import pl.urz.ssur_app.dto.response.ErrorResponse;

import java.sql.SQLException;


@Slf4j
@ControllerAdvice
public class ExceptionHandlingControllerAdvice {
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST) //400
  @ExceptionHandler({HttpMessageNotReadableException.class, IllegalArgumentException.class})
  public ErrorResponse badRequest(HttpServletRequest req, Exception ex) {
    ex.printStackTrace();
    return ErrorResponse.builder()
      .code(HttpStatus.BAD_REQUEST.value())
      .message(ex.getMessage())
      .build();
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED) //401
  @ExceptionHandler({BadCredentialsException.class, SignatureException.class, MalformedJwtException.class})
  public ErrorResponse unauthorized(HttpServletRequest req, Exception ex) {
    return ErrorResponse.builder()
      .code(HttpStatus.UNAUTHORIZED.value())
      .message(ex.getMessage())
      .build();
  }


  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND) //404
  @ExceptionHandler({NoHandlerFoundException.class})
  public ErrorResponse notFound(HttpServletRequest req, Exception ex) {
    return ErrorResponse.builder()
      .code(HttpStatus.NOT_FOUND.value())
      .message(ex.getMessage())
      .build();
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND) //404
  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ErrorResponse methodArgumentTypeMismatchException(HttpServletRequest req, Exception ex) {
    return ErrorResponse.builder()
      .code(HttpStatus.NOT_FOUND.value())
      .message("URL params data type mismatch")
      .build();
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) //405
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ErrorResponse methodNotAllowed(HttpServletRequest req, Exception ex) {
    return ErrorResponse.builder()
      .code(HttpStatus.METHOD_NOT_ALLOWED.value())
      .message(ex.getMessage())
      .build();
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE) //415
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ErrorResponse unsupportedMediaType(HttpServletRequest req, Exception ex) {
    return ErrorResponse.builder()
      .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
      .message(ex.getMessage())
      .build();
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500
  @ExceptionHandler({Exception.class})
  public ErrorResponse internalServerError(HttpServletRequest req, Exception ex) {
    ex.printStackTrace();
    return ErrorResponse.builder()
      .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
      .message(ex.getMessage())
      .build();
  }


}
