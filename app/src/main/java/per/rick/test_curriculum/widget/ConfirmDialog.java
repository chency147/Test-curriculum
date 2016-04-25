package per.rick.test_curriculum.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.listener.ConfirmDialogListener;

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
		private ConfirmDialog dialog;
		private ConfirmDialogListener confirmDialogListener = null;

		public Builder(Context context, String title, String message) {
			this.context = context;
			this.title = title;
			this.message = message;
		}

		public Builder setConfirmDialogListener(
				ConfirmDialogListener listener) {
			this.confirmDialogListener = listener;
			return this;
		}


		public ConfirmDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			dialog = new ConfirmDialog(context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.dialog_confirm_layout,
					null);
			dialog.addContentView(layout, new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT
			));
			dialog.setTv_title((TextView) layout.findViewById(R.id.tv_title));
			dialog.setTv_message((TextView) layout.findViewById(
					R.id.tv_message));
			dialog.setCancelButton((Button) layout.findViewById(
					R.id.bt_cancel));
			dialog.setSureButton((Button) layout.findViewById(R.id.bt_sure));
			dialog.getTv_title().setText(title);
			dialog.getTv_message().setText(message);
			this.setButtonListener();
			dialog.getCancelButton().setOnClickListener(
					cancelButtonClickListener);
			dialog.getSureButton().setOnClickListener(
					sureButtonClickListener);
			return dialog;
		}

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
