package de.metas.material.dispo.service.event.handler;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.compiere.util.Util;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.InventoryDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery.DateOperator;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MaterialDescriptor.MaterialDescriptorBuilder;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.util.Loggables;
import de.metas.util.time.SystemTime;

/*
 * #%L
 * metasfresh-material-dispo-service
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

@Component
@Profile(Profiles.PROFILE_MaterialDispo)
public class StockChangedEventHandler implements MaterialEventHandler<StockChangedEvent>
{

	private final CandidateRepositoryRetrieval candidateRepository;
	private final CandidateChangeService candidateChangeHandler;

	public StockChangedEventHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateChangeService candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
	}

	@Override
	public Collection<Class<? extends StockChangedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(StockChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final StockChangedEvent event)
	{

		final MaterialDescriptorQuery materialDescriptorQuery = createMaterialDescriptorQuery(event);

		final CandidatesQuery stockQuery = CandidatesQuery.builder()
				.type(CandidateType.STOCK)
				.materialDescriptorQuery(materialDescriptorQuery)
				.matchExactStorageAttributesKey(true)
				.build();

		final InventoryDetail inventoryDetail = createInventoryDetail(event);

		final Candidate latestStockRecord = candidateRepository.retrieveLatestMatchOrNull(stockQuery);
		if (latestStockRecord == null)
		{
			final BigDecimal quantityOnHand = extractQuantityOrNull(event);
			if (quantityOnHand == null)
			{
				return;
			}
			final CandidateBuilder candidateBuilder = createCandidateBuilder(event, quantityOnHand, inventoryDetail);

			final Candidate candidate = candidateBuilder
					.type(CandidateType.INVENTORY_UP)
					.build();

			candidateChangeHandler.onCandidateNewOrChange(candidate);
		}
		else
		{
			final BigDecimal quantityOnHand = event.getQtyOnHand();

			final CandidateType type = computeCandidateTypeOrNull(latestStockRecord, quantityOnHand);
			if (type == null)
			{
				return;
			}

			final int groupId = retrieveGroupIdOrZero(materialDescriptorQuery, type);

			final CandidateBuilder candidateBuilder = createCandidateBuilder(event, quantityOnHand, inventoryDetail);

			final Candidate candidate = candidateBuilder
					.groupId(groupId)
					.type(type)
					.build();

			candidateChangeHandler.onCandidateNewOrChange(candidate);
		}
	}

	private BigDecimal extractQuantityOrNull(final StockChangedEvent event)
	{
		final BigDecimal quantityOnHand = event.getQtyOnHand();
		if (quantityOnHand.signum() < 0)
		{
			Loggables.get().addLog("warning that something was out of sync since there is no existing 'latestMatch' to subtract from"); // TODO
			return null;
		}
		return quantityOnHand;
	}

	private CandidateType computeCandidateTypeOrNull(final Candidate latestStockRecord, final BigDecimal quantityOnHand)
	{
		final BigDecimal difference = latestStockRecord.getQuantity().subtract(quantityOnHand);
		if (difference.signum() == 0)
		{
			Loggables.get().addLog("Info that the event did not caise anything to change"); // TODO
			return null;
		}
		final CandidateType type = difference.signum() > 0 ? CandidateType.INVENTORY_UP : CandidateType.INVENTORY_DOWN;
		return type;
	}

	private CandidateBuilder createCandidateBuilder(
			@NonNull final StockChangedEvent event,
			@NonNull final BigDecimal quantityOnHand,
			@NonNull final BusinessCaseDetail inventoryDetail)
	{
		final MaterialDescriptor materialDescriptorBuilder = createMaterialDescriptorBuilder(event)
				.quantity(quantityOnHand)
				.build();

		final CandidateBuilder candidateBuilder = Candidate.builderForEventDescr(event.getEventDescriptor())
				.status(CandidateStatus.doc_completed)
				.businessCase(CandidateBusinessCase.INVENTORY)
				.materialDescriptor(materialDescriptorBuilder)
				.businessCaseDetail(inventoryDetail);
		return candidateBuilder;
	}

	private int retrieveGroupIdOrZero(
			@NonNull final MaterialDescriptorQuery materialDescriptorQuery,
			@NonNull final CandidateType type)
	{
		int groupId = 0;
		if (CandidateType.INVENTORY_UP.equals(type))
		{
			// see if there is a preceeding "down" record to connect with
			// that's the case when a storage attribute has schanged
			final CandidatesQuery inventoryQuery = CandidatesQuery.builder()
					.type(CandidateType.INVENTORY_DOWN)
					// .inventoryDetail(inventoryDetail) TODO
					.materialDescriptorQuery(materialDescriptorQuery)
					.matchExactStorageAttributesKey(true)
					.build();

			final Candidate preceedingInventoryRecord = candidateRepository.retrieveLatestMatchOrNull(inventoryQuery);
			if (preceedingInventoryRecord != null)
			{
				groupId = preceedingInventoryRecord.getGroupId();
			}
		}
		return groupId;
	}

	private MaterialDescriptorQuery createMaterialDescriptorQuery(StockChangedEvent event)
	{
		return MaterialDescriptorQuery.forDescriptor(
				createMaterialDescriptorBuilder(event).build(),
				DateOperator.BEFORE_OR_AT);
	}

	private MaterialDescriptorBuilder createMaterialDescriptorBuilder(StockChangedEvent event)
	{
		final ProductDescriptor productDescriptor = event.getProductDescriptor();

		final Date date = Util.coalesceSuppliers(
				() -> event.getChangeDate(),
				() -> SystemTime.asDate());

		return MaterialDescriptor.builder()
				.date(date)
				.productDescriptor(productDescriptor)
				.customerId(0)
				.warehouseId(event.getWarehouseId());
	}

	private InventoryDetail createInventoryDetail(StockChangedEvent event)
	{
		return InventoryDetail.builder()
				// .plannedQty(plannedQty)
				// .resetStockAdPinstanceId(resetStockAdPinstanceId)
				// .stockId(stockId)
				// .inventoryLineId(inventoryLineId)
				.build();

		// TODO Auto-generated method stub
	}

}
