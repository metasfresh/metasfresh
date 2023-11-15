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

import java.io.PrintStream;

final class ConsoleLoggable implements ILoggable
{
	public static final ConsoleLoggable instance = new ConsoleLoggable();

	private final PrintStream out;

	private ConsoleLoggable()
	{
		out = System.out;
	}

	@Override
	public ILoggable addLog(final String msg, final Object... msgParameter)
	{
		out.println(StringUtils.formatMessage(msg, msgParameter));
		return this;
	}

	@Override
	public void flush()
	{
		out.flush();
	}
}
