package com.silence.util;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 */
public class Nirs {

    public static String nirPath = PropertiesUtil.get("nir.path","nircmd.exe");

    /**
     * 触发组合键
     *
     * @param keys
     */
    @Deprecated
    public static void sendMuiltiKeys(String... keys) {
        try {
            //按下所有键
            for (String key : keys) {
                Shells.exec(nirPath + " sendkey " + key + " down");
            }
            //弹起所有键
            for (String key : keys) {
                Shells.exec(nirPath + "  sendkey " + key + " up");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 出发单个或多个按键按下
     */
    public static void sendkeypress(String ... key){
        String tmp=key[0];
        for (int i = 1; i < key.length; i++) {
            tmp+="+"+key[i];
        }
        Shells.exec(nirPath + " sendkeypress " + tmp );
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 触发单个按键
     *
     * @param key
     */
    public static void sendKey(String key) {
        //特殊按键转为虚拟键码
        //key=key.replaceAll(".","0xBE");
        Shells.exec(nirPath + "  sendkey " + key + " press");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送普通值
     *
     * @param value
     */
    public static void sendValue(String value) {
        Shells.exec(nirPath + "  clipboard set \"" + value + "\" ");//复制到剪切板
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendMuiltiKeys("ctrl", "v");
    }


    /**
     * 选择文件
     *
     * @param dir      文件所在路径
     * @param fileName 文件名
     */
    public static void selectFile(String dir, String fileName) {
        try {
            //为了防止出错，多按几次
            Nirs.sendkeypress("ctrl", "f4");
            Nirs.sendkeypress("ctrl", "f4");
            Nirs.sendkeypress("ctrl", "f4");

            Nirs.sendkeypress("ctrl", "a");
            Nirs.sendkeypress("ctrl", "a");
            Nirs.sendkeypress("ctrl", "a");

            Nirs.sendKey("delete");


            Nirs.sendValue(dir);
            Nirs.sendKey("enter");
            Nirs.sendKey("tab");
            Nirs.sendKey("tab");
            Nirs.sendKey("tab");
            Nirs.sendKey("tab");
            Nirs.sendKey("tab");
            Nirs.sendValue(fileName);
            Nirs.sendKey("enter");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择制定文件夹下的所有文件
     * @param dir 目标文件夹
     */
    public static void selectFiles(String dir) {
        try {
            Nirs.sendMuiltiKeys("ctrl", "f4");
            Nirs.sendMuiltiKeys("ctrl", "a");
            Nirs.sendKey("delete");
            Nirs.sendValue(dir);
            Nirs.sendKey("enter");
            Nirs.sendKey("tab");
            Nirs.sendKey("tab");
            Nirs.sendKey("tab");
            Nirs.sendKey("tab");
            Nirs.sendMuiltiKeys("ctrl","a");
            Nirs.sendKey("enter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static Integer getScreenHeight(){
        try {
            InputStream is = Shells.exec("Wmic DesktopMonitor Get ScreenHeight");
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            reader.readLine();
            reader.readLine();
            return Integer.valueOf(reader.readLine().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static Integer getScreenWidth(){
        try {
            InputStream is = Shells.exec("Wmic DesktopMonitor Get ScreenWidth");
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));
            reader.readLine();
            reader.readLine();
            return Integer.valueOf(reader.readLine().trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 移动鼠标
     * @param x 横坐标
     * @param y 纵坐标
     */
    public static void moveMouse(double x,double y){
        Shells.exec(nirPath + "  setcursor "+x+" "+y);//复制到剪切板
    }


    /**
     * 鼠标左键点击
     */
    public static void mouseLeftClick(){
        Shells.exec(nirPath + " sendmouse left click");
    }

    /**
     * 最大化窗口
     */
    public static void maxWindow(){
       sendMuiltiKeys("0x5B","0x26");
    }


    /**
     * 修改分辨率
     * @param x
     * @param y
     */
    public static void setDisplay(Integer x,Integer y){
        Shells.exec(nirPath + "  setdisplay "+x+" "+y+" 32");
    }

    /**
     * 全屏截图
     * @param path 图片存储路径
     */
    public static void savescreenshotfull(String path){
        Shells.exec(nirPath + " savescreenshotfull "+path);
    }

}
