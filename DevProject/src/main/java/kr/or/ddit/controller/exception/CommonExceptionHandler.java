package kr.or.ddit.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String handle(Exception e, Model model) {
		log.info("e : " + e.toString());
		model.addAttribute("exception", e);
		return "error/errorCommon";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public String handle404(Exception e) {
		log.info("e : " + e.toString());
		return "error/custom404";
	}
}
