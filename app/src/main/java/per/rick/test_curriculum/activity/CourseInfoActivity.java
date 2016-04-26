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

/**
 * 课程信息Activity
 * Created by Rick on 2016-4-26
 */
public class CourseInfoActivity extends AppCompatActivity {

	private CurriculumData data;// 课程表数据对象

	private TextView tv_title;// 页面标题
	private TextView tv_modify;// 修改字样
	private EditText et_course_name;// 课程名称编辑栏
	private EditText et_course_location;// 教室位置编辑栏
	private EditText et_course_teacher;// 教师名称编辑栏
	private EditText et_course_interval;// 上课节数编辑栏
	private EditText et_course_weeks;// 上课周数编辑栏
	private Button bt_delete;// 删除课程按钮
	private WeeksChooseListener weeksChooseListener;// 周数选择监听器
	private ConfirmDialogListener confirmDialogListener;// 确认框监听器
	private Course courseTemp;// 临时课程对象

	private int state = 0; //0查看课程信息，1修改课程信息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_info);
		data = CurriculumData.getInstance(this);// 获取课程数据
		state = 0;// 初始化页面为查看课程信息状态
		// 克隆当前被显示的课程信息
		courseTemp = (Course) data.getCourseToShow().clone();
		initViews();
		setWeeksChooseListener();
		setConfirmDialogListener();
		showInfo();
	}

	/**
	 * 初始化页面内各控件
	 */
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

	/**
	 * 将课程信息显示到页面控件中
	 */
	private void showInfo() {
		Course course = data.getCourseToShow();
		tv_title.setText(course.getName());
		et_course_name.setText(course.getName());
		et_course_location.setText(course.getLocation());
		et_course_teacher.setText(course.getTeacher());
		et_course_interval.setText(course.getIntervalString());
		et_course_weeks.setText(course.getWeeksString());
	}

	/**
	 * 修改课程信息
	 */
	public void modifyCourseInfo(View view) {
		// 当前状态为查看状态，则进入修改编辑状态
		if (state == 0) {
			tv_title.setText("修改课程信息");
			tv_modify.setText("确认修改");
			// 修改课程各信息编辑框为可输入
			et_course_name.setFocusableInTouchMode(true);
			et_course_location.setFocusableInTouchMode(true);
			et_course_teacher.setFocusableInTouchMode(true);
			et_course_interval.setFocusableInTouchMode(true);
			bt_delete.setVisibility(View.INVISIBLE);// 隐藏删除按钮
			state = 1;
		} else { //当前状态为修改信息状态，则确认修改
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
			bt_delete.setVisibility(View.VISIBLE);// 显示删除按钮
			data.setCourseToShowModified(true);// 设置课程已经被修改
			this.onBackPressed();
		}
	}

	/**
	 * 调出周数选择对话框
	 */
	public void chooseWeeks(View view) {
		if (state == 1) {
			WeeksChooseDialog.Builder builder =
					new WeeksChooseDialog.Builder(this, courseTemp,
							weeksChooseListener);
			builder.create().show();
		}
	}

	/**
	 * 设置周数选择对话框监听器
	 */
	public void setWeeksChooseListener() {
		weeksChooseListener = new WeeksChooseListener() {
			@Override
			public void refreshActivity(int[] weeks) {
				// 如果没有周被选中，那么不做任何操作
				if (data.getModified_weeks() == null) {
					return;
				}
				courseTemp.setWeeks(weeks);
				et_course_weeks.setText(
						courseTemp.getWeeksString());
			}
		};
	}

	/**
	 * 设置确认框监听器
	 */
	public void setConfirmDialogListener() {
		confirmDialogListener = new ConfirmDialogListener() {
			@Override
			public void doConfirm(boolean isSure) {
				// 如果选择确定操作，那么删除课程
				if (isSure) {
					deleteCourse();
				}
			}
		};
	}

	/**
	 * 返回按钮
	 */
	public void back(View view) {
		onBackPressed();
	}

	/**
	 * 删除课程
	 */
	public void deleteCourse() {
		data.setCourseToShowDeleted(true);
		this.onBackPressed();
	}

	/**
	 * 弹出删除确认选框
	 */
	public void showDeleteConfirmDialog(View view) {
		ConfirmDialog.Builder builder = new ConfirmDialog.Builder(this,
				"删除确认",
				"确定要删除 " + data.getCourseToShow().getName() + " 这门课程吗？"
		);
		builder.setConfirmDialogListener(confirmDialogListener);
		builder.create().show();
	}
}
