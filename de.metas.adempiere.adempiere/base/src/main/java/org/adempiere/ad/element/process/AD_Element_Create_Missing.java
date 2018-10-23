package org.adempiere.ad.element.process;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.menu.api.IADMenuDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MColumn;

import de.metas.process.JavaProcess;
import de.metas.translation.api.IElementTranslationBL;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class AD_Element_Create_Missing extends JavaProcess
{
	@Override
	protected String doIt() throws Exception
	{
		createAndLinkElementsForTabs();
		createAndLinkElementsForWindows();
		createAndLinkElementsForMenus();

		make_AD_Element_Mandatory_In_AD_Tab();
		make_AD_Element_Mandatory_In_AD_Window();
		make_AD_Element_Mandatory_In_AD_Menu();

		return MSG_OK;
	}

	private void make_AD_Element_Mandatory_In_AD_Menu()
	{
		final I_AD_Column elementIdColumn = Services.get(IADTableDAO.class).retrieveColumn(I_AD_Menu.Table_Name, I_AD_Menu.COLUMNNAME_AD_Element_ID);

		makeElementColumnMandatory (elementIdColumn);
	}

	private void make_AD_Element_Mandatory_In_AD_Window()
	{
		final I_AD_Column elementIdColumn = Services.get(IADTableDAO.class).retrieveColumn(I_AD_Window.Table_Name, I_AD_Window.COLUMNNAME_AD_Element_ID);

		makeElementColumnMandatory (elementIdColumn);
	}

	private void make_AD_Element_Mandatory_In_AD_Tab()
	{
		final I_AD_Column elementIdColumn = Services.get(IADTableDAO.class).retrieveColumn(I_AD_Tab.Table_Name, I_AD_Tab.COLUMNNAME_AD_Element_ID);

		makeElementColumnMandatory (elementIdColumn);
	}


	private void makeElementColumnMandatory(final I_AD_Column elementIdColumn)
	{
		elementIdColumn.setIsMandatory(true);
		save(elementIdColumn);

		final MColumn columnPO = LegacyAdapters.convertToPO(elementIdColumn);
		columnPO.syncDatabase();

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

}
