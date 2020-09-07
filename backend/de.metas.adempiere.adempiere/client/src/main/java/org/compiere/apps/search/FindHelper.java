package org.compiere.apps.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.adempiere.db.DBConstants;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.util.DB;

import de.metas.adempiere.util.Permutation;
import de.metas.util.Check;

import javax.annotation.Nullable;

public final class FindHelper
{
	public static final String COLUMNNAME_Search = "Search";

	public static void addStringRestriction(final MQuery query, final String columnName, final String value, final String infoName, final boolean isIdentifier)
	{
		if (COLUMNNAME_Search.equalsIgnoreCase(columnName))
		{
			addStringRestriction_Search(query, columnName, value, infoName, isIdentifier);
			return;
		}

		//
		// Generic implementation
		final String valueStr = prepareSearchString(value, false);
		if (valueStr == null)
		{
			query.addRestriction("UPPER(" + columnName + ")", Operator.EQUAL, null, infoName, value);
		}
		else if (valueStr.indexOf('%') == -1)
		{
			final String valueSQL = "UPPER(" + DB.TO_STRING(valueStr) + ")";
			query.addRestriction("UPPER(" + columnName + ")", Operator.EQUAL, valueSQL, infoName, value);
		}
		else
		{
			query.addRestriction(columnName, Operator.LIKE_I, valueStr, infoName, value);
		}
	}

	// metas-2009_0021_AP1_CR064
	private static void addStringRestriction_Search(final MQuery query, final String columnName, String value, final String infoName, final boolean isIdentifier)
	{
		value = value.trim();
		// metas: Permutationen ueber alle Search-Kombinationen
		// z.B. 3 Tokens ergeben 3! Moeglichkeiten als Ergebnis.
		if (value.contains(" "))
		{
			final StringTokenizer st = new StringTokenizer(value, " ");
			final int tokens = st.countTokens();
			final String[] input = new String[tokens];
			final Permutation perm = new Permutation();
			perm.setMaxIndex(tokens - 1);
			for (int token = 0; token < tokens; token++)
			{
				input[token] = st.nextToken();
			}
			perm.permute(input, tokens - 1);
			final Iterator<String> itr = perm.getResult().iterator();

			while (itr.hasNext())
			{
				value = ("%" + itr.next() + "%").replace(" ", "%");
				query.addRestriction(COLUMNNAME_Search, Operator.LIKE_I, value, infoName, value, true);
			}
		}
		else
		{
			if (!value.startsWith("%"))
				value = "%" + value;
			// metas-2009_0021_AP1_CR064: end
			if (!value.endsWith("%"))
				value += "%";
			query.addRestriction(COLUMNNAME_Search, Operator.LIKE_I, value, infoName, value);
		}
	}

	/**
	 * When dealing with String comparison ("LIKE") use this method instead of simply building the String.
	 * <p>
	 * This method provides reliability when it comes to comparison behavior
	 * <p>
	 * E.G. when searching for "123" it shall match "%123%" and when searching for "123%" it shall match exactly that and NOT "%123%".
	 * <p>
	 * In other words, the user receives exactly what he asks for
	 */
	public static String buildStringRestriction(final String columnSQL, final Object value, final boolean isIdentifier, final List<Object> params)
	{
		return buildStringRestriction(null, columnSQL, value, isIdentifier, params);
	}

	/**
	 * apply function to parameter cg: task: 02381
	 */
	public static String buildStringRestriction(@Nullable final String criteriaFunction, final String columnSQL, final Object value, final boolean isIdentifier, final List<Object> params)
	{
		final String valueStr = prepareSearchString(value, isIdentifier);
		String whereClause;
		if (Check.isBlank(criteriaFunction))
		{
			whereClause = "UPPER(" + DBConstants.FUNC_unaccent_string(columnSQL) + ")"
					+ " LIKE"
					+ " UPPER(" + DBConstants.FUNC_unaccent_string("?") + ")";
		}
		else
		{
			//noinspection ConstantConditions
			whereClause = "UPPER(" + DBConstants.FUNC_unaccent_string(columnSQL) + ")"
					+ " LIKE "
					+ "UPPER(" + DBConstants.FUNC_unaccent_string(criteriaFunction.replaceFirst(columnSQL, "?")) + ")";
		}
		params.add(valueStr);
		return whereClause;
	}

	public static String buildStringRestriction(final String columnSQL, final int displayType, final Object value, final Object valueTo, final boolean isRange, final List<Object> params)
	{
		final StringBuffer where = new StringBuffer();
		if (isRange)
		{
			if (value != null || valueTo != null)
				where.append(" (");
			if (value != null)
			{
				where.append(columnSQL).append(">?");
				params.add(value);
			}
			if (valueTo != null)
			{
				if (value != null)
					where.append(" AND ");
				where.append(columnSQL).append("<?");
				params.add(valueTo);
			}
			if (value != null || valueTo != null)
				where.append(") ");
		}
		else
		{
			where.append(columnSQL).append("=?");
			params.add(value);
		}

		return where.toString();
	}

	public static String prepareSearchString(final Object value)
	{
		return prepareSearchString(value, false);
	}

	public static String prepareSearchString(final Object value, final boolean isIdentifier)
	{
		if (value == null || value.toString().length() == 0)
		{
			return null;
		}
		String valueStr;
		if (isIdentifier)
		{
			// metas-2009_0021_AP1_CR064: begin
			valueStr = ((String)value).trim();
			if (!valueStr.startsWith("%"))
				valueStr = "%" + value;
			// metas-2009_0021_AP1_CR064: end
			if (!valueStr.endsWith("%"))
				valueStr += "%";
		}
		else
		{
			valueStr = (String)value;
			if (valueStr.startsWith("%"))
			{
				; // nothing
			}
			else if (valueStr.endsWith("%"))
			{
				; // nothing
			}
			else if (valueStr.indexOf("%") < 0)
			{
				valueStr = "%" + valueStr + "%";
			}
			else
			{
				; // nothing
			}
		}

		return valueStr;
	}
}
