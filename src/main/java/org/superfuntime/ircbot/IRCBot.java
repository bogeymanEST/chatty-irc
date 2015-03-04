/*
 * An easily extendable chat bot for any chat service.
 * Copyright (C) 2015 bogeymanEST
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package org.superfuntime.ircbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.events.ActionEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.superfuntime.chatty.Bot;
import org.superfuntime.chatty.chat.Chat;
import org.superfuntime.chatty.chat.ChatMessage;

import java.util.Date;

/**
 * User: Bogeyman
 * Date: 1.10.13
 * Time: 18:03
 */
public class IRCBot extends Bot implements Listener {
    private static Logger logger = LogManager.getLogger();
    private PircBotX bot;

    @Override
    public void start() {
        bot = new PircBotX();
        try {
            bot.getListenerManager().addListener(this);
            bot.setName(getSettingsNode().getString("name"));
            String server = getSettingsNode().getString("server");
            logger.info("Connecting to {}", server);
            bot.connect(server);
            for (String channel : getSettingsNode().getStringList("channels", null)) {
                logger.debug("Joining {}", channel);
                bot.joinChannel(channel);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        logger.info("Stopped Skype bot");
    }

    @Override
    public void setName(String name) {
        bot.changeNick(name);
    }

    private ChatMessage getChatMessage(GenericMessageEvent msg) {
        ChatMessage.Type type = ChatMessage.Type.OTHER;
        if (msg instanceof MessageEvent || msg instanceof PrivateMessageEvent)
            type = ChatMessage.Type.SAID;
        else if (msg instanceof ActionEvent)
            type = ChatMessage.Type.EMOTED;
        Chat chat = null;
        if (msg instanceof PrivateMessageEvent) {
            chat = new IRCPrivateChat(msg.getUser());
        } else if (msg instanceof MessageEvent) {
            chat = new IRCChannelChat(((MessageEvent) msg).getChannel());
        } else if (msg instanceof ActionEvent) {
            chat = new IRCChannelChat(((ActionEvent) msg).getChannel());
        }
        Date date = new Date(msg.getTimestamp());
        return new ChatMessage(msg.getMessage(), new IRCUser(msg.getUser()), chat, date, type);
    }

    @Override
    public void onEvent(Event event) throws Exception {
        if (event instanceof GenericMessageEvent && !(event instanceof NoticeEvent)) {
            onChatMessageReceived(getChatMessage((GenericMessageEvent) event));
        }
    }
}
