package cn.blcu.destroysquare;

/**
 * ∑ΩøÈ¿‡
 * 
 * @author Administrator
 * 
 */
public class Square {
	private int color;
	private boolean selected;
	private int x, y;

	public Square(int x, int y, int color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.selected = false;
	}

	public int GetColor() {
		return this.color;
	}

	public int GetX() {
		return this.x;
	}

	public int GetY() {
		return this.y;
	}

	public boolean isSelected() {
		return selected;
	}

	public void SetColor(int color) {
		this.color = color;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void toggleSelected() {
		this.selected = !this.selected;
	}
}
