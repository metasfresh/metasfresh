package de.metas.handlingunits.client.terminal.editor.model.filter.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.ITerminalLookup;
import de.metas.adempiere.form.terminal.lookup.SimpleTableLookup;
import de.metas.adempiere.model.I_M_Product_Category;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.storage.IProductStorage;

/**
 * Scan keys, get available product categories from product storages and filter by them
 *
 * @author al
 */
public class ProductCategoryHUKeyFilter extends AbstractHUKeyFilter
{
	//
	// Services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Key: M_Product_Category.M_Product_Category_ID; Name: M_Product_Category.Name
	 */
	@Override
	public List<KeyNamePair> getPropertyAvailableValues(final IHUKey key)
	{
		final List<KeyNamePair> result = new ArrayList<>();

		final Set<Integer> seenProductCategoryIds = new HashSet<>();

		final List<IProductStorage> productStorages = getProductStorages(key);
		for (final IProductStorage productStorage : productStorages)
		{
			final I_M_Product product = productStorage.getM_Product();

			if (!seenProductCategoryIds.add(product.getM_Product_Category_ID()))
			{
				continue; // skip added
			}

			final I_M_Product_Category category = InterfaceWrapperHelper.create(product.getM_Product_Category(), I_M_Product_Category.class);
			if (category == null)
			{
				continue; // shall not happen (DB constraint)
			}

			final KeyNamePair knp = createKeyNamePair(category);
			result.add(knp);
		}
		return result;
	}

	private final KeyNamePair createKeyNamePair(final I_M_Product_Category category)
	{
		final int knpKey = category.getM_Product_Category_ID();
		final String knpName = category.getName();
		final KeyNamePair knp = new KeyNamePair(knpKey, knpName);
		return knp;
	}

	@Override
	public List<KeyNamePair> getPropertyAvailableValues(final IQueryBuilder<I_M_HU> queryBuilder)
	{
		final List<I_M_Product_Category> productCategories = queryBuilder
				.andCollectChildren(I_M_HU_Storage.COLUMN_M_HU_ID, I_M_HU_Storage.class)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_M_HU_Storage.COLUMN_M_Product_ID)
				.andCollect(I_M_Product.COLUMN_M_Product_Category_ID)
				.create()
				.list(I_M_Product_Category.class);

		final List<KeyNamePair> knps = new ArrayList<>();
		for (final I_M_Product_Category category : productCategories)
		{
			final KeyNamePair knp = createKeyNamePair(category);
			knps.add(knp);
		}
		return knps;
	}

	@Override
	public IQuery<I_M_HU> getSubQueryFilter(final IContextAware contextProvider, final Object value)
	{
		if (value == null)
		{
			return null;
		}
		Check.assumeInstanceOf(value, KeyNamePair.class, "KeyNamePair");
		final int productCategoryId = ((KeyNamePair)value).getKey();
		if (productCategoryId < 0)
		{
			return null;
		}

		//
		// Filter Product Category
		final IQuery<I_M_Product> productSubQuery = queryBL.createQueryBuilder(I_M_Product.class, contextProvider)
				.addEqualsFilter(I_M_Product.COLUMN_M_Product_Category_ID, productCategoryId)
				.create();

		//
		// Filter HU Storage
		final IQuery<I_M_HU_Storage> huStorageSubQuery = queryBL.createQueryBuilder(I_M_HU_Storage.class, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_HU_Storage.COLUMN_M_Product_ID, I_M_Product.COLUMN_M_Product_ID, productSubQuery)
				.create();

		//
		// Filter HUs
		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU.class, contextProvider)
				.addInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID, I_M_HU_Storage.COLUMN_M_HU_ID, huStorageSubQuery);
		return queryBuilder.create();
	}

	@Override
	public ITerminalLookup getPropertyLookup(final IHUKey key)
	{
		return new SimpleTableLookup<>(I_M_Product_Category.class,
				org.compiere.model.I_M_Product_Category.COLUMNNAME_M_Product_Category_ID,
				org.compiere.model.I_M_Product_Category.COLUMNNAME_Name);
	}

	private static final int getProductCategoryIdFromValue(final Object value)
	{
		if (value instanceof KeyNamePair) // will normally be the case
		{
			final int productCategoryId = ((KeyNamePair)value).getKey();
			return productCategoryId;
		}
		else
		{
			// shall not happen
			// TODO: throw exception?
			return -1;
		}
	}

	@Override
	public boolean accept(final IHUKey key, final Object value)
	{
		// If no filtering value was specified => accept it
		if (value == null || KeyNamePair.EMPTY.equals(value))
		{
			return true;
		}

		final int productCategoryId = getProductCategoryIdFromValue(value);
		if (productCategoryId < 0)
		{
			// shall not happen
			return true;
		}

		final List<KeyNamePair> availableProductCategories = getPropertyAvailableValues(key);
		for (final KeyNamePair availableProductCategory : availableProductCategories)
		{
			final int availableProductCateogoryId = availableProductCategory.getKey();
			if (availableProductCateogoryId == productCategoryId)
			{
				return true;
			}
		}

		return false;
	}
}
