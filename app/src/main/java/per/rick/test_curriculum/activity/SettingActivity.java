package per.rick.test_curriculum.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.listener.CalendarWeekChangeListener;
import per.rick.test_curriculum.widget.CalendarWeekChangeDialog;

/**
 * 设置页面
 * Created by Rick on 2016-4-28
 */
public class SettingActivity extends AppCompatActivity {

	private TextView tv_calendar_week = null;// 显示当前周的TextView
	private CurriculumData data = null;// 课程表数据对象
	//周选择对话框监听器
	private CalendarWeekChangeListener calendarWeekChangeListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		data = CurriculumData.getInstance(this);
		initInfo();
		setCalendarWeekChangeListener();
	}

	/**
	 * 初始化控件
	 */
	private void initInfo() {
		tv_calendar_week = (TextView) findViewById(R.id.tv_calendar_week);
		tv_calendar_week.setText("第 " + data.getCurrentWeek() + " 周");
	}

	/**
	 * 返回
	 */
	public void back(View view) {
		this.onBackPressed();
	}

	/**
	 * 弹出周选择对话框
	 */
	public void chooseCalendarWeek(View view) {
		CalendarWeekChangeDialog.Builder builder =
				new CalendarWeekChangeDialog.Builder(this,
						"修改当前周",
						getWeekStringArray(),
						data.getCurrentWeek());
		builder.setCalendarWeekChangeListener(calendarWeekChangeListener);
		builder.create().show();
	}

	/**
	 * 获得周选择对话框内周的文本字符串数组
	 *
	 * @return 字符串数组
	 */
	private String[] getWeekStringArray() {
		String[] weeks = new String[data.getMaxWeekCount()];
		int i;
		for (i = 0; i < data.getMaxWeekCount(); i++) {
			weeks[i] = "第 " + (i + 1) + " 周";
		}
		return weeks;
	}

	/**
	 * 设置周选择对话框监听器
	 */
	private void setCalendarWeekChangeListener() {
		calendarWeekChangeListener = new CalendarWeekChangeListener() {
			@Override
			public void doChangeCalendarWeek(int week) {
				tv_calendar_week.setText("第 " + week + " 周");
				data.setFirstDay(getNewFirstDay(data.getFirstDay(),
						data.getCurrentWeek(), week));
				data.setCurrentWeek(week);
				data.setShowWeek(week);
				data.saveFirstDay();
			}
		};
	}

	/**
	 * 获得新的学期第一天日期
	 *
	 * @param oldFirsDay     旧日期
	 * @param oldCurrentWeek 新日期
	 * @param newCurrentWeek 新的当前周
	 * @return 新的学期第一天日期
	 */
	private Date getNewFirstDay(Date oldFirsDay, int oldCurrentWeek,
								int newCurrentWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldFirsDay);
		cal.add(Calendar.DATE, (oldCurrentWeek - newCurrentWeek) * 7);
		return cal.getTime();
	}
}
