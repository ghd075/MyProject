package ddit.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ddit.dao.MunuDAO;
import ddit.dao.OrderDAO;
import ddit.dto.Member;
import ddit.dto.Menu;
import ddit.dto.OrderDetail;
import ddit.dto.OrderItem;
import ddit.dto.Store;
import ddit.util.Util;

public class DirectOrder {
	Connection conn = null; // 자바와 오라클에 대한 연결 설정
	
	private static MunuDAO muDao = MunuDAO.getInstance();
	private static OrderDAO orderDAO = OrderDAO.getInstance();
	private static PointUse pU = PointUse.getInstance();
	private static MyMember mm = MyMember.getInstance();
	private static DirectOrder instance = null;
	private static Menu selectedMenu = null;
	private List<OrderItem> orderItems = null; // 사용자가 입력한 메뉴와 수량을 저장할 리스트
	
	public DirectOrder() {	}
	
	public static DirectOrder getInstance() {
		if(instance == null) instance = new DirectOrder();
		return instance;
	}
	
	 /**
	  * 
	  * @throws SQLException 
	 * @document 가게 ID를 입력하여 가게를 선택하는 페이지입니다.
	  * 
	  * 
	  */
	public void dorProcess(Store store, Member member) {

		boolean result = true;
		while(result) {	
			System.out.println("\n\n\n\n\n");
			System.out.print("\t가게를 선택해주세요: ");
			String input = Util.sc.nextLine();
			System.out.println();
							
			if (input.length() < 3 || input.length() > 5) {
				System.out.println("\n\n\t   재조회후 다시 선택해주세요 !\n\n");
				System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
				Util.sc.nextLine();
				result = false;
			} else {
				OrderStart(store, member);
				// OrderStart 메소드가 끝난 후 전 메뉴로 탈출
				result = false; // 탈출을 위해 result 값을 false로 변경
			}
		}
	}
	
	 /**
	  * 
	  * @throws SQLException 
	 * @document 가게 ID를 입력하여 가게를 선택하는 페이지입니다.
	  * 
	  * 
	  */
	public void dorProcess1(Store store, Member member) {

		boolean result = true;
		while(result) {	
			String address = muDao.addressSelect(member.getAddress());
			System.out.println("\n\n\n\n\n");
			mm.displayNearbyStores(address); // 주변 가게 목록 출력
			System.out.print("\t가게를 선택해주세요: ");
			String input = Util.sc.nextLine();
			System.out.println();
							
			if (input.length() < 3 || input.length() > 5) {
				System.out.println("\n\n\t   재조회후 다시 선택해주세요 !\n\n");
				System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
				Util.sc.nextLine();
				result = false;
			} else {
				OrderStart(store, member);
				// OrderStart 메소드가 끝난 후 전 메뉴로 탈출
				result = false; // 탈출을 위해 result 값을 false로 변경
			}
		}
	}
	
	/**
	 * 
	 * 
	 * 
	 * @document 가게 검색시 나오는 가게의 정보와 메뉴선택 페이지입니다.
	 *
	 *
	 */
	public void OrderStart(Store store, Member member) {
		String sto = store.getStoNo();
		List<Menu> list = null;
		orderItems = new ArrayList<>(); // 주문 아이템 리스트 초기화
		if (muDao.meunSelect(sto) != null && !muDao.meunSelect(sto).equals("") ) {
			Util.clearScreen();
			System.out.println(String.format("\t\t %s%s\r"
					,	Util.convert("==안녕하세요! ", 10)		
					, 	Util.convert(store.getStoName()+"입니다 ==\r",25)		
					));
			System.out.println();
			System.out.println("\t 최소주문금액 : 15000원");
			System.out.println();
			System.out.println(String.format("\t %s %s"
					,	Util.convert("누적주문수 :", 3)		
					, 	Util.convert(store.getStoOrder()+"",3)		
					));
			System.out.println();
			System.out.println(String.format("\t%s \t%s\t%s"
					,	Util.convert("[ NO ]", 5)		
					,	Util.convert("[ MENU ]", 25)		
					, 	Util.convert("[ PRICE ]",6)		
					));
			list = muDao.meunSelect(sto);
			for(Menu menuPrice : list) {
				System.out.println(String.format("\t  %s\t%s\t%s"
						, Util.convert(menuPrice.getmNo()+"", 5)
						, Util.convert(menuPrice.getMnName(), 25)
						, Util.convert(menuPrice.getPrice()+"", 6)));
				System.out.println();
				selectedMenu = menuPrice;
			}
			String ORDERNO = orderDAO.OrderNew(member);
			//System.out.println(ORDERNO);
			
			boolean result = true;
			
			while (result) {
				System.out.print("\t메뉴를 선택해주세요 (종료: Q): ");
				String input3 = Util.sc.nextLine().trim();
				System.out.println();
	            
				if (input3.equalsIgnoreCase("Q")) {
	                result = false; // 종료 입력 시 루프 종료
	            }else {
					System.out.print("\t수량을 선택해주세요: ");
					String input4Str = Util.sc.nextLine().trim(); // 개행문자 제거
					int input4 = Integer.parseInt(input4Str); // 입력 처리 수정
			        System.out.println();
					
					// 메뉴 선택 및 주문 아이템 생성
					for (Menu menu : list) {
		                if (input3.equals(menu.getmNo())) {
		                    selectedMenu = menu;
		                    break;
		                }
		            }
			        if (selectedMenu != null) {
		                int totalPrice = selectedMenu.getPrice() * input4;
		                OrderItem orderItem = new OrderItem(selectedMenu, input4, totalPrice);
		                orderItems.add(orderItem);

		                System.out.println(String.format("\t%s \t%s\t%s"
		                        ,	Util.convert("[ MENU ]", 25)		
		                        ,	Util.convert("[ QTY ]", 6)		
		                        , 	Util.convert("[ PRICE ]",6)			
		                        ));
		                System.out.println();
		                
		                // 주문 아이템 정보 출력
		                for (OrderItem item : orderItems) {
		                    System.out.println(item.toString());
		                }
		                
		                // 주문 상세 정보 추가
		                OrderDetail orderDetail = new OrderDetail();
		                orderDetail.setOrderPrice(selectedMenu.getPrice());
		                orderDetail.setCnt(input4);
		                orderDetail.setOrderNo(ORDERNO);
		                orderDetail.setMnCode(selectedMenu.getMnCode());
//		                System.out.println(orderDetail);
		                orderDAO.OrderDetailNew(orderDetail);
		                
		            } else {
						System.out.println("\t주문하실 수 없습니다. 해당하는 음식이 없습니다. .");
						result = false;
		            }
				}
			}
	        // 메뉴 선택이 종료되면 선택한 메뉴 목록과 총 가격 출력
	        if (!orderItems.isEmpty()) {
				Util.clearScreen();
				System.out.println(String.format("\t\t %s%s\r"
						,	Util.convert("==안녕하세요! ", 10)		
						, 	Util.convert(store.getStoName()+"입니다 ==\r",25)	
						));
				System.out.println("\t==[주문한 메뉴]==");
	            // 사용 가능한 포인트와 메뉴 정보 출력
	            System.out.println(String.format("\t%s%s %s"
	            		,	Util.convert(member.getName(), 5)
	                    ,	Util.convert("회원님의 사용 가능한 포인트 : ", 3)		
	                    , 	Util.convert(member.getmPoint()+"",3)		
	                    ));
	            System.out.println();
	            System.out.println(String.format("\t%s \t%s\t%s"
	                    ,	Util.convert("[ MENU ]", 25)		
	                    ,	Util.convert("[ QTY ]", 6)		
	                    , 	Util.convert("[ PRICE ]",6)			
	                    ));
	            System.out.println();
	            
	            // 총 가격 계산 및 출력
	            int totalOrderPrice = 0;
	            for (OrderItem item : orderItems) {
	                totalOrderPrice += item.getTotalPrice();
	            }
	            if(totalOrderPrice >= 15000) {
	                // 주문 아이템 정보 출력
	                for (OrderItem item : orderItems) {
	                    System.out.println(item.toString());
	                }
	                System.out.println(String.format("\t%s %s"
	                        ,	Util.convert("총 가격 : ", 3)		
	                        , 	Util.convert(totalOrderPrice+"",3)		
	                        ));
//	                System.out.println(ORDERNO + ", " + member.getCstNo() + ", " + totalOrderPrice);
	                orderDAO.updateOrderTotalPrice(ORDERNO, member.getCstNo(), totalOrderPrice);
	                pU.paychoice(ORDERNO);
	            }else {
					System.out.println("\t주문하실 수 없습니다. 15000원이상 주문해주세요.!!");
					result = false;
				}
	        }else {
	            System.out.println("\t선택한 메뉴가 없습니다.");
	        }
		}
	}
}
