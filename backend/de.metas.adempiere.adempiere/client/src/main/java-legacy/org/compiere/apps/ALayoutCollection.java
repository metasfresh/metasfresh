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
package org.compiere.apps;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  Collection of Components ordered based on ALayoutConstraint
 *
 *  @author Jorg Janke
 *  @version  $Id: ALayoutCollection.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
class ALayoutCollection extends HashMap<Object,Object>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2906536401026546141L;

	/**
	 *  Create Collection
	 */
	public ALayoutCollection()
	{
		super();
	}   //  ALayoutCollection

	/**
	 *  Add a Component.
	 *  If constraint is null, it is added to the last row as additional column
	 *  @param constraint
	 *  @param component
	 *  @return component
	 *  @throws IllegalArgumentException if component is not a Component
	 */
	public Object put (Object constraint, Object component)
	{
		if (!(component instanceof Component))
			throw new IllegalArgumentException ("ALayoutCollection can only add Component values");

		if (constraint != null
				&& !containsKey(constraint)
				&& constraint instanceof ALayoutConstraint)
		{
		//	Log.trace(this,Log.l6_Database, "ALayoutCollection.put", constraint.toString());
			return super.put (constraint, component);
		}

		//  We need to create constraint
		if (super.size() == 0)
		{
		//	Log.trace(this,Log.l6_Database, "ALayoutCollection.put - first");
			return super.put(new ALayoutConstraint(0,0), component);
		}

		//  Add to end of list
		int row = getMaxRow();
		if (row == -1)
			row = 0;
		int col = getMaxCol(row) + 1;
		ALayoutConstraint next = new ALayoutConstraint(row, col);
	//	Log.trace(this,Log.l6_Database, "ALayoutCollection.put - addEnd", next.toString());
		return super.put(next, component);
	}   //  put

	/**
	 *  Get Maximum Row Number
	 *  @return max row no - or -1 if no row
	 */
	public int getMaxRow ()
	{
		int maxRow = -1;
		//
		Iterator i = keySet().iterator();
		while (i.hasNext())
		{
			ALayoutConstraint c = (ALayoutConstraint)i.next();
			maxRow = Math.max(maxRow, c.getRow());
		}
		return maxRow;
	}   //  getMaxRow

	/**
	 *  Get Maximum Column Number
	 *  @return max col no - or -1 if no column
	 */
	public int getMaxCol ()
	{
		int maxCol = -1;
		//
		Iterator i = keySet().iterator();
		while (i.hasNext())
		{
			ALayoutConstraint c = (ALayoutConstraint)i.next();
			maxCol = Math.max(maxCol, c.getCol());
		}
		return maxCol;
	}   //  getMaxCol

	/**
	 *  Get Maximum Column Number for Row
	 *  @param row
	 *  @return max col no for row - or -1 if no col in row
	 */
	public int getMaxCol (int row)
	{
		int maxCol = -1;
		//
		Iterator i = keySet().iterator();
		while (i.hasNext())
		{
			ALayoutConstraint c = (ALayoutConstraint)i.next();
			if (c.getRow() == row)
				maxCol = Math.max(maxCol, c.getCol());
		}
		return maxCol;
	}   //  getMaxCol

}   //  ALayoutCollection
