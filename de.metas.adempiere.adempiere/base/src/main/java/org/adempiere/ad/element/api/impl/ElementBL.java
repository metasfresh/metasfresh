package org.adempiere.ad.element.api.impl;

import java.util.List;

import org.adempiere.ad.element.api.IElementBL;
import org.adempiere.ad.element.api.IElementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.util.Util;

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
	
		if(Check.isEmpty(widgetSize))
		{
			widgetSize = null;
		}

		for (final I_AD_UI_Element uiElement : uiElements)
		{
			uiElement.setWidgetSize(widgetSize);
			InterfaceWrapperHelper.save(uiElement);
		}
	}

}
