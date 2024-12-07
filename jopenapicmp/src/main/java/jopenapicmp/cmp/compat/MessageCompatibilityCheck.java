package jopenapicmp.cmp.compat;

import jopenapicmp.cmp.ApiCompatibilityChange;
import jopenapicmp.cmp.ChangeStatus;
import jopenapicmp.cmp.diff.ObjectDiff;
import jopenapicmp.cmp.diff.StringDiff;

public class MessageCompatibilityCheck {

	public static void check(ObjectDiff messageDiff) {
		StringDiff messageIdDiff = messageDiff.getStringDiffs().get("messageId");
		if (messageIdDiff != null) {
			if (messageIdDiff.getChangeStatus() == ChangeStatus.CHANGED) {
				messageIdDiff.addCompatibilityChange(ApiCompatibilityChange.MESSAGE_MESSAGE_ID_CHANGED);
			}
		}
		StringDiff contentTypeDiff = messageDiff.getStringDiffs().get("contentType");
		if (contentTypeDiff != null) {
			if (contentTypeDiff.getChangeStatus() == ChangeStatus.CHANGED) {
				contentTypeDiff.addCompatibilityChange(ApiCompatibilityChange.MESSAGE_CONTENT_TYPE_CHANGED);
			}
		}
		StringDiff schemaFormatDiff = messageDiff.getStringDiffs().get("schemaFormat");
		if (schemaFormatDiff != null) {
			if (schemaFormatDiff.getChangeStatus() == ChangeStatus.CHANGED) {
				schemaFormatDiff.addCompatibilityChange(ApiCompatibilityChange.MESSAGE_SCHEMA_FORMAT_CHANGED);
			}
		}
		ObjectDiff payloadDiff = messageDiff.getObjectDiffs().get("payload");
		SchemaCompatibilityCheck.check(payloadDiff);
	}
}
