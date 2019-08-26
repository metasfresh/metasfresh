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
 * Contributor(s): Teo Sarca
 *****************************************************************************/
package org.compiere.impexp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.lang.CoalesceUtil;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Import Format Row with pasing capability
 *
 * @author Jorg Janke
 * @version $Id: ImpFormatRow.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 * 
 *          globalqss: integrate Teo Sarca bug fix [ 1623817 ] Minor bug on importing calendar date
 */
public final class ImpFormatColumn
{
	private static final Logger logger = LogManager.getLogger(ImpFormatColumn.class);

	@Getter
	private final String name;
	@Getter
	private final String columnName;
	@Getter
	private final int startNo;
	@Getter
	private final int endNo;
	private final ImpFormatColumnDataType dataType;
	@Getter
	private final String dataFormat;
	@Getter
	private final DecimalSeparator decimalSeparator;
	@Getter
	private final boolean divideBy100;
	private final String constantValue;
	private final boolean constantIsString;
	private final int maxLength;

	private DateFormat _dateFormat = null; // lazy

	@Builder
	private ImpFormatColumn(
			final String name,
			@NonNull final String columnName,
			final int startNo,
			final int endNo,
			@NonNull final ImpFormatColumnDataType dataType,
			final int maxLength,
			//
			@Nullable final String dataFormat,
			@Nullable final DecimalSeparator decimalSeparator,
			final boolean divideBy100,

			final String constantValue)
	{
		Check.assumeNotEmpty(columnName, "columnName is not empty");

		this.name = !Check.isEmpty(name, true) ? name : columnName;
		this.columnName = columnName;
		this.startNo = startNo;
		this.endNo = endNo;
		this.dataType = dataType;
		this.maxLength = maxLength;

		this.dataFormat = dataFormat;
		this.decimalSeparator = CoalesceUtil.coalesce(decimalSeparator, DecimalSeparator.DOT);
		this.divideBy100 = divideBy100;
		this.constantValue = CoalesceUtil.coalesce(constantValue, "");
		this.constantIsString = checkIfConstantIsString(this.constantValue);
	}

	private static boolean checkIfConstantIsString(final String constantValue)
	{
		if (constantValue == null || constantValue.isEmpty())
		{
			return true;
		}

		for (int i = 0; i < constantValue.length(); i++)
		{
			final char ch = constantValue.charAt(i);
			if (!(Character.isDigit(ch) || ch == '.'))	// if a constant number, it must be with . (not ,)
			{
				return true;
			}
		}

		return false;
	}

	public boolean isString()
	{
		if (ImpFormatColumnDataType.Constant.equals(dataType))
		{
			return constantIsString;
		}
		else
		{
			return ImpFormatColumnDataType.String.equals(dataType);
		}
	}

	public boolean isNumber()
	{
		return ImpFormatColumnDataType.Number.equals(dataType);
	}

	public boolean isDate()
	{
		return ImpFormatColumnDataType.Date.equals(dataType);
	}

	public boolean isConstant()
	{
		return ImpFormatColumnDataType.Constant.equals(dataType);
	}

	private DateFormat getDateFormat()
	{
		DateFormat dateFormat = _dateFormat;
		if (dateFormat == null)
		{

			final String dateFormatPattern = getDataFormat();
			if (!Check.isEmpty(dateFormatPattern, true))
			{
				try
				{
					dateFormat = new SimpleDateFormat(dateFormatPattern);
				}
				catch (Exception ex)
				{
					dateFormat = null;
					logger.warn("Invalid date format '{}'. Considering defaults.", dateFormatPattern, ex);
				}
			}

			if (dateFormat == null)
			{
				dateFormat = DateFormat.getDateInstance();
			}

			dateFormat.setLenient(true);

			this._dateFormat = dateFormat;
		}

		return dateFormat;
	}

	/**
	 * Parse value.
	 * 
	 * Field content in [] are treated as comments.
	 * 
	 * @param info data item
	 * @return parsed info, never returns <code>null</code>
	 * @throws Exception in case there was an error while parsing
	 */
	String parse(final String info) throws Exception
	{
		if (info == null || info.length() == 0
				|| (isDate() && "00000000".equals(info))) // consider this an empty value for date
		{
			return "";
		}

		// Comment ?
		if (info.startsWith("[") && info.endsWith("]"))
		{
			return "";
		}

		//
		final String retValue;
		if (isNumber())
		{
			retValue = parseNumber(info);
		}
		else if (isDate())
		{
			retValue = parseDate(info);
		}
		else if (isConstant())
		{
			retValue = constantIsString ? parseString(constantValue) : constantValue;
		}
		else
		{
			retValue = parseString(info);
		}

		//
		// Return the value (make sure it's not null)
		return retValue == null ? "" : retValue.trim();
	}	// parse

	/**
	 * Return date as YYYY-MM-DD HH24:MI:SS (JDBC Timestamp format w/o miliseconds)
	 * 
	 * @param info data
	 * @return date as JDBC format String
	 */
	private String parseDate(final String info)
	{
		try
		{
			Timestamp ts = null;

			if (!Check.isEmpty(info, true))
			{
				final DateFormat dateFormat = getDateFormat();
				final Date date = dateFormat.parse(info.trim());
				ts = TimeUtil.asTimestamp(date);
			}

			if (ts == null)
			{
				ts = SystemTime.asTimestamp();
			}

			final String dateString = ts.toString();
			return dateString.substring(0, dateString.indexOf('.'));	// cut off miliseconds
		}
		catch (ParseException e)
		{
			throw new AdempiereException("@Invalid@ @Date@: " + info);
		}
	}

	/**
	 * Return String. - clean ' and backslash - check max length
	 * 
	 * @param info data
	 * @return info with in SQL format
	 */
	private String parseString(final String info)
	{
		String retValue = info;

		// Length restriction
		if (maxLength > 0 && retValue.length() > maxLength)
		{
			retValue = retValue.substring(0, maxLength);
		}

		// copy characters (we need to look through anyway)
		final StringBuilder out = new StringBuilder(retValue.length());
		for (int i = 0; i < retValue.length(); i++)
		{
			char c = retValue.charAt(i);
			if (c == '\'')
			{
				out.append("''");
			}
			else if (c == '\\')
			{
				out.append("\\\\");
			}
			else
			{
				out.append(c);
			}
		}

		return out.toString();
	}

	/**
	 * Return number with "." decimal
	 * 
	 * @param info data
	 * @return converted number
	 */
	private String parseNumber(final String info)
	{
		try
		{
			final String numberStringNormalized = normalizeNumberString(info);
			BigDecimal bd = new BigDecimal(numberStringNormalized);

			if (divideBy100)
			{
				// NOTE: assumed two decimal scale
				bd = bd.divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			}

			return bd.toString();
		}
		catch (NumberFormatException e)
		{
			throw new AdempiereException("@Invalid@ @Number@: " + info);
		}
	}

	private String normalizeNumberString(String info)
	{
		if (Check.isEmpty(info, true))
		{
			return "0";
		}

		final boolean hasPoint = info.indexOf('.') != -1;
		boolean hasComma = info.indexOf(',') != -1;

		// delete thousands
		if (hasComma && decimalSeparator.isDot())
		{
			info = info.replace(',', ' ');
		}
		if (hasPoint && decimalSeparator.isComma())
		{
			info = info.replace('.', ' ');
		}
		hasComma = info.indexOf(',') != -1;

		// replace decimal
		if (hasComma && decimalSeparator.isComma())
		{
			info = info.replace(',', '.');
		}

		// remove everything but digits & '.' & '-'
		char[] charArray = info.toCharArray();
		final StringBuilder sb = new StringBuilder();
		for (char element : charArray)
		{
			if (Character.isDigit(element) || element == '.' || element == '-')
			{
				sb.append(element);
			}
		}

		final String numberStringNormalized = sb.toString().trim();

		if (numberStringNormalized.isEmpty())
		{
			return "0";
		}

		return numberStringNormalized;
	}

}	// ImpFormatFow
