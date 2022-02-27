# ModernJDA

Library for easy creation of discord bots in Java based on [JDA](https://github.com/DV8FromTheWorld/JDA).

## Requirements
- Java 17 or higher

## Maven
```xml
<repository>
    <id>projectcode-repository</id>
    <name>ProjectCode Repository</name>
    <url>https://repo.projectcode.pl/releases</url>
</repository>
```
```xml
<dependency>
    <groupId>me.adipol.modernjda</groupId>
    <artifactId>ModernJDA</artifactId>
    <version>1.0.1</version>
</dependency>
```
## Gradle
```groovy
maven {
    url "https://repo.projectcode.pl/releases"
}
```
```groovy
implementation "me.adipol.modernjda:ModernJDA:1.0.1"
```

## Example code
```java
public static void main(String[] args) {
    ModernJDA modernJDA = new ClientBuilder()
            .setToken("YOUR_TOKEN")
            .checkUpdate(false) //optional, check updates on start (default: true)
            .enableIntent(GatewayIntent.GUILD_MEMBERS) //optional
            .enableCache(CacheFlag.ACTIVITY) //optional
            .build();
    
    modernJDA.run();
}
```

## Create command
```java
@CommandInfo(name = "foo")
public class FooCommand extends AbstractCommand {
    
    @Override
    public void handleCommand(Member member, MessageChannel channel, MessageReceivedEvent event, String[] args) {
        //COMMAND HERE
    }
}
```
```java
modernJDA.getCommandManager().registerCommand(new FooCommand());
modernJDA.getCommandManager().getDefaultCommandMap().setPrefix("$"); //change prefix to $
```

### CommandInfo
```java
@CommandInfo(name = "foo", aliases = {"foo1", "foo2"}) //set aliases for command
@CommandInfo(name = "foo", permissions = {"role1", "role2"}) //set one of required roles to use command
@CommandInfo(name = "foo", coolDown = 10000) //command cool down in ms
@CommandInfo(name = "foo", coolDownScope = CoolDownScope.COMMAND) //cool down for command
@CommandInfo(name = "foo", coolDownScope = CoolDownScope.MEMBER) //cool down for specific member
```

## Create custom CommandMap
```java
CommandMap adminCommandMap = new CommandMap("a!"); //create command map with a! prefix
        
adminCommandMap.setPrefix("admin!"); //set command map prefix to admin!
adminCommandMap.setPermission("role1", "role2"); //set one of the required roles to use command

adminCommandMap.registerCommand(new BanCommand());
adminCommandMap.registerCommand(new KickCommand());

modernJDA.getCommandManager().registerCommandMap(adminCommandMap);
```

## Register listener
```java
public class EventListener implements Listener {

    @EventHandler
    public void messageDeleteEvent(MessageDeleteEvent event) {
    }

    @EventHandler
    public void commandExecuteEvent(CommandExecuteEvent event) {
        //Execute when member execute the command.
    }

    @EventHandler
    public void commandExceptionEvent(CommandExceptionEvent event) {
        //Execute when command throw error.
    }

    @EventHandler
    public void commandMissingPermissionEvent(CommandMissingPermissionEvent event) {
        //Execute when member use command without the appropriate permissions.
    }

    @EventHandler
    public void commandNotFoundEvent(CommandNotFoundEvent event) {
        //Execute when member use command that not exists.
    }

    @EventHandler
    public void commandCoolDownEvent(CommandCoolDownEvent event) {
        //Execute when member use command with cool down.
    }
}
```
```java
modernJDA.getEventManager().registerListener(new EventListener());
```

## Custom Event
```java
@AllArgsConstructor
@Getter
public class FooCustomEvent extends CustomEvent {
    private final String message;
}
```
```java
@CommandInfo(name = "foo")
public class FooCommand extends AbstractCommand {

    @Override
    public void handleCommand(Member member, MessageChannel channel, MessageReceivedEvent event, String[] args) {
        ModernJDA.getInstance().getEventManager().callEvent(new FooCustomEvent("my message!"));
    }
}
```
```java
public class EventListener implements Listener {

    @EventHandler
    public void myCustomEvent(FooCustomEvent event) {
        System.out.println(event.getMessage());
    }
}
```
## Custom Config
```java
@Getter
@Setter
public class MyConfig implements Config {
    private String prefix = "!";
}
```
```java
MyConfig myConfig = ConfigUtil.loadConfig("config", MyConfig.class);
```
This method will create ``yml`` file with the given name. If the file exists, class fields will be set from configuration file.

```java
MyConfig myConfig = modernJDA.loadConfig("config", MyConfig.class, true); //override file each time the bot is started
```

## HTTP Request
```json
{
  "foo1": "foo1",
  "foo2": [
    "a",
    "b",
    "c"
  ]
}
```

```java
@Getter
@Setter
class FooData {
    private String foo1;
    private List<String> foo2;
}
```
```java
Request request = Request.builder()
        .url("https://api.foo.com")
        .method(RequestMethod.POST)
        .header("Content-Type", "application/json") //Set headers
        .body("foo", "foo") //Set body (only POST and PUT)
        .build();
        
TestData testData = request.send(TestData.class);
```

## Utility classes
```java
boolean hasRole = MemberUtil.hasRole(MEMBER, "role1", "rol2"); //return true if member has one of required roles
```