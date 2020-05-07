package org.adempiere.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * An {@link ILoggable} which collects the given log messages into one string.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class PlainStringLoggable implements ILoggable
{

	private final List<String> messages = new ArrayList<>();

	@Override
	public void addLog(String msg, Object... msgParameters)
	{
		final String formattedMessage = StringUtils.formatMessage(msg, msgParameters);
		messages.add(formattedMessage);
	}

	public boolean isEmpty()
	{
		return messages.isEmpty();
	}

	public List<String> getSingleMessages()
	{
		return ImmutableList.copyOf(messages);
	}

	public String getConcatenatedMessages()
	{
		return Joiner.on("\n").join(messages);
	}

}
