package courseSystem;

public class MenuClass {

	public static void showLoginMenu(){
		System.out.printf(
				"\n---------------\n1.사용자 로그인\n2.관리자 로그인\n3.프로그램 종료\n>");
	}
	
	public static void showClientMenu(){
		System.out.printf(
				"\n-------------\n1.수강편람\n2.수강신청\n3.수강취소\n4.되돌리기\n5.시간표 조회\n6.로그아웃\n>");
	}
	public static void showAdminMenu(){
		System.out.printf(
				"\n----------\n1.수강편람\n2.설강\n3.폐강\n4.통계\n5.로그아웃\n>");
	}
	public static void showCourseMenu(){
		System.out.printf("\n----------------\n1.전체 조회\n2.수업번호 조회\n3.학수번호 조회\n4.과목명 조회\n5.강사명 조회\n>");
	}
	public static void showDayMenu(){
		System.out.printf("1.월요일\n2.화요일\n3.수요일\n4.목요일\n5.금요일\n6.토요일\n7.미지정\n>");
	}
}
