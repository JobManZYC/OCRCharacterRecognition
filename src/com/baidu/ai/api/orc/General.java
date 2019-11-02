package com.baidu.ai.api.orc;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ai.api.auth.AuthService;
import com.baidu.ai.api.pojo.BaiDuOCRBean;
import com.baidu.ai.api.pojo.WordsBean;
import com.baidu.ai.api.util.Base64Util;
import com.baidu.ai.api.util.FileUtil;
import com.baidu.ai.api.util.HttpUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import com.baidu.aip.ocr.AipOcr;
import com.google.gson.JsonObject;


public class General {
	
	public static void main(String[] args) {
		// 通用识别url
		String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";
		// 本地图片路径
		String filePath = "D:\\zhongying.jpeg";// #####本地文件路径#####
		
//		// 获取一个文件夹里的所有图片
//		File file = new File("D:\\tupian");
//		File[] files = file.listFiles();
//		for(int i=0;i<files.length;i++) {
//			File file1 = files[i];
//            file1.getName();   //根据后缀判断
//            System.out.println(file1);
//		}
		
		
		
		try {
			byte[] imgData = FileUtil.readFileByBytes(filePath);
			String imgStr = Base64Util.encode(imgData);
			String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
			/**
			 * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
			 */
			String accessToken = AuthService.getAuth();// #####调用鉴权接口获取的token#####
			String result = HttpUtil.post(otherHost, accessToken, params);
			System.out.println(result);
			JSONObject resultJson = JSONObject.parseObject(result);
			System.out.println(resultJson);
			
			BaiDuOCRBean baiDuOCRBean = com.alibaba.fastjson.JSONObject.toJavaObject(JSON.parseObject(resultJson.toString()), BaiDuOCRBean.class);
			
			List list = baiDuOCRBean.getWords_result();
			for (int i = 0; i < list.size(); i++) {
				JSONObject record = (JSONObject) list.get(i);
				WordsBean wordBean = com.alibaba.fastjson.JSONObject.toJavaObject(JSON.parseObject(record.toString()), WordsBean.class);
				String words = wordBean.getWords();
				System.out.println(words);
			}
			
			
			
//			JSONObject resultJson = JSONObject.fromObject(result);
//			
//			Iterator<String> iterator = (Iterator<String>) resultJson.keySet();
//			
//			Map<String,String> map = new HashMap<String,String>();
//			while(iterator.hasNext()) {
//				String key = iterator.next();
//				if(resultJson.getString(key).length()>0) {
//					map.put(key, resultJson.getString(key));
//				}
//			}
//			System.out.println(map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		 // 传入可选参数调用接口
//	    HashMap<String, String> options = new HashMap<String, String>();
//	    options.put("detect_direction", "true");
//	    options.put("probability", "true");
//	    // 参数为本地图片路径
//	    String image = "test.jpg";
//	    JSONObject res = client.basicAccurateGeneral(image, options);
//	    System.out.println(res.toString(2));
//	 // 通用文字识别（含位置信息版）, 图片参数为远程url图片
//	    JSONObject res1 = client.generalUrl(url, options);
//	    System.out.println(res1.toString(2));
	    
	}
	
	
	
}
