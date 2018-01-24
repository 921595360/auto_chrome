package com.silence.core.init;


import com.silence.util.Chromes;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 系统初始化
 * Init
 * 实现ApplicationListener接口主要是为了spring启动后获取bean执行相关操作
 * silence
 * silence
 * 2016年1月4日 下午1:01:05
 * 
 * @version 1.0.0
 */

public class Init implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }


}
