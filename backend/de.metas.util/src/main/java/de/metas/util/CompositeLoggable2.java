/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.util;

import lombok.NonNull;
import org.adempiere.util.lang.ITableRecordReference;

import javax.annotation.Nullable;

class CompositeLoggable2 implements ILoggable
{
	@NonNull
	public static ILoggable compose(@Nullable final ILoggable loggable1, @Nullable final ILoggable loggable2)
	{
		if (NullLoggable.isNull(loggable1))
		{
			return NullLoggable.boxIfNull(loggable2);
		}
		else if (NullLoggable.isNull(loggable2))
		{
			return loggable1;
		}
		else
		{
			return new CompositeLoggable2(loggable1, loggable2);
		}
	}

	@NonNull private final ILoggable loggable1;
	@NonNull private final ILoggable loggable2;

	private CompositeLoggable2(@NonNull final ILoggable loggable1, @NonNull final ILoggable loggable2)
	{
		this.loggable1 = loggable1;
		this.loggable2 = loggable2;
	}

	@Override
	public ILoggable addLog(final String msg, final Object... msgParameters)
	{
		loggable1.addLog(msg, msgParameters);
		loggable2.addLog(msg, msgParameters);
		return this;
	}

	@Override
	public void flush()
	{
		loggable1.flush();
		loggable2.flush();
	}

	@Override
	public ILoggable addTableRecordReferenceLog(final ITableRecordReference recordRef, final String type, final String trxName)
	{
		loggable1.addTableRecordReferenceLog(recordRef, type, trxName);
		loggable2.addTableRecordReferenceLog(recordRef, type, trxName);
		return this;
	}
}
