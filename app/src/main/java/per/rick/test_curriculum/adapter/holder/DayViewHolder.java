package per.rick.test_curriculum.adapter.holder;

import android.widget.TextView;

/**
 * 日期Item控件持有者
 * Created by Rick on 2016/4/9.
 */
public class DayViewHolder {
	private TextView tv_day;// 日文本（显示几号，第一格显示月份）
	private TextView tv_week_day;// 周文本(显示周几）

	// set and get methods
	public TextView getTv_day() {
		return tv_day;
	}

	public void setTv_day(TextView tv_day) {
		this.tv_day = tv_day;
	}

	public TextView getTv_week_day() {
		return tv_week_day;
	}

	public void setTv_week_day(TextView tv_week_day) {
		this.tv_week_day = tv_week_day;
	}
}
