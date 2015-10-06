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
package org.compiere.minigrid;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *  ID Column for MiniGrid allows to select a column and maintains the record ID.
 *  
 *  Addition by metas: selected property is now a bound property.
 *  
 * 	@author 	Jorg Janke
 *  @author t.schoeneberg@metas.de
 */
public class IDColumn
{
	/**
	 *  ID Column constructor
	 *  @param record_ID
	 */
	public IDColumn (int record_ID)
	{
		this(new Integer(record_ID));
	}   //  IDColumn

	/**
	 *  ID Column constructor
	 *  @param record_ID
	 */
	public IDColumn(Integer record_ID)
	{
		super();
		setRecord_ID(record_ID);
		setSelected(false);
	}   //  IDColumn

	/** Is the row selected         */
	private boolean     m_selected = false;
	/** The Record_ID               */
	private Integer     m_record_ID;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	/**
	 *  Set Selection
	 *  @param selected
	 */
	public void setSelected(boolean selected)
	{
		final boolean old = m_selected;
		m_selected = selected;
        this.pcs.firePropertyChange( "selected", old, m_selected );
	}
	
	/**
	 *  Is Selected
	 *  @return true if selected
	 */
	public boolean isSelected()
	{
		return m_selected;
	}

	/**
	 *  Set Record_ID
	 *  @param record_ID
	 */
	public void setRecord_ID(Integer record_ID)
	{
		m_record_ID = record_ID;
	}
	/**
	 * Get Record ID
	 * @return ID
	 */
	public Integer getRecord_ID()
	{
		return m_record_ID;
	}

	/**
	 *  To String
	 *  @return String representation
	 */
	@Override
	public String toString()
	{
		return "IDColumn - ID=" + m_record_ID + ", Selected=" + m_selected;
	}   //  toString

	public void addPropertyChangeListener( PropertyChangeListener listener )
    {
        this.pcs.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener )
    {
        this.pcs.removePropertyChangeListener( listener );
    }

	
}   //  IDColumn
