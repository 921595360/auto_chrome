package com.silence.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by silence on 2017/10/16.
 * 房源信息
 */
@Entity(name="t_house_info")
public class HouseInfo {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "house_info_id")
    private String houseInfoId;

    @Column(name = "count_name")
    private String countName; //所属账号

    @Column(name = "xiaoqu")
    private String xiaoqu;
    @Column(name = "type")
    private Integer type;
    @Column(name = "shi")
    private Integer shi;
    @Column(name = "ting")
    private Integer ting;
    @Column(name = "wei")
    private Integer wei;
    @Column(name = "lou")
    private Integer lou;
    @Column(name = "ceng")
    private Integer ceng;
    @Column(name = "dianti")
    private Integer dianti;
    @Column(name = "chaoxiang")
    private String chaoxiang;
    @Column(name = "peizhi")
    private String peizhi;
    @Column(name = "mianji")
    private Integer mianji;
    @Column(name = "zujin")
    private Integer zujin;
    @Column(name = "biaoti")
    private String biaoti;
    @Column(name = "hushu")
    private Integer hushu;
    @Column(name = "zhuciwo")
    private Integer zhuciwo;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "img_count")
    private Integer imgCount;

    @Column(name = "template")
    private String template;

    public interface Zhuciwo {
        public static final Integer ZHUWO = 0;//主卧
        public static final Integer CIWO = 1;//次卧
    }

    public interface Chaoxiang {
        public static final String DONG = "东";//无
        public static final String XI = "西";//无
        public static final String YOU = "南";//有
        public static final String BEI = "北";//有
    }

    public String getXiaoqu() {
        return xiaoqu;
    }

    public void setXiaoqu(String xiaoqu) {
        this.xiaoqu = xiaoqu;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getShi() {
        return shi;
    }

    public void setShi(Integer shi) {
        this.shi = shi;
    }

    public Integer getTing() {
        return ting;
    }

    public void setTing(Integer ting) {
        this.ting = ting;
    }

    public Integer getWei() {
        return wei;
    }

    public void setWei(Integer wei) {
        this.wei = wei;
    }

    public Integer getLou() {
        return lou;
    }

    public void setLou(Integer lou) {
        this.lou = lou;
    }

    public Integer getCeng() {
        return ceng;
    }

    public void setCeng(Integer ceng) {
        this.ceng = ceng;
    }

    public Integer getDianti() {
        return dianti;
    }

    public void setDianti(Integer dianti) {
        this.dianti = dianti;
    }

    public String getChaoxiang() {
        return chaoxiang;
    }

    public void setChaoxiang(String chaoxiang) {
        this.chaoxiang = chaoxiang;
    }

    public String getPeizhi() {
        return peizhi;
    }

    public void setPeizhi(String peizhi) {
        this.peizhi = peizhi;
    }

    public Integer getMianji() {
        return mianji;
    }

    public void setMianji(Integer mianji) {
        this.mianji = mianji;
    }

    public Integer getZujin() {
        return zujin;
    }

    public void setZujin(Integer zujin) {
        this.zujin = zujin;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public Integer getHushu() {
        return hushu;
    }

    public void setHushu(Integer hushu) {
        this.hushu = hushu;
    }

    public Integer getZhuciwo() {
        return zhuciwo;
    }

    public void setZhuciwo(Integer zhuciwo) {
        this.zhuciwo = zhuciwo;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getHouseInfoId() {
        return houseInfoId;
    }

    public void setHouseInfoId(String houseInfoId) {
        this.houseInfoId = houseInfoId;
    }

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public Integer getImgCount() {
        return imgCount;
    }

    public void setImgCount(Integer imgCount) {
        this.imgCount = imgCount;
    }

    public interface Type {
        public static final Integer ZHENGZU = 0;//整租
        public static final Integer HEZU = 1;//合租
    }

    public interface Dianti {
        public static final Integer WU = 0;//无
        public static final Integer YOU = 1;//有
    }


}
