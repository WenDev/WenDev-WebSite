package site.wendev.website.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 这样配置就可以拦截site.wendev.website.controller包下的所有方法
     */
    @Pointcut("execution(* site.wendev.website.controller.*.*(..))")
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        var attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        var request = attributes.getRequest();

        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        var requestLog = new RequestLog(ip, uri, classMethod, args);

        logger.info("Request: {}", requestLog.toString());
    }

    @After("log()")
    public void doAfter() {
//        logger.info("===== do after =====");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void afterReturn(Object result) {
        logger.info("Result: {}", result);
    }

    private class RequestLog {
        private String ip;
        private String uri;
        private String classMethod;
        private Object[] args;

        public RequestLog(String ip, String uri, String classMethod, Object[] args) {
            this.ip = ip;
            this.uri = uri;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "ip='" + ip + '\'' +
                    ", uri='" + uri + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
