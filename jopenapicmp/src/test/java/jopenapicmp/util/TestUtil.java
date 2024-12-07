package jopenapicmp.util;

import jopenapicmp.cmp.ApiComparator;
import jopenapicmp.cmp.diff.ObjectDiff;
import jopenapicmp.model.asyncapi.AsyncApi;
import jopenapicmp.model.openapi.OpenApi;
import jopenapicmp.parser.ApiParser;

import java.nio.charset.StandardCharsets;

public class TestUtil {

    public static ObjectDiff compareAsyncApiYaml(String oldYaml, String newYaml) {
        ApiParser apiParser = new ApiParser();
        AsyncApi oldAsyncApi = (AsyncApi) apiParser.parse(oldYaml.getBytes(StandardCharsets.UTF_8), "/old");
        AsyncApi newAsyncApi = (AsyncApi) apiParser.parse(newYaml.getBytes(StandardCharsets.UTF_8), "/new");
        ApiComparator comparator = new ApiComparator();
        return comparator.compare(oldAsyncApi, newAsyncApi);
    }

	public static ObjectDiff compareOpenApiYaml(String oldYaml, String newYaml) {
		ApiParser apiParser = new ApiParser();
		OpenApi oldApi = (OpenApi) apiParser.parse(oldYaml.getBytes(StandardCharsets.UTF_8), "/old");
		OpenApi newApi = (OpenApi) apiParser.parse(newYaml.getBytes(StandardCharsets.UTF_8), "/new");
		ApiComparator comparator = new ApiComparator();
		return comparator.compare(oldApi, newApi);
	}
}
