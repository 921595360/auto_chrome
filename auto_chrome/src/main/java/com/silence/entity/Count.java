package com.silence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by silence-obook on 2017-10-15.
 */

@Entity(name="t_count")//指定表
public class Count {

    @Id //指定主键
    @Column(name = "count_name")
    private String countName;

    @Column(name = "count_pass")
    private String countPass;

    public String getCountPass() {
        return countPass;
    }

    public void setCountPass(String countPass) {
        this.countPass = countPass;
    }

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }


    public Count() {
    }

    public Count(String countName, String countPass) {
        this.countName = countName;
        this.countPass = countPass;
    }
}
