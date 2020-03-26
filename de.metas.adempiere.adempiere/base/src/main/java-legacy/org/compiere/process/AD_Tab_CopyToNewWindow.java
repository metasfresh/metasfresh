package org.compiere.process;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Window;

import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class AD_Tab_CopyToNewWindow extends JavaProcess
{
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

	@Param(parameterName = "WindowType", mandatory = true)
	private String windowType;

	@Override
	protected String doIt()
	{
		final AdTabId sourceTabId = AdTabId.ofRepoId(getRecord_ID());
		final I_AD_Tab sourceTab = adWindowDAO.getTabByIdInTrx(sourceTabId);

		final AdWindowId newWindowId = createNewWindowFromTab(sourceTab, windowType);
		final AdTabId newTabId = adWindowDAO.copyTabToWindow(sourceTab, newWindowId);

		final I_AD_Tab newTab = adWindowDAO.getTabByIdInTrx(newTabId);
		newTab.setSeqNo(10);
		newTab.setTabLevel(0);
		newTab.setAD_Column_ID(-1);
		newTab.setParent_Column_ID(-1);
		InterfaceWrapperHelper.save(newTab);

		return "@Created@ " + newWindowId + ", " + newTabId;
	}

	private AdWindowId createNewWindowFromTab(
			@NonNull final I_AD_Tab adTab,
			@NonNull final String windowType)
	{
		final I_AD_Window adWindow = newInstance(I_AD_Window.class);
		adWindow.setAD_Org_ID(OrgId.ANY.getRepoId());
		adWindow.setAD_Element_ID(adTab.getAD_Element_ID());
		// window.setInternalName(InternalName);
		adWindow.setName(adTab.getName());
		adWindow.setDescription(adTab.getDescription());
		adWindow.setHelp(adTab.getHelp());
		adWindow.setEntityType(adTab.getEntityType());
		adWindow.setWindowType(windowType);
		saveRecord(adWindow);
		return AdWindowId.ofRepoId(adWindow.getAD_Window_ID());
	}
}
