package com.silence.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by silence-pc on 2017/11/19.
 * 自定义宏任务
 */
@Entity(name = "t_macro_task")
public class MacroTask {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "task_id")
    private String taskId;
    @Column(name = "name")
    private String name;
    @Column(name = "begin_time")
    private String beginTime;
    @Column(name = "end_time")
    private String endTime;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "cycle")
    private Integer cycle;//任务执行周期
    @Column(name = "params")
    private String params;//调用时传递的参数
    @Column(name = "method")
    private String method;//执行时调用的方法
    @Column(name = "status")
    private Integer status;

    public interface Status {
        public static final Integer CREATED = -1; //已创建
        public static final Integer STARTED = 0; //已启动
        public static final Integer SUCCESS = 1;//成功
        public static final Integer FAILED = 2;//失败
        public static final Integer FINISHED = 3;//结束
    }


    public static enum Type{
        ONECE(1),//一次
        DAYLI(2);//每天
        public final Integer value;
        Type(int value){this.value=value;}
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
