package de.metas.material.dispo.service.event.handler;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;

import ch.qos.logback.classic.Level;
import de.metas.common.util.time.SystemTime;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.DateAndSeqNo.Operator;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MaterialDescriptor.MaterialDescriptorBuilder;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.material.event.stock.StockChangedEvent.StockChangeDetails;
import de.metas.util.Loggables;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;

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
	private static final Logger logger = LogManager.getLogger(StockChangedEventHandler.class);

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

	/**
	 * Only handles the event if it's coming from an invocation of the reset-stock process.
	 * It the other cases, the material dispo is updated from the {@link TransactionEventHandler}.
	 */
	@Override
	public void handleEvent(@NonNull final StockChangedEvent event)
	{
		if (event.getStockChangeDetails() == null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("The event has no stockChangeDetails; -> nothing to do");
			return;
		}
		if (event.getStockChangeDetails().getResetStockPInstanceId() == null
				|| event.getStockChangeDetails().getResetStockPInstanceId().getRepoId() <= 0)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("The event has no stockChangeDetails.resetStockPInstanceId; -> nothing to do");
			return;
		}

		final MaterialDescriptorQuery materialDescriptorQuery = createMaterialDescriptorQuery(event);

		final CandidatesQuery stockQuery = CandidatesQuery.builder()
				.type(CandidateType.STOCK)
				.materialDescriptorQuery(materialDescriptorQuery)
				.matchExactStorageAttributesKey(true)
				.build();

		final Candidate candidate;

		final TransactionDetail stockChangeDetail = createStockChangeDetail(event);

		final Candidate latestStockRecord = candidateRepository.retrieveLatestMatchOrNull(stockQuery);
		if (latestStockRecord == null)
		{
			final BigDecimal quantityOnHand = extractQuantityIfPositive(event);
			if (quantityOnHand == null)
			{
				return;
			}
			final CandidateBuilder candidateBuilder = createCandidateBuilder(
					event,
					quantityOnHand,
					stockChangeDetail);

			candidate = candidateBuilder
					.type(CandidateType.INVENTORY_UP)
					.build();
		}
		else
		{
			// we work with the delta to the predecessor candidate, so that we can invoke our existing candidateChangeHandler implementation
			final BigDecimal qtyDifference = event
					.getQtyOnHand()
					.subtract(latestStockRecord.getQuantity());

			final CandidateType type = computeCandidateTypeOrNull(qtyDifference);
			if (type == null)
			{
				return;
			}

			final MaterialDispoGroupId groupId = retrieveGroupIdOrNull(
					materialDescriptorQuery,
					type,
					stockChangeDetail.getTransactionId());

			final CandidateBuilder candidateBuilder = createCandidateBuilder(
					event,
					qtyDifference.abs(), // also in case of INVENTORY_DOWN, the engine expects a positive qty
					stockChangeDetail);

			candidate = candidateBuilder
					.groupId(groupId)
					.type(type)
					.build();
		}
		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}

	private BigDecimal extractQuantityIfPositive(@NonNull final StockChangedEvent event)
	{
		final BigDecimal quantityOnHand = event.getQtyOnHand();
		if (quantityOnHand.signum() <= 0)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Warning: something was out of sync since there is no existing 'latestMatch' with a qty to reduce");
			return null;
		}
		return quantityOnHand;
	}

	private CandidateType computeCandidateTypeOrNull(
			@NonNull final BigDecimal quantity)
	{
		if (quantity.signum() == 0)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("The event's quantity is what was already expected; nothing to do");
			return null;
		}
		return quantity.signum() > 0 ? CandidateType.INVENTORY_UP : CandidateType.INVENTORY_DOWN;
	}

	private CandidateBuilder createCandidateBuilder(
			@NonNull final StockChangedEvent event,
			@NonNull final BigDecimal quantity,
			@NonNull final TransactionDetail stockChangeDetail)
	{
		final MaterialDescriptor materialDescriptorBuilder = createMaterialDescriptorBuilder(event)
				.quantity(quantity)
				.build();

		return Candidate.builderForEventDescr(event.getEventDescriptor())
				.materialDescriptor(materialDescriptorBuilder)
				.transactionDetail(stockChangeDetail);
	}

	private MaterialDispoGroupId retrieveGroupIdOrNull(
			@NonNull final MaterialDescriptorQuery materialDescriptorQuery,
			@NonNull final CandidateType type,
			final int transactionId)
	{
		MaterialDispoGroupId groupId = null;
		if (CandidateType.INVENTORY_UP.equals(type) && transactionId > 0)
		{
			// see if there is a preceding "down" record to connect with
			// that's the case when a storage attribute has changed
			final CandidatesQuery inventoryQuery = CandidatesQuery.builder()
					.type(CandidateType.INVENTORY_DOWN)
					.transactionDetail(TransactionDetail.forQuery(transactionId))
					.materialDescriptorQuery(materialDescriptorQuery)
					.matchExactStorageAttributesKey(true)
					.build();

			final Candidate precedingInventoryRecord = candidateRepository.retrieveLatestMatchOrNull(inventoryQuery);
			if (precedingInventoryRecord != null)
			{
				groupId = precedingInventoryRecord.getGroupId();
			}
		}
		return groupId;
	}

	private MaterialDescriptorQuery createMaterialDescriptorQuery(@NonNull final StockChangedEvent event)
	{
		final ProductDescriptor productDescriptor = event.getProductDescriptor();

		final DateAndSeqNo timeRangeEnd = DateAndSeqNo.builder()
				.date(computeDate(event))
				.operator(Operator.INCLUSIVE).build();

		return MaterialDescriptorQuery.builder()
				.timeRangeEnd(timeRangeEnd)
				.productId(productDescriptor.getProductId())
				.storageAttributesKey(productDescriptor.getStorageAttributesKey())
				.warehouseId(event.getWarehouseId())
				.build();
	}

	private MaterialDescriptorBuilder createMaterialDescriptorBuilder(@NonNull final StockChangedEvent event)
	{
		final ProductDescriptor productDescriptor = event.getProductDescriptor();

		final Instant date = computeDate(event);

		return MaterialDescriptor.builder()
				.date(date)
				.productDescriptor(productDescriptor)
				.customerId(null)
				.warehouseId(event.getWarehouseId());
	}

	private TransactionDetail createStockChangeDetail(@NonNull final StockChangedEvent event)
	{
		final StockChangeDetails stockChangeDetails = event.getStockChangeDetails();
		final ProductDescriptor productDescriptor = event.getProductDescriptor();

		return TransactionDetail.builder()
				.attributeSetInstanceId(productDescriptor.getAttributeSetInstanceId())
				.complete(true)
				.quantity(event.getQtyOnHand().subtract(event.getQtyOnHandOld()))
				.resetStockPInstanceId(stockChangeDetails.getResetStockPInstanceId())
				.stockId(stockChangeDetails.getStockId())
				.storageAttributesKey(productDescriptor.getStorageAttributesKey())
				.transactionId(stockChangeDetails.getTransactionId())
				.transactionDate(computeDate(event))
				.complete(true)
				.build();
	}

	private Instant computeDate(@NonNull final StockChangedEvent event)
	{
		return CoalesceUtil.coalesceSuppliers(
				event::getChangeDate,
				SystemTime::asInstant);
	}
}
