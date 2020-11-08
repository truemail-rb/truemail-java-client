# ![Truemail web API client library for Java](https://truemail-rb.org/assets/images/truemail_logo.png)

[![CircleCI](https://circleci.com/gh/truemail-rb/truemail-java-client/tree/develop.svg?style=svg)](https://circleci.com/gh/truemail-rb/truemail-java-client/tree/develop)
[![Maven Central](https://img.shields.io/maven-central/v/org.truemail-rb/truemail-java-client.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22org.truemail-rb%22%20AND%20a:%22truemail-java-client%22)
[![GitHub](https://img.shields.io/github/license/truemail-rb/truemail-java-client)](LICENSE.txt)
[![Gitter](https://badges.gitter.im/truemail-rb/community.svg)](https://gitter.im/truemail-rb/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v1.4%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)

`truemail-java-client` - Web API Java client for Truemail Server.

> Actual and maintainable documentation :books: for developers is living [here](https://truemail-rb.org/truemail-java-client).

## Table of Contents

- [Requirements](#requirements)
- [Installation](#installation)
  - [Maven](#maven)
  - [Gradle](#gradle)
- [Usage](#usage)
  - [Creating configuration instance](#creating-configuration-instance)
  - [Establishing connection with Truemail API](#establishing-connection-with-truemail-api)
- [Truemail family](#truemail-family)
- [Contributing](#contributing)
- [License](#license)
- [Code of Conduct](#code-of-conduct)
- [Credits](#credits)
- [Versioning](#versioning)
- [Changelog](CHANGELOG.md)
- [Developers Documentation](https://truemail-rb.org/truemail-java-client)

## Requirements

Java 1.8+

## Installation

> Please change {version} to needed version, for example 0.1.0. All available versions you can find [here](https://github.com/truemail-rb/truemail-java-client/releases).

### Apache Maven

```xml
<dependency>
  <groupId>org.truemail-rb</groupId>
  <artifactId>truemail-java-client</artifactId>
  <version>{version}</version>
</dependency>
```

### Gradle Groovy

```groovy
implementation 'org.truemail-rb:truemail-java-client:{version}'
```

### Apache Ivy

```xml
<dependency org="org.truemail-rb" name="truemail-java-client" rev="{version}" />
```

### Groovy Grape

```groovy
@Grapes(
  @Grab(group='org.truemail-rb', module='truemail-java-client', version='{version}')
)
```

## Usage

```java
import org.truemail_rb.TruemailClient;
import org.truemail_rb.client.TruemailConfiguration;
```

To have an access for `TruemailClient#validate` you must create configuration instance first as in the example below:

### Creating configuration instance

```java
// Required parameter. It should be true or false
boolean secureConnection = true;

// Required parameter. It should be a hostname or an ip address where Truemail server runs
String host = "example.com";

// Optional parameter. By default it is equal 9292
int port = 80;

// Required parameter. It should be valid Truemail server access token
String token = "token";

TruemailConfiguration truemailConfiguration = new TruemailConfiguration(secureConnection, host, token, port);

// or without port, will be used 9292 port by default
// TruemailConfiguration truemailConfiguration = new TruemailConfiguration(secureConnection, host, token);
```

### Establishing connection with Truemail API

After successful configuration, you can establish connection with Truemail server.

```java
TruemailClient truemailClient = new TruemailClient(truemailConfiguration);
truemailClient.validate("admin@bestweb.com.ua")

=>

{
  "date": "2020-10-26 10:42:42 +0200",
  "email": "admin@bestweb.com.ua",
  "validation_type": "smtp",
  "success": true,
  "errors": null,
  "smtp_debug": null,
  "configuration": {
    "validation_type_by_domain": null,
    "whitelist_validation": false,
    "whitelisted_domains": null,
    "blacklisted_domains": null,
    "smtp_safe_check": false,
    "email_pattern": "default gem value",
    "smtp_error_body_pattern": "default gem value"
  }
}
```

`TruemailClient#validate` always returns JSON data. If something goes wrong you will receive JSON with error details:

```json
{
  "truemail_client_error": "error details"
}
```

---

## Truemail family

All Truemail solutions: https://truemail-rb.org

| Name | Type | Description |
| --- | --- | --- |
| [truemail](https://github.com/truemail-rb/truemail) | ruby gem | Configurable plain Ruby email validator, main core |
| [truemail server](https://github.com/truemail-rb/truemail-rack) | ruby app | Lightweight rack based web API wrapper for Truemail |
| [truemail-rack-docker](https://github.com/truemail-rb/truemail-rack-docker-image) | docker image | Lightweight rack based web API [dockerized image](https://hub.docker.com/r/truemail/truemail-rack) :whale: of Truemail server |
| [truemail-ruby-client](https://github.com/truemail-rb/truemail-ruby-client) | ruby gem | Web API Ruby client for Truemail Server |
| [truemail-crystal-client](https://github.com/truemail-rb/truemail-crystal-client) | crystal shard | Web API Crystal client for Truemail Server |
| [truemail-rspec](https://github.com/truemail-rb/truemail-rspec) | ruby gem | Truemail configuration, auditor and validator RSpec helpers |

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/truemail-rb/truemail-java-client. This project is intended to be a safe, welcoming space for collaboration, and contributors are expected to adhere to the [Contributor Covenant](http://contributor-covenant.org) code of conduct. Please check the [open tikets](https://github.com/truemail-rb/truemail-java-client/issues). Be shure to follow Contributor Code of Conduct below and our [Contributing Guidelines](CONTRIBUTING.md).

## License

The gem is available as open source under the terms of the [MIT License](https://opensource.org/licenses/MIT).

## Code of Conduct

Everyone interacting in the `truemail-java-client` project’s codebases, issue trackers, chat rooms and mailing lists is expected to follow the [code of conduct](CODE_OF_CONDUCT.md).

## Credits

- [The Contributors](https://github.com/truemail-rb/truemail-java-client/graphs/contributors) for code and awesome suggestions
- [The Stargazers](https://github.com/truemail-rb/truemail-java-client/stargazers) for showing their support

## Versioning

`truemail-java-client` uses [Semantic Versioning 2.0.0](https://semver.org)
