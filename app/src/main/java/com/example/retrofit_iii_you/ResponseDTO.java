package com.example.retrofit_iii_you;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ResponseDTO implements Serializable {

	@SerializedName("status")
	private int status;

	@SerializedName("success")
	private boolean success;

	@SerializedName("data")
	private DataDTO data;

	public int getStatus(){
		return status;
	}

	public boolean isSuccess(){
		return success;
	}

	public DataDTO getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDTO{" + 
			"status = '" + status + '\'' + 
			",success = '" + success + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}