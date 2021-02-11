package com.example.demo.Response;

import java.util.List;

import com.example.demo.model.Artist_Request;

public class ArtistResponseRequest {
	
	public String Message;
	public List<Artist_Request> allRequest;

	
	
	
	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public List<Artist_Request> getAllRequest() {
		return allRequest;
	}

	public void setAllRequest(List<Artist_Request> allRequest) {
		this.allRequest = allRequest;
	}
	
	

}
