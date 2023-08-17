package ddit.dao;

import ddit.dto.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 회원관련 DAO 클래스
 * */
public class OrderDAO {
    Connection conn = null; // 자바와 오라클에 대한 연결 설정
    PreparedStatement pstm = null; // SQL문을 담는 객체 // sql문을 수행하기 위한 객체
    ResultSet rs = null; // statement 동작에 대한 결과로 전달되는 DB의 내용..!
	
    private static OrderDAO instance = null;
    
    public OrderDAO() {	}
	
    //싱글톤패턴
	public static OrderDAO getInstance() {
		if(instance == null) instance = new OrderDAO();
		return instance;
	}
   
	
	// 주문 테이블에 데이터 추가
	public String OrderNew(Member member) {
		String ORDERNO = null; // 트리거에서 커밋을 수행하므로 여기서 커밋하지 않음 = 0;
		String insertSql = "INSERT INTO ORDERS (ORDERDATE, CSTNO) VALUES (SYSTIMESTAMP, ?)";
		String selectSql = "SELECT ORDERNO FROM ORDERS WHERE CSTNO = ?";
		try {
			int result = DAO.update(insertSql, member.getCstNo());
			if (result > 0) {
				ORDERNO = DAO.GetORDERNO(selectSql, member.getCstNo()); // 커넥션을 인자로 전달
			}
		}catch (Exception e) {
	        e.printStackTrace();
		}
		
		return ORDERNO;
	}
	
	// 총가격 업데이트
	public int updateOrderTotalPrice (String ORDERNO, String CSTNO, int totalPrice) {
		int result = 0;
		String sql = "UPDATE ORDERS SET TOTALPRICE = ? WHERE ORDERNO = ? AND CSTNO = ?";
		result = DAO.update(sql, totalPrice, ORDERNO, CSTNO);
		return result;
	}

}
