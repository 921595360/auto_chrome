package com.silence.common;

import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
* @ClassName: BeanUtil 
* @Description: TODO(bean工具类) 
* @author tanxingyi
* @date 2015年8月13日 下午2:43:30 
*
 */
public class BeanUtil {
	/**
	 * 将map转换成bean
	 * @param to 目标bean
	 * @param map 源map
	 * @return
	 */
	public static <T> T MapToBean(Class<T> to,Map<String, Object> map){
		try{
			if(!CommonUtils.isNullOrEmpty(map)){
				T t = to.newInstance();
				BeanUtils.populate(t,map);
				return t;
			}else 
				return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	/**
	 * 将map集合转换成bean集合
	 * @param to 目标bean
	 * @param maps 源maps
	 * @return
	 */
	public static <T> List<T> ListMapToBean(Class<T> to,List<Map<String, Object>> maps){
		try{
			List<T> temp=new ArrayList<T>();
			if(!CommonUtils.isNullOrEmpty(maps)){
				
				for (int i = 0; i < maps.size(); i++) {
					T t = to.newInstance();
					BeanUtils.populate(t, maps.get(i));
					temp.add(t);
				}
				return temp;
			}else 
				return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
