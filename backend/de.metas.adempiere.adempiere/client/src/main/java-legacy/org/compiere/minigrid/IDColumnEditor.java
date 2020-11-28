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

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

import org.compiere.swing.CCheckBox;

/**
 *  ID Column Editor (with Select Box).
 *  CheckBox change is only detected, if you move out of the cell.
 *  A ActionListener is added to the check box and the table forced
 *  to notice the change immediately.
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: IDColumnEditor.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class IDColumnEditor extends AbstractCellEditor
	implements TableCellEditor, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7263316764540177503L;

	/**
	 *  Constructor
	 */
	public IDColumnEditor()
	{
		m_check.setMargin(new Insets(0,0,0,0));
		m_check.setHorizontalAlignment(JLabel.CENTER);
		m_check.addActionListener(this);
	}   //  IDColumnEditor

	/** the selection       */
	private JCheckBox   m_check = new CCheckBox();
	/** temporary value     */
	private IDColumn    m_value = null;

	private JTable      m_table;

	/**
	 *  Return Selection Status as IDColumn
	 *  @return value
	 */
	@Override
	public Object getCellEditorValue()
	{
	//	log.debug( "IDColumnEditor.getCellEditorValue - " + m_check.isSelected());
		if (m_value != null)
			m_value.setSelected (m_check.isSelected());
		return m_value;
	}   //  getCellEditorValue

	/**
	 *  Get visual Component
	 *  @param table
	 *  @param value
	 *  @param isSelected
	 *  @param row
	 *  @param column
	 *  @return Component
	 */
	@Override
	public Component getTableCellEditorComponent (JTable table, Object value, boolean isSelected, int row, int column)
	{
	//	log.debug( "IDColumnEditor.getTableCellEditorComponent", value);
		m_table = table;
		//  set value
		if (value != null && value instanceof IDColumn)
			m_value = (IDColumn)value;
		else
		{
			m_value = null;
			return null;
			//throw new IllegalArgumentException("ICColumnEditor.getTableCellEditorComponent - value=" + value);
		}
		//  set editor value
		m_check.setSelected(m_value.isSelected());
		return m_check;
	}   //  getTableCellEditorComponent

	/**
	 *  Can we edit it
	 *  @param anEvent
	 *  @return true (constant)
	 */
	@Override
	public boolean isCellEditable (EventObject anEvent)
	{
		return true;
	}   //  isCellEditable

	/**
	 *  Can the cell be selected
	 *  @param anEvent
	 *  @return true (constant)
	 */
	@Override
	public boolean shouldSelectCell (EventObject anEvent)
	{
		return true;
	}   //  shouldSelectCell

	/**
	 *  Action Listener
	 *  @param e
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (m_table != null)
		{
			m_table.editingStopped(new ChangeEvent(this));
		}
	}   //  actionPerformed

}   //  IDColumnEditor
