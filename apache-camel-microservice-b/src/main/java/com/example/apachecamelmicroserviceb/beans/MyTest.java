package com.example.apachecamelmicroserviceb.beans;

public class MyTest {
	private Long id;
	private String test;
	
	
	public MyTest() {
		super();
	}

	public MyTest(Long id, String test) {
		super();
		this.id = id;
		this.test = test;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}

	@Override
	public String toString() {
		return "MyTest [id=" + id + ", test=" + test + "]";
	}
	
	
	
}
