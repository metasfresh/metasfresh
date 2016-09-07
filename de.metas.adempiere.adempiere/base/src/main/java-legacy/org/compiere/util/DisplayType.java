/******************************************************************************
 * Product: Compiere ERP & CRM Smart Business Solution                        *
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
package org.compiere.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.adempiere.util.Check;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *	System Display Types.
 *  <pre>
 *	SELECT AD_Reference_ID, Name FROM AD_Reference WHERE ValidationType = 'D'
 *  </pre>
 *  @author     Jorg Janke
 *  @version    $Id: DisplayType.java,v 1.6 2006/08/30 20:30:44 comdivision Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				<li>BF [ 1810632 ] PricePrecision error in InfoProduct (and similar)
 */
public final class DisplayType
{
	/** Display Type 10	String	*/
	public static final int String     = 10;
	/** Display Type 11	Integer	*/
	public static final int Integer    = 11;
	/** Display Type 12	Amount	*/
	public static final int Amount     = 12;
	/** Display Type 13	ID	*/
	public static final int ID         = 13;
	/** Display Type 14	Text	*/
	public static final int Text       = 14;
	/** Display Type 15	Date	*/
	public static final int Date       = 15;
	/** Display Type 16	DateTime	*/
	public static final int DateTime   = 16;
	/** Display Type 17	List	*/
	public static final int List       = 17;
	/** Display Type 18	Table	*/
	public static final int Table      = 18;
	/** Display Type 19	TableDir	*/
	public static final int TableDir   = 19;
	/** Display Type 20	YN	*/
	public static final int YesNo      = 20;
	/** Display Type 21	Location	*/
	public static final int Location   = 21;
	/** Display Type 22	Number	*/
	public static final int Number     = 22;
	/** Display Type 23	BLOB	*/
	public static final int Binary     = 23;
	/** Display Type 24	Time	*/
	public static final int Time       = 24;
	/** Display Type 25	Account	*/
	public static final int Account    = 25;
	/** Display Type 26	RowID	*/
	public static final int RowID      = 26;
	/** Display Type 27	Color   */
	public static final int Color      = 27;
	/** Display Type 28	Button	*/
	public static final int Button	   = 28;
	/** Display Type 29	Quantity	*/
	public static final int Quantity   = 29;
	/** Display Type 30	Search	*/
	public static final int Search     = 30;
	/** Display Type 31	Locator	*/
	public static final int Locator    = 31;
	/** Display Type 32 Image	*/
	public static final int Image      = 32;
	/** Display Type 33 Assignment	*/
	public static final int Assignment = 33;
	/** Display Type 34	Memo	*/
	public static final int Memo       = 34;
	/** Display Type 35	PAttribute	*/
	public static final int PAttribute = 35;
	/** Display Type 36	CLOB	*/
	public static final int TextLong   = 36;
	/** Display Type 37	CostPrice	*/
	public static final int CostPrice  = 37;
	/** Display Type 38	File Path	*/
	public static final int FilePath  = 38;
	/** Display Type 39 File Name	*/
	public static final int FileName  = 39;
	/** Display Type 40	URL	*/
	public static final int URL  = 40;
	/** Display Type 42	PrinterName	*/
	public static final int PrinterName  = 42;
	//	Candidates: 
	
	/**
	 *	- New Display Type
		INSERT INTO AD_REFERENCE
		(AD_REFERENCE_ID, AD_CLIENT_ID,AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,UPDATED,UPDATEDBY,
		NAME,DESCRIPTION,HELP, VALIDATIONTYPE,VFORMAT,ENTITYTYPE)
		VALUES (35, 0,0,'Y',now(),0,now(),0,
		'PAttribute','Product Attribute',null,'D',null,'D');
	 *
	 *  - org.compiere.model.MModel (??)
	 *	- org.compiere.grid.ed.VEditor/Dialog
	 *	- org.compiere.grid.ed.VEditorFactory
	 *	- RColumn, WWindow
	 *  add/check 0_cleanupAD.sql
	 */

	//  See DBA_DisplayType.sql ----------------------------------------------

	/** Maximum number of digits    */
	private static final int    MAX_DIGITS = 28;        //  Oracle Standard Limitation 38 digits
	/** Digits of an Integer        */
	private static final int    INTEGER_DIGITS = 10;
	/** Maximum number of fractions */
	private static final int    MAX_FRACTION = 12;
	/** Default Amount Precision    */
	private static final int    AMOUNT_FRACTION = 2;

	/**	Logger	*/
	private static Logger s_log = LogManager.getLogger(DisplayType.class);
	
	/**
	 *	Returns true if (numeric) ID (Table, Search, Account, ..).
	 *  (stored as Integer)
	 *  @param displayType Display Type
	 *  @return true if ID
	 */
	public static boolean isID (int displayType)
	{
		if (displayType == ID || displayType == Table || displayType == TableDir
			|| displayType == Search || displayType == Location || displayType == Locator
			|| displayType == Account || displayType == Assignment || displayType == PAttribute
			|| displayType == Image || displayType == Color)
			return true;
		return false;
	}	//	isID

	/**
	 *	Returns true, if DisplayType is numeric (Amount, Number, Quantity, Integer).
	 *  (stored as BigDecimal)
	 *  @param displayType Display Type
	 *  @return true if numeric
	 */
	public static boolean isNumeric(int displayType)
	{
		if (displayType == Amount || displayType == Number || displayType == CostPrice 
			|| displayType == Integer || displayType == Quantity)
			return true;
		return false;
	}	//	isNumeric
	
	/**
	 * 	Get Default Precision.
	 * 	Used for databases who cannot handle dynamic number precision.
	 *	@param displayType display type
	 *	@return scale (decimal precision)
	 */
	public static int getDefaultPrecision(int displayType)
	{
		if (displayType == Amount)
			return 2;
		if (displayType == Number)
			return 6;
		if (displayType == CostPrice 
			|| displayType == Quantity)
			return 4;
		return 0;
	}	//	getDefaultPrecision
	

	/**
	 *	Returns true, if DisplayType is text (String, Text, TextLong, Memo).
	 *  @param displayType Display Type
	 *  @return true if text
	 */
	public static boolean isText(int displayType)
	{
		if (displayType == String || displayType == Text 
			|| displayType == TextLong || displayType == Memo
			|| displayType == FilePath || displayType == FileName
			|| displayType == URL || displayType == PrinterName)
			return true;
		return false;
	}	//	isText

	/**
	 * Returns true if DisplayType is a Date or Time.
	 * 
	 * @param displayType Display Type
	 * @return true if date, time or date+time
	 */
	public static boolean isDate (int displayType)
	{
		if (displayType == Date || displayType == DateTime || displayType == Time)
			return true;
		return false;
	}	//	isDate

	/**
	 *	Returns true if DisplayType is a generic lookup (List, Table, TableDir, Search).
	 *  (stored as Integer)
	 *  @param displayType Display Type
	 *  @return true if Lookup
	 */
	public static boolean isLookup(int displayType)
	{
		if (displayType == List || displayType == Table
			|| displayType == TableDir || displayType == Search)
			return true;
		return false;
	}	//	isLookup
	
	/**
	 * Returns true if DisplayType ANY is a generic lookup (see {@link #isLookup(int)}) or a custom lookup (e.g. {@link #Location}, {@link #Locator} etc).
	 * 
	 * (stored as Integer)
	 * 
	 * @param displayType Display Type
	 * @return true if is any Lookup
	 */
	public static boolean isAnyLookup(int displayType)
	{
		if (isLookup(displayType))
		{
			return true;
		}
		return displayType == Location
				|| displayType == Locator
				|| displayType == Account
				|| displayType == PAttribute
				|| displayType == Assignment;
	}
	
	/**
	 * 	Returns true if DisplayType is a Large Object
	 *	@param displayType Display Type
	 *	@return true if LOB
	 */
	public static boolean isLOB (int displayType)
	{
		// metas: 02617: begin: In PostgreSQL TextLong shall not be considered a LOB
		if (displayType == TextLong && DB.isPostgreSQL())
		{
			return false;
		}
		// metas: 02617: end
		
		if (displayType == Binary 
			|| displayType == TextLong)
			return true;
		return false;
	}	//	isLOB

	/**************************************************************************
	 *	Return Format for numeric DisplayType
	 *  @param displayType Display Type (default Number)
	 *  @param language Language
	 *  @param pattern Java Number Format pattern e.g. "#,##0.00"
	 *  @return number format
	 */
	public static DecimalFormat getNumberFormat(int displayType, Language language, String pattern)
	{
		Language myLanguage = language;
		if (myLanguage == null)
			myLanguage = Language.getLoginLanguage();
		// metas: Fallback to base language
		if (myLanguage == null)
			myLanguage = Language.getBaseLanguage();
		Locale locale = myLanguage.getLocale();
		DecimalFormat format = null;
		if (locale != null)
			format = (DecimalFormat)NumberFormat.getNumberInstance(locale);
		else
			format = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
		//
		if (pattern != null && pattern.length() > 0)
		{
			try {
			format.applyPattern(pattern);
			return format;
			}
			catch (IllegalArgumentException e) {
				s_log.warn("Invalid number format: " + pattern);
			}
		}
		else if (displayType == Integer)
		{
			format.setParseIntegerOnly(true);
			format.setMaximumIntegerDigits(INTEGER_DIGITS);
			format.setMaximumFractionDigits(0);
		}
		else if (displayType == Quantity)
		{
			format.setMaximumIntegerDigits(MAX_DIGITS);
			format.setMaximumFractionDigits(MAX_FRACTION);
		}
		else if (displayType == Amount)
		{
			format.setMaximumIntegerDigits(MAX_DIGITS);
			format.setMaximumFractionDigits(MAX_FRACTION);
			format.setMinimumFractionDigits(AMOUNT_FRACTION);
		}
		else if (displayType == CostPrice)
		{
			format.setMaximumIntegerDigits(MAX_DIGITS);
			format.setMaximumFractionDigits(MAX_FRACTION);
			format.setMinimumFractionDigits(AMOUNT_FRACTION);
		}
		// metas-tsa: begin: Number format for IDs 
		else if (isID(displayType))
		{
			format.setParseIntegerOnly(true);
			format.setMaximumIntegerDigits(INTEGER_DIGITS);
			format.setMaximumFractionDigits(0);
			format.setGroupingUsed(false);
		}
		// metas-tsa: end: Number format for IDs 
		else //	if (displayType == Number)
		{
			format.setMaximumIntegerDigits(MAX_DIGITS);
			format.setMaximumFractionDigits(MAX_FRACTION);
			format.setMinimumFractionDigits(1);
		}
		return format;
	}	//	getDecimalFormat
	
	/**************************************************************************
	 *	Return Format for numeric DisplayType
	 *  @param displayType Display Type (default Number)
	 *  @param language Language
	 *  @return number format
	 */
	public static DecimalFormat getNumberFormat(int displayType, Language language)
	{
		return getNumberFormat(displayType, language, null);
	}
	
	/**
	 *	Return Format for numeric DisplayType
	 *  @param displayType Display Type
	 *  @return number format
	 */
	public static DecimalFormat getNumberFormat(int displayType)
	{
		return getNumberFormat (displayType, null);
	}   //  getNumberFormat


	/*************************************************************************
	 *	Return Date Format
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat()
	{
		return getDateFormat (DisplayType.Date, null);
	}   //  getDateFormat

	/**
	 *	Return Date Format
	 *  @param language Language
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat (Language language)
	{
		return getDateFormat (DisplayType.Date, language);
	}	//	getDateFormat

	/**
	 *	Return format for date displayType
	 *  @param displayType Display Type
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat (int displayType)
	{
		return getDateFormat (displayType, null);
	}   //  getDateFormat

	/**
	 *	Return format for date displayType
	 *  @param displayType Display Type (default Date)
	 *  @param language Language
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat (int displayType, Language language)
	{
		return getDateFormat(displayType, language, null);
	}
	/**
	 *	Return format for date displayType
	 *  @param displayType Display Type (default Date)
	 *  @param language Language
	 *  @param pattern Java Simple Date Format pattern e.g. "dd/MM/yy"
	 *  @return date format
	 */
	public static SimpleDateFormat getDateFormat (int displayType, Language language, String pattern)
	{
		Language myLanguage = language;
		if (myLanguage == null)
			myLanguage = Env.getLanguage(Env.getCtx());

		if(myLanguage == null)
		{
			// 03362: happens after client logout
			myLanguage = Language.getLanguage(Language.getAD_Language(Locale.getDefault()));
		}
		//
		if ( pattern != null && pattern.length() > 0)
		{
			SimpleDateFormat format = (SimpleDateFormat)DateFormat.getInstance();
			try {
			format.applyPattern(pattern);
			return format;
			}
			catch (IllegalArgumentException e) {
				s_log.warn("Invalid date pattern: " + pattern);
			}
		}
		
		if (displayType == DateTime)
			return myLanguage.getDateTimeFormat();
		else if (displayType == Time)
			return myLanguage.getTimeFormat();
	//	else if (displayType == Date)
		return myLanguage.getDateFormat();		//	default
	}	//	getDateFormat

	/**
	 *	JDBC Date Format YYYY-MM-DD
	 *  @return date format
	 */
	static public SimpleDateFormat getDateFormat_JDBC()
	{
		return new SimpleDateFormat ("yyyy-MM-dd");
	}   //  getDateFormat_JDBC

	/**
	 *	JDBC Timestamp Format yyyy-mm-dd hh:mm:ss
	 *  @return timestamp format
	 */
	static public SimpleDateFormat getTimestampFormat_Default()
	{
		return new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	}   //  getTimestampFormat_JDBC

	/**
	 *  Return Storage Class.
	 *  (used for MiniTable)
	 *  @param displayType Display Type
	 *  @param yesNoAsBoolean - yes or no as boolean
	 *  @return class Integer - BigDecimal - Timestamp - String - Boolean
	 */
	public static Class<?> getClass (int displayType, boolean yesNoAsBoolean)
	{
		if (isText(displayType) || displayType == List)
			return String.class;
		else if (isID(displayType) || displayType == Integer)    //  note that Integer is stored as BD
			return Integer.class;
		else if (isNumeric(displayType))
			return java.math.BigDecimal.class;
		else if (isDate(displayType))
			return java.sql.Timestamp.class;
		else if (displayType == YesNo)
			return yesNoAsBoolean ? Boolean.class : String.class;
		else if (displayType == Button)
			return String.class;
		else if (isLOB(displayType))	//	CLOB is String
			return byte[].class;
		//
		return Object.class;
	}   //  getClass
	
	/**
	 * 	Get Description
	 *	@param displayType display Type
	 *	@return display type description
	 */
	public static String getDescription (int displayType)
	{
		if (displayType == String)
			return "String";
		if (displayType == Integer)
			return "Integer";
		if (displayType == Amount)
			return "Amount";
		if (displayType == ID)
			return "ID";
		if (displayType == Text)
			return "Text";
		if (displayType == Date)
			return "Date";
		if (displayType == DateTime)
			return "DateTime";
		if (displayType == List)
			return "List";
		if (displayType == Table)
			return "Table";
		if (displayType == TableDir)
			return "TableDir";
		if (displayType == YesNo)
			return "YesNo";
		if (displayType == Location)
			return "Location";
		if (displayType == Number)
			return "Number";
		if (displayType == Binary)
			return "Binary";
		if (displayType == Time)
			return "Time";
		if (displayType == Account)
			return "Account";
		if (displayType == RowID)
			return "RowID";
		if (displayType == Color)
			return "Color";
		if (displayType == Button)
			return "Button";
		if (displayType == Quantity)
			return "Quantity";
		if (displayType == Search)
			return "Search";
		if (displayType == Locator)
			return "Locator";
		if (displayType == Image)
			return "Image";
		if (displayType == Assignment)
			return "Assignment";
		if (displayType == Memo)
			return "Memo";
		if (displayType == PAttribute)
			return "PAttribute";
		if (displayType == TextLong)
			return "TextLong";
		if (displayType == CostPrice)
			return "CostPrice";
		if (displayType == FilePath)
			return "FilePath";
		if (displayType == FileName)
			return "FileName";
		if (displayType == URL)
			return "URL";
		if (displayType == PrinterName)
			return "PrinterName";
		//
		return "UNKNOWN DisplayType=" + displayType;
	}	//	getDescription
	
	// metas: tsa
	private static Map<Integer, String> mapTableNamesByDisplayType = new HashMap<Integer, String>();
	static
	{
		// NOTE: we use strings instead of I_*.Table_Name because this should be independent
		mapTableNamesByDisplayType.put(Location, "C_Location");
		mapTableNamesByDisplayType.put(Account, "C_ElementValue");
		mapTableNamesByDisplayType.put(Color, "AD_Color");
		mapTableNamesByDisplayType.put(Locator, "M_Locator");
		mapTableNamesByDisplayType.put(Image, "AD_Image");
		mapTableNamesByDisplayType.put(Assignment, "S_ResourceAssignment");
		mapTableNamesByDisplayType.put(PAttribute, "M_AttributeSetInstance");
	}
	public static String getTableName(int displayType)
	{
		return mapTableNamesByDisplayType.get(displayType);
	}

	/**
	 * Returns true if DisplayType is a VLookup (List, Table, TableDir, Search). If includeHardcodedLookups is true, the
	 * method will check and will return true also if the displayType is Location, Locator etc.
	 * 
	 * @param displayType
	 *            Display Type
	 * @param includeHardcodedLookups
	 *            if true, Location, Locator, etc will be checked too
	 * @return true if Lookup
	 */
	// metas 01999
	public static boolean isLookup(int displayType, boolean includeHardcodedLookups)
	{
		if (isLookup(displayType))
			return true;

		if (includeHardcodedLookups)
		{
			return displayType == DisplayType.Location
					|| displayType == DisplayType.Locator
					|| displayType == DisplayType.Account
					|| displayType == DisplayType.PAttribute
					|| displayType == DisplayType.Assignment
					|| displayType == DisplayType.Image
			;
		}

		return false;
	}
	
	/**
	 * Convert given string value to the right object for <code>displayType</code>.
	 * 
	 * e.g. If value="123" and displayType=Integer the method will return {@link Integer} value of "123".
	 * 
	 * <pre>
	 * 	Integer 	(IDs, Integer)
	 * 	BigDecimal 	(Numbers)
	 * 	Timestamp	(Dates)
	 * 	Boolean		(YesNo)
	 * 	default: String
	 * </pre>
	 * 
	 * @param value string
	 * @param columnName
	 * @param displayType
	 * @return type dependent converted object or NULL if value is null/empty or conversion could not be done
	 */
	public static Object convertToDisplayType(final String value, final String columnName, final int displayType)
	{
		// true NULL
		if (value == null || value.isEmpty())
		{
			return null;
		}

		// see also MTable.readData
		try
		{
			//
			// Handle hardcoded cases
			// IDs & Integer & CreatedBy/UpdatedBy
			if (!Check.isEmpty(columnName))
			{
				if ("CreatedBy".equals(columnName)
						|| "UpdatedBy".equals(columnName)
						|| (columnName.endsWith("_ID") && DisplayType.isID(displayType))) // teo_sarca [ 1672725 ] Process parameter that ends with _ID but is boolean
				{
					try
					// defaults -1 => null
					{
						int ii = java.lang.Integer.parseInt(value);
						if (ii < 0)
							return null;
						return ii;
					}
					catch (Exception e)
					{
						s_log.warn("Cannot parse: " + value + " - ColumnName=" + columnName + ", DisplayType=" + displayType + ". Returning ZERO.", e);
					}
					return 0;
				}
			}
			
			// Integer
			if (displayType == DisplayType.Integer)
			{
				return new java.lang.Integer(value);
			}
			// Number
			if (DisplayType.isNumeric(displayType))
			{
				return new BigDecimal(value);
			}

			// Timestamps
			if (DisplayType.isDate(displayType))
			{
				// try timestamp format - then date format -- [ 1950305 ]
				java.util.Date date = null;
				try
				{
					date = DisplayType.getTimestampFormat_Default().parse(value);
				}
				catch (java.text.ParseException e)
				{
					date = DisplayType.getDateFormat_JDBC().parse(value);
				}
				return new Timestamp(date.getTime());
			}

			// Boolean
			if (displayType == DisplayType.YesNo)
			{
				return toBoolean(value);
			}

			// Default
			return value;
		}
		catch (Exception e)
		{
			s_log.error("Error while converting value '"+value+"', ColumnName="+columnName+", DisplayType="+displayType, e);
		}
		return null;
	}

	/**
	 * Convert given object to ADempiere's boolean value.
	 * 
	 * @param value
	 * @param defaultValue
	 * @return <ul>
	 *         <li>true if value is boolean true, "true" or "Y"
	 *         <li>false if value is boolean false, "false" or "N"
	 *         <li><code>defaultValue</code> if value is null or other
	 *         </ul>
	 */
	public static final Boolean toBoolean(final Object value, final Boolean defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		else if (value instanceof Boolean)
		{
			return (Boolean)value;
		}
		else
		{
			final String valueStr = value.toString();
			if ("true".equalsIgnoreCase(valueStr)
					|| "Y".equalsIgnoreCase(valueStr))
			{
				return Boolean.TRUE;
			}
			else if ("false".equalsIgnoreCase(valueStr)
					|| "N".equalsIgnoreCase(valueStr))
			{
				return Boolean.FALSE;
			}
			else
			{
				return defaultValue;
			}
		}
	}

	/**
	 * Converts the give object to boolean value, same as {@link #toBoolean(Object, boolean)} but assumes default value is <code>false</code>.
	 * 
	 * @param value
	 * @return <ul>
	 *         <li>true if value is boolean true, "true" or "Y"
	 *         <li>false if value is boolean false, "false" or "N"
	 *         <li>false if value is null or other
	 *         </ul>
	 */
	public static final boolean toBoolean(final Object value)
	{
		final boolean defaultValue = false;
		return toBoolean(value, defaultValue);
	}

	/**
	 * Converts given boolean value to ADempiere's string representation of it
	 * 
	 * @param value
	 * @return <ul>
	 *         <li><code>null</code> if value is null
	 *         <li>"Y" if value is true
	 *         <li>"N" if value is false
	 *         </ul>
	 */
	public static final String toBooleanString(final Boolean value)
	{
		if (value == null)
		{
			return null;
		}
		return value ? "Y" : "N";
	}
}	//	DisplayType
