

package com.silence.util;

import com.silence.entity.HouseInfo;
import com.silence.entity.HouseInfo.Type;
import com.silence.util.Chromes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.sf.jxls.transformer.XLSTransformer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Qingke {
    public Qingke() {
    }

    public static void main(String[] args) throws Exception {
        WebDriver wb = Chromes.open();
        int[] areas = new int[]{15};
        short oneCount = 2000;
        byte pages = 10;
        ArrayList houseInfos = new ArrayList();

        for(int transformer = 0; transformer < areas.length; ++transformer) {
            String map = "http://www.qk365.com/list/a" + areas[transformer];

            for(int j = 1; j <= pages; ++j) {
                List urls = getUrls(wb, j == 1?map:map + "-p" + j, oneCount);
                Iterator var10 = urls.iterator();

                while(var10.hasNext()) {
                    String tmpUrl = (String)var10.next();
                    HouseInfo houseInfo = getHouseInfo(wb, tmpUrl);
                    houseInfos.add(houseInfo);
                }
            }
        }

        XLSTransformer var13 = new XLSTransformer();
        HashMap var14 = new HashMap();
        var14.put("houseInfos", houseInfos);
        var13.transformXLS("D:\\repo\\1708\\auto_chrome\\data\\houseInfo.xls", var14, "D:\\repo\\1708\\auto_chrome\\data\\test1.xls");
    }

    private static HouseInfo getHouseInfo(WebDriver wb, String url) {
        Random random = new Random();
        wb.get(url);
        HouseInfo houseInfo = new HouseInfo();
        List lefts = wb.findElement(By.className("survey-left")).findElements(By.tagName("dd"));
        String xiaoqu = ((WebElement)lefts.get(4)).findElement(By.tagName("a")).getText();
        String louceng = ((WebElement)lefts.get(3)).getText();
        louceng = louceng.substring(louceng.indexOf("：") + 1, louceng.length() - 1);
        louceng = louceng.replaceAll(" ", "").replaceAll("\n", "");
        String[] loucengs = louceng.split("/");
        String biaoti = wb.findElement(By.className("houInfoTit")).getText();
        String chaoxiang = ((WebElement)lefts.get(2)).findElement(By.tagName("em")).getText().replaceAll("朝", "");
        Integer zujin = Integer.valueOf(wb.findElement(By.className("price_txt")).findElement(By.tagName("h4")).getText());
        houseInfo.setXiaoqu(xiaoqu);
        houseInfo.setBiaoti(biaoti);
        houseInfo.setLou(Integer.valueOf(loucengs[0]));
        houseInfo.setCeng(Integer.valueOf(loucengs[1]));
        houseInfo.setChaoxiang(chaoxiang);
        if(houseInfo.getCeng().intValue() > 6) {
            houseInfo.setDianti(Integer.valueOf(1));
        } else {
            houseInfo.setDianti(Integer.valueOf(0));
        }

        houseInfo.setZujin(zujin);
        houseInfo.setType(Type.HEZU);
        houseInfo.setHushu(Integer.valueOf(3));
        houseInfo.setImgCount(Integer.valueOf(6));
        houseInfo.setImgUrl("D:\\repo\\1708\\auto_chrome\\houseImg\\" + (random.nextInt(54) + 1));
        houseInfo.setMianji(Integer.valueOf(18));
        houseInfo.setPeizhi("冰箱,洗衣机,热水器,空调,宽带,床,衣柜,可做饭,独立阳台,独立卫生间");
        houseInfo.setShi(Integer.valueOf(3));
        houseInfo.setTing(Integer.valueOf(1));
        houseInfo.setWei(Integer.valueOf(1));
        houseInfo.setZhuciwo(Integer.valueOf(1));
        houseInfo.setTemplate("通用模板");
        return houseInfo;
    }

    private static List<String> getUrls(WebDriver wb, String url, int count) {
        wb.get(url);
        if(wb.findElements(By.className("easyList")).size() < 1) {
            return new ArrayList();
        } else {
            List houses = wb.findElement(By.className("easyList")).findElements(By.tagName("li"));
            ArrayList housesUrl = new ArrayList();
            if(houses != null) {
                int tmp = count;
                if(houses.size() < count) {
                    tmp = houses.size();
                }

                for(int k = 0; k < tmp; ++k) {
                    housesUrl.add(((WebElement)houses.get(k)).findElement(By.tagName("a")).getAttribute("href"));
                }
            }

            return housesUrl;
        }
    }
}
