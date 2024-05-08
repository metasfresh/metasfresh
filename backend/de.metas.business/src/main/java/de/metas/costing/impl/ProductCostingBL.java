package de.metas.costing.impl;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_Category_Acct;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.IProductCostingBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

public class ProductCostingBL implements IProductCostingBL
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);

	private final CCache<ProductCategoryAcctKey, ProductCategoryAcct> productCategoryAcctCache = CCache.<ProductCategoryAcctKey, ProductCategoryAcct> builder()
			.tableName(I_M_Product_Category_Acct.Table_Name)
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(100)
			.build();

	@Override
	public CostingLevel getCostingLevel(@NonNull final I_M_Product product, @NonNull final AcctSchema acctSchema)
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
	public CostingLevel getCostingLevel(final ProductId productId, final AcctSchemaId acctSchemaId)
	{
		final I_M_Product product = productDAO.getById(productId);
		final AcctSchema acctSchema = acctSchemaDAO.getById(acctSchemaId);
		return getCostingLevel(product, acctSchema);
	}

	@Override
	public CostingLevel getCostingLevel(final ProductId productId, final AcctSchema acctSchema)
	{
		final I_M_Product product = productDAO.getById(productId);
		return getCostingLevel(product, acctSchema);
	}

	@Override
	public CostingMethod getCostingMethod(final I_M_Product product, final AcctSchema acctSchema)
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
	public CostingMethod getCostingMethod(final ProductId productId, final AcctSchemaId acctSchemaId)
	{
		final I_M_Product product = productDAO.getById(productId);
		final AcctSchema acctSchema = acctSchemaDAO.getById(acctSchemaId);
		return getCostingMethod(product, acctSchema);
	}

	@Override
	public CostingMethod getCostingMethod(final ProductId productId, final AcctSchema acctSchema)
	{
		final I_M_Product product = productDAO.getById(productId);
		return getCostingMethod(product, acctSchema);
	}

	private ProductCategoryAcct getProductCategoryAcct(final ProductCategoryId productCategoryId, final AcctSchemaId acctSchemaId)
	{
		final ProductCategoryAcctKey key = ProductCategoryAcctKey.of(productCategoryId, acctSchemaId);
		return productCategoryAcctCache.getOrLoad(key, this::retrieveOrCreateAcct);
	}

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
	}

	private static ProductCategoryAcct toProductCategoryAcct(@NonNull final I_M_Product_Category_Acct record)
	{
		return ProductCategoryAcct.builder()
				.productCategoryId(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.costingMethod(CostingMethod.ofNullableCode(record.getCostingMethod()))
				.costingLevel(CostingLevel.forNullableCode(record.getCostingLevel()))
				.build();
	}

	@Value(staticConstructor = "of")
	private static class ProductCategoryAcctKey
	{
		@NonNull
		ProductCategoryId productCategoryId;
		@NonNull
		AcctSchemaId acctSchemaId;
	}

	@Value
	@Builder
	private static class ProductCategoryAcct
	{
		@NonNull
		ProductCategoryId productCategoryId;
		@NonNull
		AcctSchemaId acctSchemaId;
		@Nullable
		CostingMethod costingMethod;
		@Nullable
		CostingLevel costingLevel;
	}
}
