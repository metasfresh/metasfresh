/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.event;

/**
 * An event encapsulating a change in a Table.
 * The event details the object that changed, it's position in the table
 * and the changed value.
 * 
 * @author Andrew Kimball
 */
public class TableValueChangeEvent extends ValueChangeEvent
{
	/** the row on which the event occurred */
	private int m_row;
	/** the column on which the event occurred */
	private int m_column;

	/**
	 * Constructor for the event.
	 * 
	 * @param source		The object that changed
	 * @param propertyName	The column name of the changed object 
	 * @param row			The row of the changed object
	 * @param column		The column of the changed object 
	 * @param oldValue		The new value of the object 
	 * @param newValue		The old value of the object (often just a copy of the new value)
	 */
	public TableValueChangeEvent(Object source, String propertyName, 
								int row, int column,  
								Object oldValue, Object newValue)
	{
		super(source, propertyName, oldValue, newValue);
		this.m_row = row;
		this.m_column = column;
	}

	/**
	 * Get the column index for the changed value
	 * 
	 * @return the index of the column at which the change occurred 
	 */
    public int getColumn()
    {
        return m_column;
    }

	/**
	 * Get the row index for the changed value
	 * 
	 * @return the index of the row at which the change occurred 
	 */
    public int getRow()
    {
        return m_row;
    }
	
}
