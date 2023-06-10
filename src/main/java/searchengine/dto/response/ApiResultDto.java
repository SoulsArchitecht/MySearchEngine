package searchengine.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
import searchengine.dto.SearchingDto;

import java.util.List;

@Data
public class ApiResultDto {

    private boolean result;
    private String errorMessage;
    private HttpStatus status;
    private List<SearchingDto> data;

    public ApiResultDto(boolean result) {
        this.result = result;
    }

    public ApiResultDto(boolean result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public ApiResultDto(boolean result, String errorMessage, HttpStatus status) {
        this.result = result;
        this.errorMessage = errorMessage;
        this.status = status;
    }


}
