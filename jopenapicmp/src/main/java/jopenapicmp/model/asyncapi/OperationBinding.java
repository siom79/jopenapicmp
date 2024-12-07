package jopenapicmp.model.asyncapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jopenapicmp.model.Model;
import jopenapicmp.model.Reference;
import jopenapicmp.model.asyncapi.kafka.KafkaOperationBinding;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OperationBinding implements Reference, Model {
    private KafkaOperationBinding kafka;
    @JsonProperty("$ref")
    private String ref;
}
