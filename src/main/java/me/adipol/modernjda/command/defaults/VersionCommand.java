package me.adipol.modernjda.command.defaults;

import me.adipol.modernjda.command.AbstractCommand;
import me.adipol.modernjda.command.CommandInfo;
import me.adipol.modernjda.util.VersionInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Date;

@CommandInfo(name="version")
public class VersionCommand extends AbstractCommand {

    @Override
    public void handleCommand(Member member, MessageChannel channel, MessageReceivedEvent event, String[] args) throws Exception {
        if(!member.isOwner()) {
            return;
        }

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("ModernJDA")
                .setFooter("ModernJDA")
                .setTimestamp(new Date().toInstant());

        if(VersionInfo.checkUpdate()) {
            builder
                    .setDescription("Your version: **" + VersionInfo.VERSION + "**\n\nA new version of ModernJDA is available!\nLink: https://github.com/AdiPol1359/ModernJDA")
                    .setColor(0xff1100);
        } else {
            builder
                    .setDescription("Your version: **" + VersionInfo.VERSION + "**\nYou have the latest ModernJDA version!")
                    .setColor(0x33ff00);
        }

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
