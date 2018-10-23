package org.adempiere.ad.menu.model.interceptor;

import java.sql.SQLException;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.menu.api.IADMenuDAO;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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

@Interceptor(I_AD_Menu.class)
@Callout(I_AD_Menu.class)
@Component("org.adempiere.ad.menu.model.interceptor.AD_Menu")
public class AD_Menu
{
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Tab.COLUMNNAME_AD_Element_ID)
	@CalloutMethod(columnNames = I_AD_Menu.COLUMNNAME_AD_Element_ID)
	public void onElementIDChanged(final I_AD_Menu menu) throws SQLException
	{
		if (!IADMenuDAO.DYNATTR_AD_Menu_UpdateTranslations.getValue(menu))
		{
			// do not copy translations from element to menu
			return;
		}

		final I_AD_Element menuElement = menu.getAD_Element();

		if (menuElement == null)
		{
			// nothing to do. It was not yet set
			return;
		}

		menu.setName(menuElement.getName());
		menu.setDescription(menuElement.getDescription());
		menu.setWEBUI_NameBrowse(menuElement.getWEBUI_NameBrowse());
		menu.setWEBUI_NameNew(menuElement.getWEBUI_NameNew());
		menu.setWEBUI_NameNewBreadcrumb(menuElement.getWEBUI_NameNewBreadcrumb());

	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_AD_Menu.COLUMNNAME_AD_Element_ID)
	public void updateTranslationsForElement(final I_AD_Menu menu)
	{
		final int menuElementId = menu.getAD_Element_ID();

		if (menuElementId <= 0)
		{
			// nothing to do. It was not yet set
			return;
		}

		Services.get(IElementTranslationBL.class).updateMenuTranslationsFromElement(menuElementId);
	}

}
