package de.metas.migration.exception;

/*
 * #%L
 * de.metas.migration.base
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ScriptException extends RuntimeException
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5438314907624287813L;

	private final String _message;
	private final Map<String, Object> params = new LinkedHashMap<String, Object>();

	public ScriptException(final String message, final Throwable cause)
	{
		super(null, cause);
		_message = message;
	}

	public ScriptException(final String message)
	{
		this(message, (Throwable)null);
	}

	/**
	 * @return error message including parameters
	 */
	@Override
	public String getMessage()
	{
		final boolean printStackTrace = false; // of course we are no printing the stack trace in our exception message
		return toStringX(printStackTrace);
	}

	private final String getInnerMessage()
	{
		return _message;
	}

	public ScriptException addParameter(final String name, final Object value)
	{
		params.put(name, value);
		return this;
	}

	public Map<String, Object> getParameters()
	{
		return params;
	}

	public void print(final PrintStream out)
	{
		final boolean printStackTrace = true; // backward compatibility
		print(out, printStackTrace);
	}

	public void print(final PrintStream out, final boolean printStackTrace)
	{
		out.println("Error: " + getInnerMessage());

		for (final Map.Entry<String, Object> param : getParameters().entrySet())
		{
			final Object value = param.getValue();
			if (value == null)
			{
				continue;
			}

			final String name = param.getKey();

			if (value instanceof List<?>)
			{
				final List<?> list = (List<?>)value;
				if (list.isEmpty())
				{
					continue;
				}

				out.println(name + ":");
				for (final Object item : list)
				{
					out.println("\t" + item);
				}
			}
			else
			{
				out.println(name + ": " + value);
			}
		}

		if (printStackTrace)
		{
			out.println("Stack trace: ");
			this.printStackTrace(out);
		}
	}

	public String toStringX()
	{
		final boolean printStackTrace = true; // backward compatibility
		return toStringX(printStackTrace);
	}

	public String toStringX(final boolean printStackTrace)
	{
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream out = new PrintStream(baos);
		print(out, printStackTrace);
		return baos.toString();
	}

}
