package per.rick.test_curriculum.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.activity.CourseInfoActivity;
import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.entity.Course;
import per.rick.test_curriculum.widget.CourseButton;

/**
 * 课程控制器
 * Created by Rick on 2016/4/11.
 */
public class CourseController {
	private CurriculumData data;// 课程表数据对象
	public static int[] normalState = new int[]{};
	public static int[] pressState = new int[]{android.R.attr.state_pressed,
			android.R.attr.state_enabled};
	public static int[] disableState = new int[]{-android.R.attr.state_enabled};
	public static int[] selectedState = new int[]{android.R.attr.state_selected,
			android.R.attr.state_enabled};

	/**
	 * 构造方法
	 *
	 * @param context 上下文对象
	 */
	public CourseController(Context context) {
		// 获取课程数据
		data = CurriculumData.getInstance(context);
	}

	/**
	 * 计算课程所对应的布局参数
	 *
	 * @param course 欲显示的课程
	 * @return 布局参数
	 */
	public RelativeLayout.LayoutParams getCourseVisualLayoutParams(
			Course course) {
		// 计算课程显示宽度
		int width = data.getPerWidth() - 2 * data.getCourseButtonMargin();
		// 计算课程显示高度
		int height = data.getPerHeight()
				* (course.getEndInterval() - course.getBeginInterval() + 1)
				- 2 * data.getCourseButtonMargin();
		// 计算左偏移值
		int marginLeft = (1 + course.getDayWeek()) * data.getPerWidth()
				+ data.getCourseButtonMargin();
		// 计算上偏移值
		int marginTop = (course.getBeginInterval() - 1) * data.getPerHeight()
				+ data.getCourseButtonMargin();
		RelativeLayout.LayoutParams layoutParams =
				new RelativeLayout.LayoutParams(width, height);
		layoutParams.setMargins(marginLeft, marginTop, 0, 0);
		return layoutParams;
	}

	/**
	 * 将课程添加到课程表
	 *
	 * @param context       上下文对象
	 * @param layout        容纳课程View的布局对象
	 * @param course        欲显示的课程对象
	 * @param courseButtons 课程按钮链表对象
	 * @return 添加的结果
	 */
	public boolean addCourseToTable(final Context context,
									RelativeLayout layout, final Course course,
									List<CourseButton> courseButtons) {
		int idCode = getIdCode(course.getName());
		final CourseButton cb = new CourseButton(context);
		//设置课程按钮的各类显示参数
		cb.setTextSize(data.getCourseButtonTextSize());
		cb.setGravity(data.getCourseButtonGravity());
		cb.setSingleLine(false);
		cb.setMaxEms(data.getCourseButtonMaxEms());
		cb.setText(getCourseBriefInfo(course));
		cb.setTextColor(Color.WHITE);
		cb.setLayoutParams(this.getCourseVisualLayoutParams(course));
		cb.setPadding(
				data.getCourseButtonPaddingLeft(),
				data.getCourseButtonPaddingTop(),
				data.getCourseButtonPaddingRight(),
				data.getCourseButtonPaddingBottom()
		);
		// 设置按钮背景
		Drawable drawable = this.getCourseButtonBackground(
				getIdCode(course.getName()), data.getCourseButtonCorner(),
				data.getCourseButtonCorner(),
				data.getCourseButtonCorner(),
				data.getCourseButtonCorner());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			cb.setBackground(drawable);
		} else {
			cb.setBackgroundDrawable(drawable);
		}
		// 设置课程按钮监听器，打开课程信息查看Activity
		cb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				data.setCourseToShow(course);
				ComponentName comp = new ComponentName(context, CourseInfoActivity.class);
				Intent intent = new Intent();
				intent.setComponent(comp);
				context.startActivity(intent);
			}
		});
		course.setCourseButton(cb);
		courseButtons.add(cb);
		layout.addView(cb);
		return true;
	}

	/**
	 * 将课程信息导出为json数据
	 *
	 * @param course 欲导出信息的课程
	 * @return json对象
	 */

	public JSONObject courseToJSON(Course course) {
		JSONObject courseJSON = new JSONObject();
		try {
			courseJSON.put("name", course.getName());
			courseJSON.put("location", course.getLocation());
			courseJSON.put("teacher", course.getTeacher());
			courseJSON.put("dayWeek", course.getDayWeek());
			courseJSON.put("beginInterval", course.getBeginInterval());
			courseJSON.put("endInterval", course.getEndInterval());
			courseJSON.put("weeks", course.getWeeksBinary());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return courseJSON;
	}

	/**
	 * 由json数据获取课程对象
	 *
	 * @param courseJSON json对象
	 * @return 课程对象
	 */
	public Course jsonToCourse(JSONObject courseJSON) {
		Course course = new Course();
		try {
			course.setName(courseJSON.getString("name"));
			course.setLocation(courseJSON.getString("location"));
			course.setTeacher(courseJSON.getString("teacher"));
			course.setDayWeek(courseJSON.getInt("dayWeek"));
			course.setBeginInterval(courseJSON.getInt("beginInterval"));
			course.setEndInterval(courseJSON.getInt("endInterval"));
			course.setWeeksBinary(courseJSON.getInt("weeks"));
			int i;
			List<Integer> weeksTemp = new ArrayList<Integer>();
			// 周数二进制到数组的转换
			for (i = 0; i < data.getMaxWeekCount(); i++) {
				if ((course.getWeeksBinary() & (1 << i)) != 0) {
					weeksTemp.add(i + 1);
				}
			}
			if (weeksTemp.size() == 0) {
				course.setWeeks(null);
			} else {
				course.setWeeks(new int[weeksTemp.size()]);
				for (i = 0; i < course.getWeeks().length; i++) {
					course.getWeeks()[i] = weeksTemp.get(i);
				}
			}
			//weeksJSONArray = courseJSON.getJSONArray("weeks");
			//course.setWeeks(new int[weeksJSONArray.length()]);
			//for (int i = 0; i < weeksJSONArray.length(); i++) {
			//	course.getWeeks()[i] = weeksJSONArray.getInt(i);
			//}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return course;
	}

	/**
	 * 课程链表对象转化为json数据
	 *
	 * @param courses 课程链表对象
	 * @return json对象
	 */
	public JSONObject coursesToJSON(List<Course> courses) {
		JSONObject coursesJSON = new JSONObject();
		JSONArray coursesJSONArray = new JSONArray();
		try {
			for (int i = 0; i < courses.size(); i++) {
				coursesJSONArray.put(i, this.courseToJSON(courses.get(i)));
			}
			coursesJSON.put("courses", coursesJSONArray);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return coursesJSON;
	}

	/**
	 * json对象转换为课程链表对象
	 *
	 * @param coursesJSON json对象
	 * @return 课程链表
	 */
	public List<Course> jsonToCourses(JSONObject coursesJSON) {
		List<Course> courses = new ArrayList<Course>();
		try {
			JSONArray coursesJSONArray = coursesJSON.getJSONArray("courses");
			for (int i = 0; i < coursesJSONArray.length(); i++) {
				Course course = this.jsonToCourse(coursesJSONArray
						.getJSONObject(i));
				courses.add(course);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return courses;
	}

	/**
	 * 获取课程的简要信息，用以在课程按钮上显示
	 *
	 * @param course
	 * @return
	 */
	public String getCourseBriefInfo(Course course) {
		return course.getName() + "@" + course.getLocation();
	}

	public int getIdCode(String str) {
		byte[] temp = str.getBytes();
		int i, idCode = 0;
		for (i = 0; i < temp.length; i++) {
			idCode += temp[i];
		}
		return idCode;
	}

	/**
	 * 获取按钮背景
	 *
	 * @param idCode       按钮id
	 * @param cTopLeft     左上圆角半径
	 * @param cTopRight    右上圆角半径
	 * @param cBottomLeft  左下圆角半径
	 * @param cBottomRight 右下圆角半径
	 * @return 背景
	 */
	public Drawable getCourseButtonBackground(int idCode, float cTopLeft,
											  float cTopRight,
											  float cBottomLeft,
											  float cBottomRight) {
		float outRect[] = new float[]{cTopLeft, cTopLeft, cTopRight,
				cTopRight, cBottomRight, cBottomRight,
				cBottomLeft, cBottomLeft};
		// 获得状态画布
		StateListDrawable drawable = new StateListDrawable();
		// 获得一个圆角举行
		RoundRectShape rectShape = new RoundRectShape(outRect, null, null);
		ShapeDrawable pressedDrawable = new ShapeDrawable(rectShape);
		ShapeDrawable normalDrawable = new ShapeDrawable(rectShape);
		// 设置背景颜色
		pressedDrawable.getPaint().setColor(data.getCourseButtonColorsPressed()[
				idCode % data.getCourseButtonColorsPressed().length]);
		normalDrawable.getPaint().setColor(data.getCourseButtonColorsNormal()[
				idCode % data.getCourseButtonColorsNormal().length]);
		// 添加状态画布
		drawable.addState(pressState, pressedDrawable);
		drawable.addState(normalState, normalDrawable);
		return drawable;
	}
}
