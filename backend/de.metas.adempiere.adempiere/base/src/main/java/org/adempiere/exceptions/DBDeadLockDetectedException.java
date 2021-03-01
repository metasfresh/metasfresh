/**
 * 
 */
package org.adempiere.exceptions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.sql.Connection;

import org.compiere.util.DB;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;

import javax.annotation.Nullable;

/**
 * Dedicated exception to handle the case that the DB detected a deadlock and killed one of the participants.
 * 
 * @author ts
 * @task 08353
 */
public class DBDeadLockDetectedException extends DBException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1436774241410586947L;

	private String ownBackEndId;

	/**
	 * 
	 * @param e the "orginal" exception from which we know that it is a deadlock. Note that this is usually a SQLException.
	 * @param connection the connection that was used. may be <code>null</code>.
	 */
	public DBDeadLockDetectedException(final Throwable e, @Nullable final Connection connection)
	{
		super(e);
		setDeadLockInfo(connection);
	}

	private void setDeadLockInfo(@Nullable final Connection connection)
	{
		if (DB.isPostgreSQL() && connection != null)
		{
			final boolean throwDBException = false; // in case of e.g. a closed connection or other problems, don't make a fuss and throw further exceptions
			ownBackEndId = DB.getDatabase().getConnectionBackendId(connection, throwDBException);
		}
		else
		{
			// FIXME implement for Oracle
		}

		resetMessageBuilt();
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		// NOTE: completely override super's message because it's not relevant for our case

		final TranslatableStringBuilder message = TranslatableStrings.builder();
		message.append("Own Backend/Process ID =");
		message.append(ownBackEndId);
		message.append("; ExceptionMessage: ");
		message.append(getOriginalMessage());

		return message.build();
	}
}
