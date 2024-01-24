# Free Mobile for Java
Send SMS messages to your [Free Mobile](https://mobile.free.fr) device via any internet-connected device.

For example, you can configure a control panel or storage connected to your home network to send a notification to your mobile phone when an event occurs.

## Quick start
> SMS notifications require an API key. If you are not already registered, [sign up for a Free Mobile account](https://mobile.free.fr/subscribe).

### Get an API key
You first need to enable the **SMS notifications** in [your subscriber account](https://mobile.free.fr/account).
This will give you an identification key allowing access to the [Free Mobile](https://mobile.free.fr) API.

![Screenshot](screenshot.webp)

### Get the library
Download the latest JAR file of **Free Mobile for Java** from the GitHub releases:  
https://github.com/cedx/free-mobile.java/releases/latest

Add it to your class path. Now in your [Java](https://www.oracle.com/java) code, you can use:

```java
import io.belin.free_mobile.*;
```

## Usage
This library provides the `Client` class, which allow to send SMS messages to your mobile phone by using the `sendMessage()` method:

```java
import io.belin.free_mobile.Client;

class Program {
  public static void main(String... args) {
    try {
      var client = new Client("your account identifier", "your API key");
      client.sendMessage("Hello World from Java!");
      System.out.println("The message was sent successfully.");
    }
    catch (Client.Exception e) {
      System.err.println("An error occurred: " + e.getMessage());
    }
  }
}
```

The `Client.sendMessage()` method throws a `Client.Exception` if any error occurred while sending the message.

> The text of the messages will be automatically truncated to **160** characters: you can't send multipart messages using this library.

## See also
- [API reference](api/)
- [GitHub releases](https://github.com/cedx/free-mobile.java/releases)
