package site.wendev.website.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL: {}; Exception: {}", request.getRequestURL(), e);
        e.printStackTrace();

        // 如果是指定404、500等状态码的返回页面，就不处理直接把e抛出，交给SpringBoot处理
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
                                                   ConstraintViolationException e,
                                                   RedirectAttributes attributes) {
        var mv = new ModelAndView("redirect:" + request.getRequestURI());
        attributes.addFlashAttribute("errInfo", e.getConstraintViolations().iterator().next().getMessage());

        return mv;
    }
}
