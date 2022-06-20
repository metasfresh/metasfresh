/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2022 metas GmbH
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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.util.time.SystemTime;
import de.metas.logging.LogManager;
import de.metas.material.commons.attributes.AttributesKeyPatternsUtil;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.Product;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.async.Debouncer;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_MD_Available_For_Sales;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AvailableForSalesService
{
	private static final Logger logger = LogManager.getLogger(AvailableForSalesService.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final AvailableForSalesConfigRepo availableForSalesConfigRepo;
	private final AvailableForSalesRepository availableForSalesRepository;

	private final Debouncer<EnqueueAvailableForSalesRequest> syncProductDebouncer;

	public AvailableForSalesService(
			@NonNull final AvailableForSalesConfigRepo availableForSalesConfigRepo,
			@NonNull final AvailableForSalesRepository availableForSalesRepository)
	{
		this.availableForSalesConfigRepo = availableForSalesConfigRepo;
		this.availableForSalesRepository = availableForSalesRepository;

		this.syncProductDebouncer = Debouncer.<EnqueueAvailableForSalesRequest>builder()
				.name("syncAvailableForSalesDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue("de.metas.material.cockpit.availableforsales.AvailableForSalesService.debouncer.bufferMaxSize", 100))
				.delayInMillis(sysConfigBL.getIntValue("de.metas.material.cockpit.availableforsales.AvailableForSalesService.debouncer.delayInMillis", 5000))
				.distinct(true)
				.consumer(this::syncAvailableForSales)
				.build();
	}

	public void enqueueAvailableForSalesRequest(@NonNull final EnqueueAvailableForSalesRequest enqueueAvailableForSalesRequest)
	{
		final AvailableForSalesQuery availableForSalesQuery = enqueueAvailableForSalesRequest.getAvailableForSalesQuery();

		Loggables.withLogger(logger, Level.DEBUG).addLog("ProductId: {} and AttributesKey: {} enqueued to be synced.",
														 availableForSalesQuery.getProductId(),
														 availableForSalesQuery.getStorageAttributesKeyPattern().getSqlLikeString());

		syncProductDebouncer.add(enqueueAvailableForSalesRequest);
	}

	public void syncAvailableForSalesForProduct(@NonNull final Product product)
	{
		final ImmutableList<AvailableForSalesQuery> availableForSalesQueries = buildAvailableForSalesQueries(product);

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
	private ImmutableList<AvailableForSalesQuery> buildAvailableForSalesQueries(@NonNull final Product product)
	{
		final OrgId orgId = product.getOrgId();

		if (OrgId.ANY.equals(orgId))
		{
			return orgDAO.retrieveClientOrgs(Env.getAD_Client_ID())
					.stream()
					.map(I_AD_Org::getAD_Org_ID)
					.map(OrgId::ofRepoId)
					.map(currentOrgId -> createAvailableForSalesQuery(product, currentOrgId))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			return createAvailableForSalesQuery(product, orgId)
					.map(ImmutableList::of)
					.orElseGet(ImmutableList::of);
		}
	}

	private void syncAvailableForSales(@NonNull final Collection<EnqueueAvailableForSalesRequest> requests)
	{
		if (requests.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("SyncAvailableForSalesRequest list is empty! No action is performed!");
			return;
		}

		for (final EnqueueAvailableForSalesRequest request : requests)
		{

			try (final IAutoCloseable autoCloseable = Env.switchContext(request.getCtx()))
			{
				syncAvailableForSalesTable(request.getAvailableForSalesQuery());
			}
		}
	}

	private void syncAvailableForSalesTable(@NonNull final AvailableForSalesQuery availableForSalesQuery)
	{
		final ImmutableList<AvailableForSalesResult> availableForSalesResults = computeAvailableForSales(AvailableForSalesMultiQuery.of(availableForSalesQuery))
				.getAvailableForSalesResults();

		final ImmutableList<I_MD_Available_For_Sales> availableForSalesRecords = availableForSalesRepository.getRecordsByQuery(availableForSalesQuery);

		final ImmutableMap<I_MD_Available_For_Sales, AvailableForSalesResult> recordsToBeUpdated =
				mapRecordsWithResultBasedOnASI(availableForSalesRecords, availableForSalesResults);

		recordsToBeUpdated
				.forEach((record, result) -> {
					record.setQtyToBeShipped(result.getQuantities().getQtyToBeShipped());
					record.setQtyOnHandStock(result.getQuantities().getQtyOnHandStock());

					availableForSalesRepository.save(record);
				});

		final Set<I_MD_Available_For_Sales> recordsToBeDeleted = new HashSet<>(availableForSalesRecords);
		recordsToBeDeleted.removeAll(recordsToBeUpdated.keySet());
		recordsToBeDeleted.forEach(availableForSalesRepository::delete);

		final Set<AvailableForSalesResult> recordsToBeCreated = new HashSet<>(availableForSalesResults);
		recordsToBeCreated.removeAll(recordsToBeUpdated.values());
		recordsToBeCreated.stream()
				.map(this::buildCreateAvailableForSalesRequest)
				.forEach(availableForSalesRepository::create);
	}

	@NonNull
	private ImmutableMap<I_MD_Available_For_Sales, AvailableForSalesResult> mapRecordsWithResultBasedOnASI(
			@NonNull final ImmutableList<I_MD_Available_For_Sales> availableForSalesRecords,
			@NonNull final ImmutableList<AvailableForSalesResult> availableForSalesResults)
	{
		final ImmutableMap.Builder<I_MD_Available_For_Sales, AvailableForSalesResult> recordsToBeUpdatedMapBuilder = ImmutableMap.builder();

		for (final I_MD_Available_For_Sales availableForSales : availableForSalesRecords)
		{
			for (final AvailableForSalesResult availableForSalesResult : availableForSalesResults)
			{
				if (availableForSales.getStorageAttributesKey().equals(availableForSalesResult.getStorageAttributesKey().getAsString()))
				{
					recordsToBeUpdatedMapBuilder.put(availableForSales, availableForSalesResult);
				}
			}
		}

		return recordsToBeUpdatedMapBuilder.build();
	}

	@NonNull
	private AvailableForSalesConfig getAvailableForSalesConfig(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		return availableForSalesConfigRepo.getConfig(
				AvailableForSalesConfigRepo.ConfigQuery.builder()
						.clientId(clientId)
						.orgId(orgId)
						.build());
	}

	@NonNull
	private Optional<AvailableForSalesQuery> createAvailableForSalesQuery(
			@NonNull final Product product,
			@NonNull final OrgId orgId)
	{
		final AvailableForSalesConfig config = getAvailableForSalesConfig(Env.getClientId(), orgId);

		if (!config.isFeatureEnabled())
		{
			return Optional.empty();
		}

		return Optional.of(AvailableForSalesQuery
								   .builder()
								   .dateOfInterest(SystemTime.asInstant())
								   .productId(product.getId())
								   .storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(AttributesKey.ALL))
								   .orgId(orgId)
								   .shipmentDateLookAheadHours(config.getShipmentDateLookAheadHours())
								   .salesOrderLookBehindHours(config.getSalesOrderLookBehindHours())
								   .build());
	}

	@NonNull
	private CreateAvailableForSalesRequest buildCreateAvailableForSalesRequest(@NonNull final AvailableForSalesResult availableForSalesResult)
	{
		return CreateAvailableForSalesRequest.builder()
				.productId(availableForSalesResult.getProductId())
				.storageAttributesKey(availableForSalesResult.getStorageAttributesKey())
				.orgId(availableForSalesResult.getOrgId())
				.qtyOnHandStock(availableForSalesResult.getQuantities().getQtyOnHandStock())
				.qtyToBeShipped(availableForSalesResult.getQuantities().getQtyToBeShipped())
				.build();
	}
}
