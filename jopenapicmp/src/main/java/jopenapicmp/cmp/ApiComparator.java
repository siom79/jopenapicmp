package jopenapicmp.cmp;

import jopenapicmp.cmp.diff.ObjectDiff;
import jopenapicmp.model.asyncapi.AsyncApi;
import jopenapicmp.model.openapi.OpenApi;

public class ApiComparator {

    public ObjectDiff compare(AsyncApi oldApi, AsyncApi newApi) {
        return ObjectDiff.compare(AsyncApi.class, oldApi, newApi);
    }

	public ObjectDiff compare(OpenApi oldApi, OpenApi newApi) {
		return ObjectDiff.compare(OpenApi.class, oldApi, newApi);
	}
}
