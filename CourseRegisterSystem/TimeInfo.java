package courseSystem;

public class TimeInfo {
	
	private int class_id;
	private int period;
	private String begin;
	private String end;
	
	public TimeInfo(){}

	public int getClass_id() {
		return class_id;
	}
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin, int day) {
		this.begin = createTimeFormat(begin, day);
	}
	public void setBegin(String begin){
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end, int day) {
		this.end = createTimeFormat(end, day);
	}
	public void setEnd(String end){
		this.end = end;
	}
	private String createTimeFormat(String time, int day){
		String timeFormat;
		
		if(day != 7)
			timeFormat = "1900-01-0" + Integer.toString(day) + "T" + time + ":00.000Z";
		else
			timeFormat = time;
		return timeFormat;
	}
}
