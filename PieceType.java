package tetris;

import java.awt.Color;
import java.awt.Point;

public enum PieceType {
	LINE(Color.orange,new Point[]{new Point(-1,0),new Point(1,0),new Point(2,0),new Point(0,0)},
			new Point[]{new Point(0,-1),new Point(0,1),new Point(0,2),new Point(0,0)}),
	TEE(Color.yellow,new Point[]{new Point(-1,0),new Point(1,0),new Point(0,1),new Point(0,0)},
			new Point[]{new Point(-1,0),new Point(0,1),new Point(0,-1),new Point(0,0)},
			new Point[]{new Point(-1,0),new Point(1,0),new Point(0,-1),new Point(0,0)},
			new Point[]{new Point(1,0),new Point(0,1),new Point(0,-1),new Point(0,0)}),
	SQUARE(Color.red,new Point[]{new Point(1,1),new Point(1,0),new Point(0,1),new Point(0,0)}),
	RIGHT_S(Color.cyan,new Point[]{new Point(1,0),new Point(0,1),new Point(-1,1),new Point(0,0)},
			new Point[]{new Point(0,-1),new Point(1,0),new Point(1,1),new Point(0,0)}),
	LEFT_S(Color.green,new Point[]{new Point(-1,0),new Point(0,1),new Point(1,1),new Point(0,0)},
			new Point[]{new Point(1,-1),new Point(1,0),new Point(0,1),new Point(0,0)}),
	LEFT_L(Color.magenta,new Point[]{new Point(-1,0),new Point(1,0),new Point(1,1),new Point(0,0)},
			new Point[]{new Point(0,-1),new Point(0,1),new Point(-1,1),new Point(0,0)},
			new Point[]{new Point(-1,0),new Point(1,0),new Point(-1,-1),new Point(0,0)},
			new Point[]{new Point(0,-1),new Point(1,-1),new Point(0,1),new Point(0,0)}),
	RIGHT_L(Color.blue,new Point[]{new Point(-1,0),new Point(1,0),new Point(-1,1),new Point(0,0)},
			new Point[]{new Point(0,-1),new Point(0,1),new Point(-1,-1),new Point(0,0)},
			new Point[]{new Point(-1,0),new Point(1,0),new Point(1,-1),new Point(0,0)},
			new Point[]{new Point(0,-1),new Point(1,1),new Point(0,1),new Point(0,0)}),
	DOT(Color.gray,new Point[]{new Point(0,0)}),
	SMALL_LINE(Color.pink,new Point[]{new Point(0,0),new Point(1,0)},
			new Point[]{new Point(0,0),new Point(0,1)}),
	MEDIUM_LINE(Color.white,new Point[]{new Point(0,0),new Point(1,0),new Point(-1,0)},
			new Point[]{new Point(0,0),new Point(0,1),new Point(0,-1)}),
	CEE(new Color(200,100,50),new Point[]{new Point(0,0),new Point(1,0),new Point(0,1)},
			new Point[]{new Point(0,0),new Point(-1,0),new Point(0,1)},
			new Point[]{new Point(0,0),new Point(-1,0),new Point(0,-1)},
			new Point[]{new Point(0,0),new Point(1,0),new Point(0,-1)});
	private Color color;
	private Point[][] positions;
	private int states;
	private PieceType(Color c, Point[]... set){
		color=c;
		positions=set;
		states=set.length;
	}
	public int getStates(){
		return states;
	}
	public Point[] getPosition(int state){
		return positions[state];
	}
	public Color getColor(){
		return color;
	}
}