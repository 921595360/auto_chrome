//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.util;

import com.silence.util.Chromes;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Baidu {
    public Baidu() {
    }

    public static void search(WebDriver wb) {
        try {
            wb.get("https://www.baidu.com/");
            wb.findElement(By.id("kw")).sendKeys(new CharSequence[]{"老谭"});
            wb.findElement(By.id("su")).click();
            Thread.sleep(1000L);
            boolean e = false;
            int i = 0;

            label34:
            while(i < 3) {
                List results = wb.findElements(By.className("result"));
                Iterator var4 = results.iterator();

                while(true) {
                    if(var4.hasNext()) {
                        WebElement result = (WebElement)var4.next();
                        System.out.println(result.getText());
                        if(!result.isDisplayed() || result.getText().indexOf("www.laotan.site") <= -1) {
                            continue;
                        }

                        result.findElement(By.tagName("h3")).findElement(By.tagName("a")).click();
                        e = true;
                        Thread.sleep(2000L);
                        wb.switchTo().window(wb.getWindowHandles().toArray()[1].toString()).close();
                    }

                    if(e) {
                        break label34;
                    }

                    Chromes.findElement("a", "下一页", wb).click();
                    Thread.sleep(3000L);
                    ++i;
                    break;
                }
            }

            wb = wb.switchTo().window(wb.getWindowHandles().toArray()[0].toString());
            search(wb);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static void main(String[] args) {
        search(Chromes.open());
    }
}
