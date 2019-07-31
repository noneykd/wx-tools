package com.soecode.wxtools.bean;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *  {
 *            "touser":"OPENID",
 *            "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
 *            "url":"http://weixin.qq.com/download",
 *            "miniprogram":{
 *              "appid":"xiaochengxuappid12345",
 *              "pagepath":"index?foo=bar"
 *            },
 *            "data":{
 *                    "first": {
 *                        "value":"恭喜你购买成功！",
 *                        "color":"#173177"
 *                    },
 *                    "keyword1":{
 *                        "value":"巧克力",
 *                        "color":"#173177"
 *                    },
 *                    "keyword2": {
 *                        "value":"39.8元",
 *                        "color":"#173177"
 *                    },
 *                    "keyword3": {
 *                        "value":"2014年9月22日",
 *                        "color":"#173177"
 *                    },
 *                    "remark":{
 *                        "value":"欢迎再次购买！",
 *                        "color":"#173177"
 *                    }
 *            }
 *        }
 *  参数	是否必填	说明
 * touser	是	接收者openid
 * template_id	是	模板ID
 * url	否	模板跳转链接（海外帐号没有跳转能力）
 * miniprogram	否	跳小程序所需数据，不需跳小程序可不用传该数据
 * appid	是	所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
 * pagepath	否	所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏
 * data	是	模板数据
 * color	否	模板内容字体颜色，不填默认为黑色
 */
public class TemplateSender {

	private String touser;
	private String template_id;
	private String url;
	private Object data;

	private MiniProgram miniprogram;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public MiniProgram getMiniprogram() {
		return miniprogram;
	}

	public void setMiniprogram(MiniProgram miniprogram) {
		this.miniprogram = miniprogram;
	}

	public TemplateSender miniprogram(String appid){
		return miniprogram(appid, null);
	}

	public TemplateSender miniprogram(String appid, String pagepath){
		this.miniprogram = new MiniProgram(appid, pagepath);
		return this;
	}

	public TemplateSender addData(String key, String value){
		return addData(key, value, null);
	}

	public TemplateSender addData(String key, String value, String color){
		Map<String, DataValue> map;
		if (data == null) {
			map = new HashMap<>();
			data = map;
		} else {
			map = (Map<String, DataValue>)data;
		}
		map.put(key, new DataValue(value, color));
		return this;
	}

	@Override
	public String toString() {
		return "TemplateSender [touser=" + touser + ", template_id=" + template_id + ", url=" + url + ", data=" + data
				+ "]";
	}
	
	public String toJson() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	public static class MiniProgram{
		private String appid;
		private String pagepath;

		public MiniProgram(String appid, String pagepath) {
			this.appid = appid;
			this.pagepath = pagepath;
		}

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getPagepath() {
			return pagepath;
		}

		public void setPagepath(String pagepath) {
			this.pagepath = pagepath;
		}

		public String toJson() throws JsonGenerationException, JsonMappingException, IOException {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(this);
		}
	}

	public static enum DataKey {
		first,keyword1,keyword2,keyword3,keyword4,keyword5,remark
	}

	public static class DataValue{
		private String value;
		private String color;

		public DataValue(String value) {
			this.value = value;
		}

		public DataValue(String value, String color) {
			this.value = value;
			this.color = color;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}
		public String toJson() throws JsonGenerationException, JsonMappingException, IOException {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
			return mapper.writeValueAsString(this);
		}
	}
}
