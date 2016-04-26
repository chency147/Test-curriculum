package per.rick.test_curriculum.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.adapter.WeekChooseAdapter;
import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.entity.Course;
import per.rick.test_curriculum.entity.Week;
import per.rick.test_curriculum.listener.WeeksChooseListener;

/**
 * 周数选择对话框
 * Created by Rick on 2016/4/15.
 */
public class WeeksChooseDialog extends Dialog {
	private GridView gv_weeks;// 周数选择表格视图
	private Button sureButton;// 确认按钮
	private Button cancelButton;// 删除按钮

	public WeeksChooseDialog(Context context) {
		super(context);
	}

	public WeeksChooseDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	protected WeeksChooseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	/* set and get methods BEGIN */
	public GridView getGv_weeks() {
		return gv_weeks;
	}

	public void setGv_weeks(GridView gv_weeks) {
		this.gv_weeks = gv_weeks;
	}

	public Button getSureButton() {
		return sureButton;
	}

	public void setSureButton(Button sureButton) {
		this.sureButton = sureButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	/* set and get methods END */
	public static class Builder {
		private CurriculumData data;// 课程表数据对象
		private Context context;// 上下文对象
		private WeekChooseAdapter weekChooseAdapter;// 周数选择表格视图适配器
		private View.OnClickListener sureButtonClickListener;// 确认按钮监听器
		private View.OnClickListener cancelButtonClickListener;// 取消按钮监听器
		private List<Week> weeks;// 周数数组
		private WeeksChooseDialog dialog;// 周数选择对话框对象
		private WeeksChooseListener weeksChooseListener;// 周数选择对话框监听器

		/**
		 * 构造方法
		 *
		 * @param context             上下文对象
		 * @param course              当前课程
		 * @param weeksChooseListener 周数选择对话框监听器
		 */
		public Builder(Context context, Course course,
					   WeeksChooseListener weeksChooseListener) {
			this.context = context;
			this.weeksChooseListener = weeksChooseListener;
			data = CurriculumData.getInstance(this.context);
			weeks = new ArrayList<Week>();
			// 初始化周数
			int i;
			for (i = 0; i < data.getMaxWeekCount(); i++) {
				Week week = new Week();
				week.setNum(i + 1);
				weeks.add(week);
			}
			if (course == null || course.getWeeks() == null) {
				return;
			}
			// 利用当前课程的周数作为初始数据
			for (i = 0; i < course.getWeeks().length; i++) {
				weeks.get(course.getWeeks()[i] - 1).setSelected(true);
			}
		}

		/* set and get methods BEGIN */
		public Builder setSureButtonClickListener(
				View.OnClickListener listener) {
			this.sureButtonClickListener = listener;
			return this;
		}

		public Builder setCancelButtonClickListener(
				View.OnClickListener listener) {
			this.cancelButtonClickListener = listener;
			return this;
		}
		/* set and get methods END */

		/**
		 * 构造周数选择对话框
		 *
		 * @return 周数选择对话框对象
		 */
		public WeeksChooseDialog create() {
			// 获取布局获取器
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 声明对话框
			dialog = new WeeksChooseDialog(
					context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_weeks_choose_layout,
					null);// 获取布局
			dialog.addContentView(layout, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT
			));// 加入布局并设置布局参数
			// 声明周数选择表格的监听器
			weekChooseAdapter = new WeekChooseAdapter(context, weeks);
			/* 初始化对话框内各控件 BEGIN */
			dialog.setGv_weeks((GridView) layout.findViewById(R.id.gv_weeks));
			dialog.getGv_weeks().setAdapter(weekChooseAdapter);
			dialog.setContentView(layout);
			// 设置周选择表格的监听器
			dialog.getGv_weeks().setOnItemClickListener(
					new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
												View view,
												int position, long id) {
							// 修改被选中item的选择状态
							weeks.get(position).setSelected(
									!weeks.get(position).isSelected()
							);
							weekChooseAdapter.notifyDataSetChanged();
						}
					});
			dialog.setSureButton((Button) layout.findViewById(R.id.bt_sure));
			dialog.setCancelButton((Button) layout.findViewById(
					R.id.bt_cancel));
			/* 初始化对话框内各控件 END */
			// 设置取消和确认按钮的监听器
			setListener();
			dialog.getCancelButton().setOnClickListener(
					cancelButtonClickListener);
			dialog.getSureButton().setOnClickListener(
					sureButtonClickListener);
			return dialog;
		}

		/**
		 * 设置取消和确认按钮的监听器
		 */
		private void setListener() {
			cancelButtonClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					data.setModified_weeks(null);
					dialog.dismiss();
				}
			};
			sureButtonClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					List<Integer> weeksTemp = new ArrayList<Integer>();
					for (Week week : weeks) {
						if (week.isSelected()) {
							weeksTemp.add(week.getNum());
						}
					}
					// 将List转换为Array
					int[] weeksArray;
					if (weeksTemp.size() != 0) {
						weeksArray = new int[weeksTemp.size()];
						for (int i = 0; i < weeksTemp.size(); i++) {
							weeksArray[i] = weeksTemp.get(i);
						}
						data.setModified_weeks(weeksArray);
						weeksChooseListener.refreshActivity(
								data.getModified_weeks());
						dialog.dismiss();
					}
				}
			};
		}
	}
}
