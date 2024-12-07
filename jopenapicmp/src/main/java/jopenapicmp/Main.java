package jopenapicmp;

import jopenapicmp.cli.CliParser;
import jopenapicmp.cmp.ApiComparator;
import jopenapicmp.cmp.ApiCompatibilityCheck;
import jopenapicmp.cmp.diff.ObjectDiff;
import jopenapicmp.config.Config;
import jopenapicmp.config.ConfigValidator;
import jopenapicmp.loader.FileLoader;
import jopenapicmp.model.Api;
import jopenapicmp.model.asyncapi.AsyncApi;
import jopenapicmp.model.openapi.OpenApi;
import jopenapicmp.output.OutputProcessor;
import jopenapicmp.output.StdoutOutputSink;
import jopenapicmp.parser.ApiParser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            CliParser cliParser = new CliParser();
            Config config = cliParser.parse(args);
            ConfigValidator configValidator = new ConfigValidator();
            configValidator.validate(config);
			ObjectDiff diff = getObjectDiff(config);
			StdoutOutputSink stdoutOutputTracker = new StdoutOutputSink();
            OutputProcessor stdoutYamlOutput = new OutputProcessor(stdoutOutputTracker);
            stdoutYamlOutput.process(diff);
            System.out.println(stdoutOutputTracker);
        } catch (JAsyncApiCmpUserException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Execution failed: " + e.getMessage(), e);
        }
    }

	private static ObjectDiff getObjectDiff(Config config) {
		FileLoader fileLoader = new FileLoader();
		byte[] oldFile = fileLoader.loadFileFromDisc(config.getOldPath());
		byte[] newFile = fileLoader.loadFileFromDisc(config.getNewPath());
		ApiParser apiParser = new ApiParser();
		Api oldApi = apiParser.parse(oldFile, config.getOldPath());
		Api newApi = apiParser.parse(newFile, config.getNewPath());
		ApiComparator comparator = new ApiComparator();
		ObjectDiff objectDiff;
		if (oldApi instanceof AsyncApi && newApi instanceof AsyncApi) {
			objectDiff = comparator.compare((AsyncApi) oldApi, (AsyncApi) newApi);
		} else if (oldApi instanceof OpenApi && newApi instanceof OpenApi) {
			objectDiff = comparator.compare((OpenApi) oldApi, (OpenApi) newApi);
		} else {
			throw new JAsyncApiCmpUserException("Unable to compare AsyncApi vs. OpenApi.");
		}
		ApiCompatibilityCheck apiCompatibilityCheck = new ApiCompatibilityCheck();
		objectDiff = apiCompatibilityCheck.check(objectDiff);
		return objectDiff;
	}
}
