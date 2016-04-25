package per.rick.test_curriculum.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Rick on 2016/4/25.
 */
public class ConfirmDialog extends Dialog {
	private TextView tv_title = null;
	private TextView tv_message = null;
	private Button cancelButton = null;
	private Button sureButton = null;

	public ConfirmDialog(Context context) {
		super(context);
	}

	public ConfirmDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	protected ConfirmDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

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

	public static class Builder {
		private String title;
		private String message;
		private View.OnClickListener cancelButtonClickListener = null;
		private View.OnClickListener sureButtonClickListener = null;
		private Context context = null;

		public Builder(Context context) {
			this.context = context;
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
	}
}
