package ddit.dao;

import java.util.List;

import ddit.dto.Member;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	    // 결과를 저장할 리스트를 초기화합니다.
	    List<Object[]> resultList = DAO.selectList(query, id);

	    // 결과 리스트가 비어있지 않은 경우 실행합니다.
	    if (!resultList.isEmpty()) {
	        // 결과 리스트에서 첫 번째 행의 데이터를 가져옵니다.
	        Object[] row = resultList.get(0);

	        // 첫 번째 열의 값을 가져와서 int로 변환합니다.
	        int count = ((BigDecimal) row[0]).intValue();

	        // count가 1인 경우 아이디 중복이므로 true를 반환합니다.
	        if (count == 1) {
	            return true;
	        }
	    }

	    // 결과가 없거나 count가 1이 아닌 경우 아이디 중복이 아니므로 false를 반환합니다.
	    return false;
	}
	
	// 회원가입
	public int join(Member user) {
		int result = 0;
		/**
		CREATE SEQUENCE [시퀀스명]; 을 통하여 자동으로 중복이 되지 않는 시퀀스를 만들어 줍니다.
		사용은 [시퀀스명].NEXTVAL 로 사용하면 됩니다.
		*/
		String sql = "INSERT INTO CUSTOMER (CSTCLS, NAME, PHONE, ADDRESS) "
				+ "VALUES(1, ?, ?, ?) ";
		String CSTNO = DAO.insertAndGetCSTNO(sql, user.getName(), user.getPhone(), user.getAddress());
		
		
		if(CSTNO != null) {
			String insertMembersql = "INSERT INTO MEMBER (MID, MPW, CSTNO) "
					+ "VALUES(?, ?, ?)";
			result = DAO.update(insertMembersql, user.getmId(), user.getmPw(), CSTNO);
		}else {
			System.out.println("\t고객등록에 실패했습니다.");
		}
		return result;
	}

	// 로그인
	public int login(String id, String pw) {
	    String sql = "SELECT MPW FROM MEMBER WHERE MID = ?";
	    List<Object[]> resultList = DAO.selectList(sql, id);

	    if (!resultList.isEmpty()) {
	        Object[] row = resultList.get(0);
	        String storedPw = (String) row[0]; // 데이터베이스에 저장된 비밀번호

	        if (storedPw.equals(pw)) {
	            return 1; // 로그인 성공
	        } else {
	            return 0; // 비밀번호 불일치
	        }
	    }

	    return -1; // 아이디가 없음
	}

    // 고객번호 가져오기
    public String getCSTNOByMID(String mID) {
        String cstno = null;

        String sql = "SELECT CSTNO FROM MEMBER WHERE MID = ?";
        List<Object[]> resultList = DAO.selectList(sql, mID);

        if (!resultList.isEmpty()) {
            Object[] row = resultList.get(0);
            cstno = (String) row[0]; // 결과 리스트의 첫 번째 행의 첫 번째 열 값
        }

        return cstno;
    }
	
	//  내 회원정보 조회
	public List<Member> memberSelect(String id) {
		String sql = "SELECT M.MID 	AS ID " + 
				" ,   M.MPW       AS PW " + 
				" ,   CS.NAME 		AS NAME " + 
				" ,   CS.PHONE 		AS PHONE " + 
				" ,   CS.ADDRESS 	AS ADDRESS " + 
				" ,   M.MPOINT    AS POINT " + 
				" FROM CUSTOMER CS " + 
				" ,   MEMBER M " + 
				"WHERE CS.CSTNO = M.CSTNO " + 
				"AND M.MID = ? ";
		
		List<Object[]> list = DAO.selectList(sql, id); //반환할 리스트를 위해 list 객체 생성
		List<Member> members = new ArrayList<>(); // Member 객체를 저장할 리스트 생성
		 
        for (Object[] row : list) { //읽을 행이 있을 때
            String mID = (String) row[0];
            String mPW = (String) row[1];
            String mName = (String) row[2];
            String mPhone = (String) row[3];
            String mAddress = (String) row[4];
            int mPoint = ((BigDecimal) row[5]).intValue();
            Member member = new Member(mID, mPW, mName, mPhone, mAddress, mPoint);
            members.add(member); // 변환된 Member 객체를 리스트에 추가
        }
        return members; // 변환된 Member 객체 리스트 반환
	}

}
