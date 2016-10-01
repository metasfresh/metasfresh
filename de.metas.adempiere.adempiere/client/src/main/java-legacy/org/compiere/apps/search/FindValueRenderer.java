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

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.grid.ed.VHeaderRenderer;
import org.compiere.model.MQuery.Operator;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Renderer for Find 'Value' Column. The value is how it would be used in a query, i.e. with '' for strings
 */
final class FindValueRenderer extends DefaultTableCellRenderer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7978049705045308657L;

	/**
	 * Constructor
	 *
	 * @param valueTo true if it is the "to" value column
	 */
	public FindValueRenderer(final boolean valueTo)
	{
		super();
		isValueToColumn = valueTo;
	}

	/** Value 2(to) */
	private final boolean isValueToColumn;

	private FindPanelSearchField currentSearchField;
	private boolean currentIsValueToEnabled = false;

	/** CheckBox */
	private JCheckBox m_checkbox = null;
	/** Logger */
	private static final transient Logger log = LogManager.getLogger(FindValueRenderer.class);

	private final boolean isValueDisplayed()
	{
		return !isValueToColumn || isValueToColumn && currentIsValueToEnabled;
	}

	/**
	 * Get Check Box
	 *
	 * @return check box
	 */
	private final JCheckBox getCheckbox()
	{
		if (m_checkbox == null)
		{
			m_checkbox = new JCheckBox();
			m_checkbox.setMargin(new Insets(0, 0, 0, 0));
			m_checkbox.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return m_checkbox;
	}	// getCheck

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int rowIndex, final int columnIndex)
	{
		final IUserQueryRestriction row = getRow(table, rowIndex);

		//
		// Column
		currentSearchField = FindPanelSearchField.castToFindPanelSearchField(row.getSearchField());

		//
		// Update valueTo enabled
		final Operator operator = row.getOperator();
		currentIsValueToEnabled = isValueToColumn && operator != null && operator.isRangeOperator();
		final boolean valueDisplayed = isEnabled();

		//
		// set Background
		if (valueDisplayed)
		{
			setBackground(AdempierePLAF.getFieldBackground_Normal());
		}
		else
		{
			setBackground(AdempierePLAF.getFieldBackground_Inactive());
		}

		//
		// If value is empty or the value is not enabled, go with the standard renderer
		if (value == null || !valueDisplayed)
		{
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, columnIndex);
		}

		//
		// If we deal with a YesNo value, render it as a checkbox
		final int displayType = currentSearchField == null ? 0 : currentSearchField.getDisplayType();
		if (displayType == DisplayType.YesNo)
		{
			final JCheckBox cb = getCheckbox();
			final boolean valueBoolean = DisplayType.toBoolean(value);
			cb.setSelected(valueBoolean);
			return cb;
		}

		//
		// For any other value type, go with the standard renderer
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, columnIndex);
	}

	/**
	 * Format Display Value
	 *
	 * NOTE: we assume this method is called exclusively from {@link #getTableCellRendererComponent(JTable, Object, boolean, boolean, int, int)}.
	 *
	 * @param value value
	 */
	@Override
	protected void setValue(final Object value)
	{
		if (value == null || !isValueDisplayed())
		{
			super.setValue(null);
			return;
		}

		//
		// Get DisplayType
		final int displayType;
		if (currentSearchField != null)
		{
			displayType = currentSearchField.getDisplayType();
		}
		else
		{
			log.error("FindValueRenderer.setValue (" + value + ") No search field selected");
			displayType = 0;
		}

		//
		// Set horizontal alignment
		setHorizontalAlignment(VHeaderRenderer.getHorizontalAlignmentForDisplayType(displayType));

		//
		// Get and set the display value
		final Object displayValue = getDisplayValue(value);
		super.setValue(displayValue);
	}	// setValue

	private final Object getDisplayValue(Object value)
	{
		// Strip ' '
		if (value != null)
		{
			String str = value.toString();
			if (str.startsWith("'") && str.endsWith("'"))
			{
				str = str.substring(1, str.length() - 1);
				value = str;
			}
		}

		//
		// Convert the value to display value, based on displayType
		//
		final int displayType = currentSearchField == null ? 0 : currentSearchField.getDisplayType();

		// Number
		if (DisplayType.isNumeric(displayType))
		{
			return DisplayType.getNumberFormat(displayType).format(value);
		}
		// Date
		else if (DisplayType.isDate(displayType))
		{
			if (value instanceof java.util.Date)
			{
				return DisplayType.getDateFormat(displayType).format(value);
			}
			else if (value instanceof String)	// JDBC format
			{
				try
				{
					final java.util.Date date = DisplayType.getDateFormat_JDBC().parse((String)value);
					return DisplayType.getDateFormat(displayType).format(date);
				}
				catch (final Exception e)
				{
					return value;
				}
			}
			else
			{
				return value;
			}
		}
		// Row ID
		else if (displayType == DisplayType.RowID)
		{
			return "";
		}
		// Lookup
		else if (DisplayType.isLookup(displayType) && currentSearchField != null)
		{
			return currentSearchField.getValueDisplay(value);
		}
		// other
		else
		{
			return value;
		}
	}

	private IUserQueryRestriction getRow(final JTable table, final int viewRowIndex)
	{
		final FindAdvancedSearchTableModel model = FindAdvancedSearchTableModel.cast(table.getModel());
		final int modelRowIndex = table.convertRowIndexToModel(viewRowIndex);
		return model.getRow(modelRowIndex);
	}
}
