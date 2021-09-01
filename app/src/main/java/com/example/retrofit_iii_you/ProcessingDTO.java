package com.example.retrofit_iii_you;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ProcessingDTO implements Serializable {

	@SerializedName("status")
	private String status;

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ProcessingDTO{" + 
			"status = '" + status + '\'' + 
			"}";
		}
}