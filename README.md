# Translator

[![](https://jitpack.io/v/ondrej-nemec/translator.svg)](https://jitpack.io/#ondrej-nemec/translator)
[![MIT License](http://img.shields.io/badge/license-MIT-green.svg) ](https://github.com/ondrej-nemec/translator/LICENSE)

* [Description](#description)
* [How to install](#how-to-install)
* [Usage](#usage)
	* [Constructor](#constructor)
	* [Translate](#translate)
	* [Properties file syntax](properties-file-syntax)

## Description
Library offers management of `ResourceBundle`s, translate message with count and adding variables to message.
## How to install
### Download:
<a href="https://ondrej-nemec.github.io/download/translator-1.1.jar" target=_blank>Download jar</a>
### Maven:

After `build` tag:
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
And to `dependencies`:
```xml
<dependency>
  <groupId>com.github.ondrej-nemec</groupId>
  <artifactId>translator</artifactId>
  <version>v1.1-alpha</version>
</dependency>
```

## Usage
### Constructor

```java
new Translator(path); // String path to you *.properties
new Translator(path, logger);
```

```java
new Translator(path, otherResourceBundles); // String path to default *.properties and Map: String name -> ResourceBundle
new Translator(path, otherResourceBundles, logger);
```
### Translate
For simply translate you can use `translate` method. If no key found, the WARNING will be logged and **key returned**
```java
// *.properties file
traslate.test=Testing translate
```
```java
// translate(String key) 
translator.translate("translate.test"); // return: 'Testing translate'
translator.translate("not-existing-key"); // return: 'not-existing-key'
```
Replace some variables in returned message. If in text are more variables that given, `RuntimeException` will be throwed. If are given more variables that are in text INFO will be logged.
```java
// *.properties file
translate.variables=Total price %price% %currency%
```
```java
// translate(String key, String... variables)
translator.translate("translate.variables", 1000+"", "EUR"); // return: 'Total price 1000 EUR'
translator.translate("translate.variables", 1000+""); // `RuntimeException` throwed
translator.translate("translate.variables", 1000+"", "EUR", "currency"); // return: 'Total price 1000 EUR'
```
Select message depending on count and replace variable with count. If given count does not found, WARNING will be logged and **key + ' : ' + count returned.**
```java
// *.properties file
translate.count=1~You are only one year old;2<=~You are %years% years old
```
```java
// translate(String key, int count)
translator.translate("translate.count", 1); // return: 'You are only one year old'
translator.translate("translate.count", 8); // return: 'You are 8 years old'
translator.translate("translate.count", -1); // return: 'translate.count : -1'
```
If you wont to use another resource use `translateFrom(String resourceName, String key)` or `translateFrom(String resourceName, String key, String... variables)` or `String translateFrom(String resourceName, String key, int count)`

### Properties file syntax
For messages without count or variables syntax remains.
```java
key=message
```
For message with variables
```java
key=message %variable%
```
'%' is separator for variables in default, you could set variable separator using methods `setVariableSeparators(char startSeparator, char endSeparator)` - as you can see, separators can be different.
Syntax for messages with count
```java
key=1~message one;2<=~other message %count%
```
';' separate messages, '~' separate count from message, both could be setted `setMessageSeparator(String separator)` `setCountSeparator(String separator)`.
#### Count part of message
`count logicOperator` - exaple: '1=' mean exactly count 1, '4>' mean everything less that 4. You could use '=', '>', '<', '<=' or '>='. You could define count like '1,5' (for 1 or 5), '1-5' (from 1 to 5), '-4--2'(from -4 to -2) or '1-3,8' (from 1 to 3 or 8).
Remember: count is looking from left to right, so `1<~mess1;4<~other` for counts more that 4 return 'mess'.
