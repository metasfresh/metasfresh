package org.compiere.swing.table;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.logging.LogManager;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.grid.ed.VHeaderRenderer;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

final class AnnotatedTableCellRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 1L;

	// services
	private static final transient Logger logger = LogManager.getLogger(AnnotatedTableCellRenderer.class);

	/**
	 * Constructor for MiniGrid
	 * 
	 * @param displayType Display Type
	 */
	public AnnotatedTableCellRenderer(final int displayType)
	{
		super();
		this.displayType = displayType;
		setHorizontalAlignment(VHeaderRenderer.getHorizontalAlignmentForDisplayType(displayType));

		// Number
		if (DisplayType.isNumeric(displayType))
		{
			compCheckbox = null;
			numberFormat = DisplayType.getNumberFormat(displayType);
			dateFormat = null;
		}
		// Date
		else if (DisplayType.isDate(displayType))
		{
			compCheckbox = null;
			numberFormat = null;
			dateFormat = DisplayType.getDateFormat(displayType);
		}
		//
		else if (displayType == DisplayType.YesNo)
		{
			compCheckbox = new JCheckBox();
			compCheckbox.setMargin(new Insets(0, 0, 0, 0));
			compCheckbox.setHorizontalAlignment(JLabel.CENTER);
			compCheckbox.setOpaque(true);
			numberFormat = null;
			dateFormat = null;
		}
		else if (DisplayType.isText(displayType))
		{
			compCheckbox = null;
			numberFormat = null;
			dateFormat = null;
		}
		else
		{
			throw new IllegalArgumentException("DisplayType is not supported: " + displayType);
		}
	}

	private final int displayType;
	private boolean isPassword = false;
	//
	private SimpleDateFormat dateFormat = null;
	private DecimalFormat numberFormat = null;
	private final JCheckBox compCheckbox;

	@Override
	public Component getTableCellRendererComponent(final JTable table,
			final Object value,
			final boolean isSelected,
			final boolean hasFocus,
			final int rowIndexView,
			final int columnIndexView)
	{
		//
		final Component comp;
		if (displayType == DisplayType.YesNo)
		{
			comp = compCheckbox;
			compCheckbox.setVisible(true);
		}
		else
		{	// returns JLabel
			comp = super.getTableCellRendererComponent(table,
					null, // value=null because we are not interested in label's value at this point (note: this method will also call setValue()
					isSelected, hasFocus, rowIndexView, columnIndexView);
			comp.setFont(table.getFont());
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
			{
				bg = bg.darker();
			}
		}

		//
		// Check if the model suggests any special color to be used
		final TableModel tableModel = table.getModel();
		if (tableModel instanceof AnnotatedTableModel<?>)
		{
			final AnnotatedTableModel<?> annotatedTableModel = (AnnotatedTableModel<?>)tableModel;
			final int modelRowIndex= table.convertRowIndexToModel(rowIndexView);
			final int modelColumnIndex = table.convertColumnIndexToModel(columnIndexView);
			final Color modelForegroundColor = annotatedTableModel.getCellForegroundColor(modelRowIndex, modelColumnIndex);
			if (modelForegroundColor != null)
			{
				fg = modelForegroundColor;
			}
		}

		// Highlighted row
		if (isSelected)
		{
			// Windows is white on blue
			bg = table.getSelectionBackground();
			fg = table.getSelectionForeground();
			if (hasFocus)
			{
				bg = org.adempiere.apps.graph.GraphUtil.brighter(bg, .9);
			}
		}

		// Set Color
		comp.setBackground(bg);
		comp.setForeground(fg);
		//

		// Format it
		setValue(value);
		return comp;
	}

	/**
	 * Format Display Value. Default is JLabel
	 * 
	 * @param value (key)value
	 */
	@Override
	protected final void setValue(final Object value)
	{
		Object valueToSet;
		boolean forwardValue = true;
		try
		{
			// Checkbox
			if (displayType == DisplayType.YesNo)
			{
				compCheckbox.setSelected(DisplayType.toBooleanNonNull(value, false));
				valueToSet = null;
				forwardValue = false;
			}
			else if (value == null)
			{
				valueToSet = null;
			}
			// Number
			else if (DisplayType.isNumeric(displayType))
			{
				valueToSet = numberFormat.format(value);
			}
			// Date
			else if (DisplayType.isDate(displayType))
			{
				valueToSet = dateFormat.format(value);
			}
			// Row ID
			else if (displayType == DisplayType.RowID)
			{
				valueToSet = "";
			}
			// Password (fixed string)
			else if (isPassword)
			{
				valueToSet = "**********";
			}
			// other (String ...)
			else
			{
				valueToSet = value;
			}
		}
		catch (Exception e)
		{
			logger.error("(" + value + ") " + value.getClass().getName(), e);
			valueToSet = value.toString();
		}

		//
		// Forward the value to super
		if (forwardValue)
		{
			super.setValue(valueToSet);
		}
	}	// setValue

	/**
	 * to String
	 * 
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "[DisplayType=" + displayType + "]";
	}   // toString

	/**
	 * Sets precision to be used in case we are dealing with a number.
	 * 
	 * If not set, default displayType's precision will be used.
	 * 
	 * @param precision
	 */
	public void setPrecision(final int precision)
	{
		if (numberFormat != null)
		{
			numberFormat.setMinimumFractionDigits(precision);
			numberFormat.setMaximumFractionDigits(precision);
		}
	}
}	// VCellRenderer
