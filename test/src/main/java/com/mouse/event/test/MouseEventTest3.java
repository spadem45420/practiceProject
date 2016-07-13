package com.mouse.event.test;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class MouseEventTest3 implements NativeKeyListener {
	
	/**
	 * 休息時間(分)
	 */
	private int breakTime = 5;
	
	/**
	 * 工作時間(分)
	 */
	private int workTime = 60;
	
	/**
	 * 目前休息時間(秒)
	 */
	private int breakTimeTemp = 0;
	
	/**
	 * 目前工作時間(秒)
	 */
	private int workTimeTemp = 0;
	
	/**
	 * 中斷點
	 */
	private boolean breakPoint = true;
	
	public void mouseListener() {
		
		//啟動keyboard監控
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e1) {
			e1.printStackTrace();
		}
		GlobalScreen.addNativeKeyListener(this);
		
		//開始計算工作時間
		this.workCycle();
		
		Point pointTemp = new Point(0, 0);
		
		//Infinite roop
		while(true){
			
			//Point init
			Point point = new Point(0, 0);
			
			//Get mouse point
			point = this.getMousePoint();
			//Print point
			this.printPoint(point);
			
			//判斷取得座標與暫存座標是否相同
			//若相同表示滑鼠已經停止活動
			A:while(isSamePoint(point, pointTemp)){
				//FIXME 計算休息時間，之後想用多執行緒處理(?
				while(breakTimeTemp < breakTime * 60){
					
					if(breakTimeTemp == 0){
						System.out.println("開始休息");
					}
					
					//計算休息前先判斷滑鼠是否移動
					if(!isSamePoint(this.getMousePoint(), pointTemp)){
						System.out.println("休息終止");
						
						//休息時間歸零
						System.out.println("休息時數歸零");
						breakTimeTemp = 0;
						break A;
					}
					
					try {
						System.out.println("已經休息了" + breakTimeTemp + "秒鐘");
						Thread.sleep(1000);
						breakTimeTemp++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				//達到休息時數歸零目前工作時間
				if(breakTimeTemp == breakTime * 60){
					System.out.println("已達休息時數，工作時間歸零"); //只印一次
					JOptionPane.showMessageDialog(null, "休息時間結束", "警告", JOptionPane.INFORMATION_MESSAGE );
				}
				this.workTimeTemp = 0;
				
				//如果超過休息時間還未回，則繼續計算休息時間
				if(isSamePoint(this.getMousePoint(), pointTemp)){
					try {
						System.out.println("已經休息了" + breakTimeTemp + "秒鐘");
						Thread.sleep(1000);
						breakTimeTemp++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else{
					//休息時間歸零
					System.out.println("休息時數歸零");
					breakTimeTemp = 0;
				}
			}
			
			//暫存座標
			pointTemp.setLocation(point.getX(), point.getY());
			
			//中斷
			if(!breakPoint){
				break;
			}
			
		}
	}
	
	private void isBreak() {
		
		//如果目前工作時間達到規定工作時間則跳出警告
		if(workTimeTemp == workTime * 60){
			try {
				Thread.sleep(1000);
				System.out.println("###############################################################");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("#休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了休息時間到了#");
				System.out.println("###############################################################");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, "休息時間到了", "警告", JOptionPane.INFORMATION_MESSAGE );
		}
		
		//每5分鐘警告一次提醒休息
//		if(workTimeTemp > workTime * 60 && ((workTime * 60) - workTimeTemp)%60 == 0 && breakTimeTemp == 0){
//			JOptionPane.showMessageDialog(null, "休息時間到了，快點休息阿", "警告", JOptionPane.INFORMATION_MESSAGE );
//		}
		
		//超過工作時間瘋狂警告
		if(workTimeTemp > workTime * 60 && breakTimeTemp == 0){
			JOptionPane.showMessageDialog(null, "休息時間到了，快點休息阿", "警告", JOptionPane.INFORMATION_MESSAGE );
		}
		
	}
	
	/**
	 * 印出當下滑鼠座標
	 */
	private void printPoint(Point point) {
		System.out.println(this.workTimeTemp + " (" + point.getX() + ", " + point.getY() + ")");
	}
	
	/**
	 * 取得當下滑鼠座標
	 */
	private Point getMousePoint() {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		return pointerInfo.getLocation();
	}
	
	/**
	 * 判斷座標是否相同
	 */
	private boolean isSamePoint(Point point1, Point point2) {
		if(point1.getX() == point2.getX() && point1.getY() == point2.getY() && breakPoint){
			return true;
		}
		return false;
	}
	
	/**
	 * 計算工作時間
	 */
	private void workCycle() {
		
		//實作Runnable
		Runnable run = new Runnable() {
			
			public void run() {
				try {
					while(true){
						Thread.sleep(1000);
						workTimeTemp++;
						isBreak();
						
						if(!breakPoint){
							break;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		};
		
		Thread thread = new Thread(run);
		thread.start();
	}
	
	public static void main(String[] args) throws Exception {
		new MouseEventTest3().mouseListener();
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
//		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		System.out.println("按鍵[" + NativeKeyEvent.getKeyText(e.getKeyCode()) + "]被觸發");
		
		System.out.println("休息時數歸零");
		breakTimeTemp = 0;

        /* Terminate program when one press ESCAPE */
        if (e.getKeyCode() == NativeKeyEvent.VC_F10) {
        	System.out.println("程式結束");
            try {
				GlobalScreen.unregisterNativeHook();
				breakPoint = false;
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
            JOptionPane.showMessageDialog(null, "程式結束", "警告", JOptionPane.INFORMATION_MESSAGE );
        }
	}

	public void nativeKeyReleased(NativeKeyEvent paramNativeKeyEvent) {
		
	}

	public void nativeKeyTyped(NativeKeyEvent paramNativeKeyEvent) {
		
	}
}