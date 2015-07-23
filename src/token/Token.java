package token;

public class Token {
	int Id;
	String Type;
	String Token;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public Token(int id, String type, String token) {
		super();
		Id = id;
		Type = type;
		Token = token;
	}

	public Token() {
		super();
	}

}
