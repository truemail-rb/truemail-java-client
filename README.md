# ![Truemail web API client library for Java](https://truemail-rb.org/assets/images/truemail_logo.png)

![GitHub release (latest by date)](https://img.shields.io/github/v/release/truemail-rb/truemail-java-client) [![CircleCI](https://circleci.com/gh/truemail-rb/truemail-java-client/tree/develop.svg?style=svg)](https://circleci.com/gh/truemail-rb/truemail-java-client/tree/develop) [![Gitter](https://badges.gitter.im/truemail-rb/community.svg)](https://gitter.im/truemail-rb/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge) [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v1.4%20adopted-ff69b4.svg)](CODE_OF_CONDUCT.md)

`truemail-java-client` - Truemail web API client library for Java.

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

All available versions you can find [here](https://github.com/truemail-rb/truemail-java-client/releases).

### Maven

```xml
<dependency>
  <groupId>org.truemail</groupId>
  <artifactId>truemail-java-client</artifactId>
  <version>${version}</version>
</dependency>
```

### Gradle

```groovy
compile group: 'org.truemail', name: 'truemail-java-client', version: 'version'
```

## Usage

```java
import org.truemail.Client;
import org.truemail.client.Configuration;
```

To have an access for `Client#validate` you must create configuration instance first as in the example below:

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

Configuration configuration = new Configuration(secureConnection, host, token, port);

// or without port, will be used 9292 port by default
// Configuration configuration = new Configuration(secureConnection, host, token);
```

### Establishing connection with Truemail API

After successful configuration, you can establish connection with Truemail server.

```java
Client client = new Client(configuration);
client.validate("admin@bestweb.com.ua")

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

`Client#validate` always returns JSON data. If something goes wrong you will receive JSON with error details:

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
