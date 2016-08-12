package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
public class TetrisBlock {
	private int x,y;
	private Color color;
	private static Dimension size;
	private final int boarder = 4;
	private boolean active = true;
	private boolean death = false;
	public TetrisBlock(int x, int y, Color c){
		setPosition(x,y);
		color=c;
	}
	public void draw(Graphics g){
		if(TetrisPanel.deathColor()==Color.gray&&death){
			g.clearRect(x, y, size.width, size.height);
			g.setColor(TetrisPanel.deathColor());
			g.fillRect(x, y, size.width, size.height);
		}else{
			g.setColor(Color.BLACK);
			g.fillRect(x, y, size.width, size.height);
			g.setColor(color);
			g.fillRect(x+boarder, y+boarder, size.width-2*boarder, size.height-2*boarder);
		}
	}
	public void die(){
		death = true;
	}
	public int getY() {
		return y;
	}
	public int getX() {
		return x;
	}
	public void deactivate(){
		active = false;
	}
	public boolean canMove(int dx, int dy){
		if(active&&!TetrisPanel.isBlockAt(x+dx, y+dy)){
			return true;
		}
		return false;
	}
	public void move(int dx, int dy){
		x+=dx;
		y+=dy;
	}
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public static void setSize(Dimension d){
		size=d;
	}
	public static int getWidth(){
		return size.width;
	}
}