package org.adempiere.util.test;

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

import de.metas.util.Check;

import java.util.Collection;

/**
 * Immutable error message
 *
 * @author tsa
 *
 */
public final class ErrorMessage
{
	public static final ErrorMessage NULL = null;

	public static ErrorMessage newInstance()
	{
		return new ErrorMessage();
	}

	public static ErrorMessage derive(final ErrorMessage errorMessage)
	{
		if (errorMessage == null)
		{
			return new ErrorMessage();
		}
		return errorMessage;
	}
	
	public static ErrorMessage newErrorMessage(final String errorMessage)
	{
		return new ErrorMessage(errorMessage);
	}

	public static String toString(final ErrorMessage message)
	{
		return message == null ? "" : message.toString();
	}

	// NOTE to developer: please keep all parameters final and create a new instance if u want to change something
	private final String message;
	private final ErrorMessage parent;

	private ErrorMessage()
	{
		this(null, null);
	}

	private ErrorMessage(final String message)
	{
		this(message, null); // parent=null
	}

	private ErrorMessage(final String message, final ErrorMessage parent)
	{
		super();

		this.message = message == null ? "" : message;
		this.parent = parent;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();

		for (ErrorMessage err = this; err != null; err = err.parent)
		{
			if (Check.isEmpty(err.message, true))
			{
				continue;
			}

			if (sb.length() > 0)
			{
				sb.append("\n");
			}

			sb.append(err.message.trim());
		}

		return sb.toString();
	}

	public ErrorMessage addContextInfo(final String name, final Object value)
	{
		String nameToUse = name == null ? "" : name;
		Object valueToUse = value;
		if (value instanceof Collection<?>)
		{
			final Collection<?> collection = (Collection<?>)value;
			final int count = collection.size();
			nameToUse += " ("+count+" items)";
			
			final StringBuilder valueStr = new StringBuilder();
			for (final Object item : collection)
			{
				valueStr.append("\n");
				valueStr.append(item);
			}
			valueToUse = valueStr;
			
		}
		return addLine(nameToUse + ": " + valueToUse);
	}

	public ErrorMessage addContextInfo(final Object value)
	{
		if (value == null)
		{
			return this;
		}
		final String name = value.getClass().getSimpleName();
		return addContextInfo(name, value);
	}

	public ErrorMessage addLine(final String line)
	{
		return new ErrorMessage(line, this);
	}

	public ErrorMessage expect(final String condition)
	{
		final String conditionLine;
		if (Check.isEmpty(condition, true))
		{
			conditionLine = "Failed expectation";
		}
		else
		{
			conditionLine = "Failed expectation: " + condition.trim();
		}

		return new ErrorMessage(conditionLine, this);
	}
}
