package per.rick.test_curriculum.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import per.rick.test_curriculum.entity.Course;

/**
 * Created by Rick on 2016/4/11.
 */
public class CourseButton extends Button {

	private Course course;

	public CourseButton(Context context) {
		super(context);
	}

	public CourseButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CourseButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@SuppressLint("NewApi")
	public CourseButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
