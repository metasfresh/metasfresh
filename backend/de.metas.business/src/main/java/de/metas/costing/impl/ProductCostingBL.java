package de.metas.costing.impl;

<<<<<<< HEAD
import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_Category_Acct;

=======
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.IProductCostingBL;
import de.metas.product.IProductDAO;
<<<<<<< HEAD
=======
import de.metas.product.ProductAndCategoryId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
<<<<<<< HEAD
=======
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_Category_Acct;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProductCostingBL implements IProductCostingBL
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);

<<<<<<< HEAD
	private final CCache<ProductCategoryAcctKey, ProductCategoryAcct> productCategoryAcctCache = CCache.<ProductCategoryAcctKey, ProductCategoryAcct> builder()
=======
	private final CCache<ProductCategoryAcctKey, ProductCategoryAcct> productCategoryAcctCache = CCache.<ProductCategoryAcctKey, ProductCategoryAcct>builder()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			.tableName(I_M_Product_Category_Acct.Table_Name)
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(100)
			.build();

	@Override
<<<<<<< HEAD
	public CostingLevel getCostingLevel(@NonNull final I_M_Product product, @NonNull final AcctSchema acctSchema)
=======
	public @NonNull CostingLevel getCostingLevel(@NonNull final I_M_Product product, @NonNull final AcctSchema acctSchema)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(product.getM_Product_Category_ID());
		final AcctSchemaId acctSchemaId = acctSchema.getId();
		final ProductCategoryAcct pca = getProductCategoryAcct(productCategoryId, acctSchemaId);
		if (pca.getCostingLevel() != null)
		{
			return pca.getCostingLevel();
		}

		return acctSchema.getCosting().getCostingLevel();
	}

	@Override
<<<<<<< HEAD
	public CostingLevel getCostingLevel(final ProductId productId, final AcctSchemaId acctSchemaId)
=======
	public @NonNull CostingLevel getCostingLevel(final ProductId productId, final AcctSchemaId acctSchemaId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final I_M_Product product = productDAO.getById(productId);
		final AcctSchema acctSchema = acctSchemaDAO.getById(acctSchemaId);
		return getCostingLevel(product, acctSchema);
	}

	@Override
<<<<<<< HEAD
	public CostingLevel getCostingLevel(final ProductId productId, final AcctSchema acctSchema)
=======
	public @NonNull CostingLevel getCostingLevel(final ProductId productId, final AcctSchema acctSchema)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final I_M_Product product = productDAO.getById(productId);
		return getCostingLevel(product, acctSchema);
	}

	@Override
<<<<<<< HEAD
	public CostingMethod getCostingMethod(final I_M_Product product, final AcctSchema acctSchema)
=======
	public @NonNull Map<ProductId, CostingLevel> getCostingLevels(final Set<ProductId> productIds, final AcctSchema acctSchema)
	{
		if (productIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSetMultimap<ProductCategoryId, ProductId> productIdsByProductCategoryId = productDAO.retrieveProductAndCategoryIdsByProductIds(productIds)
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						ProductAndCategoryId::getProductCategoryId,
						ProductAndCategoryId::getProductId));

		final ImmutableSet<ProductCategoryId> productCategoryIds = productIdsByProductCategoryId.keySet();
		final Collection<ProductCategoryAcct> productCategoryAccts = getProductCategoryAccts(productCategoryIds, acctSchema.getId());

		final HashMap<ProductId, CostingLevel> costingLevelsByProductId = new HashMap<>(productIds.size());
		for (final ProductCategoryAcct productCategoryAcct : productCategoryAccts)
		{
			final ProductCategoryId productCategoryId = productCategoryAcct.getProductCategoryId();
			final CostingLevel costingLevel = productCategoryAcct.getCostingLevel();

			for (final ProductId productId : productIdsByProductCategoryId.get(productCategoryId))
			{
				costingLevelsByProductId.put(productId, costingLevel);
			}
		}

		return costingLevelsByProductId;
	}

	@Override
	public @NonNull CostingMethod getCostingMethod(final I_M_Product product, final AcctSchema acctSchema)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(product.getM_Product_Category_ID());
		final ProductCategoryAcct pca = getProductCategoryAcct(productCategoryId, acctSchema.getId());
		if (pca.getCostingMethod() != null)
		{
			return pca.getCostingMethod();
		}

		return acctSchema.getCosting().getCostingMethod();
	}

	@Override
<<<<<<< HEAD
	public CostingMethod getCostingMethod(final ProductId productId, final AcctSchemaId acctSchemaId)
=======
	public @NonNull CostingMethod getCostingMethod(final ProductId productId, final AcctSchemaId acctSchemaId)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final I_M_Product product = productDAO.getById(productId);
		final AcctSchema acctSchema = acctSchemaDAO.getById(acctSchemaId);
		return getCostingMethod(product, acctSchema);
	}

	@Override
<<<<<<< HEAD
	public CostingMethod getCostingMethod(final ProductId productId, final AcctSchema acctSchema)
=======
	public @NonNull CostingMethod getCostingMethod(final ProductId productId, final AcctSchema acctSchema)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final I_M_Product product = productDAO.getById(productId);
		return getCostingMethod(product, acctSchema);
	}

	private ProductCategoryAcct getProductCategoryAcct(final ProductCategoryId productCategoryId, final AcctSchemaId acctSchemaId)
	{
		final ProductCategoryAcctKey key = ProductCategoryAcctKey.of(productCategoryId, acctSchemaId);
		return productCategoryAcctCache.getOrLoad(key, this::retrieveOrCreateAcct);
	}

<<<<<<< HEAD
	private final ProductCategoryAcct retrieveOrCreateAcct(@NonNull final ProductCategoryAcctKey key)
	{
		// NOTE: because we currently have some bugs with "ctx" on server side, we are not filtering the query by AD_Client_ID
		// but we just rely on M_Product_Category_ID/C_AcctSchema_ID.

		final IQuery<I_M_Product_Category_Acct> query = queryBL
				.createQueryBuilderOutOfTrx(I_M_Product_Category_Acct.class)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_ID, key.getProductCategoryId())
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_C_AcctSchema_ID, key.getAcctSchemaId())
				.addOnlyActiveRecordsFilter()
				.create();
		I_M_Product_Category_Acct productCategoryAcct = query.firstOnly(I_M_Product_Category_Acct.class);

		//
		// If no product category accounting record was not found and we are asked to create one on fly
		// We are asing the MProductCategory to create all that are missing
		if (productCategoryAcct == null)
		{
			final I_M_Product_Category pc = productDAO.getProductCategoryById(key.getProductCategoryId());
			InterfaceWrapperHelper.getPO(pc).insert_Accounting(
					I_M_Product_Category_Acct.Table_Name,
					I_C_AcctSchema_Default.Table_Name,
					(String)null // whereClause
			);

			// Run the query again => we expect our product category acct record to be created
			productCategoryAcct = query.firstOnlyNotNull(I_M_Product_Category_Acct.class);
		}

		return toProductCategoryAcct(productCategoryAcct);
=======
	private Collection<ProductCategoryAcct> getProductCategoryAccts(final Set<ProductCategoryId> productCategoryIds, final AcctSchemaId acctSchemaId)
	{
		final ImmutableSet<ProductCategoryAcctKey> keys = productCategoryIds.stream()
				.map(productCategoryId -> ProductCategoryAcctKey.of(productCategoryId, acctSchemaId))
				.collect(ImmutableSet.toImmutableSet());
		return productCategoryAcctCache.getAllOrLoad(keys, this::retrieveOrCreateAcctsByKeys);
	}

	private ProductCategoryAcct retrieveOrCreateAcct(@NonNull final ProductCategoryAcctKey key)
	{
		I_M_Product_Category_Acct record = retrieveProductCategoryAcctRecord(key.getProductCategoryId(), key.getAcctSchemaId()).orElse(null);

		//
		// If no product category accounting record was not found and we are asked to create one on fly
		// We are passing the MProductCategory to create all that are missing
		if (record == null)
		{
			createMissingProductCategoryAccts(key.getProductCategoryId());
			record = retrieveProductCategoryAcctRecord(key.getProductCategoryId(), key.getAcctSchemaId())
					.orElseThrow(() -> new AdempiereException("No M_Product_Category_Acct record was found/created for " + key));
		}

		return toProductCategoryAcct(record);
	}

	private Map<ProductCategoryAcctKey, ProductCategoryAcct> retrieveOrCreateAcctsByKeys(@NonNull final Set<ProductCategoryAcctKey> keys)
	{
		if (keys.isEmpty())
		{
			return ImmutableMap.of();
		}

		final IQueryBuilder<I_M_Product_Category_Acct> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_M_Product_Category_Acct.class)
				.addOnlyActiveRecordsFilter();

		final ICompositeQueryFilter<I_M_Product_Category_Acct> keysFilter = queryBuilder.addCompositeQueryFilter().setJoinOr();
		for (ProductCategoryAcctKey key : keys)
		{
			keysFilter.addCompositeQueryFilter()
					.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_ID, key.getProductCategoryId())
					.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_C_AcctSchema_ID, key.getAcctSchemaId())
					.addOnlyActiveRecordsFilter();
		}

		final ImmutableMap<ProductCategoryAcctKey, I_M_Product_Category_Acct> recordsByKey = queryBuilder.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> ProductCategoryAcctKey.of(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()), AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID())),
						record -> record
				));

		final HashMap<ProductCategoryAcctKey, ProductCategoryAcct> productCategoryAccts = new HashMap<>();
		for (ProductCategoryAcctKey key : keys)
		{
			I_M_Product_Category_Acct record = recordsByKey.get(key);

			//
			// If no product category accounting record was not found and we are asked to create one on fly
			// We are passing the MProductCategory to create all that are missing
			if (record == null)
			{
				createMissingProductCategoryAccts(key.getProductCategoryId());
				record = retrieveProductCategoryAcctRecord(key.getProductCategoryId(), key.getAcctSchemaId())
						.orElseThrow(() -> new AdempiereException("No M_Product_Category_Acct record was found/created for " + key));
			}

			productCategoryAccts.put(key, toProductCategoryAcct(record));
		}

		return productCategoryAccts;
	}

	private void createMissingProductCategoryAccts(@NonNull final ProductCategoryId productCategoryId)
	{
		final I_M_Product_Category pc = productDAO.getProductCategoryById(productCategoryId);
		InterfaceWrapperHelper.getPO(pc).insert_Accounting(
				I_M_Product_Category_Acct.Table_Name,
				I_C_AcctSchema_Default.Table_Name,
				null // whereClause
		);
	}

	private Optional<I_M_Product_Category_Acct> retrieveProductCategoryAcctRecord(
			@NonNull final ProductCategoryId productCategoryId,
			@NonNull final AcctSchemaId acctSchemaId
	)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_M_Product_Category_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_ID, productCategoryId)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.orderBy(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_Acct_ID)
				.firstOptional();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private static ProductCategoryAcct toProductCategoryAcct(@NonNull final I_M_Product_Category_Acct record)
	{
		return ProductCategoryAcct.builder()
				.productCategoryId(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.costingMethod(CostingMethod.ofNullableCode(record.getCostingMethod()))
<<<<<<< HEAD
				.costingLevel(CostingLevel.forNullableCode(record.getCostingLevel()))
=======
				.costingLevel(CostingLevel.ofNullableCode(record.getCostingLevel()))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.build();
	}

	@Value(staticConstructor = "of")
	private static class ProductCategoryAcctKey
	{
<<<<<<< HEAD
		@NonNull
		ProductCategoryId productCategoryId;
		@NonNull
		AcctSchemaId acctSchemaId;
=======
		@NonNull ProductCategoryId productCategoryId;
		@NonNull AcctSchemaId acctSchemaId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Value
	@Builder
	private static class ProductCategoryAcct
	{
<<<<<<< HEAD
		@NonNull
		ProductCategoryId productCategoryId;
		@NonNull
		AcctSchemaId acctSchemaId;
		@Nullable
		CostingMethod costingMethod;
		@Nullable
		CostingLevel costingLevel;
=======
		@NonNull ProductCategoryId productCategoryId;
		@NonNull AcctSchemaId acctSchemaId;
		@Nullable CostingMethod costingMethod;
		@Nullable CostingLevel costingLevel;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
