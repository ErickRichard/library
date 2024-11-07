package br.com.uol.library.domain.common.utils;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Default Success Response", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Default Error Response", content = @Content)
})
public @interface ApiResponseWrapper {
    String successDescription() default "Success";
    String errorDescription() default "Error";
    String mediaType() default "application/json";
}