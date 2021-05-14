package courseSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//DB에서 데이터를 로드하는 기능을 하는 클래스
public class DB {

	private static PreparedStatement pstmt;
	private static ResultSet rs;
	private static Statement stmt;
	
	//DB의 student table에서 학번과 비밀번호 정보만 가져와서 배열리스트에 저장
	public static ArrayList<LoginInfo> getLoginInfo(Connection con){
		ArrayList<LoginInfo> student_list = new ArrayList<LoginInfo>();
		String sql = "select s.﻿student_id, s.password from student s";
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				LoginInfo tmp = new LoginInfo(rs.getInt(1), rs.getString(2));
				student_list.add(tmp);
			}
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return student_list;
	}
	//수강 편람 전체 정보 로드
	public static ArrayList<ClassInfo> getClassInfo(Connection con){
		ArrayList<ClassInfo> class_list = new ArrayList<ClassInfo>();
		String sql = "select c.class_no, c.course_id, c.name, l.name, c.credit, t.begin, t.end, b.name, c.opened" 
				+ " from class c, lecturer l, time t, room r, building b"
				+ " where c.lecturer_id = l.﻿lecturer_id AND c.﻿class_id = t.class_id AND c.room_id = r.﻿room_id AND r.building_id = b.﻿building_id"
				+ " order by c.class_no";
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				ClassInfo tmp = new ClassInfo(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9));
				class_list.add(tmp);
			}
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return class_list;
	}
	//class table에서 수업번호 정보(2014) 로드
	public static ArrayList<Integer> getClassNo(Connection con){
		ArrayList<Integer> no_list = new ArrayList<Integer>();
		String sql = "select class_no"
				+ " from class"
				+ " where opened = 2014";
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Integer no = rs.getInt(1);
				no_list.add(no);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return no_list;
	}
	//course table의 정보 로드
	public static ArrayList<CourseInfo> getCourseInfo(Connection con){
		ArrayList<CourseInfo> course_list = new ArrayList<CourseInfo>();
		String sql = "select * from course";
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				CourseInfo tmp = new CourseInfo(rs.getString(1), rs.getString(2), rs.getInt(3));
				course_list.add(tmp);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return course_list;
	}
	//교강사, 전공 정보 로드
	public static ArrayList<LecturerInfo> getLecturerInfo(Connection con){
		ArrayList<LecturerInfo> lecturer_list = new ArrayList<LecturerInfo>();
		String sql = "select l.﻿lecturer_id, l.name, m.﻿major_id, m.name"
				+ " from lecturer l, major m"
				+ " where l.major_id = m.﻿major_id";
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				LecturerInfo tmp = new LecturerInfo(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				lecturer_list.add(tmp);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return lecturer_list;
	}
	//강의실 정보 로드
	public static ArrayList<RoomInfo> getRoomInfo(Connection con){
		ArrayList<RoomInfo> room_list = new ArrayList<RoomInfo>();
		String sql = "select r.﻿room_id, b.name, r.occupancy"
				+ " from building b, room r"
				+ " where r.building_id = b.﻿building_id";
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				RoomInfo tmp = new RoomInfo(rs.getInt(1), rs.getString(2), rs.getInt(3));
				room_list.add(tmp);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return room_list;
	}
	//새로 입력한 강의 class table에 insert
	public static boolean insertClassInfo(Connection con, NewClassInfo newClass){
		pstmt = null;
		String sql = "insert into class values(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, newClass.getClass_no());
			pstmt.setString(2, newClass.getCourse_id());
			pstmt.setString(3, newClass.getCourse_name());
			pstmt.setInt(4, newClass.getMajor_id());
			pstmt.setInt(5, newClass.getYear());
			pstmt.setInt(6, newClass.getCredit());
			pstmt.setInt(7, newClass.getLecturer_id());
			pstmt.setInt(8, newClass.getPerson_max());
			pstmt.setInt(9, newClass.getOpened());
			pstmt.setInt(10, newClass.getRoom_id());
			
			int result = pstmt.executeUpdate();
			if(result <= 0){
				System.out.println("class table insert 에러");
				return false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	public static int getClassID(Connection con, int class_no){
		int no = -1;
		String sql = "select c.﻿class_id"
				+ " from class c"
				+ " where c.opened = 2014 AND c.class_no = " + class_no;
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				no = rs.getInt(1);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return no;
	}
	//설강 내역 time table에 insert
	public static boolean insertTimeInfo(Connection con, TimeInfo time){
		pstmt = null;
		String sql = "insert into time values(NULL, ?, ?, ?, ?)";
		
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, time.getClass_id());
			pstmt.setInt(2, time.getPeriod());
			pstmt.setString(3, time.getBegin());
			pstmt.setString(4, time.getEnd());
			
			int result = pstmt.executeUpdate();
			if(result <= 0){
				System.out.println("time table insert 에러");
				return false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	//폐강 - class table에서 제거
	public static boolean deleteClassInfo(Connection con, int class_no){
		pstmt = null;
		String sql = "delete from class where opened = 2014 AND class_no = ?";
		
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, class_no);
			
			int result = pstmt.executeUpdate();
			if(result <= 0){
				System.out.println("class table delete 에러");
				return false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	//폐강 - time table에서 제거
	public static boolean deleteTimeInfo(Connection con, int class_id){
		pstmt = null;
		String sql = "delete from time where class_id = ?";
		
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, class_id);
			
			int result = pstmt.executeUpdate();
			if(result <= 0){
				System.out.println("time table delete 에러");
				return false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	//해당 학생의 수강내역 학수번호 로드
	public static ArrayList<String> getRegisteredCourseID(Connection con, int ID){
		ArrayList<String> course_list = new ArrayList<String>();
		String sql = "select cl.course_id"
				+ " from class cl, credits cr"
				+ " where cl.﻿class_id = cr.class_id AND cr.student_id = " + ID;
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				course_list.add(rs.getString(1));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return course_list;
	}
	//해당 수업번호의 학수번호 (2014) 로드
	public static String getCourseID(Connection con, int class_no){
		String course_id = null;
		String sql = "select c.course_id"
				+ " from class c"
				+ " where c.opened = 2014 AND c.class_no = " + class_no;
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				course_id = rs.getString(1);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return course_id;
	}
	//신청 강의 register table에 insert
	public static boolean insertCourseToRegister(Connection con, int ID, int class_no, String course_id){
		pstmt = null;
		String sql = "insert ignore into register values(?, ?, ?, ?)";
		
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ID);
			pstmt.setInt(2, class_no);
			pstmt.setString(3, course_id);
			pstmt.setString(4, ID+course_id);
			
			int result = pstmt.executeUpdate();
			if(result == 0){
				return false;
			}
			else if(result < 0){
				System.out.println("register table insert 에러");
				return false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	//해당 학생의 수강신청 정보 로드
	public static ArrayList<ClassInfo> getRegisterInfo(Connection con, int ID, String sql_order){
		ArrayList<ClassInfo> register_list = new ArrayList<ClassInfo>();
		String sql = "select c.class_no, c.course_id, c.name, l.name, c.credit, t.begin, t.end, b.name, c.opened"
				+ " from class c, register r, time t, lecturer l, room rm, building b"
				+ " where r.student_id = " + ID +" AND c.opened = 2014 AND c.lecturer_id = l.﻿lecturer_id AND c.﻿class_id = t.class_id AND c.room_id = rm.﻿room_id AND rm.building_id = b.﻿building_id AND c.class_no = r.class_no "
				+ sql_order;
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				ClassInfo register = new ClassInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9));
				register_list.add(register);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return register_list;
	}
	//register table에서 ID와 수업번호가 일치하는 데이터 삭제 - 수강취소
	public static boolean deleteCourseFromRegister(Connection con, int ID, int class_no){
		pstmt = null;
		String sql = "delete from register where student_id = ? AND class_no = ?";
		
		try{
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ID);
			pstmt.setInt(2, class_no);
			
			int result = pstmt.executeUpdate();
			if(result <= 0){
				System.out.println("register table delete 에러");
				return false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	//major table의 정보 로드
	public static ArrayList<MajorInfo> getMajorInfo(Connection con){
		ArrayList<MajorInfo> major_list = new ArrayList<MajorInfo>();
		String sql = "select m.﻿major_id, m.name"
				+ " from major m";
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				MajorInfo major = new MajorInfo(rs.getInt(1), rs.getString(2));
				major_list.add(major);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return major_list;
	}
	//수강 통계
	public static ArrayList<StatsInfo> getStatsInfo(Connection con, String table, int opened, int major_id){
		ArrayList<StatsInfo> stats_list = new ArrayList<StatsInfo>();
		String sql = "select c.name, count(*)"
				+ " from " + table + ", credits cr"
				+ " where c.major_id = " + major_id + " AND c.﻿class_id = cr.class_id AND c.opened = " + opened
				+ " group by c.name";
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				StatsInfo stats = new StatsInfo(rs.getString(1), rs.getInt(2));
				stats_list.add(stats);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return stats_list;
	}
	//전공 별 과목 이름 로드
	public static ArrayList<String> getClassNameByMajor(Connection con, int major_id){
		ArrayList<String> name_list = new ArrayList<String>();
		String sql = "select c.name"
				+ " from class c"
				+ " where c.major_id = " + major_id;
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				String name = rs.getString(1);
				name_list.add(name);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return name_list;
	}
}