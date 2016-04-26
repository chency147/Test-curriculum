package per.rick.test_curriculum.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.adapter.holder.WeekViewHolder;
import per.rick.test_curriculum.entity.Week;

/**
 * 周数选择表格适配器
 * Created by Rick on 2016/4/15.
 */
public class WeekChooseAdapter extends BaseAdapter {
	private List<Week> weeks;// 周数链表
	private Context context;// 上下文对象

	/**
	 * 构造方法
	 *
	 * @param context 上下文对象
	 * @param weeks   周数链表
	 */
	public WeekChooseAdapter(Context context, List<Week> weeks) {
		super();
		this.context = context;
		this.weeks = weeks;
	}

	@Override
	public int getCount() {
		return weeks.size();
	}

	@Override
	public Object getItem(int position) {
		return weeks.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int index = position;
		WeekViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new WeekViewHolder();
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.item_weeks_choose, null);
			viewHolder.setTv_week_num((TextView) convertView.findViewById(
					R.id.tv_week_num));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (WeekViewHolder) convertView.getTag();
		}
		viewHolder.getTv_week_num().setText(String.valueOf(
				weeks.get(index).getNum()));
		// 根据周数是否被选择而使用不同的样式
		if (weeks.get(index).isSelected()) {
			viewHolder.getTv_week_num().setTextColor(Color.WHITE);
			convertView.setBackgroundResource(R.color.colorWeekSelected);
		} else {
			viewHolder.getTv_week_num().setTextColor(Color.BLACK);
			convertView.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}
}
