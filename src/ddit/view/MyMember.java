package ddit.view;

import java.util.List;

import ddit.dao.MunuDAO;
import ddit.dto.Member;
import ddit.dto.Store;
import ddit.util.Util;

/**
 *  회원의 주문 및 회원 정보 관리 클래스입니다.
 * 
 */
public class MyMember {

	private static MemberUI mu = MemberUI.getInstance();
	private static MunuDAO muDao = MunuDAO.getInstance();
	private static MyMember instance = null;
	private static Store selectedStore = null;
	
	public MyMember() {	}
	
	public static MyMember getInstance() {
		if(instance == null) instance = new MyMember();
		return instance;
	}
	
	/**
	 * 
	 * @document: 내정보 보기입니다.
	 * 
	 * 
	 */
	public void myPage(Member firstMember) {
		mu.title(MemberUI.INFO);
		System.out.println("\t\t 안녕하세요 회원님!!>.<");
		System.out.println("\n\n");
		System.out.println("\t\t [회원님의 정보입니다]");
		System.out.println("\n\n");
		System.out.printf("\t\t 이름: %s \r\n\r\n \t\t 주소: %s \r\n\r\n \t\t 번호: %s \r\n\r\n \t\t 포인트: %s \r\n\r\n", firstMember.getName(), firstMember.getAddress(), firstMember.getPhone(), firstMember.getmPoint());

		mu.pause(MemberUI.INFO);
		Util.clearScreen();
		
	}
	
	/**
	 * 
	 * 
	 * @document: 회원의 주소에 인접한 가게들의 리스트와 점포 선택 페이지입니다.
	 * 
	 * 
	 */
	public void orderchoice(Member firstMember) {
		
		boolean result = true;

		while(result) {
			Util.clearScreen();
			mu.title(MemberUI.ORDER);
			String myAddress = firstMember.getAddress();
			System.out.println("\n");
			System.out.printf("\t[나의 주소: %s]", myAddress);
			System.out.println("\n\n");
			System.out.println("\t집 주변 배달집 탐색 결과 입니당!!!>.<");
			/*
			 * 1. 정규식 활용해서 쿼리로 동 추출
			 * 2. 해당되는 동 기준으로 가게 탐색
			 * 3. 메뉴분류 선택 후 진행
			 * */
			String address = muDao.addressSelect(myAddress);
			if (muDao.storeSelect(address) != null && !muDao.storeSelect(address).equals("") ) {
				String meCode = "";
				List<Store> list = null;
				//System.out.println(address);
				System.out.println("\n\n");
				System.out.println(String.format("\t%s%s%s%s\t\t%s"
						,	Util.convert("[1.한식]", 15)		
						,	Util.convert("[2.중식]", 15)		
						,	Util.convert("[3.일식/양식]", 15)		
						,	Util.convert("[4.패스트푸드]", 15)		
						,	Util.convert("[5.분식]", 15)		
						));
//				System.out.println("\t[1.한식]\t [2.중식]\t [3.일식]\t[4.패스트푸드]\t[5.분식]");
				System.out.println("\n\n");
				// 리스트 가져오기
		
				// for(menu )
				System.out.print("\t이동할 화면 입력(숫자) : ");
		
				String content = Util.sc.nextLine();
				
				System.out.println("\n");
				System.out.println(String.format("\t%s  %s"
						, Util.convert("점포고유번호", 10)
						, Util.convert("점포명", 25)));

				System.out.printf("\n\t=====================================================================%n");
				if (content.equals("1")) {
					meCode = "Sa";
					if (muDao.checkStono(meCode, address)) {
						list = muDao.storeOneSelect(meCode, address);
						for(Store store : list) {
							System.out.printf("\n\t   " + Util.convert(store.getStoNo(),10));
							System.out.printf(Util.convert(store.getStoName(),25));
							System.out.println();
							
							// Store 객체를 변수에 저장
							selectedStore = store;
						}
						System.out.println("\n\n\t	계속하시려면 엔터키를 입력해주세요");
						Util.sc.nextLine();
						mu.choose(firstMember, meCode, selectedStore);
					}else {
						System.out.println("\n\n\t	해당 종류의 가게가 존재하지 않습니다.>.<" );
					}
				} else if (content.equals("2")) {
					meCode = "Sb";
					if (muDao.checkStono(meCode, address)) {
						list = muDao.storeOneSelect(meCode, address);
						for(Store store : list) {
							System.out.printf("\n\t  " + Util.convert(store.getStoNo(),10));
							System.out.printf(Util.convert(store.getStoName(),25));
							System.out.println();
							
							// Store 객체를 변수에 저장
							selectedStore = store;
						}
						System.out.println("\n\n\t	계속하시려면 엔터키를 입력해주세요");
						Util.sc.nextLine();
						mu.choose(firstMember, meCode, selectedStore);
					}else {
						System.out.println("\n\n\t	해당 종류의 가게가 존재하지 않습니다.>.<" );
					}
				} else if (content.equals("3")) {
					meCode = "Sc";
					if (muDao.checkStono(meCode, address)) {
						list = muDao.storeOneSelect(meCode, address);
						for(Store store : list) {
							System.out.printf("\t   " + Util.convert(store.getStoNo(),10));
							System.out.printf(Util.convert(store.getStoName(),25));
							System.out.println();
							
							// Store 객체를 변수에 저장
							selectedStore = store;
						}
						System.out.println("\n\n\t	계속하시려면 엔터키를 입력해주세요");
						Util.sc.nextLine();
						mu.choose(firstMember, meCode, selectedStore);
					}else {
						System.out.println("\n\n\t	해당 종류의 가게가 존재하지 않습니다.>.<" );
					}
				} else if (content.equals("4")) {
					meCode = "Sd";
					if (muDao.checkStono(meCode, address)) {
						list = muDao.storeOneSelect(meCode, address);
						for(Store store : list) {
							System.out.printf("\n\t   " + Util.convert(store.getStoNo(),10));
							System.out.printf(Util.convert(store.getStoName(),25));
							
							// Store 객체를 변수에 저장
							selectedStore = store;
						}
						System.out.println("\n\n\t	계속하시려면 엔터키를 입력해주세요");
						Util.sc.nextLine();
						mu.choose(firstMember, meCode, selectedStore);
					}else {
						System.out.println("\n\n\t	해당 종류의 가게가 존재하지 않습니다.>.<" );
					}
				} else if (content.equals("5")) {
					meCode = "Se";
					if (muDao.checkStono(meCode, address)) {
						list = muDao.storeOneSelect(meCode, address);
						for(Store store : list) {
							System.out.printf("\n\t   " + Util.convert(store.getStoNo(),10));
							System.out.printf(Util.convert(store.getStoName(),25));
							System.out.println();
							
							// Store 객체를 변수에 저장
							selectedStore = store;
						}
						System.out.println("\n\n\t	계속하시려면 엔터키를 입력해주세요");
						Util.sc.nextLine();
						mu.choose(firstMember, meCode, selectedStore);
					}else {
						System.out.println("\n\n\t	해당 종류의 가게가 존재하지 않습니다.>.<" );
					}
				} else {
					System.out.println("\n\n\t   다시 선택해주세요 !\n\n");
					result = false;
				}
			}else {
				System.out.println("\n\n\t   주변에 가게가 없습니다!!>.<");
				System.out.println("\n\n\t   다시 선택해주세요 !\n\n");
				result = false;
			}
		}
	}
	
	/**
	 * 
	 * @document 특정 카테고리를 선택했을 때 나오는 점포들의 리스트 페이지입니다.
	 * 
	 * 
	 * 
	 */
	public void accumlateList(Member firstMember, String meCode) {
		String address = muDao.addressSelect(firstMember.getAddress());
		List<Store> list = muDao.storeRankSelect(meCode, address);
//		System.out.println(list);
		Util.clearScreen();
		System.out.println(String.format("\n\n\n\n\n\n\t%s\t%s\t%s%s"
				, Util.convert("순번", 5)
				, Util.convert("점포고유번호", 10)
				, Util.convert("점포명", 25)
				, Util.convert("누적주문수", 5)));
		System.out.printf("\n\t=====================================================================%n");
		for(Store store : list) {
			System.out.printf("\t" + Util.convert(store.getsNo(), 5));
			System.out.printf("\t" + Util.convert(store.getStoNo(),10));
			System.out.printf("\t" + Util.convert(store.getStoName(),25));
			System.out.printf(Util.convert(store.getStoOrder(),5));
			System.out.println();
		}
	}// 메소드
}
