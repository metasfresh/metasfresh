package org.adempiere.ad.element.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.IElementBL;
import org.adempiere.ad.element.api.IElementDAO;
import org.adempiere.ad.menu.api.IADMenuDAO;
import org.adempiere.ad.service.IADElementDAO;
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

			final int elementId = element.getAD_Element_ID();

			Services.get(IElementTranslationBL.class).updateElementTranslationsFromTab(elementId, tab.getAD_Tab_ID());

			IADWindowDAO.DYNATTR_AD_Tab_UpdateTranslations.setValue(tab, false);

			tab.setAD_Element_ID(elementId);
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

			final int elementId = element.getAD_Element_ID();

			Services.get(IElementTranslationBL.class).updateElementTranslationsFromWindow(elementId, window.getAD_Window_ID());

			IADWindowDAO.DYNATTR_AD_Window_UpdateTranslations.setValue(window, false);

			window.setAD_Element_ID(elementId);
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

			final int elementId = element.getAD_Element_ID();

			Services.get(IElementTranslationBL.class).updateElementTranslationsFromMenu(elementId, menu.getAD_Menu_ID());

			IADMenuDAO.DYNATTR_AD_Menu_UpdateTranslations.setValue(menu, false);

			menu.setAD_Element_ID(elementId);
			save(menu);
		}
	}

	@Override
	public void performUpdatesAfterSaveElement(final AdElementId adElementId)
	{
		final I_AD_Element element = Services.get(IADElementDAO.class).getById(adElementId.getRepoId());

		if (InterfaceWrapperHelper.isNew(element))
		{
			// only handle the update case
			return;
		}

		if (InterfaceWrapperHelper.isValueChanged(element, ImmutableSet.of(
				I_AD_Element.COLUMNNAME_Name,
				I_AD_Element.COLUMNNAME_Description,
				I_AD_Element.COLUMNNAME_Help,
				I_AD_Element.COLUMNNAME_ColumnName)))
		{
			updateADColumnsFromADElement(element);

			updateADProcessParasFromADElement(element);
		}

		if (InterfaceWrapperHelper.isValueChanged(element, ImmutableSet.of(
				I_AD_Element.COLUMNNAME_Name,
				I_AD_Element.COLUMNNAME_Description,
				I_AD_Element.COLUMNNAME_Help)))
		{
			updateADFieldsFromADElement(element);
		}

		if (InterfaceWrapperHelper.isValueChanged(element, ImmutableSet.of(
				I_AD_Element.COLUMNNAME_PrintName,
				I_AD_Element.COLUMNNAME_Name)))
		{
			updateADPrintFormatItemsFromADElement(element);
		}

		if (InterfaceWrapperHelper.isValueChanged(element, ImmutableSet.of(
				I_AD_Element.COLUMNNAME_Name,
				I_AD_Element.COLUMNNAME_Description,
				I_AD_Element.COLUMNNAME_Help,
				I_AD_Element.COLUMNNAME_CommitWarning)))
		{
			updateADTabsFromADElement(element);
		}

		if (InterfaceWrapperHelper.isValueChanged(element, ImmutableSet.of(
				I_AD_Element.COLUMNNAME_Name,
				I_AD_Element.COLUMNNAME_Description,
				I_AD_Element.COLUMNNAME_Help)))
		{
			updateADWindowsFromADElement(element);
		}

		if (InterfaceWrapperHelper.isValueChanged(element, ImmutableSet.of(
				I_AD_Element.COLUMNNAME_Name,
				I_AD_Element.COLUMNNAME_Description,
				I_AD_Element.COLUMNNAME_WEBUI_NameBrowse,
				I_AD_Element.COLUMNNAME_WEBUI_NameNew,
				I_AD_Element.COLUMNNAME_WEBUI_NameNewBreadcrumb)))
		{
			updateADMenusFromADElement(element);
		}
	}

	private void updateADMenusFromADElement(final I_AD_Element element)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_Menu SET Name=").append(DB.TO_STRING(element.getName()))
				.append(", Description=").append(DB.TO_STRING(element.getDescription()))
				.append(", ").append(I_AD_Element.COLUMNNAME_WEBUI_NameBrowse).append(" = ").append(DB.TO_STRING(element.getWEBUI_NameBrowse()))
				.append(", ").append(I_AD_Element.COLUMNNAME_WEBUI_NameNew).append(" = ").append(DB.TO_STRING(element.getWEBUI_NameNew()))
				.append(", ").append(I_AD_Element.COLUMNNAME_WEBUI_NameNewBreadcrumb).append(" = ").append(DB.TO_STRING(element.getWEBUI_NameNewBreadcrumb()))

				.append(" WHERE AD_Element_ID = ").append(element.getAD_Element_ID());

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Menus updated #{}", no);
	}

	private void updateADWindowsFromADElement(final I_AD_Element element)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_WINDOW SET Name=")
				.append(DB.TO_STRING(element.getName()))
				.append(", Description=").append(DB.TO_STRING(element.getDescription()))
				.append(", Help=").append(DB.TO_STRING(element.getHelp()))
				.append(" WHERE AD_Element_ID = ").append(element.getAD_Element_ID());

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Windows updated #{}", no);
	}

	private void updateADTabsFromADElement(final I_AD_Element element)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_Tab SET Name=")
				.append(DB.TO_STRING(element.getName()))
				.append(", Description=").append(DB.TO_STRING(element.getDescription()))
				.append(", Help=").append(DB.TO_STRING(element.getHelp()))
				.append(", ").append(I_AD_Element.COLUMNNAME_CommitWarning).append(" = ").append(DB.TO_STRING(element.getCommitWarning()))
				.append(" WHERE AD_Element_ID = ").append(element.getAD_Element_ID());

		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Tabs updated #{}", no);
	}

	private void updateADPrintFormatItemsFromADElement(final I_AD_Element element)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_PrintFormatItem pi SET PrintName=")
				.append(DB.TO_STRING(element.getPrintName()))
				.append(", Name=").append(DB.TO_STRING(element.getName()))
				.append(" WHERE IsCentrallyMaintained='Y'")
				.append(" AND EXISTS (SELECT * FROM AD_Column c ")
				.append("WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=")
				.append(element.getAD_Element_ID()).append(")");
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("PrintFormatItem updated #" + no);
	}

	private void updateADFieldsFromADElement(final I_AD_Element element)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_Field SET Name=")
				.append(DB.TO_STRING(element.getName()))
				.append(", Description=").append(DB.TO_STRING(element.getDescription()))
				.append(", Help=").append(DB.TO_STRING(element.getHelp()))
				.append(" WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=")
				.append(element.getAD_Element_ID())
				.append(")")
				.append(" AND ")
				.append(I_AD_Field.COLUMNNAME_AD_Name_ID).append(" IS NULL ")
				.append(")")
				.append(" OR ")
				.append("(")
				.append(I_AD_Field.COLUMNNAME_AD_Name_ID).append(" = ").append(element.getAD_Element_ID())
				.append(")");
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Fields updated #" + no);
	}

	private void updateADProcessParasFromADElement(final I_AD_Element element)
	{
		StringBuilder sql;
		int no;
		// Parameter
		sql = new StringBuilder("UPDATE AD_Process_Para SET ColumnName=")
				.append(DB.TO_STRING(element.getColumnName()))
				.append(", Name=").append(DB.TO_STRING(element.getName()))
				.append(", Description=").append(DB.TO_STRING(element.getDescription()))
				.append(", Help=").append(DB.TO_STRING(element.getHelp()))
				.append(", AD_Element_ID=").append(element.getAD_Element_ID())
				.append(" WHERE UPPER(ColumnName)=")
				.append(DB.TO_STRING(element.getColumnName().toUpperCase()))
				.append(" AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL");
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE AD_Process_Para SET ColumnName=")
				.append(DB.TO_STRING(element.getColumnName()))
				.append(", Name=").append(DB.TO_STRING(element.getName()))
				.append(", Description=").append(DB.TO_STRING(element.getDescription()))
				.append(", Help=").append(DB.TO_STRING(element.getHelp()))
				.append(" WHERE AD_Element_ID=").append(element.getAD_Element_ID())
				.append(" AND IsCentrallyMaintained='Y'");
		no += DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("Parameters updated #" + no);
	}

	private void updateADColumnsFromADElement(final I_AD_Element element)
	{
		StringBuilder sql;
		int no;
		sql = new StringBuilder("UPDATE AD_Column SET ColumnName=")
				.append(DB.TO_STRING(element.getColumnName()))
				.append(", Name=").append(DB.TO_STRING(element.getName()))
				.append(", Description=").append(DB.TO_STRING(element.getDescription()))
				.append(", Help=").append(DB.TO_STRING(element.getHelp()))
				.append(" WHERE AD_Element_ID=").append(element.getAD_Element_ID());
		no = DB.executeUpdateEx(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		log.debug("afterSave - Columns updated #" + no);
	}

}
