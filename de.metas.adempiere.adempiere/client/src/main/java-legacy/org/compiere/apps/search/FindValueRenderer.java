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

import java.awt.Component;
import java.awt.Insets;
import java.sql.Date;
import java.util.logging.Level;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.model.GridField;
import org.compiere.model.Lookup;
import org.compiere.model.MQuery;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.ValueNamePair;

/**
 *	Renderer for Find 'Value' Column.
 *	The value is how it would be used in a query, i.e. with '' for strings
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: FindValueRenderer.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class FindValueRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5290173817551899865L;

	/**
	 *	Constructor
	 *  @param find find
	 *  @param valueTo true if it is the "to" value column
	 */
	public FindValueRenderer (Find find, boolean valueTo)
	{
		super();
		m_find = find;
		m_valueToColumn = valueTo;
	}	//	FindValueRenderer

	/** Find Window             */
	private Find			m_find;
	/** Value 2(to)             */
	private boolean         m_valueToColumn;
	/**	Between selected		*/
	private boolean			m_between = false;

	/** Current Row             */
	private volatile String	m_columnName = null;
	/** CheckBox                */
	private JCheckBox       m_check = null;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(FindValueRenderer.class);

	/**
	 * 	Get Check Box
	 *	@return check box
	 */
	private JCheckBox getCheck()
	{
		if (m_check == null)
		{
			m_check = new JCheckBox();
			m_check.setMargin(new Insets(0,0,0,0));
			m_check.setHorizontalAlignment(JLabel.CENTER);
		}
		return m_check;
	}	//	getCheck


	/*************************************************************************
	 *	Get TableCell RendererComponent
	 *  @param table table
	 *  @param value value
	 *  @param isSelected selected
	 *  @param hasFocus focus
	 *  @param row row
	 *  @param col col
	 *  @return renderer component (Label or CheckBox)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
	{
	//	log.config( "FindValueRenderer.getTableCellRendererComponent", "r=" + row + ", c=" + col );
		//	Column
		m_columnName = null;
		Object column = table.getModel().getValueAt(row, FindPanel.INDEX_COLUMNNAME);
		if (column != null)
		{
			if (column instanceof ValueNamePair)
				m_columnName = ((ValueNamePair)column).getValue();
			else
				m_columnName = column.toString();
		}

		//	Between - enables valueToColumn
		m_between = false;
		Object betweenValue = table.getModel().getValueAt(row, FindPanel.INDEX_OPERATOR);
		if (m_valueToColumn && betweenValue != null 
			&& betweenValue.equals(MQuery.OPERATORS[MQuery.BETWEEN_INDEX]))
			m_between = true;
		boolean enabled = !m_valueToColumn || (m_valueToColumn && m_between); 

		//	set Background
		if (enabled)
			setBackground(AdempierePLAF.getFieldBackground_Normal());
		else
			setBackground(AdempierePLAF.getFieldBackground_Inactive());

	//	log.config( "FindValueRenderer.getTableCellRendererComponent - (" + value + ") - Enabled=" + enabled);

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		if (value == null || (m_valueToColumn && !m_between))
			return c;
		//
		GridField field = getMField();
		if (field != null && field.getDisplayType() == DisplayType.YesNo)
		{
			JCheckBox cb = getCheck();
			if (value instanceof Boolean)
				cb.setSelected(((Boolean)value).booleanValue());
			else
				cb.setSelected(value.toString().indexOf('Y') != -1);
			return cb;
		}
		return c;
	}	//	getTableCellRendererComponent

	
	/**************************************************************************
	 *	Format Display Value
	 *  @param value value
	 */
	@Override
	protected void setValue(Object value)
	{
		boolean enabled = !m_valueToColumn || (m_valueToColumn && m_between); 
	//	Log.trace (Log.l4_Data, "FindValueRenderer.setValue (" + value + ") - Enabled=" + enabled);
		if (value == null || !enabled)
		{
			super.setValue(null);
			return;
		}

		String retValue = null;

		//	Strip ' '
		if (value != null)
		{
			String str = value.toString();
			if (str.startsWith("'") && str.endsWith("'"))
			{
				str = str.substring(1, str.length()-1);
				value = str;
			}
		}

		int displayType = 0;
		GridField field = getMField();
		if (field != null)
			displayType = field.getDisplayType();
		else
			log.log(Level.SEVERE, "FindValueRenderer.setValue (" + value + ") ColumnName=" + m_columnName + " No Target Column");

		setHorizontalAlignment(JLabel.LEFT);
		//	Number
		if (DisplayType.isNumeric(displayType))
		{
			setHorizontalAlignment(JLabel.RIGHT);
			retValue = DisplayType.getNumberFormat(displayType).format(value);
		}
		//	Date
		else if (DisplayType.isDate(displayType))
		{
			if (value instanceof Date)
			{
				retValue = DisplayType.getDateFormat(displayType).format(value);
				setHorizontalAlignment(JLabel.RIGHT);
			}
			else if (value instanceof String)	//	JDBC format
			{
				try
				{
					java.util.Date date = DisplayType.getDateFormat_JDBC().parse((String)value);
					retValue = DisplayType.getDateFormat(displayType).format(date);
					setHorizontalAlignment(JLabel.RIGHT);
				}
				catch (Exception e)
				{
				//	log.log(Level.SEVERE, "FindValueRenderer.setValue", e);
					retValue = value.toString();
				}
			}
			else
				retValue = value.toString();
		}
		//	Row ID
		else if (displayType == DisplayType.RowID)
			retValue = "";
		//	Lookup
		else if (DisplayType.isLookup(displayType) && field != null)
		{
			Lookup lookup = field.getLookup();
			if (lookup != null)
				retValue = lookup.getDisplay(value);
		}
		//	other
		else
		{
			super.setValue(value);
			return;
		}
	//	log.config( "FindValueRenderer.setValue (" + retValue + ") - DT=" + displayType);
		super.setValue(retValue);
	}	//	setValue

	/**
	 * 	Get MField
	 * 	@return field
	 */
	protected GridField getMField()
	{
		return m_find.getTargetMField(m_columnName);
	}	//	getMField

	public String getColumnName()
	{
		return m_columnName;
	}
}	//	FindValueRenderer
