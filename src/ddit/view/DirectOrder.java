package ddit.view;

import ddit.util.Util;

public class DirectOrder {
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
	public void dorProcess() {

		boolean result = true;
		while(result) {	
			System.out.println("\n\n\n\n\n");	
			System.out.print("\t가게를 선택해주세요: ");
			String input = Util.sc.nextLine();
			System.out.println();
							
			if (input.length() < 3 || input.length() > 5) {
				result = false;
			} else {
//				OrderStart(input);
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
	public void OrderStart(String input) {
		
	}
}
