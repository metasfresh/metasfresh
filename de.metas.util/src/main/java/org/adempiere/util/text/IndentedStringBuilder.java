package org.adempiere.util.text;

/*
 * #%L
 * org.adempiere.util
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


import org.adempiere.util.Check;

/**
 * Simple {@link StringBuilder} like class used to write string lines and also keep track of indentation.
 * 
 * @author tsa
 *
 */
public class IndentedStringBuilder
{
	private final StringBuilder sb;

	// Config Parameters
	private String indentToken = "\t";
	private String newlineToken = "\n";

	// State
	private String _linePrefix = null;
	private int indent = 0;

	public IndentedStringBuilder(final StringBuilder sb)
	{
		super();
		Check.assumeNotNull(sb, "sb not null");
		this.sb = sb;
	}

	public IndentedStringBuilder()
	{
		this(new StringBuilder());
	}

	public String getIndentToken()
	{
		return indentToken;
	}

	public void setIndentToken(String indent)
	{
		Check.assumeNotNull(indent, "indent not null");
		this.indentToken = indent;
		this._linePrefix = null; // reset
	}

	public String getNewlineToken()
	{
		return newlineToken;
	}

	public void setNewlineToken(String newline)
	{
		Check.assumeNotNull(newline, "newline not null");
		this.newlineToken = newline;
	}

	public int getIndent()
	{
		return indent;
	}

	public IndentedStringBuilder incrementIndent()
	{
		return setIndent(indent + 1);
	}

	public IndentedStringBuilder decrementIndent()
	{
		return setIndent(indent - 1);
	}

	private IndentedStringBuilder setIndent(int indent)
	{
		Check.assume(indent >= 0, "indent >= 0");
		this.indent = indent;
		this._linePrefix = null; // reset
		return this;
	}

	private final String getLinePrefix()
	{
		if (_linePrefix == null)
		{
			final int indent = getIndent();
			final String token = getIndentToken();
			final StringBuilder prefix = new StringBuilder();
			for (int i = 1; i <= indent; i++)
			{
				prefix.append(token);
			}
			_linePrefix = prefix.toString();
		}
		return _linePrefix;
	}

	public StringBuilder getInnerStringBuilder()
	{
		return sb;
	}

	@Override
	public String toString()
	{
		return sb.toString();
	}

	public IndentedStringBuilder appendLine(final Object obj)
	{
		final String linePrefix = getLinePrefix();
		sb.append(linePrefix).append(obj).append(getNewlineToken());

		return this;
	}

	public IndentedStringBuilder append(final Object obj)
	{
		sb.append(obj);
		return this;
	}
}
