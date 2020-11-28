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
package org.compiere.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.validationRule.IValidationContext;
import org.compiere.model.Lookup;
import org.compiere.model.MQuery;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;

import de.metas.util.Check;

/**
 *  Manual Lookup (Model)- loaded by the put method
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: XLookup.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class XLookup extends Lookup
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1388987648081532657L;

	/**
	 *	Manual Lookup
	 * 	@param keyColumn key Column
	 */
	public XLookup(String keyColumn)
	{
		super(DisplayType.TableDir, 0);
		m_keyColumn = keyColumn;
	}	//	XLookup


	/** Key Column - as identifier      */
	private String		m_keyColumn;

	/**
	 *	Get Display String of key value
	 * 	@param key key
	 * 	@return display
	 */
	@Override
	public String getDisplay (final IValidationContext evalCtx, final Object key)
	{
		//  linear search in m_data
		final int size = getSize();
		for (int i = 0; i < size; i++)
		{
			final Object oo = getElementAt(i);
			if (oo != null && oo instanceof NamePair)
			{
				NamePair pp = (NamePair)oo;
				if (pp.getID().equals(key))
					return pp.getName();
			}
		}
		return "<" + key + ">";
	}	//	getDisplay

	/**
	 *  The Lookup contains the key
	 * 	@param key key
	 * 	@return true if contains key
	 */
	@Override
	public boolean containsKey (final IValidationContext evalCtx, final Object key)
	{
		//  linear search in p_data
		final int size = getSize();
		for (int i = 0; i < size; i++)
		{
			final Object oo = getElementAt(i);
			if (oo != null && oo instanceof NamePair)
			{
				NamePair pp = (NamePair)oo;
				if (pp.getID().equals(key))
					return true;
			}
		}
		return false;
	}   //  containsKey

	/**
	 *	Get Object of Key Value
	 *  @param key key
	 *  @return Object or null
	 */
	@Override
	public NamePair get (final IValidationContext evalCtx, Object key)
	{
		//  linear search in m_data
		final int size = getSize();
		for (int i = 0; i < size; i++)
		{
			Object oo = getElementAt(i);
			if (oo != null && oo instanceof NamePair)
			{
				NamePair pp = (NamePair)oo;
				if (pp.getID().equals(key))
					return pp;
			}
		}
		return null;
	}	//	get


	/**
	 *	Return data as sorted Array
	 * 	@param mandatory mandatory
	 * 	@param onlyValidated only validated
	 * 	@param onlyActive only active
	 * 	@param temporary force load for temporary display
	 * 	@return list of data
	 */
	@Override
	public List<Object> getData (boolean mandatory,
		boolean onlyValidated, boolean onlyActive, boolean temporary)
	{
		final int size = getSize();
		final List<Object> list = new ArrayList<Object>(size);

		for (int i = 0; i < size; i++)
		{
			final Object oo = getElementAt(i);
			list.add(oo);
		}

		//	Sort Data
		if (m_keyColumn.endsWith("_ID"))
		{
			KeyNamePair p = KeyNamePair.EMPTY;
			if (!mandatory)
				list.add (p);
			Collections.sort (list, p);
		}
		else
		{
			ValueNamePair p = ValueNamePair.EMPTY;
			if (!mandatory)
				list.add (p);
			Collections.sort (list, p);
		}
		return list;
	}	//	getArray

	/**
	 *	Refresh Values (nop)
	 * 	@return number of cache
	 */
	@Override
	public int refresh()
	{
		return getSize();
	}	//	refresh

	@Override
	public String getTableName()
	{
		if (Check.isEmpty(m_keyColumn, true))
		{
			return null;
		}

		return MQuery.getZoomTableName(m_keyColumn);
	}

	/**
	 *	Get underlying fully qualified Table.Column Name
	 * 	@return column name
	 */
	@Override
	public String getColumnName()
	{
		return m_keyColumn;
	}   //  getColumnName

	@Override
	public String getColumnNameNotFQ()
	{
		return m_keyColumn;
	}

}	//	XLookup
