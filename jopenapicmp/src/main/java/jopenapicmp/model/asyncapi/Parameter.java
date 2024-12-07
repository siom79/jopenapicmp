package jopenapicmp.model.asyncapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jopenapicmp.model.Model;
import jopenapicmp.model.Reference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Parameter implements Reference, Model {
    private String description;
    private Schema schema;
    private String location;
    @JsonProperty("$ref")
    private String ref;
}
