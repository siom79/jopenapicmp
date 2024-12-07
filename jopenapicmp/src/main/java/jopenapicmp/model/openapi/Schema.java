package jopenapicmp.model.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import jopenapicmp.model.Model;
import jopenapicmp.model.Reference;
import jopenapicmp.model.asyncapi.ListId;
import jopenapicmp.model.asyncapi.SchemaPattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Schema implements Model, Reference {
	@ListId
	private String title;
	private String type;
	private String description;
	private List<String> required = new ArrayList<>();
	private Map<String, jopenapicmp.model.asyncapi.Schema> properties = new HashMap<>();
	private Object additionalProperties;
	private Map<String, jopenapicmp.model.asyncapi.Schema> patternProperties = new HashMap<>();
	private int minLength;
	private int maxLength;
	private String format;
	private int minimum;
	private int maximum;
	private int multipleOf;
	private int exclusiveMinimum;
	private int exclusiveMaximum;
	private String pattern;
	private SchemaPattern propertyNames;
	private int minProperties;
	private int maxProperties;
	private Schema items;
	private int minItems;
	private int maxItems;
	private boolean uniqueItems;
	@JsonProperty("enum")
	private List<String> enumeration;
	@JsonProperty("const")
	private String constant;
	private boolean readOnly;
	private boolean writeOnly;
	private List<jopenapicmp.model.asyncapi.Schema> allOf;
	private List<jopenapicmp.model.asyncapi.Schema> anyOf;
	private List<jopenapicmp.model.asyncapi.Schema> oneOf;
	private jopenapicmp.model.asyncapi.Schema not;
	@JsonProperty("$ref")
	private String ref;
}
