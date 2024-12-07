# jopenapicmp

jopenapicmp is a tool to compare two versions of an [OpenAPI](https://swagger.io/specification/) or 
an [asyncapi](https://www.asyncapi.com/) specification:

```bash
java -jar target/jopenapicmp-0.0.4-jar-with-dependencies.jar -o old.yaml -n new.yaml
```
A read-to-use jar file with all dependencies can be downloaded from the Maven Central Repository [here](https://repo1.maven.org/maven2/io/github/siom79/jopenapicmp/jopenapicmp/0.0.4/jopenapicmp-0.0.4-jar-with-dependencies.jar).

It can also be used as a library:
```java
ApiParser parser = new ApiParser();
OpenApi oldApi = (OpenApi) parser.parse(oldFile, "old.yaml");
OpenApi newApi = (OpenApi) parser.parse(newFile, "new.yaml");

ApiComparator comparator = new ApiComparator();
ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
```
jopenapicmp is available in the Maven Central Repository:
[![maven](https://img.shields.io/maven-central/v/io.github.siom79.jopenapicmp/jopenapicmp.svg)](https://central.sonatype.com/artifact/io.github.siom79.jopenapicmp/jopenapicmp)
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

- Java API to parse OpenAPI and async API specifications
- Comparison of two parsed API specifications
- Differences can be printed out as simple YAML diff format
- Check for incompatibilities

### Planned features

Development of this tool is not completed, yet. Hence, there are a lot of planned
features:

- Evaluation of more changes as breaking or not-breaking
- HTML report
- Gradle plugin

## Online

You can try it out online [here](https://www.japicmp.de/).

## Usage

To use the tool you can clone the repository, build the tool and run it:

```bash
git clone https://github.com/siom79/jopenapicmp.git
cd jopenapicmp
mvn package
java -jar target/jopenapicmp-0.0.4-jar-with-dependencies.jar -o old.yaml -n new.yaml
```

Sample output (old values and incompatibilities are printed as comment):

```yaml
asyncapi: 2.6.0 # ===
id: https://github.com/siom79/jopenapicmp # ===
info: # ===
  description: This is a sample app. # ===
  termsOfService: https://asyncapi.org/terms/ # ===
  title: AsyncAPI Sample App # ===
  version: 1.0.0 # *** old: 0.1.0
  license: # ===
    name: Apache 2.0 # ===
    url: https://www.apache.org/licenses/LICENSE-2.0.html # ===
  contact: # ===
    name: API Support # ===
    url: https://www.asyncapi.org/support # ===
    email: support@asyncapi.org # *** old: team@asyncapi.org
servers: # ===
  development: # ===
    protocol: amqp # ===
    description: Development AMQP broker. # ===
    protocolVersion: 0-9-1 # ===
    url: localhost:5672 # ===
    tags: # ===
      - name: env:development # ===
        description: This environment is meant for developers to run their own tests. # ===
  staging: # ===
    protocol: amqp # ===
    description: RabbitMQ broker. Use the `env` variable to point to either `production` or `staging`. # *** old: RabbitMQ broker. Use the `env` variable to point to either `production`.
    url: rabbitmq.in.mycompany.com:5672 # ===
    variables: # ===
      env: # ===
        description: Environment to connect to. It can be either `production` or `staging`. # ===
        enums: # ===
          - production # ===
          - staging # ===
    tags: # +++
      - name: env:staging # +++
        description: This environment is meant for staging. # +++
defaultContentType: application/json # ===
channels: # ===
  userSignedUp: # ===
    subscribe: # ===
      summary: Action to sign a user up. # ===
      operationId: userSignup # ===
      description: A longer description # ===
      message: # ===
        contentType: application/json # ===
        headers: # ===
          format: object # ===
          properties: # ===
            correlationId: # ===
              description: Correlation ID set by application # ===
              format: string # ===
            applicationInstanceId: # ===
              description: Unique identifier for a given instance of the publishing application # ===
              format: string # ===
        payload: # ===
          format: object # ===
          properties: # ===
            role: # +++, compatibility change: SCHEMA_PROPERTY_ADDED
              format: string # +++
            user: # ===
              format: string # ===
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

## Contributions

Pull requests are welcome, but please follow these rules:

* The basic editor settings (indentation, newline, etc.) are described in the `.editorconfig` file (see [EditorConfig](http://editorconfig.org/)).
* Provide a unit test for every change.
* Name classes/methods/fields expressively.
* Fork the repo and create a pull request (see [GitHub Flow](https://guides.github.com/introduction/flow/index.html)).

## Release

This is the release procedure:
* Update ReleaseNotes.md.
* Set the release version in maven:
```bash
mvn versions:set -DnewVersion=<version>-SNAPSHOT
mvn versions:commit
```
* Increment version in README.md / Site-Report
``` bash
python3 release.py --release-version <release-version> --old-version <old-version>
```
* Push changes to remote repository.
* Run release [Action](https://github.com/siom79/jopenapicmp/actions/workflows/release.yml)
* Login to [OSSRH](https://s01.oss.sonatype.org/#stagingRepositories)
    * Download released artifact from staging repository.
    * Close and release staging repository if sanity checks are successful.
* Update maven site report with [Action](https://github.com/siom79/jopenapicmp/actions/workflows/mvn-site.yml)



