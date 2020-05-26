package PunchBot;

import API.APIHandler;

import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


public class PunchMain {
    private static DiscordClient client;
    private static APIHandler api;

    interface Command {
        // Since we are expecting to do reactive things in this method, like
        // send a message, then this method will also return a reactive type.
        Mono<Void> execute(MessageCreateEvent event);
    }

    private static final Map<String, Command> commands = new HashMap<>();


    static {
        commands.put("wiki", event -> event.getMessage().getChannel()
                .flatMap(channel -> channel.createMessage(SearchWiki(event)))
                .then());
    }

    public static void main(final String args[]) {

        //Investigate which contructor to use
        //the Discord4J wiki says to use this, but it is deprecated
        client = new DiscordClientBuilder(args[0]).build();

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .flatMap(event -> Mono.justOrEmpty(event.getMessage().getContent())
                        .flatMap(content -> Flux.fromIterable(commands.entrySet())
                                // We will be using ! as our "prefix" to any command in the system.
                                .filter(entry -> content.startsWith('!' + entry.getKey()))
                                .flatMap(entry -> entry.getValue().execute(event))
                                .next()))
                .subscribe();

        initializer();

        client.login().block();
    }

    //Made this a  separate funciton for readability going forwards as this bot grows
    private static void initializer() {
        api = new APIHandler();
    }

    //function to interact with the wikipedia APi
    private static String SearchWiki(MessageCreateEvent event) {

        //get the full test of what is being requested
        String value = event.getMessage().getContent().map(content -> content.substring(5, content.length())).toString();

        //the above code returns Optional[-----]. We only want what is in the brackets
        value = value.substring(9, value.length()-1);


        String pageExtract = "";
        try {
             pageExtract = api.getWikiLink(value);
        }
        catch (Exception ex) {
            //this needs to be handled better
            System.out.println(ex.toString());
        }

        return pageExtract;
    }
}
