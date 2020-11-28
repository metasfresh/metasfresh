package de.metas.impexp.parser;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import lombok.EqualsAndHashCode;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Error message.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@EqualsAndHashCode
public final class ErrorMessage
{
	public static ErrorMessage of(final String message)
	{
		final Throwable exception = null;
		return new ErrorMessage(message, exception);
	}

	public static ErrorMessage of(final Throwable exception)
	{
		String message = AdempiereException.extractMessage(exception);
		return new ErrorMessage(message, exception);
	}

	private final String message;
	private final transient Throwable exception;

	private ErrorMessage(
			final String message,
			@Nullable final Throwable exception)
	{
		this.message = message;
		this.exception = exception;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return message == null ? "" : message;
	}

	public String getMessage()
	{
		return message;
	}

	public AdempiereException toException()
	{
		return exception != null
				? AdempiereException.wrapIfNeeded(exception)
				: new AdempiereException(message);
	}
}
