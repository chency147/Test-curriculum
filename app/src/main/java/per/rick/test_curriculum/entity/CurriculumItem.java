package per.rick.test_curriculum.entity;

/**
 * Created by Rick on 2016/4/10.
 */
public class CurriculumItem {
	private String info;
	private boolean isSelected = false;
	private int width;
	private int height;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
}
