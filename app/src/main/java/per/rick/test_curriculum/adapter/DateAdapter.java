package per.rick.test_curriculum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import per.rick.test_curriculum.adapter.holder.DayViewHolder;
import per.rick.test_curriculum.R;
import per.rick.test_curriculum.entity.Day;

/**
 * Created by Rick on 2016/4/10.
 */
public class DateAdapter extends BaseAdapter {

	private Context context;
	private List<Day> days;
	private String month;

	public DateAdapter(Context context, List<Day> days, String month) {
		super();
		this.context = context;
		this.days = days;
		this.month = month;
	}

	@Override
	public int getCount() {
		return 8;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DayViewHolder viewHolder;
		if (convertView == null || !(convertView.getTag() instanceof DayViewHolder)) {
			viewHolder = new DayViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_day, null);
			viewHolder.setTv_day((TextView) convertView.findViewById(R.id.tv_day));
			viewHolder.setTv_week_day((TextView) convertView.findViewById(R.id.tv_week_day));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (DayViewHolder) convertView.getTag();
		}
		if (position == 0) {
			viewHolder.getTv_day().setText(month);
			viewHolder.getTv_week_day().setText("");
		} else {
			viewHolder.getTv_day().setText(days.get(position - 1).getDay());
			viewHolder.getTv_week_day().setText(days.get(position - 1).getDay_week());
		}
		return convertView;
	}
}
