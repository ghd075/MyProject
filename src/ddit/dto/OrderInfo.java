package ddit.dto;

import ddit.util.Util;

public class OrderInfo {
    private String storeName;
    private String menuName;
    private int orderPrice;
    private int totalPrice;

    public OrderInfo(String storeName, String menuName, int orderPrice, int totalPrice) {
        this.storeName = storeName;
        this.menuName = menuName;
        this.orderPrice = orderPrice;
        this.totalPrice = totalPrice;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

	@Override
	public String toString() {
		return String.format("\t%s \t%s \t%s \t%s"
                ,	Util.convert(storeName, 25)		
                , 	Util.convert(menuName, 25)
                ,	Util.convert(orderPrice+"원", 10)		
                , 	Util.convert(totalPrice+"원",10)	
                );
	}
    
    
}
