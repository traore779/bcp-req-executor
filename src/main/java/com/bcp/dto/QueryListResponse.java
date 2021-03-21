package com.bcp.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bcp.service.QueryService;

public class QueryListResponse {
	
	private List <QueryObjectDto> requetes;

	public List <QueryObjectDto> getRequetes() {
		return requetes;
	}

	public void setRequetes(List <QueryObjectDto> requetes) {
		this.requetes = requetes;
	}


	
	
}

