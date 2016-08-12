package tetris;

import java.awt.Graphics;
import java.util.ArrayList;

public class TetrisPiece{
	private PieceType type;
	private int x,y,state;
	private ArrayList<TetrisBlock> set = new ArrayList<TetrisBlock>();
	
	public TetrisPiece(int x, int y, PieceType p) {
		type = p;
		this.x=x;
		this.y=y;
		state = 0;
		for(int i = 0; i<p.getPosition(state).length; i++){
			int tx = p.getPosition(state)[i].x*TetrisBlock.getWidth()+x;
			int ty = p.getPosition(state)[i].y*TetrisBlock.getWidth()+y;
			set.add(new TetrisBlock(tx,ty,p.getColor()));
		}
	}
	public void rotate(){
		next();
		for(int i = 0; i<type.getPosition(state).length; i++){
			int tx = type.getPosition(state)[i].x*TetrisBlock.getWidth()+x;
			int ty = type.getPosition(state)[i].y*TetrisBlock.getWidth()+y;
			if(TetrisPanel.isBlockAt(tx, ty)){
				previous();
				return;
			}
		}
		set.clear();
		for(int i = 0; i<type.getPosition(state).length; i++){
			int tx = type.getPosition(state)[i].x*TetrisBlock.getWidth()+x;
			int ty = type.getPosition(state)[i].y*TetrisBlock.getWidth()+y;
			set.add(new TetrisBlock(tx,ty,type.getColor()));
		}
	}
	public void next(){
		state=(state+1)%type.getStates();
	}
	public void previous(){
		state=(state+type.getStates()-1)%type.getStates();
	}
	public void draw(Graphics g){
		for(TetrisBlock b: set){
			b.draw(g);
		}
	}
	public boolean tryMove(int dx, int dy){
		for(TetrisBlock b: set){
			if(!b.canMove(dx, dy))return false;
		}
		move(dx,dy);
		return true;
	}
	public void move(int dx, int dy){
		x+=dx;
		y+=dy;
		for(TetrisBlock b: set){
			b.move(dx, dy);
		}
	}
	public void deactivate(){
		int score = 5*set.size()-(18-this.y/TetrisBlock.getWidth());
		TetrisGame.addScore((score>0)?score:0);
		for(TetrisBlock b: set){
			b.deactivate();
		}
	}
	public ArrayList<TetrisBlock> getSet(){
		return set;
	}
}