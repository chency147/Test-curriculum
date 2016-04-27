package per.rick.test_curriculum.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.controller.CourseController;
import per.rick.test_curriculum.entity.Course;
import per.rick.test_curriculum.entity.CurriculumItem;
import per.rick.test_curriculum.entity.Day;
import per.rick.test_curriculum.tool.DisplayUtil;

/**
 * 课程表数据
 * Created by Rick on 2016/4/11.
 */
public class CurriculumData {
	private static CurriculumData instance = null; // 单例
	private static Context context = null;// 上下文对象

	private List<Course> courses = null;// 课程链表
	private List<CurriculumItem> curriculumItems = null;// 课程表中item对象链表
	// 课程表中选中item的映射
	private Map<Integer, CurriculumItem> selectedCurriculumItems = null;
	private int day_course_count = 12;// 每天的节数
	private List<Day> days;// 日期链表
	private String[] str_week;// 周数字符串数组，用以显示周几
	private String[] str_beginInterval;// 开始节数字符串数组
	private String[] str_endInterval;// 开始节数字符串数组
	private String month;// 月份字符串对象
	private int maxWeekCount = 25;// 一学期的最大周数
	private int perWidth;// 课程表每个Item的宽度 单位：像素px
	private int perHeight;// 课程表每个Item的高度 单位：像素px
	private int[] modified_weeks = null;// 修改后的课程的周数
	private int[] courseButtonColorsNormal = null;// 课程按钮正常状态颜色
	private int[] courseButtonColorsPressed = null;// 课程按钮按下状态颜色
	private int courseButtonColorGrayNormal;// 非本周课程正常状态颜色
	private int courseButtonColorGrayPressed;// 非本周课程按下状态颜色
	private int courseButtonCorner = 0;// 课程按钮圆角半径 单位：px
	private int courseButtonCorner_dp = 5;// 课程按钮圆角半径 单位：dp
	private int courseButtonMargin = 0;// 课程按钮外边距 单位：px
	private int courseButtonMargin_dp = 2;// 课程按钮外边距 单位：dp

	private int tableColumnCount;// 课程表列数

	/* 课程按钮显示参数 BEGIN */
	private int perHeight_dp = 50;/// 课程表每个Item的宽度 单位：dp
	// 课程按钮的gravity参数
	private int courseButtonGravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
	private int courseButtonPaddingBasic_dp = 4;// 课程按钮的基础内边距 单位dp
	private int courseButtonPaddingBasic_px;// 课程按钮的基础内边距 单位：px
	private int courseButtonPaddingLeft;// 课程按钮左内边距
	private int courseButtonPaddingTop;// 课程按钮上内边距
	private int courseButtonPaddingRight;// 课程按钮右内边距
	private int courseButtonPaddingBottom;// 课程按钮下内边距
	private int courseButtonMaxEms = 6;// 课程按钮每行显示文字数
	private int courseButtonTextSize = 12;// 课程按钮字体大小
	private int courseButtonTextColor;// 课程按钮字体颜色
	/* 课程按钮显示参数 END */
	private Course courseToAdd = null;// 待添加的课程
	private Course courseToShow = null;// 待显示的课程
	private boolean isCourseToShowModified = false;// 课程是否被修改
	private boolean isCourseToShowDeleted = false;// 课程是否被删除

	private SharedPreferences preferences;// 共享存储对象
	private SharedPreferences.Editor editor;// 共享存储编辑对象

	/**
	 * 构造方法（私有）
	 *
	 * @param context 上下文对象
	 */
	private CurriculumData(Context context) {
		this.context = context;
		this.initData();
	}

	/**
	 * 获取单例
	 *
	 * @param context 上下文对象
	 * @return 课程表数据对象
	 */
	public static CurriculumData getInstance(Context context) {
		if (instance == null) {
			synchronized (CurriculumData.class) {// 保证线程安全
				if (instance == null) {
					instance = new CurriculumData(context);
				}
			}
		}
		return instance;
	}

	/**
	 * 获取单例（无参版，只能在单例初始化之后才能调用）
	 *
	 * @return 课程表数据对象
	 */
	public static CurriculumData getInstance() {
		return instance;
	}

	/* set and get methods BEGIN */
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<CurriculumItem> getCurriculumItems() {
		return curriculumItems;
	}

	public void setCurriculumItems(List<CurriculumItem> curriculumItems) {
		this.curriculumItems = curriculumItems;
	}

	public int getDay_course_count() {
		return day_course_count;
	}

	public void setDay_course_count(int day_course_count) {
		this.day_course_count = day_course_count;
	}

	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}

	public String[] getStr_week() {
		return str_week;
	}

	public void setStr_week(String[] str_week) {
		this.str_week = str_week;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getPerWidth() {
		return perWidth;
	}

	public void setPerWidth(int perWidth) {
		this.perWidth = perWidth;
	}

	public int getPerHeight() {
		return perHeight;
	}

	public void setPerHeight(int perHeight) {
		this.perHeight = perHeight;
	}

	public int getTableColumnCount() {
		return tableColumnCount;
	}

	public void setTableColumnCount(int tableColumnCount) {
		this.tableColumnCount = tableColumnCount;
	}

	public int getCourseButtonGravity() {
		return courseButtonGravity;
	}

	public void setCourseButtonGravity(int courseButtonGravity) {
		this.courseButtonGravity = courseButtonGravity;
	}

	public int getCourseButtonPaddingLeft() {
		return courseButtonPaddingLeft;
	}

	public void setCourseButtonPaddingLeft(int courseButtonPaddingLeft) {
		this.courseButtonPaddingLeft = courseButtonPaddingLeft;
	}

	public int getCourseButtonPaddingTop() {
		return courseButtonPaddingTop;
	}

	public void setCourseButtonPaddingTop(int courseButtonPaddingTop) {
		this.courseButtonPaddingTop = courseButtonPaddingTop;
	}

	public int getCourseButtonPaddingRight() {
		return courseButtonPaddingRight;
	}

	public void setCourseButtonPaddingRight(int courseButtonPaddingRight) {
		this.courseButtonPaddingRight = courseButtonPaddingRight;
	}

	public int getCourseButtonPaddingBottom() {
		return courseButtonPaddingBottom;
	}

	public void setCourseButtonPaddingBottom(int courseButtonPaddingBottom) {
		this.courseButtonPaddingBottom = courseButtonPaddingBottom;
	}

	public int getCourseButtonMaxEms() {
		return courseButtonMaxEms;
	}

	public void setCourseButtonMaxEms(int courseButtonMaxEms) {
		this.courseButtonMaxEms = courseButtonMaxEms;
	}

	public int getCourseButtonTextColor() {
		return courseButtonTextColor;
	}

	public void setCourseButtonTextColor(int courseButtonTextColor) {
		this.courseButtonTextColor = courseButtonTextColor;
	}

	public int getCourseButtonTextSize() {
		return courseButtonTextSize;
	}

	public void setCourseButtonTextSize(int courseButtonTextSize) {
		this.courseButtonTextSize = courseButtonTextSize;
	}

	public Map<Integer, CurriculumItem> getSelectedCurriculumItems() {
		return selectedCurriculumItems;
	}

	public void setSelectedCurriculumItems(Map<Integer, CurriculumItem> selectedCurriculumItems) {
		this.selectedCurriculumItems = selectedCurriculumItems;
	}

	public Course getCourseToAdd() {
		return courseToAdd;
	}

	public void setCourseToAdd(Course courseToAdd) {
		this.courseToAdd = courseToAdd;
	}

	public Course getCourseToShow() {
		return courseToShow;
	}

	public void setCourseToShow(Course courseToShow) {
		this.courseToShow = courseToShow;
	}

	public int getMaxWeekCount() {
		return maxWeekCount;
	}

	public void setMaxWeekCount(int maxWeekCount) {
		this.maxWeekCount = maxWeekCount;
	}

	public int[] getModified_weeks() {
		return modified_weeks;
	}

	public void setModified_weeks(int[] modified_weeks) {
		this.modified_weeks = modified_weeks;
	}

	public boolean isCourseToShowModified() {
		return isCourseToShowModified;
	}

	public void setCourseToShowModified(boolean courseToShowModified) {
		isCourseToShowModified = courseToShowModified;
	}

	public boolean isCourseToShowDeleted() {
		return isCourseToShowDeleted;
	}

	public void setCourseToShowDeleted(boolean courseToShowDeleted) {
		isCourseToShowDeleted = courseToShowDeleted;
	}

	public String[] getStr_beginInterval() {
		return str_beginInterval;
	}

	public void setStr_beginInterval(String[] str_beginInterval) {
		this.str_beginInterval = str_beginInterval;
	}

	public String[] getStr_endInterval() {
		return str_endInterval;
	}

	public void setStr_endInterval(String[] str_endInterval) {
		this.str_endInterval = str_endInterval;
	}

	public int[] getCourseButtonColorsNormal() {
		return courseButtonColorsNormal;
	}

	public void setCourseButtonColorsNormal(int[] courseButtonColorsNormal) {
		this.courseButtonColorsNormal = courseButtonColorsNormal;
	}

	public int[] getCourseButtonColorsPressed() {
		return courseButtonColorsPressed;
	}

	public void setCourseButtonColorsPressed(int[] courseButtonColorsPressed) {
		this.courseButtonColorsPressed = courseButtonColorsPressed;
	}

	public int getCourseButtonColorGrayNormal() {
		return courseButtonColorGrayNormal;
	}

	public void setCourseButtonColorGrayNormal(int courseButtonColorGrayNormal) {
		this.courseButtonColorGrayNormal = courseButtonColorGrayNormal;
	}

	public int getCourseButtonColorGrayPressed() {
		return courseButtonColorGrayPressed;
	}

	public void setCourseButtonColorGrayPressed(int courseButtonColorGrayPressed) {
		this.courseButtonColorGrayPressed = courseButtonColorGrayPressed;
	}

	public int getCourseButtonCorner() {
		return courseButtonCorner;
	}

	public void setCourseButtonCorner(int courseButtonCorner) {
		this.courseButtonCorner = courseButtonCorner;
	}

	public int getCourseButtonMargin() {
		return courseButtonMargin;
	}

	public void setCourseButtonMargin(int courseButtonMargin) {
		this.courseButtonMargin = courseButtonMargin;
	}
	/* set and get methods END */

	/**
	 * 初始化课程表数据
	 */
	private void initData() {
		// 初始化周数字符串数组
		str_week = context.getResources().getStringArray(R.array.str_week);
		courseButtonColorsNormal = context.getResources().getIntArray(
				R.array.colorsForCourseButtonNormal);
		courseButtonColorsPressed = context.getResources().getIntArray(
				R.array.colorsForCourseButtonPressed);
		courseButtonColorGrayNormal = ContextCompat.getColor(context,
				R.color.colorCourseButtonGrayNormal);
		courseButtonColorGrayPressed = ContextCompat.getColor(context,
				R.color.colorCourseButtonGrayPressed);
		tableColumnCount = str_week.length + 1;// 设置课程表列数
		/* 计算课程表每个Item的宽度和高度 BEGIN */
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(
				Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		perWidth = dm.widthPixels / tableColumnCount;
		perHeight = DisplayUtil.dip2px(context, perHeight_dp);
		/* 计算课程表每个Item的宽度和高度 END */
		// 初始化课程表Item链表
		curriculumItems = new ArrayList<CurriculumItem>();
		selectedCurriculumItems = new HashMap<Integer, CurriculumItem>();
		/* 初始化日期链表 BEGIN */
		days = new ArrayList<Day>();
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.SUNDAY);// 设置周日为每周第一天
		int fix = 1 + (-1) * cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, fix);
		month = (cal.get(Calendar.MONTH) + 1) + "月";
		int i = 0;
		do {
			Day day = new Day();
			day.setDay(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
			day.setDay_week(str_week[i]);
			days.add(day);
			cal.add(Calendar.DATE, 1);
			i++;
		} while (i < str_week.length);
		/* 初始化日期链表 END */
		// 初始化课程表Item链表
		for (i = 0; i < day_course_count * tableColumnCount; i++) {
			CurriculumItem curriculumItem = new CurriculumItem();
			curriculumItem.setInfo("");
			curriculumItems.add(curriculumItem);
		}
		// 初始化节数字符串数组
		str_beginInterval = new String[getDay_course_count()];
		str_endInterval = new String[getDay_course_count()];
		for (i = 0; i < getDay_course_count(); i++) {
			str_beginInterval[i] = "第 " + (i + 1) + " 节";
			str_endInterval[i] = "到 " + (i + 1) + " 节";
		}
		// 计算课程按钮内边距数据
		courseButtonPaddingBasic_px = DisplayUtil.dip2px(context,
				courseButtonPaddingBasic_dp);
		courseButtonPaddingLeft = courseButtonPaddingBasic_px;
		courseButtonPaddingTop = 2 * courseButtonPaddingBasic_px;
		courseButtonPaddingRight = courseButtonPaddingBasic_px;
		courseButtonPaddingBottom = 2 * courseButtonPaddingBasic_px;

		courseButtonCorner = DisplayUtil.dip2px(context, courseButtonCorner_dp);
		courseButtonMargin = DisplayUtil.dip2px(context, courseButtonMargin_dp);
	}

	/**
	 * 保存课程表数据
	 */
	public void saveData() {
		CourseController courseController = new CourseController(context);
		preferences = context.getSharedPreferences("rick-curriculum", Context.MODE_PRIVATE);
		editor = preferences.edit();
		editor.putString("coursesData", courseController.coursesToJSON(courses).toString());
		editor.commit();
	}

	/**
	 * 读取课程表数据
	 */
	public void getData() {
		CourseController courseController = new CourseController(context);
		preferences = context.getSharedPreferences("rick-curriculum", Context.MODE_PRIVATE);
		try {
			JSONObject coursesJSON = new JSONObject(preferences.getString("coursesData", ""));
			this.setCourses(courseController.jsonToCourses(coursesJSON));
		} catch (Exception e) {
			e.printStackTrace();
			this.courses = new ArrayList<Course>();
		}
	}
}
