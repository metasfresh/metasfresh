package de.metas.lock.exceptions;

/*
 * #%L
 * de.metas.async
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


import org.adempiere.util.lang.ITableRecordReference;

import de.metas.lock.api.ILockCommand;

/**
 * Exception thrown when a lock aquiring failed.
 * 
 * @author tsa
 *
 */
public class LockFailedException extends LockException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -343950791566871398L;

	public static final LockFailedException wrapIfNeeded(final Exception e)
	{
		if (e instanceof LockFailedException)
		{
			return (LockFailedException)e;
		}
		else
		{
			return new LockFailedException("Lock failed: " + e.getLocalizedMessage(), e);
		}
	}

	public LockFailedException(final String message)
	{
		super(message);
	}

	public LockFailedException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public LockFailedException setLockCommand(final ILockCommand lockCommand)
	{
		setParameter("Lock command", lockCommand);
		return this;
	}

	@Override
	public LockFailedException setSql(final String sql, final Object[] sqlParams)
	{
		super.setSql(sql, sqlParams);
		return this;
	}

	public LockFailedException setRecordToLock(final ITableRecordReference recordToLock)
	{
		super.setRecord(recordToLock);
		return this;
	}
}
