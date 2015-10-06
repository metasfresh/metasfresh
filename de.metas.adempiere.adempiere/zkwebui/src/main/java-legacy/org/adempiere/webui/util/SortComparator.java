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
package org.adempiere.webui.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.Collator;
import java.util.Comparator;

import org.compiere.util.Language;
import org.compiere.util.NamePair;

/**
 * Default comparator, adapted from MSort
 * @author hengsin
 */
public final class SortComparator implements Comparator<Object>, Serializable
{
	/**
	 * generated
	 */
	private static final long serialVersionUID = 1265701980018071753L;

	private int columnIndex;

	/**
	 *  @param columnIndex
	 *  @param ascending
	 *  @param language
	 */
	public SortComparator (int columnIndex, boolean ascending, Language language)
	{
		// Create string collator for login language - teo_sarca, [ 1672820 ]
		m_collator = Collator.getInstance(language.getLocale());
		
		setSortAsc(ascending);
		
		this.columnIndex = columnIndex;
	}	//	MSort

	/** Multiplier          */
	private int		m_multiplier = 1;		//	Asc by default
	
	/** String Collator */
	private Collator m_collator = null;

	/**
	 *	Sort Ascending
	 *  @param ascending if true sort ascending
	 */
	public void setSortAsc (boolean ascending)
	{
		if (ascending)
			m_multiplier = 1;
		else
			m_multiplier = -1;
	}	//	setSortAsc

	
	/**************************************************************************
	 *	Compare Data of two entities
	 *  @param o1 object
	 *  @param o2 object
	 *  @return comparator
	 */
	public int compare (Object o1, Object o2)
	{
		//	Get Objects to compare
		Object cmp1 = o1;
		if (cmp1 instanceof NamePair)
			cmp1 = ((NamePair)cmp1).getName();

		Object cmp2 = o2;
		if (cmp2 instanceof NamePair)
			cmp2 = ((NamePair)cmp2).getName();

		//	Comparing Null values
		if (cmp1 == null)
		{
			if (cmp2 == null)
				return 0;
			return -1 * m_multiplier;
		}
		if (cmp2 == null)
			return 1 * m_multiplier;

		/**
		 *	compare different data types
		 */

		//	String
		if (cmp1 instanceof String && cmp2 instanceof String)
		{
			return m_collator.compare(cmp1, cmp2) * m_multiplier; // teo_sarca [ 1672820 ]
		}
		//	Date
		else if (cmp1 instanceof Timestamp && cmp2 instanceof Timestamp)
		{
			Timestamp t = (Timestamp)cmp1;
			return t.compareTo((Timestamp)cmp2) * m_multiplier;
		}
		//	BigDecimal
		else if (cmp1 instanceof BigDecimal && cmp2 instanceof BigDecimal)
		{
			BigDecimal d = (BigDecimal)cmp1;
			return d.compareTo((BigDecimal)cmp2) * m_multiplier;
		}
		//	Integer
		else if (cmp1 instanceof Integer && cmp2 instanceof Integer)
		{
			Integer d = (Integer)cmp1;
			return d.compareTo((Integer)cmp2) * m_multiplier;
		}
		//	Double
		else if (cmp1 instanceof Double && cmp2 instanceof Double)
		{
			Double d = (Double)cmp1;
			return d.compareTo((Double)cmp2) * m_multiplier;
		}

		//  Convert to string value
		String s = cmp1.toString();
		return m_collator.compare(s, cmp2.toString()) * m_multiplier;  // teo_sarca [ 1672820 ]
	}	//	compare


	public int getColumnIndex() {
		return columnIndex;
	}

}	//	SortComparator
