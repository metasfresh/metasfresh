package de.metas.adempiere.form.terminal.table.swing;

/*
 * #%L
 * de.metas.swat.base
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


import javax.swing.table.TableModel;

import org.jdesktop.swingx.table.ColumnFactory;
import org.jdesktop.swingx.table.TableColumnExt;

/**
 * {@link ColumnFactory} extension used to set the the custom {@link TableColumnExt} properties from {@link SwingTerminalTableModelAdapter}.
 * 
 * @author tsa
 *
 */
final class SwingTerminalTable2ColumnFactory extends ColumnFactory
{
	public static final transient SwingTerminalTable2ColumnFactory instance = new SwingTerminalTable2ColumnFactory();

	private SwingTerminalTable2ColumnFactory()
	{
		super();
	}

	@Override
	public void configureTableColumn(final TableModel model, final TableColumnExt columnExt)
	{
		super.configureTableColumn(model, columnExt);

		//
		// Set TableColumn properties from underlying model adapter (if any)
		final SwingTerminalTableModelAdapter<?> tableModelAdapter = SwingTerminalTableModelAdapter.castOrNull(model);
		if (tableModelAdapter != null)
		{
			final int columnIndex = columnExt.getModelIndex();
			columnExt.setPrototypeValue(tableModelAdapter.getPrototypeValue(columnIndex));
		}
	}

}
