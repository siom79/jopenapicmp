package jopenapicmp.cmp.compat;

import jopenapicmp.cmp.ApiCompatibilityChange;
import jopenapicmp.cmp.ChangeStatus;
import jopenapicmp.cmp.diff.ObjectDiff;
import jopenapicmp.cmp.diff.StringDiff;

public class OperationCompatibilityCheck {

	public static void check(ObjectDiff operationDiff) {
		StringDiff operationIdDiff = operationDiff.getStringDiffs().get("operationId");
		if (operationIdDiff != null) {
			if (operationIdDiff.getChangeStatus() == ChangeStatus.CHANGED) {
				operationIdDiff.addCompatibilityChange(ApiCompatibilityChange.OPERATION_OPERATION_ID_CHANGED);
			}
		}
		ObjectDiff messageDiff = operationDiff.getObjectDiffs().get("message");
		if (messageDiff != null) {
			MessageCompatibilityCheck.check(messageDiff);
		}
	}
}
