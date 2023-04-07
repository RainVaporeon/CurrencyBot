package com.spiritlight.currencybot.bot.commands;

import com.spiritlight.currencybot.Main;
import com.spiritlight.currencybot.bot.SlashCommandListener;
import com.spiritlight.currencybot.misc.Command;
import com.spiritlight.currencybot.util.PriceHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Date;

@SuppressWarnings("ConstantConditions")
public class QueryCommand implements SlashCommandListener {
    public static final String PROPERTY_FROM = "from";
    public static final String PROPERTY_TO = "to";
    public static final String PROPERTY_VALUE = "value";

    @Override
    public void onSlashCommand(SlashCommandInteractionEvent event) {
        if (!event.getName().equals(Command.QUERY)) return;
        if (!Main.prepared()) {
            event.reply("The info has not been collected yet!").setEphemeral(true).queue();
            return;
        }
        String from = event.getOption(PROPERTY_FROM).getAsString();
        String to = event.getOption(PROPERTY_TO).getAsString();
        OptionMapping val = event.getOption(PROPERTY_VALUE);
        int value = val == null ? 1 : val.getAsInt();
        double result = PriceHelper.convert(from, to, value);
        MessageEmbed embed = new EmbedBuilder()
                .addField("Result", String.format("%d %s is worth %f %s", value, from, result, to), false)
                .setFooter("Data retrieved at " + new Date(Main.getLatestDataTime()))
                .build();
        event.replyEmbeds(embed).setEphemeral(true).queue();
    }
}
