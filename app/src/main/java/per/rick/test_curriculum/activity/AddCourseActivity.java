package per.rick.test_curriculum.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Iterator;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.entity.Course;
import per.rick.test_curriculum.listener.WeeksChooseListener;
import per.rick.test_curriculum.widget.WeeksChooseDialog;

public class AddCourseActivity extends AppCompatActivity {
	private EditText et_course_name;
	private EditText et_course_location;
	private EditText et_course_teacher;
	private EditText et_course_interval;
	private EditText et_course_weeks;

	private CurriculumData data;
	private int beginInterval = Integer.MAX_VALUE;
	private int endInterval = Integer.MIN_VALUE;
	private int dayWeek;
	private Course courseToAdd;
	private WeeksChooseListener weeksChooseListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_course);
		data = CurriculumData.getInstance(this);
		courseToAdd = new Course();
		setWeeksChooseListener();
		initAddCourseInfo();
	}

	protected void initAddCourseInfo() {
		et_course_name = (EditText) findViewById(R.id.et_course_name);
		et_course_location = (EditText) findViewById(R.id.et_course_location);
		et_course_teacher = (EditText) findViewById(R.id.et_course_teacher);
		et_course_interval = (EditText) findViewById(R.id.et_course_interval);
		et_course_weeks = (EditText) findViewById(R.id.et_course_weeks);
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
		dayWeek = position % data.getTableColumnCount() - 1;
		et_course_interval.setText(data.getStr_week()[dayWeek]
				+ "  第" +
				(beginInterval == endInterval ?
						beginInterval : (beginInterval + "-" + endInterval))
				+ "节");
		data.setModified_weeks(null);
	}

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

	public void chooseWeeks(View view) {
		WeeksChooseDialog.Builder builder =
				new WeeksChooseDialog.Builder(this, courseToAdd, weeksChooseListener);
		builder.create().show();
	}

	public void setWeeksChooseListener() {
		weeksChooseListener = new WeeksChooseListener() {
			@Override
			public void refreshActivity(int[] weeks) {
				if (data.getModified_weeks() == null) {
					return;
				}
				courseToAdd.setWeeks(weeks);
				et_course_weeks.setText(courseToAdd.getWeeksString());
			}
		};
	}

	public void back(View view) {
		onBackPressed();
	}
}
