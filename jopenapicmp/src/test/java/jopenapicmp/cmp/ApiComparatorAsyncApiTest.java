package jopenapicmp.cmp;

import jopenapicmp.cmp.diff.ObjectDiff;
import jopenapicmp.model.asyncapi.AsyncApi;
import jopenapicmp.output.OutputProcessor;
import jopenapicmp.output.StdoutOutputSink;
import jopenapicmp.parser.ApiParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class ApiComparatorAsyncApiTest {

    @Test
    void testServerAddedRemovedChanged() {
        ApiComparator comparator = new ApiComparator();
        ApiParser parser = new ApiParser();
        AsyncApi oldApi = (AsyncApi) parser.parse(("asyncapi: '2.6.0'\n" +
				"info:\n" +
				"  title: \"Test\"\n" +
				"  version: \"1.0.0\"\n" +
				"servers:\n" +
				"  removed:\n" +
				"    url: removed-server.example.com\n" +
				"    protocol: amqp\n" +
				"  changed:\n" +
				"    url: changed-server.example.com\n" +
				"    protocol: kafka\n" +
				"  unchanged:\n" +
				"    url: unchanged-server.example.com\n" +
				"    protocol: amqp").getBytes(StandardCharsets.UTF_8), "/path");
        AsyncApi newApi = (AsyncApi) parser.parse(("asyncapi: '2.6.0'\n" +
				"info:\n" +
				"  title: \"Test\"\n" +
				"  version: \"1.0.0\"\n" +
				"servers:\n" +
				"  new:\n" +
				"    url: new-server.example.com\n" +
				"    protocol: amqp\n" +
				"  changed:\n" +
				"    url: changed-server.example.com\n" +
				"    protocol: kafka\n" +
				"  unchanged:\n" +
				"    url: unchanged-server.example.com\n" +
				"    protocol: amqp").getBytes(StandardCharsets.UTF_8), "/path");

        ObjectDiff objectDiff = comparator.compare(oldApi, newApi);

        Assertions.assertThat(objectDiff.getMapDiffs().get("servers").getMapDiffEntries().get("new").getChangeStatus()).isEqualTo(ChangeStatus.ADDED);
        Assertions.assertThat(objectDiff.getMapDiffs().get("servers").getMapDiffEntries().get("changed").getChangeStatus()).isEqualTo(ChangeStatus.UNCHANGED);
        Assertions.assertThat(objectDiff.getMapDiffs().get("servers").getMapDiffEntries().get("unchanged").getChangeStatus()).isEqualTo(ChangeStatus.UNCHANGED);
        Assertions.assertThat(objectDiff.getMapDiffs().get("servers").getMapDiffEntries().get("removed").getChangeStatus()).isEqualTo(ChangeStatus.REMOVED);
    }

    @Test
    void testListTags() {
        ApiComparator comparator = new ApiComparator();
        ApiParser parser = new ApiParser();
        AsyncApi oldApi = (AsyncApi) parser.parse(("asyncapi: '2.6.0'\n" +
				"info:\n" +
				"  title: \"Test\"\n" +
				"  version: \"1.0.0\"\n" +
				"servers:\n" +
				"  my-server:\n" +
				"    url: my-server.example.com\n" +
				"    protocol: amqp\n" +
				"    tags:\n" +
				"      - name: my-server\n" +
				"        description: desc-my-server\n" +
				"      - name: amqp").getBytes(StandardCharsets.UTF_8), "/path");
        AsyncApi newApi = (AsyncApi) parser.parse(("asyncapi: '2.6.0'\n" +
				"info:\n" +
				"  title: \"Test\"\n" +
				"  version: \"1.0.0\"\n" +
				"servers:\n" +
				"  my-server:\n" +
				"    url: my-server.example.com\n" +
				"    protocol: amqp\n" +
				"    tags:\n" +
				"      - name: example-server\n" +
				"        description: desc-changed-my-server\n" +
				"      - name: amqp").getBytes(StandardCharsets.UTF_8), "/path");

        ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
        Assertions.assertThat(objectDiff.getMapDiffs().get("servers").getMapDiffEntries().get("my-server").getObjectDiff().getListDiffs().get("tags").getListDiffsEntries().size()).isEqualTo(3);

        StdoutOutputSink stdoutOutputTracker = new StdoutOutputSink();
        OutputProcessor outputProcessor = new OutputProcessor(stdoutOutputTracker);
        outputProcessor.process(objectDiff);
    }
}
