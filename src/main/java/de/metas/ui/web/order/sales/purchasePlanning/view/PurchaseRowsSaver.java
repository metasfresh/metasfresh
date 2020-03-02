package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

class PurchaseRowsSaver
{
	// services
	private final PurchaseCandidateRepository purchaseCandidatesRepo;
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	@Builder
	private PurchaseRowsSaver(
			@NonNull final PurchaseCandidateRepository purchaseCandidatesRepo)
	{
		this.purchaseCandidatesRepo = purchaseCandidatesRepo;
	}

	public List<PurchaseCandidate> save(@NonNull final List<PurchaseRow> groupingRows)
	{
		final Set<DemandGroupReference> demandIds = extractDemandIds(groupingRows);
		final Map<PurchaseCandidateId, PurchaseCandidate> existingPurchaseCandidatesById = getExistingPurchaseCandidatesIndexedById(demandIds);

		//
		// Create/Update purchase candidates (this used to be stream-based, but it was too hard to debug)
		final List<PurchaseCandidate> purchaseCandidatesToSave = new ArrayList<>();
		final List<PurchaseCandidatesGroup> purchaseCandidatesGroups = extractPurchaseCandidatesGroups(groupingRows);
		for (final PurchaseCandidatesGroup purchaseCandidatesGroup : purchaseCandidatesGroups)
		{
			final List<PurchaseCandidate> purchaseCandidates = createOrUpdatePurchaseCandidate(purchaseCandidatesGroup, existingPurchaseCandidatesById);
			purchaseCandidatesToSave.addAll(purchaseCandidates);
		}

		purchaseCandidatesRepo.saveAll(purchaseCandidatesToSave);

		//
		// Zerofy remaining candidates:
		final Set<PurchaseCandidateId> purchaseCandidateIdsSaved = purchaseCandidatesToSave.stream()
				.map(PurchaseCandidate::getId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		final List<PurchaseCandidate> purchaseCandidatesToZero = existingPurchaseCandidatesById.values()
				.stream()
				.filter(candidate -> !candidate.isProcessedOrLocked()) // don't delete processed/locked candidates
				.filter(candidate -> !purchaseCandidateIdsSaved.contains(candidate.getId()))
				.peek(candidate -> {
					candidate.setQtyToPurchase(candidate.getQtyToPurchase().toZero());
					candidate.setPrepared(false);
				})
				.collect(ImmutableList.toImmutableList());
		purchaseCandidatesRepo.saveAll(purchaseCandidatesToZero);

		return purchaseCandidatesToSave;
	}

	private static List<PurchaseCandidatesGroup> extractPurchaseCandidatesGroups(final List<PurchaseRow> groupingRows)
	{
		return groupingRows
				.stream()
				.flatMap(PurchaseRow::streamPurchaseCandidatesGroup)
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableMap<PurchaseCandidateId, PurchaseCandidate> getExistingPurchaseCandidatesIndexedById(final Set<DemandGroupReference> demandIds)
	{
		return purchaseCandidatesRepo
				.getAllByDemandIds(demandIds)
				.values()
				.stream()
				.collect(ImmutableMap.toImmutableMap(PurchaseCandidate::getId, Function.identity()));
	}

	private ImmutableSet<DemandGroupReference> extractDemandIds(@NonNull final List<PurchaseRow> groupingRows)
	{
		return groupingRows.stream()
				.flatMap(groupingRow -> groupingRow.getIncludedRows().stream())
				.flatMap(lineRow -> lineRow.getDemandGroupReferences().stream())
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private List<PurchaseCandidate> createOrUpdatePurchaseCandidate(
			@NonNull final PurchaseCandidatesGroup candidatesGroup,
			@NonNull final Map<PurchaseCandidateId, PurchaseCandidate> existingPurchaseCandidatesById)
	{
		Quantity qtyToPurchaseRemainingOfGroup = candidatesGroup.getQtyToPurchase();
		if (qtyToPurchaseRemainingOfGroup.signum() <= 0)
		{
			return ImmutableList.of();
		}

		final PurchaseProfitInfo profitInfo = candidatesGroup.getProfitInfoOrNull();
		final ZonedDateTime purchaseDatePromised = candidatesGroup.getPurchaseDatePromised();
		final Duration reminderTime = candidatesGroup.getReminderTime();
		final List<PurchaseCandidate> allCandidates = getPurchaseCandidates(candidatesGroup, existingPurchaseCandidatesById);

		//
		// Adjust qtyToPurchaseRemaining: Subtract the qtyToPurchase which was already processed
		{
			final Optional<Quantity> qtyToPurchaseProcessed = computeQtyToPurchaseAlreadyProcessed(allCandidates);
			if (qtyToPurchaseProcessed.isPresent())
			{
				qtyToPurchaseRemainingOfGroup = qtyToPurchaseRemainingOfGroup.subtract(qtyToPurchaseProcessed.get());
			}
			if (qtyToPurchaseRemainingOfGroup.signum() < 0)
			{
				// TODO: throw exception?
				return ImmutableList.of();
			}
			else if (qtyToPurchaseRemainingOfGroup.signum() == 0)
			{
				return ImmutableList.of();
			}
		}

		//
		// Extract all updatable candidates
		final ArrayList<PurchaseCandidate> candidatesToUpdate = allCandidates.stream()
				.filter(candidate -> !candidate.isProcessedOrLocked())
				.collect(Collectors.toCollection(ArrayList::new));

		final ArrayList<PurchaseCandidate> candidatesChanged = new ArrayList<>();

		//
		// Distribute qtyToPurchase to updatable purchase candidates (FIFO order)
		while (qtyToPurchaseRemainingOfGroup.signum() > 0 && !candidatesToUpdate.isEmpty())
		{
			final PurchaseCandidate candidate = candidatesToUpdate.remove(0);

			final Quantity qtyToPurchaseTarget = getQtyToPurchaseTarget(candidate);
			final Quantity qtyToPurchase = qtyToPurchaseTarget.min(qtyToPurchaseRemainingOfGroup);
			candidate.setQtyToPurchase(qtyToPurchase);
			candidate.setPrepared(qtyToPurchase.signum() != 0);
			candidate.setPurchaseDatePromised(purchaseDatePromised);
			candidate.setProfitInfoOrNull(profitInfo);

			candidatesChanged.add(candidate);
			qtyToPurchaseRemainingOfGroup = qtyToPurchaseRemainingOfGroup.subtract(qtyToPurchase);
		}

		//
		// If there is no remaining qty to purchase then ZERO all the remaining purchase candidates lines
		if (qtyToPurchaseRemainingOfGroup.signum() <= 0)
		{
			while (!candidatesToUpdate.isEmpty())
			{
				final PurchaseCandidate candidate = candidatesToUpdate.remove(0);
				candidate.setQtyToPurchase(candidate.getQtyToPurchase().toZero());

				candidatesChanged.add(candidate);
			}
		}
		//
		// If there is remaining qty to purchase then add it to last changed purchase candidate line
		else if (!candidatesToUpdate.isEmpty())
		{
			final PurchaseCandidate lastCandidate = candidatesToUpdate.get(candidatesToUpdate.size() - 1);
			lastCandidate.setQtyToPurchase(lastCandidate.getQtyToPurchase().add(qtyToPurchaseRemainingOfGroup));
			lastCandidate.setPurchaseDatePromised(purchaseDatePromised);

			qtyToPurchaseRemainingOfGroup = qtyToPurchaseRemainingOfGroup.toZero();
		}
		//
		// If there is remaining qty to purchase but no purchase candidate to add to then create a new candidate
		else
		{
			final DemandGroupReference groupReference;
			if (candidatesGroup.getDemandGroupReferences().isEmpty())
			{
				groupReference = DemandGroupReference.EMPTY;
			}
			else
			{
				groupReference = candidatesGroup.getDemandGroupReferences().get(0);
			}

			final PurchaseCandidate newCandidate = PurchaseCandidate
					.builder()
					.groupReference(groupReference)
					.salesOrderAndLineIdOrNull(candidatesGroup.getSingleSalesOrderAndLineIdOrNull())
					//
					.purchaseDatePromised(purchaseDatePromised)
					.reminderTime(reminderTime)
					//
					.orgId(candidatesGroup.getOrgId())
					.warehouseId(candidatesGroup.getWarehouseId())
					.vendorId(candidatesGroup.getVendorId())
					.vendorProductNo(candidatesGroup.getVendorProductNo())
					//
					.productId(candidatesGroup.getProductId())
					.attributeSetInstanceId(candidatesGroup.getAttributeSetInstanceId())
					//
					.qtyToPurchase(qtyToPurchaseRemainingOfGroup)
					.prepared(true)
					//
					.aggregatePOs(candidatesGroup.isAggregatePOs())
					//
					.profitInfoOrNull(profitInfo)
					//
					.build();

			candidatesChanged.add(newCandidate);
			qtyToPurchaseRemainingOfGroup = qtyToPurchaseRemainingOfGroup.toZero();
		}

		return candidatesChanged;
	}

	/** gets all candidates that have their ID in the group and are among {@code existingPurchaseCandidatesById}. */
	private static List<PurchaseCandidate> getPurchaseCandidates(
			final PurchaseCandidatesGroup candidatesGroup,
			final Map<PurchaseCandidateId, PurchaseCandidate> existingPurchaseCandidatesById)
	{
		return candidatesGroup.getPurchaseCandidateIds()
				.stream()
				.map(existingPurchaseCandidatesById::get)
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(candidate -> candidate.getId().getRepoId()))
				.collect(ImmutableList.toImmutableList());
	}

	private static Optional<Quantity> computeQtyToPurchaseAlreadyProcessed(final Collection<PurchaseCandidate> candidates)
	{
		return candidates.stream()
				.filter(PurchaseCandidate::isProcessedOrLocked)
				.map(PurchaseCandidate::getQtyToPurchase)
				.reduce(Quantity::add);
	}

	private Quantity getQtyToPurchaseTarget(final PurchaseCandidate candidate)
	{
		final OrderAndLineId orderAndLineId = candidate.getSalesOrderAndLineIdOrNull();
		if (orderAndLineId != null)
		{
			return orderLineBL
					.getQtyToDeliver(orderAndLineId)
					.toZeroIfNegative();
		}
		else
		{
			// TODO: handle this case
			return candidate.getQtyToPurchase().toZero();
		}
	}
}
