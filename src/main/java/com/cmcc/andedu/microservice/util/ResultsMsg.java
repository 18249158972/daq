package com.cmcc.andedu.microservice.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultsMsg implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1181117633986179566L;
	
	@JsonProperty("ret")
    private int ret;
    @JsonProperty("msg")
    private String msg;
    
	public ResultsMsg() {
		super();
	}
	
	public ResultsMsg(int code){
        this.ret=code;
        if(ret==0){
            this.msg="调用成功";
        }else{
            this.msg="调用失败";
        }
    }
	
	public ResultsMsg(int ret, String msg) {
		super();
		this.ret = ret;
		this.msg = msg;
	}

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	

}
