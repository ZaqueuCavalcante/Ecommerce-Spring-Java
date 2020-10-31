package br.com.zaqueucavalcante.ecommercespringjava.entities.payments;

public enum PaymentStatus {

	PENDING(1, "Pending"),
	SETTLED(2, "Settled"),
	CANCELED(3, "Canceled");
	
	private int code;
	private String description;
	
	private PaymentStatus(int code, String description) {
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
	public static PaymentStatus valueOf(int code) {
		for (PaymentStatus value : PaymentStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid PaymentStatus code.");
	}

}
