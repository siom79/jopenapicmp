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
public class RequestBody implements Model {
	private String description;
	private Map<String, MediaType> content = new HashMap<>();
	private boolean required;
}
