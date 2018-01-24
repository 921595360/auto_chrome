//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.controller;

import com.silence.entity.Count;
import com.silence.service.CountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"count"})
public class CountController {
    @Autowired
    private CountService countService;

    public CountController() {
    }

    @RequestMapping({"list"})
    public String list(Model model) {
        List counts = this.countService.list();
        model.addAttribute("counts", counts);
        return "countList";
    }

    @RequestMapping({"login"})
    public Object login(String countName) {
        this.countService.login(countName);
        return null;
    }

    @RequestMapping({"del"})
    public Object del(String countName) {
        this.countService.del(countName);
        return "redirect:/";
    }

    @RequestMapping({"add"})
    public Object add(Count count) {
        this.countService.add(count);
        return "redirect:/";
    }

    @RequestMapping("applyGanji")
    public Object applyGanji(String countName){
        countService.appplayGanji(countName);
        return null;
    }

}
