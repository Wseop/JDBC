# JDBC - Practice
## Course Register System
### 1. 개요
JDBC 프로그래밍을 활용한 수강 신청 시스템
### 2. 구현 내용
유저 타입(관리자 or 사용자)에 따라 수강편람, 수강신청, 강의개설, 강의폐지 등의 기능을 구현
#### 2-1. DB 생성
MySQL을 사용하였으며, CSV파일을 import하여 아래 9개의 Table을 생성
* building
* class
* course
* credits
* lecturer
* major
* room
* student
* time
#### 2-2. 관리자 기능
1. 수강편람
2. 설강
3. 폐강
4. 통계
#### 2-3. 사용자 기능
1. 수강편람
2. 수강신청
3. 수강취소
4. 시간표 조회
