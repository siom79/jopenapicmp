package jopenapicmp.model.openapi;

import jopenapicmp.model.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class OAuthFlow implements Model {
	private String authorizationUrl;
	private String tokenUrl;
	private String refreshUrl;
	private Map<String, String> scopes = new HashMap<>();
}
