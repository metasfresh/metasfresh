package org.adempiere.ad.ui.model.interceptor;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.IADElementDAO;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_AD_UI_Element.class)
@Callout(I_AD_UI_Element.class)
@Component
public class AD_UI_Element
{

	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings =  ModelValidator.TYPE_BEFORE_DELETE )
	public void onBeforeElementDelete(@NonNull final I_AD_UI_Element uiElement)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_AD_UI_ElementField.class)
		.addEqualsFilter(I_AD_UI_ElementField.COLUMN_AD_UI_Element_ID, uiElement.getAD_UI_Element_ID())
		.create()
		.delete();
	}

	@CalloutMethod(columnNames = I_AD_UI_Element.COLUMNNAME_AD_Field_ID)
	public void calloutOnFieldIdChanged(@NonNull final I_AD_UI_Element uiElement)
	{
		if (uiElement.getAD_Field_ID() > 0)
		{
			updateNameFromElement(uiElement);
		}
	}

	private void updateNameFromElement(@NonNull final I_AD_UI_Element uiElement)
	{
		final I_AD_Field field = uiElement.getAD_Field();
		final AdElementId fieldElementId = getFieldElementId(field);
		final I_AD_Element fieldElement = Services.get(IADElementDAO.class).getById(fieldElementId.getRepoId());

		uiElement.setName(fieldElement.getName());
		uiElement.setDescription(fieldElement.getDescription());
		uiElement.setHelp(fieldElement.getHelp());
	}

	private AdElementId getFieldElementId(final I_AD_Field field)
	{
		if (field.getAD_Name_ID() > 0)
		{
			return AdElementId.ofRepoId(field.getAD_Name_ID());
		}
		else
		{
			final I_AD_Column fieldColumn = field.getAD_Column();
			return AdElementId.ofRepoId(fieldColumn.getAD_Element_ID());
		}
	}
}
