package org.adempiere.ad.tab.model.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.IADElementDAO;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Element;
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

@Interceptor(I_AD_Tab.class)
@Callout(I_AD_Tab.class)
@Component("org.adempiere.ad.tab.model.interceptor.AD_Tab")
public class AD_Tab
{
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_AD_Tab.COLUMNNAME_AD_Table_ID)
	public void onTableIdChanged(final I_AD_Tab tab)
	{
		if (tab.getAD_Table_ID() > 0)
		{
			final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);
			final IADElementDAO adElementsRepo = Services.get(IADElementDAO.class);
			final String tableName = adTablesRepo.retrieveTableName(tab.getAD_Table_ID());

			tab.setInternalName(tableName);

			final AdElementId adElementId = adElementsRepo.getADElementIdByColumnNameOrNull(tableName + "_ID");
			if (adElementId != null)
			{
				tab.setAD_Element_ID(adElementId.getRepoId());
				onElementIDChanged(tab);
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_AD_Tab.COLUMNNAME_AD_Element_ID)
	@CalloutMethod(columnNames = I_AD_Tab.COLUMNNAME_AD_Element_ID)
	public void onElementIDChanged(final I_AD_Tab tab)
	{
		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
		IADElementDAO adElementDAO = Services.get(IADElementDAO.class);

		final I_AD_Element tabElement = adElementDAO.getById(tab.getAD_Element_ID());

		if (tabElement == null)
		{
			// nothing to do. It was not yet set
			return;
		}

		tab.setName(tabElement.getName());
		tab.setDescription(tabElement.getDescription());
		tab.setHelp(tabElement.getHelp());
		tab.setCommitWarning(tabElement.getCommitWarning());

		if (InterfaceWrapperHelper.isNew(tab))
		{
			// nothing to do. Window was not yet saved
			return;
		}

		final AdTabId adTabId = AdTabId.ofRepoIdOrNull(tab.getAD_Tab_ID());

		adWindowDAO.deleteExistingADElementLinkForTabId(adTabId);
		adWindowDAO.createADElementLinkForTabId(adTabId);

	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(final I_AD_Tab tab)
	{
		final AdTabId adTabId = AdTabId.ofRepoId(tab.getAD_Tab_ID());

		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
		adWindowDAO.deleteExistingADElementLinkForTabId(adTabId);
		adWindowDAO.deleteUISectionsByTabId(adTabId);
		adWindowDAO.deleteFieldsByTabId(adTabId);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_AD_Tab.COLUMNNAME_AD_Element_ID)
	public void updateTranslationsForElement(final I_AD_Tab tab)
	{
		if (!IElementTranslationBL.DYNATTR_AD_Tab_UpdateTranslations.getValue(tab, true))
		{
			// do not copy translations from element to tab
			return;
		}

		final AdElementId tabElementId = AdElementId.ofRepoIdOrNull(tab.getAD_Element_ID());
		if (tabElementId == null)
		{
			// nothing to do. It was not yet set
			return;
		}

		Services.get(IElementTranslationBL.class).updateTabTranslationsFromElement(tabElementId);
	}
}
