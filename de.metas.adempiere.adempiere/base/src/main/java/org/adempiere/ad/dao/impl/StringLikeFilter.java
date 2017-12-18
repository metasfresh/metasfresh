package org.adempiere.ad.dao.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.dao.IQueryFilterModifier;

import lombok.NonNull;

/**
 * Filters for substrings. This filter creates SQL like <code>column LIKE '%value%'</code>.
 *
 * @param <T> the type of the class we filter for.
 */
public class StringLikeFilter<T> extends CompareQueryFilter<T>
{
	/**
	 * Modified the given parameters for for the substring-SQL
	 *
	 */
	private static class StringLikeQueryFilterModifier implements IQueryFilterModifier
	{
		private final boolean ignoreCase;

		private StringLikeQueryFilterModifier(final boolean ignoreCase)
		{
			this.ignoreCase = ignoreCase;
		}

		/**
		 * Makes sure that the given <code>value</code> starts with <code>" '%</code> and ends with <code>"%'"</code>.<br>
		 * Also escapes quotes within the string by replacing <code>'</code> with <code>''</code>.
		 */
		@Override
		public String getValueSql(final Object value, final List<Object> ignored_params)
		{
			final StringBuilder result = new StringBuilder();
			final String stringVal = (String)value;

			result.append(" '"); // we need one space after the "LIKE"
			if (!stringVal.startsWith("%"))
			{
				result.append("%");
			}

			result.append(stringVal.replace("'", "''")); // escape quotes within the string

			if (!stringVal.endsWith("%"))
			{
				result.append("%");
			}
			result.append("'");
			return result.toString();
		}

		/**
		 * Just prepends a space to the given <code>columnSql</code>.
		 */
		@Override
		public String getColumnSql(final String columnSql)
		{
			return columnSql + " "; // nothing more to do
		}

		/**
		 * Uppercases the given {@code value} if {@code ignoreCase} was specified.
		 */
		@Override
		public Object convertValue(final String columnName, final Object value, final Object model)
		{
			if (value == null)
			{
				return ""; // shall not happen, see constructor
			}

			final String str;
			if (ignoreCase)
			{
				// will return the uppercase version of both operands
				str = ((String)value).toUpperCase();
			}
			else
			{
				str = (String)value;
			}
			return str;
		}

		@Override
		public String toString()
		{
			return "Modifier[ignoreCase=" + ignoreCase + "]";
		}
	}

	/**
	 *
	 * @param columnName
	 * @param substring
	 * @param ignoreCase
	 */
	public StringLikeFilter(
			@NonNull final String columnName,
			@NonNull final String substring,
			final boolean ignoreCase)
	{
		super(columnName,
				ignoreCase ? Operator.STRING_LIKE_IGNORECASE : Operator.STRING_LIKE,
				substring,
				new StringLikeQueryFilterModifier(ignoreCase));
	}
}
