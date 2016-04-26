package per.rick.test_curriculum.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.listener.ConfirmDialogListener;

/**
 * 确认框
 * Created by Rick on 2016/4/25.
 */
public class ConfirmDialog extends Dialog {
	private TextView tv_title = null;// 对话框标题
	private TextView tv_message = null;// 对话框信息
	private Button cancelButton = null;// 取消按钮
	private Button sureButton = null;// 确认按钮

	public ConfirmDialog(Context context) {
		super(context);
	}

	public ConfirmDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	protected ConfirmDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	// set and get methods
	public TextView getTv_title() {
		return tv_title;
	}

	public void setTv_title(TextView tv_title) {
		this.tv_title = tv_title;
	}

	public TextView getTv_message() {
		return tv_message;
	}

	public void setTv_message(TextView tv_message) {
		this.tv_message = tv_message;
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

	/**
	 * 对话框生成器
	 */
	public static class Builder {
		private String title;// 标题
		private String message;// 信息
		// 取消按钮监听器
		private View.OnClickListener cancelButtonClickListener = null;
		// 确认按钮监听器
		private View.OnClickListener sureButtonClickListener = null;
		private Context context = null;// 上下文对象
		private ConfirmDialog dialog;// 确认框对象
		// 确认框监听器
		private ConfirmDialogListener confirmDialogListener = null;

		/**
		 * 构造方法
		 *
		 * @param context 上下文对象
		 * @param title   标题
		 * @param message 消息
		 */
		public Builder(Context context, String title, String message) {
			this.context = context;
			this.title = title;
			this.message = message;
		}

		/**
		 * 设置确认框监听器
		 * @param listener 确认框监听器
		 * @return 确认框生成器
		 */
		public Builder setConfirmDialogListener(
				ConfirmDialogListener listener) {
			this.confirmDialogListener = listener;
			return this;
		}

		/**
		 * 构造确认框
		 * @return 对话框对象
		 */
		public ConfirmDialog create() {
			// 获取布局获取器
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// 声明确认框
			dialog = new ConfirmDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_confirm_layout,
					null);// 获取布局
			dialog.addContentView(layout, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT
			));// 加入布局并设置参数
			// 初始化确认框内各控件
			dialog.setTv_title((TextView) layout.findViewById(R.id.tv_title));
			dialog.setTv_message((TextView) layout.findViewById(
					R.id.tv_message));
			dialog.setCancelButton((Button) layout.findViewById(
					R.id.bt_cancel));
			dialog.setSureButton((Button) layout.findViewById(R.id.bt_sure));
			dialog.getTv_title().setText(title);
			dialog.getTv_message().setText(message);
			// 设置取消和确认按钮的监听器
			this.setButtonListener();
			dialog.getCancelButton().setOnClickListener(
					cancelButtonClickListener);
			dialog.getSureButton().setOnClickListener(
					sureButtonClickListener);
			return dialog;
		}

		/**
		 * 设置取消和确认按钮的监听器
		 */
		private void setButtonListener() {
			cancelButtonClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (confirmDialogListener != null) {
						confirmDialogListener.doConfirm(false);
					}
					dialog.dismiss();
				}
			};

			sureButtonClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (confirmDialogListener != null) {
						confirmDialogListener.doConfirm(true);
					}
					dialog.dismiss();
				}
			};
		}
	}
}
