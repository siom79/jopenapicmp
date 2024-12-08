# jopenapicmp

jopenapicmp is a tool to compare two versions of an [OpenAPI](https://swagger.io/specification/) or
an [asyncapi](https://www.asyncapi.com/) specification:

```bash
java -jar target/jopenapicmp-0.0.4-jar-with-dependencies.jar -o old.yaml -n new.yaml
```
A read-to-use jar file with all dependencies can be downloaded from the Maven Central Repository [here](https://repo1.maven.org/maven2/io/github/siom79/jopenapicmp/jopenapicmp/0.0.4/jopenapicmp-0.0.4-jar-with-dependencies.jar).

It can also be used as a library:
```java
AsyncApiParser apiParser = new AsyncApiParser();
AsyncApi oldAsyncApi = apiParser.parse(oldFile, config.getOldPath());
AsyncApi newAsyncApi = apiParser.parse(newFile, config.getNewPath());

AsyncApiComparator comparator = new AsyncApiComparator();
ObjectDiff diff = comparator.compare(oldAsyncApi, newAsyncApi);
```
jopenapicmp is available in the Maven Central Repository:
[![maven](https://img.shields.io/maven-central/v/io.github.siom79.jopenapicmp/jopenapicmp?color=green)](https://central.sonatype.com/artifact/io.github.siom79.jopenapicmp/jopenapicmp)
```xml
<dependency>
  <groupId>io.github.siom79.jopenapicmp</groupId>
  <artifactId>jopenapicmp</artifactId>
  <version>0.0.4</version>
</dependency>
```
A maven plugin allows you to integrate the checks into your build:
```xml
<build>
	<plugins>
		<plugin>
			<groupId>io.github.siom79.jopenapicmp</groupId>
			<artifactId>jopenapicmp-maven-plugin</artifactId>
			<version>0.0.4</version>
			<executions>
				<execution>
					<id>cmp</id>
					<phase>verify</phase>
					<goals>
						<goal>jopenapicmp</goal>
					</goals>
					<configuration>
						<oldVersion>
							<file>old_2.6.0.yaml</file>
						</oldVersion>
						<newVersion>
							<file>new_2.6.0.yaml</file>
						</newVersion>
					</configuration>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```

## Motivation

Every time you release a new version of an API defined by an OpenApi or asyncapi specification,
you need to check if only these things have changed that you wanted to change.
Probably you also have to inform the clients of the API about and changes and want
to document them.

Without the appropriate tooling, this task is tedious and error-prone.
This tool/library helps you to determine the differences between two versions of
an asyncapi specification.

## Features

- Java API to parse async API specifications
- Comparison of two parsed async API specifications
- Differences can be printed out as simple YAML diff format

### Planned features

Development of this tool is not completed, yet. Hence, there are a lot of planned
features:

- Evaluation of more changes as breaking or not-breaking
- HTML report
- Gradle plugin

## Usage

To use the tool you can clone the repository, build the tool and run it:

```bash
git clone https://github.com/siom79/jopenapicmp.git
cd jopenapicmp
mvn package
java -jar target/jopenapicmp-0.0.4-jar-with-dependencies.jar -o old.yaml -n new.yaml
```

A read-to-use jar file with all dependencies can be downloaded from the Maven Central Repository [here](https://repo1.maven.org/maven2/io/github/siom79/jopenapicmp/jopenapicmp/0.0.4/jopenapicmp-0.0.4-jar-with-dependencies.jar).
Be sure to download the file with the extension `-jar-with-dependencies.jar`.

You can also use it as a library:

```xml
<dependency>
  <groupId>io.github.siom79.jopenapicmp</groupId>
  <artifactId>jopenapicmp</artifactId>
  <version>0.0.4</version>
</dependency>
```

To integrate the output into your build, you can utilize the maven plugin:

```xml
<build>
	<plugins>
		<plugin>
			<groupId>io.github.siom79.jopenapicmp</groupId>
			<artifactId>jopenapicmp-maven-plugin</artifactId>
			<version>0.0.4</version>
			<executions>
				<execution>
					<id>cmp</id>
					<phase>verify</phase>
					<goals>
						<goal>jopenapicmp</goal>
					</goals>
					<configuration>
						<oldVersion>
							<file>old_2.6.0.yaml</file>
						</oldVersion>
						<newVersion>
							<file>new_2.6.0.yaml</file>
						</newVersion>
					</configuration>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```

Sample output:

```yaml
asyncapi: 2.6.0 (===)
id: https://github.com/siom79/jopenapicmp (===)
info: (===)
  description: This is a sample app. (===)
  termsOfService: https://asyncapi.org/terms/ (===)
  title: AsyncAPI Sample App (===)
  version: 1.0.0 (*** old: 0.1.0)
  license: (===)
    name: Apache 2.0 (===)
    url: https://www.apache.org/licenses/LICENSE-2.0.html (===)
  contact: (===)
    name: API Support (===)
    url: https://www.asyncapi.org/support (===)
    email: support@asyncapi.org (*** old: team@asyncapi.org)
servers: (===)
  production: (===)
    protocol: secure-mqtt (===)
    description: The production API server (===)
    url: gigantic-server.com:{port}/path (===)
    variables: (===)
      port: (===)
        defaultValue: 8883 (===)
        enums: (***)
          - 8883 (===)
          - 8884 (+++)
channels: (===)
  userSignedUp: (===)
    subscribe: (===)
      summary: Action to sign a user up. (===)
      operationId: userSignup (===)
      message: (===)
        contentType: application/json (===)
        payload: (===)
          format: object (===)
          properties: (===)
            role: (+++, compatibility change: SCHEMA_PROPERTY_ADDED)
              format: string (+++)
            user: (===)
              format: string (===)
[...]
```

The following incompatibilities ares detected:

| Incompatibility                  | Backward compatible | Forward compatible |
|----------------------------------| ------------------- | ------------------ |
| CHANNEL_ADDED                    |true | true |
| CHANNEL_REMOVED                  |false | false |
| OPERATION_OPERATION_ID_CHANGED   |false | false |
| MESSAGE_MESSAGE_ID_CHANGED       |false | false |
| MESSAGE_CONTENT_TYPE_CHANGED     |false | false |
| MESSAGE_SCHEMA_FORMAT_CHANGED    |false | false |
| SCHEMA_TYPE_CHANGED              |false | false |
| SCHEMA_PROPERTY_ADDED            |true | true |
| SCHEMA_PROPERTY_REMOVED          |true | true |
| SCHEMA_PROPERTY_REQUIRED_ADDED   |false | true |
| SCHEMA_PROPERTY_REQUIRED_REMOVED |true | false |
| SCHEMA_MIN_LENGTH_INCREASED      |false|true|
| SCHEMA_MIN_LENGTH_DECREASED      |true | false |
| SCHEMA_MAX_LENGTH_INCREASED      |true | false |
| SCHEMA_MAX_LENGTH_DECREASED      |false|true|
