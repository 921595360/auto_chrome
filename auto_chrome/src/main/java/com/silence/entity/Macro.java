package com.silence.entity;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.hibernate.annotations.GenericGenerator;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by silence-pc on 2017/11/19.
 */
@Entity(name = "t_macro")
public class Macro {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "maro_id")
    private String maroId;

    private Integer type;

    public static void main(String[] args) {
        JexlEngine jexl=new JexlEngine();
        JexlContext jc = new MapContext();



        //jc.set("macro",new Macro());
        //jexl.createScript("macro.test(\"123\")").execute(jc);
        jc.set("pool",new HashMap<>());

        jexl.createScript("pool.put(\"age\",18)").execute(jc);
        jexl.createScript("pool['age']=19").execute(jc);

    }

    public Object test(String name){
        System.out.println("test");
        return 1;
    }




}
