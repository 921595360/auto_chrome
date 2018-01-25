//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.service;

import com.silence.entity.Count;
import com.silence.repository.CountRepository;
import com.silence.util.Chromes;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountService {
    @Autowired
    private CountRepository countRepository;

    public CountService() {
    }

    public List<Count> list() {
        return this.countRepository.findAll();
    }

    /**
     * 登录到指定账户
     * @param countName
     */
    public WebDriver login(String countName) {
        Count count = countRepository.findOne(countName);

        //打开浏览器登录
        WebDriver wb= Chromes.open();

        try {
            wb.get("http://vip.anjuke.com/");
            wb.findElement(By.id("loginName")).sendKeys(countName);
            wb.findElement(By.id("loginPwd")).sendKeys(count.getCountPass());
            wb.findElement(By.id("loginSubmit")).click();
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void del(String countName) {
        this.countRepository.delete(countName);
    }

    public void add(Count count) {
        this.countRepository.save(count);
    }

    /**
     * 推送赶集
     * @param countName
     */
    public void appplayGanji(String countName) {
        try {
            WebDriver wb = login(countName);
            Thread.sleep(3000);
            wb.get("http://vip.anjuke.com/zfgj/tgW0QQpZ5");
            wb.findElement(By.id("ckAll")).click();
            Thread.sleep(3000);
            wb.findElement(By.className("batch-btn")).click();
            Thread.sleep(1000);
            //Chromes.findElement("a", "确定", wb).click();
            wb.findElement(By.id("alert")).findElements(By.tagName("div")).get(1).findElements(By.tagName("a")).get(0).click();

            wb.get("http://vip.anjuke.com/zfgj/jpf");

            wb.findElement(By.id("ckAll")).click();//全选
            wb.findElement(By.id("setpro")).click();//【批量推广、
            wb.findElement(By.id("alert")).findElements(By.tagName("div")).get(1).findElements(By.tagName("a")).get(0).click();//确定
            Thread.sleep(2000);
            wb.get("http://vip.anjuke.com/zfgj/jpfW0QQpZ2");
            wb.findElement(By.id("ckAll")).click();//全选
            wb.findElement(By.id("setpro")).click();//【批量推广、
            wb.findElement(By.id("alert")).findElements(By.tagName("div")).get(1).findElements(By.tagName("a")).get(0).click();//确定
            Thread.sleep(2000);
            wb.get("http://vip.anjuke.com/zfgj/jpfW0QQpZ3");
            wb.findElement(By.id("ckAll")).click();//全选
            wb.findElement(By.id("setpro")).click();//【批量推广、
            wb.findElement(By.id("alert")).findElements(By.tagName("div")).get(1).findElements(By.tagName("a")).get(0).click();//确定
            Thread.sleep(2000);
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 58热租推送
     * @param countName
     */
    public void appplayRezu(String countName) {
        try {
            WebDriver wb = login(countName);
            //取消推送
            Thread.sleep(3000);
            wb.get("http://vip.58ganji.com/zf58/cxfy58");
            wb.findElement(By.id("ckAll")).click();
            Thread.sleep(3000);
            wb.findElement(By.id("cancelpro")).click();
            Thread.sleep(1000);
            
          //最多推送5个热租
			for (int j = 0; j < 5; j++) {
				//进入58库存
				wb.get("http://vip.58ganji.com/zf58/kcfy58");
				//获取当前页所有房源
				List<WebElement> trs = wb.findElement(By.id("houselist")).findElements(By.tagName("tr"));
				//第一个tr是广告
				for (int i = 1; i < trs.size(); i++) {
					//找到推送按钮
					WebElement push = trs.get(i).findElements(By.tagName("td")).get(5).findElements(By.tagName("a")).get(1);
					
					if(push.getAttribute("class").indexOf("grey")==-1) {
						//点击推送
						push.click();
						Thread.sleep(1000);
						Chromes.findElement("a", "确定", wb).click();
						Thread.sleep(1000);
						break;
					}
					
				}
			}
			wb.close();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
