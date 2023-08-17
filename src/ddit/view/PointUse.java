package ddit.view;

import ddit.dao.OrderDAO;
import ddit.util.Util;

public class PointUse {
	
	private static PointUse instance = null;
	private static OrderDAO orderDAO = OrderDAO.getInstance();
	
	//기본 생성자
	public PointUse() {	}

	//싱글톤
	public static PointUse getInstance() {
		if(instance == null) instance = new PointUse();
		return instance;
	}
	
	/**
	 * 
	 * @document 결제 여부를 묻는 페이지입니다.
	 * 
	 * 
	 */
	public void paychoice(String ORDERNO) {
				
		boolean result = true;
		
		while(result) {
			
			System.out.print("\t결제 하시겠습니까?(Y/N) : ");
			String input = Util.sc.nextLine();
			if (input.equalsIgnoreCase("Y")) {
//					payPay();
			}else if(input.equalsIgnoreCase("N")) {
				orderDAO.deleteOrderAndMenu(ORDERNO); // 주문 및 주문 내역 삭제 메서드 호출
				result = false;
			}else {
	            System.out.println("\t잘못된 입력입니다. 다시 입력해주세요.");
//	            Util.sc.nextLine(); // 개행 문자 처리
	        }
		}
	}
		

	/**
	 * 
	 * 
	 * @안지연 포인트 사용 여부와 사용할 포인트를 결정하는 페이지입니다. 
	 * 
	 * 
	 */
	public void payPay() {

		boolean result = true;
		while (result) {
//					String input = scan.nextLine();
//
//					System.out.print("\t포인트를 사용하시겠습니까? (y/n)");
//					input = scan.nextLine();
//					System.out.println();
//					
//					if (input.equals("y")) {
//						System.out.println();
//						System.out.print("\t사용할 포인트 : ");
//						Integer usedP = scan.nextInt();
//						dor.setUsedP(usedP);
//						if (usedP <= Integer.parseInt(ml.getuPoint())) {
//							System.out.println();
//							System.out.print("\t결제할 최종 금액: ");
//							dor.setFinalfinalprice(dor.finalprice - usedP);
//							System.out.println(dor.getFinalprice() - usedP);
//							dor.setuPilePoint(dor.getFinalfinalprice()/10);
//
//							mu.pause(1);
//							System.out.println();
//							mu.fffinal();
//
//						} else if (usedP > Integer.parseInt(ml.getuPoint())) {
//
//							System.out.println("\t사용불가! 다시 결제창으로 돌아갑니다.");
//
//							result = false;
//						}
//
//					} else if (input.equals("n")) {
//						System.out.println();
//						System.out.print("\t결제할 최종 금액: ");
//						dor.setFinalfinalprice(dor.finalprice);
//						dor.setuPilePoint(dor.getFinalfinalprice()/10);
//						System.out.println(dor.getFinalprice());
//
//						mu.pause(1);
//						System.out.println();
//						mu.fffinal();
//					}else {
//						
//						result = false;
//					}
//
			}
		}
}