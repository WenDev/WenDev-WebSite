package site.wendev.website.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL: {}; Exception: {}", request.getRequestURL(), e);

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        var mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");

        return mv;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView validationExceptionHandler(HttpServletRequest request,
                                                   ConstraintViolationException e) {
        var mv = new ModelAndView();
        mv.addObject("errInfo", e.getConstraintViolations().iterator().next().getMessage());
        mv.setViewName("register");

        return mv;
    }
}
