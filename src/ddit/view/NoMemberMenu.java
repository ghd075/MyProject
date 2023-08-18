package ddit.view;

import java.util.List;
import java.util.Scanner;

import ddit.dao.NoMemberDAO;
import ddit.dao.NoMemberOrderDAO;
import ddit.dto.Customer;
import ddit.dto.Store;
import ddit.util.Util;
//비회원 메뉴 카테고리 선택 및 가게 조회 화면
public class NoMemberMenu {

	private static NoMemberMenu instance = null;
	private NoMemberDAO noDao = NoMemberDAO.getInstance();
	Scanner sc = new Scanner(System.in);
	List<Store> list; 
	public static Store sto;
	public static String address;
	public static String mnCode;
	NoMemberOrderDAO noOrdDao = NoMemberOrderDAO.getInstance();
	NoMemberOrder noMemberOrder = NoMemberOrder.getInstance();
	
	public static NoMemberMenu getInstance() {
		if(instance == null) {
			instance = new NoMemberMenu();
		}
		return instance; 
	}
	
	//카테고리 선택 및 집 주변 탐색결과 화면
	public void noMemberCategory(Customer noMemVo) {
		boolean run = true;
		String category = "";
		address = noMemVo.getAddress();
		
		while(run) {
		System.out.println("\n\n\t================[배달 카테고리를 선택하세요]=====================\n\n\n");
		System.out.println("\t[1.한식]    [2.중식]    [3.일식/양식]    [4.패스트푸드]    [5.분식]\n\n");
		System.out.println("\t\t     (0을 입력하시면 뒤로 이동합니다.)\n\n");
		System.out.print("\t\t\t선택 : ");
		String input = sc.nextLine();
		
		switch(input) {
		case "1" : category = "한식"; mnCode = Store.KOR_FOOD; 
		list = noDao.recommendStoreList(noMemVo, mnCode, 0); break;
		case "2" : category = "중식"; mnCode = Store.CHI_FOOD; 
		list = noDao.recommendStoreList(noMemVo, mnCode, 0); break;
		case "3" : category = "일식/양식"; mnCode = Store.JAP_FOOD; 
		list = noDao.recommendStoreList(noMemVo, mnCode, 0); break;
		case "4" : category = "패스트푸드"; mnCode = Store.FAST_FOOD; 
		list = noDao.recommendStoreList(noMemVo, mnCode, 0); break;
		case "5" : category = "분식"; mnCode = Store.SNACK_FOOD; 
		list = noDao.recommendStoreList(noMemVo, mnCode, 0); break;
		case "0" : return;
		default : System.out.println("\t\t 잘못된 선택입니다(0~5번 중 선택)");
		}
		
		Util.clearScreen();
		System.out.println("\n\n\n\n\t==============[고객님 집 주변 \""+category+"\"집 탐색 결과입니다]===============\n\n\n");
		System.out.println(String.format("\t%s \t%s \t%s\n"
				, Util.convert("[가게코드]", 15)
				, Util.convert("[상호명]", 30)
				, Util.convert("[가게위치]", 30)));
		System.out.println("\n\n");
		if(list == null) {
			System.out.println("\t\t\t...주변에 배달집이 없습니다");  	continue;
		}
		
		for (Store store : list) {
			System.out.println(String.format("\t%s \t%s \t%s\n"
					, Util.convert(store.getStoNo(), 15)
					, Util.convert(store.getStoName(), 30)
					, Util.convert(store.getStoAddress(), 30)));
			System.out.println("\n\n");
		}
		System.out.println("\n\n\t\t    (나의 주소  : "+noMemVo.getAddress()+")\n\n\n\n");
		System.out.println("\t        PRESS ENTER TO CONTUNUE...");
		sc.nextLine();
		noMemberOption(list, noMemVo);
	}
	}
	
	//옵션 선택 화면
	public void noMemberOption(List<Store> list, Customer noMemVo) {
		List<Store> list2;
		boolean run = true;
		
		
		while(run) {
		Util.clearScreen();
		System.out.println("\t=================[옵션 선택]=========================\n\n\n");
		System.out.println("\t\t       1.바로 주문하기\n");
		System.out.println("\t\t     2.누적 주문 순으로 보기\n");
		System.out.println("\t\t  (0을 입력하시면 뒤로 이동합니다.)\n");
		System.out.print("\t\t\t입력 : ");
		String input = sc.nextLine();
		Util.clearScreen();
		
		
		switch(input) {
		case "1" : 
		System.out.println("\n\n\t=================[바로 주문하기]=======================");
		System.out.println("\n\t*집주변 기준 가게입니다.\n\n\n");
		System.out.println(String.format("\t%s \t%s \t%s\n"
				, Util.convert("[가게코드]", 16)
				, Util.convert("[상호명]", 30)
				, Util.convert("[가게위치]", 30)));
		System.out.println("\n\n");
			
		for (Store store : list) {
			System.out.println(String.format("\t%s \t%s \t%s\n"
					, Util.convert(store.getStoNo(), 15)
					, Util.convert(store.getStoName(), 30)
					, Util.convert(store.getStoAddress(), 30)));
			System.out.println("\n\n");
		} break;
		case "2" :
		Util.clearScreen();
		System.out.println("\n\n\t=================[누적 주문순으로 보기]=======================\n\n\n");
		System.out.println(String.format("\t\t%s \t%s \t%s\n"
				, Util.convert("[가게코드]", 16)
				, Util.convert("[상호명]", 45)
				, Util.convert("[주문수]", 30)));
		System.out.println("\n\n");
		list2 = noDao.recommendStoreList(noMemVo, mnCode, 1); 
			for (Store store : list2) {
				System.out.println(String.format("\t\t%s \t%s \t%s\n"
						, Util.convert(store.getStoNo(), 16)
						, Util.convert(store.getStoName(), 45)
						, Util.convert(store.getStoOrder()+"", 30)));
				System.out.println("\n\n");
			}
			break;
		case "0" : return; 
		default : System.out.println("\t\t  잘못된 입력입니다(0~2 중 선택).\n\n"); continue;
		}
		
		System.out.println("\n\t\t주문하실 점포의 번호를 입력해주세yo~");
		System.out.println("\t\t   (0을 입력하시면 뒤로 이동합니다.)\n\n");
		System.out.print("\t\t     가게코드 입력 : ");
		input = sc.nextLine();
		if(input.equals("0")) {  continue;  }
		Util.clearScreen();
		
		noMemberOrder.noMemberMenu(input, address);
		}
	}
}
