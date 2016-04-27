package per.rick.test_curriculum.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import per.rick.test_curriculum.entity.Course;

/**
 * 课程按钮
 * Created by Rick on 2016/4/11.
 */
public class CourseButton extends Button {

	private Course course;// 对应的课程

	// set and get methods
	public CourseButton(Context context) {
		super(context);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			this.setStateListAnimator(null);
		}
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
