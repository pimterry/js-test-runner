package org.housered.qunitrunner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App
{
    public static void main(String[] args)
    {
        new ClassPathXmlApplicationContext("applicationcontext.xml").getBean(CommandLineTool.class).run(args);
    }
}
