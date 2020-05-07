package org.adempiere.ad.service.impl;

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_AD_InfoWindow_From;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheModel;
import de.metas.adempiere.util.CacheTrx;

public class ADInfoWindowDAO implements IADInfoWindowDAO
{
	static final int AD_InfoWindow_ID_BPARTNER = 540008;

	static final int AD_InfoWindow_ID_PRODUCT = 540009;

	@Override
	public List<I_AD_InfoWindow_From> retrieveFroms(final I_AD_InfoWindow infoWindow)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(infoWindow);
		final String trxName = InterfaceWrapperHelper.getTrxName(infoWindow);
		final int adInfoWindowId = infoWindow.getAD_InfoWindow_ID();
		return retrieveFroms(ctx, adInfoWindowId, trxName);
	}

	@Cached(cacheName = I_AD_InfoWindow_From.Table_Name + "#By#" + I_AD_InfoWindow_From.COLUMNNAME_AD_InfoWindow_ID)
	/* package */List<I_AD_InfoWindow_From> retrieveFroms(
			@CacheCtx final Properties ctx
			, final int adInfoWindowId
			, @CacheTrx final String trxName)
	{
		final IQueryBuilder<I_AD_InfoWindow_From> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_InfoWindow_From.class, ctx, trxName)
				.filter(new EqualsQueryFilter<I_AD_InfoWindow_From>(I_AD_InfoWindow_From.COLUMNNAME_AD_InfoWindow_ID, adInfoWindowId))
				.filter(ActiveRecordQueryFilter.getInstance(I_AD_InfoWindow_From.class));

		queryBuilder.orderBy()
				.addColumn(I_AD_InfoWindow_From.COLUMNNAME_AD_InfoWindow_From_ID);

		return queryBuilder.create()
				.list(I_AD_InfoWindow_From.class);
	}

	@Override
	@Cached(cacheName = I_AD_InfoWindow.Table_Name + "#By#" + I_AD_InfoWindow.COLUMNNAME_AD_Table_ID)
	public I_AD_InfoWindow retrieveInfoWindowByTableId(
			@CacheCtx final Properties ctx,
			final int adTableId)
	{
		if (adTableId <= 0)
		{
			return null;
		}

		final IQueryBuilder<I_AD_InfoWindow> queryBuilder =
				Services.get(IQueryBL.class).createQueryBuilder(I_AD_InfoWindow.class, ctx, ITrx.TRXNAME_None)
						.filter(new EqualsQueryFilter<I_AD_InfoWindow>(I_AD_InfoWindow.COLUMNNAME_AD_Table_ID, adTableId))
						.filter(ActiveRecordQueryFilter.getInstance(I_AD_InfoWindow.class));

		queryBuilder.orderBy()
				.addColumn(I_AD_InfoWindow.COLUMNNAME_IsDefault, false);

		return queryBuilder.create()
				.first(I_AD_InfoWindow.class);

	}

	@Override
	public I_AD_InfoWindow retrieveInfoWindowByTableName(final Properties ctx, final String tableName)
	{
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		return retrieveInfoWindowByTableId(ctx, adTableId);
	}

	@Override
	public List<I_AD_InfoColumn> retrieveInfoColumns(final I_AD_InfoWindow infoWindow)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(infoWindow);
		final String trxName = InterfaceWrapperHelper.getTrxName(infoWindow);
		final int adInfoWindowId = infoWindow.getAD_InfoWindow_ID();

		return retrieveInfoColumns(ctx, adInfoWindowId, trxName);
	}

	@Cached(cacheName = I_AD_InfoColumn.Table_Name + "#By#" + I_AD_InfoColumn.COLUMNNAME_AD_InfoWindow_ID)
	/* package */List<I_AD_InfoColumn> retrieveInfoColumns(
			@CacheCtx final Properties ctx,
			final int adInfoWindowId,
			@CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_InfoColumn.class, ctx, trxName)
				.filter(new EqualsQueryFilter<I_AD_InfoColumn>(I_AD_InfoColumn.COLUMNNAME_AD_InfoWindow_ID, adInfoWindowId))
				.filter(ActiveRecordQueryFilter.getInstance(I_AD_InfoColumn.class))
				.create()
				.list(I_AD_InfoColumn.class);
	}

	@Override
	@Cached(cacheName = I_AD_InfoColumn.Table_Name + "#By#" + I_AD_InfoColumn.COLUMNNAME_AD_InfoWindow_ID+"#"+I_AD_InfoColumn.COLUMNNAME_AD_Element_ID)
	public I_AD_InfoColumn retrieveInfoColumnByColumnName(@CacheModel final I_AD_InfoWindow infoWindow, final String columnName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(infoWindow);

		final IQueryBuilder<I_AD_Element> addElementQueryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Element.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Element.COLUMNNAME_ColumnName, columnName);

		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_InfoColumn.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_InfoColumn.COLUMNNAME_AD_InfoWindow_ID, infoWindow.getAD_InfoWindow_ID())
				.addInSubQueryFilter(I_AD_InfoColumn.COLUMNNAME_AD_Element_ID, I_AD_Element.COLUMNNAME_AD_Element_ID, addElementQueryBuilder.create())
				.create()
				.firstOnly(I_AD_InfoColumn.class);
	}

	@Override
	public I_AD_InfoColumn retrieveTreeInfoColumn(final I_AD_InfoWindow infoWindow)
	{
		for (final I_AD_InfoColumn infoColumn : retrieveInfoColumns(infoWindow))
		{
			if (!infoColumn.isActive())
			{
				continue;
			}
			if (infoColumn.isTree())
			{
				return infoColumn;
			}
		}

		return null;
	}

	@Override
	public List<I_AD_InfoColumn> retrieveQueryColumns(final I_AD_InfoWindow infoWindow)
	{
		final List<I_AD_InfoColumn> list = new ArrayList<I_AD_InfoColumn>();
		for (final I_AD_InfoColumn infoColumn : retrieveInfoColumns(infoWindow))
		{
			if (!infoColumn.isActive())
			{
				continue;
			}
			if (!infoColumn.isQueryCriteria())
			{
				continue;
			}
			list.add(infoColumn);
		}

		Collections.sort(list, new Comparator<I_AD_InfoColumn>()
		{
			@Override
			public int compare(final I_AD_InfoColumn o1, final I_AD_InfoColumn o2)
			{
				return o1.getParameterSeqNo() - o2.getParameterSeqNo();
			}
		});
		return list;
	}

	@Override
	public List<I_AD_InfoColumn> retrieveDisplayedColumns(final I_AD_InfoWindow infoWindow)
	{
		final List<I_AD_InfoColumn> list = new ArrayList<I_AD_InfoColumn>();
		for (final I_AD_InfoColumn infoColumn : retrieveInfoColumns(infoWindow))
		{
			if (!infoColumn.isActive())
			{
				continue;
			}
			if (!infoColumn.isDisplayed())
			{
				continue;
			}
			list.add(infoColumn);
		}

		Collections.sort(list, new Comparator<I_AD_InfoColumn>()
		{
			@Override
			public int compare(final I_AD_InfoColumn o1, final I_AD_InfoColumn o2)
			{
				return o1.getSeqNo() - o2.getSeqNo();
			}
		});
		return list;
	}

	@Override
	@Cached(cacheName = I_AD_InfoWindow.Table_Name + "#by#" + I_AD_InfoWindow.COLUMNNAME_ShowInMenu)
	public List<I_AD_InfoWindow> retrieveInfoWindowsInMenu(@CacheCtx final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_InfoWindow.class, ctx, ITrx.TRXNAME_None)
				.addCompareFilter(I_AD_InfoWindow.COLUMNNAME_AD_InfoWindow_ID, CompareQueryFilter.Operator.NOT_EQUAL, 540008) // FIXME: hardcoded "Info Partner"
				.addCompareFilter(I_AD_InfoWindow.COLUMNNAME_AD_InfoWindow_ID, CompareQueryFilter.Operator.NOT_EQUAL, 540009) // FIXME: hardcoded "Info Product"
				.addEqualsFilter(I_AD_InfoWindow.COLUMNNAME_IsActive, true)
				.addEqualsFilter(I_AD_InfoWindow.COLUMNNAME_ShowInMenu, true)
				.addOnlyContextClientOrSystem()
				//
				.orderBy()
				.addColumn(I_AD_InfoWindow.COLUMNNAME_AD_InfoWindow_ID)
				.endOrderBy()
				//
				.create()
				.list(I_AD_InfoWindow.class);
	}
}
