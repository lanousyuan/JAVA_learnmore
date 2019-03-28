package njzy.game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;


/**
 * 飞机游戏主窗口
 * @author lanousyuan
 *
 */

public class MyGameFrame extends Frame{
	
	//双缓冲技术解决闪烁问题
	private Image offScreenImage=null;
	public void update(Graphics g) {
		if(offScreenImage==null) {
			offScreenImage=this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		}
		Graphics gOff=offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	//读取图片
	Image background = GameUtil.getImage("images/blue.jpg");
	Image plane = GameUtil.getImage("images/plane.png");
	
	Plane p=new Plane(plane,250,250);
	Shell[] shells=new Shell[50];
	
	Date startTime=new Date();
	Date endTime;
	int period;//游戏持续的时间
	
	public void paint(Graphics g) {//自动被调用，g等于一支画笔
		
		Color c=g.getColor();
		
		super.paint(g);
		g.drawImage(background, 0, 0, null);
		
		p.drawSelf(g);//画飞机
		//画出所有炮弹
		for(int i=0;i<shells.length;i++) {
			shells[i].draw(g);
			
			if(shells[i].getRect().intersects(p.getRect())) {
				p.live=false;
				
			}
		}
		
		if(!p.live) {
			endTime=new Date();
			period=(int)((endTime.getTime()-startTime.getTime())/1000);
			g.setColor(Color.RED);
			Font f =new Font("宋体",Font.BOLD,25);
			g.setFont(f);
			g.drawString("游戏时间："+period+"秒", 200, 200);
			
		}

		g.setColor(c);
		
	}
	
	class PaintThread extends Thread{
		@Override
		public void run() {
			
			super.run();
			while(true) {
				repaint();//重画
				
				try {
					Thread.sleep(40);//1s画25次
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//键盘监听的内部类
	class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {			
			super.keyPressed(e);
			p.addDirection(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			super.keyReleased(e);
			p.minusDirection(e);
		}
		
		
		
	}
	
	
	//初始化窗口
	public void launchFrame() {
		this.setTitle("lanousyuan的飞机小游戏");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(350, 350);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//启动线程
		new PaintThread().start();//启动重画窗口的线程
		addKeyListener(new KeyMonitor());//给窗口增加键盘的监听
		
		//初始化50个球
		for(int i=0;i<shells.length;i++) {
			shells[i]=new Shell();
		}
		
	}
	
	public static void main(String[] args) {
		MyGameFrame gf=new MyGameFrame();
		gf.launchFrame();
	}

}
