package per.rick.test_curriculum.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.listener.CalendarWeekChangeListener;

/**
 * 选择当前第几周的周数选择对话框
 * 我命名其为「日历周数选择对话框」
 * Created by Rick on 2016/4/28.
 */
public class CalendarWeekChangeDialog extends Dialog {
	private TextView tv_title = null;// 对话框标题
	private NumberPicker np_week = null;// 周选择器
	private Button cancelButton = null;// 取消按钮
	private Button sureButton = null;// 确认按钮

	/* 构造方法 BEGIN */
	public CalendarWeekChangeDialog(Context context) {
		super(context);
	}

	public CalendarWeekChangeDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	protected CalendarWeekChangeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	/* 构造方法 END */
	/* set and get methods BEGIN */
	public TextView getTv_title() {
		return tv_title;
	}

	public void setTv_title(TextView tv_title) {
		this.tv_title = tv_title;
	}

	public NumberPicker getNp_week() {
		return np_week;
	}

	public void setNp_week(NumberPicker np_week) {
		this.np_week = np_week;
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
	 * 日历周数选择对话框生成器
	 */
	public static class Builder {
		// 取消按钮监听器
		private View.OnClickListener cancelButtonClickListener = null;
		// 确认按钮监听器
		private View.OnClickListener sureButtonClickListener = null;
		private Context context = null;// 上下文对象
		private CalendarWeekChangeDialog dialog = null;// 对话框对象
		private int currentWeek = 1;// 当前周或者当前显示的周，默认为第一周
		// 对话框监听器
		private CalendarWeekChangeListener calendarWeekChangeListener = null;
		private String[] weeks = null;// 显示的周数字符串数组
		private String title;// 对话框标题

		/**
		 * 构造方法
		 *
		 * @param context     上下文对象
		 * @param title       对话框标题
		 * @param weeks       周数文本
		 * @param currentWeek 当前周数
		 */
		public Builder(Context context, String title,
					   String[] weeks, int currentWeek) {
			this.context = context;
			this.title = title;
			this.weeks = weeks;
			this.currentWeek = currentWeek;
		}

		/* set and get methods BEGIN */
		public View.OnClickListener getCancelButtonClickListener() {
			return cancelButtonClickListener;
		}

		public Builder setCancelButtonClickListener(
				View.OnClickListener cancelButtonClickListener) {
			this.cancelButtonClickListener = cancelButtonClickListener;
			return this;
		}

		public View.OnClickListener getSureButtonClickListener() {
			return sureButtonClickListener;
		}

		public Builder setSureButtonClickListener(
				View.OnClickListener sureButtonClickListener) {
			this.sureButtonClickListener = sureButtonClickListener;
			return this;
		}

		public CalendarWeekChangeListener getCalendarWeekChangeListener() {
			return calendarWeekChangeListener;
		}

		public Builder setCalendarWeekChangeListener(
				CalendarWeekChangeListener calendarWeekChangeListener) {
			this.calendarWeekChangeListener = calendarWeekChangeListener;
			return this;
		}
		/* set and get methods END */

		public CalendarWeekChangeDialog create() {
			// 获取布局获取器
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 声明对话框
			dialog = new CalendarWeekChangeDialog(context, R.style.Dialog);
			// 获取布局
			View layout = inflater.inflate(R.layout
					.dialog_calendar_week_change_layout, null);
			dialog.addContentView(layout, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT
			));// 向对话框中加入布局并设置布局参数
			/* 初始化对话框内控件 BEGIN */
			dialog.setTv_title((TextView) layout.findViewById(R.id.tv_title));
			dialog.setNp_week((NumberPicker) layout.findViewById(R.id.np_week));
			dialog.setSureButton((Button) layout.findViewById(R.id.bt_sure));
			dialog.setCancelButton((Button) layout.findViewById(
					R.id.bt_cancel));
			/* 初始化对话框内控件 END */
			dialog.getTv_title().setText(title);
			initWeekPicker();
			setListener();
			dialog.getCancelButton().setOnClickListener(
					cancelButtonClickListener);
			dialog.getSureButton().setOnClickListener(sureButtonClickListener);
			return dialog;
		}

		/**
		 * 初始化周数选择器
		 */
		private void initWeekPicker() {
			dialog.getNp_week().setDescendantFocusability(
					NumberPicker.FOCUS_BLOCK_DESCENDANTS);
			dialog.getNp_week().setDisplayedValues(weeks);
			dialog.getNp_week().setMinValue(1);
			dialog.getNp_week().setMaxValue(weeks.length);
			dialog.getNp_week().setWrapSelectorWheel(false);
			dialog.getNp_week().setValue(currentWeek);
		}

		/**
		 * 设置取消和确认按钮的监听器
		 */
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
						if (calendarWeekChangeListener != null) {
							calendarWeekChangeListener.doChangeCalendarWeek(
									dialog.getNp_week().getValue());
						}
						dialog.dismiss();
					}
				};
			}
		}
	}
}
