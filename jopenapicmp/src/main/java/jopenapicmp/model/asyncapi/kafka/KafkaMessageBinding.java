package jopenapicmp.model.asyncapi.kafka;

import jopenapicmp.model.asyncapi.Schema;

public class KafkaMessageBinding {
    private Schema key;
    private String schemaIdLocation;
    private String schemaIdPayloadEncoding;
    private String schemaLookupStrategy;
    private String bindingVersion;
}
