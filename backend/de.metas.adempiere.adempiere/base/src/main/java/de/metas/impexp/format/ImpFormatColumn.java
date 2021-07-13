package de.metas.impexp.format;

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
import de.metas.util.StringUtils;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Import Format Column Definition
 */
@ToString(of = { "columnName", "dataType", "startNo" })
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
	@Getter
	private final String constantValue;
	private final boolean constantIsString;
	private final int maxLength;

	private DateFormat _dateFormat = null; // lazy

	@Builder
	private ImpFormatColumn(
			final String name,
			@NonNull final String columnName,
			final int startNo,
			final Integer endNo,
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
		this.endNo = endNo != null ? endNo : startNo;
		this.dataType = dataType;
		this.maxLength = maxLength > 0 ? maxLength : 0;

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

	public boolean isYesNo()
	{
		return ImpFormatColumnDataType.YesNo.equals(dataType);
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
	 * @param valueStr data item
	 * @return parsed info, never returns <code>null</code>
	 * @throws Exception in case there was an error while parsing
	 */
	public Object parseCellValue(final String valueStr) throws Exception
	{
		if (valueStr == null)
		{
			return null;
		}

		// // Comment ?
		// else if (valueStr.startsWith("[") && valueStr.endsWith("]"))
		// {
		// return null;
		// }

		//
		else if (isNumber())
		{
			return parseNumber(valueStr);
		}
		else if (isYesNo())
		{
			return parseYesNo(valueStr);
		}
		else if (isDate())
		{
			// FIXME: HARDCODED - consider this an empty value for date
			if ("00000000".equals(valueStr))
			{
				return null;
			}

			return parseDate(valueStr);
		}
		else if (isConstant())
		{
			return constantIsString ? parseString(constantValue) : constantValue;
		}
		else
		{
			return parseString(valueStr);
		}
	}

	/**
	 * Return date as YYYY-MM-DD HH24:MI:SS (JDBC Timestamp format w/o miliseconds)
	 *
	 * @param valueStr data
	 * @return date as JDBC format String
	 */
	private Timestamp parseDate(final String valueStr)
	{
		if (Check.isEmpty(valueStr, true))
		{
			return null;
		}

		try
		{
			final DateFormat dateFormat = getDateFormat();
			final Date date = dateFormat.parse(valueStr.trim());
			return TimeUtil.asTimestamp(date);
		}
		catch (final ParseException ex)
		{
			throw new AdempiereException("@Invalid@ @Date@: " + valueStr, ex);
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
		if (info == null || info.isEmpty())
		{
			return null;
		}
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

	private BigDecimal parseNumber(@Nullable final String valueStr)
	{
		if (valueStr == null || valueStr.isEmpty())
		{
			return null;
		}

		try
		{
			final String numberStringNormalized = normalizeNumberString(valueStr);
			BigDecimal bd = new BigDecimal(numberStringNormalized);

			if (divideBy100)
			{
				// NOTE: assumed two decimal scale
				bd = bd.divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			}

			return bd;
		}
		catch (final NumberFormatException ex)
		{
			throw new AdempiereException("@Invalid@ @Number@: " + valueStr, ex);
		}
	}

	private boolean parseYesNo(String valueStr)
	{
		return StringUtils.toBoolean(valueStr, false);
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
