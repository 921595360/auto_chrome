//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.service;

import com.silence.common.DateUtils;
import com.silence.entity.HouseInfo;
import com.silence.repository.HouseInfoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class HouseInfoService {
    @Autowired
    private HouseInfoRepository houseInfoRepository;

    public HouseInfoService() {
    }

    public List<HouseInfo> list() {
        return this.houseInfoRepository.findAll();
    }

    public List<HouseInfo> list(PageRequest page, String countName) {
        return countName != null && countName != ""?this.houseInfoRepository.findByCountName(page, countName).getContent():this.houseInfoRepository.findAll(page).getContent();
    }

    public Integer getCount() {
        return Integer.valueOf(String.valueOf(this.houseInfoRepository.count()));
    }

    public int getCount(String countName) {
        return this.houseInfoRepository.findByCountName(countName).size();
    }

    /**
     * 添加房源
     * @param houseInfo
     */
    public void save(HouseInfo houseInfo) {
    	//设置房源上传时间
    	houseInfo.setCreateTime(DateUtils.getDatetime());//当前时间
        this.houseInfoRepository.save(houseInfo);
    }

    public void delete(String id) {
        this.houseInfoRepository.delete(id);
    }

    public void delAll() {
        this.houseInfoRepository.deleteAll();
    }
}
