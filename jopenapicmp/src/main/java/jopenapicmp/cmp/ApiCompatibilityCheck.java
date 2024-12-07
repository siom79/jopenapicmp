package jopenapicmp.cmp;

import jopenapicmp.cmp.compat.ChannelCompatibilityCheck;
import jopenapicmp.cmp.diff.ObjectDiff;

public class ApiCompatibilityCheck {

	public ObjectDiff check(ObjectDiff objectDiff) {
		ChannelCompatibilityCheck.check(objectDiff);
		return objectDiff;
	}
}
