package com.silence.util;

import java.io.InputStream;

/**
 * 执行脚本
 */
public class Shells {
    public static InputStream exec(String shell){
        try {
            Process process = Runtime.getRuntime().exec(shell);
            process.getOutputStream().flush();
            Thread.sleep(200);
            return process.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
