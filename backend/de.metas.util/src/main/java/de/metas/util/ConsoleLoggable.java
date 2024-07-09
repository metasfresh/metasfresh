package de.metas.util;

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

import lombok.NonNull;

import javax.annotation.Nullable;
import java.io.PrintStream;
import java.util.Objects;

final class ConsoleLoggable implements ILoggable
{
	private static final ConsoleLoggable defaultInstance = new ConsoleLoggable(null);

	@Nullable private final String prefix;
	@NonNull private final PrintStream out;

	private ConsoleLoggable(@Nullable final String prefix)
	{
		this.prefix = prefix;
		this.out = System.out;
	}

	public static ConsoleLoggable withPrefix(@Nullable final String prefix)
	{
		if (Objects.equals(defaultInstance.prefix, prefix))
		{
			return defaultInstance;
		}
		return new ConsoleLoggable(prefix);
	}

	@Override
	public ILoggable addLog(final String msg, final Object... msgParameter)
	{
		if (prefix != null)
		{
			out.print(prefix);
		}
		out.println(StringUtils.formatMessage(msg, msgParameter));
		return this;
	}

	@Override
	public void flush()
	{
		out.flush();
	}
}
