package com.xhh.demo.prometheus.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 切面
 *
 * @author 扶苏
 * @version 1.0.0 createTime: 2017/10/30 上午9:55
 */
@Log4j2
@Component
public class MonitorAspect {

    @Pointcut("execution(* com.xhh.demo.prometheus.controller.MainController.*(..))")
    private void anyMethod(){}

    @Before("anyMethod() && args(name)")
    public void doAccessCheck(String name){
        log.debug(name);
        log.debug("Before 前置通知");
    }

    @AfterReturning("anyMethod()")
    public void doAfter(){
        log.debug("AfterReturning 后置通知");
    }

    @After("anyMethod()")
    public void after(){
        log.debug("After 最终通知");
    }

    @AfterThrowing("anyMethod()")
    public void doAfterThrow(){
        log.debug("AfterThrowing 例外通知");
    }

    @Around("anyMethod()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{
        log.debug("Around 进入环绕通知");
        Object object = pjp.proceed();//执行该方法
        log.debug("Around 退出方法");
        return object;
    }
}
