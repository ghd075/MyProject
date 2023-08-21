package ddit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ddit.dto.Menu;
import ddit.dto.Store;
import ddit.util.Util;
import ddit.view.NoMemberMenu;

public class NoMemberOrderDAO {

	Connection conn = null; 
	PreparedStatement pstm = null; 
	ResultSet rs = null; 
	
	private static NoMemberOrderDAO instance = new NoMemberOrderDAO();
	private NoMemberOrderDAO() {}
	public static NoMemberOrderDAO getInstance() {
		return instance;
	}
	
	//해당 가게 메뉴판 조회
	public List<Menu> listMenu(String stoNo){
		List<Menu> list = new ArrayList<>();
		String sql = "SELECT ROWNUM, MNCODE, MNNAME, PRICE, "
				+ "MNCTCODE, STONO FROM MENU WHERE STONO = ?";
		
		
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stoNo);
			rs = pstm.executeQuery();
			
			while(rs.next()) {
				String mnCode = rs.getString("MNCODE");
				String mnName = rs.getString("MNNAME");
				int price = rs.getInt("PRICE");
				String formattedPrice = Util.formatPrice(price); 
				String mnCtCode = rs.getString("MNCTCODE");
				String rowNum = rs.getString("ROWNUM");
				Menu menu = new Menu(mnCode, mnName, formattedPrice, mnCtCode, rowNum);
				list.add(menu);
			   }
			
			sql = "SELECT STONAME, SUBSTR(STOADDRESS, INSTR(STOADDRESS, ' ')+1, "
					+ "INSTR(STOADDRESS, ' ', 2)-1) AS STOADDRESS FROM STORE WHERE STONO = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stoNo);
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				String stoName = rs.getString("STONAME");
				String stoAddress = rs.getString("STOADDRESS")+"점";
				NoMemberMenu.sto = new Store(stoNo, stoName, stoAddress); //가게정보 보관
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);
		}
		return list;
	}
	
	
	//메뉴정보(가격) 조회
	public Menu selectMenu(String mnCode) {
		Menu menu = null;
		String sql = "SELECT MNNAME, PRICE AS PRICE FROM MENU WHERE MNCODE = ?";
		
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, mnCode);
			rs = pstm.executeQuery();
			if(rs.next()) {
				 int price = rs.getInt("PRICE");
				 String formattedPrice = Util.formatPrice(price); 
				 String mnName = rs.getString("MNNAME");
				 menu = new Menu(mnName, formattedPrice); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);
		}
		return menu;
	}
	
	//주문 관련 작업
	public void insertOrder(int totalPrice, int cnt, String cstNo, String stoNo, String mnCode) {
		//가게 주문수 업데이트
		int result = 0;
		String orderNo = "";
		String sql = "UPDATE STORE SET STOORDER = STOORDER+1 WHERE STONO = ?";
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, stoNo);
			result = pstm.executeUpdate();

			if(result == 0) {
				System.out.println("\t\t 주문에 실패하였습니다1");
				return; }
				
			//주문 생성
			sql = "INSERT INTO ORDERS(ORDERDATE, RIDERTIME, TOTALPRICE, CSTNO) "
					+ "VALUES(SYSDATE, 30, ?, ?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, totalPrice);
			pstm.setString(2, cstNo);
			result = pstm.executeUpdate();
			
			if(result == 0) {
				return; }
			
			sql = "SELECT ORDERNO FROM ORDERS WHERE ORDERDATE IN(SELECT MAX(ORDERDATE) FROM ORDERS)";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();
			orderNo = rs.getString("ORDERNO");
			
			//주문상세내역 생성
			sql = "INSERT INTO ORDERMENU(ORDERPRICE, CNT, ORDERNO, MNCODE) "
					+ "VALUES(?, ?, ?, ?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, totalPrice);
			pstm.setInt(2, cnt);
			pstm.setString(3, orderNo);
			pstm.setString(4, mnCode);
			result = pstm.executeUpdate();
			if(result == 0) {
				System.out.println("\t\t 주문에 실패하였습니다3");
				return; }
			System.out.println("\t\t  주문상세내역서 생성 완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAO.close(rs);
			DAO.close(pstm);
			DAO.close(conn);
		}
	}
}
