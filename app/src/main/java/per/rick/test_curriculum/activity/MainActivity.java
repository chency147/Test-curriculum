package per.rick.test_curriculum.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.adapter.CurriculumAdapter;
import per.rick.test_curriculum.adapter.DateAdapter;
import per.rick.test_curriculum.controller.CourseController;
import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.entity.Course;
import per.rick.test_curriculum.entity.CurriculumItem;
import per.rick.test_curriculum.widget.CourseButton;

/**
 * 主界面类
 * Created by Rick on 2016-4-26
 */
public class MainActivity extends AppCompatActivity {

	private RelativeLayout rl_course_table;// 防止课程表的相对布局
	private GridView gv_curriculum;// 课程表容器
	private GridView gv_date;// 课程表上方的日期显示容器
	private CurriculumData data;// 课程表数据
	private CourseController courseController;// 课程操作工具
	private List<CourseButton> courseButtons;// 保存课程按钮的链表
	DateAdapter dateAdapter;// 日期容器适配器
	CurriculumAdapter curriculumAdapter;// 课程表容器适配器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		data = CurriculumData.getInstance(this);// 获取课程表数据对象
		data.getData();// 初始化数据
		courseController = new CourseController(this);// 初始化课程操作工具对象
		courseButtons = new ArrayList<CourseButton>();// 初始化课程按钮链表
		this.initCurriculum();
		// 配置课程表点击事件，标记或者取消标记被点击的Item
		gv_curriculum.setOnItemClickListener(
				new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent,
											View view, int position, long id) {
						if (position % data.getTableColumnCount() != 0) {
							MainActivity.this.doSelectCurriculum(position);
							curriculumAdapter.notifyDataSetChanged();
						}
					}
				});
		// 将课程数据以按钮的形式放置在课程表相对布局中
		for (Course course : data.getCourses()) {
			courseController.addCourseToTable(this, rl_course_table,
					course, courseButtons);
		}
	}

	/**
	 * 初始化课程表各控件
	 */
	private void initCurriculum() {
		gv_curriculum = (GridView) findViewById(R.id.gv_curriculum);
		gv_date = (GridView) findViewById(R.id.gv_date);
		dateAdapter = new DateAdapter(this, data.getDays(), data.getMonth());
		curriculumAdapter = new CurriculumAdapter(this, data);
		gv_date.setAdapter(dateAdapter);
		gv_curriculum.setAdapter(curriculumAdapter);
		rl_course_table = (RelativeLayout) findViewById(R.id.rl_course_table);
	}

	/**
	 * 课程表点击事件
	 */
	private void doSelectCurriculum(int position) {
		CurriculumItem item;
		// 如果选中节已经被选中，且上下节都没有被选中，那么取消该节的选中
		if (data.getSelectedCurriculumItems().containsKey(position)) {
			if (!data.getSelectedCurriculumItems()
					.containsKey(position - data.getTableColumnCount())
					|| !data.getSelectedCurriculumItems()
					.containsKey(position + data.getTableColumnCount())) {
				item = data.getCurriculumItems().get(position);
				item.setSelected(false);
				data.getSelectedCurriculumItems().remove(position);
				return;
			}
		}
		// 如果目前没有任何节被选中，那么选中当前节
		if (data.getSelectedCurriculumItems().isEmpty()) {
			item = data.getCurriculumItems().get(position);
			item.setSelected(true);
			data.getSelectedCurriculumItems().put(position, item);
			return;
		}
		// 如果当前点击的节的上节或者下节有选中，那么选中该节
		if (data.getSelectedCurriculumItems()
				.containsKey(position - data.getTableColumnCount())
				|| data.getSelectedCurriculumItems()
				.containsKey(position + data.getTableColumnCount())) {
			item = data.getCurriculumItems().get(position);
			item.setSelected(true);
			data.getSelectedCurriculumItems().put(position, item);
		}
	}

	/**
	 * 开启添加课程的Activity
	 */
	public void startAddCourseActivity(View view) {
		// 如果有节选中那么就开启目标Activity
		if (data.getSelectedCurriculumItems().size() != 0) {
			ComponentName comp = new ComponentName(this, AddCourseActivity.class);
			Intent intent = new Intent();
			intent.setComponent(comp);
			startActivity(intent);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		boolean needSave = false;
		// 判断并加入课程
		if (data.getCourseToAdd() != null) {
			data.getCourses().add(data.getCourseToAdd());
			courseController.addCourseToTable(this, rl_course_table,
					data.getCourseToAdd(), courseButtons);
			Iterator<Integer> iterator = data.getSelectedCurriculumItems().keySet()
					.iterator();
			while (iterator.hasNext()) {
				data.getSelectedCurriculumItems().get(iterator.next())
						.setSelected(false);
			}
			data.getSelectedCurriculumItems().clear();
			data.setCourseToAdd(null);
			needSave = true;
		}
		// 判断并修改课程
		if (data.isCourseToShowModified()) {
			data.getCourseToShow().getCourseButton().setVisibility(View.INVISIBLE);
			rl_course_table.removeView(data.getCourseToShow().getCourseButton());
			courseButtons.remove(data.getCourseToShow().getCourseButton());
			courseController.addCourseToTable(this, rl_course_table,
					data.getCourseToShow(), courseButtons);
			data.setCourseToShow(null);
			data.setCourseToShowModified(false);
			needSave = true;
		}
		// 判断并删除课程
		if (data.isCourseToShowDeleted()) {
			data.getCourseToShow().getCourseButton().setVisibility(View.INVISIBLE);
			rl_course_table.removeView(data.getCourseToShow().getCourseButton());
			courseButtons.remove(data.getCourseToShow().getCourseButton());
			data.getCourses().remove(data.getCourseToShow());
			data.setCourseToShow(null);
			data.setCourseToShowDeleted(false);
			needSave = true;
		}
		// 重新保存课程信息
		if (needSave) {
			data.saveData();
			curriculumAdapter.notifyDataSetChanged();
		}
	}

	/**
	 * 返回
	 */
	public void back(View view) {
		onBackPressed();
	}
}
