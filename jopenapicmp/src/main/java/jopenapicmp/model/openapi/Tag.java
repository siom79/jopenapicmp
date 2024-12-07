package jopenapicmp.model.openapi;

import jopenapicmp.model.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Tag implements Model {
	private String name;
	private String description;
	private ExternalDocumentation externalDocs;
}
