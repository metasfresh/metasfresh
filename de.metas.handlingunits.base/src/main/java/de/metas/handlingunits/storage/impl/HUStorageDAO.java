package de.metas.handlingunits.storage.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

// public only for testing purposes
public class HUStorageDAO extends AbstractHUStorageDAO
{
	public HUStorageDAO()
	{
		super();
	}

	@Override
	public <T> T newInstance(final Class<T> modelClass, final Object contextProvider)
	{
		return InterfaceWrapperHelper.newInstance(modelClass, contextProvider);
	}

	@Override
	public void initHUStorages(final I_M_HU hu)
	{
		// nothing
	}

	@Override
	public void initHUItemStorages(final I_M_HU_Item item)
	{
		// nothing
	}

	@Override
	public I_M_HU_Storage retrieveStorage(final I_M_HU hu, @NonNull final ProductId productId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Storage.class, hu)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Storage.COLUMN_M_HU_ID, hu.getM_HU_ID())
				.addEqualsFilter(I_M_HU_Storage.COLUMN_M_Product_ID, productId)
				.create()
				.firstOnly(I_M_HU_Storage.class);
	}

	@Override
	public void save(final I_M_HU_Storage storage)
	{
		InterfaceWrapperHelper.save(storage);
	}

	@Override
	public List<I_M_HU_Storage> retrieveStorages(final I_M_HU hu)
	{
		final List<I_M_HU_Storage> huStorages = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Storage.class, hu)
				.filter(new EqualsQueryFilter<I_M_HU_Storage>(I_M_HU_Storage.COLUMNNAME_M_HU_ID, hu.getM_HU_ID()))
				.create()
				.setOnlyActiveRecords(true)
				.list(I_M_HU_Storage.class);

		// Optimization: set parent link
		for (final I_M_HU_Storage huStorage : huStorages)
		{
			huStorage.setM_HU(hu);
		}

		return huStorages;
	}

	@Override
	public I_M_HU_Item_Storage retrieveItemStorage(final I_M_HU_Item huItem, @NonNull final ProductId productId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Item_Storage.class, huItem)
				.filter(new EqualsQueryFilter<I_M_HU_Item_Storage>(I_M_HU_Item_Storage.COLUMNNAME_M_HU_Item_ID, huItem.getM_HU_Item_ID()))
				.filter(new EqualsQueryFilter<I_M_HU_Item_Storage>(I_M_HU_Item_Storage.COLUMNNAME_M_Product_ID, productId))
				.create()
				.setOnlyActiveRecords(true)
				.firstOnly(I_M_HU_Item_Storage.class);
	}

	@Override
	public List<I_M_HU_Item_Storage> retrieveItemStorages(final I_M_HU_Item huItem)
	{
		final IQueryBuilder<I_M_HU_Item_Storage> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Item_Storage.class, huItem)
				.filter(new EqualsQueryFilter<I_M_HU_Item_Storage>(I_M_HU_Item_Storage.COLUMNNAME_M_HU_Item_ID, huItem.getM_HU_Item_ID()));

		queryBuilder.orderBy()
				.addColumn(I_M_HU_Item_Storage.COLUMNNAME_M_HU_Item_Storage_ID); // predictive order

		final List<I_M_HU_Item_Storage> huItemStorages = queryBuilder
				.create()
				.setOnlyActiveRecords(true)
				.list(I_M_HU_Item_Storage.class);

		// Optimization: set parent link
		for (final I_M_HU_Item_Storage huItemStorage : huItemStorages)
		{
			huItemStorage.setM_HU_Item(huItem);
		}

		return huItemStorages;
	}

	@Override
	public void save(final I_M_HU_Item_Storage storageLine)
	{
		InterfaceWrapperHelper.save(storageLine);
	}

	@Override
	public void save(final I_M_HU_Item item)
	{
		InterfaceWrapperHelper.save(item);
	}
}
