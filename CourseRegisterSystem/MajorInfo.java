package courseSystem;

public class MajorInfo {
	private int major_id;
	private String name;

	public MajorInfo(){
		major_id = 0;
	}
	public MajorInfo(int major_id, String name) {
		this.major_id = major_id;
		this.name = name;
	}
	
	public int getMajor_id() {
		return major_id;
	}
	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void printInfo(MajorInfo major){
		System.out.printf("%d %s\n", major.getMajor_id(), major.getName());
	}
}
