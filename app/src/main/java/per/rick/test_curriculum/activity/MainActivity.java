package per.rick.test_curriculum.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

	private RelativeLayout rl_course_table;
	private GridView gv_curriculum;
	private GridView gv_date;
	private CurriculumData data;
	private CourseController courseController;
	private List<CourseButton> courseButtons;
	DateAdapter dateAdapter;
	CurriculumAdapter curriculumAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		data = CurriculumData.getInstance(this);
		data.getData();
		courseController = new CourseController(this);
		courseButtons = new ArrayList<CourseButton>();
		this.initCurriculum();
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
		for (Course course : data.getCourses()) {
			courseController.addCourseToTable(this, rl_course_table,
					course, courseButtons);
		}
	}

	private void initCurriculum() {
		gv_curriculum = (GridView) findViewById(R.id.gv_curriculum);
		gv_date = (GridView) findViewById(R.id.gv_date);
		dateAdapter = new DateAdapter(this, data.getDays(), data.getMonth());
		curriculumAdapter = new CurriculumAdapter(this, data);
		gv_date.setAdapter(dateAdapter);
		gv_curriculum.setAdapter(curriculumAdapter);
		rl_course_table = (RelativeLayout) findViewById(R.id.rl_course_table);
	}

	private void doSelectCurriculum(int position) {
		CurriculumItem item;
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
		if (data.getSelectedCurriculumItems().isEmpty()) {
			item = data.getCurriculumItems().get(position);
			item.setSelected(true);
			data.getSelectedCurriculumItems().put(position, item);
			return;
		}
		if (data.getSelectedCurriculumItems()
				.containsKey(position - data.getTableColumnCount())
				|| data.getSelectedCurriculumItems()
				.containsKey(position + data.getTableColumnCount())) {
			item = data.getCurriculumItems().get(position);
			item.setSelected(true);
			data.getSelectedCurriculumItems().put(position, item);
		}
	}

	public void startAddCourseActivity(View view) {
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
		if (data.isCourseToShowDeleted()) {
			data.getCourseToShow().getCourseButton().setVisibility(View.INVISIBLE);
			rl_course_table.removeView(data.getCourseToShow().getCourseButton());
			courseButtons.remove(data.getCourseToShow().getCourseButton());
			data.getCourses().remove(data.getCourseToShow());
			data.setCourseToShow(null);
			data.setCourseToShowDeleted(false);
			needSave = true;
		}
		if (needSave) {
			data.saveData();
			curriculumAdapter.notifyDataSetChanged();
		}
	}

	public void back(View view) {
		onBackPressed();
	}
}
