package com.minsait.challenge.twitter.domain;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@Schema(description = "Base response")
public abstract class BaseResponseDto implements Serializable {

    @Schema(description = "Response code. Usually the same as HTTP code")
    private Integer code;

    @Schema(description = "Message description. Usually the description for the HTTP code")
    private String message;

    @Hidden
    public void setInfo(HttpStatus httpStatus) {
        this.setCode(httpStatus.value());
        this.setMessage(httpStatus.getReasonPhrase());
    }

}
