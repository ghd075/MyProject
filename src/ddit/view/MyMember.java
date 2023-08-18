package ddit.view;

import java.sql.SQLException;
import java.util.Arrays;
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
	 * @throws SQLException 
	 * @document: 회원의 주소에 인접한 가게들의 리스트와 점포 선택 페이지입니다.
	 * 
	 * 
	 */
    public void orderchoice(Member firstMember) {
        while (true) {
			Util.clearScreen();
			mu.title(MemberUI.ORDER);
			String myAddress = firstMember.getAddress();
			System.out.println("\n");
			System.out.printf("\t[나의 주소: %s]", myAddress);
			System.out.println("\n\n");

			/*
			 * 1. 정규식 활용해서 쿼리로 동 추출
			 * 2. 해당되는 동 기준으로 가게 탐색
			 * 3. 메뉴분류 선택 후 진행
			 * */
			String address = muDao.addressSelect(myAddress);

            if (address != null && !address.isEmpty()) {
                int option = displayStoreOptions(address, firstMember);
                if (option == 0) {
                    break; // 0번을 입력하면 루프 종료
                }
                return;	//orderchoice 종료
            } else {
                System.out.println("\n\n\t   주변에 가게가 없습니다!!>.<");
                System.out.println("\n\n\t   다시 선택해주세요 !\n\n");
                break;
            }
        }
    }

    private int displayStoreOptions(String address, Member firstMember) {
        List<String> categories = Arrays.asList("한식", "중식", "일식/양식", "패스트푸드", "분식");
        System.out.println("\n\n");
        for (int i = 0; i < categories.size(); i++) {
        	System.out.print(String.format("\t[%d.%s]\t",  i + 1, categories.get(i)));
        }
        System.out.println("\n\n");
		System.out.println("\t[뒤로 가기를 원하면 0번을 입력해주세요]");
		System.out.println("\n\n");
        System.out.print("\t이동할 화면 입력(숫자) : ");
        int categoryChoice = Integer.parseInt(Util.sc.nextLine());

        if (categoryChoice == 0) {
            // 사용자가 0번을 입력하면 뒤로 가기
            return 0;
        }else if (categoryChoice >= 1 && categoryChoice <= categories.size()) {
            String meCode = "S" + (char) ('a' + categoryChoice - 1);
            List<Store> storeList = muDao.storeOneSelect(meCode, address);

            if (!storeList.isEmpty()) {
                displayStores(storeList);
                
                for (Store store : storeList) {

                    // Store 객체를 변수에 저장
                    selectedStore = store;
                }
                
                System.out.println("\n\n\t계속하시려면 엔터키를 입력해주세요");
                Util.sc.nextLine();
                // Store 객체를 mu.choose 메서드로 전달
                mu.choose(firstMember, meCode, selectedStore);

            } else {
                System.out.println("\n\n\t   해당 종류의 가게가 존재하지 않습니다.>.<");
            }
            return categoryChoice;
        }else {
            System.out.println("\n\n\t   다시 선택해주세요 !\n\n");
            return -1;
        }
    }

    private void displayStores(List<Store> storeList) {
        System.out.println(String.format("\n\t%s  %s", Util.convert("점포고유번호", 10), Util.convert("점포명", 25)));
        System.out.printf("\n\t=====================================================================%n");

        for (int i = 0; i < storeList.size(); i++) {
            Store store = storeList.get(i);
            System.out.printf("\t   " + Util.convert(store.getStoNo(), 10));
            System.out.printf(Util.convert(store.getStoName(), 25));
            System.out.println();
        }
    }
    
    public List<Store> displayNearbyStores(String meCode, String address) {
    	Util.clearScreen();
    	List<Store> storeList = muDao.storeOneSelect(meCode, address);
    	System.out.println("\n\n\t\t\t== 주변 가게 목록 ==\n");
    	System.out.println(String.format("\t%s  %s", Util.convert("점포고유번호", 10), Util.convert("점포명", 25)));
        System.out.printf("\n\t=====================================================================%n");

        for (int i = 0; i < storeList.size(); i++) {
            Store store = storeList.get(i);
            System.out.printf("\t   " + Util.convert(store.getStoNo(), 10));
            System.out.printf(Util.convert(store.getStoName(), 25));
            System.out.println();
        }
        
        return storeList;
    }
	
	/**
	 * 
	 * @document 특정 카테고리를 선택했을 때 나오는 점포들의 리스트 페이지입니다.
	 * 
	 * 
	 * 
	 */
	public List<Store> accumlateList(Member firstMember, String meCode) {
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
			System.out.printf("\t" + Util.convert(store.getsNo()+"", 5));
			System.out.printf("\t" + Util.convert(store.getStoNo(),10));
			System.out.printf("\t" + Util.convert(store.getStoName(),25));
			System.out.printf(Util.convert(store.getStoOrder()+"",5));
			System.out.println();
		}
		
		return list;
	}// 메소드
}
