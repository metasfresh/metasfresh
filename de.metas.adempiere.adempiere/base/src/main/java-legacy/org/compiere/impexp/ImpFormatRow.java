/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 * Contributor(s): Teo Sarca
 *****************************************************************************/
package org.compiere.impexp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.time.SystemTime;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Callout;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/**
 * Import Format Row with pasing capability
 *
 * @author Jorg Janke
 * @version $Id: ImpFormatRow.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 * 
 *          globalqss: integrate Teo Sarca bug fix [ 1623817 ] Minor bug on importing calendar date
 */
public final class ImpFormatRow
{
	/**
	 * Constructor for fixed format
	 * 
	 * @param seqNo sequence
	 * @param columnName db dolumn name
	 * @param startNo start no
	 * @param endNo and no
	 * @param dataType data type - see constants DATATYPE_
	 * @param maxLength if String it is the maximum length (truncated)
	 */
	public ImpFormatRow(int seqNo, String columnName, int startNo, int endNo, String dataType, int maxLength)
	{
		super();
		m_seqNo = seqNo;
		setColumnName(columnName);
		m_startNo = startNo;
		m_endNo = endNo;
		setDataType(dataType);
		setMaxLength(maxLength);
	}	// ImpFormatRow

	/**
	 * Constructor for non-fixed format
	 * 
	 * @param seqNo sequence
	 * @param columnName db column name
	 * @param dataType data type - see constants DATATYPE_
	 * @param maxLength if String it is the maximum length (truncated)
	 */
	public ImpFormatRow(int seqNo, String columnName, String dataType, int maxLength)
	{
		super();
		m_seqNo = seqNo;
		setColumnName(columnName);
		setDataType(dataType);
		setMaxLength(maxLength);
	}	// ImpFormatRow

	public ImpFormatRow(final I_AD_ImpFormat_Row model)
	{
		super();
		final I_AD_Column adColumn = model.getAD_Column();

		m_seqNo = model.getSeqNo();
		m_name = model.getName();
		setColumnName(adColumn.getColumnName());
		m_startNo = model.getStartNo();
		m_endNo = model.getEndNo();
		setDataType(model.getDataType());
		setMaxLength(adColumn.getFieldLength());

		setFormatInfo(
				model.getDataFormat(), // dataFormat,
				model.getDecimalPoint(), // decimalPoint,
				model.isDivideBy100(), // divideBy100,
				model.getConstantValue(), // constantValue,
				model.getCallout());
	}

	private int m_seqNo;
	private String m_name;
	private String m_columnName;
	private int m_startNo = 0;
	private int m_endNo = 0;
	private String m_dataType;
	private String _dataFormat = "";
	private String m_decimalPoint = ".";
	private boolean m_divideBy100 = false;
	private String m_constantValue = "";
	private boolean m_constantIsString = true;
	//
	private Callout m_callout = null;
	private String m_method = null;
	//
	private DateFormat _dateFormat = null;
	private int m_maxLength = 0;

	/** Logger */
	private Logger log = LogManager.getLogger(getClass());

	/**
	 * Sequence No
	 * 
	 * @return seq no
	 */
	public int getSeqNo()
	{
		return m_seqNo;
	}   // getSeqNo

	/**
	 * Set Sequence No
	 * 
	 * @param newSeqNo sequence
	 */
	public void setSeqNo(int newSeqNo)
	{
		m_seqNo = newSeqNo;
	}   // setSeqNo

	/**
	 * Start Position
	 * 
	 * @param newStartNo start position
	 */
	public void setStartNo(int newStartNo)
	{
		m_startNo = newStartNo;
	}   // setStartNo

	/**
	 * Get Start Position
	 * 
	 * @return start position
	 */
	public int getStartNo()
	{
		return m_startNo;
	}   // getStartNo

	/**
	 * End Position
	 * 
	 * @param newEndNo end position
	 */
	public void setEndNo(int newEndNo)
	{
		m_endNo = newEndNo;
	}   // setEndNo

	/**
	 * Get End Position
	 * 
	 * @return End Position
	 */
	public int getEndNo()
	{
		return m_endNo;
	}   // getEndNo

	/**
	 * Column
	 * 
	 * @param columnName column name
	 */
	public void setColumnName(String columnName)
	{
		if (columnName == null || columnName.length() == 0)
			throw new IllegalArgumentException("ColumnName must be at least 1 char");
		else
			m_columnName = columnName;
	}   // setColumnName

	public String getName()
	{
		if (Check.isEmpty(m_name, true))
		{
			return getColumnName();
		}
		return m_name;
	}

	/**
	 * Get Column Name
	 * 
	 * @return Column Name
	 */
	public String getColumnName()
	{
		return m_columnName;
	}   // getColumnName

	/**
	 * Data Type
	 * 
	 * @param dataType data type - see constants DATATYPE_
	 */
	public void setDataType(String dataType)
	{
		if (dataType.equals(DATATYPE_String) || dataType.equals(DATATYPE_Date)
				|| dataType.equals(DATATYPE_Number) || dataType.equals(DATATYPE_Constant))
			m_dataType = dataType;
		else
			throw new IllegalArgumentException("DataType must be S/D/N/C");
	}   // setDataType

	/** String Data type */
	public static final String DATATYPE_String = "S";
	/** Data Data type */
	public static final String DATATYPE_Date = "D";
	/** Numeric Data type */
	public static final String DATATYPE_Number = "N";
	/** Constant Data type */
	public static final String DATATYPE_Constant = "C";

	/**
	 * Data Type
	 * 
	 * @return data type
	 */
	public String getDataType()
	{
		return m_dataType;
	}   // getDataType

	/**
	 * Is String
	 * 
	 * @return true if data type is String
	 */
	public boolean isString()
	{
		if (m_dataType.equals(DATATYPE_Constant))
			return m_constantIsString;
		return m_dataType.equals(DATATYPE_String);
	}	// isString

	/**
	 * Is Number
	 * 
	 * @return true if data type is Number
	 */
	public boolean isNumber()
	{
		return m_dataType.equals(DATATYPE_Number);
	}

	/**
	 * Is Date
	 * 
	 * @return true if data type is Date
	 */
	public boolean isDate()
	{
		return m_dataType.equals(DATATYPE_Date);
	}

	/**
	 * Is Constant
	 * 
	 * @return true if data type is Constant
	 */
	public boolean isConstant()
	{
		return m_dataType.equals(DATATYPE_Constant);
	}

	/**
	 * Set Format Info
	 * 
	 * @param dataFormat data format - see constants DATATYPE_
	 * @param decimalPoint decimal point representation
	 * @param divideBy100 divide number by 100
	 * @param constantValue constant value
	 * @param callout Java callout
	 */
	public void setFormatInfo(final String dataFormat, final String decimalPoint, final boolean divideBy100, final String constantValue, final String callout)
	{
		setDataFormat(dataFormat);

		// number
		if (decimalPoint == null || !decimalPoint.equals(","))
			m_decimalPoint = ".";
		else
			m_decimalPoint = ",";

		m_divideBy100 = divideBy100;

		// constant
		if (constantValue == null || constantValue.length() == 0 || !m_dataType.equals(DATATYPE_Constant))
		{
			m_constantValue = "";
			m_constantIsString = true;
		}
		else
		{
			m_constantValue = constantValue;
			m_constantIsString = false;
			for (int i = 0; i < m_constantValue.length(); i++)
			{
				char c = m_constantValue.charAt(i);
				if (!(Character.isDigit(c) || c == '.'))	// if a constant number, it must be with . (not ,)
				{
					m_constantIsString = true;
					break;
				}
			}
		}
		// callout
		if (callout != null)
		{
			int methodStart = callout.lastIndexOf('.');
			try
			{
				if (methodStart != -1)      // no class
				{
					Class<?> cClass = Class.forName(callout.substring(0, methodStart));
					m_callout = (Callout)cClass.newInstance();
					m_method = callout.substring(methodStart + 1);
				}
			}
			catch (Exception e)
			{
				log.error("MTab.setFormatInfo - " + e.toString());
			}
			if (m_callout == null || m_method == null || m_method.length() == 0)
			{
				log.error("MTab.setFormatInfo - Invalid Callout " + callout);
				m_callout = null;
			}
		}
	}   // setFormatInfo

	/**
	 * Get Format
	 * 
	 * @return Data Format
	 */
	public String getDataFormat()
	{
		return _dataFormat;
	}

	private void setDataFormat(final String dataFormat)
	{
		if (dataFormat == null)
			_dataFormat = "";
		else
			_dataFormat = dataFormat;
		_dateFormat = null; // reset date format
	}

	private final DateFormat getDateFormat()
	{
		if (_dateFormat == null)
		{
			final String dateFormatPattern = getDataFormat();
			DateFormat dateFormat = null;
			try
			{
				dateFormat = new SimpleDateFormat(dateFormatPattern);
			}
			catch (Exception e)
			{
				dateFormat = null;
				log.error("ImpFormatRow.parseDate Format=" + dateFormatPattern, e);
			}
			if (dateFormat == null)
				dateFormat = DateFormat.getDateInstance();
			dateFormat.setLenient(true);

			this._dateFormat = dateFormat;
		}
		return _dateFormat;
	}

	/**
	 * Get Decimal Point
	 * 
	 * @return Decimal Point
	 */
	public String getDecimalPoint()
	{
		return m_decimalPoint;
	}

	/**
	 * Divide result by 100
	 * 
	 * @return true if result will be divided by 100
	 */
	public boolean isDivideBy100()
	{
		return m_divideBy100;
	}

	/**
	 * Get the constant value
	 * 
	 * @return constant value
	 */
	public String getConstantValue()
	{
		return m_constantValue;
	}

	/**
	 * Set maximum length for Strings (truncated). Ignored, if 0
	 * 
	 * @param maxLength max length
	 */
	public void setMaxLength(int maxLength)
	{
		m_maxLength = maxLength;
	}	// setMaxLength

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
		if (info == null || info.length() == 0)
		{
			return "";
		}

		// Comment ?
		if (info.startsWith("[") && info.endsWith("]"))
			return "";
		//
		String retValue;
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
			retValue = m_constantIsString ? parseString(m_constantValue) : m_constantValue;
		}
		else
		{
			retValue = parseString(info);
		}

		//
		// Apply the callout's convert method
		if (m_callout != null)
		{
			try
			{
				retValue = m_callout.convert(m_method, retValue);
			}
			catch (Exception e)
			{
				log.error("ImpFormatRow.parse - " + info + " (" + retValue + ")", e);
			}
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
		if (m_maxLength > 0 && retValue.length() > m_maxLength)
			retValue = retValue.substring(0, m_maxLength);

		// copy characters (wee need to look through anyway)
		final StringBuilder out = new StringBuilder(retValue.length());
		for (int i = 0; i < retValue.length(); i++)
		{
			char c = retValue.charAt(i);
			if (c == '\'')
				out.append("''");
			else if (c == '\\')
				out.append("\\\\");
			else
				out.append(c);
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

			if (m_divideBy100)
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

	private final String normalizeNumberString(String info)
	{
		if (Check.isEmpty(info, true))
		{
			return "0";
		}

		boolean hasPoint = info.indexOf('.') != -1;
		boolean hasComma = info.indexOf(',') != -1;
		// delete thousands
		if (hasComma && m_decimalPoint.equals("."))
			info = info.replace(',', ' ');
		if (hasPoint && m_decimalPoint.equals(","))
			info = info.replace('.', ' ');
		hasComma = info.indexOf(',') != -1;

		// replace decimal
		if (hasComma && m_decimalPoint.equals(","))
			info = info.replace(',', '.');

		// remove everything but digits & '.' & '-'
		char[] charArray = info.toCharArray();
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < charArray.length; i++)
		{
			if (Character.isDigit(charArray[i]) || charArray[i] == '.' || charArray[i] == '-')
				sb.append(charArray[i]);
		}

		final String numberStringNormalized = sb.toString().trim();

		if (numberStringNormalized.isEmpty())
		{
			return "0";
		}

		return numberStringNormalized;
	}

}	// ImpFormatFow
