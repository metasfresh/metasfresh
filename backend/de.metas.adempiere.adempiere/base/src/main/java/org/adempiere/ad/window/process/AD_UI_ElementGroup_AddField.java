package org.adempiere.ad.window.process;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.ad.window.api.UIElementGroupId;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_UI_Element;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class AD_UI_ElementGroup_AddField extends JavaProcess
{
	@Param(parameterName = "AD_Field_ID", mandatory = true)
	private AdFieldId adFieldId;

	// @Param(parameterName = "After_UI_Element_ID", mandatory = false)
	// private UIElementId afterUIElementId;

	private UIElementGroupId getAdElementGroupId()
	{
		return UIElementGroupId.ofRepoId(getRecord_ID());
	}

	@Override
	protected String doIt()
	{
		final I_AD_Field adField = loadOutOfTrx(adFieldId, I_AD_Field.class);
		final AdTabId adTabId = AdTabId.ofRepoId(adField.getAD_Tab_ID());

		//
		//
		final UIElementGroupId uiElementGroupId = getAdElementGroupId();
		I_AD_UI_Element uiElement = getUIElementByTabAndFieldId(adTabId, adFieldId);
		if (uiElement == null)
		{
			uiElement = createUIElement(adField, uiElementGroupId);
		}
		else
		{
			uiElement.setAD_UI_ElementGroup_ID(uiElementGroupId.getRepoId());
		}

		final int seqNo = Services.get(IADWindowDAO.class).getUIElementNextSeqNo(uiElementGroupId);
		uiElement.setIsDisplayed(true);
		uiElement.setSeqNo(seqNo);

		// if (afterUIElementId != null)
		// {
		// 	// TODO implement
		// 	addLog("WARNING: Adding after a given element not yet implemented, so your element will be added last.");
		// }

		saveRecord(uiElement);

		return MSG_OK;
	}

	private I_AD_UI_Element getUIElementByTabAndFieldId(final AdTabId adTabId, final AdFieldId adFieldId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_UI_Element.class)
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_Tab_ID, adTabId)
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_Field_ID, adFieldId)
				.create()
				.firstOnly(I_AD_UI_Element.class);
	}

	private I_AD_UI_Element createUIElement(
			@NonNull final I_AD_Field adField,
			@NonNull final UIElementGroupId uiElementGroupId)
	{
		final I_AD_UI_Element uiElement = WindowUIElementsGenerator.createUIElementNoSave(uiElementGroupId, adField);
		uiElement.setIsDisplayed(true);
		uiElement.setSeqNo(10);
		return uiElement;
	}

}
