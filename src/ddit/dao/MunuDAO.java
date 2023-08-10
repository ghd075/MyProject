package ddit.dao;

import java.util.List;

import ddit.dto.MenuPrice;
import ddit.dto.Store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * 메뉴, 가게관련 DAO 클래스
 * */
public class MunuDAO {
    Connection conn = null; // 자바와 오라클에 대한 연결 설정
    PreparedStatement pstm = null; // SQL문을 담는 객체 // sql문을 수행하기 위한 객체
    ResultSet rs = null; // statement 동작에 대한 결과로 전달되는 DB의 내용..!
	
    private static MunuDAO instance = null;
    
    public MunuDAO() {	}
	
    //싱글톤패턴
	public static MunuDAO getInstance() {
		if(instance == null) instance = new MunuDAO();
		return instance;
	}
    
	// 주소 매칭
	public String addressSelect(String address) {
		String sql = "SELECT REGEXP_SUBSTR(ADDRESS, '[가-힣A-Za-z]+(동)') AS ADDRESS " 
					+ "FROM CUSTOMER " 
					+ "WHERE REGEXP_SUBSTR(ADDRESS, '[가-힣A-Za-z]+(동)') IS NOT NULL " 
					+ "AND ADDRESS LIKE ?"; // 실제로 DB에 입력될 명령어를 SQL 문장으로 만듬.
		try {
			conn = DAO.getConnection();;
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%" + address + "%");
			rs = pstm.executeQuery(); // 어떠한 결과를 받아오는 ResultSet 타입의 rs 변수에 쿼리문을 실행한 결과를 넣어줌 
			String mAddress = null;
			if(rs.next()) {
				mAddress = rs.getString("ADDRESS");
			}
			// 연결과 역순으로 해제
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);
			
			return mAddress;	//search 성공
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;	//DB오류
	}
	
	// 가게정보 조회
	public List<Store> storeSelect(String etc) {
		List<Store> list = new ArrayList<>(); //반환할 리스트를 위해 list 객체 생성
		try {
			conn = DAO.getConnection();
			String sql = "SELECT STONO " + 
					" ,   STONAME " + 
					" ,   STOPHONE " + 
					" ,   STOADDRESS " + 
					" ,   STOORDER " + 
					" FROM STORE " + 
					" WHERE 1 = 1 " + 
					" AND STOADDRESS LIKE ? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%" + etc + "%");
			rs = pstm.executeQuery();		//SELECT 문과 같이 여러 개의 행로 결과가 반환될 때 사용
			
			while(rs.next()) {			//읽을 행이 있을 때
				String stoNo = rs.getString(1);
				String stoName = rs.getString(2);
				String stoPhone = rs.getString(3);
				String stoAddress = rs.getString(4);
				String stoOrder = rs.getString(5);
				Store store = new Store(stoNo, stoName, stoPhone, stoAddress, stoOrder);
				list.add(store);
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
	
	// 가게 분류 체크
	public boolean checkStono(String stono, String address) {
		boolean flag = false;
		String query = "SELECT COUNT(STONO) FROM STORE WHERE STONO LIKE ? AND STOADDRESS LIKE ? ";
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, "%" + stono + "%");
			pstm.setString(2, "%" + address + "%");
			rs = pstm.executeQuery();

			if (rs.next()) { // 행) {
				int count = rs.getInt(1);
				flag = count > 0;
			}
			
			// 연결과 역순으로 해제
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	// 주변 가게 리스트 조회
	public List<Store> storeOneSelect(String stono, String address) {
		List<Store> list = new ArrayList<>(); //반환할 리스트를 위해 list 객체 생성
		String query = "SELECT  STONO " 
					+ ",   STONAME " 
					+ ",   STOORDER "
					+ "FROM STORE " 
					+ "WHERE STONO LIKE ? "
					+ "AND STOADDRESS LIKE ? ";
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, "%" + stono + "%");
			pstm.setString(2, "%" + address + "%");
			rs = pstm.executeQuery();

			while(rs.next()) {			//읽을 행이 있을 때
				String stoNo = rs.getString(1);
				String stoName = rs.getString(2);
				String stoOrder = rs.getString(3);
				Store store = new Store(stoNo, stoName, stoOrder);
				list.add(store);
			}
			
			// 연결과 역순으로 해제
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 누적 주문량 순으로 조회
	public List<Store> storeRankSelect(String stono, String address) {
		List<Store> list = new ArrayList<>(); //반환할 리스트를 위해 list 객체 생성
		String query = "SELECT DENSE_RANK() OVER (ORDER BY STOORDER DESC) AS SNO " 
					+ ",   STONO " 
					+ ",   STONAME " 
				 	+ ",   STOORDER " 
					+ "FROM STORE " 
					+ "WHERE STONO LIKE ? "
					+ "AND STOADDRESS LIKE ? ";
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(query);
			pstm.setString(1, "%" + stono + "%");
			pstm.setString(2, "%" + address + "%");
			rs = pstm.executeQuery();

			while(rs.next()) {			//읽을 행이 있을 때
				String sNo = rs.getString(1);
				String stoNo = rs.getString(2);
				String stoName = rs.getString(3);
				String stoOrder = rs.getString(4);
				Store store = new Store(sNo, stoNo, stoName, stoOrder);
				list.add(store);
			}
			
			// 연결과 역순으로 해제
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 메뉴정보 조회
	public List<MenuPrice> meunSelect(String sto) {
		List<MenuPrice> list = new ArrayList<>(); //반환할 리스트를 위해 list 객체 생성
		try {
			conn = DAO.getConnection();
			String sql = " SELECT ROWNUM AS MNO "
					 +  " ,       MEMENU " 
					 + " ,       PRICE "
					 + " FROM MENUPRICE "
					 + " WHERE STONO = ? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, sto);
			rs = pstm.executeQuery();		//SELECT 문과 같이 여러 개의 행로 결과가 반환될 때 사용
			
			while(rs.next()) {			//읽을 행이 있을 때
				int mNo = rs.getInt(1);
				String memeNu = rs.getString(2);
				int price = rs.getInt(3);
				MenuPrice menuPrice = new MenuPrice(mNo, memeNu, price);
				list.add(menuPrice);
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
