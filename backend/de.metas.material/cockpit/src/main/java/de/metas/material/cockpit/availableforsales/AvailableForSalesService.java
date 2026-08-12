/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.cockpit.availableforsales;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.common.util.time.SystemTime;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.material.cockpit.availableforsales.event.EnqueueAvailableForSalesPublisher;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.Product;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_MD_Available_For_Sales;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvailableForSalesService
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@NonNull private final AvailableForSalesConfigRepo availableForSalesConfigRepo;
	@NonNull private final AvailableForSalesRepository availableForSalesRepository;
	@NonNull private final EnqueueAvailableForSalesPublisher availableForSalesEventPublisher;

	public static AvailableForSalesService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new AvailableForSalesService(
				new AvailableForSalesConfigRepo(),
				new AvailableForSalesRepository(),
				new EnqueueAvailableForSalesPublisher(new PlainEventBusFactory())
		);
	}

	public void enqueueAvailableForSalesRequestAfterCommit(@NonNull final EnqueueAvailableForSalesRequest request)
	{
		availableForSalesEventPublisher.publishAfterCommit(request);
	}

	public void syncAvailableForSalesForProduct(@NonNull final Product product, @NonNull ClientId clientId)
	{
		final ImmutableList<AvailableForSalesQuery> availableForSalesQueries = buildAvailableForSalesQueries(product, clientId);

		for (final AvailableForSalesQuery availableForSalesQuery : availableForSalesQueries)
		{
			syncAvailableForSalesTable(availableForSalesQuery);
		}
	}

	@NonNull
	public AvailableForSalesMultiResult computeAvailableForSales(@NonNull final AvailableForSalesMultiQuery query)
	{
		return availableForSalesRepository.computeAvailableForSales(query, productBL::getStockUOMId);
	}

	@NonNull
	private ImmutableList<AvailableForSalesQuery> buildAvailableForSalesQueries(@NonNull final Product product, @NonNull ClientId clientId)
	{
		final OrgId orgId = product.getOrgId();
		if (orgId.isAny())
		{
			return orgDAO.retrieveClientOrgs(clientId)
					.stream()
					.map(I_AD_Org::getAD_Org_ID)
					.map(OrgId::ofRepoId)
					.map(currentOrgId -> createAvailableForSalesQuery(product, ClientAndOrgId.ofClientAndOrg(clientId, currentOrgId)))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			return createAvailableForSalesQuery(product, ClientAndOrgId.ofClientAndOrg(clientId, orgId))
					.map(ImmutableList::of)
					.orElseGet(ImmutableList::of);
		}
	}

	public void syncAvailableForSalesTable(@NonNull final AvailableForSalesQuery availableForSalesQuery)
	{
		final ImmutableList<AvailableForSalesResult> availableForSalesComputationResults = computeAvailableForSales(AvailableForSalesMultiQuery.of(availableForSalesQuery))
				.getAvailableForSalesResults();

		final ImmutableMap<AvailableForSalesId, I_MD_Available_For_Sales> availableForSalesIds2Records = getRecordsForQuery(availableForSalesQuery);

		final AvailableForSalesMergeResult availableForSalesMergeResult = computeAvailableForSalesMergeResult(availableForSalesIds2Records, availableForSalesComputationResults);

		for (final AvailableForSalesResult availableForSalesResult : availableForSalesMergeResult.getResultsToInsert())
		{
			saveResult(availableForSalesResult);
		}

		for (final I_MD_Available_For_Sales availableForSales : availableForSalesMergeResult.getRecordsToDelete())
		{
			availableForSalesRepository.delete(availableForSales);
		}

		availableForSalesMergeResult.getRecordId2Result().forEach((recordId, result) -> {
			final I_MD_Available_For_Sales toBeUpdated = availableForSalesIds2Records.get(recordId);

			toBeUpdated.setQtyToBeShipped(result.getQuantities().getQtyToBeShipped());
			toBeUpdated.setQtyOnHandStock(result.getQuantities().getQtyOnHandStock());

			availableForSalesRepository.save(toBeUpdated);
		});
	}

	@NonNull
	private Optional<AvailableForSalesQuery> createAvailableForSalesQuery(
			@NonNull final Product product,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final AvailableForSalesConfig config = availableForSalesConfigRepo.getConfig(clientAndOrgId);

		if (!config.isFeatureEnabled())
		{
			return Optional.empty();
		}

		return Optional.of(AvailableForSalesQuery
				.builder()
				.dateOfInterest(SystemTime.asInstant())
				.productId(product.getId())
				.storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(AttributesKey.ALL))
				.clientAndOrgId(clientAndOrgId)
				.shipmentDateLookAheadHours(config.getShipmentDateLookAheadHours())
				.salesOrderLookBehindHours(config.getSalesOrderLookBehindHours())
				.build());
	}

	private void saveResult(@NonNull final AvailableForSalesResult result)
	{
		final CreateAvailableForSalesRequest createAvailableForSalesRequest = buildCreateAvailableForSalesRequest(result);

		availableForSalesRepository.create(createAvailableForSalesRequest);
	}

	@NonNull
	private ImmutableMap<AvailableForSalesId, I_MD_Available_For_Sales> getRecordsForQuery(@NonNull final AvailableForSalesQuery availableForSalesQuery)
	{
		final RetrieveAvailableForSalesQuery retrieveAvailableForSalesQuery = buildRetrieveAvailableForSalesQuery(availableForSalesQuery);

		return Maps.uniqueIndex(availableForSalesRepository.getRecordsByQuery(retrieveAvailableForSalesQuery),
				record -> AvailableForSalesId.ofRepoId(record.getMD_Available_For_Sales_ID()));
	}

	@NonNull
	private static CreateAvailableForSalesRequest buildCreateAvailableForSalesRequest(@NonNull final AvailableForSalesResult availableForSalesResult)
	{
		return CreateAvailableForSalesRequest.builder()
				.productId(availableForSalesResult.getProductId())
				.storageAttributesKey(availableForSalesResult.getStorageAttributesKey())
				.orgId(availableForSalesResult.getOrgId())
				.qtyOnHandStock(availableForSalesResult.getQuantities().getQtyOnHandStock())
				.qtyToBeShipped(availableForSalesResult.getQuantities().getQtyToBeShipped())
				.warehouseId(availableForSalesResult.getWarehouseId())
				.build();
	}

	@NonNull
	private static RetrieveAvailableForSalesQuery buildRetrieveAvailableForSalesQuery(@NonNull final AvailableForSalesQuery availableForSalesQuery)
	{
		return RetrieveAvailableForSalesQuery.builder()
				.productId(availableForSalesQuery.getProductId())
				.orgId(availableForSalesQuery.getOrgId())
				.storageAttributesKeyPattern(availableForSalesQuery.getStorageAttributesKeyPattern())
				.warehouseId(availableForSalesQuery.getWarehouseId())
				.build();
	}

	@NonNull
	private static AvailableForSalesMergeResult computeAvailableForSalesMergeResult(
			@NonNull final ImmutableMap<AvailableForSalesId, I_MD_Available_For_Sales> availableForSalesIds2Records,
			@NonNull final ImmutableList<AvailableForSalesResult> availableForSalesResults)
	{
		final ImmutableMap.Builder<AvailableForSalesId, AvailableForSalesResult> recordId2ResultCollector = ImmutableMap.builder();
		final ImmutableList.Builder<AvailableForSalesResult> resultsToInsertCollector = ImmutableList.builder();

		for (final AvailableForSalesResult availableForSalesResult : availableForSalesResults)
		{
			AvailableForSalesId idForCurrentResult = null;

			final Iterator<AvailableForSalesId> availableForSalesIdIterator = availableForSalesIds2Records.keySet().iterator();

			while (availableForSalesIdIterator.hasNext() && idForCurrentResult == null)
			{
				final AvailableForSalesId availableForSalesId = availableForSalesIdIterator.next();

				final I_MD_Available_For_Sales availableForSalesRecord = availableForSalesIds2Records.get(availableForSalesId);

				if (availableForSalesRecord.getStorageAttributesKey().equals(availableForSalesResult.getStorageAttributesKey().getAsString()) &&
						availableForSalesRecord.getM_Warehouse_ID() == availableForSalesResult.getWarehouseId().getRepoId())
				{
					idForCurrentResult = AvailableForSalesId.ofRepoId(availableForSalesRecord.getMD_Available_For_Sales_ID());
				}
			}

			if (idForCurrentResult == null)
			{
				resultsToInsertCollector.add(availableForSalesResult);
			}
			else
			{
				recordId2ResultCollector.put(idForCurrentResult, availableForSalesResult);
			}
		}

		final Map<AvailableForSalesId, AvailableForSalesResult> recordId2Result = recordId2ResultCollector.build();

		final List<I_MD_Available_For_Sales> recordsToBeDeleted = availableForSalesIds2Records
				.entrySet()
				.stream()
				.filter(id2Record -> !recordId2Result.containsKey(id2Record.getKey()))
				.map(Map.Entry::getValue)
				.collect(ImmutableList.toImmutableList());

		return AvailableForSalesMergeResult.builder()
				.recordId2Result(recordId2Result)
				.recordsToDelete(recordsToBeDeleted)
				.resultsToInsert(resultsToInsertCollector.build())
				.build();
	}

	@Value
	@Builder
	private static class AvailableForSalesMergeResult
	{
		@NonNull
		Map<AvailableForSalesId, AvailableForSalesResult> recordId2Result;

		@NonNull
		List<AvailableForSalesResult> resultsToInsert;

		@NonNull
		List<I_MD_Available_For_Sales> recordsToDelete;
	}
}
