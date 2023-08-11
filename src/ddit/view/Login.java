package ddit.view;

import java.util.List;

import ddit.dao.MemberDAO;
import ddit.dto.Member;
import ddit.util.Util;


/**
 * 
 * 
 * 
 * 
 * 로그인 실행 클래스입니다. 
 *
 *
 *
 */
public class Login {

	private static Login instance = null;
	private static MemberDAO mDao = MemberDAO.getInstance();
	private static MemberUI mu = MemberUI.getInstance();
	private static MyMember ms = MyMember.getInstance();
	
	//기본 생성자
	public Login() {	}

	//싱글톤
	public static Login getInstance() {
		if(instance == null) instance = new Login();
		return instance;
	}

	/**
	 * 
	 * 
	 * @document 회원의 1번 입력시 나오는 로그인 페이지입니다.
	 * 
	 * 
	 */
	public void Memberloginmain (){
		
		boolean loop = true;
//		Member m = new Member();

		while (loop) {
			
			Util.clearScreen();
			System.out.println("\t\t안녕하세요. 회원님!\r\n ");
			System.out.println();
			System.out.println("\t\t[ 회원 로그인 화면 입니다. ]\r\n\r\n");

			System.out.print("\tID : ");
			String inputid = Util.sc.nextLine();
			System.out.println();
			System.out.print("\tPW : ");
			String inputpw = Util.sc.nextLine();

//			boolean flage = false;

			String loginname = "";
			
			int result1 = mDao.login(inputid, inputpw);  //로그인 정보 가져오기
			
			// DAO에서 Member 객체들을 가져옴
			List<Member> list = mDao.memberSelect(inputid);
//			System.out.println(list.toString());
			for(Member member : list) {
				loginname = member.getName();	//회원 이름 가져오기
			}
			
			// Member 객체의 값을 가져옴
			Member firstMember = null;
			
			if (!list.isEmpty()) {
		        firstMember = list.get(0); // 첫 번째 Member 객체를 가져옴
		    }
			
			if (result1 == 1){ // 로그인 정보가 맞으면 실행
				Util.clearScreen();
				System.out.printf("\t\t[반갑습니다. %s님!]\r\n", loginname);
				System.out.println();
				System.out.println("\t()()       () ()      () ()");
				System.out.println("\t(..)       (o.o)      (x x)");
				System.out.println("\t(  )       (   )      (   )");
				SuccessMenu(firstMember);
				loop = false; 
			} else if (result1 == 0){ 	//비밀번호가 틀리면
				Util.clearScreen();
				System.out.println("\t비밀번호가 틀립니다.");
				System.out.println();
				System.out.println("\t계속하시려면 엔터키를 입력해주세요");
				Util.sc.nextLine();
			} else {  					//아이디와 비밀번호 둘 다 일치하지 않으면
				Util.clearScreen();
				System.out.println("\t존재하지 않는 아이디입니다.");
				System.out.println();
				System.out.println("\t계속하시려면 엔터키를 입력해주세요");
				Util.sc.nextLine();
				if (FailMenu() == 0) {		//로그인 실패 페이지로 이동 후 입력 값이 0이면 빠져나오기
					loop = false;
				}
			}
		} // loop
	}
	
	/**
	 * 
	 * 
	 * @document: 로그인 성공시 나오는 주문 페이지입니다. 주문페이지의 선택안 중 하나를 선택합니다.
	 * 
	 */	
	public void SuccessMenu(Member firstMember) {

		boolean loop = true;

		while (loop) {

			mu.order();
			
			String input = Util.sc.nextLine();
			System.out.println("\n\n");
			
			if (input.equals("0")) {
				loop = false;
			}else if(input.equals("1")) {
				ms.orderchoice(firstMember);
			//회원 정보 보기
			}else if(input.equals("2")) {
				ms.myPage(firstMember);
			}else if(input.equals("3")) {
//				ol.orderlistcheck();
			}
				
		}
	} // SuccessMenu
	
	/**
	 * 
	 * 
	 * 
	 * @document: 로그인 실패시 나오는 페이지입니다. 다시 로그인 하거나, 초기화면으로 돌아갈 수 있습니다.
	 * 
	 * 
	 * 
	 */
	public int FailMenu() {
		
		int result = 1;
	
		Util.clearScreen();
		System.out.println("\t1. 다시 로그인 하기\r\n");
		System.out.println("\t[뒤로 가기를 원하시면 0번을 입력하세요.]");
		System.out.println("\n");
		System.out.print("\t이동할 화면 입력(숫자) : ");
		
		String input = Util.sc.nextLine();

		if (input.equals("0")) {
			result = 0;
		} else if (input.equals("1")) {
			result = 1;
		}
		return result;
	}
}
