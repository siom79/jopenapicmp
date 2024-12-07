package jopenapicmp.model.openapi;

import jopenapicmp.model.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Example implements Model {
	private String summary;
	private String description;
	private String externalValue;
}
