//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.service;

import com.silence.common.DateUtils;
import com.silence.entity.Count;
import com.silence.entity.HouseInfo;
import com.silence.entity.Task;
import com.silence.entity.Task.Status;
import com.silence.repository.CountRepository;
import com.silence.repository.HouseInfoRepository;
import com.silence.repository.TaskRepository;
import com.silence.util.Chromes;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private Log logger = LogFactory.getLog(TaskService.class);
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private CountRepository countRepository;
    @Autowired
    private HouseInfoRepository houseInfoRepository;

    public TaskService() {
    }

    public List<Task> list(String countName, Integer status) {
        Sort sort = new Sort(new String[]{"createTime", "countName"});
        return countName == null && status == null?this.taskRepository.findAll(sort):(countName != null && status == null?this.taskRepository.findByCountName(countName, sort):(countName != null && status != null?this.taskRepository.findByCountNameAndStatus(countName, status, sort):this.taskRepository.findByStatus(status, sort)));
    }

    public void add(String houseInfoId) {
        HouseInfo houseInfo = (HouseInfo)this.houseInfoRepository.findOne(houseInfoId);
        Task task = new Task();
        task.setCountName(houseInfo.getCountName());
        task.setHouseInfoId(houseInfoId);
        task.setStatus(Status.CREATED);
        task.setCreateTime(DateUtils.getDatetime());
        this.taskRepository.save(task);
    }

    public void run(final String[] taskIds, final Integer time) {
        String[] thread = taskIds;
        int var4 = taskIds.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String taskId = thread[var5];
            Task task = (Task)this.taskRepository.findOne(taskId);
            if(task != null) {
                task.setStatus(Status.STARTED);
                this.taskRepository.save(task);
            }
        }

        Thread var8 = new Thread(new Runnable() {
            public void run() {
                if(time != null && time.intValue() > 0) {
                    TaskService.this.logger.info("任务将于" + time + "小时后执行");

                    try {
                        Thread.sleep((long)(3600000 * time.intValue()));
                    } catch (InterruptedException var16) {
                        var16.printStackTrace();
                    }
                }

                String tmpCountName = null;
                WebDriver wb = Chromes.open();

                for(int i = 0; i < taskIds.length; ++i) {
                    long start = System.currentTimeMillis();
                    String taskId = taskIds[i];
                    Task task = (Task)TaskService.this.taskRepository.findOne(taskId);
                    task.setBeginTime(DateUtils.getDatetime());
                    TaskService.this.taskRepository.save(task);
                    Count count = (Count)TaskService.this.countRepository.findOne(task.getCountName());
                    HouseInfo houseInfo = (HouseInfo)TaskService.this.houseInfoRepository.findOne(task.getHouseInfoId());
                    TaskService.this.logger.info("正在执行第" + (i + 1) + "条任务，任务信息：" + task);

                    try {
                        tmpCountName = this.apply(wb, tmpCountName, task, count, houseInfo);
                        long e = System.currentTimeMillis();
                        long totalTime = 180000L;
                        long sleep = totalTime - (e - start);
                        if(sleep > 0L) {
                            Thread.sleep(sleep);
                        }

                        task.setStatus(Status.SUCCESS);
                    } catch (Throwable var17) {
                        var17.printStackTrace();
                        task.setStatus(Status.FAILED);
                        String errMsg = var17.getMessage();
                        task.setErrMsg(errMsg.length() < 1000?errMsg:errMsg.substring(0, 1000));
                        TaskService.this.logger.error("第" + (i + 1) + "条任务执行出现异常，任务编号：" + taskId + "，错误信息：" + var17.getMessage());
                    }

                    task.setEndTime(DateUtils.getDatetime());
                    TaskService.this.taskRepository.save(task);
                }

                wb.close();
            }

            private String apply(WebDriver wb, String tmpCountName, Task task, Count count, HouseInfo houseInfo) throws InterruptedException {
                if(!task.getCountName().equals(tmpCountName)) {
                    tmpCountName = task.getCountName();

                    try {
                        TaskService.this.login(wb, count.getCountName(), count.getCountPass());
                        Thread.sleep(2000L);
                    } catch (Exception var15) {
                        var15.printStackTrace();
                    }
                }

                wb.get("http://vip.anjuke.com/house/publish/rent/?from=manage");
                ((JavascriptExecutor)wb).executeScript("$(\'#chooseWebForm>input\').click();", new Object[0]);
                if(!houseInfo.getType().equals(Integer.valueOf(1))) {
                    ((JavascriptExecutor)wb).executeScript("$(\'#hz-entire\').click();", new Object[0]);
                } else {
                    ((JavascriptExecutor)wb).executeScript(" $(\'[name=\"flatshare\"]\').val(" + houseInfo.getHushu() + ")", new Object[0]);
                    if(houseInfo.getZhuciwo().equals(Integer.valueOf(0))) {
                        wb.findElement(By.id("select-bedroom")).click();
                        Chromes.findElement("li", "次卧", wb).click();
                    }

                    wb.findElement(By.id("select-roomorient")).click();
                    Chromes.findElement("li", houseInfo.getChaoxiang(), wb).click();
                }

                WebElement xiaoqu = null;

                try {
                    xiaoqu = wb.findElement(By.id("community_unite"));
                } catch (Exception var14) {
                    try {
                        xiaoqu = wb.findElement(By.id("GJ-village-input"));
                    } catch (Exception var13) {
                        xiaoqu = wb.findElement(By.id("wuba-village-input"));
                    }
                }

                xiaoqu.sendKeys(new CharSequence[]{houseInfo.getXiaoqu()});
                Thread.sleep(3000L);
                Chromes.execScript(wb, "$($(\'.auto-ul > li\')[0]).click()");
                Thread.sleep(1000L);
                WebElement tmpXiaoqu = Chromes.findElement("span", houseInfo.getXiaoqu(), wb);
                if(tmpXiaoqu != null) {
                    tmpXiaoqu.click();
                }

                if(tmpXiaoqu == null) {
                    tmpXiaoqu = Chromes.findElement("li", houseInfo.getXiaoqu(), wb);
                    if(tmpXiaoqu != null) {
                        tmpXiaoqu.click();
                    }
                }

                ((JavascriptExecutor)wb).executeScript("$(\'[name=\"room\"]\').click()", new Object[0]);
                ((JavascriptExecutor)wb).executeScript("$(\'[name=\"room\"]\').val(" + houseInfo.getShi() + ")", new Object[0]);
                ((JavascriptExecutor)wb).executeScript("$(\'[name=\"hall\"]\').val(" + houseInfo.getTing() + ")", new Object[0]);
                ((JavascriptExecutor)wb).executeScript("$(\'[name=\"bathroom\"]\').val(" + houseInfo.getWei() + ")", new Object[0]);
                ((JavascriptExecutor)wb).executeScript("$(\'[name=\"floor\"]\').val(" + houseInfo.getLou() + ")", new Object[0]);
                ((JavascriptExecutor)wb).executeScript("$(\'[name=\"allFloor\"]\').val(" + houseInfo.getCeng() + ")", new Object[0]);
                if(houseInfo.getDianti().equals(Integer.valueOf(1))) {
                    ((JavascriptExecutor)wb).executeScript("$(\'.ui-radio\')[2].click();", new Object[0]);
                } else {
                    ((JavascriptExecutor)wb).executeScript("$(\'.ui-radio\')[3].click();", new Object[0]);
                }

                ((JavascriptExecutor)wb).executeScript("scrollTo(0,700)", new Object[0]);
                wb.findElement(By.id("select-paymode")).click();
                Thread.sleep(5000L);
                wb.findElement(By.id("select-housetype")).click();
                Thread.sleep(1000L);
                Chromes.findElement("li", "普通住宅", wb).click();
                wb.findElement(By.id("select-housefit")).click();
                Thread.sleep(1000L);
                Chromes.findElement("li", "精装修", wb).click();
                if(!houseInfo.getType().equals(Integer.valueOf(1))) {
                    wb.findElement(By.id("select-exposure")).click();
                    Chromes.findElement("li", houseInfo.getChaoxiang(), wb).click();
                }

                String[] peizhi = houseInfo.getPeizhi().split(",");
                String[] j = peizhi;
                int var10 = peizhi.length;

                for(int var11 = 0; var11 < var10; ++var11) {
                    String tmp = j[var11];
                    Chromes.findElement("label", tmp, wb).click();
                }

                ((JavascriptExecutor)wb).executeScript("$(\'[name=\"roomarea\"]\').val(" + houseInfo.getMianji() + ")", new Object[0]);
                ((JavascriptExecutor)wb).executeScript("$(\'[name=\"rentprice\"]\').val(" + houseInfo.getZujin() + ")", new Object[0]);
                wb.findElement(By.id("select-paymode")).click();
                Chromes.findElement("li", "付1押1", wb).click();
                ((JavascriptExecutor)wb).executeScript("$(\'[name=\"title\"]\').val(\'" + houseInfo.getBiaoti() + "\')", new Object[0]);
                Chromes.findElement("a", "使用房源模板", wb).click();
                Thread.sleep(3000L);
                Chromes.findElement("label", houseInfo.getTemplate(), wb).click();
                Chromes.findElement("a", "确定", wb).click();
                ((JavascriptExecutor)wb).executeScript("scrollTo(0,1700)", new Object[0]);

                for(int var16 = 1; var16 <= houseInfo.getImgCount().intValue(); ++var16) {
                    this.selectFile(wb, houseInfo, var16);
                }

                ((JavascriptExecutor)wb).executeScript("$(\'.set-main\')[0].click()", new Object[0]);
                Thread.sleep(2000L);
                ((JavascriptExecutor)wb).executeScript("scrollTo(0,4000)", new Object[0]);
                ((JavascriptExecutor)wb).executeScript("$(\'#publish_form\')[0].submit()", new Object[0]);
                return tmpCountName;
            }

            private void selectFile(WebDriver wb, HouseInfo houseInfo, int j) throws InterruptedException {
                WebElement upload = wb.findElement(By.id("room_fileupload"));
                LocalFileDetector detector = new LocalFileDetector();
                String path = houseInfo.getImgUrl() + "\\" + j + ".jpg";
                File f = detector.getLocalFile(new CharSequence[]{path});
                ((RemoteWebElement)upload).setFileDetector(detector);
                upload.sendKeys(new CharSequence[]{f.getAbsolutePath()});
                if(wb.findElement(By.id("alert")).isDisplayed()) {
                    wb.findElement(By.className("ui-dialog-close")).click();
                    this.selectFile(wb, houseInfo, j);
                }

                Thread.sleep(30000L);
            }
        });
        var8.start();
    }

    public void login(WebDriver wb, String userName, String userPass) {
        try {
            wb.get("http://vip.anjuke.com/logout/");
            wb.get("http://vip.anjuke.com/login/");
            wb.findElement(By.id("loginName")).sendKeys(new CharSequence[]{userName});
            wb.findElement(By.id("loginPwd")).sendKeys(new CharSequence[]{userPass});
            wb.findElement(By.id("loginSubmit")).click();
            Thread.sleep(2000L);
        } catch (InterruptedException var5) {
            var5.printStackTrace();
        }

    }

    public void delAll() {
        this.taskRepository.deleteAll();
    }

    public long getCount() {
        return this.taskRepository.count();
    }

    public int countByStatus(Integer status) {
        return this.taskRepository.countByStatus(status);
    }

    private static void push(WebDriver wb, String url, Integer pushDay) {
        try {
            wb.get(url);
            wb.findElement(By.id("ckAll")).click();
            wb.findElement(By.id("batchprom")).click();
            Thread.sleep(500L);
            if(pushDay == null || pushDay.intValue() < 1 || pushDay.intValue() != 1) {
                ((WebElement)wb.findElement(By.className("time-cont")).findElements(By.tagName("label")).get(1)).findElement(By.tagName("input")).click();
            }

            Thread.sleep(500L);
            ((WebElement)wb.findElement(By.className("btn-cont")).findElements(By.tagName("a")).get(0)).click();
            Thread.sleep(1000L);
        } catch (InterruptedException var4) {
            var4.printStackTrace();
        }

    }

    private static void canCelPush(WebDriver wb, String url) {
        try {
            wb.get(url);
            wb.findElement(By.id("ckAll")).click();
            wb.findElement(By.id("cancelpro")).click();
            Thread.sleep(1000L);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

    }

    public void quickPush(String[] countNames, Integer pushDay) {
        try {
            if(countNames != null && countNames.length > 0) {
                WebDriver e = Chromes.open();

                for(int i = 0; i < countNames.length; ++i) {
                    if(countNames[i] != null && !countNames[i].equals("")) {
                        Count count = this.countRepository.findByCountName(countNames[i]);
                        this.login(e, count.getCountName(), count.getCountPass());
                        String url = "http://vip.anjuke.com/zf58/yxtg58";
                        e.get(url);
                        Integer needPush = Integer.valueOf(((WebElement)e.findElement(By.className("ui-info")).findElement(By.tagName("dl")).findElement(By.tagName("dd")).findElements(By.tagName("span")).get(1)).findElement(By.tagName("em")).getText());
                        int page = 1;
                        url = "http://vip.anjuke.com/zf58/kcfy58/W0QQpZ";
                        e.get(url + page);
                        List houselist = e.findElement(By.id("houselist")).findElements(By.tagName("tr"));

                        while(needPush.intValue() > 0) {
                            int selected = 0;

                            for(int j = 1; j < houselist.size() && needPush.intValue() >= 1; ++j) {
                                List tds = ((WebElement)houselist.get(j)).findElements(By.tagName("td"));
                                String status = ((WebElement)tds.get(5)).findElement(By.tagName("a")).getAttribute("class");
                                if(status.indexOf("batchproyx") > -1 && selected < needPush.intValue()) {
                                    ((WebElement)tds.get(0)).findElement(By.tagName("input")).click();
                                    ++selected;
                                }

                                if(selected > 0 && j == houselist.size() - 1) {
                                    e.findElement(By.id("batchprom")).click();
                                    Thread.sleep(500L);
                                    if(pushDay == null || pushDay.intValue() < 1 || pushDay.intValue() != 1) {
                                        ((WebElement)e.findElement(By.className("time-cont")).findElements(By.tagName("label")).get(1)).findElement(By.tagName("input")).click();
                                    }

                                    Thread.sleep(500L);
                                    ((WebElement)e.findElement(By.className("btn-cont")).findElements(By.tagName("a")).get(0)).click();
                                    Thread.sleep(1000L);
                                    ((WebElement)e.findElements(By.className("ui-button-positive")).get(0)).click();
                                    Thread.sleep(500L);
                                    needPush = Integer.valueOf(needPush.intValue() - selected);
                                }
                            }

                            if(needPush.intValue() > 0) {
                                ++page;
                                e.get(url + page);
                                houselist = e.findElement(By.id("houselist")).findElements(By.tagName("tr"));
                                boolean var15 = false;
                            }
                        }
                    }
                }

                e.close();
            }
        } catch (InterruptedException var14) {
            var14.printStackTrace();
        }

    }

    public Object getPushData(WebDriver wb, String countName, Integer pages) {
        Count count = this.countRepository.findByCountName(countName);
        this.login(wb, countName, count.getCountPass());
        String url = "http://vip.anjuke.com/zf58/kcfy58/W0QQpZ";
        ArrayList result = new ArrayList();

        for(int i = 1; i <= pages.intValue(); ++i) {
            wb.get(url + i);
            List houselist = wb.findElement(By.id("houselist")).findElements(By.tagName("tr"));

            for(int j = 1; j < houselist.size(); ++j) {
                List tds = ((WebElement)houselist.get(j)).findElements(By.tagName("td"));
                String status = ((WebElement)tds.get(5)).findElement(By.tagName("a")).getAttribute("class");
                String[] strs = ((WebElement)tds.get(2)).getText().split("/");
                String yesDayView = strs[0];
                String totalView = strs[1];
                if((!yesDayView.equals("0") || !totalView.equals("0")) && status.indexOf("batchproyx") > -1) {
                    HashedMap data = new HashedMap();
                    data.put("title", ((WebElement)((WebElement)tds.get(1)).findElement(By.tagName("div")).findElements(By.tagName("a")).get(2)).getText());
                    data.put("detail", ((WebElement)((WebElement)tds.get(1)).findElements(By.tagName("p")).get(0)).getText());
                    String houseNum = ((WebElement)((WebElement)tds.get(1)).findElements(By.tagName("p")).get(1)).getText();
                    houseNum = houseNum.substring(3, houseNum.indexOf("，"));
                    data.put("houseNum", houseNum);
                    data.put("pushAndUpdateDate", ((WebElement)((WebElement)tds.get(1)).findElements(By.tagName("p")).get(3)).getText());
                    data.put("yesDayView", yesDayView);
                    data.put("totalView", totalView);
                    result.add(data);
                }
            }
        }

        wb.close();
        return result;
    }

    public void push(String countName, Integer pushDay, String[] houseIds) {
        try {
            WebDriver e = Chromes.open();
            Count count = this.countRepository.findByCountName(countName);
            this.login(e, countName, count.getCountPass());

            for(int i = 1; i < houseIds.length; ++i) {
                this.goEditHouse(e, houseIds[i]);
                e.findElement(By.id("publish-rent-edit")).click();
                Thread.sleep(1000L);
                e.get("http://vip.anjuke.com/zf58/kcfy58?title=" + houseIds[i]);
                if(e.findElements(By.className("batchproyx")).size() >= 1) {
                    WebElement push = e.findElement(By.className("batchproyx"));
                    e.findElement(By.className("batchproyx")).click();
                    Thread.sleep(500L);
                    if(pushDay == null || pushDay.intValue() < 1 || pushDay.intValue() != 1) {
                        ((WebElement)e.findElement(By.className("time-cont")).findElements(By.tagName("label")).get(1)).findElement(By.tagName("input")).click();
                    }

                    Thread.sleep(500L);
                    ((WebElement)e.findElement(By.className("btn-cont")).findElements(By.tagName("a")).get(0)).click();
                    Thread.sleep(1000L);
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    public void goEditHouse(WebDriver wb, String houseId) {
        String url = "http://vip.anjuke.com/house/publish/rent/wb/";
        wb.get(url + houseId);
    }

    public void calcelPush(String[] countNames) {
        WebDriver wb = Chromes.open();

        for(int i = 1; i < countNames.length; ++i) {
            Count count = this.countRepository.findByCountName(countNames[i]);
            this.login(wb, countNames[i], count.getCountPass());
            String url = "http://vip.anjuke.com/zf58/yxtg58";
            canCelPush(wb, url);
            canCelPush(wb, url);
        }

        wb.close();
    }
}
