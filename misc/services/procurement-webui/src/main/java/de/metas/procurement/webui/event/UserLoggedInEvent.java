package de.metas.procurement.webui.event;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;

import de.metas.procurement.webui.model.User;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class UserLoggedInEvent
{
	public static final UserLoggedInEvent of (final User user)
	{
		final String afterLoginMessage = null;
		return new UserLoggedInEvent(user, afterLoginMessage);
	}
	
	public static final UserLoggedInEvent of(final User user, final String afterLoginMessage)
	{
		return new UserLoggedInEvent(user, afterLoginMessage);
	}
	
	private final User user;
	private final String afterLoginMessage;

	private UserLoggedInEvent(final User user, String afterLoginMessage)
	{
		super();
		this.user = Preconditions.checkNotNull(user);
		this.afterLoginMessage = afterLoginMessage;
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this)
				.add("user", user)
				.add("afterLoginMessage", afterLoginMessage)
				.toString();
	}

	public User getUser()
	{
		return user;
	}
	
	public String getAfterLoginMessage()
	{
		return afterLoginMessage;
	}
}
