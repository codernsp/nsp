package com.nsp.messageProcessor.processEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *  Main class calls MessageProcessorApplication
 *
 */

@Component
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        MessageProcessorApplication app = (MessageProcessorApplication) ctx.getBean("messageProcessorApp");;
    }
}
