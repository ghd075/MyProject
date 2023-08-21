package ddit.view;

import java.util.Scanner;

import ddit.dao.NoMemberDAO;
import ddit.dto.Customer;
import ddit.util.Util;
//비회원 메인화면 페이지
public class NoMemberMain {

	private static NoMemberMain instance = new NoMemberMain();
	private static Scanner sc = new Scanner(System.in);
	private Customer noMemVo = new Customer();
	private NoMemberDAO noDAO = NoMemberDAO.getInstance();
	
	
	private NoMemberMain(){}
	
	public static NoMemberMain getInstance() {
		return instance;
	}
	
	public void noMemberMain(String cstCls) {
		boolean run = true;
		
		while(run) {
			Util.clearScreen();
			System.out.println("\n\n\n\n");
			System.out.println("\t=============[ 안녕하세yo, 비회원님 :) ]================\n\n");
			System.out.println("\t\t\t 1.주문하기\n");
			System.out.println("\t\t   (0을 입력하시면 뒤로 이동합니다.)\n\n\n");
			System.out.print("\t\t   이동할 화면 입력 : ");
			String input = sc.nextLine();
			if(!input.equals("1")) {
				if(input.equals("0")) { return; }
				System.out.println("\n\n\t\t  <<<<1 또는 0을 입력하십시오>>>>\n\n\n");
				System.out.println("\t        PRESS ENTER TO TRY AGAIN...");
				sc.nextLine();
				Util.clearScreen();
				continue;
			}
			//비회원 고객 정보입력
			Util.clearScreen();
			System.out.println("\n\n\n\n");
			System.out.println("\t==================[비회원정보]==========================\n\n");
			System.out.println("\t\t              기본 정보를 입력해주세yo~!\n\n");
			noMemVo.setCstCls(Integer.parseInt(cstCls));
			System.out.print("\n\n\n\t   ⊙ 고객이름 : ");
			noMemVo.setName(sc.nextLine());
			noMemVo.setPhone(Util.phonenumber());  
//			System.out.print("\n\n\t   ⊙ 배송지 : ");
//			noMemVo.setAddress(sc.nextLine());
			noMemVo.setAddress(Util.NoMemberAddress());
			
			int result = noDAO.insertNoMember(noMemVo);
	
			if(result != 0) {
				Util.clearScreen();
				NoMemberMenu noMenu = NoMemberMenu.getInstance();
				noMenu.noMemberCategory(noMemVo);
			}else {
				System.out.println("고객정보 입력에 실패하였습니다.\n\n\n");
			}
		}
	}
}
