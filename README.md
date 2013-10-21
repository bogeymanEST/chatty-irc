chatty-irc
========
IRC connector for Chatty.

Building
========
Building is done using Maven. Build the `pom.xml` file using your favourite Java IDE or the command prompt: `mvn install`. 
The bot's jar file is located in the `target` directory.

Installation
============
Drop the jar file into Chatty's `bots` folder, add the following after `bots:` 
in your Chatty config file changing values as needed and restart Chatty:

      - type: irc
        settings:
             name: MyBot #The nickname the bot will use, IRC doesn't allow spaces in nicknames.
             server: irc.ircserver.com #The IRC server address to connect to
             channels: ["#mychannel", "#myotherchannel"] #List of channels to connect to
             
For more info, see [Chatty Configuration](https://github.com/bogeymanEST/chatty/wiki/Getting-Started#configuration).
