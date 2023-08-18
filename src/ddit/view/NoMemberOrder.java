package ddit.view;

import java.util.List;
import java.util.Scanner;

import ddit.dao.NoMemberDAO;
import ddit.dao.NoMemberOrderDAO;
import ddit.dto.Menu;
import ddit.util.Util;

public class NoMemberOrder {
	public static final String FONT_RED = "\u001B[31m"; 
	public static final String RESET = "\u001B[0m";
	private static NoMemberOrder instance = null;
	public static NoMemberOrder getInstance() {
		if(instance == null) {
			instance = new NoMemberOrder();
		}
		return instance; 
	}
	private NoMemberOrderDAO noOrdDao = NoMemberOrderDAO.getInstance(); 
	
	Scanner sc = new Scanner(System.in);
	List<Menu> list;
	Menu menu = null;
	String address;
	
	
	//선택한 가게의 메뉴 출력
	public void noMemberMenu(String stoNo, String address){
		list = noOrdDao.listMenu(stoNo);
		boolean run = true;
		this.address = address;
		
		if(list == null) {
			System.out.println("\t\t해당 가게에 메뉴가 등록되지 않습니다.");
		}
		
		while(run) {
			Util.clearScreen();
			System.out.println("\n\n\t▒▒▒▒▒▒▒▒▒▒▒▒ 안녕하세요yo! ["+NoMemberMenu.sto.getStoName()+" "+NoMemberMenu.sto.getStoAddress()+"]입니다. ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println("\n\t *최소 주문금액 : 15,000원\n\n\n");
			System.out.println(String.format("\t     %s \t%s \t%s\n"
					, Util.convert("[NO]", 10)
					, Util.convert("[ MENU ]", 49)
					, Util.convert("[ PRICE ]", 20)));
			System.out.println();
			for (Menu menu : list) {
				System.out.println(String.format("\t     %s \t%s \t%s"
						, Util.convert(menu.getRowNum(), 10)
						, Util.convert(menu.getMnName(), 49)
						, Util.convert(menu.getFormattedPrice(), 20)));
				System.out.println("\n\n");
			}
		
			System.out.println("\n\n\t\t     ▶ 주문하실 메뉴의 번호를 입력해주세yo!");
			System.out.print("\t\t    메뉴번호 입력 : ");
			String orderMenu = sc.nextLine();
			System.out.println("\n\t\t     ▶ 주문하실 메뉴의 수량을 입력해주세yo!");
			System.out.print("\t\t    수량 입력 : ");
			int orderAmount = Integer.parseInt(sc.nextLine());
			System.out.println("\n\n\n\t\t            ▶ 결제화면으로 이동하시겠습니까?");
			System.out.println("\t\t            (y/n)");
	        System.out.print("\n\n\t\t                 입력 : ");
	        String input = sc.nextLine();
        
	        if(input.equals("n")) {
	        	return;
	        }else if(input.equals("y")) {
	        	noMemberOrder(orderMenu, orderAmount);
	        }else {
	        	System.out.println("\n\n\t\t잘못된입력입니다(y/n 중 선택)");
	        }
		}
	}

    //메뉴 선택 후 주문
	private void noMemberOrder(String orderMenu, int orderAmount) {
		String mnCode = "";
		int totalPrice = 0;
		String mnName = "";
		int price = 0;
		
		for (Menu menu : list) {
			if(menu.getRowNum().equals(orderMenu)) {
				mnCode = menu.getMnCode();
			}
		}
		
		if(mnCode == null) {
			System.out.println("\t\t메뉴번호를 잘못 입력하였습니다. 다시 입력하십시오.");
			return;
		}
		menu = noOrdDao.selectMenu(mnCode);
		mnName = menu.getMnName();
		price = Util.parsePrice(menu.getFormattedPrice());
		totalPrice = price * orderAmount;
		
		Util.clearScreen();
		System.out.println("\n\n\t===============[ 결제 페이지 입니다 ]====================\n\n\n");
		System.out.println(String.format("\t  %s \t%s \t%s\n"
				, Util.convert("[주문한 메뉴]", 43)
				, Util.convert("[수량]", 10)
				, Util.convert("[결제 금액]", 5)));
		System.out.println("\n\n");
		System.out.println(String.format("\t  %s \t%s \t%s\n"
				, Util.convert(mnName, 43)
				, Util.convert(orderAmount+"", 10)
				, Util.convert(Util.formatPrice(totalPrice), 5)));
		System.out.println("\n\n");
		System.out.println("\n\n\t--------------------------------------------------");
		System.out.println("\t총 주문 금액 : "+Util.formatPrice(totalPrice)+"(VAT포함)");		
		System.out.println("\t--------------------------------------------------\n\n");
		System.out.println("\t\t            ▶ 위의 내용을 결제하시겠습니까?");
		System.out.println("\t\t            (y/n)\n\n");
		System.out.print("\t\t\t   입력 : ");
		String input = sc.nextLine();
		
		if(input.equals("n")) {
			return;
		}else if(!input.equals("y")) {
			System.out.println("\n\n\t\t   잘못된 입력입니다.(y/n 중 선택)");
		}
		
		noOrdDao.insertOrder(totalPrice, orderAmount, NoMemberDAO.cstNo, NoMemberMenu.sto.getStoNo(), mnCode);
		
		Util.clearScreen();
		System.out.println("\n\n\n\t\t            ✿✿결제가 완료되었습니다!✿✿\n\n\n");
	    System.out.println(" ######   #######  ##     ## ########  ##       ######## ######## ########    #### \r\n" + 
	    		"##    ## ##     ## ###   ### ##     ## ##       ##          ##    ##          #### \r\n" + 
	    		"##       ##     ## #### #### ##     ## ##       ##          ##    ##          #### \r\n" + 
	    		"##       ##     ## ## ### ## ########  ##       ######      ##    ######       ##  \r\n" + 
	    		"##       ##     ## ##     ## ##        ##       ##          ##    ##               \r\n" + 
	    		"##    ## ##     ## ##     ## ##        ##       ##          ##    ##          #### \r\n" + 
	    		" ######   #######  ##     ## ##        ######## ########    ##    ########    #### ");
	    System.out.println("\n\n\n\t\t       ▶ ▶▶주문내역을 확인하려면 enter키를 눌러주세요!");
	    sc.nextLine();
	    
	    orderDedails(mnName, orderAmount, totalPrice, NoMemberDAO.cstNo);
	}
	
	private void orderDedails(String mnName, int orderAmount, int totalPrice, String cstNo) {
		Util.clearScreen();
		System.out.println("\t▒▒▒▒▒▒▒▒▒▒▒▒▒고객님, 주문이 완료되었습니다▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\t\n\n");
		System.out.println("\t--------------------------------------------------");
		System.out.println("\t✿ 고객이름 : "+mnName+"\n");
		System.out.println("\t✿ 배달지 : "+address+"\n\n");
		System.out.println(String.format("\t  %s \t%s \t%s\n"
				, Util.convert("[주문한 메뉴]", 42)
				, Util.convert("[수량]", 10)
				, Util.convert("[결제 금액]", 5)));
		System.out.println("\n\n");
		System.out.println(String.format("\t  %s \t%s \t%s\n"
				, Util.convert(mnName, 42)
				, Util.convert(orderAmount+"", 10)
				, Util.convert(Util.formatPrice(totalPrice), 5)));
		System.out.println("\n\n");
		System.out.println("\t✿ 현재 상태 : 대기\n");
		System.out.println("\t✿ 배달 시간 : 30분\n");
		System.out.println("\t✿ 고객님의 비회원 번호는 \""+cstNo+"\"입니다.");
		System.out.println("\t--------------------------------------------------\n");
		System.out.println("\t▶▶▶추가 주문이 필요할 경우 1번 입력\n");
		System.out.println("\t▶▶▶프로그램을 종료할 경우 0번 입력\n\n");
		System.out.print("\t입력 : ");
		String input = sc.nextLine();
		
		if(input.equals("1")) {
			return;
		}else if(!input.equals("0")) {
			System.out.println("\t 잘못된 입력입니다.(1 또는 0 입력)");
		}
		Util.clearScreen();
		System.out.println("\t[대덕의 민족]을 종료합니다....감사합니다.");
		System.exit(0);
	}
}























