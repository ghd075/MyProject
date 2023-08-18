package ddit.dto;

import ddit.util.Util;

public class OrderItem {
    private Menu menu;
    private int quantity;
    private int totalPrice;

    public OrderItem(Menu selectedMenu, int quantity, int totalPrice) {
    	this.menu = selectedMenu;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    
    public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
    public String toString() {
        return  String.format("\n\t%s \t%s\t%s원"
    			,	Util.convert(menu.getMnName(), 25)		
    			,	Util.convert(quantity+"", 6)		
    			, 	Util.convert(totalPrice+"",6));
    }
}
