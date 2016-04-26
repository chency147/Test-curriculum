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
 * 课程表表格适配器
 * Created by Rick on 2016/4/10.
 */
public class CurriculumAdapter extends BaseAdapter {

	private CurriculumData data;// 课程表数据对象
	private GridView.LayoutParams layoutParams;// 课程表Item的布局参数
	private Context context;

	/**
	 * 适配器构造方法
	 *
	 * @param context 上下文对象
	 * @param data    课程表数据对象
	 */
	public CurriculumAdapter(Context context, CurriculumData data) {
		super();
		this.data = data;
		this.context = context;
		// 初始化课程表Item的布局参数
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
		CurriculumViewHolder viewHolder;// 课程表Item内控件Holder
		// 如果该Item的View不存在
		if (convertView == null ||
				!(convertView.getTag() instanceof CurriculumViewHolder)) {
			viewHolder = new CurriculumViewHolder();// 声明新的ViewHolder
			// 设置对应的布局文件
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.item_curriculum_course, null);
			// 设置Item的布局参数
			convertView.setLayoutParams(this.layoutParams);
			viewHolder.setTv_info((TextView) convertView
					.findViewById(R.id.tv_info));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (CurriculumViewHolder) convertView.getTag();
		}
		// 根绝Item的位置定义不同的内容
		if (index % data.getTableColumnCount() == 0) {// Item为表头
			viewHolder.getTv_info().setText(
					String.valueOf(index / data.getTableColumnCount() + 1));
			convertView.setBackgroundResource(R.drawable.item_header_bg);
		} else {// Item不为表头
			viewHolder.getTv_info().setText(
					data.getCurriculumItems().get(index).getInfo());
			// 根据Item的选中状态来定义不同的样式
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

