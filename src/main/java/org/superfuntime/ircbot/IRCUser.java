/*
 * An easily extendable chat bot for any chat service.
 * Copyright (C) 2013 bogeymanEST
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

import org.pircbotx.User;

/**
 * User: Bogeyman
 * Date: 2.10.13
 * Time: 19:10
 */
public class IRCUser extends org.superfuntime.chatty.chat.User {
    private User user;

    public IRCUser(User user) {
        this.user = user;
    }

    @Override
    public String getDisplayName() {
        return user.getNick();
    }

    @Override
    public String getUniqueIdentifier() {
        return "irc|" + user.getLogin() + "@" + user.getHostmask();
    }
}
