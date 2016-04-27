package per.rick.test_curriculum.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Iterator;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.entity.Course;
import per.rick.test_curriculum.listener.IntervalChooseListener;
import per.rick.test_curriculum.listener.WeeksChooseListener;
import per.rick.test_curriculum.widget.IntervalChooseDialog;
import per.rick.test_curriculum.widget.WeeksChooseDialog;

/**
 * 添加课程Activity
 * Created by Rick on 2016-4-26
 */
public class AddCourseActivity extends AppCompatActivity {
	private EditText et_course_name;// 课程名称编辑栏
	private EditText et_course_location;// 教室编辑栏
	private EditText et_course_teacher;// 教师编辑栏
	private EditText et_course_interval;// 课程节数编辑栏
	private EditText et_course_weeks;// 课程周数编辑栏

	private CurriculumData data;// 课程表数据对象
	private int beginInterval = Integer.MAX_VALUE;// 开始节数
	private int endInterval = Integer.MIN_VALUE;// 节数节数
	private int dayWeek;// 周几上课
	private Course courseToAdd;// 欲添加的课程对象
	private WeeksChooseListener weeksChooseListener;// 周数选择对话框监听器
	private IntervalChooseListener intervalChooseListener;//节数选择对话框监听器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_course);
		data = CurriculumData.getInstance(this);
		courseToAdd = new Course();// 初始化欲添加的课程对象
		setWeeksChooseListener();
		setIntervalChooseListener();
		initAddCourseInfo();
	}

	/**
	 * 初始化页面控件
	 */
	protected void initAddCourseInfo() {
		et_course_name = (EditText) findViewById(R.id.et_course_name);
		et_course_location = (EditText) findViewById(R.id.et_course_location);
		et_course_teacher = (EditText) findViewById(R.id.et_course_teacher);
		et_course_interval = (EditText) findViewById(R.id.et_course_interval);
		et_course_weeks = (EditText) findViewById(R.id.et_course_weeks);
		// 根据之前选择的节数来计算开始节数和结束节数
		Iterator<Integer> iterator = data.getSelectedCurriculumItems().keySet()
				.iterator();
		int position = 1;
		while (iterator.hasNext()) {
			position = iterator.next();
			int interval = position / data.getTableColumnCount() + 1;
			if (interval < beginInterval) {
				beginInterval = interval;
			}
			if (interval > endInterval) {
				endInterval = interval;
			}
		}
		// 获取周几上课
		dayWeek = position % data.getTableColumnCount() - 1;
		courseToAdd.setDayWeek(dayWeek);
		courseToAdd.setBeginInterval(beginInterval);
		courseToAdd.setEndInterval(endInterval);
		et_course_interval.setText(courseToAdd.getIntervalStringToShow());
		data.setModified_weeks(null);
	}

	/**
	 * 添加课程
	 */
	public void addCourse(View view) {
		courseToAdd.setName(et_course_name.getText().toString());
		courseToAdd.setTeacher(et_course_teacher.getText().toString());
		courseToAdd.setLocation(et_course_location.getText().toString());
		courseToAdd.setBeginInterval(beginInterval);
		courseToAdd.setEndInterval(endInterval);
		courseToAdd.setDayWeek(dayWeek);
		data.setCourseToAdd(courseToAdd);
		this.onBackPressed();
	}

	/**
	 * 弹出周数选择对话框
	 */
	public void chooseWeeks(View view) {
		WeeksChooseDialog.Builder builder =
				new WeeksChooseDialog.Builder(this, courseToAdd,
						weeksChooseListener);
		builder.create().show();
	}

	/**
	 * 调出节数选择对话框
	 */
	public void chooseIntervals(View view) {
		IntervalChooseDialog.Builder builder =
				new IntervalChooseDialog.Builder(this, courseToAdd);
		builder.setIntervalChooseListener(intervalChooseListener);
		builder.create().show();
	}

	/**
	 * 设置周数选择对话框监听器
	 */
	public void setWeeksChooseListener() {
		weeksChooseListener = new WeeksChooseListener() {
			@Override
			public void refreshActivity(int[] weeks) {
				// 如果没有周数选中，不采取任何操作
				if (data.getModified_weeks() == null) {
					return;
				}
				courseToAdd.setWeeks(weeks, true);
				et_course_weeks.setText(courseToAdd.getWeeksString());
			}
		};
	}

	/**
	 * 设置节数选择对话框监听器
	 */
	public void setIntervalChooseListener() {
		intervalChooseListener = new IntervalChooseListener() {
			@Override
			public void refreshActivity(
					int dayWeek, int beginInterval, int endInterval) {
				AddCourseActivity.this.dayWeek = dayWeek;
				AddCourseActivity.this.beginInterval = beginInterval;
				AddCourseActivity.this.endInterval = endInterval;
				courseToAdd.setDayWeek(dayWeek);
				courseToAdd.setBeginInterval(beginInterval);
				courseToAdd.setEndInterval(endInterval);
				et_course_interval.setText(courseToAdd
						.getIntervalStringToShow());
			}
		};
	}

	public void back(View view) {
		onBackPressed();
	}
}
