package per.rick.test_curriculum.listener;

/**
 * Created by Rick on 2016/4/27.
 */
public interface IntervalChooseListener {
	// 节数选择完毕后的动作
	public void refreshActivity(int dayWeek,
								int beginInterval, int endInterval);
}
