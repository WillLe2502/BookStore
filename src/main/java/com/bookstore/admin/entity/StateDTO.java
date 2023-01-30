package com.bookstore.admin.entity;

public class StateDTO {
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
		
	}
	public StateDTO() {
		super();
	}
	public StateDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
