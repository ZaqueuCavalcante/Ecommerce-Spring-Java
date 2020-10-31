package br.com.zaqueucavalcante.ecommercespringjava.entities.clients;

public enum ClientType {

	PHYSICAL_PERSON(1, "Physical Person"),
	LEGAL_PERSON(2, "Legal Person");

	private int code;
	private String description;

	private ClientType(int code, String description) {
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
	public static ClientType valueOf(int code) {
		for (ClientType value : ClientType.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid ClientType code.");
	}

}
