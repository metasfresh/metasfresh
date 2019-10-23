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
package org.compiere.apps.search;

import org.compiere.minigrid.ColumnInfo;

/**
 *  Info Column Details
 *
 * @author 	Jorg Janke
 * @version 	$Id: Info_Column.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * 
 * NOTE: Use {@link org.compiere.minigrid.ColumnInfo} instead
 */
public class Info_Column extends ColumnInfo
{
	/**
	 *  Create Info Column (r/o and not color column)
	 *
	 *  @param colHeader Column Header
	 *  @param colSQL    SQL select code for column
	 *  @param colClass  class of column - determines display
	 */
	public Info_Column (String colHeader, String colSQL, Class<?> colClass)
	{
		super(colHeader, colSQL, colClass);
	}   //  Info_Column

	/**
	 *  Create Info Column (r/o and not color column)
	 *
	 *  @param colHeader Column Header
	 *  @param colSQL    SQL select code for column
	 *  @param colClass  class of column - determines display
	 *  @param IDcolSQL  SQL select for the ID of the for the displayed column (KeyNamePair)
	 */
	public Info_Column (String colHeader, String colSQL, Class<?> colClass, String IDcolSQL)
	{
		super(colHeader, colSQL, colClass, true, false, IDcolSQL);
	}   //  Info_Column
	
	/**
	 *  Create Info Column (not color column)
	 *
	 *  @param colHeader Column Header
	 *  @param colSQL    SQL select code for column
	 *  @param colClass  class of column - determines display
	 *  @param readOnly  column is read only
	 *  @author ashley
	 */
	 public Info_Column (String colHeader, String colSQL, Class<?> colClass, boolean readOnly)
	 {
	    super(colHeader, colSQL, colClass, readOnly, false, null);
	 }   //  Info_Column

	/**
	 *  Create Info Column
	 *
	 *  @param colHeader Column Header
	 *  @param colSQL    SQL select code for column
	 *  @param colClass  class of column - determines display
	 *  @param readOnly  column is read only
	 *  @param colorColumn   if true, value of column determines foreground color
	 *  @param IDcolSQL  SQL select for the ID of the for the displayed column
	 */
	public Info_Column (String colHeader, String colSQL, Class<?> colClass, 
		boolean readOnly, boolean colorColumn, String IDcolSQL)
	{
		super(colHeader, colSQL, colClass, readOnly, colorColumn, IDcolSQL);
	}   //  Info_Column
	public void setIDcolSQL(String IDcolSQL)
	{
		super.setKeyPairColSQL(IDcolSQL);
	}
	public String getIDcolSQL()
	{
		return super.getKeyPairColSQL();
	}
	public boolean isIDcol()
	{
		return super.isKeyPairCol();
	}

	@Override
	public String toString()
	{
		return super.toString();
	}
}   //  infoColumn
