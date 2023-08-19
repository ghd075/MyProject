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
		System.out.printf("\t\t ⊙ 이름: %s \r\n\r\n \t\t ⊙ 주소: %s \r\n\r\n \t\t ⊙ 번호: %s \r\n\r\n \t\t ⊙ 포인트: %s \r\n\r\n", firstMember.getName(), firstMember.getAddress(), firstMember.getPhone(), firstMember.getmPoint());

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
    	Util.clearScreen();
        while (true) {
			mu.title(MemberUI.ORDER);
			String myAddress = firstMember.getAddress();
			System.out.println("\n\n");
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
                	Util.clearScreen();
                    break; // 0번을 입력하면 루프 종료
                } else if (option == -1) {
                    continue; // -1번을 입력하면 루프 재실행
                }
//                return;	//orderchoice 종료
            } 
            else {
                System.out.println("\n\n\t   주변에 가게가 없습니다!!>.<");
                System.out.println("\n\n\t   다시 선택해주세요 !\n\n");
                break;	 // 주변에 가게가 없을 경우 루프 종료
            }
        }
    }

    private int displayStoreOptions(String address, Member firstMember) {
        List<String> categories = Arrays.asList("한식", "중식", "일식/양식", "패스트푸드", "분식");
        while (true) {
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
                    
//                    System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
//                    Util.sc.nextLine();
                    // Store 객체를 mu.choose 메서드로 전달
                    mu.choose(firstMember, meCode, selectedStore);

                    return categoryChoice;	// 선택 완료 후 리턴
                } else {
                    System.out.println("\n\n\t   해당 카테고리에 가게 목록이 없습니다.\n\n");
                    System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
                    Util.sc.nextLine();
                	Util.clearScreen();
                    return -1; // 루프 재실행을 위해 -1 반환
                }
                
            }else {
                System.out.println("\n\n\t   다시 선택해주세요 !\n\n");
                // 다시 선택할 수 있도록 루프를 계속 유지
                System.out.println("\n\n\t   계속하시려면 엔터키를 입력해주세요");
                Util.sc.nextLine();
            	Util.clearScreen();
                return -1; // 루프 재실행을 위해 -1 반환
            }
        }
    }

    private void displayStores(List<Store> storeList) {
		int itemsPerPage = 5; // 한 페이지에 보여줄 아이템 수
		int totalPages = (storeList.size() + itemsPerPage - 1) / itemsPerPage;  // 총 페이지 수
		int currentPage = totalPages > 0 ? 1 : 0; ;   // 현재 페이지 번호
		// 총 페이지가 0이면 현재 페이지도 0
		
		boolean loop = true;
		while (loop) {
		    String prevPageInfo = "[P]이전 페이지";
		    String nextPageInfo = "[N]다음 페이지";
		    String quitInfo = "[Q]메뉴선택창";
		    String fixInfo = " : ";
		    
		    if (storeList.isEmpty() || (currentPage <= 1 && totalPages <= 1)) {
	            prevPageInfo = "";
	            nextPageInfo = "";
	            quitInfo = "\t\t PRESS ENTER TO CONTUNUE...";
	            fixInfo = "";
	        }  else if (currentPage == 1) {
	            prevPageInfo = "";
	        } else if (currentPage == totalPages) {
	            nextPageInfo = "";
	        }
	        
		    System.out.printf("\n\t=====================================================================%n");
		    if (!storeList.isEmpty() && currentPage > 0) {
		        System.out.printf("\n\t\t\t    [ 현재 페이지 %d / 총 페이지 %d ]\n\n", currentPage, totalPages);
		    }

		    int startIndex = (currentPage - 1) * itemsPerPage;
		    int endIndex = Math.min(startIndex + itemsPerPage, storeList.size());

		    // 출력할 가게 정보
		    if (storeList.isEmpty()) {
		    	 System.out.println("\n\t\t\t    주문 정보가 없습니다.\n");
		    }else {
			    System.out.println(String.format("\n\t%s  %s", Util.convert("점포고유번호", 10), Util.convert("점포명", 25)));
			    System.out.println("\n");
			    for (int i = startIndex; i < endIndex; i++) {
		            Store store = storeList.get(i);
		            System.out.println(String.format("\n\t%s  %s", Util.convert(store.getStoNo(), 10), Util.convert(store.getStoName(), 25)));
		            System.out.println();
			    }
			    System.out.printf("\n\t=====================================================================%n");
		    }
		    
		    String options = "";
	        if (!prevPageInfo.isEmpty()) {
	            options += prevPageInfo + "  ";
	        }
		    if (!nextPageInfo.isEmpty()) {
		        options += prevPageInfo + "  ";
		    }
		    options += quitInfo;

	        if (totalPages > 1) {
	        	options = options + fixInfo;
	        }
	        
		    System.out.print("\n\t\t\t    " + options);
		    String input = Util.sc.nextLine().trim().toLowerCase();

		    if (input.equals("p")) {
		        if (currentPage > 1) {
		            currentPage--;
		        }
		    } else if (input.equals("n") && !nextPageInfo.isEmpty() && endIndex < storeList.size()) {
	            currentPage++;
	        } else if (input.equals("q")) {
		    	loop = false;  // 종료
		    } else if ((storeList.isEmpty() || totalPages <= 1) && input.isEmpty()) {
		        loop = false; 
		    }
		}
    }
    
    public List<Store> displayNearbyStores(String meCode, String address) {
    	Util.clearScreen();
    	List<Store> storeList = muDao.storeOneSelect(meCode, address);
    	System.out.println("\n\n\t\t\t▒▒▒▒▒ 주변 가게 목록 ▒▒▒▒▒\n");
		int itemsPerPage = 5; // 한 페이지에 보여줄 아이템 수
		int totalPages = (storeList.size() + itemsPerPage - 1) / itemsPerPage; // 총 페이지 수
		int currentPage = totalPages > 0 ? 1 : 0; ;   // 현재 페이지 번호
		
		boolean loop = true;
		while (loop) {
		    String prevPageInfo = "[P]이전 페이지";
		    String nextPageInfo = "[N]다음 페이지";
		    String quitInfo = "[Q]메뉴선택창";
		    String fixInfo = " : ";
		    
		    if (storeList.isEmpty() || (currentPage <= 1 && totalPages <= 1)) {
	            prevPageInfo = "";
	            nextPageInfo = "";
	            quitInfo = "\t\t PRESS ENTER TO CONTUNUE...";
	            fixInfo = "";
	        } else if (currentPage == 1) {
	            prevPageInfo = "";
	        } else if (currentPage == totalPages) {
	            nextPageInfo = "";
	        }
	        
		    System.out.printf("\n\t=====================================================================%n");
		    if (!storeList.isEmpty() && currentPage > 0) {
		        System.out.printf("\n\t\t\t    [ 현재 페이지 %d / 총 페이지 %d ]\n\n", currentPage, totalPages);
		    }

		    int startIndex = (currentPage - 1) * itemsPerPage;
		    int endIndex = Math.min(startIndex + itemsPerPage, storeList.size());

		    // 출력할 가게정보
		    if (storeList.isEmpty()) {
		    	 System.out.println("\n\t\t\t    주문 정보가 없습니다.\n");
		    }else {
			    System.out.println(String.format("\n\t%s  %s", Util.convert("점포고유번호", 10), Util.convert("점포명", 25)));
			    System.out.println("\n");
			    for (int i = startIndex; i < endIndex; i++) {
		            Store store = storeList.get(i);
		            System.out.println(String.format("\n\t%s  %s", Util.convert(store.getStoNo(), 10), Util.convert(store.getStoName(), 25)));
		            System.out.println();
			    }
			    System.out.printf("\n\t=====================================================================%n");
		    }
		    
		    String options = prevPageInfo;
		    if (!nextPageInfo.isEmpty()) {
		        options += "  " + nextPageInfo;
		    }
		    options += "  " + quitInfo;

	        if (totalPages > 1) {
	            options = prevPageInfo + "  " + options + fixInfo;
	        }
	        
		    System.out.print("\n\t\t\t    " + options);
		    String input = Util.sc.nextLine().trim().toLowerCase();

		    if (input.equals("p")) {
		        if (currentPage > 1) {
		            currentPage--;
		        }
		    } else if (input.equals("n") && !nextPageInfo.isEmpty()) {
		        if (endIndex < storeList.size()) {
		            currentPage++;
		        }
		    } else if (input.equals("q")) {
		    	loop = false;  // 종료
		    } else if (storeList.isEmpty() || totalPages <= 1) {
		    	if(input.isEmpty()) {
		    		loop = false; 
		    	}
		    	// 페이지가 하나이면서 엔터키를 입력하면 종료
	        }
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
		int itemsPerPage = 5; // 한 페이지에 보여줄 아이템 수
		int totalPages = (list.size() + itemsPerPage - 1) / itemsPerPage; // 총 페이지 수
		int currentPage = totalPages > 0 ? 1 : 0; ;   // 현재 페이지 번호
		
		boolean loop = true;
		while (loop) {
		    String prevPageInfo = "[P]이전 페이지";
		    String nextPageInfo = "[N]다음 페이지";
		    String quitInfo = "[Q]메뉴선택창";
		    String fixInfo = " : ";
		    
		    if (list.isEmpty() || (currentPage <= 1 && totalPages <= 1)) {
	            prevPageInfo = "";
	            nextPageInfo = "";
	            quitInfo = "\t\t PRESS ENTER TO CONTUNUE...";
	            fixInfo = "";
	        } else if (currentPage == 1) {
	            prevPageInfo = "";
	        } else if (currentPage == totalPages) {
	            nextPageInfo = "";
	        }
		    
		    System.out.printf("\n\t=====================================================================%n");
		    if (!list.isEmpty() && currentPage > 0) {
		        System.out.printf("\n\t\t\t    [ 현재 페이지 %d / 총 페이지 %d ]\n\n", currentPage, totalPages);
		    }
		    
		    int startIndex = (currentPage - 1) * itemsPerPage;
		    int endIndex = Math.min(startIndex + itemsPerPage, list.size());

		    // 출력할 가게정보
		    if (list.isEmpty()) {
		    	 System.out.println("\n\t\t\t    주문 정보가 없습니다.\n");
		    }else {
				System.out.println(String.format("\n\n\n\n\n\n\t%s\t%s\t%s%s"
						, Util.convert("순번", 5)
						, Util.convert("점포고유번호", 10)
						, Util.convert("점포명", 25)
						, Util.convert("누적주문수", 5)));
				System.out.printf("\n\t=====================================================================%n");
			    for (int i = startIndex; i < endIndex; i++) {
		            Store store = list.get(i);
					System.out.println(String.format("\n\t%s\t%s\t%s%s"
							, Util.convert(store.getsNo()+"", 5)
							, Util.convert(store.getStoNo(), 10)
							, Util.convert(store.getStoName(), 25)
							, Util.convert(store.getStoOrder()+"", 5)));
					System.out.println("\n\n");
			    }
			    System.out.printf("\n\t=====================================================================%n");
		    }
		    
		    String options = prevPageInfo;
		    if (!nextPageInfo.isEmpty()) {
		        options += "  " + nextPageInfo;
		    }
		    options += "  " + quitInfo;

	        if (totalPages > 1) {
	            options = prevPageInfo + "  " + options + fixInfo;
	        }
	        
		    System.out.print("\n\t\t\t    " + options);
		    String input = Util.sc.nextLine().trim().toLowerCase();

		    if (input.equals("p")) {
		        if (currentPage > 1) {
		            currentPage--;
		        }
		    } else if (input.equals("n") && !nextPageInfo.isEmpty()) {
		        if (endIndex < list.size()) {
		            currentPage++;
		        }
		    } else if (input.equals("q")) {
		    	loop = false;  // 종료
		    } else if (list.isEmpty() || totalPages <= 1) {
		    	if(input.isEmpty()) {
		    		loop = false; 
		    	}
		    	// 페이지가 하나이면서 엔터키를 입력하면 종료
	        }
		}

		return list;
	}// 메소드
}