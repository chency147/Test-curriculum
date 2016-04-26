package per.rick.test_curriculum.entity;

/**
 * 周数类
 * Created by Rick on 2016/4/15.
 */
public class Week {
	private int num;// 第几周
	private boolean isSelected = false;// 是否被选中

	// set and get methods
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}
}
