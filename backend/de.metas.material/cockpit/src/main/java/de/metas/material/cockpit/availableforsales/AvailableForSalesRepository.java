package de.metas.material.cockpit.availableforsales;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.material.cockpit.availableforsales.AvailableForSalesMultiResult.AvailableForSalesMultiResultBuilder;
import de.metas.material.cockpit.availableforsales.AvailableForSalesResult.Quantities;
import de.metas.material.cockpit.model.I_MD_Available_For_Sales_QueryResult;
import org.adempiere.mm.attributes.keys.AttributesKeyPattern;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_MD_Available_For_Sales;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public AvailableForSalesMultiResult computeAvailableForSales(
			@NonNull final AvailableForSalesMultiQuery availableForSalesMultiQuery,
			@NonNull final Function<ProductId, UomId> stockUOMProvider)
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
			final AvailableForSalesQuery singleQuery = singleQueries.get(queryNo);

			final UomId stockUOMId = stockUOMProvider.apply(singleQuery.getProductId());

			queryNo2records.get(queryNo)
					.stream()
					.peek(result -> validateResultUOM(result, stockUOMId))
					.map(resultRow -> createSingleResult(resultRow, singleQuery))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.forEach(multiResult::availableForSalesResult);
		}

		return multiResult.build();
	}

	@NonNull
	public ImmutableList<I_MD_Available_For_Sales> getRecordsByQuery(@NonNull final RetrieveAvailableForSalesQuery retrieveAvailableForSalesQuery)
	{
		final IQueryBuilder<I_MD_Available_For_Sales> queryBuilder = queryBL.createQueryBuilder(I_MD_Available_For_Sales.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Available_For_Sales.COLUMNNAME_M_Product_ID, retrieveAvailableForSalesQuery.getProductId());

		if (retrieveAvailableForSalesQuery.getOrgId() != null)
		{
			queryBuilder.addEqualsFilter(I_MD_Available_For_Sales.COLUMNNAME_AD_Org_ID, retrieveAvailableForSalesQuery.getOrgId());

		}

		if (retrieveAvailableForSalesQuery.getStorageAttributesKeyPattern() != null)
		{
			queryBuilder.filter(ASIAvailableForSalesAttributesKeyFilter.matchingAttributes(retrieveAvailableForSalesQuery.getStorageAttributesKeyPattern()));
		}

		return queryBuilder
				.create()
				.listImmutable(I_MD_Available_For_Sales.class);
	}

	public void delete(@NonNull final I_MD_Available_For_Sales availableForSales)
	{
		InterfaceWrapperHelper.deleteRecord(availableForSales);
	}

	public void create(@NonNull final CreateAvailableForSalesRequest createAvailableForSalesRequest)
	{
		final I_MD_Available_For_Sales availableForSales = InterfaceWrapperHelper.newInstance(I_MD_Available_For_Sales.class);

		availableForSales.setStorageAttributesKey(createAvailableForSalesRequest.getStorageAttributesKey().getAsString());
		availableForSales.setM_Product_ID(createAvailableForSalesRequest.getProductId().getRepoId());
		availableForSales.setQtyToBeShipped(createAvailableForSalesRequest.getQtyToBeShipped());
		availableForSales.setQtyOnHandStock(createAvailableForSalesRequest.getQtyOnHandStock());
		availableForSales.setAD_Org_ID(createAvailableForSalesRequest.getOrgId().getRepoId());
		availableForSales.setIsActive(true);

		save(availableForSales);
	}

	public void save(@NonNull final I_MD_Available_For_Sales availableForSalesRecord)
	{
		saveRecord(availableForSalesRecord);
	}

	@Nullable
	public I_MD_Available_For_Sales getByUniqueKey(
			@NonNull final ProductId productId,
			@NonNull final AttributesKey storageAttributesKey,
			@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_MD_Available_For_Sales.class)
				.addEqualsFilter(I_MD_Available_For_Sales.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_MD_Available_For_Sales.COLUMNNAME_StorageAttributesKey, storageAttributesKey.getAsString())
				.addEqualsFilter(I_MD_Available_For_Sales.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.firstOnly(I_MD_Available_For_Sales.class);
	}

	@NonNull
	private Optional<AvailableForSalesResult> createSingleResult(
			@NonNull final I_MD_Available_For_Sales_QueryResult queryResultRow,
			@NonNull final AvailableForSalesQuery singleQuery)
	{
		final BigDecimal qtyOnHandStock = queryResultRow.getQtyOnHandStock();
		final BigDecimal qtyToBeShipped = queryResultRow.getQtyToBeShipped();

		if (ZERO.equals(qtyOnHandStock) && ZERO.equals(qtyToBeShipped))
		{
			return Optional.empty();
		}

		final String storageAttributesKey = queryResultRow.getStorageAttributesKey();

		return Optional.of(AvailableForSalesResult
								   .builder()
								   .availableForSalesQuery(singleQuery)
								   .productId(singleQuery.getProductId())
								   .storageAttributesKey(AttributesKey.ofString(storageAttributesKey))
								   .orgId(OrgId.ofRepoId(queryResultRow.getAD_Org_ID()))
								   .quantities(Quantities.builder()
													   .qtyOnHandStock(qtyOnHandStock)
													   .qtyToBeShipped(qtyToBeShipped)
													   .build())
								   .build());
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

	private static void validateResultUOM(@NonNull final I_MD_Available_For_Sales_QueryResult result, @NonNull final UomId stockUOMId)
	{
		if (result.getC_UOM_ID() != stockUOMId.getRepoId())
		{
			throw new AdempiereException("MD_Available_For_Sales_QueryResult is not in stock uom!")
					.appendParametersToMessage()
					.setParameter("Result.C_UOM_ID", result.getC_UOM_ID())
					.setParameter("stockUOMId", stockUOMId.getRepoId());
		}
	}
}
