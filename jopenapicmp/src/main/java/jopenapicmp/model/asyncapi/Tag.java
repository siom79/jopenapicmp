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
public class Tag implements Reference, Model {
    @ListId
    private String name;
    private String description;
    private ExternalDocumentation externalDocs;
    @JsonProperty("$ref")
    private String ref;
}
