package ddit.dao;

import java.util.List;

import ddit.dto.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 회원관련 DAO 클래스
 * */
public class MemberDAO {
    Connection conn = null; // 자바와 오라클에 대한 연결 설정
    PreparedStatement pstm = null; // SQL문을 담는 객체 // sql문을 수행하기 위한 객체
    ResultSet rs = null; // statement 동작에 대한 결과로 전달되는 DB의 내용..!
	
    private static MemberDAO instance = null;
    
    public MemberDAO() {	}
	
    //싱글톤패턴
	public static MemberDAO getInstance() {
		if(instance == null) instance = new MemberDAO();
		return instance;
	}
    
	// 아이디 중복검사
	public boolean checkId(String id) {
		String query = "SELECT COUNT(MID) FROM MEMBER WHERE MID = ?";
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, id);
			rs = pstm.executeQuery();

			rs.next(); // 행
			if (rs.getInt(1)/* 열 */ == 1) {
				return true;
			}
			
			// 연결과 역순으로 해제
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// 회원가입
	public int join(Member user) {
		int result = 0;
		/**
		CREATE SEQUENCE [시퀀스명]; 을 통하여 자동으로 중복이 되지 않는 시퀀스를 만들어 줍니다.
		사용은 [시퀀스명].NEXTVAL 로 사용하면 됩니다.
		*/
		String query = "INSERT INTO MEMBER (NNO, MID, MPW, MNAME, MPHONE, MADDRESS, MPOINT) "
				+ "VALUES(MEMBER_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(query);

			pstm.setString(1, user.getmId());
			pstm.setString(2, user.getmPw());
			pstm.setString(3, user.getmName());
			pstm.setString(4, user.getmPhone());
			pstm.setString(5, user.getmAddress());
			pstm.setInt(6, user.getmPoint());

			result = pstm.executeUpdate();
			conn.commit();
			
			DAO.close(pstm);
			DAO.close(conn);
			
		}catch (Exception e) {
			e.printStackTrace();
	          if(conn!=null) {
	              try{
	                  conn.rollback();
	                  System.out.println("rollback");
	              }catch(SQLException sqle) { }
	          }
		}
		return result;
	}

	// 로그인
	public int login(String id, String pw) {
		String sql = "SELECT MPW, MNAME FROM MEMBER WHERE MID = ?"; // 실제로 DB에 입력될 명령어를 SQL 문장으로 만듬.
		try {
			conn = DAO.getConnection();;
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			rs = pstm.executeQuery(); // 어떠한 결과를 받아오는 ResultSet 타입의 rs 변수에 쿼리문을 실행한 결과를 넣어줌 
			if(rs.next()) {
				if(rs.getString(1).contentEquals(pw)) {
					return 1;	//로그인 성공
				}else {
					return 0; 	//비밀번호 불일치
				}
			}
			// 연결과 역순으로 해제
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);
			
			return -1; //아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -2;	//DB오류
	}


	//  내 회원정보 조회
	public List<Member> memberSelect(String id) {
		List<Member> list = new ArrayList<>(); //반환할 리스트를 위해 list 객체 생성
		try {
			conn = DAO.getConnection();
			String sql = "SELECT mID, mPW, mName, mPhone, mAddress, mPoint FROM MEMBER WHERE MID=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			rs = pstm.executeQuery();		//SELECT 문과 같이 여러 개의 행로 결과가 반환될 때 사용
			
			while(rs.next()) {			//읽을 행이 있을 때
				String mID = rs.getString("mID");
				String mPW = rs.getString("mPW");
				String mName = rs.getString("mName");
				String mPhone = rs.getString("mPhone");
				String mAddress = rs.getString("mAddress");
				int mPoint = rs.getInt("mPoint");
				Member member = new Member(mID, mPW, mName, mPhone, mAddress, mPoint);
				list.add(member);
			}
			
			// 연결과 역순으로 해제
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
