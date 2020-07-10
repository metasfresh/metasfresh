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
package org.compiere.grid.ed;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import org.adempiere.images.Images;
import org.compiere.swing.CButton;
import org.compiere.swing.CTable;
import org.compiere.util.DisplayType;

/**
 *  Table Header Renderer based on Display Type for aligmnet
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: VHeaderRenderer.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public final class VHeaderRenderer implements TableCellRenderer
{
	public VHeaderRenderer() 
	{
		super();
		m_button = new CButton();
		m_button.setMargin(new Insets(0,0,0,0));
		m_button.putClientProperty("Plastic.is3D", Boolean.FALSE);
	}
	
	/**
	 *	Constructor
	 *  @param displayType
	 */
	public VHeaderRenderer(final int displayType)
	{
		super();
		//	Alignment
		m_alignment = getHorizontalAlignmentForDisplayType(displayType);
	}	//	VHeaderRenderer

	//  for 3D effect in Windows
	private CButton m_button; 
	
	private int m_alignment;

	/**
	 *	Get TableCell RendererComponent
	 *  @param table
	 *  @param value
	 *  @param isSelected
	 *  @param hasFocus
	 *  @param viewRowIndex
	 *  @param viewColumnIndex
	 *  @return Button
	 */
	@Override
	public Component getTableCellRendererComponent(final JTable table,
			final Object value,
			final boolean isSelected,
			final boolean hasFocus,
			final int viewRowIndex, final int viewColumnIndex)
	{
		final Icon columnSortIcon = getColumnSortIcon(table, viewColumnIndex);
		
		final TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
		Component headerComponent = headerRenderer == null ? null : headerRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, viewRowIndex, viewColumnIndex);
		if (headerComponent != null && headerComponent instanceof JComponent)
		{
			if (headerComponent instanceof JLabel)
			{
				final JLabel label = (JLabel)headerComponent;
				label.setHorizontalAlignment(m_alignment);
				if (value == null)
				{
					label.setText("");
					label.setPreferredSize(new Dimension(0,0));
				}
				else
				{
					label.setText(value.toString());
				}
				label.setIcon(columnSortIcon);
				label.setHorizontalTextPosition(SwingConstants.LEADING);
				
				return label;
			}
			m_button.setBorder(((JComponent)headerComponent).getBorder());
		}
		else
		{
			m_button.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		}
		
		if (value == null)
		{
			m_button.setPreferredSize(new Dimension(0,0));
			return m_button;
		}
		m_button.setText(value.toString());
		m_button.setIcon(columnSortIcon);
		m_button.setHorizontalTextPosition(SwingConstants.LEADING);
		return m_button;
	}	//	getTableCellRendererComponent

	/** @return column sort icon or null */
	private final Icon getColumnSortIcon(final JTable table, final int viewColumnIndex)
	{
		if (table instanceof CTable)
		{
			final CTable cTable = (CTable)table;
			final int modelColumnIndex = table.convertColumnIndexToModel(viewColumnIndex);
			final SortOrder sortOrder = cTable.getSortOrder(modelColumnIndex);
			if (sortOrder == SortOrder.ASCENDING)
			{
				return Images.getImageIcon2("uparrow");
			}
			else if (sortOrder == SortOrder.DESCENDING)
			{
				return Images.getImageIcon2("downarrow");
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * Gets {@link JLabel}'s horizontal alignment to be used for given display type.
	 * 
	 * @param displayType
	 * @return label's horizontal alignment or {@link SwingConstants#LEFT} in case the display type is not valid.
	 */
	public static int getHorizontalAlignmentForDisplayType(final int displayType)
	{
		if (DisplayType.isNumeric(displayType))
		{
			return JLabel.RIGHT;
		}
		else if (displayType == DisplayType.YesNo)
		{
			return JLabel.CENTER;
		}
		else if (DisplayType.isDate(displayType))
		{
			// NOTE: Right alignment required by http://dewiki908/mediawiki/index.php/05863_Fenster_Kommissionierung_-_bessere_Ausnutzung_Kn%C3%B6pfefelder_f%C3%BCr_Textausgabe_%28102244669218%29
			return JLabel.RIGHT;
		}
		else
		{
			return JLabel.LEFT;
		}
	}
}	//	VHeaderRenderer
