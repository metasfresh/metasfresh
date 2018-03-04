package de.metas.ui.web.window.descriptor.factory.standard;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.X_AD_UI_Element;
import org.compiere.util.Env;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

final class DAOWindowUIElementsProvider implements IWindowUIElementsProvider
{
	private final transient IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);

	@Override
	public List<I_AD_UI_Section> getUISections(final int AD_Tab_ID)
	{
		final Properties ctx = Env.getCtx();
		return windowDAO.retrieveUISections(ctx, AD_Tab_ID);
	}

	@Override
	public List<I_AD_UI_Column> getUIColumns(final I_AD_UI_Section uiSection)
	{
		return windowDAO.retrieveUIColumns(uiSection);
	}

	@Override
	public List<I_AD_UI_ElementGroup> getUIElementGroups(final I_AD_UI_Column uiColumn)
	{
		return windowDAO.retrieveUIElementGroups(uiColumn);
	}

	@Override
	public List<I_AD_UI_Element> getUIElements(final I_AD_UI_ElementGroup uiElementGroup)
	{
		return windowDAO.retrieveUIElements(uiElementGroup);
	}

	@Override
	public List<I_AD_UI_Element> getUIElementsOfTypeLabels(final int adTabId)
	{
		return windowDAO.retrieveUIElementsQueryByTabId(adTabId)
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_UI_ElementType, X_AD_UI_Element.AD_UI_ELEMENTTYPE_Labels)
				.create()
				.list(I_AD_UI_Element.class);
	}

	@Override
	public List<I_AD_UI_ElementField> getUIElementFields(final I_AD_UI_Element uiElement)
	{
		return windowDAO.retrieveUIElementFields(uiElement);
	}
}
