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

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.adempiere.ad.ui.ITable;
import org.adempiere.ad.ui.ITableColorProvider;
import org.adempiere.ad.ui.NullTableColorProvider;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Check;
import org.adempiere.util.GridRowCtx;
import org.adempiere.util.Services;
import org.compiere.grid.GridController;
import org.compiere.model.GridField;
import org.compiere.model.GridTabLayoutMode;
import org.compiere.model.Lookup;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * Table Cell Renderer based on DisplayType
 * 
 * @author Jorg Janke
 * @version $Id: VCellRenderer.java,v 1.4 2006/07/30 00:51:27 jjanke Exp $
 * 
 * @author Teo Sarca <li>FR [ 2866571 ] VCellRenderer: implement getters https://sourceforge.net/tracker/?func=detail&aid=2866571&group_id=176962&atid=879335
 */
public final class VCellRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3135422746697244864L;
	
	/** Logger */
	private static Logger logger = LogManager.getLogger(VCellRenderer.class);

	/**
	 * Constructor for Grid
	 * 
	 * @param mField field model
	 */
	public VCellRenderer(final GridField mField)
	{
		this(mField.getDisplayType());
		m_field = mField; // metas
		m_columnName = mField.getColumnName();
		this.setName(m_columnName);
		m_lookup = mField.getLookup();
		m_password = mField.isEncryptedField();
		// metas-2009_0021_AP1_CR053: begin
		m_field = mField;
		if (m_displayType == DisplayType.Button)
		{
			m_button = (VButton)VEditorFactory.getEditor(mField, true);
		}
		// metas-2009_0021_AP1_CR053: end
		
		final String formatPattern = mField.getVO().getFormatPattern();
		if (!Check.isEmpty(formatPattern, true))
		{
			try
			{
				this.m_numberFormat = new DecimalFormat(formatPattern);
			}
			catch (Exception e)
			{
				logger.warn("Invalid decimal format '" + formatPattern + "' for field " + mField, e);
			}
		}

	}	// VCellRenderer

	/**
	 * Constructor for MiniGrid
	 * 
	 * @param displayType Display Type
	 */
	public VCellRenderer(final int displayType)
	{
		super();
		m_displayType = displayType;
		setHorizontalAlignment(VHeaderRenderer.getHorizontalAlignmentForDisplayType(displayType));
		// Number
		if (DisplayType.isNumeric(m_displayType))
		{
			m_numberFormat = DisplayType.getNumberFormat(m_displayType);
		}
		// Date
		else if (DisplayType.isDate(m_displayType))
		{
			m_dateFormat = DisplayType.getDateFormat(m_displayType);
		}
		//
		else if (m_displayType == DisplayType.YesNo)
		{
			m_check = new JCheckBox();
			m_check.setMargin(new Insets(0, 0, 0, 0));
			m_check.setHorizontalAlignment(JLabel.CENTER);
			m_check.setOpaque(true);
		}
	}   // VCellRenderer

	private final int m_displayType;
	private String m_columnName = null;
	private Lookup m_lookup = null;
	private boolean m_password = false;
	//
	private SimpleDateFormat m_dateFormat = null;
	private DecimalFormat m_numberFormat = null;
	private JCheckBox m_check = null;
	private GridField m_field = null; // metas-2009_0021_AP1_CR053
	private VButton m_button = null; // metas-2009_0021_AP1_CR053

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(VCellRenderer.class);

	/**
	 * Get TableCell RendererComponent.
	 * 
	 * @param table table
	 * @param value value
	 * @param isSelected selected
	 * @param hasFocus focus
	 * @param row row
	 * @param col col
	 * @return component
	 */
	@Override
	public Component getTableCellRendererComponent(final JTable table,
			final Object value,
			final boolean isSelected,
			final boolean hasFocus,
			final int rowIndexView,
			final int columnIndexView)
	{
		final int rowIndexModel = table.convertRowIndexToModel(rowIndexView);

		Component c = null;

		// metas-2009_0021_AP1_CR053: begin
		final GridRowCtx rowCtx;
		final boolean isDisplayed;
		final boolean isEditable;
		final IValidationContext validationCtx;
		if (m_field != null)
		{
			rowCtx = new GridRowCtx(Env.getCtx(), m_field.getGridTab(), rowIndexModel);
			isDisplayed = m_field.isDisplayed(rowCtx, GridTabLayoutMode.Grid);
			isEditable = m_field.isEditable(rowCtx, GridTabLayoutMode.Grid);
			if (isEditable)
			{
				// NOTE: we display the right value (even if is not validated), even when field is editable
				// see 04764 - G01T080 
				//validationCtx = Services.get(IValidationRuleFactory.class).createValidationContext(m_field, rowIndexModel);
				validationCtx = IValidationContext.DISABLED;
			}
			else
			{
				// NOTE: in case field is read-only we want to display the right value anyways
				// see 04764 - G01T070 
				validationCtx = IValidationContext.DISABLED;
			}
		}
		else
		{
			rowCtx = null;
			isDisplayed = true;
			isEditable = false;
			validationCtx = IValidationContext.DISABLED;
		}
		//
		if (!isDisplayed)
		{
			c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndexView, columnIndexView);
			c.setFont(table.getFont());
		}
		else if (m_displayType == DisplayType.Button && m_button != null && m_field != null)
		{
			c = m_button;
			m_button.setVisible(isDisplayed);
			m_button.setReadWrite(isEditable);
		}
		// metas-2009_0021_AP1_CR053: end
		else if (m_displayType == DisplayType.YesNo)
		{
			c = m_check;
			m_check.setVisible(isDisplayed);
		}
		else
		{	// returns JLabel
			c = super.getTableCellRendererComponent(table,
					null, // value=null because we are not interested in label's value at this point (note: this method will also call setValue()
					isSelected, hasFocus, rowIndexView, columnIndexView);
			// c.setFont(AdempierePLAF.getFont_Field());
			c.setFont(table.getFont());
		}

		// Background & Foreground
		Color bg = AdempierePLAF.getFieldBackground_Normal();
		Color fg = AdempierePLAF.getTextColor_Normal();
		// Inactive Background
		final boolean ro = !table.isCellEditable(rowIndexView, columnIndexView);
		if (ro)
		{
			bg = AdempierePLAF.getFieldBackground_Inactive();
			if (isSelected && !hasFocus)
				bg = bg.darker();
		}

		if (table instanceof ITable)
		{
			final ITable itable = (ITable)table;
			final ITableColorProvider colorProvider = itable.getColorProvider();
			
			final Color colorProviderFg = colorProvider.getForegroundColor(itable, rowIndexModel);
			if (colorProviderFg != null && colorProviderFg != ITableColorProvider.COLOR_NONE)
			{
				fg = colorProviderFg;
			}
			
			final Color colorProviderBg = colorProvider.getBackgroundColor(itable, rowIndexModel);
			if (colorProviderBg != null && colorProviderBg != ITableColorProvider.COLOR_NONE)
			{
				bg = colorProviderBg;
			}
		}

		// Highlighted row
		if (isSelected)
		{
			// Windows is white on blue
			bg = table.getSelectionBackground();
			fg = table.getSelectionForeground();
			if (hasFocus)
				bg = org.adempiere.apps.graph.GraphUtil.brighter(bg, .9);
		}
		// metas-2009_0021_AP1_CR045: teo_sarca: begin
		if (m_field != null && rowCtx != null)
		{
			final Color color = GridController.getColor(rowCtx, m_field);
			if (color != null)
			{
				bg = color;
			}
		}
		// metas-2009_0021_AP1_CR045: teo_sarca: end
		// Set Color
		c.setBackground(bg);
		c.setForeground(fg);
		//

		final Object valueToDisplay;
		if (isDisplayed)
		{
			valueToDisplay = value;
		}
		else
		{
			// In case the value is not displayed we just set the JLabel value to NULL.
			// NOTE: setting "c.setVisible(isDisplayed) does not work because the "setVisible(true)" will be invoked by parent invoker.
			// More we want to display the component because we just set the colors
			valueToDisplay = null;
		}

		// Format it
		setValue(validationCtx, valueToDisplay);
		return c;
	}	// getTableCellRendererComponent

	/**
	 * Format Display Value. Default is JLabel
	 * 
	 * @param value (key)value
	 */
	@Override
	protected void setValue(final Object value)
	{
		setValue(IValidationContext.DISABLED, value);
	}
	
	private void setValue(final IValidationContext validationCtx, final Object value)
	{
		String retValue = null;
		try
		{
			// Checkbox
			if (m_displayType == DisplayType.YesNo)
			{
				if (value instanceof Boolean)
					m_check.setSelected(((Boolean)value).booleanValue());
				else
					m_check.setSelected("Y".equals(value));
				return;
			}
			// metas-2009_0021_AP1_CR053: begin
			else if (m_displayType == DisplayType.Button && m_button != null)
			{
				m_button.setValue(value);
			}
			// metas-2009_0021_AP1_CR053: end
			else if (value == null)
			{
				retValue = null;
			}
			// Number
			else if (DisplayType.isNumeric(m_displayType))
			{
				retValue = m_numberFormat.format(value);
			}
			// Date
			else if (DisplayType.isDate(m_displayType))
			{
				retValue = m_dateFormat.format(value);
			}
			// Row ID
			else if (m_displayType == DisplayType.RowID)
			{
				retValue = "";
			}
			// Lookup
			else if (m_lookup != null && DisplayType.isAnyLookup(m_displayType))
			{
				retValue = m_lookup.getDisplay(validationCtx, value);
			}
			// Button
			else if (m_displayType == DisplayType.Button)
			{
				if (Services.get(IColumnBL.class).isRecordColumnName(m_columnName))
					retValue = "#" + value + "#";
				else
					retValue = null;
			}
			// Password (fixed string)
			else if (m_password)
			{
				retValue = "**********";
			}
			// other (String ...)
			else
			{
				super.setValue(value);
				return;
			}
		}
		catch (Exception e)
		{
			log.error("(" + value + ") " + value.getClass().getName(), e);
			retValue = value.toString();
		}
		super.setValue(retValue);
	}	// setValue

	/**
	 * to String
	 * 
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		return "VCellRenderer[" + m_columnName
				+ ",DisplayType=" + m_displayType + " - " + m_lookup + "]";
	}   // toString

	/**
	 * Dispose
	 */
	public void dispose()
	{
		if (m_lookup != null)
			m_lookup.dispose();
		m_lookup = null;
	}	// dispose

	public String getColumnName()
	{
		return m_columnName;
	}

	public Lookup getLookup()
	{
		return m_lookup;
	}

	public int getDisplayType()
	{
		return m_displayType;
	}

	public boolean isPassword()
	{
		return m_password;
	}
	
	public ITableColorProvider getColorProvider(final JTable table)
	{
		if (table instanceof ITable)
		{
			final ITableColorProvider colorProvider = ((ITable)table).getColorProvider();
			return colorProvider;
		}
		else
		{
			return NullTableColorProvider.instance;
		}
	}

	/**
	 * Sets precision to be used in case we are dealing with a number.
	 * 
	 * If not set, default displayType's precision will be used.
	 * 
	 * @param precision
	 */
	public void setPrecision(final int precision)
	{
		if (m_numberFormat != null)
		{
			m_numberFormat.setMinimumFractionDigits(precision);
			m_numberFormat.setMaximumFractionDigits(precision);
		}
	}
}	// VCellRenderer
