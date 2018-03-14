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
package org.compiere.model;

import java.io.Serializable;

import org.adempiere.util.Check;


/**
 *  Lookup Display Column Value Object
 *
 *  @author Jorg Janke
 *  @version $Id: LookupDisplayColumn.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 *
 * @author Teo Sarca
 * 		<li>BF [ 2933367 ] Virtual Column Identifiers are not working
 * 			https://sourceforge.net/tracker/?func=detail&aid=2933367&group_id=176962&atid=879332
 */
public class LookupDisplayColumn implements ILookupDisplayColumn, Serializable
{
	private static final long serialVersionUID = 5876427657897043394L;

	/**
	 * Lookup Column Value Object
	 * @param columnName column name
	 * @param columnSQL column SQL (in case is virtual column)
	 * @param isTranslated translated
	 * @param ad_Reference_ID display type
	 * @param ad_Reference_Value_ID table/list reference id
	 * @param formatPattern
	 */
	public LookupDisplayColumn(
			final String columnName,
			final String columnSQL,
			final boolean isTranslated,
			final int ad_Reference_ID,
			final int ad_Reference_Value_ID,
			final String formatPattern)
	{
		ColumnName = columnName;
		IsTranslated = isTranslated;
		DisplayType = ad_Reference_ID;
		AD_Reference_ID = ad_Reference_Value_ID;
		ColumnSQL = columnSQL;
		IsVirtual = !Check.isEmpty(ColumnSQL, true);
		this.formatPattern = formatPattern;
	}	//

	/** Column Name		*/
	private final String 	ColumnName;
	/** Translated		*/
	private final boolean 	IsTranslated;
	/** Display Type	*/
	private final int 		DisplayType;
	/** Value Reference	*/
	private final int 		AD_Reference_ID;
	/** Column SQL		*/
	private final String	ColumnSQL;
	/** Is Virtual Column */
	private final boolean	IsVirtual;
	private final String formatPattern;

	/**
	 * 	String Representation
	 * 	@return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("LookupDisplayColumn[");
		sb.append("ColumnName=").append(ColumnName);
		if (IsVirtual)
			sb.append(",ColumnSQL=").append(ColumnSQL);
		if (IsTranslated)
			sb.append(",IsTranslated");
		sb.append(",DisplayType=").append(DisplayType);
		if (AD_Reference_ID != 0)
			sb.append(",AD_Reference_ID=").append(AD_Reference_ID);
		if (formatPattern != null)
			sb.append(", formatPattern").append(formatPattern);
		sb.append("]");
		return sb.toString();
	}	//	toString

	@Override
	public String getColumnName()
	{
		return ColumnName;
	}

	@Override
	public boolean isTranslated()
	{
		return IsTranslated;
	}

	@Override
	public int getDisplayType()
	{
		return DisplayType;
	}

	@Override
	public int getAD_Reference_ID()
	{
		return AD_Reference_ID;
	}

	@Override
	public String getColumnSQL()
	{
		return ColumnSQL;
	}

	@Override
	public boolean isVirtual()
	{
		return IsVirtual;
	}

	@Override
	public String getFormatPattern()
	{
		return formatPattern;
	}
}	//	LookupDisplayColumn
