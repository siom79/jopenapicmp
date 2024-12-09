package jopenapicmp.cmp.diff;

import jopenapicmp.cmp.ChangeStatus;
import jopenapicmp.model.openapi.Schema;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ObjectDiffTest {

    @Test
    void testIntegerDiffs() {
        Schema oldSchema = new Schema();
        oldSchema.setMinLength(5);
        Schema newSchema = new Schema();
        newSchema.setMinLength(10);

        ObjectDiff objectDiff = ObjectDiff.compare(Schema.class, oldSchema, newSchema);

        Assertions.assertThat(objectDiff).isNotNull();
        Assertions.assertThat(objectDiff.getIntegerDiffs()).isNotEmpty();
        Assertions.assertThat(objectDiff.getIntegerDiffs().get("minLength").getChangeStatus()).isEqualTo(ChangeStatus.CHANGED);
    }
}