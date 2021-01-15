package com.lrm.handler;

import org.junit.platform.commons.util.AnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest httpServletRequest, Exception exception) throws Exception {
        logger.error("Request URL : {},Exception : {}", httpServletRequest.getRequestURL(), exception);
        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
            throw exception;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", httpServletRequest.getRequestURL());
        mv.addObject("exception", exception);
        mv.setViewName("error/error");
        return mv;
    }

}
