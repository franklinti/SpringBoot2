package franklinit.com.br.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails  extends  ExceptionDetails{

    private String field;
    private String fieldsMessage;

}
