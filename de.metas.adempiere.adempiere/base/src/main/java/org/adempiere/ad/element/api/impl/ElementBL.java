package org.adempiere.ad.element.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.ElementChangedEvent;
import org.adempiere.ad.element.api.IElementBL;
import org.adempiere.ad.element.api.IElementDAO;
import org.adempiere.ad.menu.api.IADMenuDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_Window;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ElementBL implements IElementBL
{

	private static final Logger log = LogManager.getLogger(ElementBL.class);

	@Override
	public void updateUIElement(final I_AD_Element element)
	{
		final List<I_AD_UI_Element> uiElements = Services.get(IElementDAO.class).retrieveChildUIElements(element);

		String widgetSize = element.getWidgetSize();

		if (Check.isEmpty(widgetSize))
		{
			widgetSize = null;
		}

		for (final I_AD_UI_Element uiElement : uiElements)
		{
			uiElement.setWidgetSize(widgetSize);
			InterfaceWrapperHelper.save(uiElement);
		}
	}

	@Override
	public void createAndAssignElementsToApplicationDictionaryEntries()
	{
		createAndLinkElementsForTabs();
		createAndLinkElementsForWindows();
		createAndLinkElementsForMenus();

	}

	private void createAndLinkElementsForTabs()
	{
		final List<I_AD_Tab> tabsWithMissingElements = Services.get(IADWindowDAO.class).retrieveTabsWithMissingElements();

		for (final I_AD_Tab tab : tabsWithMissingElements)
		{
			final I_AD_Element element = newInstance(I_AD_Element.class);
			element.setName(tab.getName());
			element.setPrintName(tab.getName());
			element.setDescription(tab.getDescription());
			element.setHelp(tab.getHelp());
			element.setCommitWarning(tab.getCommitWarning());
			save(element);

			final AdElementId elementId = AdElementId.ofRepoId(element.getAD_Element_ID());

			Services.get(IElementTranslationBL.class).updateElementTranslationsFromTab(elementId, tab.getAD_Tab_ID());

			IADWindowDAO.DYNATTR_AD_Tab_UpdateTranslations.setValue(tab, false);

			tab.setAD_Element_ID(elementId.getRepoId());
			save(tab);
		}
	}

	private void createAndLinkElementsForWindows()
	{
		final List<I_AD_Window> windowsWithMissingElements = Services.get(IADWindowDAO.class).retrieveWindowsWithMissingElements();

		for (final I_AD_Window window : windowsWithMissingElements)
		{
			final I_AD_Element element = newInstance(I_AD_Element.class);
			element.setName(window.getName());
			element.setPrintName(window.getName());
			element.setDescription(window.getDescription());
			element.setHelp(window.getHelp());
			save(element);

			final AdElementId elementId = AdElementId.ofRepoId(element.getAD_Element_ID());

			Services.get(IElementTranslationBL.class).updateElementTranslationsFromWindow(elementId, window.getAD_Window_ID());

			IADWindowDAO.DYNATTR_AD_Window_UpdateTranslations.setValue(window, false);

			window.setAD_Element_ID(elementId.getRepoId());
			save(window);
		}
	}

	private void createAndLinkElementsForMenus()
	{
		final List<I_AD_Menu> menusWithMissingElements = Services.get(IADMenuDAO.class).retrieveMenusWithMissingElements();

		for (final I_AD_Menu menu : menusWithMissingElements)
		{
			final I_AD_Element element = newInstance(I_AD_Element.class);
			element.setName(menu.getName());
			element.setPrintName(menu.getName());
			element.setDescription(menu.getDescription());
			element.setWEBUI_NameBrowse(menu.getWEBUI_NameBrowse());
			element.setWEBUI_NameNew(menu.getWEBUI_NameNew());
			element.setWEBUI_NameNewBreadcrumb(menu.getWEBUI_NameNewBreadcrumb());
			save(element);

			final AdElementId elementId = AdElementId.ofRepoId(element.getAD_Element_ID());

			Services.get(IElementTranslationBL.class).updateElementTranslationsFromMenu(elementId, menu.getAD_Menu_ID());

			IADMenuDAO.DYNATTR_AD_Menu_UpdateTranslations.setValue(menu, false);

			menu.setAD_Element_ID(elementId.getRepoId());
			save(menu);
		}
	}

	@Override
	public void performUpdatesAfterSaveElement(final ElementChangedEvent event)
	{
		if (availableUpdatesForADColumn(event))
		{
			updateADColumns(event);
		}

		if (availableUpdatesForADProcessParas(event))
		{
			updateADProcessParas(event);
		}

		if (availableUpdatesForADField(event))
		{
			updateADFields(event);
		}

		if (availableUpdatesForPrintFormatItem(event))
		{
			updateADPrintFormatItems(event);
		}

		if (availableUpdatesForADTab(event))
		{
			updateADTabs(event);
		}

		if (availableUpdatesForADWindow(event))
		{

			updateADWindows(event);
		}

		if (availableUpdatesForADMenu(event))
		{
			updateADMenus(event);
		}
	}

	private boolean availableUpdatesForADColumn(final ElementChangedEvent event)
	{
		final ImmutableSet<String> updatedColumns = event.getUpdatedColumns();

		return updatedColumns.contains(I_AD_Element.COLUMNNAME_Name) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Description) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Help) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_ColumnName);
	}

	private boolean availableUpdatesForADProcessParas(final ElementChangedEvent event)
	{
		final ImmutableSet<String> updatedColumns = event.getUpdatedColumns();

		return updatedColumns.contains(I_AD_Element.COLUMNNAME_Name) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Description) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Help) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_ColumnName);
	}

	private boolean availableUpdatesForADField(final ElementChangedEvent event)
	{
		final ImmutableSet<String> updatedColumns = event.getUpdatedColumns();

		return updatedColumns.contains(I_AD_Element.COLUMNNAME_Name) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Description) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Help);
	}

	private boolean availableUpdatesForPrintFormatItem(final ElementChangedEvent event)
	{
		final ImmutableSet<String> updatedColumns = event.getUpdatedColumns();

		return updatedColumns.contains(I_AD_Element.COLUMNNAME_Name) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_PrintName);
	}

	private boolean availableUpdatesForADTab(final ElementChangedEvent event)
	{
		final ImmutableSet<String> updatedColumns = event.getUpdatedColumns();

		return updatedColumns.contains(I_AD_Element.COLUMNNAME_Name) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Description) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Help) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_CommitWarning);
	}

	private boolean availableUpdatesForADWindow(final ElementChangedEvent event)
	{
		final ImmutableSet<String> updatedColumns = event.getUpdatedColumns();

		return updatedColumns.contains(I_AD_Element.COLUMNNAME_Name) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Description) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Help);
	}

	private boolean availableUpdatesForADMenu(final ElementChangedEvent event)
	{
		final ImmutableSet<String> updatedColumns = event.getUpdatedColumns();

		return updatedColumns.contains(I_AD_Element.COLUMNNAME_Name) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Description) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_Help) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_WEBUI_NameBrowse) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_WEBUI_NameNew) ||
				updatedColumns.contains(I_AD_Element.COLUMNNAME_WEBUI_NameNewBreadcrumb);
	}

	private void updateADMenus(final ElementChangedEvent event)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_Menu SET Name=").append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", ").append(I_AD_Element.COLUMNNAME_WEBUI_NameBrowse).append(" = ").append(DB.TO_STRING(event.getWebuiNameBrowse()))
				.append(", ").append(I_AD_Element.COLUMNNAME_WEBUI_NameNew).append(" = ").append(DB.TO_STRING(event.getWebuiNameNew()))
				.append(", ").append(I_AD_Element.COLUMNNAME_WEBUI_NameNewBreadcrumb).append(" = ").append(DB.TO_STRING(event.getWebuiNameNewBreadcrumb()))

				.append(" WHERE AD_Element_ID = ").append(event.getAdElementId().getRepoId());

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Menus updated #{}", no);
	}

	private void updateADWindows(final ElementChangedEvent event)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_WINDOW SET Name=")
				.append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(" WHERE AD_Element_ID = ").append(event.getAdElementId().getRepoId());

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Windows updated #{}", no);
	}

	private void updateADTabs(final ElementChangedEvent event)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_Tab SET Name=")
				.append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(", ").append(I_AD_Element.COLUMNNAME_CommitWarning).append(" = ").append(DB.TO_STRING(event.getCommitWarning()))
				.append(" WHERE AD_Element_ID = ").append(event.getAdElementId().getRepoId());

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Tabs updated #{}", no);
	}

	private void updateADPrintFormatItems(final ElementChangedEvent event)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_PrintFormatItem pi SET PrintName=")
				.append(DB.TO_STRING(event.getPrintName()))
				.append(", Name=").append(DB.TO_STRING(event.getName()))
				.append(" WHERE IsCentrallyMaintained='Y'")
				.append(" AND EXISTS (SELECT * FROM AD_Column c ")
				.append("WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=")
				.append(event.getAdElementId().getRepoId()).append(")");
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("PrintFormatItem updated #" + no);
	}

	private void updateADFields(final ElementChangedEvent event)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_Field SET Name=")
				.append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(" WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=")
				.append(event.getAdElementId().getRepoId())
				.append(")")
				.append(" AND ")
				.append(I_AD_Field.COLUMNNAME_AD_Name_ID).append(" IS NULL ")
				.append(")")
				.append(" OR ")
				.append("(")
				.append(I_AD_Field.COLUMNNAME_AD_Name_ID).append(" = ").append(event.getAdElementId().getRepoId())
				.append(")");
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Fields updated #" + no);
	}

	private void updateADProcessParas(final ElementChangedEvent event)
	{
		StringBuilder sql;
		int no = 0;
		// Parameter
		if (event.getColumnName() != null)
		{
			sql = new StringBuilder("UPDATE AD_Process_Para SET ColumnName=")
					.append(DB.TO_STRING(event.getColumnName()))
					.append(", Name=").append(DB.TO_STRING(event.getName()))
					.append(", Description=").append(DB.TO_STRING(event.getDescription()))
					.append(", Help=").append(DB.TO_STRING(event.getHelp()))
					.append(", AD_Element_ID=").append(event.getAdElementId().getRepoId())
					.append(" WHERE UPPER(ColumnName)=")
					.append(DB.TO_STRING(event.getColumnName().toUpperCase()))
					.append(" AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL");
			no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}

		sql = new StringBuilder("UPDATE AD_Process_Para SET ColumnName=")
				.append(DB.TO_STRING(event.getColumnName()))
				.append(", Name=").append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(" WHERE AD_Element_ID=").append(event.getAdElementId().getRepoId())
				.append(" AND IsCentrallyMaintained='Y'");
		no += DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Parameters updated #" + no);
	}

	private void updateADColumns(final ElementChangedEvent event)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_Column SET ColumnName=")
				.append(DB.TO_STRING(event.getColumnName()))
				.append(", Name=").append(DB.TO_STRING(event.getName()))
				.append(", Description=").append(DB.TO_STRING(event.getDescription()))
				.append(", Help=").append(DB.TO_STRING(event.getHelp()))
				.append(" WHERE AD_Element_ID=").append(event.getAdElementId().getRepoId());
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("afterSave - Columns updated #" + no);
	}

}
