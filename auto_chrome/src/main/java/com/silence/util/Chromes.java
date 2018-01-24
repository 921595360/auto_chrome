//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.util;

import com.silence.util.PropertiesUtil;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Chromes {
    public Chromes() {
    }

    public static WebDriver open() {
        return new ChromeDriver();
    }

    public static WebElement findElement(String tagName, String text, WebDriver wb) {
        List elementLists = null;
        elementLists = wb.findElements(By.tagName(tagName));
        Iterator var4 = elementLists.iterator();

        WebElement element;
        do {
            if(!var4.hasNext()) {
                return null;
            }

            element = (WebElement)var4.next();
        } while(!element.isDisplayed() || element.getText().indexOf(text) <= -1);

        return element;
    }

    public static void execScript(WebDriver wb, String script) {
        ((JavascriptExecutor)wb).executeScript(script, new Object[0]);
    }

    static {
        System.setProperty("webdriver.chrome.driver", PropertiesUtil.get("driver.path", "chromedriver.exe"));
    }
}
