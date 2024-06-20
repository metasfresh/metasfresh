package de.metas.material.cockpit.availableforsales;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.material.cockpit.availableforsales.AvailableForSalesMultiResult.AvailableForSalesMultiResultBuilder;
import de.metas.material.cockpit.availableforsales.AvailableForSalesResult.Quantities;
import de.metas.material.cockpit.model.I_MD_Available_For_Sales_QueryResult;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static java.math.BigDecimal.ZERO;

/*
 * #%L
 * metasfresh-available-for-sales
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class AvailableForSalesRepository
{
	private static final String SYSCONFIG_AVAILABILITY_INFO_ATTRIBUTES_KEYS = "de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.AvailabilityInfo.AttributesKeys";

	public AvailableForSalesMultiResult getBy(@NonNull final AvailableForSalesMultiQuery availableForSalesMultiQuery)
	{
		final AvailableForSalesMultiResultBuilder multiResult = AvailableForSalesMultiResult.builder();
		if (availableForSalesMultiQuery.getAvailableForSalesQueries().isEmpty())
		{
			return multiResult.build(); // empty query => empty result
		}

		final IQuery<I_MD_Available_For_Sales_QueryResult> //
				dbQuery = AvailableForSalesSqlHelper.createDBQueryForAvailableForSalesMultiQuery(availableForSalesMultiQuery);

		final List<I_MD_Available_For_Sales_QueryResult> records = dbQuery.list();

		final List<AvailableForSalesQuery> singleQueries = availableForSalesMultiQuery.getAvailableForSalesQueries();

		final ImmutableListMultimap<Integer, I_MD_Available_For_Sales_QueryResult> //
				queryNo2records = Multimaps.index(records, I_MD_Available_For_Sales_QueryResult::getQueryNo);

		for (int queryNo = 0; queryNo < singleQueries.size(); queryNo++)
		{
			BigDecimal qtyOnHandStock = ZERO;
			BigDecimal qtyToBeShipped = ZERO;
			String storageAttributesKey = null;

			for (final I_MD_Available_For_Sales_QueryResult recordForQueryNo : queryNo2records.get(queryNo))
			{
				qtyOnHandStock = qtyOnHandStock.add(recordForQueryNo.getQtyOnHandStock());
				qtyToBeShipped = qtyToBeShipped.add(recordForQueryNo.getQtyToBeShipped());
				storageAttributesKey = recordForQueryNo.getStorageAttributesKey();
			}

			final AvailableForSalesQuery singleQuery = singleQueries.get(queryNo);
			final AvailableForSalesResult result = createSingleResult(qtyOnHandStock, qtyToBeShipped, AttributesKey.ofString(storageAttributesKey), singleQuery);
			multiResult.availableForSalesResult(result);
		}

		return multiResult.build();
	}

	private AvailableForSalesResult createSingleResult(
			@NonNull final BigDecimal qtyOnHandStock,
			@NonNull final BigDecimal qtyToBeShipped,
			@NonNull final AttributesKey storageAttributesKey,
			@NonNull final AvailableForSalesQuery singleQuery)
	{
		return AvailableForSalesResult
				.builder()
				.availableForSalesQuery(singleQuery)
				.productId(singleQuery.getProductId())
				.storageAttributesKey(storageAttributesKey)
				.quantities(Quantities.builder()
						.qtyOnHandStock(qtyOnHandStock)
						.qtyToBeShipped(qtyToBeShipped).build())
				.build();
	}

	public AvailableForSalesLookupResult retrieveAvailableStock(@NonNull final AvailableForSalesMultiQuery availableForSalesMultiQuery)
	{
		final AvailableForSaleResultBuilder result = AvailableForSaleResultBuilder.createEmptyWithPredefinedBuckets(availableForSalesMultiQuery);
		if (availableForSalesMultiQuery.getAvailableForSalesQueries().isEmpty())
		{
			return result.build(); // empty query => empty result
		}
		final IQuery<I_MD_Available_For_Sales_QueryResult> //
				dbQuery = AvailableForSalesSqlHelper.createDBQueryForAvailableForSalesMultiQuery(availableForSalesMultiQuery);

		final List<I_MD_Available_For_Sales_QueryResult> records = dbQuery.list();

		final ImmutableList<AddToResultGroupRequest> requests = records
				.stream()
				.filter(req -> ZERO.compareTo(req.getQtyOnHandStock()) < 0 || ZERO.compareTo(req.getQtyToBeShipped()) < 0)
				.map(AvailableForSalesRepository::createAddToResultGroupRequest)
				.collect(ImmutableList.toImmutableList());
		requests.forEach(result::addQtyToAllMatchingGroups);
		return result.build();
	}

	private static AddToResultGroupRequest createAddToResultGroupRequest(final I_MD_Available_For_Sales_QueryResult result)
	{
		return AddToResultGroupRequest.builder()
				.productId(ProductId.ofRepoId(result.getM_Product_ID()))
				.storageAttributesKey(AttributesKey.ofString(result.getStorageAttributesKey()))
				.qtyToBeShipped(result.getQtyToBeShipped())
				.qtyOnHandStock(result.getQtyOnHandStock())
				.queryNo(result.getQueryNo())
				.build();
	}

	public Set<AttributesKeyPattern> getPredefinedStorageAttributeKeys()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int clientId = Env.getAD_Client_ID(Env.getCtx());
		final int orgId = Env.getAD_Org_ID(Env.getCtx());

		final String storageAttributesKeys = sysConfigBL.getValue(
				SYSCONFIG_AVAILABILITY_INFO_ATTRIBUTES_KEYS,
				AttributesKey.ALL.getAsString(),
				clientId, orgId);

		return AttributesKeyPatternsUtil.parseCommaSeparatedString(storageAttributesKeys);
	}

}
