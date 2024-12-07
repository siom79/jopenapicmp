package jopenapicmp.model.asyncapi.kafka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KafkaServerBinding {
    private String schemaRegistryUrl;
    private String schemaRegistryVendor;
    private String bindingVersion;
}
