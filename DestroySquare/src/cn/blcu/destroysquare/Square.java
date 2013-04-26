package cn.blcu.destroysquare;


/**
 * ∑ΩøÈ¿‡
 * @author Administrator
 *
 */
public class Square {
	private int x,y;
	private int color;
	private boolean selected;

	public Square(int x,int y,int color){
		this.x=x;
		this.y=y;
		this.color=color;
	}
	
	
	public int GetX(){
		return this.x;
	}
	public int GetY(){
		return this.y;
	}
	public void SetColor(int color){
		this.color=color;
	}
	public int GetColor(){
		return this.color;
	}
	public boolean isSelected(){
		return selected;
	}
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	public void toggleSelected(){
		this.selected = !this.selected;
	}
}
