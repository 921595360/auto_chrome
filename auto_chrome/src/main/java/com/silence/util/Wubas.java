//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.util;

import com.silence.exception.TipException;
import com.silence.util.Chromes;
import com.silence.util.Objs;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;

public class Wubas {
    public static Map<String, Object> valiCodes = new HashMap();

    public Wubas() {
    }

    public static void init(String countName, String countPass) {
        WebDriver wb = Chromes.open();

        try {
            valiCodes.remove(countName);
            wb.get("https://passport.58.com/login");
            wb.findElement(By.id("pwdLogin")).click();
            wb.findElement(By.id("isremember_id_new")).click();
            wb.findElement(By.id("usernameUser")).sendKeys(new CharSequence[]{countName});
            wb.findElement(By.id("passwordUserText")).click();
            Thread.sleep(1000L);
            wb.findElement(By.id("passwordUser")).sendKeys(new CharSequence[]{countPass});
            wb.findElement(By.id("btnSubmitUser")).click();
            Thread.sleep(10000L);
            wb.findElement(By.id("pptmobilecoderesendbtn")).click();
            Thread.sleep(10000L);
            String e = null;

            for(int cookies = 0; cookies < 5; ++cookies) {
                if(valiCodes.get(countName) != null) {
                    e = valiCodes.get(countName).toString();
                    break;
                }

                Thread.sleep(100000L);
            }

            if(e == null) {
                throw new TipException("验证码输入超时，账户：" + countName + "初始化失败");
            }

            wb.findElement(By.id("pptmobilecode")).sendKeys(new CharSequence[]{e});
            wb.findElement(By.id("submitButton")).click();
            Object[] var11 = wb.manage().getCookies().toArray();
            File data = new File("data");
            if(!data.exists()) {
                data.mkdir();
            }

            Objs.serialize(var11, data.getAbsolutePath() + "/" + countName);
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            wb.close();
        }

    }

    public static void applyHezu(WebDriver wb, String xiaoqu, Integer shi, Integer ting, Integer wei, Integer lou, Integer ceng, Integer mianji, Integer zujin, String title) {
        wb.get("http://post.58.com/fang/2/10/s5?PGTID=0d500000-0000-2d96-305a-c5139e581d20&ClickID=2");
        wb.findElement(By.id("xiaoqu")).sendKeys(new CharSequence[]{xiaoqu});
        wb.findElement(By.id("jushishuru")).sendKeys(new CharSequence[]{shi.toString()});
        wb.findElement(By.id("huxingting")).sendKeys(new CharSequence[]{ting.toString()});
        wb.findElement(By.id("huxingwei")).sendKeys(new CharSequence[]{wei.toString()});
        wb.findElement(By.id("Floor")).sendKeys(new CharSequence[]{lou.toString()});
        wb.findElement(By.id("zonglouceng")).sendKeys(new CharSequence[]{ceng.toString()});
        wb.findElement(By.id("area")).sendKeys(new CharSequence[]{mianji.toString()});
        ((JavascriptExecutor)wb).executeScript("$(\'[name=\"xianzhi2\"] li\')[1].click()", new Object[0]);
        ((JavascriptExecutor)wb).executeScript("$(\'[name=\"Toward\"] li\')[1].click()", new Object[0]);
        ((JavascriptExecutor)wb).executeScript("$(\'[name=\"FitType\"] li\')[4].click()", new Object[0]);
        wb.findElement(By.id("MinPrice")).sendKeys(new CharSequence[]{zujin.toString()});
        ((JavascriptExecutor)wb).executeScript(" $(\'[name=\"fukuanfangshi\"] li\')[1].click()", new Object[0]);
        wb.findElement(By.id("Title")).sendKeys(new CharSequence[]{title});
        wb.findElement(By.id("ueditor_0")).click();
        wb.switchTo().activeElement().click();
        wb.switchTo().activeElement().sendKeys(new CharSequence[]{"你好\r\n你好"});
        WebElement upload = wb.findElement(By.className("html5")).findElement(By.tagName("input"));
        LocalFileDetector detector = new LocalFileDetector();
        String path = "D:\\tmp\\admin\\picture\\1.jpg";
        File f = detector.getLocalFile(new CharSequence[]{path});
        ((RemoteWebElement)upload).setFileDetector(detector);
        upload.sendKeys(new CharSequence[]{f.getAbsolutePath()});
    }

    public static WebDriver login(String countName, String countPass) {
        try {
            if(!(new File("data/" + countName)).exists()) {
                throw new TipException("请先初始化账户");
            } else {
                WebDriver e = Chromes.open();
                e.get("https://passport.58.com/login");
                Object[] cookies = (Object[])((Object[])Objs.deserialize("data/" + countName));

                for(int i = 0; i < cookies.length; ++i) {
                    e.manage().addCookie((Cookie)cookies[i]);
                }

                e.findElement(By.id("pwdLogin")).click();
                Thread.sleep(1000L);
                e.findElement(By.id("usernameUser")).sendKeys(new CharSequence[]{"17723507462.xls"});
                e.findElement(By.id("passwordUserText")).click();
                Thread.sleep(1000L);
                e.findElement(By.id("passwordUser")).sendKeys(new CharSequence[]{"19940808ab"});
                e.findElement(By.id("btnSubmitUser")).click();
                Thread.sleep(3000L);
                e.get("http://my.58.com/index");
                return e;
            }
        } catch (InterruptedException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        WebDriver wb = login("17723507462.xls", "19940808ab");
        applyHezu(wb, "其灵公寓", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(30), Integer.valueOf(1800), "精装修电梯房");
    }
}
