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

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.adempiere.plaf.AdempierePLAF;

/**
 *  Check Box Renderer based on Boolean values
 *
 *  @author     Jorg Janke
 *  @version    $Id: CheckRenderer.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public final class CheckRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7687503957742100068L;

	/**
	 *  Constructor
	 */
	public CheckRenderer()
	{
		super();
		m_check.setMargin(new Insets(0,0,0,0));
		m_check.setHorizontalAlignment(JLabel.CENTER);
		m_check.setOpaque(true);
	}   //  CheckRenderer

	private JCheckBox   m_check = new JCheckBox();

	/**
	 *  Return centered, white Check Box
	 *  @param table
	 *  @param value
	 *  @param isSelected
	 *  @param hasFocus
	 *  @param row
	 *  @param col
	 *  @return Component
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int col)
	{
		//  Background & Foreground
		Color bg = AdempierePLAF.getFieldBackground_Normal();
		//  Selected is white on blue in Windows
		if (isSelected && !hasFocus)
			bg = table.getSelectionBackground();
		//  row not selected or field has focus
		else
		{
			//  Inactive Background
			if (!table.isCellEditable(row, col))
				bg = AdempierePLAF.getFieldBackground_Inactive();
		}
		//  Set Color
		m_check.setBackground(bg);

		//  Value
		setValue(value);
		return m_check;
	}	//	getTableCellRendererComponent

	/**
	 *  Set Value
	 *  @param value
	 */
	@Override
	public void setValue(Object value)
	{
		if (value != null && ((Boolean)value).booleanValue())
			m_check.setSelected(true);
		else
			m_check.setSelected(false);
	}   //  setValue

}   //  CheckRenderer
