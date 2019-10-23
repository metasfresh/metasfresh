package org.adempiere.ad.migration.executor;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.exceptions.AdempiereException;

public class MigrationExecutorException extends AdempiereException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5403917143953357678L;

	private final boolean fatal;

	/**
	 * Wrap an {@link Throwable} exception with a {@link MigrationExecutorException}
	 * 
	 * @param e
	 */
	public MigrationExecutorException(Throwable e)
	{
		super(e);
		fatal = true;
	}

	/**
	 * Create a new {@link MigrationExecutorException}. The fatal flag should be set to true when the execution must be ended on error.
	 * 
	 * @param message
	 * @param fatal
	 */
	public MigrationExecutorException(String message, boolean fatal)
	{
		super(message);
		this.fatal = fatal;
	}

	/**
	 * Get the exception's fatal flag.
	 * 
	 * @return boolean
	 */
	public boolean isFatal()
	{
		return fatal;
	}

	/**
	 * Checks if a {@link MigrationExecutorException} is fatal for an instance of {@link Throwable}.
	 * 
	 * @param e
	 * @return boolean
	 */
	public static boolean isFatal(Throwable e)
	{
		if (e instanceof MigrationExecutorException)
		{
			final MigrationExecutorException mee = (MigrationExecutorException)e;
			return mee.isFatal();
		}

		return true;
	}
}
