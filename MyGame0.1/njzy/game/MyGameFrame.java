package njzy.game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;


/**
 * �ɻ���Ϸ������
 * @author lanousyuan
 *
 */

public class MyGameFrame extends Frame{
	
	//˫���弼�������˸����
	private Image offScreenImage=null;
	public void update(Graphics g) {
		if(offScreenImage==null) {
			offScreenImage=this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
		}
		Graphics gOff=offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	//��ȡͼƬ
	Image background = GameUtil.getImage("images/blue.jpg");
	Image plane = GameUtil.getImage("images/plane.png");
	
	Plane p=new Plane(plane,250,250);
	Shell[] shells=new Shell[50];
	
	Date startTime=new Date();
	Date endTime;
	int period;//��Ϸ������ʱ��
	
	public void paint(Graphics g) {//�Զ������ã�g����һ֧����
		
		Color c=g.getColor();
		
		super.paint(g);
		g.drawImage(background, 0, 0, null);
		
		p.drawSelf(g);//���ɻ�
		//���������ڵ�
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
			Font f =new Font("����",Font.BOLD,25);
			g.setFont(f);
			g.drawString("��Ϸʱ�䣺"+period+"��", 200, 200);
			
		}

		g.setColor(c);
		
	}
	
	class PaintThread extends Thread{
		@Override
		public void run() {
			
			super.run();
			while(true) {
				repaint();//�ػ�
				
				try {
					Thread.sleep(40);//1s��25��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//���̼������ڲ���
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
	
	
	//��ʼ������
	public void launchFrame() {
		this.setTitle("lanousyuan�ķɻ�С��Ϸ");
		this.setVisible(true);
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(350, 350);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//�����߳�
		new PaintThread().start();//�����ػ����ڵ��߳�
		addKeyListener(new KeyMonitor());//���������Ӽ��̵ļ���
		
		//��ʼ��50����
		for(int i=0;i<shells.length;i++) {
			shells[i]=new Shell();
		}
		
	}
	
	public static void main(String[] args) {
		MyGameFrame gf=new MyGameFrame();
		gf.launchFrame();
	}

}
