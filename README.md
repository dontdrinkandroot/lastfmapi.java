Java bindings for the last.fm API
=================================

[![Flattr this git repo](http://api.flattr.com/button/flattr-badge-large.png)](https://flattr.com/submit/auto?user_id=shoxrocks&url=http://www.dontdrinkandroot.net/github/lastfmapi.java&title=Java%20bindings%20for%20the%20last.fm%20API&language=&tags=github&category=software)


[![Donate](http://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=W9NAXW8YAZ4D6&item_name=lastfmapi.java%20Donation&currency_code=EUR) 

Maven
-----

To use the library in your maven managed project, simply add the following to your ``pom.xml``

```xml
<repositories>
  ...
  <repository>
    <id>public</id> <!-- or whatever id you want -->
    <url>http://maven.dontdrinkandroot.net/content/groups/public</url>
  </repository>
  ...
</repositories>

<dependencies>
  ...
  <dependency>
    <groupId>net.dontdrinkandroot</groupId>
    <artifactId>lastfmapi</artifactId>
    <version>${lastfmapi.version}</version> <!-- Replace with desired version -->
  </dependency>
  ...
</dependencies>
```

Usage
-----

To create a new Instance of the LastfmWebServices simply do the following:

```java
String apiKey = "yourapikey";
String apiSecret = "yourapisecret";
LastfmWebServices ws = new DefaultLastfmWebServices(apiKey, apiSecret);
```
To perform requests you can then use this instance:

```java
List<User> getAllFriends(String userName, LastfmWebServices ws) throws LastfmWebServicesException {

  List<User> friends = new ArrayList<User>();
  PaginatedResult<List<User>> result = ws.fetch(User.getFriends(userName, null, null, 1));
  friends.addAll(result.getEntries());
  while (result.hasMorePages()) {
    result = ws.fetch(User.getFriends(userName, null, null, result.getNextPage()));
    friends.addAll(result.getEntries());
  }

  return friends;
}
```
