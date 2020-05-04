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

import de.metas.lock.api.IUnlockCommand;

/**
 * Exception thrown when a lock releasing failed.
 * 
 * @author tsa
 *
 */
public class UnlockFailedException extends LockException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1714489085379778609L;
	
	public static final UnlockFailedException wrapIfNeeded(final Exception e)
	{
		if (e instanceof UnlockFailedException)
		{
			return (UnlockFailedException)e;
		}
		else
		{
			return new UnlockFailedException("Unlock failed: " + e.getLocalizedMessage(), e);
		}
	}

	public UnlockFailedException(String message)
	{
		super(message);
	}

	public UnlockFailedException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UnlockFailedException setUnlockCommand(final IUnlockCommand unlockCommand)
	{
		setParameter("Unlock command", unlockCommand);
		return this;
	}

	@Override
	public UnlockFailedException setSql(final String sql, final Object[] sqlParams)
	{
		super.setSql(sql, sqlParams);
		return this;
	}

	public UnlockFailedException setRecordToLock(final ITableRecordReference recordToLock)
	{
		super.setRecord(recordToLock);
		return this;
	}

	@Override
	public UnlockFailedException setParameter(String name, Object value)
	{
		super.setParameter(name, value);
		return this;
	}
}
