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
	public void dorProcess(Member member, List<Store> storeList) {

		boolean result = true;
		while(result) {	
			System.out.println("\n\n");
			System.out.println("\n\t   주문하실 점포의 번호를 입력해주세yo~");
			System.out.print("\n\t   가게코드 입력: ");
			String input = Util.sc.nextLine();
			System.out.println("\n\n\n");
			System.out.println();
			
			if (input.length() < 3 || input.length() > 5) {
				System.out.println("\n\t   가게코드는 정확하게 입력해주세요!\n\n");
				System.out.println("\n\n\t   다시 배달 카테고리를 선택한 후 진행해주세요! 선택 가능한 카테고리는 다음과 같습니다:");
				System.out.println("\n\t   1. 한식\n\t   2. 중식\n\t   3. 일식/양식\n\t   4. 패스트푸드\n\t   5. 분식\n");
				System.out.println("\n\t   ⊙•‿•⊙  \n\n");
				System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
				Util.sc.nextLine();
				result = false;
				Util.clearScreen();
			} else {
				// 첫 번째 문자를 대문자로 변경
				String formattedInput = input.substring(0, 1).toUpperCase() + input.substring(1);
	            Store selectedStore = null;
	            for (Store store : storeList) {
	                if (store.getStoNo().equals(formattedInput)) {
	                    selectedStore = store;
	                    break;
	                }
	            }
	            
	            if (selectedStore != null) {
	                OrderStart(selectedStore, member);
	                result = false; // 탈출을 위해 result 값을 false로 변경
	            } else {
	                System.out.println("\n\n\t   선택한 가게가 없습니다. 다시 선택해주세요. (◞ ˛ ◟) \n\n");
	                System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
	                Util.sc.nextLine();
	                result = false;
	    			Util.clearScreen();
	            }
			}
		}
		return;
	}
	
	 /**
	  * 
	  * @throws SQLException 
	 * @document 가게 ID를 입력하여 가게를 선택하는 페이지입니다.
	  * 
	  * 
	  */
	public void dorProcess1(Member member, String meCode) {

		boolean result = true;
		while(result) {	
			String address = muDao.addressSelect(member.getAddress());
			List<Store> storeList = mm.displayNearbyStores(meCode, address); // 주변 가게 목록 출력
			System.out.println("\n\n\n\n");
			System.out.println("\n\t   주문하실 점포의 번호를 입력해주세yo~");
			System.out.print("\n\t   가게코드 입력: ");
			String input = Util.sc.nextLine();
			System.out.println("\n\n\n");
			System.out.println();
			
			if (input.length() < 3 || input.length() > 5) {
				System.out.println("\n\t   가게코드는 정확하게 입력해주세요!\n\n");
				System.out.println("\n\t   다시 카테고리를 선택한 후 진행해주세요! 선택 가능한 카테고리는 다음과 같습니다:");
				System.out.println("\n\t   1. 한식\n\t   2. 중식\n\t   3. 일식/양식\n\t   4. 패스트푸드\n\t   5. 분식\n");
				System.out.println("\n\t   ⊙•‿•⊙  \n\n");
				System.out.println("\n\t   계속하시려면 엔터키를 입력해주세요");
				Util.sc.nextLine();
				result = false;
				Util.clearScreen();
			} else {
			 	// 첫 번째 문자를 대문자로 변경
				String formattedInput = input.substring(0, 1).toUpperCase() + input.substring(1);
				Store selectedStore = null;
	            for (Store store : storeList) {
	                if (store.getStoNo().equals(formattedInput)) {
	                    selectedStore = store;
	                    break;
	                }
	            }
	            
	            if (selectedStore != null) {
	            	Store store = new Store();
	                store.setStoNo(selectedStore.getStoNo());
	                store.setStoName(selectedStore.getStoName());
	                store.setStoOrder(selectedStore.getStoOrder());

	                OrderStart(store, member);
	                result = false;
	            } else {
	            	Util.clearScreen();
	                System.out.println("\n\n\t   선택한 가게가 없습니다. 다시 선택해주세요. (◞ ˛ ◟) \n\n");
	                System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
	                Util.sc.nextLine();
	                result = false;
	                Util.clearScreen();
	            }
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
		String ORDERNO = "";
		orderItems = new ArrayList<>(); // 주문 아이템 리스트 초기화
		boolean result = true;
		boolean menuSelected = false; // 메뉴 선택 여부를 나타내는 변수
		int totalOrderPrice = 0;
		
		
		while (result) {
			if (muDao.meunSelect(sto) != null && !muDao.meunSelect(sto).equals("") ) {
				Util.clearScreen();
				System.out.println("\n\n\n");
				System.out.println(String.format("\t\t\t %s%s\r"
						,	Util.convert("▒▒▒▒▒안녕하세요! ", 10)		
						, 	Util.convert(store.getStoName()+"입니다 ▒▒▒▒▒ ㄴ(*^▽^*)a \r",25)		
						));
				System.out.println();
				System.out.println("\t ⊙ 최소주문금액 : 15000원");
				System.out.println();
				System.out.println(String.format("\t %s %s"
						,	Util.convert("⊙ 누적주문수 :", 3)			
						, 	Util.convert(store.getStoOrder()+"",3)		
						));
				System.out.println();
				System.out.println(String.format("\t%s \t%s \t%s\n\n"
						,	Util.convert("[ NO ]", 10)		
						,	Util.convert("[ MENU ]", 42)		
						, 	Util.convert("[ PRICE ]",10)		
						));
				list = muDao.meunSelect(sto);
				for(Menu menuPrice : list) {
					System.out.println(String.format("\t%s \t%s \t%s\n"
							, Util.convert(menuPrice.getmNo()+"", 10)
							, Util.convert(menuPrice.getMnName(), 42)
							, Util.convert(Util.formatPrice(menuPrice.getPrice()), 10)));
//					selectedMenu = menuPrice;
				}
				System.out.println();
				
		    if (!menuSelected) {
		        System.out.print("\n\t⊙ 메뉴를 선택해주세요 (뒤로 가기[0]): ");
		    } else {
			    // 선택한 메뉴가 있는 경우에만 보여줌
			    System.out.printf("\n\t=====================================================================%n");
			    System.out.println("\n\t\t\t▒▒▒▒▒[ 선택한 메뉴 목록 ]▒▒▒▒▒\n");
			    System.out.printf("\n\t=====================================================================%n");
			    System.out.println(String.format("\t%s \t%s\t%s"
			            , Util.convert("[ MENU ]", 45)
			            , Util.convert("[ QTY ]", 10)
			            , Util.convert("[ PRICE ]", 10)
			    ));
			    System.out.println();

			    for (OrderItem item : orderItems) {
			        System.out.println(item.toString());
			    }
			    System.out.println();
			    
			    System.out.print("\n\t⊙ 메뉴를 선택해주세요 (메뉴 결정[Q] | 뒤로 가기[0]): ");
		    }
		    
			String input3 = Util.sc.nextLine().trim();
			System.out.println();
            
			if (input3.equals("0")) {
				if (menuSelected) {
					orderDAO.deleteOrderAndMenu(ORDERNO); // 주문 및 주문 내역 삭제 메서드 호출
			    }
	            Util.clearScreen();
				break; // 종료 입력 시 루프 종료
	        } else if (input3.equalsIgnoreCase("Q")) {
//				Util.clearScreen();
				//	                 continue; // 메뉴 선택하지 않고 Q를 누르면 다시 루프의 처음으로 돌아감
				//                break;
				if(orderItems.isEmpty() && totalOrderPrice >= 15000) {
					System.out.println("\t방문해주셔서 감사합니다. ◝(⁰▿⁰)◜ ");
					result = false; // 결제 입력 시 루프 종료
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} // 450밀리초 동안 일시 중지 // 450밀리초 동안 일시 중지
				}
				if (!orderItems.isEmpty()) {
					Util.clearScreen();
					System.out.println("\n\n\n\n");
					System.out.println(String.format("\t\t %s%s\r"
						,	Util.convert("▒▒▒▒▒안녕하세요! ", 10)		
						, 	Util.convert(store.getStoName()+"입니다 ▒▒▒▒▒\r",25)	
						));
					System.out.println("\t\t\t▒▒▒▒▒[주문한 메뉴]▒▒▒▒▒\n\n");
					// 사용 가능한 포인트와 메뉴 정보 출력
					System.out.println(String.format("\t⊙ %s %s %s"
	            		,	Util.convert(member.getName(), 5)
	                    ,	Util.convert("회원님의 사용 가능한 포인트 : ", 3)		
	                    , 	Util.convert(member.getmPoint()+"",3)		
	                    ));
					System.out.println();

					// 총 가격 계산 및 출력

					for (OrderItem item : orderItems) {
						totalOrderPrice += item.getTotalPrice();
					}
	            
					// 주문 아이템 정보 출력
					System.out.println(String.format("\t%s \t%s\t%s"
	                    ,	Util.convert("[ MENU ]", 45)		
	                    ,	Util.convert("[ QTY ]", 10)		
	                    , 	Util.convert("[ PRICE ]",10)			
	                    ));
					System.out.println();
	            
					for (OrderItem item : orderItems) {
						System.out.println(item.toString());
					}
               
					System.out.println(String.format("\n\n\t%s %s"
                       ,	Util.convert("⊙ 총 가격 : ", 3)		
                       , 	Util.convert(Util.formatPrice(totalOrderPrice),3)		
                       ));
					System.out.println();
               
					if(totalOrderPrice >= 15000) {
//			                System.out.println(ORDERNO + ", " + member.getCstNo() + ", " + totalOrderPrice);
						orderDAO.updateOrderTotalPrice(ORDERNO, member.getCstNo(), totalOrderPrice);
						pU.paychoice(store, member, ORDERNO, totalOrderPrice);
					}else {
						System.out.println("\t 주문하실 수 없습니다. 15,000원이상 주문해주세요.!! (´ρ`) ");
						System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
						Util.sc.nextLine();
						orderDAO.deleteOrderAndMenu(ORDERNO); // 주문 및 주문 내역 삭제 메서드 호출
//					result = false;
//					Util.clearScreen();
//						Util.clearScreen();
						continue; // 다시 루프의 처음으로 돌아감
					}
				 }
			} else {
	        	if (input3.isEmpty()) {
					System.out.println("\n\t 선택한 메뉴가 없습니다. d(￣▽￣)b ");
					System.out.println("\n\t 메뉴 선택이 필요합니다. 메뉴 번호를 확인하고 다시 입력해주세요. ( ͡°- ͡°) \n\n");
    	            try {
    					Thread.sleep(450);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				} // 450밀리초 동안 일시 중지 // 450밀리초 동안 일시 중지
//	                    Util.clearScreen();
    	            continue; // 입력값이 비어있으면 메서드 처음으로 돌아감
	        	}
	        	
                for (Menu menu : list) {
                    if (input3.equals(menu.getmNo())) {
                        selectedMenu = menu;
                        break;
                    }
                }
                
                if (selectedMenu == null) {
                    System.out.println("\n\t 잘못된 입력입니다. 메뉴 번호를 확인하고 다시 입력해주세요. ( ͡°- ͡°) \n\n");
                    try {
                        Thread.sleep(450);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
	        	
				System.out.print("\n\t⊙ 수량을 입력해주세요: ");
				String input4Str = Util.sc.nextLine().trim(); // 개행문자 제거
				
			    if (input4Str.isEmpty()) {
			        System.out.println("\n\t   수량은 정확하게 입력해주세요! 메뉴를 다시 선택해주세요. ( ͡°- ͡°) \n\n");
			        try {
			            Thread.sleep(450);
			        } catch (InterruptedException e1) {
			            e1.printStackTrace();
			        }
			        // Util.clearScreen();
			        continue; // 입력값이 비어있으면 메서드 처음으로 돌아감
			    }
			    
				int input4 = 0;
			    
				try {
				    input4 = Integer.parseInt(input4Str);
				    // 숫자로 변환 가능한 경우, input4 변수에 숫자가 저장됨
				    System.out.println();
				} catch (NumberFormatException e) {
					System.out.println("\n\t 숫자로 변환할 수 없는 값입니다. 메뉴를 다시 선택해주세요. ( ͡°- ͡°) \n\n");
					continue; // 숫자로 변환 불가능한 경우 메서드 처음으로 돌아감
				}

//					System.out.println(input4);
				

                menuSelected = true;
                
				// 메뉴 선택 및 주문 아이템 생성
                int totalPrice = selectedMenu.getPrice() * input4;
                OrderItem orderItem = new OrderItem(selectedMenu, input4, totalPrice);
                orderItems.add(orderItem);
                System.out.println(String.format("\t%s \t%s\t%s"
                        , Util.convert("[ MENU ]", 45)
                        , Util.convert("[ QTY ]", 10)
                        , Util.convert("[ PRICE ]", 10)
                ));
                System.out.println();
				
                
                // 주문 아이템 정보 출력
                for (OrderItem item : orderItems) {
                    System.out.println(item.toString());
                }
                
                ORDERNO = orderDAO.OrderNew(member);
                
                // 주문 상세 정보 추가
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderPrice(selectedMenu.getPrice());
                orderDetail.setCnt(input4);
                orderDetail.setOrderNo(ORDERNO);
                orderDetail.setMnCode(selectedMenu.getMnCode());
                
                orderDAO.OrderDetailNew(orderDetail);

//				for (Menu menu : list) {
//	                if (input3.equals(menu.getmNo())) {
//	                    selectedMenu = menu;
//	                    if (selectedMenu != null) {
//			                int totalPrice = selectedMenu.getPrice() * input4;
//			                System.out.println(totalPrice);
//			                OrderItem orderItem = new OrderItem(selectedMenu, input4, totalPrice);
//			                orderItems.add(orderItem);
//
//			                System.out.println(String.format("\t%s \t%s\t%s"
//			                        ,	Util.convert("[ MENU ]", 45)		
//			                        ,	Util.convert("[ QTY ]", 10)		
//			                        , 	Util.convert("[ PRICE ]",10)			
//			                        ));
//			                System.out.println();
//
//			                ORDERNO = orderDAO.OrderNew(member);
//			                
//			                // 주문 아이템 정보 출력
//			                for (OrderItem item : orderItems) {
//			                    System.out.println(item.toString());
//			                }
//			                
//			                // 주문 상세 정보 추가
//			                OrderDetail orderDetail = new OrderDetail();
//			                orderDetail.setOrderPrice(selectedMenu.getPrice());
//			                orderDetail.setCnt(input4);
//			                orderDetail.setOrderNo(ORDERNO);
//			                orderDetail.setMnCode(selectedMenu.getMnCode());
////				                System.out.println(orderDetail);
//			                orderDAO.OrderDetailNew(orderDetail);		                    	
//	                    }
//
//		                menuSelected = true; // 메뉴 선택되었음을 표시
////			                break; // 해당 메뉴를 찾았으므로 더 이상 반복할 필요가 없음
//	                }
//	            }
		        // 메뉴 선택이 없는 경우
//			        if (!menuSelected) {
//			            
//			            try {
//			                Thread.sleep(450);
//			            } catch (InterruptedException e) {
//			                e.printStackTrace();
//			            }
//			        }
	        }
		}
	        
//	        if (!orderItems.isEmpty()) {
//
//	        }else {
//
//	        }
		}
	}
}