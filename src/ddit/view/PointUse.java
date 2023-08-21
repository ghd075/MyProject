package ddit.view;

import ddit.dao.OrderDAO;
import ddit.dto.Member;
import ddit.dto.Store;
import ddit.util.Util;

public class PointUse {
	
	private static OrderDAO orderDAO = OrderDAO.getInstance();
	private static MemberUI mu = MemberUI.getInstance();
	private static PointUse instance = null;
	
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
	public void paychoice(Store store, Member member, String ORDERNO, int totalOrderPrice) {
				
		boolean result = true;
		
		while(result) {
	        System.out.print("\n\t결제 하시겠습니까?(Y/N) : ");
			String input = Util.sc.nextLine().trim();
			if (input.equalsIgnoreCase("Y")) {
				//누적 주문수 업데이트
				orderDAO.UpdateOrderCount(store.getStoNo());
				payPay(member, totalOrderPrice, ORDERNO);
				return; // paychoice 메서드 종료
			}else if(input.equalsIgnoreCase("N")) {
				orderDAO.deleteOrderAndMenu(ORDERNO); // 주문 및 주문 내역 삭제 메서드 호출
				result = false;
			}else {
	            System.out.println("\n\t잘못된 입력입니다. 다시 입력해주세요. ( ͡°- ͡°) ");
	            Util.sc.nextLine(); // 개행 문자 처리
	        }	
		}
	}
		

	/**
	 * 
	 * 
	 * @document 포인트 사용 여부와 사용할 포인트를 결정하는 페이지입니다. 
	 * 포인트는 총가격의 2%로 계산해서 적립된다.
	 * 
	 */
	public void payPay(Member member, int totalOrderPrice, String ORDERNO) {

		boolean result = true;
		int finalprice = 0;			//결제할 최종금액
		int userPoint = member.getmPoint();		//고객포인트
		int UsedP  = 0;							//적립한 포인트
		int usedPoint = 0;						//사용할 포인트
		while (result) {
			System.out.print("\n\t포인트를 사용하시겠습니까? (Y/N) : ");
			String input = Util.sc.nextLine().trim();
			System.out.println();
			if (input.equalsIgnoreCase("Y")) {
				System.out.println();
				System.out.print("\t사용할 포인트 : ");
				String pointStr = Util.sc.nextLine().trim();
				usedPoint = Integer.parseInt(pointStr);

				if(usedPoint <= member.getmPoint()) {
					System.out.println();
					System.out.print("\t결제할 최종 금액: ");
					finalprice = totalOrderPrice - usedPoint;
					System.out.println(Util.formatPrice(finalprice));
					 // 사용한 포인트 차감 및 적립
					userPoint -= usedPoint;
					UsedP = (int) (totalOrderPrice * 0.02);
					userPoint += UsedP;
					System.out.println("\n\t포인트 적립 후 사용 가능 포인트: " + userPoint);
					orderDAO.updateUserPoint(member.getCstNo(), userPoint);
					
					mu.pause(1);
					System.out.println();
					mu.fffinal(member, UsedP, usedPoint, ORDERNO);
					return; // payPay 메서드 종료
				}else if(usedPoint > member.getmPoint()) {
					System.out.println("\n\t포인트가 부족합니다. 다시 입력해주세요. ◎_◎ ");
					result = false;
				}
			}else if(input.equalsIgnoreCase("N")) {
				System.out.println();
				System.out.print("\t결제할 최종 금액: ");
				System.out.println(Util.formatPrice(totalOrderPrice));
				 // 사용한 포인트 차감 및 적립
				UsedP = (int) (totalOrderPrice * 0.02);
				userPoint += UsedP;
				System.out.println("\n\t포인트 적립 후 사용 가능 포인트: " + userPoint);
				orderDAO.updateUserPoint(member.getCstNo(), userPoint);
				
				mu.pause(1);
				System.out.println();
				mu.fffinal(member, UsedP, usedPoint, ORDERNO);
				return; // payPay 메서드 종료
			}else {
	            System.out.println("\t잘못된 입력입니다. 다시 입력해주세요. ( ͡°- ͡°) ");
	            Util.sc.nextLine(); // 개행 문자 처리
			}
		}
	}
}
