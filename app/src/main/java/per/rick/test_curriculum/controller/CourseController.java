package per.rick.test_curriculum.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import per.rick.test_curriculum.activity.CourseInfoActivity;
import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.entity.Course;
import per.rick.test_curriculum.widget.CourseButton;

/**
 * Created by Rick on 2016/4/11.
 */
public class CourseController {
	private CurriculumData data;

	public CourseController(Context context) {
		data = CurriculumData.getInstance(context);

	}

	public RelativeLayout.LayoutParams getCourseVisualLayoutParams(
			Course course) {
		int width = data.getPerWidth();
		int height = data.getPerHeight() * (course.getEndInterval() - course.getBeginInterval() + 1);
		int marginLeft = (1 + course.getDayWeek()) * data.getPerWidth();
		int marginTop = (course.getBeginInterval() - 1) * data.getPerHeight();
		RelativeLayout.LayoutParams layoutParams =
				new RelativeLayout.LayoutParams(width, height);
		layoutParams.setMargins(marginLeft, marginTop, 0, 0);
		return layoutParams;
	}

	public boolean addCourseToTable(final Context context,
									RelativeLayout layout, final Course course,
									List<CourseButton> courseButtons) {
		CourseButton cb = new CourseButton(context);
		cb.setTextSize(data.getCourseButtonTextSize());
		cb.setGravity(data.getCourseButtonGravity());
		cb.setSingleLine(false);
		cb.setMaxEms(data.getCourseButtonMaxEms());
		cb.setText(getCourseBriefInfo(course));
		cb.setLayoutParams(this.getCourseVisualLayoutParams(course));
		cb.setPadding(
				data.getCourseButtonPaddingLeft(),
				data.getCourseButtonPaddingTop(),
				data.getCourseButtonPaddingRight(),
				data.getCourseButtonPaddingBottom()
		);
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

	public JSONObject courseToJSON(Course course) {
		JSONObject courseJSON = new JSONObject();
		JSONArray weeksJSONArray = new JSONArray();
		try {
			courseJSON.put("name", course.getName());
			courseJSON.put("location", course.getLocation());
			courseJSON.put("teacher", course.getTeacher());
			courseJSON.put("dayWeek", course.getDayWeek());
			courseJSON.put("beginInterval", course.getBeginInterval());
			courseJSON.put("endInterval", course.getEndInterval());
			for (int i = 0; i < course.getWeeks().length; i++) {
				weeksJSONArray.put(i, course.getWeeks()[i]);
			}
			courseJSON.put("weeks", weeksJSONArray);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return courseJSON;
	}

	public Course jsonToCourse(JSONObject courseJSON) {
		Course course = new Course();
		JSONArray weeksJSONArray;
		try {
			course.setName(courseJSON.getString("name"));
			course.setLocation(courseJSON.getString("location"));
			course.setTeacher(courseJSON.getString("teacher"));
			course.setDayWeek(courseJSON.getInt("dayWeek"));
			course.setBeginInterval(courseJSON.getInt("beginInterval"));
			course.setEndInterval(courseJSON.getInt("endInterval"));
			weeksJSONArray = courseJSON.getJSONArray("weeks");
			course.setWeeks(new int[weeksJSONArray.length()]);
			for (int i = 0; i < weeksJSONArray.length(); i++) {
				course.getWeeks()[i] = weeksJSONArray.getInt(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return course;
	}

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

	public String getCourseBriefInfo(Course course) {
		return course.getName() + "@" + course.getLocation();
	}
}
