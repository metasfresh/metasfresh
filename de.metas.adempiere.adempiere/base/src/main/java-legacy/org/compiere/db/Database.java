/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.db;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.compiere.util.DisplayType;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

/**
 * General Database Constants and Utilities
 *
 * Based on work by Jorg Janke
 */
public class Database
{
	/** PostgreSQL ID */
	public static final String DB_POSTGRESQL = "PostgreSQL";

	/** Connection Timeout in seconds */
	public static int CONNECTION_TIMEOUT = 10;

	public static AdempiereDatabase newAdempiereDatabase(final String type)
	{
		assertThatTypeIsPostgres(type);
		return new DB_PostgreSQL();
	}

	public static void assertThatTypeIsPostgres(final String type)
	{
		Preconditions.checkArgument(
				Database.DB_POSTGRESQL.equals(type),
				"Given type=%s is not supported; the only currently supported type is %s",
				type, Database.DB_POSTGRESQL);
	}

	/**
	 * Create SQL TO Date String from Timestamp
	 *
	 * @param time Date to be converted; if {@code null}, then the current time is returned.
	 * @param dayOnly true if time set to 00:00:00
	 *
	 * @return TO_DATE('2001-01-30 18:10:20',''YYYY-MM-DD HH24:MI:SS')
	 *         or TO_DATE('2001-01-30',''YYYY-MM-DD')
	 */
	public static String TO_DATE(@Nullable final Timestamp time, final boolean dayOnly)
	{
		if (time == null)
		{
			if (dayOnly)
			{
				return "current_date()";
			}
			return "current_date()";
		}

		final StringBuilder dateString = new StringBuilder("TO_TIMESTAMP('");
		// YYYY-MM-DD HH24:MI:SS.mmmm JDBC Timestamp format
		final String myDate = time.toString();
		if (dayOnly)
		{
			dateString.append(myDate.substring(0, 10));
			dateString.append("','YYYY-MM-DD')");
		}
		else
		{
			dateString.append(myDate.substring(0, myDate.indexOf('.')));	// cut off miliseconds
			dateString.append("','YYYY-MM-DD HH24:MI:SS')");
		}
		return dateString.toString();
	}   // TO_DATE

	/**
	 * Create SQL for formatted Date, Number
	 *
	 * @param columnName the column name in the SQL
	 * @param displayType Display Type
	 *
	 * 
	 * @see #TO_CHAR(String, int, String)
	 */
	public static String TO_CHAR(final String columnName, final int displayType)
	{
		Check.assumeNotEmpty(columnName, "columnName is not empty");

		final StringBuilder retValue = new StringBuilder("CAST (");
		retValue.append(columnName);
		retValue.append(" AS Text)");
		return retValue.toString();
	}

	/**
	 * Create SQL for formatted Date, Number.
	 *
	 * @param columnName the column name in the SQL
	 * @param displayType Display Type
	 * @param AD_Language 6 character language setting (from Env.LANG_*)
	 * @param formatPattern formatting pattern to be used ( {@link DecimalFormat} pattern, {@link SimpleDateFormat} pattern etc). In case the formatting pattern is not supported or is not valid, the
	 *            implementation method can ignore it silently.
	 *
	 * @return SQL code
	 */
	public static String TO_CHAR(
			final String columnName,
			final int displayType,
			final String formatPattern)
	{
		if (Check.isEmpty(formatPattern, false))
		{
			return TO_CHAR(columnName, displayType);
		}
		else if (DisplayType.isNumeric(displayType))
		{
			final String pgFormatPattern = convertDecimalPatternToPG(formatPattern);
			if (pgFormatPattern == null)
			{
				return TO_CHAR(columnName, displayType);
			}

			return TO_CHAR("to_char(" + columnName + ", '" + pgFormatPattern + "')", displayType);
		}
		else
		{
			return TO_CHAR(columnName, displayType);
		}
	}

	/**
	 * Convert {@link DecimalFormat} pattern to PostgreSQL's number formatting pattern
	 *
	 * @param formatPattern
	 * @return PostgreSQL's number formatting pattern or <code>null</code> if it could not be converted
	 * @see DecimalFormat
	 * @see http://www.postgresql.org/docs/9.1/static/functions-formatting.html#FUNCTIONS-FORMATTING-NUMERIC-TABLE
	 */
	@VisibleForTesting
	/* package */static final String convertDecimalPatternToPG(final String formatPattern)
	{
		if (formatPattern == null || formatPattern.isEmpty())
		{
			return null;
		}

		final StringBuilder pgFormatPattern = new StringBuilder(formatPattern.length() + 2);
		pgFormatPattern.append("FM"); // fill mode (suppress padding blanks and trailing zeroes)
		for (int i = 0; i < formatPattern.length(); i++)
		{
			final char ch = formatPattern.charAt(i);

			// Case: chars that don't need to be translated because have the same meaning
			if (ch == '0' || ch == '.' || ch == ',')
			{
				pgFormatPattern.append(ch);
			}
			// Case: # - Digit, zero shows as absent
			// Convert to: 9 - value with the specified number of digits
			else if (ch == '#')
			{
				pgFormatPattern.append('9');
			}
			// Case: invalid char / char that we cannot convert (atm)
			else
			{
				return null;
			}
		}

		return pgFormatPattern.toString();
	}

	/**
	 * Return number as string for INSERT statements with correct precision
	 *
	 * @param number number
	 * @param displayType display Type
	 * @return number as string
	 */
	public static String TO_NUMBER(final BigDecimal number, final int displayType)
	{
		if (number == null)
		{
			return "NULL";
		}
		BigDecimal result = number;
		final int scale = DisplayType.getDefaultPrecision(displayType);
		if (scale > number.scale())
		{
			result = number.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		return result.toString();
	}
}
