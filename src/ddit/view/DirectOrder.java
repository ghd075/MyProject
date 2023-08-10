package ddit.view;

import java.util.List;

import ddit.dao.MunuDAO;
import ddit.dto.Member;
import ddit.dto.MenuPrice;
import ddit.dto.Store;
import ddit.util.Util;

public class DirectOrder {
	
	private static MunuDAO muDao = MunuDAO.getInstance();
	private static DirectOrder instance = null;
	
	public DirectOrder() {	}
	
	public static DirectOrder getInstance() {
		if(instance == null) instance = new DirectOrder();
		return instance;
	}
	
	 /**
	  * 
	  * @document 가게 ID를 입력하여 가게를 선택하는 페이지입니다.
	  * 
	  * 
	  */
	public void dorProcess(Store store, Member member) {

		boolean result = true;
		while(result) {	
			System.out.println("\n\n\n\n\n");	
			System.out.print("\t   가게를 선택해주세요: ");
			String input = Util.sc.nextLine();
			System.out.println();
							
			if (input.length() < 3 || input.length() > 5) {
				System.out.println("\n\n\t   재조회후 다시 선택해주세요 !\n\n");
				System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
				Util.sc.nextLine();
				result = false;
			} else {
				OrderStart(store, member);
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
		List<MenuPrice> list = null;
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
					, 	Util.convert(store.getStoOrder(),3)		
					));
			System.out.println();
			System.out.println(String.format("\t%s \t%s\t%s"
					,	Util.convert("[ NO ]", 5)		
					,	Util.convert("[ MENU ]", 25)		
					, 	Util.convert("[ PRICE ]",6)		
					));
			list = muDao.meunSelect(sto);
			for(MenuPrice menuPrice : list) {
				System.out.println(String.format("\t  %s\t%s\t%s"
						, Util.convert(menuPrice.getmNo()+"", 5)
						, Util.convert(menuPrice.getMemeNu(), 25)
						, Util.convert(menuPrice.getPrice()+"", 6)));
				System.out.println();
			}
			
			boolean result = true;
			while (result) {
				System.out.print("\t메뉴를 선택해주세요: ");
				String input3 = Util.sc.nextLine();
				System.out.println();
				System.out.print("\t수량을 선택해주세요: ");
				Integer input4 = Util.sc.nextInt();
				System.out.println();
				System.out.println(String.format("\t %s %s"
						,	Util.convert("사용 가능한 포인트 : ", 3)		
						, 	Util.convert(member.getmPoint()+"",3)		
						));
				System.out.println();
				System.out.println(String.format("\t%s \t%s\t%s"
						,	Util.convert("[ MENU ]", 25)		
						,	Util.convert("[ QTY ]", 6)		
						, 	Util.convert("[ PRICE ]",6)			
						));
				System.out.println();
			}
		}
	}
}
