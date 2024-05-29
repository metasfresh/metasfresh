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

import org.compiere.util.DisplayType;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class SelectionColumnCellRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 1L;

	public SelectionColumnCellRenderer()
	{
		super();
		checkbox = new JCheckBox();
		checkbox.setMargin(new Insets(0, 0, 0, 0));
		checkbox.setHorizontalAlignment(SwingConstants.CENTER);
	}

	private final JCheckBox checkbox;

	/**
	 * Set Value (for multi-selection)
	 *
	 * @param value
	 */
	@Override
	protected void setValue(final Object value)
	{
		final boolean selected = DisplayType.toBooleanNonNull(value, false);
		checkbox.setSelected(selected);
	}   // setValue

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column)
	{
		setValue(value);
		return checkbox;
	}

}
