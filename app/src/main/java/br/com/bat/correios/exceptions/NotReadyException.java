package br.com.bat.correios.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Serviço esta em instalação. Favor aguarde de 3 a 5 minutos")
public class NotReadyException extends Exception{

}
