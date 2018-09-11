# Translator
**Newest version:**1.0

* [Description](#description)
* [How to install](#how-to-install)
* [Usage](#usage)

## Description
Library offers management of `ResourceBundle`s, translate message with count and adding variables to message.
## How to install
### Download:
<a href="">Unter construction</a>
### Maven:

`
<dependency>
  <groupId>io.github.ondrej-nemec.translator</groupId>
  <artifactId>translator</artifactId>
  <version>1.0</version>
</dependency>
`

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
```
Replace some variables in returned message. If in text are more variables that given, RuntimeException will be throwed. If are given more variables that are in text INFO will be logged.
```java
// *.properties file
translate.variables=Total price %price% %currency%
```
```java
// translate(String key, String... variables)
translator.translate("translate.variables", 1000+"", "EUR"); // return: 'Total price 1000 EUR'
```
Select message depending on coun and replace variable with count. If given count does not foud, WARNING will be logged and **key + ' : ' + count returned.**
```java
// *.properties file
translate.count=1~You are only one year old;2<=~You are %years% years old
```
```java
// translate(String key, int count)
translator.translate("translate.count", 1); // return: 'You are only one year old'
translator.translate("translate.count", 8); // return: 'You are 8 years old'
```
If you wont to use another resource use `translateFrom(String resourceName, String key)` or `translateFrom(String from, String key, String... variables)` or `String translateFrom(String from, String key, int count)`

### *.properties syntax
//TODO required syntax of messages, setting, Info, rules for count