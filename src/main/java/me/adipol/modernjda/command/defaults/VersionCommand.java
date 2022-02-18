package me.adipol.modernjda.command.defaults;

import me.adipol.modernjda.command.AbstractCommand;
import me.adipol.modernjda.command.CommandInfo;
import me.adipol.modernjda.util.VersionInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Date;

@CommandInfo(name="version")
public class VersionCommand extends AbstractCommand {

    @Override
    public void handleCommand(Member member, MessageChannel channel, MessageReceivedEvent event, String[] args) {
        if(!member.isOwner()) {
            return;
        }

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("ModernJDA")
                .setDescription("Your version: **" + VersionInfo.VERSION + "**\nYou have the latest ModernJDA version!")
                .setFooter("ModernJDA")
                .setTimestamp(new Date().toInstant())
                .setColor(0x33ff00)
                .build();

        event.getChannel().sendMessageEmbeds(embed).queue();
    }
}
