package br.com.zaqueucavalcante.ecommercespringjava.entities.orders;

public enum OrderStatus {

	WAITING_PAYMENT(1, "Waiting Payment"),
	PAID(2, "Paid"),
	SHIPPED(3, "Shipped"),
	DELIVERED(4, "Delivered"),
	CANCELED(5, "Canceled");
	
	private int code;
	private String description;
	
	private OrderStatus(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public int getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public static OrderStatus valueOf(int code) {
		for (OrderStatus value : OrderStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid OrderStatus code.");
	}

}
