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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * An {@link ILoggable} which does nothing
 *
 * @author tsa
 */
final class NullLoggable implements ILoggable
{
	public static final NullLoggable instance = new NullLoggable();

	public static boolean isNull(@Nullable final ILoggable loggable)
	{
		return loggable == null || loggable == NullLoggable.instance;
	}

	@NonNull
	public static ILoggable boxIfNull(@Nullable final ILoggable loggable)
	{
		return loggable != null ? loggable : NullLoggable.instance;
	}

	private NullLoggable()
	{
	}

	@Override
	public ILoggable addLog(String msg_IGNORED, Object... msgParameters_IGNORED)
	{
		// nothing to do
		return this;
	}

}
