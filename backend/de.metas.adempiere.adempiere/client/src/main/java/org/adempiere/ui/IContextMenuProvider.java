package org.adempiere.ui;

import org.compiere.grid.VTable;
import org.compiere.grid.ed.VEditor;

import de.metas.util.ISingletonService;

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
