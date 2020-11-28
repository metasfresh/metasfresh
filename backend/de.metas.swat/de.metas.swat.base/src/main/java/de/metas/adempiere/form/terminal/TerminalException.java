/**
 *
 */
package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.exceptions.AdempiereException;

/**
 * @author tsa
 *
 */
public class TerminalException extends AdempiereException
{
	private static final long serialVersionUID = 4302763688803800294L;

	public static final TerminalException wrapIfNeeded(final Throwable throwable)
	{
		if (throwable instanceof TerminalException)
		{
			return (TerminalException)throwable;
		}

		final String message = extractMessage(throwable);
		final Throwable cause = extractCause(throwable);
		return new TerminalException(message, cause);
	}

	public TerminalException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public TerminalException(final String message)
	{
		super(message);
	}

	public TerminalException(final Throwable cause)
	{
		super(cause);
	}
}
