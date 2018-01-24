//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.controller;

import com.silence.entity.Task.Status;
import com.silence.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    private TaskService taskService;

    public IndexController() {
    }

    @RequestMapping({"/"})
    public String index(Model model) {
        long total = this.taskService.getCount();
        int success = this.taskService.countByStatus(Status.SUCCESS);
        int failed = this.taskService.countByStatus(Status.FAILED);
        int started = this.taskService.countByStatus(Status.STARTED);
        model.addAttribute("success", Integer.valueOf((int)((double)success / ((double)total + 0.0D) * 100.0D)));
        model.addAttribute("failed", Integer.valueOf((int)((double)failed / ((double)total + 0.0D) * 100.0D)));
        model.addAttribute("started", Integer.valueOf((int)((double)started / ((double)total + 0.0D) * 100.0D)));
        return "index";
    }
}
