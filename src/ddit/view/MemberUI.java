package ddit.view;

import java.sql.SQLException;

import ddit.dto.Member;
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
			System.out.println("\t\t[주문 페이지]");
		} else if (n == MemberUI.INFO) {
			System.out.println("\t\t [회원 정보 보기]");
			System.out.println("\n");
		}else if(n== MemberUI.ORDERINFO) {
			System.out.println("\t\t[지난 배달 내역 보기]");
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
			System.out.println("\n\n\n");
			System.out.println("\t주문이 정상 접수되었습니다.\r\n\r\n\t계속 하시려면 엔터를 입력하세요.");
			Util.sc.nextLine();
		} else if (n == MemberUI.INFO) {
			System.out.println("\n\n\n");
			System.out.println("\t\t 회원님의 정보입니다.\n\n\t\t 계속 하시려면 엔터를 입력하세요.");
			Util.sc.nextLine();
		}else if(n == MemberUI.ORDERINFO) {
			System.out.println("\n\n\n");
			System.out.println("\t\t회원님의 주문 정보입니다.\n\n\t\t계속 하시려면 엔터를 입력하세요.");
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
			} else if (input == 2) {
				dor.dorProcess1(store, member);
				//result = false; // 바로 주문하기를 선택했을 때 메뉴 선택 루프 종료
				//dor.dorProcess(store, member);
			} else if (input == 0) {
				result = false;
			}else {
				result = false;
			}
		}
	}
}
