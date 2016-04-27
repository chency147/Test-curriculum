package per.rick.test_curriculum.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.data.CurriculumData;
import per.rick.test_curriculum.entity.Course;
import per.rick.test_curriculum.listener.IntervalChooseListener;

/**
 * 课程节数选择Dialog
 * Created by Rick on 2016/4/26.
 */
public class IntervalChooseDialog extends Dialog {

	private NumberPicker np_dayWeek;// 星期几上课选择器
	private NumberPicker np_beginInterval;// 开始上课节数选择器
	private NumberPicker np_endInterval;// 节数上课节数选择器
	private Button cancelButton;// 取消按钮
	private Button sureButton;// 确定按钮
	private NumberPicker.OnValueChangeListener
			beginIntervalChangeListener = null;
	private NumberPicker.OnValueChangeListener
			endIntervalChangeListener = null;

	public IntervalChooseDialog(Context context) {
		super(context);
	}

	public IntervalChooseDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	protected IntervalChooseDialog(Context context, boolean cancelable,
								   OnCancelListener cancelListener) {

		super(context, cancelable, cancelListener);
	}

	/**
	 * 初始化设置开始节数和结束节数选择器的监听器
	 */
	public void initPickerListener() {
		if (np_beginInterval == null || np_endInterval == null) {
			return;
		}
		beginIntervalChangeListener = new NumberPicker.OnValueChangeListener() {
			@Override
			public void onValueChange(
					NumberPicker picker, int oldVal, int newVal) {
				if (newVal > np_endInterval.getValue()) {
					np_endInterval.setValue(newVal);
				}
			}
		};
		endIntervalChangeListener = new NumberPicker.OnValueChangeListener() {
			@Override
			public void onValueChange(
					NumberPicker picker, int oldVal, int newVal) {
				if (newVal < np_beginInterval.getValue()) {
					picker.setValue(np_beginInterval.getValue());
				}
			}
		};
		this.np_beginInterval.setOnValueChangedListener(
				beginIntervalChangeListener);
		this.np_endInterval.setOnValueChangedListener(
				endIntervalChangeListener);
	}

	/* set and get methods BEGIN */
	public NumberPicker getNp_dayWeek() {
		return np_dayWeek;
	}

	public void setNp_dayWeek(NumberPicker np_dayWeek) {
		this.np_dayWeek = np_dayWeek;
	}

	public NumberPicker getNp_beginInterval() {
		return np_beginInterval;
	}

	public void setNp_beginInterval(NumberPicker np_beginInterval) {
		this.np_beginInterval = np_beginInterval;
	}

	public NumberPicker getNp_endInterval() {
		return np_endInterval;
	}

	public void setNp_endInterval(NumberPicker np_endInterval) {
		this.np_endInterval = np_endInterval;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public Button getSureButton() {
		return sureButton;
	}

	public void setSureButton(Button sureButton) {
		this.sureButton = sureButton;
	}
	/* set and get methods END */

	/**
	 * 节数选择对话框生成器
	 */
	public static class Builder {
		// 取消按钮监听器
		private View.OnClickListener cancelButtonClickListener = null;
		// 确定按钮监听器
		private View.OnClickListener sureButtonClickListener = null;
		private Context context;// 上下文对象
		private IntervalChooseDialog dialog;// 对话框对象
		private Course course = null;
		private IntervalChooseListener intervalChooseListener = null;
		private CurriculumData data = null;

		/**
		 * 构造方法
		 *
		 * @param context 上下文对象
		 * @param course  课程对象
		 */
		public Builder(Context context, Course course) {
			this.context = context;
			this.course = course;
			this.data = CurriculumData.getInstance(context);
		}

		/* set and get methods BEGIN */
		public Builder setCancelButtonClickListener(
				View.OnClickListener listener) {
			this.cancelButtonClickListener = listener;
			return this;
		}

		public Builder setSureButtonClickListener(
				View.OnClickListener listener) {
			this.sureButtonClickListener = listener;
			return this;
		}

		public IntervalChooseListener getIntervalChooseListener() {
			return intervalChooseListener;
		}

		public Builder setIntervalChooseListener(
				IntervalChooseListener intervalChooseListener) {
			this.intervalChooseListener = intervalChooseListener;
			return this;
		}
		/* set and get methods END */

		/**
		 * 构造节数选择对话框
		 *
		 * @return 节数选择对话框
		 */
		public IntervalChooseDialog create() {
			//获取布局获取器
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 声明对话框
			dialog = new IntervalChooseDialog(context, R.style.Dialog);
			// 获取布局
			View layout = inflater.inflate(R.layout
					.dialog_interval_choose_layout, null);
			dialog.addContentView(layout, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT
			));// 向对话框中加入布局并设置布局参数
			/* 初始化对话框内各控件 BEGIN */
			dialog.setNp_dayWeek((NumberPicker) layout.findViewById(
					R.id.np_dayWeek));
			dialog.setNp_beginInterval((NumberPicker) layout.findViewById(
					R.id.np_beginInterval));
			dialog.setNp_endInterval((NumberPicker) layout.findViewById(
					R.id.np_endInterval));
			dialog.setSureButton((Button) layout.findViewById(R.id.bt_sure));
			dialog.setCancelButton((Button) layout.findViewById(
					R.id.bt_cancel));
			/* 初始化对话框内各控件 END */
			this.initIntervalPickers();
			dialog.initPickerListener();
			this.setListener();
			dialog.getCancelButton().setOnClickListener(
					cancelButtonClickListener);
			dialog.getSureButton().setOnClickListener(
					sureButtonClickListener);
			return dialog;
		}


		private void setListener() {
			if (cancelButtonClickListener == null) {
				cancelButtonClickListener = new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				};
			}
			if (sureButtonClickListener == null) {
				sureButtonClickListener = new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (intervalChooseListener != null) {
							intervalChooseListener.refreshActivity(
									dialog.getNp_dayWeek().getValue(),
									dialog.getNp_beginInterval().getValue(),
									dialog.getNp_endInterval().getValue());
						}
						dialog.dismiss();
					}
				};
			}
		}

		private void initIntervalPickers() {
			dialog.getNp_dayWeek().setDescendantFocusability(
					NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			dialog.getNp_beginInterval().setDescendantFocusability(
					NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			dialog.getNp_endInterval().setDescendantFocusability(
					NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			dialog.getNp_dayWeek().setDisplayedValues(
					data.getStr_week()
			);
			dialog.getNp_dayWeek().setMinValue(0);
			dialog.getNp_dayWeek().setMaxValue(data.getStr_week().length - 1);
			dialog.getNp_dayWeek().setWrapSelectorWheel(false);
			dialog.getNp_dayWeek().setValue(course.getDayWeek());
			dialog.getNp_beginInterval().setDisplayedValues(
					data.getStr_beginInterval());
			dialog.getNp_beginInterval().setMinValue(1);
			dialog.getNp_beginInterval().setMaxValue(
					data.getDay_course_count());
			dialog.getNp_beginInterval().setWrapSelectorWheel(false);
			dialog.getNp_beginInterval().setValue(course.getBeginInterval());
			dialog.getNp_endInterval().setDisplayedValues(
					data.getStr_endInterval());
			dialog.getNp_endInterval().setMinValue(1);
			dialog.getNp_endInterval().setMaxValue(
					data.getDay_course_count());
			dialog.getNp_endInterval().setWrapSelectorWheel(false);
			dialog.getNp_endInterval().setValue(course.getEndInterval());
		}
	}
}
