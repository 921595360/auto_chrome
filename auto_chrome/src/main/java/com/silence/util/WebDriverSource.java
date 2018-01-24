

package com.silence.util;

import com.silence.util.PropertiesUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.list.SynchronizedList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverSource {
    private static volatile List<WebDriver> webDrivers = null;

    public WebDriverSource() {
    }

    public static WebDriver get() {
        try {
            while(webDrivers.size() < 1) {
                Thread.sleep(100L);
            }

            WebDriver e = (WebDriver)webDrivers.get(0);
            webDrivers.remove(0);
            return e;
        } catch (InterruptedException var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static void put(WebDriver webDriver) {
        if(!webDrivers.contains(webDriver)) {
            if(webDriver != null) {
                webDrivers.add(webDriver);
            }

        }
    }

    static {
        System.setProperty("webdriver.chrome.driver", PropertiesUtil.get("driver.path", "chromedriver.exe"));
        int count = Integer.valueOf(PropertiesUtil.get("chrome.count", "3")).intValue();
        ArrayList webDrivers = new ArrayList();

        for(int i = 0; i < count; ++i) {
            webDrivers.add(new ChromeDriver());
        }

    }
}
