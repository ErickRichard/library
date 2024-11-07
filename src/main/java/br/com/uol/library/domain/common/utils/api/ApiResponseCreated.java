package br.com.uol.library.domain.common.utils.api;

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
        @ApiResponse(responseCode = "201", description = "Resource Created Successfully", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Bad Request - Invalid Parameters", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
})
public @interface ApiResponseCreated {
    String successDescription() default "Resource Created Successfully";
    String errorDescription() default "Bad Request - Invalid Parameters";
}