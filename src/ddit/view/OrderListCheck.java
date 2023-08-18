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
			System.out.printf("\t\t== %s 회원님의 정보입니다 ==\r\n\r\n", myname);
			System.out.printf("\t이름 : %s\r\n\r\n", myname);
			System.out.printf("\t주소 : %s\r\n\r\n", myaddress);
			System.out.printf("\t번호 : %s\r\n\r\n", myphonenum);
			System.out.printf("\t\t\t== 배달내역 == \r\n\r\n");
            System.out.println(String.format("\t%s \t%s \t%s \t%s"
                    ,	Util.convert("[ STORE ]", 25)		
                    , 	Util.convert("[ MENU ]", 25)
                    ,	Util.convert("[ PRICE ]", 10)		
                    , 	Util.convert("[ TOTAL PRICE ]",10)	
                    ));
            System.out.println();
            
            // 주문 아이템 정보 출력
            for (OrderInfo info : orderInfos) {
                System.out.println(info.toString());
            }
			
			System.out.printf("\n\n\t가용 포인트 : %s\r\n\r\n\r\n", mypoint);
			System.out.println("\t\t()()       () ()       () ()");
			System.out.println("\t\t(..)       (o.o)       (x x)");
			System.out.println("\t\t(  )       (   )       (   )");
			
			System.out.println("\n\n");
			System.out.println("\t1. 주문하기");
			System.out.println();
			System.out.println("\t[뒤로 가기를 원하면 0번을 입력해주세yo]");
			System.out.println();
			System.out.println("\t--------------------------------------------------\n");
			System.out.println("\t▶▶▶추가 주문이 필요할 경우 1번 입력\n");
			System.out.println("\t▶▶▶프로그램을 종료할 경우 0번 입력\n\n");
			System.out.print("\t입력 : ");
			String input = Util.sc.nextLine();
			
			if (input.equals("0")) {
				Util.clearScreen();
				System.out.println("\t[대덕의 민족]을 종료합니다....감사합니다.");
				System.exit(0);
			}
			else if(input.equals("1")) {
				ms.orderchoice(firstMember);
				return;
			}
		}
	}
}
