package com.whut.richer.bean;

/*
 * ��������������Ϣ
 */
public class House {
	private int position;	//���ӵ�λ��
	private String name;	//ӵ�и÷��������
	private int toll;		//��·��
	private int price;		//��������ļ۸�(������������)������������
	private int update_price;	//���������۸�
	private int grade;		//���ӵļ���  ���3��
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getToll() {
		return toll;
	}
	public void setToll(int toll) {
		this.toll = toll;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getUpdate_price() {
		return update_price;
	}
	public void setUpdate_price(int update_price) {
		this.update_price = update_price;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
}
