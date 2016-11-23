package org.adempiere.ad.window.api;

import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.I_AD_Window;

public interface IADWindowDAO extends ISingletonService
{

	/**
	 * Loads the given window (cached) and returns its name. Uses {@link org.compiere.util.Env#getCtx()} to get the context.
	 * 
	 * @param adWindowId
	 * @return the name for the given <code>AD_Window_ID</code> or <code>null</code> if the given ID is less or equal zero.
	 */
	String retrieveWindowName(int adWindowId);

	/**
	 * Loads and returns the given window (cached).
	 * 
	 * @param ctx
	 * @param adWindowId whe window's <code>AD_Window_ID</code>.
	 * @return
	 */
	I_AD_Window retrieveWindow(Properties ctx, int adWindowId);

	List<I_AD_UI_ElementField> retrieveUIElementFields(final I_AD_UI_Element uiElement);

	List<I_AD_UI_Element> retrieveUIElements(final I_AD_UI_ElementGroup uiElementGroup);

	List<I_AD_UI_ElementGroup> retrieveUIElementGroups(final I_AD_UI_Column uiColumn);

	List<I_AD_UI_Column> retrieveUIColumns(final I_AD_UI_Section uiSection);

	List<I_AD_UI_Section> retrieveUISections(final Properties ctx, final int AD_Tab_ID);

	List<I_AD_UI_Section> retrieveUISections(final I_AD_Tab adTab);

	boolean hasUISections(I_AD_Tab adTab);

	List<I_AD_Tab> retrieveTabs(final I_AD_Window adWindow);

	void moveElementGroup(I_AD_UI_ElementGroup uiElementGroup, I_AD_UI_Column toUIColumn);

	void moveElement(I_AD_UI_Element uiElement, I_AD_UI_ElementGroup toUIElementGroup);

	/**
	 * Retrieve the first tab of the given window (seqNo = 10)
	 * 
	 * @param window
	 * @return
	 */
	I_AD_Tab retrieveFirstTab(I_AD_Window window);

}
