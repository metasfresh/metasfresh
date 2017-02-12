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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
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
		return retrieveWindowNameCached(ctx, adWindowId);
	}

	@Cached(cacheName = I_AD_Window.Table_Name + "#By#" + I_AD_Window.COLUMNNAME_AD_Window_ID)
	public String retrieveWindowNameCached(
			@CacheCtx final Properties ctx,
			final int adWindowId)
	{
		// using a simple DB call would be faster, but this way it's less coupled and after all we have caching
		
		final I_AD_Window window = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Window.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Window.COLUMNNAME_AD_Window_ID, adWindowId)
				.create()
				.firstOnly(I_AD_Window.class);
		
		if(window == null)
		{
			return null;
		}
		
		final I_AD_Window windowTrl = InterfaceWrapperHelper.translate(window, I_AD_Window.class);
		return window.getName();
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
				.addColumn(I_AD_Tab.COLUMN_AD_Tab_ID)
				.endOrderBy()
				.create()
				.list(I_AD_Tab.class);
	}

	@Override
	public List<I_AD_UI_Section> retrieveUISections(final I_AD_Tab adTab)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(adTab);
		final int AD_Tab_ID = adTab.getAD_Tab_ID();
		return retrieveUISectionsQuery(ctx, AD_Tab_ID)
				.create()
				.list(I_AD_UI_Section.class);
	}

	@Override
	public List<I_AD_UI_Section> retrieveUISections(final Properties ctx, final int AD_Tab_ID)
	{
		return retrieveUISectionsQuery(ctx, AD_Tab_ID)
				.create()
				.list(I_AD_UI_Section.class);
	}

	@Override
	public boolean hasUISections(final I_AD_Tab adTab)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(adTab);
		final int AD_Tab_ID = adTab.getAD_Tab_ID();
		return retrieveUISectionsQuery(ctx, AD_Tab_ID)
				.create()
				.match();
	}

	private IQueryBuilder<I_AD_UI_Section> retrieveUISectionsQuery(final Properties ctx, final int AD_Tab_ID)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Section.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Section.COLUMN_AD_Tab_ID, AD_Tab_ID)
				.orderBy()
				.addColumn(I_AD_UI_Section.COLUMN_SeqNo)
				.addColumn(I_AD_UI_Section.COLUMN_AD_UI_Section_ID)
				.endOrderBy();
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
				.addColumn(I_AD_UI_Column.COLUMN_SeqNo)
				.addColumn(I_AD_UI_Column.COLUMN_AD_UI_Column_ID)
				.endOrderBy()
				.create()
				.list(I_AD_UI_Column.class);
	}

	@Override
	public List<I_AD_UI_ElementGroup> retrieveUIElementGroups(final I_AD_UI_Column uiColumn)
	{
		return retrieveUIElementGroupsQuery(uiColumn)
				.create()
				.list(I_AD_UI_ElementGroup.class);
	}

	private IQueryBuilder<I_AD_UI_ElementGroup> retrieveUIElementGroupsQuery(final I_AD_UI_Column uiColumn)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_ElementGroup.class, uiColumn)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_ElementGroup.COLUMN_AD_UI_Column_ID, uiColumn.getAD_UI_Column_ID())
				.orderBy()
				.addColumn(I_AD_UI_ElementGroup.COLUMN_SeqNo)
				.endOrderBy();
	}

	private int retrieveUIElementGroupsNextSeqNo(final I_AD_UI_Column uiColumn)
	{
		final Integer lastSeqNo = retrieveUIElementGroupsQuery(uiColumn)
				.create()
				.aggregate(I_AD_UI_ElementGroup.COLUMN_SeqNo, IQuery.AGGREGATE_MAX, Integer.class);
		return nextSeqNo(lastSeqNo);
	}

	private static final int nextSeqNo(final Integer lastSeqNo)
	{
		if (lastSeqNo == null)
		{
			return 10;
		}

		if (lastSeqNo % 10 == 0)
		{
			return lastSeqNo + 10;
		}

		return lastSeqNo / 10 * 10 + 10;
	}

	@Override
	public List<I_AD_UI_Element> retrieveUIElements(final I_AD_UI_ElementGroup uiElementGroup)
	{
		return retrieveUIElementsQuery(uiElementGroup)
				.create()
				.list(I_AD_UI_Element.class);
	}

	private int retrieveUIElementNextSeqNo(final I_AD_UI_ElementGroup uiElementGroup)
	{
		final Integer lastSeqNo = retrieveUIElementsQuery(uiElementGroup)
				.create()
				.aggregate(I_AD_UI_Element.COLUMN_SeqNo, IQuery.AGGREGATE_MAX, Integer.class);
		return nextSeqNo(lastSeqNo);
	}

	private IQueryBuilder<I_AD_UI_Element> retrieveUIElementsQuery(final I_AD_UI_ElementGroup uiElementGroup)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_Element.class, uiElementGroup)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_Element.COLUMN_AD_UI_ElementGroup_ID, uiElementGroup.getAD_UI_ElementGroup_ID())
				.orderBy()
				.addColumn(I_AD_UI_Element.COLUMN_SeqNo)
				.endOrderBy();
	}

	@Override
	public List<I_AD_UI_ElementField> retrieveUIElementFields(final I_AD_UI_Element uiElement)
	{
		return retrieveUIElementFieldsQuery(uiElement)
				.create()
				.list(I_AD_UI_ElementField.class);
	}

	private IQueryBuilder<I_AD_UI_ElementField> retrieveUIElementFieldsQuery(final I_AD_UI_Element uiElement)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL
				.createQueryBuilder(I_AD_UI_ElementField.class, uiElement)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_UI_ElementField.COLUMN_AD_UI_Element_ID, uiElement.getAD_UI_Element_ID())
				.orderBy()
				.addColumn(I_AD_UI_ElementField.COLUMN_SeqNo)
				.addColumn(I_AD_UI_ElementField.COLUMN_AD_UI_ElementField_ID)
				.endOrderBy();
	}

	@Override
	public void moveElementGroup(final I_AD_UI_ElementGroup uiElementGroup, final I_AD_UI_Column toUIColumn)
	{
		Check.assumeNotNull(uiElementGroup, "Parameter uiElementGroup is not null");
		Check.assumeNotNull(toUIColumn, "Parameter toUIColumn is not null");

		if (uiElementGroup.getAD_UI_Column_ID() == toUIColumn.getAD_UI_Column_ID())
		{
			// nothing to move
			return;
		}

		InterfaceWrapperHelper.ATTR_ReadOnlyColumnCheckDisabled.setValue(uiElementGroup, Boolean.TRUE);
		uiElementGroup.setAD_UI_Column(toUIColumn);
		uiElementGroup.setSeqNo(retrieveUIElementGroupsNextSeqNo(toUIColumn));
		InterfaceWrapperHelper.save(uiElementGroup);
	}

	@Override
	public void moveElement(final I_AD_UI_Element uiElement, final I_AD_UI_ElementGroup toUIElementGroup)
	{
		Check.assumeNotNull(uiElement, "Parameter uiElement is not null");
		Check.assumeNotNull(toUIElementGroup, "Parameter toUIElementGroup is not null");

		if (uiElement.getAD_UI_ElementGroup_ID() == toUIElementGroup.getAD_UI_ElementGroup_ID())
		{
			// nothing to move
			return;
		}

		InterfaceWrapperHelper.ATTR_ReadOnlyColumnCheckDisabled.setValue(uiElement, Boolean.TRUE);
		uiElement.setAD_UI_ElementGroup(toUIElementGroup);
		uiElement.setSeqNo(retrieveUIElementNextSeqNo(toUIElementGroup));
		InterfaceWrapperHelper.save(uiElement);
	}

	@Override
	public I_AD_Tab retrieveFirstTab(final I_AD_Window window)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Tab.class, window)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Tab.COLUMNNAME_AD_Window_ID, window.getAD_Window_ID())
				.addEqualsFilter(I_AD_Tab.COLUMNNAME_SeqNo, 10)
				.create()
				.firstOnly(I_AD_Tab.class);
	}
}
