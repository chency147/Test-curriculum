package per.rick.test_curriculum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import per.rick.test_curriculum.R;
import per.rick.test_curriculum.adapter.holder.CurriculumViewHolder;
import per.rick.test_curriculum.data.CurriculumData;

/**
 * Created by Rick on 2016/4/10.
 */
public class CurriculumAdapter extends BaseAdapter {

	private CurriculumData data;

	private GridView.LayoutParams layoutParams;

	private Context context;

	public CurriculumAdapter(Context context, CurriculumData data) {
		super();
		this.data = data;
		this.context = context;
		this.layoutParams = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				data.getPerHeight());
	}

	@Override
	public int getCount() {
		return data.getCurriculumItems().size();
	}

	@Override
	public Object getItem(int position) {
		return data.getCurriculumItems().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int index = position;
		CurriculumViewHolder viewHolder;
		if (convertView == null ||
				!(convertView.getTag() instanceof CurriculumViewHolder)) {
			viewHolder = new CurriculumViewHolder();
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.item_curriculum_course, null);
			convertView.setLayoutParams(this.layoutParams);
			viewHolder.setTv_info((TextView) convertView
					.findViewById(R.id.tv_info));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (CurriculumViewHolder) convertView.getTag();
		}
		if (index % data.getTableColumnCount() == 0) {
			viewHolder.getTv_info().setText(
					String.valueOf(index / data.getTableColumnCount() + 1));
			convertView.setBackgroundResource(R.drawable.item_header_bg);
		} else {
			viewHolder.getTv_info().setText(
					data.getCurriculumItems().get(index).getInfo());
			if (data.getCurriculumItems().get(position).isSelected()) {
				convertView.setBackgroundResource(
						R.drawable.item_curriculum_bg_selected);
			} else {
				convertView.setBackgroundResource(0);
			}
		}
		data.getCurriculumItems().get(index).setWidth(convertView.getWidth());
		data.getCurriculumItems().get(index).setHeight(convertView.getHeight());
		return convertView;
	}
}

