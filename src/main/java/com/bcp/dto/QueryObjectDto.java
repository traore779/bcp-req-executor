package com.bcp.dto;

public class QueryObjectDto {

	private long id;

	private String description ;

	private String request;

	public QueryObjectDto() {
	}

	public QueryObjectDto(String description, String request) {
		this.description = description;
		this.request = request;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
}
