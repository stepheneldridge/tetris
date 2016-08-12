package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JPanel;

public class TetrisPanel extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	private TetrisPiece current;
	private final Dimension tileSize = new Dimension(50,50);
	private final int speed = tileSize.height;
	private static byte cleanStage = 0;
	private boolean needToClean = false;
	private boolean[] full = new boolean[18];
	private int counter = 0;
	private int counterMax = 10;
	private Random rand;
	private boolean isPlaying = false;
	private static HashMap<Point,TetrisBlock> board = new HashMap<Point,TetrisBlock>();
	private int totalLines = 0;
	
	public TetrisPanel(){
		TetrisBlock.setSize(tileSize);
		setPreferredSize(TetrisGame.getDimensions());
		addKeyListener(this);
		setFocusable(true);
		setBackground(Color.lightGray);
		this.setFont(new Font(this.getFont().getFontName(),Font.PLAIN, 50));
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		isPlaying = true;
		spawnPiece();
	}
	private void spawnPiece(){
		current=new TetrisPiece(250,0,PieceType.values()[rand.nextInt(PieceType.values().length)]);
		if(!current.tryMove(0,0)){
			endGame();
		}
	}
	private void endGame(){
		isPlaying = false;
		current = null;
	}
	private void deactivate(TetrisPiece p){
		p.deactivate();
		for(TetrisBlock t: p.getSet()){
			board.put(new Point(t.getX(),t.getY()), t);
		}
	}
	private void checkRows(){
		for(int i = 0; i<18; i++){
			for(int j = 0; j<10; j++){
				if(isBlockAt(j*speed,i*speed)){
					full[i]=true;
				}else{
					full[i]=false;
					break;
				}
			}
			if(full[i])needToClean=true;
		}
		for(int i = 0; i<full.length; i++){
			if(full[i]){
				for(int j = 0; j<10; j++){
					board.get(new Point(j*50,i*50)).die();
				}
			}
		}
	}
	public static boolean isBlockAt(int x, int y){
		if(x>=TetrisGame.getDimensions().width||x<0||y>=TetrisGame.getDimensions().height){
			return true;
		}
		TetrisBlock a = board.get(new Point(x,y));
		if(a!=null)return true;
		return false;
	}
	
	public void update(){
		if(!isPlaying){
			
		}else if(needToClean){
			clean();
		}else if(counter>=counterMax-Math.floor(totalLines/10)){
			if(!current.tryMove(0,speed)){
				deactivate(current);
				spawnPiece();
			}
			checkRows();
			counter=0;
		}else{
			counter++;
		}
		repaint();
	}
	
	private void clean(){
		cleanStage++;
		int shift = 0;
		if(cleanStage>=9){
			for(int i = full.length-1; i>0; i--){
				if(full[i]){
					shift++;
					for(int j = 0; j<10; j++){
						board.remove(new Point(j*50,i*50));
					}
				}else if(shift>0){
					for(int j = 0;j<10; j++){
						Point key = new Point(j*TetrisBlock.getWidth(),i*TetrisBlock.getWidth());
						TetrisBlock b = board.get(key);
						if(b!=null){
							b.move(0, TetrisBlock.getWidth()*shift);
							board.remove(key);
							key.translate(0, TetrisBlock.getWidth()*shift);
							board.put(key, b);
						}
					}
				}
			}
			totalLines += shift;
			TetrisGame.addScore(100*shift*shift);
			if(board.isEmpty())TetrisGame.addScore(500);
			cleanStage=0;
			needToClean=false;
		}
	}
	public static Color deathColor(){
		if(cleanStage%2==0){
			return Color.WHITE;//clear
		}else{
			return Color.gray;
		}
	}
	public void paint(Graphics g){
		
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if(current!=null)current.draw(g);
		for(TetrisBlock t: board.values()){
			t.draw(g);
		}
		g.setColor(Color.black);
		g.setFont(this.getFont());
		g.drawString(TetrisGame.getScore().toString(), 250, 250);
		g.drawString("Lines: "+totalLines, 250, 400);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			TetrisGame.stopRunning();
		}
		if(isPlaying){
			if(e.getKeyChar()=='a'||e.getKeyCode()==KeyEvent.VK_LEFT){
				current.tryMove(-speed,0);
			}
			if(e.getKeyChar()=='d'||e.getKeyCode()==KeyEvent.VK_RIGHT){
				current.tryMove(speed,0);
			}
			if(e.getKeyChar()=='w'||e.getKeyCode()==KeyEvent.VK_UP){
				current.rotate();
			}
			if(e.getKeyChar()=='s'||e.getKeyCode()==KeyEvent.VK_DOWN){
				if(!current.tryMove(0,2*speed)){
					current.tryMove(0,speed);
				}
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}