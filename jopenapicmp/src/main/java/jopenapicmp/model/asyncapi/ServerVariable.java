package jopenapicmp.model.asyncapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jopenapicmp.model.Model;
import jopenapicmp.model.Reference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ServerVariable implements Reference, Model {
    @JsonProperty("enum")
    private List<String> enums = new ArrayList<>();
    @JsonProperty("default")
    private String defaultValue;
    private String description;
    private List<String> examples = new ArrayList<>();
    @JsonProperty("$ref")
    private String ref;
}
