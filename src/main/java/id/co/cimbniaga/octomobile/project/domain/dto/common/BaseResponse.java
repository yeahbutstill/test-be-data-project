package id.co.cimbniaga.octomobile.project.domain.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {

    private LocalDateTime localDateTime;
    private Integer statusCode;
    private HttpStatus status;
    private String reason;
    private String developerMessage;
    private String message;
    private Object data;

}
