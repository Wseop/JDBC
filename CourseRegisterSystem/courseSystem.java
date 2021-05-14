package courseSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

class RestoreInfo{
	private int ID;
	private int class_no;
	
	public RestoreInfo(){}
	public RestoreInfo(int iD, int class_no) {
		ID = iD;
		this.class_no = class_no;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getClass_no() {
		return class_no;
	}
	public void setClass_no(int class_no) {
		this.class_no = class_no;
	}
}

public class courseSystem {

	private static Connection con;
	private static Scanner kb = new Scanner(System.in);
	private static ArrayList<RestoreInfo> restore_list = new ArrayList<RestoreInfo>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* DB�� �����ϱ� ���� ���� �Է� */
		String DB_ID;
		int ID;
		String PW;
		
		System.out.println("=== DB �α��� ===");
		System.out.printf("ID : ");
		DB_ID = kb.nextLine();
		System.out.printf("Password : ");
		PW = kb.nextLine();
		
		try{
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db", DB_ID, PW);
			if(con != null)
				System.out.println("DB ���Ἲ��");
		}
		catch (SQLException e) {
			// TODO: handle exception
			System.out.println("DB ���� ����");
			e.printStackTrace();
			return;
		}
		
		/* ����� �α��� */
		while(true){
			System.out.printf("\n=== ������û �ý��� �α��� ===\n");
			MenuClass.showLoginMenu();
			int sel;
			while(true){
				sel = kb.nextInt();
				kb.nextLine();
				if(sel < 1 || sel > 3){
					System.out.println("�߸��� �Է�");
					continue;
				}
				else
					break;
			}
			if(sel == 3){
				return;
			}
			System.out.printf("ID : ");
			ID = kb.nextInt();
			kb.nextLine();
			System.out.printf("Password : ");
			PW = kb.nextLine();
			//1. �����
			if(sel == 1){
				if(LoginManager.isCorrectInfo(ID, PW, con)){
					System.out.println("�α��� ����");
					//������û ���α׷� ���� - �����
					while(startStudentSystem(ID));
				}
				else{
					System.out.println("�α��� ����");
				}
			}
			//2. ������
			else if(sel == 2){
				if(LoginManager.isAdminID(ID)){
					if(LoginManager.isAdminPW(PW)){
						System.out.println("�α��� ����");
						//������û ���α׷� ���� - ������
						while(startAdminSystem(ID));
					}
					else
						System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				}
				else{
					System.out.println("������ ID�� ��ġ���� �ʽ��ϴ�.");
				}
			}
		}
	}
	private static boolean startStudentSystem(int ID){
		MenuClass.showClientMenu();
		int sel = kb.nextInt();
		kb.nextLine();
		switch(sel){
		//1.������� 2.������û 3.������� 4.�ǵ����� 5.�ð�ǥ ��ȸ 6.����
			case 1:
				startCourseCatalog();
				return true;
			case 2:
				startCourseRegister(ID);
				return true;
			case 3:
				startCourseCancle(ID);
				return true;
			case 4:
				courseRestore();
				return true;
			case 5:
				showTimeTable(ID);
				return true;
			case 6:
				return false;
			default:
				System.out.println("�߸��� �Է�");
				return false;
		}
	}
	private static boolean startAdminSystem(int ID){
		MenuClass.showAdminMenu();
		int sel = kb.nextInt();
		kb.nextLine();
		switch(sel){
		//1.������� 2.���� 3.�� 4.��� 5.����
			case 1:
				startCourseCatalog();
				return true;
			case 2:
				startCreateCourse();
				return true;
			case 3:
				startDeleteCourse();
				return true;
			case 4:
				startCourseStats();
				return true;
			case 5:
				return false;
			default:
				System.out.println("�߸��� �Է�");
				return false;
		}
	}
	//���� ���
	private static void startCourseCatalog(){
		
		ArrayList<ClassInfo> class_list = new ArrayList<ClassInfo>();
		ClassInfo course = new ClassInfo();
		
		class_list = DB.getClassInfo(con);
		MenuClass.showCourseMenu();
		int sel = kb.nextInt();
		kb.nextLine();
		
		switch(sel){
			//��ü ��ȸ
			case 1:
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.printf("������ȣ\t�м���ȣ\t\t        �����\t\t\t����\t������\t�ð�\t\t\t�ǹ�\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printCourseInfo(course);
				}
				break;
			//������ȣ�� ��ȸ
			case 2:
				System.out.print("������ȣ �Է� : ");
				int class_no = kb.nextInt();
				kb.nextLine();
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println("������ȣ\t�м���ȣ\t\t        �����\t\t\t����\t������\t�ð�\t\t\t�ǹ�\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printClassInfoByNO(course, class_no);
				}
				break;
			//�м���ȣ�� ��ȸ
			case 3:
				System.out.print("�м���ȣ �Է� : ");
				String id = kb.nextLine();
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println("������ȣ\t�м���ȣ\t\t        �����\t\t\t����\t������\t�ð�\t\t\t�ǹ�\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printClassInfoByID(course, id);
				}
				break;
			//��������� ��ȸ
			case 4:
				System.out.print("����� �Է� : ");
				String course_name = kb.nextLine();
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println("������ȣ\t�м���ȣ\t\t        �����\t\t\t����\t������\t�ð�\t\t\t�ǹ�\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printClassInfoByCName(course, course_name);
				}
				break;
			//��������� ��ȸ
			case 5:
				System.out.print("����� �Է� : ");
				String lec_name = kb.nextLine();
				System.out.println("------------------------------------------------------------");
				System.out.println("������ȣ\t�м���ȣ\t\t        �����\t\t\t����\t������\t�ð�\t\t\t�ǹ�\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printClassInfoByLName(course, lec_name);
				}
				break;
		}
	}
	//���� ��û - ������ȣ�� ��û
	private static void startCourseRegister(int ID){
		
		//ID�л��� �����̷� �м���ȣ
		ArrayList<String> course_list = new ArrayList<String>();
		course_list = DB.getRegisteredCourseID(con, ID);
		
		//������� ��ü ����
		ArrayList<ClassInfo> all_class_list = new ArrayList<ClassInfo>();
		all_class_list = DB.getClassInfo(con);
		
		while(true){
			//��û����
			ArrayList<ClassInfo> register_list = new ArrayList<ClassInfo>();
			String sql_order = " order by t.begin";
			register_list = DB.getRegisterInfo(con, ID, sql_order);
			
			System.out.printf("\n���� ��ȣ �Է�(���� : -1)\n>");
			int class_no = kb.nextInt();
			kb.nextLine();
			if(class_no == -1){
				break;
			}
			
			//������ȣ ����Ʈ�� �������� ������ ���Է�
			boolean exist = false;
			ArrayList<Integer> class_list = new ArrayList<Integer>();
			class_list = DB.getClassNo(con);
			for(int i = 0; i < class_list.size(); i++){
				if(class_list.get(i) == class_no){
					exist = true;
					break;
				}
			}
			if(!exist){
				System.out.println("�������� �ʴ� ������ȣ�Դϴ�.");
				continue;
			}
			boolean overlap = false;
			//������ȣ ��ħ
			for(int i = 0; i < register_list.size(); i++){
				if(class_no == register_list.get(i).getClass_no()){
					System.out.println("�̹� ��û�� �����Դϴ�.");
					overlap = true;
					break;
				}
			}
			if(overlap)
				continue;
			
			//�ð��� ��ħ
			overlap = false;
			String begin = null;
			for(int i = 0; i < all_class_list.size(); i++){
				ClassInfo tmp = new ClassInfo();
				tmp = all_class_list.get(i);
				
				if(overlap)
					break;
				if(tmp.getOpened() == 2014 && tmp.getClass_no() == class_no){
					begin = tmp.getBegin();
					if(begin.equals("NO"))
						continue;
					ClassInfo reg = new ClassInfo();
					for(int j = 0; j < register_list.size(); j++){
						reg = register_list.get(j);
						if(reg.getBegin().equals("NO"))
							continue;
						if(reg.getBegin().charAt(9) == begin.charAt(9)){
							if( Integer.parseInt(reg.getBegin().substring(11, 13)) <= Integer.parseInt(begin.substring(11, 13))
									&& Integer.parseInt(begin.substring(11, 13)) < Integer.parseInt(reg.getEnd().substring(11, 13)) ){
								System.out.println("���� �ð��� ��Ĩ�ϴ�.");
								overlap = true;
								break;
							}
							else if( Integer.parseInt(begin.substring(11, 13)) == Integer.parseInt(reg.getEnd().substring(11, 13))
										&& Integer.parseInt(begin.substring(14, 16)) < Integer.parseInt(reg.getEnd().substring(14, 16)) ){
								System.out.println("���� �ð��� ��Ĩ�ϴ�.");
								overlap = true;
								break;
							}
						}
					}
				}
			}
			if(overlap)
				continue;
			
			//�ش� ������ȣ�� �м���ȣ
			String course_id = DB.getCourseID(con, class_no);	
			//register table�� insert
			if(DB.insertCourseToRegister(con, ID, class_no, course_id) == false){
				System.out.println("�̹� ��û�� �����Դϴ�.");
			}
			else{
				//�����̷¿� ������ ��������� �˸�
				for(int i = 0; i < course_list.size(); i++){
					if(course_list.get(i).equals(course_id)){
						System.out.println("������Դϴ�.");
						break;
					}
				}
				System.out.println("������û�Ǿ����ϴ�.");
				//�ǵ����� ���� �Է�
				RestoreInfo restore = new RestoreInfo();
				restore.setID(ID);
				restore.setClass_no(class_no);
				restore_list.add(restore);
			}
		}
	}
	//���� ��� - ������ȣ�� ���
	private static void startCourseCancle(int ID){

		while(true){
			//��û�� ������ȣ ����Ʈ
			ArrayList<Integer> classNo_list = new ArrayList<Integer>();
			//��û ���� ����Ʈ
			ArrayList<ClassInfo> register_list = new ArrayList<ClassInfo>();
			String sql_order = " order by c.class_no";
			register_list = DB.getRegisterInfo(con, ID, sql_order);
			//��û ���� ���
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("������ȣ\t�м���ȣ\t\t        �����\t\t\t����\t������\t�ð�\t\t\t�ǹ�\n");
			for(int i = 0; i < register_list.size(); i++){
				ClassInfo register = new ClassInfo();
				register = register_list.get(i);
				classNo_list.add(register.getClass_no());
				register.printCourseInfo(register);
			}
			while(true){
				boolean success = false;
				System.out.printf("����� ������ȣ �Է� (���� : -1)\n>");
				int input = kb.nextInt();
				kb.nextLine();
				if(input == -1)
					return;
				else{
					for(int i = 0; i < classNo_list.size(); i++){
						//��û�� ������ȣ�̸� table���� ����
						if(classNo_list.get(i) == input){
							if(DB.deleteCourseFromRegister(con, ID, input)){
								System.out.println("���� ��ҵǾ����ϴ�.");
								success = true;
								break;
							}
							else{
								System.out.println("���� ����");
								return;
							}
						}
					}
				}
				if(success)
					break;
				System.out.println("��û�� ������ �ƴմϴ�.");
			}
		}
	}
	//�ǵ�����
	private static void courseRestore(){
		for(int i = 0; i < restore_list.size(); i++){
			RestoreInfo restore = new RestoreInfo();
			restore = restore_list.get(i);
			if(!DB.deleteCourseFromRegister(con, restore.getID(), restore.getClass_no())){
				System.out.println("�ǵ����� ����");
				return;
			}
		}
		System.out.println("��û������ �ʱ���·� �����Ǿ����ϴ�.");
		restore_list.clear();
	}
	//�ð�ǥ ��ȸ
	private static void showTimeTable(int ID){
		//��û ����
		ArrayList<ClassInfo> register_list = new ArrayList<ClassInfo>();
		String sql_order = " order by t.begin";
		register_list = DB.getRegisterInfo(con, ID, sql_order);
		
		System.out.println("------------------------------------------------------------------------------------------------------------------------");
		System.out.println("����\t�ð�\t\t\t        �����\t\t\t�ǹ�");
		for(int i = 0; i < register_list.size(); i++){
			ClassInfo cls = new ClassInfo();
			cls = register_list.get(i);
			if(cls.getBegin().equals("NO"))
				continue;
			String day = cls.getDay(cls.getBegin().charAt(9));
			String start = cls.getBegin().substring(11, 16);
			int time_start = Integer.parseInt(cls.getBegin().substring(11, 13));
			String end = cls.getEnd().substring(11, 16);
			if(day.equals("�����"))
				continue;
			else if(day.equals("�ݿ���") && (time_start >= 23))
				continue;

			System.out.printf("%s\t%s ~ %s\t%30s\t\t%s\n", day, start, end, cls.getCourse_name(), cls.getBuilding());
		}
	}
	//����
	private static void startCreateCourse(){

		NewClassInfo newClass = new NewClassInfo();
		ArrayList<TimeInfo> time = new ArrayList<TimeInfo>();
		
		//class table�� ���� �Է�
		newClass = createCourseClass();
		
		//class table�� insert �� class_id�� ���� (class_id�� primary key, auto_increment)
		if(DB.insertClassInfo(con, newClass) == false){
			System.out.println("���� ���� ����");
			return;
		}
		int class_id = DB.getClassID(con, newClass.getClass_no());
		if(class_id == -1){
			System.out.println("class_id ����");
			return;
		}
		
		//time table�� ���� �Է�
		System.out.printf("�ִ� ���� Ƚ�� : ");
		int period;
		while(true){
			System.out.printf("\n>");
			period = kb.nextInt();
			kb.nextLine();
			if(period < 1){
				System.out.println("�ٽ� �Է��ϼ���.");
				continue;
			}
			else
				break;
		}
		for(int i = 0; i < period; i++){
			TimeInfo t = new TimeInfo();
			t = createCourseTime(class_id, i + 1);
			time.add(t);
		}
		
		//time table�� insert
		for(int i = 0; i < period; i++){
			TimeInfo t = new TimeInfo();
			t = time.get(i);
			if(DB.insertTimeInfo(con, t) == false){
				System.out.println("time table insert ����");
				return;
			}
		}
	}
	//���� - class table�� ���� ������ �Է�
	private static NewClassInfo createCourseClass(){
		NewClassInfo newClass = new NewClassInfo();
		
		//������ȣ ������ ���� �迭
		ArrayList<Integer> no_list = new ArrayList<Integer>();
		no_list = DB.getClassNo(con);
		int class_no;
		//������ȣ �Է� (�ߺ��� �ƴҶ�����)
		while(true){
			System.out.printf("������ȣ �Է� : ");
			class_no = kb.nextInt();
			kb.nextLine();
			
			boolean pass = true;
			for(int i = 0; i < no_list.size(); i++){
				if(no_list.get(i) == class_no){
					System.out.println("������ȣ �ߺ�");
					pass = false;
					break;
				}
			}
			if(pass)
				break;
		}
		newClass.setClass_no(class_no);
		
		//������ ���� ����
		System.out.println("������ ���� ����");
		System.out.println("------------------------------------------------------------");
		System.out.println("No.\t�м���ȣ\t\t        �����\t\t\t����");
		ArrayList<CourseInfo> course_list = new ArrayList<CourseInfo>();
		CourseInfo course = new CourseInfo();
		course_list = DB.getCourseInfo(con);
		for(int i = 1; i <= course_list.size(); i++){
			course = course_list.get(i-1);
			course.printCourseInfo(course, i);
		}
		int sel;
		while(true){
			System.out.printf(">");
			sel = kb.nextInt();
			kb.nextLine();
			if(sel > course_list.size() || sel < 1){
				System.out.println("�ٽ� �Է��ϼ���.");
				continue;
			}
			else
				break;
		}
		course = course_list.get(sel-1);
		newClass.setCourse_id(course.getCourse_id());
		newClass.setCourse_name(course.getName());
		newClass.setCredit(course.getCredit());
		
		//�����г�, �����ο� �Է�
		int year, person_max;
		while(true){
			System.out.printf("�����г�, �����ο��Է�\n>");
			year = kb.nextInt();
			person_max = kb.nextInt();
			kb.nextLine();
			if(year > 4 || year < 1 || person_max > 140 || person_max < 1){
				System.out.println("�ٽ� �Է��ϼ���.");
				continue;
			}
			else
				break;
		}
		newClass.setYear(year);
		newClass.setPerson_max(person_max);
		
		//���ǽ� ����
		System.out.println("���ǽ� ����");
		System.out.println("------------------------------------------------------------");
		System.out.println("No.\t�ǹ� �̸�\t���� �ο�");
		ArrayList<RoomInfo> room_list = new ArrayList<RoomInfo>();
		RoomInfo room = new RoomInfo();
		room_list = DB.getRoomInfo(con);
		for(int i = 1; i <= room_list.size(); i++){
			room = room_list.get(i-1);
			room.printRoomInfo(room);
		}
		while(true){
			System.out.printf(">");
			sel = kb.nextInt();
			kb.nextLine();
			if(sel > room_list.size() || sel < 1){
				System.out.println("�ٽ� �Է��ϼ���");
				continue;
			}
			if(room_list.get(sel-1).getOccupancy() < person_max){
				System.out.printf("�ش� ���ǽ��� �����ο��� �����մϴ�. �ٸ� ���ǽ��� �����ϼ���.\n");
				continue;
			} 
			break;
		}
		room = room_list.get(sel-1);
		newClass.setRoom_id(room.getRoom_id());
		
		//������ �Է�
		System.out.println("������ ����");
		System.out.println("------------------------------------------------------------");
		System.out.println("No.\t������ ��\t������");
		ArrayList<LecturerInfo> lecturer_list = new ArrayList<LecturerInfo>();
		LecturerInfo lecturer = new LecturerInfo();
		lecturer_list = DB.getLecturerInfo(con);
		for(int i = 1; i <= lecturer_list.size(); i++){
			lecturer = lecturer_list.get(i-1);
			lecturer.printLecturerInfo(lecturer, i);
		}
		while(true){
			System.out.printf(">");
			sel = kb.nextInt();
			kb.nextLine();
			if(sel > lecturer_list.size() || sel < 1){
				System.out.println("�ٽ� �Է��ϼ���.");
				continue;
			}
			else
				break;
		}
		lecturer = lecturer_list.get(sel-1);
		newClass.setLecturer_id(lecturer.getLecturer_id());
		newClass.setMajor_id(lecturer.getMajor_id());
		
		newClass.setOpened(2014);
		return newClass;
	}
	//���� - time table�� ���� ������ �Է�
	private static TimeInfo createCourseTime(int class_id, int period){

		TimeInfo time = new TimeInfo();
		int begin_h, begin_m, end_h, end_m;
		
		time.setClass_id(class_id);
		time.setPeriod(period);
		
		while(true){
			//���� �Է�
			System.out.printf("���� ���� \n");
			MenuClass.showDayMenu();
			int day;
			while(true){
				day = kb.nextInt();
				kb.nextLine();
				if(day > 7 || day < 1){
					System.out.println("�ٽ� �Է��ϼ���");
					continue;
				}
				else
					break;
			}
			String begin, end;
			//�������� ���
			if(day == 7){
				begin = "NO";
				end = "NO";
			}
			else{
				//���� �ð� ����
				System.out.printf("���� �ð� ���� (�Է� ���� : 09:00)\n");
				System.out.printf("���� �ð� : ");
				begin = kb.nextLine();
				
				StringTokenizer st = new StringTokenizer(begin, ":");
				begin_h = Integer.parseInt(st.nextToken());
				begin_m = Integer.parseInt(st.nextToken());
				System.out.printf("���� �ð� : ");
				end = kb.nextLine();
				st = new StringTokenizer(end, ":");
				end_h = Integer.parseInt(st.nextToken());
				end_m = Integer.parseInt(st.nextToken());
				
				//�Է��� �̻��ϸ� ���Է�
				if(begin_h > end_h){
					System.out.println("�߸��� �Է�");
					continue;
				}
				else if(begin_h == end_h){
					if(begin_m > end_m){
						System.out.println("�߸��� �Է�");
						continue;
					}
				}
			}
			time.setBegin(begin, day);
			time.setEnd(end, day);
			break;
		}
		return time;
	}
	//��
	private static void startDeleteCourse(){
		
		ArrayList<Integer> classNo_list = new ArrayList<Integer>();
		ArrayList<ClassInfo> class_list = new ArrayList<ClassInfo>();
		class_list = DB.getClassInfo(con);
		
		System.out.println("------------------------------------------------------------");
		System.out.println("������ȣ\t�м���ȣ\t\t        �����\t\t\t����\t������\t�ð�\t\t\t�ǹ�\n");
		for(int i = 0; i < class_list.size(); i++){
			ClassInfo cls = new ClassInfo();
			cls = class_list.get(i);
			cls.printCourseInfo(cls);
			if(cls.getOpened() == 2014)
				classNo_list.add(cls.getClass_no());
		}
		while(true){
			System.out.printf("���� ������ȣ �Է� (���� : -1)\n>");
			int input = kb.nextInt();
			kb.nextLine();
			if(input == -1)
				return;
			else{
				for(int i = 0; i < classNo_list.size(); i++){
					if(input == classNo_list.get(i)){
						int class_id = DB.getClassID(con, input);
						if(DB.deleteClassInfo(con, input)){
							if(DB.deleteTimeInfo(con, class_id)){
								System.out.println("�󰭵Ǿ����ϴ�.");
								return;
							}
							System.out.println("�� ���� - time");
						}
						System.out.println("�䰭 ���� - class");
					}
				}
				System.out.println("�������� �ʴ� ������ȣ�Դϴ�.");
			}
		}
	}
	//���� ���
	private static void startCourseStats(){
	
		ArrayList<MajorInfo> major_list = new ArrayList<MajorInfo>();
		major_list = DB.getMajorInfo(con);
		for(int i = 0; i < major_list.size(); i++){
			MajorInfo major = new MajorInfo();
			major = major_list.get(i);
			major.printInfo(major);
		}
		System.out.printf("�к� ����\n>");
		int major_id = kb.nextInt();
		kb.nextLine();
		
		int arr[][] = new int[20][4];
		ArrayList<StatsInfo> stats_list = new ArrayList<StatsInfo>();
		ArrayList<String> name_list = new ArrayList<String>();
		name_list = DB.getClassNameByMajor(con, major_id);
		
		for(int i = 0; i < name_list.size(); i++){
			StatsInfo stats = new StatsInfo();
			stats.setName(name_list.get(i));
			stats_list.add(stats);
		}
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 4; j++){
				arr[i][j] = 0;
			}
		}
		
		System.out.printf("%30s\t\t2014\t2013\t2012\t2011\n", "�����");
		//2014
		stats_list = DB.getStatsInfo(con, "class c", 2014, major_id);
		for(int i = 0; i < name_list.size(); i++){
			for(int j = 0; j < stats_list.size(); j++){
				if(name_list.get(i).equals(stats_list.get(j).getName())){
					arr[i][0] = stats_list.get(j).getCount();
				}
			}
		}
		//2013
		stats_list.clear();
		stats_list = DB.getStatsInfo(con, "class_past c", 2013, major_id);
		for(int i = 0; i < name_list.size(); i++){
			for(int j = 0; j < stats_list.size(); j++){
				if(name_list.get(i).equals(stats_list.get(j).getName())){
					arr[i][1] = stats_list.get(j).getCount();
				}
			}
		}
		//2012
		stats_list.clear();
		stats_list = DB.getStatsInfo(con, "class_past c", 2012, major_id);
		for(int i = 0; i < name_list.size(); i++){
			for(int j = 0; j < stats_list.size(); j++){
				if(name_list.get(i).equals(stats_list.get(j).getName())){
					arr[i][2] = stats_list.get(j).getCount();
				}
			}
		}
		//2011
		stats_list.clear();
		stats_list = DB.getStatsInfo(con, "class_past c", 2011, major_id);
		for(int i = 0; i < name_list.size(); i++){
			for(int j = 0; j < stats_list.size(); j++){
				if(name_list.get(i).equals(stats_list.get(j).getName())){
					arr[i][3] = stats_list.get(j).getCount();
				}
			}
		}
		//���
		for(int i = 0; i < name_list.size(); i++){
			System.out.printf("%30s\t\t", name_list.get(i));
			for(int j = 0; j < 4; j++){
				System.out.printf("%d\t", arr[i][j]);
			}
			System.out.printf("\n");
		}
	}
}
