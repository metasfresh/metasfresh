package org.adempiere.ad.element.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.element.api.IElementBL;
import org.adempiere.ad.element.api.IElementDAO;
import org.adempiere.ad.menu.api.IADMenuDAO;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_Window;

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

}
