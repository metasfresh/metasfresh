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
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.persistence.cache.AbstractModelListCacheLocal;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;

/**
 * Links to the {@link I_M_HU} (which is also containing and instance to this class, it's {@link #DYNATTR_Instance} dynamic attribute), and holds the items of that HU.
 *
 * @author tsa
 *
 */
/* package */final class HUItemsLocalCache extends AbstractModelListCacheLocal<I_M_HU, I_M_HU_Item>
{
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final String DYNATTR_Instance = HUItemsLocalCache.class.getName();

	public static final HUItemsLocalCache getCreate(final I_M_HU hu)
	{
		HUItemsLocalCache cache = InterfaceWrapperHelper.getDynAttribute(hu, DYNATTR_Instance);
		if (cache == null)
		{
			cache = new HUItemsLocalCache(hu);
			cache.setCacheDisabled(HUConstants.DEBUG_07504_Disable_HUItemsLocalCache);
			InterfaceWrapperHelper.setDynAttribute(hu, DYNATTR_Instance, cache);
		}
		return cache;
	}

	private HUItemsLocalCache(final I_M_HU hu)
	{
		super(hu);
	}

	@Override
	protected final Comparator<I_M_HU_Item> createItemsComparator()
	{
		return IHandlingUnitsDAO.HU_ITEMS_COMPARATOR;
	}

	/**
	 * Retrieves a list of {@link I_M_HU_Item}s with ordering according to {@link #ITEM_TYPE_ORDERING}.
	 */
	@Override
	protected final List<I_M_HU_Item> retrieveItems(final IContextAware ctx, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Item> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Item.class, ctx)
				.addEqualsFilter(I_M_HU_Item.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.addOnlyActiveRecordsFilter();

		final List<I_M_HU_Item> items = queryBuilder
				.create()
				.list()
				.stream()
				.peek(item -> item.setM_HU(hu)) // Make sure item.getM_HU() will return our HU
				.sorted(createItemsComparator())
				.collect(Collectors.toList());

		return items;
	}

	@Override
	protected Object mkKey(final I_M_HU_Item item)
	{
		return item.getM_HU_Item_ID();
	}
}
