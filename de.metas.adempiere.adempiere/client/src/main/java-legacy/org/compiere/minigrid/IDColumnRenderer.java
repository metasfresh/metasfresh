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
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *  ID Column Renderer
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: IDColumnRenderer.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class IDColumnRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7513837304119751313L;

	/**
	 *  Constructor
	 *  @param multiSelection determines layout - button for single, check for multi
	 */
	public IDColumnRenderer(boolean multiSelection)
	{
		super();
		m_multiSelection = multiSelection;
		//          Multi => Check
		if (m_multiSelection)
		{
			m_check = new JCheckBox();
			m_check.setMargin(new Insets(0,0,0,0));
			m_check.setHorizontalAlignment(JLabel.CENTER);
		}
		else    //  Single => Button
		{
			m_button = new JButton();
			m_button.setMargin(new Insets(0,0,0,0));
			m_button.setSize(new Dimension(5,5));
		}
	}   //  IDColumnRenderer

	/** Mult-Selection flag */
	private boolean     m_multiSelection;
	/** The Single-Selection renderer   */
	private JButton     m_button;
	/* The Multi-Selection renderer     */
	private JCheckBox   m_check;

	/**
	 *  Set Value (for multi-selection)
	 *  @param value
	 */
	@Override
	protected void setValue(Object value)
	{
		if (m_multiSelection)
		{
			boolean sel = false;
			if (value == null)
				;
			else if (value instanceof IDColumn)
				sel = ((IDColumn)value).isSelected();
			else if (value instanceof Boolean)
				sel = ((Boolean)value).booleanValue();
			else
				sel = value.toString().equals("Y");
			//
			m_check.setSelected(sel);
		}
	}   //  setValue

	/**
	 *  Return rendering component
	 *  @param table
	 *  @param value
	 *  @param isSelected
	 *  @param hasFocus
	 *  @param row
	 *  @param column
	 *  @return Component (CheckBox or Button)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		setValue(value);
		if (m_multiSelection)
			return m_check;
		else
			return m_button;
	}   //  setTableCellRenderereComponent

}   //  IDColumnRenderer
