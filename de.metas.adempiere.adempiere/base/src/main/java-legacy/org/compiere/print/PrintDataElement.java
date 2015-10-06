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
 *****************************************************************************/
package org.compiere.print;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.MLocation;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Language;
import org.compiere.util.NamePair;

/**
 *	Print Data Element
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: PrintDataElement.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public class PrintDataElement
{
	/**
	 *	Print Data Element Constructor
	 *  @param columnName name
	 *  @param value display value
	 *  @param displayType optional displayType
	 *  @param isPKey is primary key
	 *  @param isPageBreak if true force page break
	 */
	public PrintDataElement (String columnName, Object value, int displayType, boolean isPKey, boolean isPageBreak, String format)
	{
		if (columnName == null)
			throw new IllegalArgumentException("PrintDataElement - Name cannot be null");
		m_columnName = columnName;
		m_value = value;
		m_displayType = displayType;
		m_isPKey = isPKey;
		m_isPageBreak = isPageBreak;
		m_formatPattern = format;
	}	//	PrintDataElement

	/**
	 *	Print Data Element Constructor
	 *  @param columnName name
	 *  @param value display value
	 *  @param pattern Number/date format pattern
	 *  @param displayType optional displayType
	 */
	public PrintDataElement(String columnName, Object value, int displayType, String pattern)
	{
		this (columnName, value, displayType, false, false, pattern);
	}	//	PrintDataElement

	/**	Data Name			*/
	private String 		m_columnName;
	/** Data Value			*/
	private Object 		m_value;
	/** Display Type		*/
	private int 		m_displayType;
	/** Is Primary Key		*/
	private boolean		m_isPKey;
	/**	Is Page Break		*/
	private boolean		m_isPageBreak;
	/** Value format pattern */
	private String		m_formatPattern;


	/**	XML Element Name			*/
	public static final String	XML_TAG = "element";
	/**	XML Attribute Name			*/
	public static final String	XML_ATTRIBUTE_NAME = "name";
	/**	XML Attribute Key			*/
	public static final String	XML_ATTRIBUTE_KEY = "key";


	/**
	 * 	Get Name
	 * 	@return name
	 */
	public String getColumnName()
	{
		return m_columnName;
	}	//	getName

	/**
	 * 	Get Node Value
	 * 	@return value
	 */
	public Object getValue()
	{
		return m_value;
	}	//	getValue

	/**
	 * 	Get Function Value
	 * 	@return length or numeric value
	 */
	public BigDecimal getFunctionValue()
	{
		if (m_value == null)
			return Env.ZERO;

		//	Numbers - return number value
		if (m_value instanceof BigDecimal)
			return (BigDecimal)m_value;
		if (m_value instanceof Number)
			return new BigDecimal(((Number)m_value).doubleValue());

		//	Boolean - return 1 for true 0 for false
		if (m_value instanceof Boolean)
		{
			if (((Boolean)m_value).booleanValue())
				return Env.ONE;
			else
				return Env.ZERO;
		}

		//	Return Length
		String s = m_value.toString();
		return new BigDecimal(s.length());
	}	//	getFunctionValue


	/**
	 * 	Get Node Value Display
	 *  @param language optional language - if null numbers/dates are not formatted
	 * 	@return display value optionally formatted
	 */
	public String getValueDisplay (Language language)
	{
		if (m_value == null)
			return "";
		String retValue = m_value.toString();
		if (m_displayType == DisplayType.Location)
			return getValueDisplay_Location();
		// ID columns should be printed as ID numbers - teo_sarca [ 1673363 ]
		else if (DisplayType.ID == m_displayType && m_value instanceof KeyNamePair)
			return ((KeyNamePair)m_value).getID();
		else if (m_columnName.equals("C_BPartner_Location_ID") || m_columnName.equals("Bill_Location_ID"))
			return getValueDisplay_BPLocation();
		else if (m_displayType == 0 || m_value instanceof String || m_value instanceof NamePair)
			;
		else if (language != null)	//	Optional formatting of Numbers and Dates
		{
			if (DisplayType.isNumeric(m_displayType)) {
				retValue = DisplayType.getNumberFormat(m_displayType, language, m_formatPattern).format(m_value);
			}
			else if (DisplayType.isDate(m_displayType))
				retValue = DisplayType.getDateFormat(m_displayType, language, m_formatPattern).format(m_value);
		}
		return retValue;
	}	//	getValueDisplay

	/**
	 * 	Get Node Data Value as String
	 * 	@return data value
	 */
	public String getValueAsString()
	{
		if (m_value == null)
			return "";
		String retValue = m_value.toString();
		if (m_value instanceof NamePair)
			retValue = ((NamePair)m_value).getID();
		return retValue;
	}	//	getValueDisplay

	/**
	 *	Return Address String not just name
	 * 	@return Address String
	 */
	private String getValueDisplay_BPLocation ()
	{
		try
		{
			int C_BPartner_Location_ID = Integer.parseInt (getValueKey ());
			if (C_BPartner_Location_ID != 0)
			{
				MLocation loc = MLocation.getBPLocation(Env.getCtx(), C_BPartner_Location_ID, null);
				if (loc != null)
					return loc.toStringCR();
			}
		}
		catch (Exception ex)
		{
		}
		return m_value.toString();
	}	//	getValueDisplay_BPLocation


	/**
	 *	Return Address String not just City
	 * 	@return Address String
	 */
	private String getValueDisplay_Location ()
	{
		try
		{
			int C_Location_ID = Integer.parseInt (getValueKey ());
			if (C_Location_ID != 0)
			{
				MLocation loc = new MLocation (Env.getCtx(), C_Location_ID, null);
				if (loc != null)
					return loc.toStringCR();
			}
		}
		catch (Exception ex)
		{
		}
		return m_value.toString();
	}	//	getValueDisplay_Location


	/**
	 * 	Get Node Value Key
	 * 	@return key
	 */
	public String getValueKey()
	{
		if (m_value == null)
			return "";
		if (m_value instanceof NamePair)
			return ((NamePair)m_value).getID();
		return "";
	}	//	getValueKey

	/**
	 * 	Is Value Null
	 * 	@return true if value is null
	 */
	public boolean isNull()
	{
		return m_value == null;
	}	//	isNull

	/*************************************************************************/

	/**
	 * 	Get Display Type
	 *  @return Display Type
	 */
	public int getDisplayType()
	{
		return m_displayType;
	}	//	getDisplayType

	/**
	 * 	Is Value numeric
	 * 	@return true if value is a numeric
	 */
	public boolean isNumeric()
	{
		if (m_displayType == 0)
			return m_value instanceof BigDecimal;
		return DisplayType.isNumeric(m_displayType);
	}	//	isNumeric

	/**
	 * 	Is Value a date
	 * 	@return true if value is a date
	 */
	public boolean isDate()
	{
		if (m_displayType == 0)
			return m_value instanceof Timestamp;
		return DisplayType.isDate(m_displayType);
	}	//	isDate

	/**
	 * 	Is Value an ID
	 * 	@return true if value is an ID
	 */
	public boolean isID()
	{
		// ID columns are considered numbers - teo_sarca [ 1673363 ]
		if (DisplayType.ID == m_displayType)
			return false;
		return DisplayType.isID(m_displayType);
	}	//	isID

	/**
	 * 	Is Value boolean
	 * 	@return true if value is a boolean
	 */
	public boolean isYesNo()
	{
		if (m_displayType == 0)
			return m_value instanceof Boolean;
		return DisplayType.YesNo == m_displayType;
	}	//	isYesNo

	/**
	 * 	Is Value the primary key of row
	 * 	@return true if value is the PK
	 */
	public boolean isPKey()
	{
		return m_isPKey;
	}	//	isPKey

	/**
	 * 	Column value forces page break
	 * 	@return true if page break
	 */
	public boolean isPageBreak()
	{
		return m_isPageBreak;
	}	//	isPageBreak

	/*************************************************************************/

	/**
	 * 	HashCode
	 * 	@return hash code
	 */
	public int hashCode()
	{
		if (m_value == null)
			return m_columnName.hashCode();
		return m_columnName.hashCode() + m_value.hashCode();
	}	//	hashCode

	/**
	 * 	Equals
	 * 	@param compare compare object
	 * 	@return true if equals
	 */
	public boolean equals (Object compare)
	{
		if (compare instanceof PrintDataElement)
		{
			PrintDataElement pde = (PrintDataElement)compare;
			if (pde.getColumnName().equals(m_columnName))
			{
				if (pde.getValue() != null && pde.getValue().equals(m_value))
					return true;
				if (pde.getValue() == null && m_value == null)
					return true;
			}
		}
		return false;
	}	//	equals

	/**
	 * 	String representation
	 * 	@return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer(m_columnName).append("=").append(m_value);
		if (m_isPKey)
			sb.append("(PK)");
		return sb.toString();
	}	//	toString

	/**
	 * 	Value Has Key
	 * 	@return true if value has a key
	 */
	public boolean hasKey()
	{
		return m_value instanceof NamePair;
	}	//	hasKey

	/**
	 * 	String representation with key info
	 * 	@return info
	 */
	public String toStringX()
	{
		if (m_value instanceof NamePair)
		{
			NamePair pp = (NamePair)m_value;
			StringBuffer sb = new StringBuffer(m_columnName);
			sb.append("(").append(pp.getID()).append(")")
				.append("=").append(pp.getName());
			if (m_isPKey)
				sb.append("(PK)");
			return sb.toString();
		}
		else
			return toString();
	}	//	toStringX

	public String getM_formatPattern() {
		return m_formatPattern;
	}

	public void setM_formatPattern(String pattern) {
		m_formatPattern = pattern;
	}

}	//	PrintDataElement
