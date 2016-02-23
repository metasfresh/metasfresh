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
package org.compiere.report.core;

import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.util.DisplayType;

/**
 *  Cell Renderer for Report Result Table
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ResultTableCellRenderer.java,v 1.2 2006/07/30 00:51:06 jjanke Exp $
 */
class ResultTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1491594061244117003L;


	/**
	 *  Constructor (extends Label)
	 *  @param rm
	 *  @param rc
	 */
	public ResultTableCellRenderer(RModel rm, RColumn rc)
	{
		m_rm = rm;
		m_rc = rc;
		int dt = m_rc.getDisplayType();
		//  Numbers
		if (DisplayType.isNumeric(dt))
		{
			super.setHorizontalAlignment(JLabel.TRAILING);
			m_nFormat = DisplayType.getNumberFormat(dt);
		}
		//  Dates
		else if (DisplayType.isDate(m_rc.getDisplayType()))
		{
			super.setHorizontalAlignment(JLabel.TRAILING);
			m_dFormat = DisplayType.getDateFormat(dt);
		}
		//
		else if (dt == DisplayType.YesNo)
		{
			m_check = new JCheckBox();
			m_check.setMargin(new Insets(0,0,0,0));
			m_check.setHorizontalAlignment(JLabel.CENTER);
		}
	}   //  ResultTableCellRenderer

	/** Report Column           */
	private RModel              m_rm = null;
	/** Report Column           */
	private RColumn             m_rc = null;
	/** Number Format           */
	private DecimalFormat       m_nFormat = null;
	/** Date Format             */
	private SimpleDateFormat    m_dFormat = null;
	/** Boolean renderer        */
	private JCheckBox           m_check;


	/**
	 *  Return Renderer Component
	 *  @param table
	 *  @param value
	 *  @param isSelected
	 *  @param hasFocus
	 *  @param row
	 *  @param col
	 *  @return renderer component
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int col)
	{
		//  Get Component
		Component c = m_check;
		if (c == null)  //  default JLabel
			c = super.getTableCellRendererComponent(table,value, isSelected,hasFocus, row,col);
		//  Background
		if (m_rm.isCellEditable(row, col))
			c.setBackground(AdempierePLAF.getFieldBackground_Normal());
		else
			c.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		//
		if (m_rm.isGroupRow(row))
			c.setFont(c.getFont().deriveFont(Font.BOLD));
		//  Value
		setValue (value);
		return c;
	}   //  getTableCellRendererComponent

	/**
	 *  Set Value
	 *  @param value
	 */
	protected void setValue (Object value)
	{
		//  Boolean
		if (m_check != null)
		{
			boolean sel = false;
			if (value != null && ((Boolean)value).booleanValue())
				sel = true;
			m_check.setSelected(sel);
			return;
		}

		//  JLabel
		if (value == null)
			setText("");
		else if (m_nFormat != null)
			try
			{
				setText(m_nFormat.format(value));
			}
			catch (Exception e)
			{
				setText(value.toString());
			}
		else if (m_dFormat != null)
			try
			{
				setText(m_dFormat.format(value));
			}
			catch (Exception e)
			{
				setText(value.toString());
			}
		else
			setText(value.toString());
	}   //  setValue

}   //  ResultTableCellRenderer
