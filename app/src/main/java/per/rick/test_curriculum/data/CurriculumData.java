package per.rick.test_curriculum.data;

import android.content.Context;
import android.content.SharedPreferences;
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
 * Created by Rick on 2016/4/11.
 */
public class CurriculumData {
	private static CurriculumData instance = null; // 单例
	private Context context;

	private List<Course> courses = null;
	private List<CurriculumItem> curriculumItems = null;
	private Map<Integer, CurriculumItem> selectedCurriculumItems = null;
	private int day_course_count = 12;
	private List<Day> days;
	private String[] str_week;
	private String month;
	private int maxWeekCount = 25;
	private int perWidth;// 单位：像素px
	private int perHeight;// 单位：像素px
	private int[] modified_weeks = null;

	private int tableColumnCount;
	private int perHeight_dp = 50;
	private int courseButtonGravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
	private int courseButtonPaddingBasic_dp = 4;
	private int courseButtonPaddingBasic_px;
	private int courseButtonPaddingLeft;
	private int courseButtonPaddingTop;
	private int courseButtonPaddingRight;
	private int courseButtonPaddingBottom;
	private int courseButtonMaxEms = 6;
	private int courseButtonTextSize = 12;
	private int courseButtonTextColor;
	private Course courseToAdd = null;
	private Course courseToShow = null;
	private boolean isCourseToShowModified = false;
	private boolean isCourseToShowDeleted = false;

	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;

	private CurriculumData(Context context) {
		this.context = context;
		this.initData();
	}

	public static CurriculumData getInstance(Context context) { // 获取单例
		if (instance == null) {
			synchronized (CurriculumData.class) {
				if (instance == null) {
					instance = new CurriculumData(context);
				}
			}
		}
		return instance;
	}

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

	private void initData() {
		str_week = context.getResources().getStringArray(R.array.str_week);
		tableColumnCount = str_week.length + 1;
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		perWidth = dm.widthPixels / tableColumnCount;
		perHeight = DisplayUtil.dip2px(context, perHeight_dp);
		curriculumItems = new ArrayList<CurriculumItem>();
		selectedCurriculumItems = new HashMap<Integer, CurriculumItem>();
		days = new ArrayList<Day>();
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
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
		for (i = 0; i < day_course_count * tableColumnCount; i++) {
			CurriculumItem curriculumItem = new CurriculumItem();
			curriculumItem.setInfo("");
			curriculumItems.add(curriculumItem);
		}
		courseButtonPaddingBasic_px = DisplayUtil.dip2px(context,
				courseButtonPaddingBasic_dp);
		courseButtonPaddingLeft = courseButtonPaddingBasic_px;
		courseButtonPaddingTop = 2 * courseButtonPaddingBasic_px;
		courseButtonPaddingRight = courseButtonPaddingBasic_px;
		courseButtonPaddingBottom = 2 * courseButtonPaddingBasic_px;
	}

	public void saveData() {
		CourseController courseController = new CourseController(context);
		preferences = context.getSharedPreferences("rick-curriculum", Context.MODE_PRIVATE);
		editor = preferences.edit();
		editor.putString("coursesData", courseController.coursesToJSON(courses).toString());
		editor.commit();
	}

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