package de.schaeffer.compiere.tools;

/*
 * #%L
 * de.metas.banking.swingui
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


import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TableHelper {
	/*
	 * Serviceklassen und -methoden fuer die Tabellendarstellung
	 */

	/**
	 * Sets the optimal column widths. And sets the height to ADempiere standard.
	 * 
	 * @param table
	 */
	public static void initColumnSizes(JTable table) {
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			final TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(getPreferredWidthForColumn(column, table));
		}
		TableHelper.initRowHeight(table);
	}
	
	/**
	 * Sets the row height to fontsize + 8 (ADempiere table standard)
	 * @param table
	 */
	public static void initRowHeight(JTable table) {
		table.setRowHeight(table.getFont().getSize() + 8);
	}

	/**
	 * Returns the prefered width for the given TableColumn.
	 * 
	 * @param col
	 * @param table
	 * @return
	 */
	public static int getPreferredWidthForColumn(TableColumn col, JTable table) {
		final int hw = columnHeaderWidth(col, table);
		final int cw = widestCellInColumn(col, table); // cw = column width

		return hw > cw ? hw : cw;
	}

	/**
	 * Returns the width of the given columns header.
	 * 
	 * @param col
	 * @param table
	 * @return
	 */
	public static int columnHeaderWidth(TableColumn col, JTable table) {
		TableCellRenderer renderer = col.getHeaderRenderer();
		if (renderer == null) {
			renderer = table.getTableHeader().getDefaultRenderer();
		}

		final Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(),
				false, false, 0, 0);

		return comp.getPreferredSize().width + 3;
	}

	/**
	 * Returns the width of the widest cell in the given column.
	 * 
	 * @param col
	 * @param table
	 * @return
	 */
	public static int widestCellInColumn(TableColumn col, JTable table) {
		final int c = col.getModelIndex();
		int width = 0;
		int maxw = 0;

		for (int r = 0; r < table.getRowCount(); ++r) {
			final TableCellRenderer renderer = table.getCellRenderer(r, c);

			final Component comp = renderer.getTableCellRendererComponent(table, table.getValueAt(
					r, c), false, false, r, c);

			width = comp.getPreferredSize().width;
			maxw = width > maxw ? width : maxw;
		}
		return maxw + 3;
	}

}
