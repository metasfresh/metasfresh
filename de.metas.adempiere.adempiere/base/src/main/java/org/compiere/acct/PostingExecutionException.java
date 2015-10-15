package org.compiere.acct;

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


import java.lang.reflect.InvocationTargetException;

import org.adempiere.exceptions.AdempiereException;

/**
 * Exception thrown when
 * <ul>
 * <li>an accountable document could not be loaded
 * <li>a document posting request fails
 * </ul>
 *
 * @author tsa
 *
 */
public class PostingExecutionException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8775226397946060131L;

	/**
	 * Wraps given <code>throwable</code> as {@link PostingExecutionException}, if it's not already an {@link PostingExecutionException}.
	 *
	 * @param throwable
	 * @return {@link PostingExecutionException} or <code>null</code> if the throwable was null.
	 */
	public static final PostingExecutionException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable == null)
		{
			return null;
		}
		else if (throwable instanceof PostingExecutionException)
		{
			return (PostingExecutionException)throwable;
		}
		else if (throwable instanceof InvocationTargetException)
		{
			final Throwable target = ((InvocationTargetException)throwable).getTargetException();
			if (target != null)
			{
				return wrapIfNeeded(target);
			}
			return new PostingExecutionException(throwable.getLocalizedMessage(), throwable);
		}
		else
		{
			return new PostingExecutionException(throwable.getLocalizedMessage(), throwable);
		}
	}

	public PostingExecutionException(final String message)
	{
		super(message);
	}

	public PostingExecutionException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
