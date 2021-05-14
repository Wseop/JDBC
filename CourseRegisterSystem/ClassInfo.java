package courseSystem;

public class ClassInfo {

	private int class_no;
	private String course_id;
	private String course_name;
	private String lec_name;
	private int credit;
	private String begin;
	private String end;
	private String building;
	private int opened;
	
	public ClassInfo(){
		this.class_no = 0;
		this.course_id = null;
		this.course_name = null;
		this.lec_name = null;
		this.credit = 0;
		this.begin = null;
		this.end = null;
		this.building = null;
		this.opened = 0;
	};
	
	public ClassInfo(int class_no, String course_id, String course_name, String lec_name, int credit, String begin,
			String end, String building, int opened) {
		this.class_no = class_no;
		this.course_id = course_id;
		this.course_name = course_name;
		this.lec_name = lec_name;
		this.credit = credit;
		this.begin = begin;
		this.end = end;
		this.building = building;
		this.opened = opened;
	}

	public int getClass_no() {
		return class_no;
	}
	public void setClass_no(int class_no) {
		this.class_no = class_no;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getLec_name() {
		return lec_name;
	}
	public void setLec_name(String lec_name) {
		this.lec_name = lec_name;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public int getOpened(){
		return opened;
	}
	public void setOpened(int opened){
		this.opened = opened;
	}
	//조건 없이 조회
	public void printCourseInfo(ClassInfo course){
		printInfo(course);
	}
	//수업 번호로 조회
	public void printClassInfoByNO(ClassInfo course, int class_no){
		if(class_no == course.getClass_no()){
			printInfo(course);
		}
	}
	//학수 번호로 조회
	public void printClassInfoByID(ClassInfo course, String course_id){
		if(course_id.equals(course.getCourse_id())){
			printInfo(course);
		}
	}
	//과목명으로 조회, 키워드 검색
	public void printClassInfoByCName(ClassInfo course, String course_name){
		if(course.getCourse_name().contains(course_name)){
			printInfo(course);
		}
	}
	//교강사명으로 조회, 전방일치 검색
	public void printClassInfoByLName(ClassInfo course, String lec_name){
		String name;
		int len = lec_name.length();
		
		if(len < course.getLec_name().length())
			name = course.getLec_name().substring(0, len);
		else
			name = course.getLec_name();
		
		if(lec_name.equals(name)){
			printInfo(course);
		}
	}
	private void printInfo(ClassInfo course){
		String day, start, end;
		
		if(course.getOpened() != 2014)
			return;
		
		if(course.getBegin().equals("NO")){
			day = "미지정";
			start = "--:--";
			end = "--:--";
		}
		else{
			day = getDay(course.getBegin().charAt(9));
			start = course.getBegin().substring(11, 16);
			end = course.getEnd().substring(11, 16);
		}
		System.out.printf("%d\t%s\t%30s\t\t%d\t%s\t%s %s ~ %s\t%s\n", course.getClass_no(), course.getCourse_id(), course.getCourse_name(),
				course.getCredit(), course.getLec_name(), day, start, end, course.getBuilding());
	}
	public String getDay(char day){
		int d = day - 48;
		switch(d){
			case 1:
				return "월요일";
			case 2:
				return "화요일";
			case 3:
				return "수요일";
			case 4:
				return "목요일";
			case 5:
				return "금요일";
			case 6:
				return "토요일";
			default:
				return null;	
		}
	}
}
