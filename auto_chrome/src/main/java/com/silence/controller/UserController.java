package com.silence.controller;

import com.silence.entity.Task;
import com.silence.repository.TaskRepository;
import com.silence.util.Chromes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping("test")
    public  void test(){
        taskRepository.save(new Task());
    }


}
