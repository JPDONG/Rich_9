package com.whut.richer.util;

import java.util.List;

import com.whut.richer.bean.Ground;
import com.whut.richer.bean.House;
import com.whut.richer.bean.Player;
import com.whut.richer.map.Map;

/*
 * ����������Ҳ�������
 */
public class Control {
	
	//�����ӣ����ص���
	public static int roll(){
		return (int)(Math.random()*6+1);
	}
	
	//�ֵ����ʱ�����Գ�������λ��N���Լ��ķ���
	public static void sell(int n,Player player){
		boolean flag = false;
		List<House> property = player.getProperty();
		for(House h:property){
			if(h.getPosition()==n){
				int price = player.getCash();
				price+=h.getPrice()*2;
				player.setCash(price);
				Ground ground = Map.map.get(n);
				ground.setHouse(null);
				ground.setPreSymbol('0');
				System.out.println("���۳ɹ�����Ǯ��"+h.getPrice()*2);
				flag=true;
			}		
		}
		if(!flag){
			System.out.println("���ز�����ģ���Ȩ����");
		}
	}
	
	//��·�� NΪǰ����Ծ��� ����Ϊ��ʾ��
	public static void block(int n,Player player){
		int blockNum = player.getBlockNum();
		if(blockNum!=0){
			int position = player.getPosition();
			if(n>=-10&&n<=10){
				Ground ground = Map.map.get(position+n);
				if(ground.getPreSymbol()!='H'&&ground.getPreSymbol()!='P'
						&&ground.getPlayer()==null&&ground.isBlock()==false
						&&ground.isBomb()==false){
					ground.setBlock(true);
					ground.setSymbol('#');
					player.setBlockNum(--blockNum);
					System.out.println("·�����óɹ���ʣ��·�Ͽ���Ϊ��"+player.getBlockNum());
				}else{
					if((ground.getPreSymbol()=='H'||ground.getPreSymbol()=='P')
							&&ground.isBlock()==false&&ground.isBomb()==false)
						ground.setBlock(true);
						ground.setSymbol('#');
						player.setBlockNum(--blockNum);
						System.out.println("·�����óɹ���ʣ��·�Ͽ���Ϊ��"+player.getBlockNum());
				}

			}else{
				System.out.println("�����õ�λ�ù�Զ�������10�����ڣ�");
			}
		}else{
			System.out.println("��Ǹ����û��·�Ͽ���");		
		}
	}
	
	//��ը��  ��·����ͬ  ��ը��סԺ3��
	public static void bomb(int n,Player player){
		int bombNum = player.getBombNum();
		if(bombNum!=0){
			int position = player.getPosition();
			if(n>=-10&&n<=10){
				Ground ground = Map.map.get(position+n);
				if(ground.getPreSymbol()!='H'&&ground.getPreSymbol()!='P'
						&&ground.getPlayer()==null&&ground.isBlock()==false
						&&ground.isBomb()==false){
					ground.setBomb(true);
					ground.setSymbol('@');
					player.setBombNum(--bombNum);
					System.out.println("ը�����óɹ���ʣ��ը������Ϊ��"+player.getBombNum());
				}else{
					if((ground.getPreSymbol()=='H'||ground.getPreSymbol()=='P')
							&&ground.isBlock()==false&&ground.isBomb()==false)
						ground.setBomb(true);
						ground.setSymbol('@');
						player.setBombNum(--bombNum);
						System.out.println("ը�����óɹ���ʣ��ը������Ϊ��"+player.getBombNum());
				}
			}else{
				System.out.println("�����õ�λ�ù�Զ�������10�����ڣ�");
			}
		}else{
			System.out.println("��Ǹ����û��ը������");
		}
	}
	
	//�õ��߿�����ɨǰ��10���ڵ���������
	public static void robot(Player player){
		int robot = player.getRobotNum();
		if(robot!=0){
			int position = player.getPosition();
			for(int i=0;i<10;i++){
				Ground ground = Map.map.get(position+i+1);
				if(ground.isBlock()==true){			
					ground.setBlock(false);
					ground.setSymbol(ground.getPreSymbol());
				}
				if(ground.isBomb()==true){				
					ground.setBomb(false);
					ground.setSymbol(ground.getPreSymbol());
				}
			}
			player.setRobotNum(--robot);
			System.out.println("������ʹ�óɹ���ʣ���������Ϊ��"+player.getRobotNum());
		}else{
			System.out.println("��Ǹ����û�л����ˣ�");
		}
	}
	
	//��ʾ�Լ��ʲ���Ϣ
	public static void query(Player player){
		int cash = player.getCash();
		int point = player.getPoint();
		int blockNum = player.getBlockNum();
		int bombNum = player.getBombNum();
		int robot = player.getRobotNum();
		List<House> property = player.getProperty();
		StringBuilder sb = new StringBuilder();
		String hp = null;
		for(House h:property){
			int position = h.getPosition();
			int price = h.getPrice()*2;
			hp = position+":"+price+"  ";
			sb.append(hp);
		}
		System.out.println(player.getName()+",���������������ʲ���");
		System.out.println("�����ֽ�"+cash);
		System.out.println("���ĵ�����"+point);
		System.out.println("���ĵ��ߣ�"+"·�ϣ�"+blockNum+" ը����"+bombNum+" �����ˣ�"+robot);
		if(sb.length()!=0)
			System.out.println("���ķ����У�"+sb.toString());
		else
			System.out.println("��û�з���");
	}
	
	//Help����  �鿴�������
	public static void help(){
		System.out.println("����		����˵��							����˵��");
		System.out.println("Roll		�������������1-6��������������㷨������ ");
		System.out.println("Sell n		�ֵ����ʱ���ɳ����Լ������ⷿ�������ۼ۸�ΪͶ����		n Ϊ�����ڵ�ͼ�ϵľ���λ��");
		System.out.println("		�ɱ���2����		");
		System.out.println("Block n		��ҿɽ�·�Ϸ��õ��뵱ǰλ�õ�ǰ��10�����κ�λ�ã�		nΪǰ�����Ծ��룬������ʾ�󷽡�");
		System.out.println("		��һ��Ҿ���·�ϣ��������ء��õ���һ����Ч��		");
		System.out.println("Bomb n		��ҿɽ�·�Ϸ��õ��뵱ǰλ��ǰ��10��������λ�ã���һ	nΪǰ�����Ծ��룬������ʾ�󷽡�");
		System.out.println("		��Ҿ���·�ϣ�����ը�ˣ�����ҽԺ��סԺ���졣		");
		System.out.println("Robot		ʹ�øõ��ߣ�����ɨǰ��·����10���ڵ��κ��������ߣ���");
		System.out.println("		ը����·�ϡ�");
		System.out.println("Query		��ʾ�Լ��ʲ���Ϣ");
		System.out.println("Help		�鿴�������");
		System.out.println("quit		ǿ���˳�");
		System.out.println("Step n		ң������							nΪָ���Ĳ���");
	}
	
	//ǿ���˳�
	public static void quit(){
		System.out.println("��Ϸ����");
		System.exit(0);
	}
	
	//ң������
	public static int step(int n){
		return n;
	}
}
