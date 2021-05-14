package courseSystem;

public class LecturerInfo {

	private int lecturer_id;
	private String lecturer_name;
	private int major_id;
	private String major_name;
	
	public LecturerInfo(){}
	
	public LecturerInfo(int lecturer_id, String lecturer_name, int major_id, String major_name) {
		this.lecturer_id = lecturer_id;
		this.lecturer_name = lecturer_name;
		this.major_id = major_id;
		this.major_name = major_name;
	}

	public int getLecturer_id() {
		return lecturer_id;
	}
	public void setLecturer_id(int lecturer_id) {
		this.lecturer_id = lecturer_id;
	}
	public String getLecturer_name() {
		return lecturer_name;
	}
	public void setLecturer_name(String lecturer_name) {
		this.lecturer_name = lecturer_name;
	}
	public int getMajor_id() {
		return major_id;
	}
	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}
	public String getMajor_name() {
		return major_name;
	}
	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}
	
	public void printLecturerInfo(LecturerInfo lecturer, int index){
		System.out.printf("%d\t%s\t%s\n", index, lecturer.getLecturer_name(), lecturer.getMajor_name());
	}
}
