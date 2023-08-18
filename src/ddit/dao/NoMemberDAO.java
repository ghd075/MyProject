package ddit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import ddit.dto.Customer;
import ddit.dto.Store;

public class NoMemberDAO {

	Connection conn = null; 
	PreparedStatement pstm = null; 
	ResultSet rs = null; 
	public static String cstNo;
	private static NoMemberDAO instance = new NoMemberDAO();
	private NoMemberDAO() {}
	
	public static NoMemberDAO getInstance() {
		return instance;
	}
	
	//비회원 정보 등록 //
	public int insertNoMember(Customer noMemVo) {
	    int result = 0;
		String sql = "INSERT INTO CUSTOMER(CSTCLS, NAME, PHONE, ADDRESS) "
				+ "VALUES(?, ?, ?, ?)";
		try {
			conn = DAO.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, noMemVo.getCstCls());
			pstm.setString(2, noMemVo.getName());
			pstm.setString(3, noMemVo.getPhone());
			pstm.setString(4, noMemVo.getAddress());
			
			result = pstm.executeUpdate();
			conn.commit();
			
		sql = "SELECT CSTNO FROM (SELECT * FROM CUSTOMER ORDER BY CSTNO DESC)" + 
				" WHERE ROWNUM = 1";	
		    pstm = conn.prepareStatement(sql);
		    rs = pstm.executeQuery();
		    rs.next();
		    cstNo = rs.getString("CSTNO");
			
			DAO.close(pstm);
			DAO.close(conn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
    
	//집주변 배달집 탐색(시+구), 누적주문순 조회//
	public List<Store> recommendStoreList(Customer noMemVo, String category, int mnCode) {
		List<Store> list = new ArrayList<>();
		
		//도시 검색//
		String sql = "SELECT SUBSTR(?, 1, INSTR(?, ' ')-1) AS CITY FROM DUAL";	
		 try {
				conn = DAO.getConnection();
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, noMemVo.getAddress());
				pstm.setString(2, noMemVo.getAddress());
				rs = pstm.executeQuery();
				rs.next();
				String city = rs.getString("CITY");
				
				sql = "SELECT STONO, STONAME, STOADDRESS FROM STORE WHERE STOADDRESS LIKE ? AND MNCTCODE = ?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, "%"+city+"%");
				pstm.setString(2, category);
				rs = pstm.executeQuery();
				
				//구군 검색//
				if(rs.next()) {
					sql = "SELECT SUBSTR(?, INSTR(?, ' ')+1, INSTR(?, ' ', 2)-1) AS DISTRICT FROM DUAL";
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, noMemVo.getAddress());
					pstm.setString(2, noMemVo.getAddress());
					pstm.setString(3, noMemVo.getAddress());
					rs = pstm.executeQuery();
					rs.next();
					String district = rs.getString("DISTRICT");
					
					sql = "SELECT STONO, STONAME, STOADDRESS, MNCTCODE, STOORDER FROM STORE "
							+ "WHERE STOADDRESS LIKE ? AND MNCTCODE = ?";
					if(mnCode == 1) {
						sql += " ORDER BY STOORDER DESC";
					}
					pstm = conn.prepareStatement(sql);
					pstm.setString(1, "%"+city+"%"+district+"%");
					pstm.setString(2, category);
					rs = pstm.executeQuery();
					
					if(rs.next()) {
					do {
					 String stoNo = rs.getString("STONO");
					 String stoName = rs.getString("STONAME");
					 String stoAddress = rs.getString("STOADDRESS");
					 String mnctCode = rs.getString("MNCTCODE");
					 int stoOrder = rs.getInt("STOORDER");
					 Store sto = new Store(stoNo, stoName, stoAddress, mnctCode, stoOrder);
					 list.add(sto);
					}while(rs.next()); 
					  return list;
					   }else {
				    //구군 검색 실패//
				    //구군 검색 실패//
				    sql = "SELECT STONO, STONAME, STOADDRESS, MNCTCODE, STOORDER FROM STORE "
				    		+ "WHERE STOADDRESS LIKE ? AND MNCTCODE = ?";
				    if(mnCode == 1) {
						sql += " ORDER BY STOORDER DESC";
					}					pstm = conn.prepareStatement(sql);
					pstm.setString(1, "%"+city+"%");
					pstm.setString(2, category);
					rs = pstm.executeQuery();
					while(rs.next()) {
						String stoNo = rs.getString("STONO");
						 String stoName = rs.getString("STONAME");
						 String stoAddress = rs.getString("STOADDRESS");
						 String mnctCode = rs.getString("MNCTCODE");
						 int stoOrder = rs.getInt("STOORDER");
						 Store sto = new Store(stoNo, stoName, stoAddress, mnctCode, stoOrder);
						 list.add(sto);
					} return list;
					   }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				DAO.close(rs);
				DAO.close(pstm);
				DAO.close(conn);
			}
		    //시 검색 실패//
			return null;
			
		        }
	

	//주문 많은순으로 가게조회//
	public List<Store> storeListByOrder() {
		List<Store> list = new ArrayList<>();
		String sql = "SELECT STONO, STONAME, STOADDRESS, STOORDER FROM STORE ORDER BY STOORDER DESC";
		
		try(Connection conn = DAO.getConnection(); 
			PreparedStatement pstm = conn.prepareStatement(sql); 
			ResultSet rs = pstm.executeQuery();){
			
			while(rs.next()) {
				String stoNo = rs.getString("STONO");
				String stoName = rs.getString("STONAME");
				String stoAddress = rs.getString("STOADDRESS");
				int stoOrder = rs.getInt("STOORDER");
				Store sto = new Store(stoNo, stoName, stoAddress, stoOrder);
				list.add(sto);
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}return list;
	}
}
