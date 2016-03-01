package com.gs.pp.framework.aspect;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);


    public void before(JoinPoint point) {
        logger.info("**********调用开始：{}.{}()！", point.getTarget().getClass().getName(), point.getSignature().getName());
    }

    public Object around(ProceedingJoinPoint point) throws Throwable {
        StopWatch watch = new StopWatch();
        watch.start();//计时开始
        Object retVal = point.proceed();
        watch.stop();//计时结束
        logger.info("**********执行{}.{}()共耗时：{}毫秒！", point.getTarget().getClass().getName(), point.getSignature().getName(), watch.getTime());
        return retVal;
    }


    @SuppressWarnings("all")
    public Object afterReturning(JoinPoint point, Object retValue) throws Exception {
       
        return null;
    }


    public void after(JoinPoint point) {

        logger.info("**********调用结束：{}.{}()！", point.getTarget().getClass().getName(), point.getSignature().getName());
    }

    
    public void doThrowing(Throwable ex) {
    	logger.info("运行时候抛出的异常->{}", ex.getMessage());
    }

}
