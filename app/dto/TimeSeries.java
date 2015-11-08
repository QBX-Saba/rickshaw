package dto;
import com.fasterxml.jackson.core.JsonProcessingException;

import play.twirl.api.Content;
import util.Util;


public class TimeSeries implements Content{
	 
	private Double value;
	private Long time;
	
	
	@Override
	public String body() {
		String json = null;
		try {
			json = Util.mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	@Override
	public String contentType() {
		
		return "json/application";
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	


}
