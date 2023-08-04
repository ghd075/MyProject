package ddit.util;

import java.util.Scanner;

import ddit.dao.MemberDAO;

/**
 * 공통모듈 클래스
 * */
public class Util {

	public static Scanner sc = new Scanner(System.in);
	private static MemberDAO mDao = MemberDAO.getInstance();
	
	/**
	 * 
	 * 콘솔창 클리어
	 * 
	 * */
	/*
	 * public static void clearScreen() { for (int i = 0; i < 32; i++) {
	 * System.out.println(""); } }
	 */
	
	public static void clearScreen() {
        try{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system
              
            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            } 
        }catch(Exception e){
            System.out.println(e);
        }
    }	
	/**
	 * 
	 * 
	 * 
	 *전화번호 유효성 검사 페이지입니다. '-'를 사용하고 13자리를 입력해야 다음 단계로 이동합니다.
	 * 
	 * 
	 */
	public static String phonenumber() {

		String memberPhone = "";
		
		boolean loop = true;
		while (loop) {

			System.out.print("\t전화번호 ('-' 포함) \n\t ex)000-0000-0000형태로 입력 : ");
			memberPhone = sc.nextLine();

			if (memberPhone.length() != 13) {
				System.out.println("\t'-'을 포함하여 다시 입력해주세요.");
			} else {
				loop = false;
			}

		}
		return memberPhone;

	} // phonenumber
	
	/**
	 * 
	 * 
	 * @document 주소입력하는 페이지입니다.
	 * 
	 * 
	 */
	public static String Address() {

		String memberAddress = "";
		
		System.out.print("\t주소 : ");
		memberAddress = sc.nextLine();
		System.out.println();
		
		return memberAddress;
		
	}//Address
	
	 /** 
	 * 
	 * @document 아이디 유효성 검사 페이지입니다. 조건에 맞는 아이디를 생성하지 않으면 다시 돌아갑니다.
	 * 
	 * 
	 */
	public static String createid() {
		
		String memberID = "";
		

		boolean loop = true;
		while (loop) {

			System.out.print("\t사용할 아이디 : ");
			memberID = sc.nextLine();
			
			int cnt1=0;
			int cnt2=0;
			
			//영문자 대소문자, 숫자 체크
			for(int i=0;i< memberID.length();i++){
				char ch = memberID.charAt(i);
				if((ch>='a' && ch<='z') || (ch>='A' && ch<='Z')) {
					cnt1++;
				}else if(ch>='0' && ch<='9') {
					cnt2++;
				}
			}
			
			//중복ID 체크
			if(mDao.checkId(memberID)) {
				System.out.println();
				System.out.println("\t중복된 아이디입니다.");
				System.out.println();
			//ID 길이가 5 ~ 15자 이내의 아이디만 입력할 수 있도록
			}else if(memberID.length() < 5 || memberID.length() > 15) {
				System.out.println("\t5~15자 이내의 아이디만 가능합니다");
			//영문자와 숫자를 혼용해서 입력할 수 있도록
			}else if(cnt1==0 || cnt2==0) {
				System.out.println("\t아이디는 영문자와 숫자를 혼용해서 만들어주세요.");
			}else {
				loop = false;
			}
		}

		return memberID;

	} // createid
}
