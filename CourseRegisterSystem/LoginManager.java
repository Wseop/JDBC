package courseSystem;

import java.sql.Connection;
import java.util.ArrayList;

//�α��� ���� �޼ҵ� ����
public class LoginManager {

	private static final int ADMIN_ID = 12345678;
	private static final String ADMIN_PW = "1234";
	
	public static boolean isCorrectInfo(int ID, String PW, Connection con){
		ArrayList<LoginInfo> student_list = new ArrayList<LoginInfo>();
		student_list = DB.getLoginInfo(con);
		
		for(int i = 0; i < student_list.size(); i++){
			if(student_list.get(i).getID() == ID){
				if(student_list.get(i).getPW().equals(PW))
					return true;
				else
				{
					System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					return false;
				}
			}
		}
		System.out.println("�������� �ʴ� ID�Դϴ�.");
		return false;
	}
	public static boolean isAdminID(int ID){
		if(ADMIN_ID == ID)
			return true;
		else
			return false;
	}
	public static boolean isAdminPW(String PW){
		if(ADMIN_PW.equals(PW))
			return true;
		else
			return false;
	}
}
