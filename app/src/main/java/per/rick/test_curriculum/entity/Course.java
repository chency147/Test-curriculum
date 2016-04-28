package per.rick.test_curriculum.entity;

import java.util.Arrays;

import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.widget.CourseButton;

/**
 * 课程类
 * Created by Rick on 2016/4/9.
 */
public class Course implements Cloneable {
	private String name;// 课程名称
	private String location;// 上课位置
	private String teacher;// 教师
	private int dayWeek = 0;// 周几上课
	private int beginInterval;// 开始上课节数
	private int endInterval;// 结束节数
	private int[] weeks = null;// 上课周数
	private int weeksBinary = 0;// 上课周数（二进制版）
	private CourseButton courseButton = null;// 对应的课程按钮
	private CurriculumData data = null;

	// set and get methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String tearcher) {
		this.teacher = tearcher;
	}

	public int getDayWeek() {
		return dayWeek;
	}

	public void setDayWeek(int dayWeek) {
		this.dayWeek = dayWeek;
	}

	public int getBeginInterval() {
		return beginInterval;
	}

	public void setBeginInterval(int beginInterval) {
		this.beginInterval = beginInterval;
	}

	public int getEndInterval() {
		return endInterval;
	}

	public void setEndInterval(int endInterval) {
		this.endInterval = endInterval;
	}

	public int[] getWeeks() {
		return weeks;
	}

	public void setWeeks(int[] weeks) {
		this.setWeeks(weeks, false);
	}

	public void setWeeks(int[] weeks, boolean isSetBinary) {
		this.weeks = weeks;
		if (!isSetBinary) {
			return;
		}
		int i;
		this.weeksBinary = 0;
		for (i = 0; i < this.weeks.length; i++) {
			weeksBinary = weeksBinary | (1 << (this.weeks[i] - 1));
		}
	}

	public int getWeeksBinary() {
		return weeksBinary;
	}

	public void setWeeksBinary(int weeksBinary) {
		this.weeksBinary = weeksBinary;
	}

	public String getIntervalString() {
		return "第" + (beginInterval == endInterval ?
				beginInterval : (beginInterval + "-" + endInterval)) + "节";
	}

	public CourseButton getCourseButton() {
		return courseButton;
	}

	public void setCourseButton(CourseButton courseButton) {
		this.courseButton = courseButton;
	}

	/**
	 * 获得周数的文本
	 *
	 * @return 周数文本
	 */
	public String getWeeksString() {
		if (weeks == null) {
			return "未设置周数";
		}
		if (weeks.length == 1) {
			return "第 " + weeks[0] + " 周";
		}
		Arrays.sort(weeks);
		int i, j = 0;
		int div[] = {
				-1, -1, -1, -1
		};
		int flag = 0;
		for (i = 0; i < weeks.length - 1; i++) {
			if (weeks[i] + 1 != weeks[i + 1]) {
				flag++;
				if (flag >= div.length) {
					break;
				}
			} else {
				div[flag] = i + 1;
			}
		}
		if (flag == 0) {
			return "第 " + weeks[0] + "-" + weeks[div[0]] + " 周";
		}
		if (flag == 1) {
			return "第 " + weeks[0]
					+ (div[0] == -1 ? "" : ("-" + weeks[div[0]]))
					+ " , " + weeks[div[0] == -1 ? 1 : div[0] + 1]
					+ (div[1] == -1 ? "" : ("-" + weeks[div[1]])) + " 周";
		}
		String result = "第";
		for (i = 0; i < weeks.length; i++) {
			result += " " + weeks[i];
		}
		result += " 周";
		return result;
	}

	/**
	 * 克隆
	 *
	 * @return 和自己一样的副本
	 */
	public Object clone() {
		Object object = null;
		try {
			object = (Course) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 获取用以显示用途的节数文本
	 *
	 * @return 节数文本
	 */
	public String getIntervalStringToShow() {
		if (data == null) {
			data = CurriculumData.getInstance();
		}
		return data.getStr_week()[dayWeek] + " " + this.getIntervalString();
	}

	public boolean isNeedToAttend(int week) {
		return (weeksBinary & (1 << (week - 1))) != 0;
	}
}
