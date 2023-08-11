package ddit.dto;

public class OrderDetail {
	   private String orderNuno;
	    private int orderPrice;
	    private int cnt;
	    private String orderNo;
	    private String mnCode;

	    public OrderDetail() {
	        // 기본 생성자
	    }

	    public OrderDetail(String orderNuno, int orderPrice, int cnt, String orderNo, String mnCode) {
	        this.orderNuno = orderNuno;
	        this.orderPrice = orderPrice;
	        this.cnt = cnt;
	        this.orderNo = orderNo;
	        this.mnCode = mnCode;
	    }

		/**주문내역번호를 불러오는 메소드*/
	    public String getOrderNuno() {
	        return orderNuno;
	    }

		/**주문내역번호를 설정하는 메소드*/
	    public void setOrderNuno(String orderNuno) {
	        this.orderNuno = orderNuno;
	    }
	    
		/**주문한 메뉴의 가격을 불러오는 메소드*/
	    public int getOrderPrice() {
	        return orderPrice;
	    }

	    /**주문한 메뉴의 가격을 설정하는 메소드*/
	    public void setOrderPrice(int orderPrice) {
	        this.orderPrice = orderPrice;
	    }

	    /**주문한 메뉴의 수량을 불러오는 메소드*/
	    public int getCnt() {
	        return cnt;
	    }

	    /**주문한 메뉴의 수량을 설정하는 메소드*/
	    public void setCnt(int cnt) {
	        this.cnt = cnt;
	    }

	    /**주문번호를 불러오는 메소드*/
	    public String getOrderNo() {
	        return orderNo;
	    }

	    /**주문번호를 설정하는 메소드*/
	    public void setOrderNo(String orderNo) {
	        this.orderNo = orderNo;
	    }

	    /**메뉴코드를 불러오는 메소드*/
	    public String getMnCode() {
	        return mnCode;
	    }

	    /**메뉴코드를 설정하는 메소드*/
	    public void setMnCode(String mnCode) {
	        this.mnCode = mnCode;
	    }

	    @Override
	    public String toString() {
	        return "OrderDetailDTO{" +
	                "orderNuno='" + orderNuno + '\'' +
	                ", orderPrice=" + orderPrice +
	                ", cnt=" + cnt +
	                ", orderNo='" + orderNo + '\'' +
	                ", mnCode='" + mnCode + '\'' +
	                '}';
	    }
}
