package ddit.view;

import java.util.List;

import ddit.dao.OrderDAO;
import ddit.dto.Member;
import ddit.dto.OrderInfo;
import ddit.util.Util;

/**
 * 
 * 회원의 지난 배달 내역 조회 클래스입니다. 
 *
 */
public class OrderListCheck {
	
	private static OrderListCheck instance = null;
	private static MyMember ms = MyMember.getInstance();
	public OrderListCheck() {	}
	
	//싱글톤
	public static OrderListCheck getInstance() {
		if(instance == null) instance = new OrderListCheck();
		return instance;
	}
	
	public void orderlistcheck(Member firstMember) {

		boolean loop = true;

		while (loop) {
			List<Member> memberInfos = OrderDAO.MemberInfo(firstMember.getmId());
			List<OrderInfo> orderInfos = OrderDAO.OrderInfo(firstMember.getCstNo());
			String myname = "";
			String myaddress = "";
			String myphonenum = "";
			int mypoint = 0;

	        for (Member memberInfo : memberInfos) {
	        	myname = memberInfo.getName();
	            myaddress = memberInfo.getAddress();
	            myphonenum = memberInfo.getPhone();
	            mypoint = memberInfo.getmPoint();
	        }
			
			Util.clearScreen();
			System.out.println("\t안녕하세요. 회원느님!\r\n");
			System.out.printf("\t\t\t▒▒▒▒▒ %s 회원님의 정보입니다 ▒▒▒▒▒\r\n\r\n", myname);
			System.out.printf("\t⊙ 이름 : %s\r\n\r\n", myname);
			System.out.printf("\t⊙ 주소 : %s\r\n\r\n", myaddress);
			System.out.printf("\t⊙ 번호 : %s\r\n\r\n", myphonenum);
			System.out.printf("\t\t\t▒▒▒▒▒ 배달내역 ▒▒▒▒▒ \r\n\r\n");

			int itemsPerPage = 5; // 한 페이지에 보여줄 아이템 수
			int totalPages = (orderInfos.size() + itemsPerPage - 1) / itemsPerPage; // 총 페이지 수
			int currentPage = totalPages > 0 ? 1 : 0; ;   // 현재 페이지 번호
			// 총 페이지가 0이면 현재 페이지도 0

			boolean loop1 = true;
			while (loop1) {
			    String prevPageInfo = "[P]이전 페이지";
			    String nextPageInfo = "[N]다음 페이지";
			    String quitInfo = "[Q]메뉴선택창";
			    String fixInfo = " : ";
			    
		        if (orderInfos.isEmpty() || (currentPage <= 1 && totalPages <= 1)) {
		            prevPageInfo = "";
		            nextPageInfo = "";
		            quitInfo = "\t\t PRESS ENTER TO CONTUNUE...";
		            fixInfo = "";
		        } else if (currentPage == 1) {
		            prevPageInfo = "";
		        } else if (currentPage == totalPages) {
		            nextPageInfo = "";
		        }
		        
			    System.out.println("\n\t-------------------------------------------------------------------------------------------------------------------");
			    if (!orderInfos.isEmpty() && currentPage > 0) {
			        System.out.printf("\n\t\t\t    [ 현재 페이지 %d / 총 페이지 %d ]\n\n", currentPage, totalPages);
			    }

			    int startIndex = (currentPage - 1) * itemsPerPage;
			    int endIndex = Math.min(startIndex + itemsPerPage, orderInfos.size());

			    // 출력할 주문 아이템 정보
			    if (orderInfos.isEmpty()) {
			    	 System.out.println("\n\t\t\t    주문 정보가 없습니다.\n");
			    }else {
				    System.out.println(String.format("\t%s \t%s \t%s \t%s"
				            , Util.convert("[ STORE ]", 45)
				            , Util.convert("[ MENU ]", 45)
				            , Util.convert("[ PRICE ]", 10)
				            , Util.convert("[ TOTAL PRICE ]", 10)
				    ));
				    System.out.println();
			    	 for (int i = startIndex; i < endIndex; i++) {
					        OrderInfo info = orderInfos.get(i);
					        System.out.println(info.toString());
					    }
					    System.out.println("\n\t-------------------------------------------------------------------------------------------------------------------\n");
			    }
			    
			    String options = prevPageInfo;
			    if (!nextPageInfo.isEmpty()) {
			        options += "  " + nextPageInfo;
			    }
			    options += "  " + quitInfo;

		        if (totalPages > 1) {
		            options = prevPageInfo + "  " + options + fixInfo;
		        }
		        
			    System.out.print("\n\t\t    " + options);
			    String input = Util.sc.nextLine().trim().toLowerCase();

			    if (input.equals("p")) {
			        if (currentPage > 1) {
			            currentPage--;
			        }
			    } else if (input.equals("n") && !nextPageInfo.isEmpty()) {
			        if (endIndex < orderInfos.size()) {
			            currentPage++;
			        }
			    } else if (input.equals("q")) {
			    	loop = false;  // 종료
			    } else if (orderInfos.isEmpty() || totalPages <= 1) {
			    	if(input.isEmpty()) {
			    		loop1 = false; 
			    	}
			    }
			}
			
			System.out.printf("\n\n\t⊙ 가용 포인트 : %s\r\n\r\n\r\n", mypoint);
			System.out.println("\t\t()()       () ()       () ()");
			System.out.println("\t\t(..)       (o.o)       (x x)");
			System.out.println("\t\t(  )       (   )       (   )");
			
			System.out.println();
			System.out.println("\n\t-------------------------------------------------------------------------------------------------------------------\n");
			System.out.println("\t▶▶▶추가 주문이 필요할 경우 1번 입력\n");
			System.out.println("\t▶▶▶[뒤로 가기를 원하면 0번을 입력해주세yo]\n\n");
			System.out.print("\t입력 : ");
			String input = Util.sc.nextLine();
			
			if (input.equals("0")) {
		    	Util.clearScreen();
				loop = false;
			}
			else if(input.equals("1")) {
				ms.orderchoice(firstMember);
				return;
			}
		}
	}
}
