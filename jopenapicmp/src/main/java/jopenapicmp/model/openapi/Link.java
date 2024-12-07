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
public class Link implements Model {
	private String operationRef;
	private String operationId;
	private Map<String, String> parameters = new HashMap<>();
	private Map<String, String> requestBody = new HashMap<>();
	private String description;
	private Server server;
}
