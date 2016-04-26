package per.rick.test_curriculum.entity;

/**
 * 日期类
 * Created by Rick on 2016/4/9.
 */
public class Day {
	private String day;// 日文本（显示几号）
	private String day_week;// 周文本（显示周几）

	// set and get methods
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDay_week() {
		return day_week;
	}

	public void setDay_week(String day_week) {
		this.day_week = day_week;
	}
}
