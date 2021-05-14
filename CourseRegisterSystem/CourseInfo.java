package courseSystem;

public class CourseInfo {

	private String course_id;
	private String name;
	private int credit;
	
	public CourseInfo(){}
	
	public CourseInfo(String course_id, String name, int credit) {
		super();
		this.course_id = course_id;
		this.name = name;
		this.credit = credit;
	}
	
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public void printCourseInfo(CourseInfo course, int index){
		System.out.printf("%d\t%s\t%30s\t\t%d\n", index, course.getCourse_id(), course.getName(), course.getCredit());
	}
}
