package per.rick.test_curriculum.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.entity.Course;
import per.rick.test_curriculum.listener.ConfirmDialogListener;
import per.rick.test_curriculum.listener.WeeksChooseListener;
import per.rick.test_curriculum.widget.ConfirmDialog;
import per.rick.test_curriculum.widget.WeeksChooseDialog;

public class CourseInfoActivity extends AppCompatActivity {

	private CurriculumData data;

	private TextView tv_title;
	private TextView tv_modify;
	private EditText et_course_name;
	private EditText et_course_location;
	private EditText et_course_teacher;
	private EditText et_course_interval;
	private EditText et_course_weeks;
	private Button bt_delete;
	private WeeksChooseListener weeksChooseListener;
	private ConfirmDialogListener confirmDialogListener;

	private Course courseTemp;

	private int state = 0; //0查看课程信息，1修改课程信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);
		data = CurriculumData.getInstance(this);
		state = 0;
		courseTemp = (Course) data.getCourseToShow().clone();
		initViews();
		setWeeksChooseListener();
		setConfirmDialogListener();
		showInfo();
	}

	private void initViews() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_modify = (TextView) findViewById(R.id.tv_modify);
		et_course_name = (EditText) findViewById(R.id.et_course_name);
		et_course_location = (EditText) findViewById(R.id.et_course_location);
		et_course_teacher = (EditText) findViewById(R.id.et_course_teacher);
		et_course_interval = (EditText) findViewById(R.id.et_course_interval);
		et_course_weeks = (EditText) findViewById(R.id.et_course_weeks);
		bt_delete = (Button) findViewById(R.id.bt_delete);
	}

	private void showInfo() {
		Course course = data.getCourseToShow();
		tv_title.setText(course.getName());
		et_course_name.setText(course.getName());
		et_course_location.setText(course.getLocation());
		et_course_teacher.setText(course.getTeacher());
		et_course_interval.setText(course.getIntervalString());
		et_course_weeks.setText(course.getWeeksString());
	}


	public void modifyCourseInfo(View view) {
		if (state == 0) {
			tv_title.setText("修改课程信息");
			tv_modify.setText("确认修改");
			et_course_name.setFocusableInTouchMode(true);
			et_course_location.setFocusableInTouchMode(true);
			et_course_teacher.setFocusableInTouchMode(true);
			et_course_interval.setFocusableInTouchMode(true);
			bt_delete.setVisibility(View.INVISIBLE);
			state = 1;
		} else {
			data.getCourseToShow().setName(et_course_name.getText()
					.toString());
			data.getCourseToShow().setLocation(et_course_location.getText()
					.toString());
			data.getCourseToShow().setTeacher(et_course_teacher.getText()
					.toString());
			if (courseTemp.getWeeks() != null) {
				data.getCourseToShow().setWeeks(courseTemp.getWeeks());
			}
			// tv_title.setText(et_course_name.getText().toString());
			// tv_modify.setText("修改");
			// et_course_name.setFocusable(false);
			// et_course_location.setFocusable(false);
			// et_course_teacher.setFocusable(false);
			// et_course_interval.setFocusable(false);
			state = 0;
			bt_delete.setVisibility(View.VISIBLE);
			data.setCourseToShowModified(true);
			this.onBackPressed();
		}
	}

	public void chooseWeeks(View view) {
		if (state == 1) {
			WeeksChooseDialog.Builder builder =
					new WeeksChooseDialog.Builder(this, courseTemp,
							weeksChooseListener);
			builder.create().show();
		}
	}

	public void setWeeksChooseListener() {
		weeksChooseListener = new WeeksChooseListener() {
			@Override
			public void refreshActivity(int[] weeks) {
				if (data.getModified_weeks() == null) {
					return;
				}
				courseTemp.setWeeks(weeks);
				et_course_weeks.setText(
						courseTemp.getWeeksString());
			}
		};
	}

	public void setConfirmDialogListener() {
		confirmDialogListener = new ConfirmDialogListener() {
			@Override
			public void doConfirm(boolean isSure) {
				if (isSure) {
					deleteCourse();
				}
			}
		};
	}

	public void back(View view) {
		onBackPressed();
	}

	public void deleteCourse() {
		data.setCourseToShowDeleted(true);
		this.onBackPressed();
	}

	public void showDeleteConfirmDialog(View view) {
		ConfirmDialog.Builder builder = new ConfirmDialog.Builder(this,
				"删除确认",
				"确定要删除 " + data.getCourseToShow().getName() + " 这门课程吗？"
		);
		builder.setConfirmDialogListener(confirmDialogListener);
		builder.create().show();
	}
}
