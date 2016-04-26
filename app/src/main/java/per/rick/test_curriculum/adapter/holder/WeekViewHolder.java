package per.rick.test_curriculum.adapter.holder;

import android.widget.TextView;

/**
 * 周数选择表格Item控件持有者
 * Created by Rick on 2016/4/15.
 */
public class WeekViewHolder {
	private TextView tv_week_num;// 周数文本对象

	// set and get methods
	public TextView getTv_week_num() {
		return tv_week_num;
	}

	public void setTv_week_num(TextView tv_week_num) {
		this.tv_week_num = tv_week_num;
	}
}
