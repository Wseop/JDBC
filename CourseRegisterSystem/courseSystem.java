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
		
		/* DB에 접근하기 위한 정보 입력 */
		String DB_ID;
		int ID;
		String PW;
		
		System.out.println("=== DB 로그인 ===");
		System.out.printf("ID : ");
		DB_ID = kb.nextLine();
		System.out.printf("Password : ");
		PW = kb.nextLine();
		
		try{
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db", DB_ID, PW);
			if(con != null)
				System.out.println("DB 연결성공");
		}
		catch (SQLException e) {
			// TODO: handle exception
			System.out.println("DB 연결 에러");
			e.printStackTrace();
			return;
		}
		
		/* 사용자 로그인 */
		while(true){
			System.out.printf("\n=== 수강신청 시스템 로그인 ===\n");
			MenuClass.showLoginMenu();
			int sel;
			while(true){
				sel = kb.nextInt();
				kb.nextLine();
				if(sel < 1 || sel > 3){
					System.out.println("잘못된 입력");
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
			//1. 사용자
			if(sel == 1){
				if(LoginManager.isCorrectInfo(ID, PW, con)){
					System.out.println("로그인 성공");
					//수강신청 프로그램 실행 - 사용자
					while(startStudentSystem(ID));
				}
				else{
					System.out.println("로그인 실패");
				}
			}
			//2. 관리자
			else if(sel == 2){
				if(LoginManager.isAdminID(ID)){
					if(LoginManager.isAdminPW(PW)){
						System.out.println("로그인 성공");
						//수강신청 프로그램 실행 - 관리자
						while(startAdminSystem(ID));
					}
					else
						System.out.println("비밀번호가 일치하지 않습니다.");
				}
				else{
					System.out.println("관리자 ID가 일치하지 않습니다.");
				}
			}
		}
	}
	private static boolean startStudentSystem(int ID){
		MenuClass.showClientMenu();
		int sel = kb.nextInt();
		kb.nextLine();
		switch(sel){
		//1.수강편람 2.수강신청 3.수강취소 4.되돌리기 5.시간표 조회 6.종료
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
				System.out.println("잘못된 입력");
				return false;
		}
	}
	private static boolean startAdminSystem(int ID){
		MenuClass.showAdminMenu();
		int sel = kb.nextInt();
		kb.nextLine();
		switch(sel){
		//1.수강편람 2.설강 3.폐강 4.통계 5.종료
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
				System.out.println("잘못된 입력");
				return false;
		}
	}
	//수강 편람
	private static void startCourseCatalog(){
		
		ArrayList<ClassInfo> class_list = new ArrayList<ClassInfo>();
		ClassInfo course = new ClassInfo();
		
		class_list = DB.getClassInfo(con);
		MenuClass.showCourseMenu();
		int sel = kb.nextInt();
		kb.nextLine();
		
		switch(sel){
			//전체 조회
			case 1:
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.printf("수업번호\t학수번호\t\t        과목명\t\t\t학점\t교강사\t시간\t\t\t건물\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printCourseInfo(course);
				}
				break;
			//수업번호로 조회
			case 2:
				System.out.print("수업번호 입력 : ");
				int class_no = kb.nextInt();
				kb.nextLine();
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println("수업번호\t학수번호\t\t        과목명\t\t\t학점\t교강사\t시간\t\t\t건물\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printClassInfoByNO(course, class_no);
				}
				break;
			//학수번호로 조회
			case 3:
				System.out.print("학수번호 입력 : ");
				String id = kb.nextLine();
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println("수업번호\t학수번호\t\t        과목명\t\t\t학점\t교강사\t시간\t\t\t건물\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printClassInfoByID(course, id);
				}
				break;
			//과목명으로 조회
			case 4:
				System.out.print("과목명 입력 : ");
				String course_name = kb.nextLine();
				System.out.println("------------------------------------------------------------------------------------------------------------------------");
				System.out.println("수업번호\t학수번호\t\t        과목명\t\t\t학점\t교강사\t시간\t\t\t건물\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printClassInfoByCName(course, course_name);
				}
				break;
			//강사명으로 조회
			case 5:
				System.out.print("강사명 입력 : ");
				String lec_name = kb.nextLine();
				System.out.println("------------------------------------------------------------");
				System.out.println("수업번호\t학수번호\t\t        과목명\t\t\t학점\t교강사\t시간\t\t\t건물\n");
				for(int i = 0; i < class_list.size(); i++){
					course = class_list.get(i);
					course.printClassInfoByLName(course, lec_name);
				}
				break;
		}
	}
	//수강 신청 - 수업번호로 신청
	private static void startCourseRegister(int ID){
		
		//ID학생의 수강이력 학수번호
		ArrayList<String> course_list = new ArrayList<String>();
		course_list = DB.getRegisteredCourseID(con, ID);
		
		//수강편람 전체 정보
		ArrayList<ClassInfo> all_class_list = new ArrayList<ClassInfo>();
		all_class_list = DB.getClassInfo(con);
		
		while(true){
			//신청내역
			ArrayList<ClassInfo> register_list = new ArrayList<ClassInfo>();
			String sql_order = " order by t.begin";
			register_list = DB.getRegisterInfo(con, ID, sql_order);
			
			System.out.printf("\n수업 번호 입력(종료 : -1)\n>");
			int class_no = kb.nextInt();
			kb.nextLine();
			if(class_no == -1){
				break;
			}
			
			//수업번호 리스트에 존재하지 않으면 재입력
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
				System.out.println("존재하지 않는 수업번호입니다.");
				continue;
			}
			boolean overlap = false;
			//수업번호 겹침
			for(int i = 0; i < register_list.size(); i++){
				if(class_no == register_list.get(i).getClass_no()){
					System.out.println("이미 신청한 수업입니다.");
					overlap = true;
					break;
				}
			}
			if(overlap)
				continue;
			
			//시간이 겹침
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
								System.out.println("수업 시간이 겹칩니다.");
								overlap = true;
								break;
							}
							else if( Integer.parseInt(begin.substring(11, 13)) == Integer.parseInt(reg.getEnd().substring(11, 13))
										&& Integer.parseInt(begin.substring(14, 16)) < Integer.parseInt(reg.getEnd().substring(14, 16)) ){
								System.out.println("수업 시간이 겹칩니다.");
								overlap = true;
								break;
							}
						}
					}
				}
			}
			if(overlap)
				continue;
			
			//해당 수업번호의 학수번호
			String course_id = DB.getCourseID(con, class_no);	
			//register table에 insert
			if(DB.insertCourseToRegister(con, ID, class_no, course_id) == false){
				System.out.println("이미 신청한 수업입니다.");
			}
			else{
				//수강이력에 있으면 재수강임을 알림
				for(int i = 0; i < course_list.size(); i++){
					if(course_list.get(i).equals(course_id)){
						System.out.println("재수강입니다.");
						break;
					}
				}
				System.out.println("수강신청되었습니다.");
				//되돌리기 정보 입력
				RestoreInfo restore = new RestoreInfo();
				restore.setID(ID);
				restore.setClass_no(class_no);
				restore_list.add(restore);
			}
		}
	}
	//수강 취소 - 수업번호로 취소
	private static void startCourseCancle(int ID){

		while(true){
			//신청한 수업번호 리스트
			ArrayList<Integer> classNo_list = new ArrayList<Integer>();
			//신청 내역 리스트
			ArrayList<ClassInfo> register_list = new ArrayList<ClassInfo>();
			String sql_order = " order by c.class_no";
			register_list = DB.getRegisterInfo(con, ID, sql_order);
			//신청 내역 출력
			System.out.println("------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("수업번호\t학수번호\t\t        과목명\t\t\t학점\t교강사\t시간\t\t\t건물\n");
			for(int i = 0; i < register_list.size(); i++){
				ClassInfo register = new ClassInfo();
				register = register_list.get(i);
				classNo_list.add(register.getClass_no());
				register.printCourseInfo(register);
			}
			while(true){
				boolean success = false;
				System.out.printf("취소할 수업번호 입력 (종료 : -1)\n>");
				int input = kb.nextInt();
				kb.nextLine();
				if(input == -1)
					return;
				else{
					for(int i = 0; i < classNo_list.size(); i++){
						//신청한 수업번호이면 table에서 제거
						if(classNo_list.get(i) == input){
							if(DB.deleteCourseFromRegister(con, ID, input)){
								System.out.println("수강 취소되었습니다.");
								success = true;
								break;
							}
							else{
								System.out.println("삭제 에러");
								return;
							}
						}
					}
				}
				if(success)
					break;
				System.out.println("신청한 수업이 아닙니다.");
			}
		}
	}
	//되돌리기
	private static void courseRestore(){
		for(int i = 0; i < restore_list.size(); i++){
			RestoreInfo restore = new RestoreInfo();
			restore = restore_list.get(i);
			if(!DB.deleteCourseFromRegister(con, restore.getID(), restore.getClass_no())){
				System.out.println("되돌리기 에러");
				return;
			}
		}
		System.out.println("신청내역이 초기상태로 복구되었습니다.");
		restore_list.clear();
	}
	//시간표 조회
	private static void showTimeTable(int ID){
		//신청 내역
		ArrayList<ClassInfo> register_list = new ArrayList<ClassInfo>();
		String sql_order = " order by t.begin";
		register_list = DB.getRegisterInfo(con, ID, sql_order);
		
		System.out.println("------------------------------------------------------------------------------------------------------------------------");
		System.out.println("요일\t시간\t\t\t        과목명\t\t\t건물");
		for(int i = 0; i < register_list.size(); i++){
			ClassInfo cls = new ClassInfo();
			cls = register_list.get(i);
			if(cls.getBegin().equals("NO"))
				continue;
			String day = cls.getDay(cls.getBegin().charAt(9));
			String start = cls.getBegin().substring(11, 16);
			int time_start = Integer.parseInt(cls.getBegin().substring(11, 13));
			String end = cls.getEnd().substring(11, 16);
			if(day.equals("토요일"))
				continue;
			else if(day.equals("금요일") && (time_start >= 23))
				continue;

			System.out.printf("%s\t%s ~ %s\t%30s\t\t%s\n", day, start, end, cls.getCourse_name(), cls.getBuilding());
		}
	}
	//설강
	private static void startCreateCourse(){

		NewClassInfo newClass = new NewClassInfo();
		ArrayList<TimeInfo> time = new ArrayList<TimeInfo>();
		
		//class table의 정보 입력
		newClass = createCourseClass();
		
		//class table에 insert 후 class_id를 구함 (class_id는 primary key, auto_increment)
		if(DB.insertClassInfo(con, newClass) == false){
			System.out.println("수업 설강 에러");
			return;
		}
		int class_id = DB.getClassID(con, newClass.getClass_no());
		if(class_id == -1){
			System.out.println("class_id 에러");
			return;
		}
		
		//time table의 정보 입력
		System.out.printf("주당 강의 횟수 : ");
		int period;
		while(true){
			System.out.printf("\n>");
			period = kb.nextInt();
			kb.nextLine();
			if(period < 1){
				System.out.println("다시 입력하세요.");
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
		
		//time table에 insert
		for(int i = 0; i < period; i++){
			TimeInfo t = new TimeInfo();
			t = time.get(i);
			if(DB.insertTimeInfo(con, t) == false){
				System.out.println("time table insert 에러");
				return;
			}
		}
	}
	//설강 - class table에 대한 정보를 입력
	private static NewClassInfo createCourseClass(){
		NewClassInfo newClass = new NewClassInfo();
		
		//수업번호 정보를 가진 배열
		ArrayList<Integer> no_list = new ArrayList<Integer>();
		no_list = DB.getClassNo(con);
		int class_no;
		//수업번호 입력 (중복이 아닐때까지)
		while(true){
			System.out.printf("수업번호 입력 : ");
			class_no = kb.nextInt();
			kb.nextLine();
			
			boolean pass = true;
			for(int i = 0; i < no_list.size(); i++){
				if(no_list.get(i) == class_no){
					System.out.println("수업번호 중복");
					pass = false;
					break;
				}
			}
			if(pass)
				break;
		}
		newClass.setClass_no(class_no);
		
		//설강할 수업 선택
		System.out.println("설강할 수업 선택");
		System.out.println("------------------------------------------------------------");
		System.out.println("No.\t학수번호\t\t        과목명\t\t\t학점");
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
				System.out.println("다시 입력하세요.");
				continue;
			}
			else
				break;
		}
		course = course_list.get(sel-1);
		newClass.setCourse_id(course.getCourse_id());
		newClass.setCourse_name(course.getName());
		newClass.setCredit(course.getCredit());
		
		//개설학년, 수용인원 입력
		int year, person_max;
		while(true){
			System.out.printf("개설학년, 수용인원입력\n>");
			year = kb.nextInt();
			person_max = kb.nextInt();
			kb.nextLine();
			if(year > 4 || year < 1 || person_max > 140 || person_max < 1){
				System.out.println("다시 입력하세요.");
				continue;
			}
			else
				break;
		}
		newClass.setYear(year);
		newClass.setPerson_max(person_max);
		
		//강의실 선택
		System.out.println("강의실 선택");
		System.out.println("------------------------------------------------------------");
		System.out.println("No.\t건물 이름\t수용 인원");
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
				System.out.println("다시 입력하세요");
				continue;
			}
			if(room_list.get(sel-1).getOccupancy() < person_max){
				System.out.printf("해당 강의실의 수용인원이 부족합니다. 다른 강의실을 선택하세요.\n");
				continue;
			} 
			break;
		}
		room = room_list.get(sel-1);
		newClass.setRoom_id(room.getRoom_id());
		
		//교강사 입력
		System.out.println("교강사 선택");
		System.out.println("------------------------------------------------------------");
		System.out.println("No.\t교강사 명\t전공명");
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
				System.out.println("다시 입력하세요.");
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
	//설강 - time table에 대한 정보를 입력
	private static TimeInfo createCourseTime(int class_id, int period){

		TimeInfo time = new TimeInfo();
		int begin_h, begin_m, end_h, end_m;
		
		time.setClass_id(class_id);
		time.setPeriod(period);
		
		while(true){
			//요일 입력
			System.out.printf("요일 선택 \n");
			MenuClass.showDayMenu();
			int day;
			while(true){
				day = kb.nextInt();
				kb.nextLine();
				if(day > 7 || day < 1){
					System.out.println("다시 입력하세요");
					continue;
				}
				else
					break;
			}
			String begin, end;
			//미지정일 경우
			if(day == 7){
				begin = "NO";
				end = "NO";
			}
			else{
				//강의 시간 설정
				System.out.printf("강의 시간 설정 (입력 예시 : 09:00)\n");
				System.out.printf("시작 시간 : ");
				begin = kb.nextLine();
				
				StringTokenizer st = new StringTokenizer(begin, ":");
				begin_h = Integer.parseInt(st.nextToken());
				begin_m = Integer.parseInt(st.nextToken());
				System.out.printf("종료 시간 : ");
				end = kb.nextLine();
				st = new StringTokenizer(end, ":");
				end_h = Integer.parseInt(st.nextToken());
				end_m = Integer.parseInt(st.nextToken());
				
				//입력이 이상하면 재입력
				if(begin_h > end_h){
					System.out.println("잘못된 입력");
					continue;
				}
				else if(begin_h == end_h){
					if(begin_m > end_m){
						System.out.println("잘못된 입력");
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
	//폐강
	private static void startDeleteCourse(){
		
		ArrayList<Integer> classNo_list = new ArrayList<Integer>();
		ArrayList<ClassInfo> class_list = new ArrayList<ClassInfo>();
		class_list = DB.getClassInfo(con);
		
		System.out.println("------------------------------------------------------------");
		System.out.println("수업번호\t학수번호\t\t        과목명\t\t\t학점\t교강사\t시간\t\t\t건물\n");
		for(int i = 0; i < class_list.size(); i++){
			ClassInfo cls = new ClassInfo();
			cls = class_list.get(i);
			cls.printCourseInfo(cls);
			if(cls.getOpened() == 2014)
				classNo_list.add(cls.getClass_no());
		}
		while(true){
			System.out.printf("폐강할 수업번호 입력 (종료 : -1)\n>");
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
								System.out.println("폐강되었습니다.");
								return;
							}
							System.out.println("폐강 에러 - time");
						}
						System.out.println("페강 에러 - class");
					}
				}
				System.out.println("존재하지 않는 수업번호입니다.");
			}
		}
	}
	//과목 통계
	private static void startCourseStats(){
	
		ArrayList<MajorInfo> major_list = new ArrayList<MajorInfo>();
		major_list = DB.getMajorInfo(con);
		for(int i = 0; i < major_list.size(); i++){
			MajorInfo major = new MajorInfo();
			major = major_list.get(i);
			major.printInfo(major);
		}
		System.out.printf("학부 선택\n>");
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
		
		System.out.printf("%30s\t\t2014\t2013\t2012\t2011\n", "과목명");
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
		//출력
		for(int i = 0; i < name_list.size(); i++){
			System.out.printf("%30s\t\t", name_list.get(i));
			for(int j = 0; j < 4; j++){
				System.out.printf("%d\t", arr[i][j]);
			}
			System.out.printf("\n");
		}
	}
}
