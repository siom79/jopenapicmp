package com.github.siom79.jopenapicmp;

import com.github.siom79.jopenapicmp.configuration.Version;
import jopenapicmp.JAsyncApiCmpUserException;
import jopenapicmp.cmp.ApiComparator;
import jopenapicmp.cmp.ApiCompatibilityCheck;
import jopenapicmp.cmp.diff.ObjectDiff;
import jopenapicmp.model.Api;
import jopenapicmp.model.asyncapi.AsyncApi;
import jopenapicmp.model.openapi.OpenApi;
import jopenapicmp.output.OutputProcessor;
import jopenapicmp.output.StdoutOutputSink;
import jopenapicmp.parser.ApiParser;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Mojo(name = "jopenapicmp", defaultPhase = LifecyclePhase.VERIFY)
public class JOpenApiCmpMojo extends AbstractMojo {

	@Parameter(required = true)
	private Version oldVersion;
	@Parameter(required = true)
	private Version newVersion;
	@Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
	private File outputDirectory;

	public void execute() throws MojoExecutionException {
		try {
			ApiParser apiParser = new ApiParser();
			Api oldApi = apiParser.parse(Files.readAllBytes(oldVersion.getFile().toPath()), oldVersion.getFile().getPath());
			Api newApi = apiParser.parse(Files.readAllBytes(newVersion.getFile().toPath()), newVersion.getFile().getPath());
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
			StdoutOutputSink stdoutOutputTracker = new StdoutOutputSink();
			OutputProcessor stdoutYamlOutput = new OutputProcessor(stdoutOutputTracker);
			stdoutYamlOutput.process(objectDiff);
			Path outputPath = Paths.get(outputDirectory.getPath(), "jasyncapicmp.txt");
			getLog().info("Writing output to " + outputPath);
			Files.write(outputPath, stdoutOutputTracker.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}
}
