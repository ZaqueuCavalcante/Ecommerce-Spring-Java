package br.com.zaqueucavalcante.ecommercespringjava.entities.enums;

public enum UserType {

	PHYSICAL_PERSON(1, "Physical Person"),
	LEGAL_PERSON(2, "Legal Person");

	private int code;
	private String description;

	private UserType(int code, String description) {
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
	public static UserType valueOf(int code) {
		for (UserType value : UserType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid UserType code.");
	}
}
