# jopenapicmp

jopenapicmp is a tool to compare two versions of an [OpenAPI](https://swagger.io/specification/) or 
an [asyncapi](https://www.asyncapi.com/) specification:

```bash
java -jar target/jopenapicmp-0.0.1-jar-with-dependencies.jar -o old.yaml -n new.yaml
```

It can also be used as a library to parse and compare asyncapi specifications:
```java
ApiParser parser = new ApiParser();
OpenApi oldApi = (OpenApi) parser.parse(oldFile, "old.yaml");
OpenApi newApi = (OpenApi) parser.parse(newFile, "new.yaml");

ApiComparator comparator = new ApiComparator();
ObjectDiff objectDiff = comparator.compare(oldApi, newApi);
```

There is also a maven plugin available.

The website is located [here](https://siom79.github.io/jopenapicmp/).

## Motivation

Every time you release a new version of an API defined by a asyncapi specification,
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
java -jar target/jopenapicmp-0.0.1-jar-with-dependencies.jar -o old.yaml -n new.yaml
```

You can also use it as a library:

```xml
<dependency>
  <groupId>io.github.siom79.jopenapicmp</groupId>
  <artifactId>jopenapicmp</artifactId>
  <version>0.0.1</version>
</dependency>
```

To integrate the output into your build, you can utilize the maven plugin:

```xml
<build>
	<plugins>
		<plugin>
			<groupId>io.github.siom79.jopenapicmp</groupId>
			<artifactId>jopenapicmp-maven-plugin</artifactId>
			<version>0.0.1</version>
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

This is the current release procedure:

```bash
./release-prepare.sh <last-released-version> <new-version>
```
Run Github Action [Release](https://github.com/siom79/jopenapicmp/actions/workflows/maven-publish-central.yml)

Login to [OSSRH](https://s01.oss.sonatype.org/#stagingRepositories), close and release staging repository
```bash
mvn versions:set -DnewVersion=<new-SNAPSHOT-version>
mvn versions:commit
git add .
git commit -m "New SNAPSHOT version <new-SNAPSHOT-version>"
```
