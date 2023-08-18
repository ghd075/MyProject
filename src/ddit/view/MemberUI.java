package ddit.view;

import java.sql.SQLException;
import java.util.List;

import ddit.dao.OrderDAO;
import ddit.dto.Member;
import ddit.dto.Order;
import ddit.dto.Store;
import ddit.util.Util;

/**
 * 회원주문페이지에 대한 모든 명령어 클래스입니다.
 * 
 */
public class MemberUI {
	
	public final static int ORDER = 1;
	public final static int INFO = 2;
	public final static int ORDERINFO = 3;
	private static MyMember mm = MyMember.getInstance();
	private static DirectOrder dor = DirectOrder.getInstance();
	private static OrderDAO orderDAO = OrderDAO.getInstance();
	
	private static MemberUI instance = null;
	
	public MemberUI() {	}
	
	public static MemberUI getInstance() {
		if(instance == null) instance = new MemberUI();
		return instance;
	}

	
	/**
	 * 
	 * 
	 * @document 회원이 선택할 수 있는 메뉴얼 페이지입니다. 
	 * 
	 * 
	 */
	public void order() {
		System.out.println("\n\n");
		System.out.println("\t1. 주문페이지 ");
		System.out.println();
		System.out.println("\t2. 회원 정보 보기 ");
		System.out.println();
		System.out.println("\t3. 배달 내역 조회하기");
		System.out.println();
		System.out.println("\t[뒤로 가기를 원하면 0번을 입력해주세요]");
		System.out.println("\n\n");
		System.out.print("\t이동할 화면 입력(숫자) : ");
	}
	
	/**
	 * 
	 * 
	 * @document 메뉴얼 입장시 생성되는 첫 글귀입니다. 
	 * 
	 * 
	 */
	public void title(int n) {
		System.out.println("\n\n");
		if (n == MemberUI.ORDER) {
			System.out.println("\t\t [주문 페이지]");
		} else if (n == MemberUI.INFO) {
			System.out.println("\t\t [회원 정보 보기]");
			System.out.println("\n");
		}else if(n== MemberUI.ORDERINFO) {
			System.out.println("\t\t [지난 배달 내역 보기]");
		}
	}
	
	/**
	 * 
	 * @document 해당페이지를 나갈 때 나오는 글귀입니다. 
	 * 
	 * 
	 */
	public void pause(int n) {
		if (n == MemberUI.ORDER) {
			System.out.println("\n\n");
			System.out.println("\t\t\t주문이 정상 접수되었습니다.\r\n\r\n\t\t\t계속 하시려면 엔터를 입력하세요.");
			Util.sc.nextLine();
		} else if (n == MemberUI.INFO) {
			System.out.println("\n\n");
			System.out.println("\t\t회원님의 정보입니다.\n\n\t\t계속 하시려면 엔터를 입력하세요.");
			Util.sc.nextLine();
		}else if(n == MemberUI.ORDERINFO) {
			System.out.println("\n\n");
			System.out.println("\t\t\t회원님의 주문 정보입니다.\n\n\t\t\t누르신 키에 상관없이 처음으로 돌아갑니다.");
			Util.sc.nextLine();
		}
	}
	
	/**
	 * 
	 * 
	 * @throws SQLException 
	 * @document 주문페이지의 정렬선택 옵션 페이지입니다. 
	 * 
	 * 
	 */
	public void choose(Member member, String meCode, Store store) {
		boolean result = true;
		while(result) {
			Util.clearScreen();
			System.out.println("\n\n");
			System.out.println("\t\t\t\t== 정렬선택 ==");
			System.out.println("\n");
			System.out.println("\t1. 누적 주문 순으로 보기");
			System.out.println();
			System.out.println("\t2. 바로주문하기");
			System.out.println();
			System.out.println("\t[뒤로 가기를 원하면 0번을 눌러주세yo]");
			System.out.println("\n\n");
			System.out.print("\t이동할 화면 입력(번호) : ");
			Integer input = Integer.parseInt(Util.sc.nextLine());
			System.out.println();

			if (input == 1) {
				System.out.println("\n\n\n\n\n\n\n");
				mm.accumlateList(member, meCode);
				dor.dorProcess(store, member);
				return;		// choose 메서드 종료
			} else if (input == 2) {
				dor.dorProcess1(store, member, meCode);
				//result = false; // 바로 주문하기를 선택했을 때 메뉴 선택 루프 종료
				//dor.dorProcess(store, member);
				return;		// choose 메서드 종료
			} else if (input == 0) {
				result = false;
			}else {
				result = false;
			}
		}
	}
	
	/**
	 * 
	 * 
	 * @document 각 페이지 종료시 나오는 다음 메뉴얼 선택 페이지입니다. 
	 * 
	 * 
	 */
	public void fffinal(Member member, int UsedP, int usedPoint, String ORDERNO) {
		
		boolean result = true;
		while(result) {
			Util.clearScreen();
			System.out.println("\n\n\n");
			System.out.println("\t ==어디로 이동할까요? ==");
			System.out.println();
			System.out.println("\t1. 주문 정보 보기");
			System.out.println();
			System.out.println("\t[뒤로 가기를 원하면 0번을 눌러주세yo]");
			System.out.println("\n\n");
			System.out.print("\t이동할 화면 입력(숫자) : ");
			
			String input = Util.sc.nextLine(); // 개행 문자 소비
			System.out.println("\n\n");

			if (input.equals("1")) {
				userfinalorderinfo(member, UsedP, usedPoint, ORDERNO);
				return;			// fffinal 메서드 종료
			} else if (input.equals("0")) {
				result = false; // 0을 입력하면 루프 종료
			}
		}
	}
	
	/**
	 * 
	 * @document  회원의 기존 정보에 대한 페이지입니다. 
	 * 
	 * 
	 */
	private void userfinalorderinfo(Member member, int UsedP, int usedPoint, String ORDERNO) {
		Util.clearScreen();
		System.out.printf("\t\t\t[%s 님이 신청하신 주문 내역을 불러옵니다.]\r\n\r\n",member.getName());
		List<Order> orders = orderDAO.memberSelect(member.getCstNo(), ORDERNO); // 주문번호를 실제로 어떻게 가져올지에 따라 수정 필요
		
		System.out.println("\t\t\t이름: " + member.getName());
		System.out.println();
		System.out.println("\t\t\t배달할 주소: " + member.getAddress());
		System.out.println();
		System.out.println("\t\t\t사용할 포인트: " + usedPoint);
		System.out.println();
		System.out.println("\t\t\t적립한 포인트: " + UsedP);
		System.out.println();
	
        System.out.println(String.format("\t\t\t%s \t%s"
                ,	Util.convert("[ MENU ]", 25)		
                , 	Util.convert("[ PRICE ]",6)			
                ));
        System.out.println();
        // 주문 아이템 정보 출력
        for (Order order : orders) {
            System.out.println(order.toString());
        }
        int totalOrderPrice = orders.stream().mapToInt(Order::getPrice).sum();
        System.out.println();
		System.out.println("\t\t\t총 주문 금액: " + totalOrderPrice + "원");
		System.out.println();
		System.out.println("\t\t\t현재 상태: 대기중..");
		System.out.println();
		System.out.println("\t\t\t배달시간 : 30분");
		
		pause(3);
	}
}
