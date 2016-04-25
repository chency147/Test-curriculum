package per.rick.test_curriculum.adapter.holder;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Rick on 2016/4/9.
 */
public class CurriculumViewHolder {
	private TextView tv_info;
	private LinearLayout ll_item_curriculum;

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
