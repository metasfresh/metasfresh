package de.metas.handlingunits.client.terminal.editor.model.filter.impl;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.KeyNamePair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.form.terminal.ITerminalLookup;
import de.metas.adempiere.form.terminal.lookup.SimpleTableLookup;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;

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
		final ImmutableSet<ProductId> productIds = getProductStorages(key)
				.stream()
				.map(IProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		return getProductCategoriesKeyNamePairs(productIds);
	}

	@Override
	public List<KeyNamePair> getPropertyAvailableValues(final IQueryBuilder<I_M_HU> queryBuilder)
	{
		final ImmutableSet<ProductId> productIds = retrieveProductIds(queryBuilder);
		return getProductCategoriesKeyNamePairs(productIds);
	}

	private ImmutableSet<ProductId> retrieveProductIds(final IQueryBuilder<I_M_HU> queryBuilder)
	{
		return queryBuilder
				.andCollectChildren(I_M_HU_Storage.COLUMN_M_HU_ID, I_M_HU_Storage.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_M_HU_Storage.COLUMNNAME_M_Product_ID, Integer.class)
				.stream()
				.map(ProductId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private List<KeyNamePair> getProductCategoriesKeyNamePairs(final Set<ProductId> productIds)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);

		return productIds.stream()
				.map(productsRepo::retrieveProductCategoryByProductId)
				.distinct()
				.map(productCategoryId -> KeyNamePair.of(productCategoryId.getRepoId(), productsRepo.getProductCategoryNameById(productCategoryId)))
				.collect(ImmutableList.toImmutableList());

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
				.addInSubQueryFilter(I_M_HU_Storage.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID, productSubQuery)
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
