package jopenapicmp.model.asyncapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jopenapicmp.model.Model;
import jopenapicmp.model.Reference;
import jopenapicmp.model.asyncapi.kafka.KafkaMessageBinding;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageBinding implements Reference, Model {
    KafkaMessageBinding kafka;
    @JsonProperty("$ref")
    private String ref;
}