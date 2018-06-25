package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.Comparator;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.persistence.cache.AbstractModelListCacheLocal;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;

/**
 * Links to the {@link I_M_HU_Item} (which is also containing and instace to this class, it's {@link #DYNATTR_Instance} dynamic attribute), and holds the parent HU of that HU Item.
 *
 * @author tsa
 *
 */
/* package */class IncludedHUsLocalCache extends AbstractModelListCacheLocal<I_M_HU_Item, I_M_HU>
{
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final IQueryOrderBy queryOrderBy;
	static
	{
		queryOrderBy = Services.get(IQueryBL.class)
				.createQueryOrderByBuilder(I_M_HU.class)
				.addColumn(I_M_HU.COLUMN_M_HU_ID, Direction.Ascending, Nulls.Last)
				.createQueryOrderBy();
	}

	private static final String DYNATTR_Instance = IncludedHUsLocalCache.class.getName();

	public static final IncludedHUsLocalCache getCreate(final I_M_HU_Item huItem)
	{
		IncludedHUsLocalCache cache = InterfaceWrapperHelper.getDynAttribute(huItem, DYNATTR_Instance);
		if (cache == null)
		{
			cache = new IncludedHUsLocalCache(huItem);

			// FIXME: this is making de.metas.customer.picking.service.impl.PackingServiceTest to fail
			cache.setCacheDisabled(HUConstants.DEBUG_07504_Disable_IncludedHUsLocalCache);

			InterfaceWrapperHelper.setDynAttribute(huItem, DYNATTR_Instance, cache);
		}
		return cache;
	}

	public IncludedHUsLocalCache(final I_M_HU_Item parentItem)
	{
		super(parentItem);
	}

	@Override
	protected Comparator<I_M_HU> createItemsComparator()
	{
		return queryOrderBy.getComparator(I_M_HU.class);
	}

	@Override
	protected List<I_M_HU> retrieveItems(final IContextAware ctx, final I_M_HU_Item parentItem)
	{
		final IQueryBuilder<I_M_HU> queryBuilder = queryBL
				.createQueryBuilder(I_M_HU.class, ctx)
				.addEqualsFilter(I_M_HU.COLUMN_M_HU_Item_Parent_ID, parentItem.getM_HU_Item_ID())
		// Retrieve all HUs, even if they are not active.
		// We need this because in case of shipped HUs (HUStatus=E) those are also inactivated (IsActive=N).
		// see https://github.com/metasfresh/metasfresh-webui-api/issues/567.
		// .addOnlyActiveRecordsFilter()
		;

		final List<I_M_HU> hus = queryBuilder
				.create()
				.setOrderBy(queryOrderBy)
				.list();

		// Make sure hu.getM_HU_Item_Parent() returns our parentItem
		for (final I_M_HU hu : hus)
		{
			hu.setM_HU_Item_Parent(parentItem);
		}

		return hus;
	}

	@Override
	protected final Object mkKey(final I_M_HU item)
	{
		return item.getM_HU_ID();
	}
}
