package org.adempiere.ui;

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


import java.util.Properties;

import org.compiere.grid.VTable;
import org.compiere.grid.ed.VEditor;

public interface IContextMenuActionContext
{
	int ROW_NA = -1;
	int COLUMN_NA = -1;

	/** @return context; never returns null */
	Properties getCtx();

	/** @return editor which triggered the context menu popup; never returns null */
	VEditor getEditor();

	/**
	 * Gets grid mode table.
	 * 
	 * It is available only if the user called the context menu while in grid mode.
	 * 
	 * @return grid mode table or <code>null</code> if not available.
	 */
	VTable getVTable();

	/**
	 * Gets the grid mode table's row (view) index.
	 * 
	 * It is available only if the user called the context menu while in grid mode.
	 * 
	 * @return row index or {@link #ROW_NA}
	 */
	int getViewRow();

	/**
	 * Gets the grid mode table's column (view) index.
	 * 
	 * It is available only if the user called the context menu while in grid mode.
	 * 
	 * @return column index or {@link #COLUMN_NA}
	 */
	int getViewColumn();
}
