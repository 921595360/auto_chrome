//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.silence.controller;

import com.silence.common.JSONResult;
import com.silence.entity.HouseInfo;
import com.silence.service.HouseInfoService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({ "houseInfo" })
public class HouseInfoController {
	@Resource
	private HouseInfoService houseInfoService;

	public HouseInfoController() {
	}

	@RequestMapping(value = { "list" }, method = { RequestMethod.GET })
	public String list(Model model, String countName) {
		return "houseInfoList";
	}

	@RequestMapping(value = { "list" }, method = { RequestMethod.POST })
	@ResponseBody
	public Object listData(String countName, Integer page, Integer rows) {
		PageRequest pageRequest = new PageRequest(page.intValue() - 1, rows.intValue());
		HashedMap datas = new HashedMap();
		Integer count = Integer.valueOf(0);
		if (countName != null && countName != "") {
			count = Integer.valueOf(this.houseInfoService.getCount(countName));
		} else {
			count = this.houseInfoService.getCount();
		}

		List houseInfos = this.houseInfoService.list(pageRequest, countName);
		datas.put("rows", houseInfos);
		datas.put("records", count);
		datas.put("page", page);
		datas.put("total",
				Integer.valueOf((int) Math.ceil((double) count.intValue() / ((double) rows.intValue() + 0.0D))));
		return datas;
	}

	@RequestMapping(value = { "update" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONResult update(HouseInfo houseInfo) {
		JSONResult result = new JSONResult();

		try {
			this.houseInfoService.save(houseInfo);
		} catch (Exception var4) {
			result.setSuccess(false);
			result.setMsg(var4.toString());
			return result;
		}

		result.setSuccess(true);
		return result;
	}

	@RequestMapping(value = { "delete" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONResult delete(String id) {
		JSONResult result = new JSONResult();

		try {
			this.houseInfoService.delete(id);
		} catch (Exception var4) {
			result.setSuccess(false);
			result.setMsg(var4.toString());
			return result;
		}

		result.setSuccess(true);
		return result;
	}

	@RequestMapping(value = { "upload" }, method = { RequestMethod.POST })
	public String upload(String countName, @RequestParam("file") MultipartFile file) {
		try {
			//解析上传的excel
			HSSFWorkbook e = new HSSFWorkbook(file.getInputStream());
			HSSFSheet sheet = e.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();

			for (int i = 1; i <= rowNum; ++i) {
				HSSFRow row = sheet.getRow(i);
				HouseInfo houseInfo = new HouseInfo();
				houseInfo.setCountName(countName);
				String xiaoQuMIngCheng = row.getCell(0).getStringCellValue();
				Integer type = Integer.valueOf(row.getCell(1).getStringCellValue().equals("整租") ? 0 : 1);
				Integer shi = Integer.valueOf((int) row.getCell(2).getNumericCellValue());
				Integer ting = Integer.valueOf((int) row.getCell(3).getNumericCellValue());
				Integer wei = Integer.valueOf((int) row.getCell(4).getNumericCellValue());
				Integer lou = Integer.valueOf((int) row.getCell(5).getNumericCellValue());
				Integer ceng = Integer.valueOf((int) row.getCell(6).getNumericCellValue());
				Integer dianti = Integer.valueOf(row.getCell(7).getStringCellValue().equals("无") ? 0 : 1);
				String chaoxiang = row.getCell(8).getStringCellValue();
				String peizhi = row.getCell(9).getStringCellValue();
				Integer mianji = Integer.valueOf((int) row.getCell(10).getNumericCellValue());
				Integer zujin = Integer.valueOf((int) row.getCell(11).getNumericCellValue());
				String biaoti = row.getCell(12).getStringCellValue();
				Integer hushu = Integer.valueOf((int) row.getCell(13).getNumericCellValue());
				Integer zhuciwo = Integer.valueOf(row.getCell(14).getStringCellValue().equals("次卧") ? 0 : 1);
				String imgUrl = row.getCell(15).getStringCellValue();
				Integer imgCount = Integer.valueOf((int) row.getCell(16).getNumericCellValue());
				String template = row.getCell(17).getStringCellValue();
				houseInfo.setXiaoqu(xiaoQuMIngCheng);
				houseInfo.setType(type);
				houseInfo.setShi(shi);
				houseInfo.setTing(ting);
				houseInfo.setWei(wei);
				houseInfo.setLou(lou);
				houseInfo.setCeng(ceng);
				houseInfo.setDianti(dianti);
				houseInfo.setChaoxiang(chaoxiang);
				houseInfo.setPeizhi(peizhi);
				houseInfo.setMianji(mianji);
				houseInfo.setZujin(zujin);
				houseInfo.setBiaoti(biaoti);
				houseInfo.setHushu(hushu);
				houseInfo.setZhuciwo(zhuciwo);
				houseInfo.setImgUrl(imgUrl);
				houseInfo.setImgCount(imgCount);
				houseInfo.setTemplate(template);
				
				//保存房源
				this.houseInfoService.save(houseInfo);
			}

			e.close();
		} catch (Exception var27) {
			var27.printStackTrace();
		}

		return "redirect:/houseInfo/list?countName=" + countName;
	}

	@RequestMapping({ "delAll" })
	public Object delAll() {
		this.houseInfoService.delAll();
		return "redirect:/houseInfo/list";
	}
}
