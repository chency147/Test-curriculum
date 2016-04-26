package per.rick.test_curriculum.adapter.holder;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 课程表Item控件持有者
 * Created by Rick on 2016/4/9.
 */
public class CurriculumViewHolder {
	private TextView tv_info;// 信息文本对象
	private LinearLayout ll_item_curriculum;// 背景布局对象

	// set and get methods
	public TextView getTv_info() {
		return tv_info;
	}

	public void setTv_info(TextView tv_course_info) {
		this.tv_info = tv_course_info;
	}

	public LinearLayout getLl_item_curriculum() {
		return ll_item_curriculum;
	}

	public void setLl_item_curriculum(LinearLayout ll_item_curriculum) {
		this.ll_item_curriculum = ll_item_curriculum;
	}
}
