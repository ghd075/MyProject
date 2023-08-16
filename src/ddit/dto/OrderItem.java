package ddit.dto;

public class OrderItem {
    private Menu menu;
    private int quantity;
    private int totalPrice;

    public OrderItem(Menu menu, int quantity, int totalPrice) {
        this.menu = menu;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return String.format("메뉴: %s, 수량: %d, 총 가격: %d원",
            menu.getMnName(), quantity, totalPrice);
    }
}
