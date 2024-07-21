package dgu.camputhon.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    private boolean isSuccess;
    private String code;
    private String message;
    private Object result;

    public ResponseDTO(boolean isSuccess, String code, String message, Object result) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }
}
