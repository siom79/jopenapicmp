package jopenapicmp.cmp.compat;

import jopenapicmp.cmp.ApiComparator;
import jopenapicmp.cmp.ApiCompatibilityChange;
import jopenapicmp.cmp.ApiCompatibilityCheck;
import jopenapicmp.cmp.ChangeStatus;
import jopenapicmp.cmp.diff.ObjectDiff;
import jopenapicmp.model.asyncapi.AsyncApi;
import jopenapicmp.parser.ApiParser;
import jopenapicmp.util.AccessHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class SchemaCompatibilityCheckTest {

	@Test
	void testSchemaPropertyAdded() {
		ApiComparator comparator = new ApiComparator();
		ApiParser parser = new ApiParser();
		AsyncApi oldApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+json;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string").getBytes(StandardCharsets.UTF_8), "/path");
		AsyncApi newApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+yaml;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string\n" +
			"                role:\n" +
			"                    type: string").getBytes(StandardCharsets.UTF_8), "/path");

		ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
		ApiCompatibilityCheck apiCompatibilityCheck = new ApiCompatibilityCheck();
		apiCompatibilityCheck.check(objectDiff);

		Assertions.assertThat(objectDiff.getMapDiffs().get("channels")
			.getMapDiffEntries().get("userSignedUp")
			.getObjectDiff().getObjectDiffs().get("subscribe")
			.getObjectDiffs().get("message")
			.getObjectDiffs().get("payload")
			.getMapDiffs().get("properties")
			.getMapDiffEntries().get("role")
			.getApiCompatibilityChanges()).contains(ApiCompatibilityChange.SCHEMA_PROPERTY_ADDED);
	}

	@Test
	void testSchemaPropertyRemoved() {
		ApiComparator comparator = new ApiComparator();
		ApiParser parser = new ApiParser();
		AsyncApi oldApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+json;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string\n" +
			"                role:\n" +
			"                    type: string").getBytes(StandardCharsets.UTF_8), "/path");
		AsyncApi newApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+yaml;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string").getBytes(StandardCharsets.UTF_8), "/path");

		ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
		ApiCompatibilityCheck apiCompatibilityCheck = new ApiCompatibilityCheck();
		apiCompatibilityCheck.check(objectDiff);

		Assertions.assertThat(objectDiff.getMapDiffs().get("channels")
			.getMapDiffEntries().get("userSignedUp")
			.getObjectDiff().getObjectDiffs().get("subscribe")
			.getObjectDiffs().get("message")
			.getObjectDiffs().get("payload")
			.getMapDiffs().get("properties")
			.getMapDiffEntries().get("role")
			.getApiCompatibilityChanges()).contains(ApiCompatibilityChange.SCHEMA_PROPERTY_REMOVED);
	}

	@Test
	void testSchemaPropertyRemovedRequired() {
		ApiComparator comparator = new ApiComparator();
		ApiParser parser = new ApiParser();
		AsyncApi oldApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+json;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string\n" +
			"                role:\n" +
			"                    type: string\n" +
			"            required:\n" +
			"                - user\n" +
			"                - role").getBytes(StandardCharsets.UTF_8), "/path");
		AsyncApi newApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+yaml;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string\n" +
			"            required:\n" +
			"                - user").getBytes(StandardCharsets.UTF_8), "/path");

		ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
		ApiCompatibilityCheck apiCompatibilityCheck = new ApiCompatibilityCheck();
		apiCompatibilityCheck.check(objectDiff);

		Assertions.assertThat(objectDiff.getMapDiffs().get("channels")
			.getMapDiffEntries().get("userSignedUp")
			.getObjectDiff().getObjectDiffs().get("subscribe")
			.getObjectDiffs().get("message")
			.getObjectDiffs().get("payload")
			.getListDiffs().get("required")
			.getListDiffEntryByName("role")
			.getApiCompatibilityChanges()).contains(ApiCompatibilityChange.SCHEMA_PROPERTY_REQUIRED_REMOVED);
	}

	@Test
	void testSchemaPropertyAddedRequired() {
		ApiComparator comparator = new ApiComparator();
		ApiParser parser = new ApiParser();
		AsyncApi oldApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+json;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string\n" +
			"            required:\n" +
			"                - user").getBytes(StandardCharsets.UTF_8), "/path");
		AsyncApi newApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+yaml;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string\n" +
			"                    minLength: 6\n" +
			"                role:\n" +
			"                    type: string\n" +
			"            required:\n" +
			"                - user\n" +
			"                - role").getBytes(StandardCharsets.UTF_8), "/path");

		ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
		ApiCompatibilityCheck apiCompatibilityCheck = new ApiCompatibilityCheck();
		apiCompatibilityCheck.check(objectDiff);

		Assertions.assertThat(objectDiff.getMapDiffs().get("channels")
			.getMapDiffEntries().get("userSignedUp")
			.getObjectDiff().getObjectDiffs().get("subscribe")
			.getObjectDiffs().get("message")
			.getObjectDiffs().get("payload")
			.getListDiffs().get("required")
			.getListDiffEntryByName("role")
			.getApiCompatibilityChanges()).contains(ApiCompatibilityChange.SCHEMA_PROPERTY_REQUIRED_ADDED);
	}

	@Test
	void testSchemaPropertyAddedDeeper() {
		ApiComparator comparator = new ApiComparator();
		ApiParser parser = new ApiParser();
		AsyncApi oldApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+json;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: object\n" +
			"                    properties:\n" +
			"                        name:\n" +
			"                            type: string\n" +
			"                        email:\n" +
			"                            type: string").getBytes(StandardCharsets.UTF_8), "/path");
		AsyncApi newApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+yaml;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: object\n" +
			"                    properties:\n" +
			"                        firstName:\n" +
			"                            type: string\n" +
			"                        lastName:\n" +
			"                            type: string\n" +
			"                        email:\n" +
			"                            type: string").getBytes(StandardCharsets.UTF_8), "/path");

		ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
		ApiCompatibilityCheck apiCompatibilityCheck = new ApiCompatibilityCheck();
		apiCompatibilityCheck.check(objectDiff);

		Assertions.assertThat(AccessHelper.get(objectDiff, "channels.userSignedUp.subscribe.message.payload.properties.user.properties.name")
			.getChangeStatus()).isEqualTo(ChangeStatus.REMOVED);
		Assertions.assertThat(((HasCompatibilityChanges) AccessHelper.get(objectDiff,
			"channels.userSignedUp.subscribe.message.payload.properties.user.properties.name"))
			.getApiCompatibilityChanges()).contains(ApiCompatibilityChange.SCHEMA_PROPERTY_REMOVED);
		Assertions.assertThat(AccessHelper.get(objectDiff, "channels.userSignedUp.subscribe.message.payload.properties.user.properties.firstName")
			.getChangeStatus()).isEqualTo(ChangeStatus.ADDED);
		Assertions.assertThat(((HasCompatibilityChanges) AccessHelper.get(objectDiff,
			"channels.userSignedUp.subscribe.message.payload.properties.user.properties.firstName"))
			.getApiCompatibilityChanges()).contains(ApiCompatibilityChange.SCHEMA_PROPERTY_ADDED);
		Assertions.assertThat(AccessHelper.get(objectDiff, "channels.userSignedUp.subscribe.message.payload.properties.user.properties.lastName")
			.getChangeStatus()).isEqualTo(ChangeStatus.ADDED);
		Assertions.assertThat(((HasCompatibilityChanges) AccessHelper.get(objectDiff,
			"channels.userSignedUp.subscribe.message.payload.properties.user.properties.lastName"))
			.getApiCompatibilityChanges()).contains(ApiCompatibilityChange.SCHEMA_PROPERTY_ADDED);
		Assertions.assertThat(AccessHelper.get(objectDiff, "channels.userSignedUp.subscribe.message.payload.properties.user.properties.email")
			.getChangeStatus()).isEqualTo(ChangeStatus.UNCHANGED);
		Assertions.assertThat(((HasCompatibilityChanges) AccessHelper.get(objectDiff,
			"channels.userSignedUp.subscribe.message.payload.properties.user.properties.email"))
			.getApiCompatibilityChanges()).isEmpty();
	}

	@Test
	void testSchemaMinLengthDecreased() {
		ApiComparator comparator = new ApiComparator();
		ApiParser parser = new ApiParser();
		AsyncApi oldApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+json;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string\n" +
			"                    minLength: 10").getBytes(StandardCharsets.UTF_8), "/path");
		AsyncApi newApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        schemaFormat: application/schema+yaml;version=draft-07\n" +
			"        payload:\n" +
			"            type: object\n" +
			"            properties:\n" +
			"                user:\n" +
			"                    type: string\n" +
			"                    minLength: 5").getBytes(StandardCharsets.UTF_8), "/path");

		ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
		ApiCompatibilityCheck apiCompatibilityCheck = new ApiCompatibilityCheck();
		apiCompatibilityCheck.check(objectDiff);

		Assertions.assertThat(((HasCompatibilityChanges) AccessHelper.get(objectDiff,
			"channels.userSignedUp.subscribe.message.payload.properties.user.minLength"))
			.getApiCompatibilityChanges()).contains(ApiCompatibilityChange.SCHEMA_MIN_LENGTH_DECREASED);
	}

	@Test
	void testSchemaOneOfRef() {
		ApiComparator comparator = new ApiComparator();
		ApiParser parser = new ApiParser();
		AsyncApi oldApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        oneOf:\n" +
			"          - $ref: \"#/components/messages/M1\"\n" +
			"          - $ref: \"#/components/messages/M2\"\n" +
			"components:\n" +
			"  messages:\n" +
			"    M1:\n" +
			"      title: \"Title1\"\n" +
			"    M2:\n" +
			"      title: \"Title2\"").getBytes(StandardCharsets.UTF_8), "/path");
		AsyncApi newApi = (AsyncApi) parser.parse(("asyncapi: 2.6.0\n" +
			"channels:\n" +
			"  userSignedUp:\n" +
			"    subscribe:\n" +
			"      operationId: userSignup\n" +
			"      summary: Action to sign a user up.\n" +
			"      description: A longer description\n" +
			"      message:\n" +
			"        oneOf:\n" +
			"          - $ref: \"#/components/messages/M1\"\n" +
			"          - $ref: \"#/components/messages/M2\"\n" +
			"components:\n" +
			"  messages:\n" +
			"    M1:\n" +
			"      title: \"Title1\"\n" +
			"    M2:\n" +
			"      title: \"Title2\"").getBytes(StandardCharsets.UTF_8), "/path");

		ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
		ApiCompatibilityCheck apiCompatibilityCheck = new ApiCompatibilityCheck();
		apiCompatibilityCheck.check(objectDiff);

		Assertions.assertThat(AccessHelper.get(objectDiff,
			"channels.userSignedUp.subscribe.message.oneOf").getChangeStatus()
		).isEqualTo(ChangeStatus.UNCHANGED);
		Assertions.assertThat(AccessHelper.get(objectDiff,
			"channels.userSignedUp.subscribe.message.oneOf[0]").getChangeStatus()
		).isEqualTo(ChangeStatus.UNCHANGED);
		Assertions.assertThat(AccessHelper.get(objectDiff,
			"channels.userSignedUp.subscribe.message.oneOf[1]").getChangeStatus()
		).isEqualTo(ChangeStatus.UNCHANGED);
	}
}
