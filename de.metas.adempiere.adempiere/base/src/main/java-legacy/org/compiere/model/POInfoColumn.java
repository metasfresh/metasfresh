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
package org.compiere.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.logging.LogManager;

/**
 * PO Info Column Info Value Object
 *
 * @author Jorg Janke
 * @version $Id: POInfoColumn.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public final class POInfoColumn implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1667303121090497293L;

	private static final transient Logger logger = LogManager.getLogger(POInfoColumn.class);

	/**
	 * Constructor
	 *
	 * @param ad_Column_ID Column ID
	 * @param columnName Column name
	 * @param columnSQL virtual column
	 * @param displayType Display Type
	 * @param isMandatory Mandatory
	 * @param isUpdateable Updateable
	 * @param defaultLogic Default Logic
	 * @param columnLabel Column Label
	 * @param columnDescription Column Description
	 * @param isKey true if key
	 * @param isParent true if parent
	 * @param ad_Reference_Value_ID reference value
	 * @param validationCode sql validation code
	 * @param fieldLength Field Length
	 * @param valueMin minimal value
	 * @param valueMax maximal value
	 * @param isTranslated translated
	 * @param isEncrypted encrypted
	 * @param isAllowLogging allow logging
	 */
	public POInfoColumn(int ad_Column_ID, String columnName, String columnSQL, int displayType,
			boolean isMandatory, boolean isUpdateable, String defaultLogic,
			String columnLabel, String columnDescription,
			boolean isKey, boolean isParent,
			int ad_Reference_Value_ID, int AD_Val_Rule_ID,
			int fieldLength, String valueMin, String valueMax,
			boolean isTranslated, boolean isEncrypted, boolean isAllowLogging)
	{
		super();
		AD_Column_ID = ad_Column_ID;
		ColumnName = columnName;
		this.virtualColumn = !Check.isEmpty(columnSQL, false); // trimWhitespaces=false to preserve back compatibility
		if (virtualColumn)
		{
			this.ColumnSQL = columnSQL;
			this.sqlColumnForSelect = ColumnSQL + " AS " + ColumnName;
		}
		else
		{
			this.ColumnSQL = null;
			this.sqlColumnForSelect = ColumnName;
		}

		int displayTypeToSet = displayType;

		ITableRefInfo tableRefInfo = null;

		// task #500: For DisplayType=Table or Search and AD_Reference_Value_ID > 0, retrieve the ITableRefInfo
		if (ad_Reference_Value_ID > 0 && (isTableDisplayType(displayType) || isSearchDisplayType(displayType)))
		{
			// The method org.adempiere.ad.service.ILookupDAO.retrieveTableRefInfo(int) logs warnings when the {@link ITableRefInfo} was not found.
			// Permit warnings here because we shouldn't have Table or Search types with no reference table IDs.
			tableRefInfo = Services.get(ILookupDAO.class).retrieveTableRefInfo(ad_Reference_Value_ID);
		}

		// FIXME: HARDCODED
		if (columnName.equals("AD_Language") ||
				columnName.equals("EntityType") ||
				// task #500: Also allow type String for non-numeric types with a reference value (Table and search)
				// Note that the column shall not be a numeric key
				(tableRefInfo != null && !tableRefInfo.isNumericKey()))

		{
			displayTypeToSet = org.compiere.util.DisplayType.String;
			ColumnClass = String.class;
		}

		else if (columnName.equals("Posted") || columnName.equals("Processed") || columnName.equals("Processing"))

		{
			ColumnClass = Boolean.class;
		}
		else if (Services.get(IColumnBL.class).isRecordColumnName(columnName))

		{
			displayTypeToSet = org.compiere.util.DisplayType.ID;
			ColumnClass = Integer.class;
		}
		else

		{
			ColumnClass = org.compiere.util.DisplayType.getClass(displayType, true);
		}

		DisplayType = displayTypeToSet;
		IsMandatory = isMandatory;
		IsUpdateable = isUpdateable;
		DefaultLogic = defaultLogic;
		ColumnLabel = columnLabel;
		ColumnDescription = columnDescription;
		IsKey = isKey;
		IsParent = isParent;
		//
		AD_Reference_Value_ID = ad_Reference_Value_ID;
		// ValidationCode = validationCode;
		this.AD_Val_Rule_ID = AD_Val_Rule_ID <= 0 ? -1 : AD_Val_Rule_ID;
		//
		FieldLength = fieldLength;
		ValueMin = valueMin;
		ValueMin_BD =

		toBigDecimalOrNull(ValueMin, "ValueMin");
		ValueMax = valueMax;
		ValueMax_BD = toBigDecimalOrNull(ValueMax, "ValueMax");
		IsTranslated = isTranslated;
		IsEncrypted = isEncrypted;
		IsAllowLogging = isAllowLogging;
	}   // Column

	/**
	 * Return true only for Search display type
	 * 
	 * @param displayType
	 * @return
	 */
	private boolean isSearchDisplayType(int displayType)
	{
		if (org.compiere.util.DisplayType.Search == displayType)
		{
			return true;
		}
		return false;
	}

	/**
	 * Return true only for Table display type
	 * 
	 * @param displayType
	 * @return
	 */
	private boolean isTableDisplayType(int displayType)
	{
		if (org.compiere.util.DisplayType.Table == displayType)
		{
			return true;
		}
		return false;
	}

	/** Column ID */
	final int AD_Column_ID;
	/** Column Name */
	private final String ColumnName;
	/** Virtual Column SQL */
	private final String ColumnSQL;
	/** Is Virtual Column */
	private final boolean virtualColumn;
	/** Is Lazy Loading */
	boolean IsLazyLoading;
	/** Display Type */
	final int DisplayType;
	/** Data Type */
	final Class<?> ColumnClass;
	/** Mandatory */
	final boolean IsMandatory;
	/** Default Value */
	final String DefaultLogic;
	/** Updateable */
	boolean IsUpdateable;
	/** Label */
	private final String ColumnLabel;
	/** Description */
	private final String ColumnDescription;
	/** PK */
	final boolean IsKey;
	/** FK to Parent */
	final boolean IsParent;
	/** Translated */
	private final boolean IsTranslated;
	/** Encrypted */
	final boolean IsEncrypted;
	/** Allow Logging */
	final boolean IsAllowLogging;

	/** Reference Value */
	final int AD_Reference_Value_ID;
	/** Validation */
	// public String ValidationCode;
	final int AD_Val_Rule_ID;

	/** Field Length */
	final int FieldLength;
	/** Min Value */
	final String ValueMin;
	/** Max Value */
	final String ValueMax;
	/** Min Value */
	final BigDecimal ValueMin_BD;
	/** Max Value */
	final BigDecimal ValueMax_BD;

	/* package */boolean IsCalculated = false;
	// metas: us215
	/* package */boolean IsUseDocumentSequence = false;
	// metas: 05133
	/* package */boolean IsStaleable = true;
	// metas: 01537
	/* package */boolean IsSelectionColumn;

	private final String sqlColumnForSelect;
	
	/** Cached {@link MLookupInfo} for {@link Env#WINDOW_None} (most used case) */
	private Optional<MLookupInfo> _lookupInfoForWindowNone = null;

	/**
	 * String representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("POInfo.Column[")
				.append(ColumnName)
				.append(",ID=").append(AD_Column_ID)
				.append(",DisplayType=").append(DisplayType)
				.append(",ColumnClass=").append(ColumnClass)
				.append("]");
		return sb.toString();
	}	// toString

	private static final BigDecimal toBigDecimalOrNull(final String valueStr, final String name)
	{
		if (Check.isEmpty(valueStr, true))
		{
			return null;
		}

		try
		{
			return new BigDecimal(valueStr.trim());
		}
		catch (final Exception ex) // i.e. NumberFormatException
		{
			logger.error("Cannot parse " + name + "=" + valueStr, ex);
		}

		return null;
	}

	public String getColumnName()
	{
		return this.ColumnName;
	}

	public int getAD_Column_ID()
	{
		return this.AD_Column_ID;
	}

	public String getColumnLabel()
	{
		return this.ColumnLabel;
	}

	public String getColumnDescription()
	{
		return this.ColumnDescription;
	}

	public boolean isTranslated()
	{
		return this.IsTranslated;
	}

	public String getColumnSQL()
	{
		return this.ColumnSQL;
	}

	public boolean isVirtualColumn()
	{
		return this.virtualColumn;
	}

	public String getColumnSqlForSelect()
	{
		return sqlColumnForSelect;
	}

	public int getDisplayType()
	{
		return DisplayType;
	}
	
	public int getAD_Reference_Value_ID()
	{
		return AD_Reference_Value_ID;
	}
	
	public boolean isSelectionColumn()
	{
		return IsSelectionColumn;
	}

	public boolean isMandatory()
	{
		return IsMandatory;
	}
	
	public boolean isParent()
	{
		return IsParent;
	}
	
	public int getAD_Val_Rule_ID()
	{
		return AD_Val_Rule_ID;
	}
	
	public boolean isLookup()
	{
		return org.compiere.util.DisplayType.isLookup(DisplayType);
	}
	
	public String getReferencedTableNameOrNull()
	{
		// Special lookups (Location, Locator etc)
		final String refTableName = org.compiere.util.DisplayType.getTableName(DisplayType);
		if(refTableName != null)
		{
			return refTableName;
		}

		// Regular lookups
		final MLookupInfo lookupInfo = getLookupInfo(Env.WINDOW_None);
		if(lookupInfo != null)
		{
			return lookupInfo.getTableName();
		}
		
		return null;
	}

	
	public MLookupInfo getLookupInfo(final int windowNo)
	{
		//
		// List, Table, TableDir
		if(isLookup())
		{
			if(windowNo == Env.WINDOW_None)
			{
				if(_lookupInfoForWindowNone == null)
				{
					final MLookupInfo lookupInfoCached = MLookupFactory.getLookupInfo(Env.WINDOW_None, DisplayType, ColumnName, AD_Reference_Value_ID, IsParent, AD_Val_Rule_ID);
					_lookupInfoForWindowNone = Optional.ofNullable(lookupInfoCached);
				}
				return _lookupInfoForWindowNone.orElse(null);
			}
			
			final MLookupInfo lookupInfo = MLookupFactory.getLookupInfo(windowNo, DisplayType, ColumnName, AD_Reference_Value_ID, IsParent, AD_Val_Rule_ID);
			return lookupInfo;
		}
		else
		{
			return null;
		}
	}
	
	public Lookup getLookup(final Properties ctx, final int windowNo)
	{
		//
		// List, Table, TableDir
		if (isLookup())
		{
			try
			{
				final MLookupInfo lookupInfo = getLookupInfo(windowNo);
				if(lookupInfo == null)
				{
					return null;
				}
				
				final Lookup lookup = MLookupFactory.ofLookupInfo(ctx, lookupInfo, AD_Column_ID);
				return lookup;
			}
			catch (Exception e)
			{
				return null;
			}
		}
		else
		{
			return null;
		}
		
	}
}	// POInfoColumn
