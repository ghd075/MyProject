package ddit.dao;

import ddit.dto.Member;
import ddit.dto.Order;
import ddit.dto.OrderDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
		ORDERNO = DAO.GetORDERNO(insertSql, member.getCstNo()); // 커넥션을 인자로 전달
		
		return ORDERNO;
	}
	
	// 주문 테이블에 데이터 추가
	public int OrderDetailNew(OrderDetail orderDetail) {
		int result = 0;
		String insertSql = "INSERT INTO ORDERMENU (ORDERPRICE,CNT,ORDERNO,MNCODE) VALUES (?, ?, ?, ?)";
		result = DAO.update(insertSql, orderDetail.getOrderPrice(), orderDetail.getCnt(), orderDetail.getOrderNo(), orderDetail.getMnCode());

		return result;
	}
	
	// 총가격 업데이트
	public int updateOrderTotalPrice (String ORDERNO, String CSTNO, int totalPrice) {
		int result = 0;
		String sql = "UPDATE ORDERS SET TOTALPRICE = ? WHERE ORDERNO = ? AND CSTNO = ?";
		result = DAO.update(sql, totalPrice, ORDERNO, CSTNO);
		return result;
	}
	
	// 주문내역 삭제
	public void deleteOrderAndMenu(String orderNo) {
	    String deleteOrderMenuQuery = "DELETE FROM ORDERMENU WHERE ORDERNO = ?";
	    String deleteOrderQuery = "DELETE FROM orders WHERE ORDERNO = ?";

	    try {
	    	DAO.update(deleteOrderMenuQuery, orderNo);
	        DAO.update(deleteOrderQuery, orderNo);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	// 포인트 업데이트
	public int updateUserPoint (String CSTNO, int userPoint) {
		int result = 0;
		String sql = "UPDATE MEMBER SET MPOINT = ? WHERE CSTNO = ?";
		result = DAO.update(sql, userPoint, CSTNO);
		return result;
	}
	
	//주문내역 조회
	public List<Order> memberSelect(String CSTNO, String orderNo) {
		String sql = " SELECT O.TOTALPRICE AS TOTALPRICE " + 
				" ,   O.RIDERTIME AS RIDERTIME " + 
				" ,   MN.MNNAME AS MNNAME " + 
				" ,   MN.PRICE * M.CNT AS PRICE " + 
				" FROM ORDERS O " + 
				" , ORDERMENU M " + 
				" , MENU MN " + 
				" , MEMBER U " + 
				" WHERE O.ORDERNO = M.ORDERNO " + 
				" AND M.MNCODE = MN.MNCODE " + 
				" AND O.CSTNO = U.CSTNO " + 
				" AND U.CSTNO = ? " + 
				" AND O.ORDERNO = ? ";
		
		List<Object[]> list = DAO.selectList(sql, CSTNO, orderNo); //반환할 리스트를 위해 list 객체 생성
		List<Order> orders = new ArrayList<>(); // Order 객체를 저장할 리스트 생성
		 
        for (Object[] row : list) { //읽을 행이 있을 때
            int totalPrice = ((BigDecimal) row[0]).intValue();
            int riderTime = ((BigDecimal) row[1]).intValue();
            String mnName = (String) row[2];
            int price = ((BigDecimal) row[3]).intValue();
            Order order = new Order(totalPrice, riderTime, mnName, price);
            orders.add(order); // 변환된 Order 객체를 리스트에 추가
        }
        return orders; // 변환된 Order 객체 리스트 반환
	}
	
	// 누적주문수 업데이트
	public int UpdateOrderCount (String stoNo) {
		int result = 0; 
		String sql = "UPDATE STORE SET STOORDER = STOORDER + 1 WHERE STONO = ?";
		result = DAO.update(sql, stoNo);
		return result;
	}
	
}
