package org.adempiere.ad.window.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_UI_Column;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_UI_ElementField;
import org.compiere.model.I_AD_UI_ElementGroup;
import org.compiere.model.I_AD_UI_Section;
import org.compiere.model.I_AD_Window;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;

public class ADWindowDAO implements IADWindowDAO
{

	@Override
	public String retrieveWindowName(final int adWindowId)
	{
		final Properties ctx = Env.getCtx();
		final I_AD_Window window = retrieveWindow(ctx, adWindowId); // using a simple DB call would be faster, but this way it's less coupled and after all we have caching
		return window.getName();
	}

	@Override
	@Cached(cacheName = I_AD_Window.Table_Name + "#By#" + I_AD_Window.COLUMNNAME_AD_Window_ID)
	public I_AD_Window retrieveWindow(
			@CacheCtx final Properties ctx,
			final int adWindowId)
	{
		final I_AD_Window window = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Window.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Window.COLUMNNAME_AD_Window_ID, adWindowId)
				.create()
				.firstOnly(I_AD_Window.class);

		return window;
	}

	@Override
	public List<I_AD_Tab> retrieveTabs(final I_AD_Window adWindow)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_Tab.class, adWindow)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Tab.COLUMN_AD_Window_ID, adWindow.getAD_Window_ID())
				.orderBy()
				.addColumn(I_AD_Tab.COLUMN_SeqNo)
				.endOrderBy()
				.create()
				.list(I_AD_Tab.class);
	}

	@Override
	public List<I_AD_UI_Section> retrieveUISections(final I_AD_Tab adTab)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(adTab);
		final int AD_Tab_ID = adTab.getAD_Tab_ID();
		return retrieveUISections(ctx, AD_Tab_ID);
	}
	
	@Override
	public List<I_AD_UI_Section> retrieveUISections(final Properties ctx, final int AD_Tab_ID)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Section.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Section.COLUMN_AD_Tab_ID, AD_Tab_ID)
				.orderBy()
				.addColumn(I_AD_UI_Section.COLUMN_SeqNo)
				.endOrderBy()
				.create()
				.list(I_AD_UI_Section.class);
	}

	@Override
	public List<I_AD_UI_Column> retrieveUIColumns(final I_AD_UI_Section uiSection)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Column.class, uiSection)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Column.COLUMN_AD_UI_Section_ID, uiSection.getAD_UI_Section_ID())
				.orderBy()
				.addColumn(I_AD_UI_Column.COLUMN_AD_UI_Column_ID)
				.endOrderBy()
				.create()
				.list(I_AD_UI_Column.class);
	}

	@Override
	public List<I_AD_UI_ElementGroup> retrieveUIElementGroups(final I_AD_UI_Column uiColumn)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_ElementGroup.class, uiColumn)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_ElementGroup.COLUMN_AD_UI_Column_ID, uiColumn.getAD_UI_Column_ID())
				.orderBy()
				.addColumn(I_AD_UI_ElementGroup.COLUMN_SeqNo)
				.endOrderBy()
				.create()
				.list(I_AD_UI_ElementGroup.class);
	}

	@Override
	public List<I_AD_UI_Element> retrieveUIElements(final I_AD_UI_ElementGroup uiElementGroup)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Element.class, uiElementGroup)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_UI_ElementGroup_ID, uiElementGroup.getAD_UI_ElementGroup_ID())
				.orderBy()
				.addColumn(I_AD_UI_Element.COLUMN_SeqNo)
				.endOrderBy()
				.create()
				.list(I_AD_UI_Element.class);
	}

	@Override
	public List<I_AD_UI_ElementField> retrieveUIElementFields(final I_AD_UI_Element uiElement)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_ElementField.class, uiElement)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_ElementField.COLUMN_AD_UI_Element_ID, uiElement.getAD_UI_Element_ID())
				.orderBy()
				.addColumn(I_AD_UI_ElementField.COLUMN_AD_UI_ElementField_ID)
				.endOrderBy()
				.create()
				.list(I_AD_UI_ElementField.class);
	}

}
