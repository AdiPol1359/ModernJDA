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
    <version>1.0.0</version>
</dependency>
```
## Gradle
```groovy
maven {
    url "https://repo.projectcode.pl/releases"
}
```
```groovy
implementation "me.adipol.modernjda:ModernJDA:1.0.0"
```

## Example code
```java
public static void main(String[] args) {
    ModernJDA modernJDA = new ModernJDA("TOKEN");
    
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
public static void main(String[] args) {
    ModernJDA modernJDA = new ModernJDA("TOKEN");
    
    modernJDA.getCommandManager().getDefaultCommandMap().registerCommand(new FooCommand());
    modernJDA.run();
}
```

### CommandInfo
```java
@CommandInfo(name = "foo", aliases = {"foo1", "foo2"}) //set aliases for command
@CommandInfo(name = "foo", permissions = {"role1", "role2"}) //set one of required roles to use command
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
    public void commandExecuteEvent(CommandExceptionEvent event) {
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
}
```
```java
public static void main(String[] args) {
    ModernJDA modernJDA = new ModernJDA("TOKEN");
    
    modernJDA.getEventManager().registerListener(new EventListener());
    
    modernJDA.run();
}
```

## Create custom event
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