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


import org.adempiere.util.ISingletonService;
import org.compiere.grid.VTable;
import org.compiere.grid.ed.VEditor;

public interface IContextMenuProvider extends ISingletonService
{
	
	/**
	 * Manually add an action to the context menu.
	 * 
	 * @param actionClass
	 */
	void registerActionClass(Class<? extends IContextMenuAction> actionClass);

	/**
	 * Manually add an action to the context menu.
	 * 
	 * @param displayType
	 * @param actionClass
	 */
	void registerActionClass(int displayType, Class<? extends IContextMenuAction> actionClass);

	/**
	 * Manually add an action to the context menu.
	 * 
	 * @param tableName
	 * @param columnName
	 * @param actionClass
	 */
	void registerActionClass(String tableName, String columnName, Class<? extends IContextMenuAction> actionClass);

	/** @return root menu action; never returns null */
	IContextMenuAction getRootContextMenuAction(IContextMenuActionContext menuCtx);

	IContextMenuActionContext createContext(VEditor editor);

	IContextMenuActionContext createContext(VEditor editor, VTable vtable, int rowIndexView, int columnIndexView);
}
