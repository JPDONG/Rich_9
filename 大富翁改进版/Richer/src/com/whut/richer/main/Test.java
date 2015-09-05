package com.whut.richer.main;

import java.util.List;
import java.util.Scanner;

import com.whut.richer.bean.Ground;
import com.whut.richer.bean.House;
import com.whut.richer.bean.Player;
import com.whut.richer.map.Map;
import com.whut.richer.util.Control;
import com.whut.richer.util.Operation;

/*
 * ��������
 */
public class Test {
	private static int member;	//�������
	private static int alive_member;	//����������
	private static String[] names ={"","Ǯ����","������","��С��","�𱴱�"};//�������
	private static char[] symbols = {'Q','A','S','J'};//����ڵ�ͼ�ϵı�ʶ
	private static Player[] players;//��ǰ���
	private static Scanner input = new Scanner(System.in);
	private static int step;	//������Ĳ���
	public static void main(String[] args) {	
		init();
		new Map();
//		Map.printMap(Map.map);
//		Map.map.get(1).setBomb(true);
//		Map.map.get(1).setSymbol('@');
//		Map.map.get(2).setBomb(true);
//		Map.map.get(2).setSymbol('@');
//		Map.map.get(3).setBomb(true);
//		Map.map.get(3).setSymbol('@');
//		Map.map.get(4).setBomb(true);
//		Map.map.get(4).setSymbol('@');
//		Map.map.get(5).setBomb(true);
//		Map.map.get(5).setSymbol('@');
//		Map.map.get(6).setBomb(true);
//		Map.map.get(6).setSymbol('@');
		do{
			for(int i=0;i<member;i++){
				if(players[i].isAlive())
					ing(i);
			}
			System.out.println("......"+alive_member);
		}while(alive_member!=1);
	}
	
	/*
	 * ��ʼ����Ϸ
	 */
	public static void init(){
	
		System.out.print("��ѡ��2-4λ���ظ���ң�"
				+ "�����ż��ɣ�1.Ǯ���ˣ�2.��������3.��С����4.�𱴱���:");
		
		String m = input.nextLine();
		member = m.length();
		alive_member = member;
		players = new Player[member];
		if(member<2||member>4){
			System.out.println("�����������ԡ�");
			init();
			return;
		}
		System.out.print("������ҳ�ʼ�ʽ𣬷�Χ1000~50000��Ĭ��10000��:");
		String money = input.nextLine();
		for(int i=0; i<member; i++){
			players[i] = new Player();
			int id = Integer.parseInt(m.charAt(i)+"");
			players[i].setName(names[id]);		
			players[i].setSymbol(symbols[id-1]);
			if(money.trim().length()!=0){
				players[i].setCash(Integer.parseInt(money));
			}
		}
	}
	
	/*
	 * ��Ϸ����
	 */
	public static void ing(int i){
		//��ӡ��ͼ
		Map.printMap(Map.map);
		//��ǰ����Ƿ�����
		int stayBout = players[i].getStayBout();
		if(stayBout!=0){
			players[i].setStayBout(--stayBout);		
			System.out.println(players[i].getName()+"�������غ�,��������"+players[i].getStayBout()+"�غ�");
			return;
		}
		//ָ���������ָ��
		System.out.print(players[i].getName()+">:");
		//��ȡ����̨һ��
		String[] orders = input.nextLine().split(" ");
		String order = orders[0];
		if(orders.length>1){
			step = Integer.parseInt(orders[1].trim());			
		}
		if("sell".equalsIgnoreCase(order)){
			exist_step(i);
			Control.sell(step, players[i]);
			ing(i);
			return;
		}else if("block".equalsIgnoreCase(order)){
			exist_step(i);
			Control.block(step, players[i]);
			ing(i);
			return;
		}else if("bomb".equalsIgnoreCase(order)){
			exist_step(i);
			Control.bomb(step, players[i]);
			ing(i);
			return;
		}else if("step".equalsIgnoreCase(order)){
			exist_step(i);
			int n = Control.step(step);
			do_roll(i,n);
		}else if("robot".equalsIgnoreCase(order)){
			if(orders.length>1){
				error(i);
			}
			Control.robot(players[i]);
			ing(i);
			return;
		}else if("query".equalsIgnoreCase(order)){
			if(orders.length>1){
				error(i);
			}
			Control.query(players[i]);
			ing(i);
			return;
		}else if("help".equalsIgnoreCase(order)){
			if(orders.length>1){
				error(i);
			}
			Control.help();
			ing(i);
			return;
		}else if("quit".equalsIgnoreCase(order)){
			if(orders.length>1){
				error(i);
			}
			Control.quit();
		}else if("roll".equalsIgnoreCase(order)){
			if(orders.length>1){
				error(i);
			}
			//����Roll��������һ��1~6�������
			int n = Control.roll();
			do_roll(i,n);
		}else{
				error(i);
		}
		for(int j=0;j<member;j++){
			int cur_position = players[j].getPosition();
			Ground cur_ground = Map.map.get(cur_position);
			cur_ground.setSymbol(players[j].getSymbol());
		}		
	}
	
	public static void do_roll(int i,int n){
		//��ʾ���ӵ���ֵ
		System.out.println("���ӵĵ�����"+n);
		n = Operation.exist_block(n, players[i]);
		//��ȡ��ǰ��ҵ�ǰλ��			
		int current = players[i].getPosition();
		//��ȡ���Ŀ��λ�õı�ʶ
		Ground aim_ground = Map.map.get(Operation.real_position(n+current));	
		//����ߵ�Ŀ��λ��
		players[i].setPosition(Operation.real_position(n+current));
		//�ж�Ŀ��λ�õ����������Ӧ�Ĵ�����
		if(aim_ground.isBomb()){
			exist_bomb(aim_ground,i);
		}else if(aim_ground.isJail()){
			System.out.println("Ŀ��λ���Ǽ���");
			exist_jail(i);
		}else if(aim_ground.isGiftHouse()){
			System.out.println("Ŀ��λ������Ʒ��");
			exist_giftHouse(i);
		}else if(aim_ground.isTool()){
			System.out.println("��ӭ���ٵ����ݣ���ѡ��������Ҫ�ĵ���.");
//			if(players[i].getPoint()<50){
//				System.out.println("���ĵ������㣬�Զ��˳�");
//				return;
//			}			
			System.out.println("ͨ��������ߵı��ѡ����ߣ�ÿλ���������ӵ��10������");
			System.out.println("1.·�� 50��2.�������� 50��3.ը�� 50");
			System.out.println("�����ʽ����� ����������:1 2");
			System.out.println("��F���˳������ݡ�");
			exist_toolHouse(i);
		}else if(aim_ground.getMine()!=null){
			System.out.println("Ŀ��λ���ǿ��");
			exist_mine(aim_ground,i);
		}else if(aim_ground.getHouse()!=null){
			System.out.println("Ŀ��λ����һ������");
			exist_house(aim_ground,i);
		}else if(aim_ground.getPreSymbol()=='S'){
			System.out.println("�ص����");
		}else {
			System.out.println("Ŀ��λ���ǿյ�");
			exist_empty(aim_ground,i);
		}
		//�޸����Ŀ�ĵصı�ʶ
		aim_ground.setSymbol(players[i].getSymbol());
		//����ҵ�ǰλ�øĻ�֮ǰ��״̬
		Ground cur_ground = Map.map.get(current);
		cur_ground.setSymbol(cur_ground.getPreSymbol());
	}
	
	//�ж�Ŀ��λ����ը������
	public static void exist_bomb(Ground aim_ground,int i){
		players[i].setStayBout(3);
		players[i].setPosition(14);
		aim_ground.setSymbol(aim_ground.getPreSymbol());
		aim_ground.setBomb(false);
		System.out.println("���Ҳȵ�ը��,��ҽԺ��Ϣ3��");
		return;
	}
	
	
	//Ŀ��λ���Ǽ����Ĳ���
	public static void exist_jail(int i){
		players[i].setStayBout(2);
		System.out.println("��ץ�����2��");
		return;
	}
	
	//Ŀ��λ������Ʒ�ݵĲ���
	public static void exist_giftHouse(int i){
		System.out.println("��ӭ������Ʒ�ݣ���ѡ��һ����ϲ������Ʒ��");
		System.out.println("ͨ��������ѡ����Ʒ:1.����2.��������3.����");
		System.out.println("ֻ�ܺ�ѡ��һ����Ʒ��ѡ����Զ��˳���Ʒ�ݣ��������Ҳ�˳����͵������˴λ��ᣩ");
		String choose = input.nextLine();
		try{
			int c = Integer.parseInt(choose.trim());
			if(c==1){
				players[i].setCash(players[i].getCash()+2000);
			}else if(c==2){
				players[i].setPoint(players[i].getPoint()+200);
			}else if(c==3){
				players[i].setMammon(players[i].getMammon()+5);
			}else{
				System.out.println("�������,�����ʸ�");
				return;
			}
		}catch(Exception e){
			System.out.println("�������,�����ʸ�");
			return;
		}
	}
	
	//Ŀ��λ���ǵ����ݵĲ���
	public static void exist_toolHouse(int i){	
		if(players[i].getPoint()<50){
			System.out.println("���ĵ������㣬�Զ��˳�");
			return;
		}	
		int num = players[i].getBlockNum()+players[i].getBombNum()+players[i].getRobotNum();
		if(num<10){
			String in = input.nextLine();
			if("F".equalsIgnoreCase(in))
				return;
			String[] ins = in.split(" ");
			int biao = Integer.parseInt(ins[0].trim());
			int n = Integer.parseInt(ins[1].trim());
			if(num+n>10){
				System.out.println("��������������������Я���޶������Ч�����������롣");
				exist_toolHouse(i);
				return;
			}else{				
				int points = 50*n;
				if(points>players[i].getPoint()){
					System.out.println("���������Թ�����Ҫ��Ʒ����������������");
					exist_toolHouse(i);
					return;
				}else if(biao==1){
					players[i].setBlockNum(players[i].getBlockNum()+n);
					players[i].setPoint(players[i].getPoint()-points);
					System.out.println("�ѹ���·�Ͽ�"+n+"����������"+points+"����");
					System.out.println("����F�˳������������:");
					exist_toolHouse(i);
					return;
				}else if(biao==2){
					players[i].setRobotNum(players[i].getRobotNum()+n);
					players[i].setPoint(players[i].getPoint()-points);
					System.out.println("�ѹ���������޿�"+n+"����������"+points+"����");
					System.out.println("����F�˳������������:");
					exist_toolHouse(i);
					return;
				}else if(biao==3){
					players[i].setBombNum(players[i].getBombNum()+n);
					players[i].setPoint(players[i].getPoint()-points);
					System.out.println("�ѹ���ը����"+n+"����������"+points+"����");
					System.out.println("����F�˳������������:");
					exist_toolHouse(i);
					return;
				}else{
					System.out.println("������Ʒ��Ŵ�������������.");
					exist_toolHouse(i);
					return;
				}
			}
		}else{
			System.out.println("���ĵ��������������Զ��˳�");
			return;
		}			
	}
	
	//Ŀ��λ���ǿ�صĲ���
	public static void exist_mine(Ground aim_ground,int i){
		int points = aim_ground.getMine().getPoint();
		players[i].setPoint(players[i].getPoint()+points);
		System.out.println("·����أ���������"+points);
	} 
	
	//Ŀ��λ���Ƿ����Ĳ���
	public static void exist_house(Ground aim_ground,int i){
		House house = aim_ground.getHouse();
		if(house.getName().equals(players[i].getName())){
			if(house.getGrade()==3)
				System.out.println("�����ѵ���߼����޷�����");
			else{
				System.out.println("��ǰ�����ȼ�Ϊ"+house.getGrade()+" �Ƿ�������������Y/N)");
				String in = input.nextLine();
				if("y".equalsIgnoreCase(in.trim())){
					int price = aim_ground.getPrice();
					if(price>players[i].getCash())
						System.out.println("�ֽ����޷�����!");
					else{
						players[i].setCash(players[i].getCash()-price);
						house.setGrade(house.getGrade()+1);
						house.setPrice(house.getPrice()+price);
						aim_ground.setPreSymbol((char)(house.getGrade()+48));
						aim_ground.setSymbol((char)(house.getGrade()+48));
						System.out.println("���������ɹ�����ǰ�ȼ�Ϊ"+house.getGrade());
					}
				}
			}
		}else{
			int mammon = players[i].getMammon();
			if(mammon!=0){
				System.out.println("�����������·��,ʣ�����"+(--mammon));
				players[i].setMammon(--mammon);
			}else{
				int price = house.getPrice()/2;
				if(price>players[i].getCash()){
					System.out.println("��Ǹ���ֽ��㣬������Ϸ������");
					List<House> property = players[i].getProperty();
					for(House h :property){
						Ground ground =Map.map.get(h.getPosition());
						h.setPrice(0);
						h.setPosition(0);
						h.setGrade(0);
						ground.setPreSymbol('0');
						ground.setHouse(null);
					}
					players[i].setAlive(false);
					alive_member-=1;
				}else{
					players[i].setCash(players[i].getCash()-price);
					for(Player p : players){
						if(p.getName().equals(house.getName())){
							p.setCash(p.getCash()+price);
							break;
						}
					}
					System.out.println("·�����˷���,�ɷ�"+price);
				}
			}
		}
	}
	
	//Ŀ��λ���ǿյصĲ���
	public static void exist_empty(Ground aim_ground,int i){
		System.out.print("��λ���ǿ�յأ��Ƿ���Y/N��");
		String in = input.nextLine();
		if("y".equalsIgnoreCase(in.trim())){
			int price = aim_ground.getPrice();
			if(price>players[i].getCash()){
				System.out.println("�ֽ𲻹����޷�����");
				return;
			}else{
				players[i].setCash(players[i].getCash()-price);
				House h = new House();
				h.setPosition(aim_ground.getPosition());
				h.setPrice(price);
				h.setGrade(1);
				h.setName(players[i].getName());
				players[i].getProperty().add(h);
				aim_ground.setPreSymbol('1');
				aim_ground.setSymbol('1');
				aim_ground.setHouse(h);
				System.out.println("����ɹ���");
			}
		}
//		else
//			return;
	}
	//�ж����������ʽ
	public static void exist_step(int i){
		if(step==0){
			System.out.println("�����ʽ�д���������Ϊ0,������Help�����ѯ����������.");
			ing(i);
			return ;
		}
	}
	
	//��ʾ�����ʽ����
	public static void error(int i){
		System.out.println("�����ʽ�д���������Help�����ѯ����������.");
		ing(i);
		return;
	}
}