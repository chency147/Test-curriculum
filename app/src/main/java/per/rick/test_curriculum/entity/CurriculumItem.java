package per.rick.test_curriculum.entity;

/**
 * 课程表Item类
 * Created by Rick on 2016/4/10.
 */
public class CurriculumItem {
	private String info;// 信息文本
	private boolean isSelected = false;// 是否被选中
	private int width;// 显示宽度
	private int height;// 显示高度

	// set and get methods
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
