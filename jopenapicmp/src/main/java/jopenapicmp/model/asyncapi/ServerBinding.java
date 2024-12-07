package jopenapicmp.model.asyncapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jopenapicmp.model.Model;
import jopenapicmp.model.Reference;
import jopenapicmp.model.asyncapi.kafka.KafkaServerBinding;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServerBinding implements Reference, Model {
    private KafkaServerBinding kafka;
    @JsonProperty("$ref")
    private String ref;
}
