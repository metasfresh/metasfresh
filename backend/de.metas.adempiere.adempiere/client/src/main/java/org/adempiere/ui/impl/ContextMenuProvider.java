package org.adempiere.ui.impl;

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ui.IContextMenuAction;
import org.adempiere.ui.IContextMenuActionContext;
import org.adempiere.ui.IContextMenuProvider;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.grid.VTable;
import org.compiere.grid.ed.VEditor;
import org.compiere.model.GridField;
import org.compiere.model.I_AD_Field_ContextMenu;
import org.compiere.util.CCache;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.util.CacheCtx;

public class ContextMenuProvider implements IContextMenuProvider
{
	private final Logger logger = LogManager.getLogger(getClass());

	/** Cache: AD_Client_ID to ContextMenuKey to {@link IContextMenuAction} list */
	private final CCache<Integer, Map<ArrayKey, List<Class<? extends IContextMenuAction>>>> actionClassesForClient = new CCache<>(I_AD_Field_ContextMenu.Table_Name + "#Classes", 2);

	/** ContextMenuKey to {@link IContextMenuAction} list */
	private final Map<ArrayKey, List<Class<? extends IContextMenuAction>>> actionClassesManual = new HashMap<ArrayKey, List<Class<? extends IContextMenuAction>>>();

	private static final int DISPLAYTYPE_None = -100;
	private static final ArrayKey ACTIONCLASSKEY_Global = new ArrayKey(DISPLAYTYPE_None, null, null);

	public ContextMenuProvider()
	{
		super();
		// Here we can manually add actions to every context menu.
	}

	@Override
	public void registerActionClass(Class<? extends IContextMenuAction> actionClass)
	{
		registerActionClass(ACTIONCLASSKEY_Global, actionClass);
	}

	@Override
	public void registerActionClass(int displayType, Class<? extends IContextMenuAction> actionClass)
	{
		Check.assume(displayType > 0, "displayType > 0");
		final ArrayKey key = createActionClassKey(displayType, -1, -1);
		registerActionClass(key, actionClass);
	}

	@Override
	public void registerActionClass(String tableName, String columnName, Class<? extends IContextMenuAction> actionClass)
	{
		Check.assume(tableName != null, "tableName not null");
		Check.assume(columnName != null, "columnName not null");

		final int adColumnId = Services.get(IADTableDAO.class).retrieveColumn(tableName, columnName).getAD_Column_ID();
		final int adFieldId = -1;
		final ArrayKey key = createActionClassKey(DISPLAYTYPE_None, adColumnId, adFieldId);

		registerActionClass(key, actionClass);
	}

	private ArrayKey createActionClassKey(int displayType, int adColumnId, int adFieldId)
	{
		return new ArrayKey(displayType, adColumnId, adFieldId);
	}

	private void registerActionClass(final ArrayKey key, final Class<? extends IContextMenuAction> actionClass)
	{
		if (actionClass == null)
		{
			return;
		}

		List<Class<? extends IContextMenuAction>> list = actionClassesManual.get(key);
		if (list == null)
		{
			list = new ArrayList<Class<? extends IContextMenuAction>>();
			actionClassesManual.put(key, list);
		}
		if (!list.contains(actionClass))
		{
			list.add(actionClass);
		}
	}

	@Override
	public IContextMenuAction getRootContextMenuAction(final IContextMenuActionContext menuCtx)
	{
		Check.assumeNotNull(menuCtx, "menuCtx not null");

		final VEditor editor = menuCtx.getEditor();
		if (editor == null)
		{
			return RootContextMenuAction.EMPTY;
		}

		final GridField gridField = editor.getField();
		if (gridField == null)
		{
			return RootContextMenuAction.EMPTY;
		}

		final Properties ctx = menuCtx.getCtx();
		final List<Class<? extends IContextMenuAction>> actionClasses = new ArrayList<>();
		addActionClassesToList(ctx, gridField, actionClasses, ACTIONCLASSKEY_Global);
		addActionClassesToList(ctx, gridField, actionClasses, createActionClassKey(gridField.getDisplayType(), -1, -1));
		addActionClassesToList(ctx, gridField, actionClasses, createActionClassKey(DISPLAYTYPE_None, gridField.getAD_Column_ID(), -1));
		addActionClassesToList(ctx, gridField, actionClasses, createActionClassKey(DISPLAYTYPE_None, -1, gridField.getVO().AD_Field_ID));

		final List<IContextMenuAction> actions = getInstances(menuCtx, actionClasses);
		final IContextMenuAction rootAction = new RootContextMenuAction(actions);
		return rootAction;
	}

	private void addActionClassesToList(final Properties ctx, final GridField gridField, final List<Class<? extends IContextMenuAction>> list, final ArrayKey key)
	{
		final Map<ArrayKey, List<Class<? extends IContextMenuAction>>> actionClasses = getActionsClassesForClient(ctx);
		addActionClassesToList(list, key, actionClasses);
		addActionClassesToList(list, key, actionClassesManual);
	}

	private Map<ArrayKey, List<Class<? extends IContextMenuAction>>> getActionsClassesForClient(final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final Map<ArrayKey, List<Class<? extends IContextMenuAction>>> actionClasses = actionClassesForClient.get(adClientId
				, new Callable<Map<ArrayKey, List<Class<? extends IContextMenuAction>>>>()
		{

			@Override
			public Map<ArrayKey, List<Class<? extends IContextMenuAction>>> call() throws Exception
			{
				return retrieveActionsClassesForClient(ctx, adClientId);
			}
		});
		
		if (actionClasses == null)
		{
			return ImmutableMap.of();
		}
		return actionClasses;
	}
	
	private final Map<ArrayKey, List<Class<? extends IContextMenuAction>>> retrieveActionsClassesForClient(final Properties ctx, final int adClientId)
	{
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final Map<ArrayKey, List<Class<? extends IContextMenuAction>>> loadActionClasses = new HashMap<>();
		for (final I_AD_Field_ContextMenu item : retrieveContextMenuForClient(ctx, adClientId))
		{
			if (!item.isActive())
			{
				continue;
			}

			final String className = item.getClassname();
			try
			{
				@SuppressWarnings("unchecked")
				final Class<? extends IContextMenuAction> clazz = (Class<? extends IContextMenuAction>)classLoader.loadClass(className);

				final ArrayKey key = createActionClassKeyFromItem(item);
				List<Class<? extends IContextMenuAction>> list = loadActionClasses.get(key);
				if (list == null)
				{
					list = new ArrayList<Class<? extends IContextMenuAction>>();
					loadActionClasses.put(key, list);
				}
				if (!list.contains(clazz))
				{
					list.add(clazz);
				}
			}
			catch (final Exception e)
			{
				logger.warn("Could not load class for " + item + " (Classname:" + className + ")", e);
			}
		}
		return loadActionClasses;
	}

	private ArrayKey createActionClassKeyFromItem(final I_AD_Field_ContextMenu item)
	{
		if (item.getAD_Field_ID() > 0)
		{
			return createActionClassKey(DISPLAYTYPE_None, -1, item.getAD_Field_ID());
		}
		else if (item.getAD_Column_ID() > 0)
		{
			return createActionClassKey(DISPLAYTYPE_None, item.getAD_Column_ID(), -1);
		}
		else if (item.getAD_Reference_ID() > 0)
		{
			return createActionClassKey(item.getAD_Reference_ID(), -1, -1);
		}
		else
		{
			return ACTIONCLASSKEY_Global;
		}
	}

	private void addActionClassesToList(final List<Class<? extends IContextMenuAction>> list, final ArrayKey key, final Map<ArrayKey, List<Class<? extends IContextMenuAction>>> actionClasses)
	{
		List<Class<? extends IContextMenuAction>> classesList = actionClasses.get(key);
		if (classesList == null || classesList.isEmpty())
		{
			return;
		}

		for (Class<? extends IContextMenuAction> actionClass : classesList)
		{
			if (!list.contains(actionClass))
			{
				list.add(actionClass);
			}
		}
	}

	private List<IContextMenuAction> getInstances(final IContextMenuActionContext menuCtx, final List<Class<? extends IContextMenuAction>> actionClasses)
	{
		if (actionClasses == null || actionClasses.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<IContextMenuAction> result = new ArrayList<IContextMenuAction>();
		for (final Class<? extends IContextMenuAction> actionClass : actionClasses)
		{
			final IContextMenuAction action = getInstance(menuCtx, actionClass);
			if (action != null)
			{
				result.add(action);
			}
		}
		return result;
	}

	private IContextMenuAction getInstance(final IContextMenuActionContext menuCtx, final Class<? extends IContextMenuAction> actionClass)
	{
		try
		{
			final IContextMenuAction action = actionClass.newInstance();
			action.setContext(menuCtx);

			if (!action.isAvailable())
			{
				return null;
			}

			return action;
		}
		catch (Exception e)
		{
			logger.warn("Cannot create action for " + actionClass + ": " + e.getLocalizedMessage() + " [SKIP]", e);
			return null;
		}
	}

	// @Cached(cacheName = I_AD_Field_ContextMenu.Table_Name + "#By#AD_Client_ID") // not needed, we are caching the classname lists
	private final List<I_AD_Field_ContextMenu> retrieveContextMenuForClient(@CacheCtx final Properties ctx, final int adClientId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Field_ContextMenu.class, ctx, ITrx.TRXNAME_None)
				.addInArrayOrAllFilter(I_AD_Field_ContextMenu.COLUMN_AD_Client_ID, 0, adClientId)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				.addColumn(I_AD_Field_ContextMenu.COLUMN_SeqNo)
				.addColumn(I_AD_Field_ContextMenu.COLUMN_AD_Field_ContextMenu_ID)
				.endOrderBy()
				//
				.create()
				.list(I_AD_Field_ContextMenu.class);
	}

	@Override
	public IContextMenuActionContext createContext(final VEditor editor)
	{
		final VTable vtable = null;
		final int viewRow = IContextMenuActionContext.ROW_NA;
		final int viewColumn = IContextMenuActionContext.COLUMN_NA;
		return createContext(editor, vtable, viewRow, viewColumn);
	}

	@Override
	public IContextMenuActionContext createContext(final VEditor editor, final VTable vtable, final int rowIndexView, final int columnIndexView)
	{
		return new DefaultContextMenuActionContext(editor, vtable, rowIndexView, columnIndexView);
	}
}
