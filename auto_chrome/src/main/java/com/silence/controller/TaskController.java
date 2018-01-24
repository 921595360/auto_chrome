//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.controller;

import com.silence.common.JSONResult;
import com.silence.service.TaskService;
import com.silence.util.Chromes;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"task"})
public class TaskController {
    @Resource
    private TaskService taskService;

    public TaskController() {
    }

    @RequestMapping({"list"})
    public String list(Model model, String countName, Integer status) {
        List tasks = this.taskService.list(countName, status);
        model.addAttribute("tasks", tasks);
        return "taskList";
    }

    @RequestMapping({"add"})
    @ResponseBody
    public JSONResult add(String countName, String houseInfoId) {
        JSONResult jsonResult = new JSONResult();

        try {
            this.taskService.add(houseInfoId);
        } catch (Exception var5) {
            var5.printStackTrace();
            jsonResult.setMsg(var5.getMessage());
            jsonResult.setSuccess(false);
        }

        return jsonResult;
    }

    @RequestMapping({"run"})
    @ResponseBody
    public JSONResult run(String[] taskIds, Integer time) {
        JSONResult jsonResult = new JSONResult();

        try {
            this.taskService.run(taskIds, time);
        } catch (Exception var5) {
            var5.printStackTrace();
            jsonResult.setMsg(var5.getMessage());
            jsonResult.setSuccess(false);
        }

        return jsonResult;
    }

    @RequestMapping({"delAll"})
    public Object delAll() {
        this.taskService.delAll();
        return "redirect:/task/list";
    }

    @RequestMapping({"quickPush"})
    @ResponseBody
    public JSONResult quickPush(String[] countNames, Integer pushDay) {
        JSONResult jsonResult = new JSONResult();

        try {
            this.taskService.quickPush(countNames, pushDay);
        } catch (Exception var5) {
            var5.printStackTrace();
            jsonResult.setMsg(var5.getMessage());
            jsonResult.setSuccess(false);
        }

        return jsonResult;
    }

    @RequestMapping({"cancelPush"})
    @ResponseBody
    public JSONResult cancelPush(String[] countNames) {
        JSONResult jsonResult = new JSONResult();

        try {
            this.taskService.calcelPush(countNames);
        } catch (Exception var4) {
            var4.printStackTrace();
            jsonResult.setMsg(var4.getMessage());
            jsonResult.setSuccess(false);
        }

        return jsonResult;
    }

    @RequestMapping({"aiPush"})
    @ResponseBody
    public JSONResult aiPush(String countName, Integer pages) {
        JSONResult jsonResult = new JSONResult();

        try {
            Object e = this.taskService.getPushData(Chromes.open(), countName, pages);
            jsonResult.setResult(e);
        } catch (Exception var5) {
            var5.printStackTrace();
            jsonResult.setMsg(var5.getMessage());
            jsonResult.setSuccess(false);
        }

        return jsonResult;
    }

    @RequestMapping({"push"})
    @ResponseBody
    public JSONResult push(String countName, Integer pushDay, String[] houseIds) {
        JSONResult jsonResult = new JSONResult();

        try {
            this.taskService.push(countName, pushDay, houseIds);
        } catch (Exception var6) {
            var6.printStackTrace();
            jsonResult.setMsg(var6.getMessage());
            jsonResult.setSuccess(false);
        }

        return jsonResult;
    }
}
