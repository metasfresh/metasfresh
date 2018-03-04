package de.metas.ui.web.window.descriptor.factory.standard;

import java.util.List;

import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;

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

interface IWindowUIElementsProvider
{
	List<I_AD_UI_Section> getUISections(int AD_Tab_ID);

	List<I_AD_UI_Column> getUIColumns(I_AD_UI_Section uiSection);

	List<I_AD_UI_ElementGroup> getUIElementGroups(I_AD_UI_Column uiColumn);

	List<I_AD_UI_Element> getUIElements(I_AD_UI_ElementGroup uiElementGroup);

	List<I_AD_UI_Element> getUIElementsOfTypeLabels(int adTabId);

	List<I_AD_UI_ElementField> getUIElementFields(I_AD_UI_Element uiElement);

}
