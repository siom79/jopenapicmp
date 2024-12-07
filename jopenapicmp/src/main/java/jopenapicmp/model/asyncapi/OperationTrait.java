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
public class OperationTrait implements Reference, Model {
    @ListId
    private String title;
    private String summary;
    private String description;
    private SecurityScheme security;
    private List<Tag> tags = new ArrayList<>();
    private ExternalDocumentation externalDocs;
    private OperationBinding binding;
    @JsonProperty("$ref")
    private String ref;
}
