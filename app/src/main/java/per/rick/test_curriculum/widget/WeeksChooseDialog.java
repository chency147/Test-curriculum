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
 * Created by Rick on 2016/4/15.
 */
public class WeeksChooseDialog extends Dialog {
	private GridView gv_weeks;
	private Button sureButton;
	private Button cancelButton;

	public WeeksChooseDialog(Context context) {
		super(context);
	}

	public WeeksChooseDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	protected WeeksChooseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

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

	public static class Builder {
		private CurriculumData data;
		private Context context;
		private View contentView;
		private WeekChooseAdapter weekChooseAdapter;
		private View.OnClickListener sureButtonClickListener;
		private View.OnClickListener cancelButtonClickListener;
		private List<Week> weeks;
		private WeeksChooseDialog dialog;
		private WeeksChooseListener weeksChooseListener;

		public Builder(Context context, Course course,
					   WeeksChooseListener weeksChooseListener) {
			this.context = context;
			this.weeksChooseListener = weeksChooseListener;
			data = CurriculumData.getInstance(this.context);
			weeks = new ArrayList<Week>();
			int i;
			for (i = 0; i < data.getMaxWeekCount(); i++) {
				Week week = new Week();
				week.setNum(i + 1);
				weeks.add(week);
			}
			if (course == null || course.getWeeks() == null) {
				return;
			}
			for (i = 0; i < course.getWeeks().length; i++) {
				weeks.get(course.getWeeks()[i] - 1).setSelected(true);
			}
		}

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

		public WeeksChooseDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			dialog = new WeeksChooseDialog(
					context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_weeks_choose_layout,
					null);
			dialog.addContentView(layout, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT
			));
			weekChooseAdapter = new WeekChooseAdapter(context, weeks);
			dialog.setGv_weeks((GridView) layout.findViewById(R.id.gv_weeks));
			dialog.getGv_weeks().setAdapter(weekChooseAdapter);
			dialog.setContentView(layout);
			dialog.getGv_weeks().setOnItemClickListener(
					new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
												View view,
												int position, long id) {
							weeks.get(position).setSelected(
									!weeks.get(position).isSelected()
							);
							weekChooseAdapter.notifyDataSetChanged();
						}
					});
			dialog.setSureButton((Button) layout.findViewById(R.id.bt_sure));
			dialog.setCancelButton((Button) layout.findViewById(
					R.id.bt_cancel));
			setListener();
			dialog.getCancelButton().setOnClickListener(
					cancelButtonClickListener);
			dialog.getSureButton().setOnClickListener(
					sureButtonClickListener);
			return dialog;
		}

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
