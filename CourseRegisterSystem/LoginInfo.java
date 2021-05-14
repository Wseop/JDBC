package courseSystem;

public class LoginInfo {

	private int ID;
	private String PW;
	
	public LoginInfo(){};
	public LoginInfo(int ID, String PW){
		this.ID = ID;
		this.PW = PW;
	}
	
	public int getID(){ return ID; }
	public String getPW(){ return PW; }
	public void setID(int ID){ this.ID = ID; }
	public void setPW(String PW){ this.PW = PW; }
}
