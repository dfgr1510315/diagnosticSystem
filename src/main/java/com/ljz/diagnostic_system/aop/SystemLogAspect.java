package com.ljz.diagnostic_system.aop;

import com.ljz.diagnostic_system.common.Annotation.SystemControllerLog;
import com.ljz.diagnostic_system.common.Utils.DateUtils;
import com.ljz.diagnostic_system.model.Log;
import com.ljz.diagnostic_system.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Aspect
@Component
public class SystemLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);
    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<>("ThreadLocal beginTime");
    private static final ThreadLocal<Log> logThreadLocal = new NamedThreadLocal<>("ThreadLocal log");
    //private static final ThreadLocal<String> currentUser = new NamedThreadLocal<>("ThreadLocal user");

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private LogService logService;

    /**
     * Controller 层切入点 注解拦截
     */
    @Pointcut("@annotation(com.ljz.diagnostic_system.common.Annotation.SystemControllerLog)")
    public void controllerAspect(){}

    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);
        if (logger.isDebugEnabled()){ //这里日志级别为debug
            logger.debug("开始计时：{} URI：{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(beginTime
            ),request.getRequestURI());
        }
        //读取拦截器解析出的用户id
        //currentUser.set((String) request.getAttribute("id"));
    }

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint){
        //String userId = currentUser.get();
        String userId = (String)request.getAttribute("id");
        System.out.println(userId);
        if (userId!=null){
            String title = "";
            String type = "info"; //日志类型(info:入库,error:错误)
            String remoteAddr = request.getRemoteAddr();//请求的IP
            String requestUri = request.getRequestURI();//请求的Uri
            String method = request.getMethod();//请求的方法类型(post/get)
            Map<String,String[]> params = request.getParameterMap();//请求提交的参数
            try{
                title = getControllerMethodDescription2(joinPoint);
            }catch (Exception e){
                e.printStackTrace();
            }
            //打印JVM信息
            long beginTime = beginTimeThreadLocal.get().getTime();//得到线程绑定的局部变量（开始时间）
            long endTime = System.currentTimeMillis();//结束时间
            if (logger.isDebugEnabled()){
                logger.debug("计时结束：{}  URI: {}  耗时： {}   最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(endTime),
                        request.getRequestURI(),
                        DateUtils.formatDateTime(endTime - beginTime),
                        Runtime.getRuntime().maxMemory()/1024/1024,
                        Runtime.getRuntime().totalMemory()/1024/1024,
                        Runtime.getRuntime().freeMemory()/1024/1024,
                        (Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024);
            }
            Log log = new Log();
            log.setTitle(title);
            log.setType(type);
            log.setRemoteAddr(remoteAddr);
            log.setRequestUri(requestUri);
            log.setMethod(method);
            log.setMapToParams(params);
            log.setUserId(userId);
            Date operateDate=beginTimeThreadLocal.get();
            log.setOperateDate(operateDate);
            log.setTimeout(DateUtils.formatDateTime(endTime - beginTime));

            //1.直接执行保存操作
            //this.logService.createSystemLog(log);

            //2.优化:异步保存日志
            //new SaveLogThread(log, logService).start();

            //3.再优化:通过线程池来执行日志保存
            threadPoolTaskExecutor.execute(new SaveLogThread(log,logService));
            logThreadLocal.set(log);
        }
    }

    /**
     *  异常通知 记录操作报错日志
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
        Log log = logThreadLocal.get();
        if (log==null) return;
        log.setType("error");
        log.setException(e.toString());
        new UpdateLogThread(log,logService).start();
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return discription
     */
     private static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemControllerLog controllerLog = method
                .getAnnotation(SystemControllerLog.class);
        return controllerLog.description();
    }

    /**
     * 保存日志线程
     */
    private static class SaveLogThread implements Runnable{
        private Log log;
        private LogService logService;

        public SaveLogThread(Log log, LogService logService) {
            this.log = log;
            this.logService = logService;
        }

        @Override
        public void run() {
            logService.createLog(log);
        }
    }

    /**
     * 日志更新线程
     */
    private static class UpdateLogThread extends Thread{
        private Log log;
        private LogService logService;

        public UpdateLogThread(Log log, LogService logService) {
            super(UpdateLogThread.class.getSimpleName());
            this.log = log;
            this.logService = logService;
        }

        @Override
        public void run() {
            this.logService.updateLog(log);
        }
    }
}
