package com.silence.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by silence on 2017/10/16.
 */
@Entity(name = "t_task")
public class Task {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "task_id")
    private String taskId;
    @Column(name = "count_name")
    private String countName;//账户
    @Column(name = "house_info_id")
    private String houseInfoId;//本次发布的房源信息
    @Column(name = "begin_time")
    private String beginTime;
    @Column(name = "end_time")
    private String endTime;
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "error_msg",length = 1000)
    private String errMsg;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public String getHouseInfoId() {
        return houseInfoId;
    }

    public void setHouseInfoId(String houseInfoId) {
        this.houseInfoId = houseInfoId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public interface Status {
        public static final Integer CREATED = -1;//已创建
        public static final Integer STARTED = 0;//已启动
        public static final Integer SUCCESS = 1;//成功
        public static final Integer FAILED = 2;//失败
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", countName='" + countName + '\'' +
                ", houseInfoId='" + houseInfoId + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
