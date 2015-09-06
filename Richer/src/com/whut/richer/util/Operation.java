package com.whut.richer.util;

import com.whut.richer.bean.Ground;
import com.whut.richer.bean.Player;
import com.whut.richer.map.Map;

/*
 * ����������ʾ��Ҳ�������
 */
public class Operation {
	//���¶������λ��
	public static int real_position(int n){
		return n<70?n:(n-70);
	}
	//�ж�ǰ����·�Ƿ���·�ϣ��з��ؾ��룬��Ϊ0
	public static int exist_block(int n,Player player){
		int distance = n;
		int position = player.getPosition();
		Ground ground;
		for(int i = 0; i<= n; i++ ){
			ground = Map.map.get(real_position(position+i));
			if(ground.isBlock()){
				distance = i;
				System.out.println("ǰ��"+i+"������·�ϣ�ͣ��·�ϴ�");
				ground.setSymbol(ground.getPreSymbol());
				ground.setBlock(false);
				break;
			}
		}
		return distance;
	}
}
