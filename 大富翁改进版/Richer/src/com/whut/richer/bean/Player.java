package com.whut.richer.bean;

import java.util.ArrayList;
import java.util.List;


/*
 *����������ҵ����ԺͲ������� 
 */
public class Player {
	private int blockNum;	//���ӵ�е�·����
	private int bombNum;	//��ҵ�ը����
	private int robotNum;	//��ҵĻ�������
	private int position;	//��ҵ�ǰλ��
	private char symbol;	//��ҵ�ͼ��ʶ
	private String name;		//�������
	private int cash;	//����ֽ���
	private int point;	//��ҹ���������õĵ���
	private int stayBout;	//��ҽԺ��������ͣ���غ���
	private int mammon;	//����״̬
	private boolean alive;	//����Ƿ񻹴��
	private List<House> property = new ArrayList<House>();	//��ҷ���
	
	public Player(){
		cash=10000;
		point=0;
		stayBout=0;
		position=0;
		mammon=0;
		alive=true;
	}
	
	public int getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(int blockNum) {
		this.blockNum = blockNum;
	}

	public int getBombNum() {
		return bombNum;
	}

	public void setBombNum(int bombNum) {
		this.bombNum = bombNum;
	}

	public int getRobotNum() {
		return robotNum;
	}

	public void setRobotNum(int robotNum) {
		this.robotNum = robotNum;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public char getSymbol() {
		return symbol;
	}

	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCash() {
		return cash;
	}
	public void setCash(int cash) {
		this.cash = cash;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getStayBout() {
		return stayBout;
	}
	public void setStayBout(int stayBout) {
		this.stayBout = stayBout;
	}
	
	public int getMammon() {
		return mammon;
	}

	public void setMammon(int mammon) {
		this.mammon = mammon;
	}

	public List<House> getProperty() {
		return property;
	}
	public void setProperty(List<House> property) {
		this.property = property;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
}
