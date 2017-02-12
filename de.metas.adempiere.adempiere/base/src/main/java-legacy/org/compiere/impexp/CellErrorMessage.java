package org.compiere.impexp;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link ImpDataCell}'s error message.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class CellErrorMessage
{
	public static final CellErrorMessage of(final String message)
	{
		return new CellErrorMessage(message);
	}

	public static final CellErrorMessage of(final Throwable exception)
	{
		String message = exception.getLocalizedMessage();
		if (message == null || message.length() < 4)
		{
			message = exception.toString();
		}
		return of(message);
	}

	private final String message;

	private CellErrorMessage(final String message)
	{
		super();
		this.message = message;
	}

	@Override
	public String toString()
	{
		return message == null ? "" : message;
	}

	public String getMessage()
	{
		return message;
	}

}
