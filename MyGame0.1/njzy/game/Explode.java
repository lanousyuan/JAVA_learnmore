package njzy.game;

import java.awt.Graphics;
import java.awt.Image;

/**
 * ·É»ú±¬Õ¨
 * @author lanousyuan
 *
 */
public class Explode {
	double x,y;
	static Image[] images=new Image[4];
	static {
		for(int i=0;i<4;i++) {
			images[i]=GameUtil.getImage("image/explode/e"+(i+1)+".png");
			images[i].getWidth(null);
		}
	}
	
	int count=0;
	
	public void draw(Graphics g) {
		if(count<=3) {
			g.drawImage(images[count],(int)x,(int)y,null);
			count++;
		}
	}
	
	public Explode(double x,double y){
		this.x=x;
		this.y=y;
	}

}
