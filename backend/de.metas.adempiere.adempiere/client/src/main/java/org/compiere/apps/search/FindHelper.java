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
import org.adempiere.util.Check;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;
import org.compiere.util.DB;

import de.metas.adempiere.util.Permutation;

/**
 * 
 * @author tsa
 * 
 */
public final class FindHelper
{
	public static final String COLUMNNAME_Search = "Search";

	public static void addStringRestriction(MQuery query, String columnName, String value, String infoName, boolean isIdentifier)
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
			String valueSQL = "UPPER(" + DB.TO_STRING(valueStr) + ")";
			query.addRestriction("UPPER(" + columnName + ")", Operator.EQUAL, valueSQL, infoName, value);
		}
		else
		{
			query.addRestriction(columnName, Operator.LIKE_I, valueStr, infoName, value);
		}
	}

	// metas-2009_0021_AP1_CR064
	private static void addStringRestriction_Search(MQuery query, String columnName, String value, String infoName, boolean isIdentifier)
	{
		value = value.trim();
		// metas: Permutationen ueber alle Search-Kombinationen
		// z.B. 3 Tokens ergeben 3! Moeglichkeiten als Ergebnis.
		if (value.contains(" "))
		{
			StringTokenizer st = new StringTokenizer(value, " ");
			int tokens = st.countTokens();
			String input[] = new String[tokens];
			Permutation perm = new Permutation();
			perm.setMaxIndex(tokens - 1);
			for (int token = 0; token < tokens; token++)
			{
				input[token] = st.nextToken();
			}
			perm.permute(input, tokens - 1);
			Iterator<String> itr = perm.getResult().iterator();

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
	 * 
	 * This method provides reliability when it comes to comparison behavior
	 * 
	 * E.G. when searching for "123" it shall match "%123%" and when searching for "123%" it shall match exactly that and NOT "%123%".
	 * 
	 * In other words, the user receives exactly what he asks for
	 * 
	 * @param columnSQL
	 * @param value
	 * @param isIdentifier
	 * @param params
	 * @return
	 */
	public static String buildStringRestriction(String columnSQL, Object value, boolean isIdentifier, List<Object> params)
	{
		return buildStringRestriction(null, columnSQL, value, isIdentifier, params);
	}

	/**
	 * apply function to parameter cg: task: 02381
	 * 
	 * @param criteriaFunction
	 * @param columnSQL
	 * @param value
	 * @param isIdentifier
	 * @param params
	 * @return
	 */
	public static String buildStringRestriction(String criteriaFunction, String columnSQL, Object value, boolean isIdentifier, List<Object> params)
	{
		final String valueStr = prepareSearchString(value, isIdentifier);
		String whereClause = null;
		if (Check.isEmpty(criteriaFunction, true))
		{
			whereClause = "UPPER(" + DBConstants.FUNC_unaccent_string(columnSQL) + ")"
					+ " LIKE"
					+ " UPPER(" + DBConstants.FUNC_unaccent_string("?") + ")";
		}
		else
		{
			whereClause = "UPPER(" + DBConstants.FUNC_unaccent_string(columnSQL) + ")"
					+ " LIKE "
					+ "UPPER(" + DBConstants.FUNC_unaccent_string(criteriaFunction.replaceFirst(columnSQL, "?")) + ")";
		}
		params.add(valueStr);
		return whereClause;
	}

	public static String buildStringRestriction(String columnSQL, int displayType, Object value, Object valueTo, boolean isRange, List<Object> params)
	{
		StringBuffer where = new StringBuffer();
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

	public static String prepareSearchString(Object value)
	{
		return prepareSearchString(value, false);
	}

	public static String prepareSearchString(Object value, boolean isIdentifier)
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
