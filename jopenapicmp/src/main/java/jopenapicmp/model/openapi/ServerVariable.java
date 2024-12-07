package jopenapicmp.model.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jopenapicmp.model.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ServerVariable implements Model {
	@JsonProperty("enum")
	private List<String> enumProperty = new ArrayList<>();
	@JsonProperty("default")
	private String defaultProperty;
	private String description;
}
