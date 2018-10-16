package org.adempiere.ad.window.model.interceptor;

import java.sql.SQLException;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Window;
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
@Interceptor(I_AD_Window.class)
@Callout(I_AD_Window.class)
@Component("org.adempiere.ad.window.model.interceptor.AD_Window")
public class AD_Window
{
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Window.COLUMNNAME_AD_Element_ID)
	@CalloutMethod(columnNames = I_AD_Window.COLUMNNAME_AD_Element_ID)
	public void onElementIDChanged(final I_AD_Window window) throws SQLException
	{
		final I_AD_Element windowElement = window.getAD_Element();

		if (windowElement == null)
		{
			// nothing to do. It was not yet set
			return;
		}

		window.setName(windowElement.getName());
		window.setDescription(windowElement.getDescription());
		window.setHelp(windowElement.getHelp());

	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_AD_Window.COLUMNNAME_AD_Element_ID)
	public void updateTranslationsForElement(final I_AD_Window window)
	{
		final int windowElementId = window.getAD_Element_ID();
		if(windowElementId <= 0)
		{
			// nothing to do. It was not yet set
			return;
		}

		Services.get(IElementTranslationBL.class).updateFieldTranslationsFromAD_Name(windowElementId);
	}
}
