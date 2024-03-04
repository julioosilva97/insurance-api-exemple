package com.julioosilva97.insurancesapi.api.exceptionhandler;
import java.util.ArrayList;


import com.julioosilva97.insurancesapi.infra.exception.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = new ArrayList<ApiError.Error>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            errors.add(new ApiError.Error(name, message));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {

            errors.add(new ApiError.Error(error.getObjectName(), error.getDefaultMessage()));
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "One or more fields are invalid", errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
            String error = ex.getParameterName() + "parâmetro está faltando";

            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // erro no parametro da requisição
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        var errors = new ArrayList<ApiError.Error>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {

            String name = violation.getRootBeanClass().getName();
            String message = violation.getPropertyPath() + ": " + violation.getMessage();

            errors.add(new ApiError.Error(name, message));

        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // parametro com o tipo errado
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                "Parametro " + ex.getName() + " inválido,valor passado : " + ex.getValue()
                        + " , esperado um valor do tipo : " + ex.getRequiredType().getName());
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());

    }

}