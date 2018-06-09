package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.util.Services;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.order.IOrderLineBL;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.quantity.Quantity;
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

	public List<PurchaseCandidate> save(final List<PurchaseRow> groupingRows)
	{
		final Set<PurchaseDemandId> demandIds = extractDemandIds(groupingRows);
		final Map<PurchaseCandidateId, PurchaseCandidate> existingPurchaseCandidatesById = getExistingPurchaseCandidatesIndexedById(demandIds);

		//
		// Create/Update purchase candidates
		final List<PurchaseCandidate> purchaseCandidatesToSave = streamPurchaseCandidatesGroups(groupingRows)
				.map(candidatesGroup -> updatePurchaseCandidate(candidatesGroup, existingPurchaseCandidatesById))
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
		purchaseCandidatesRepo.saveAll(purchaseCandidatesToSave);

		//
		// Delete remaining candidates:
		final Set<PurchaseCandidateId> purchaseCandidateIdsSaved = purchaseCandidatesToSave.stream()
				.map(PurchaseCandidate::getId)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
		final Set<PurchaseCandidateId> purchaseCandidateIdsToDelete = existingPurchaseCandidatesById.values()
				.stream()
				.filter(candidate -> !candidate.isProcessedOrLocked()) // don't delete processed/locked candidates
				.filter(candidate -> !purchaseCandidateIdsSaved.contains(candidate.getId()))
				.map(candidate -> candidate.getId())
				.collect(ImmutableSet.toImmutableSet());
		purchaseCandidatesRepo.deleteByIds(purchaseCandidateIdsToDelete);

		return purchaseCandidatesToSave;
	}

	private static Stream<PurchaseCandidatesGroup> streamPurchaseCandidatesGroups(final List<PurchaseRow> groupingRows)
	{
		return groupingRows.stream().flatMap(PurchaseRow::streamPurchaseCandidatesGroup);
	}

	private ImmutableMap<PurchaseCandidateId, PurchaseCandidate> getExistingPurchaseCandidatesIndexedById(final Set<PurchaseDemandId> demandIds)
	{
		return purchaseCandidatesRepo
				.getAllByDemandIds(demandIds)
				.values()
				.stream()
				.collect(ImmutableMap.toImmutableMap(PurchaseCandidate::getId, Function.identity()));
	}

	private ImmutableSet<PurchaseDemandId> extractDemandIds(final List<PurchaseRow> groupingRows)
	{
		return groupingRows.stream()
				.map(PurchaseRow::getPurchaseDemandId)
				.filter(id -> id != null)
				.collect(ImmutableSet.toImmutableSet());
	}

	private List<PurchaseCandidate> updatePurchaseCandidate(
			@NonNull final PurchaseCandidatesGroup candidatesGroup,
			@NonNull final Map<PurchaseCandidateId, PurchaseCandidate> existingPurchaseCandidatesById)
	{
		Quantity qtyToPurchaseRemaining = candidatesGroup.getQtyToPurchase();
		if (qtyToPurchaseRemaining.signum() <= 0)
		{
			return ImmutableList.of();
		}

		final PurchaseProfitInfo profitInfo = candidatesGroup.getProfitInfo();
		final LocalDateTime purchaseDatePromised = candidatesGroup.getPurchaseDatePromised();
		final List<PurchaseCandidate> allCandidates = getPurchaseCandidates(candidatesGroup, existingPurchaseCandidatesById);

		//
		// Adjust qtyToPurchaseRemaining: Subtract the qtyToPurchase which was already processed
		{
			final Optional<Quantity> qtyToPurchaseProcessed = computeQtyToPurchaseAlreadyProcessed(allCandidates);
			if (qtyToPurchaseProcessed.isPresent())
			{
				qtyToPurchaseRemaining = qtyToPurchaseRemaining.subtract(qtyToPurchaseProcessed.get());
			}
			if (qtyToPurchaseRemaining.signum() < 0)
			{
				// TODO: throw exception?
				return ImmutableList.of();
			}
			else if (qtyToPurchaseRemaining.signum() == 0)
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
		while (qtyToPurchaseRemaining.signum() > 0 && !candidatesToUpdate.isEmpty())
		{
			final PurchaseCandidate candidate = candidatesToUpdate.remove(0);

			final Quantity qtyToPurchaseTarget = getQtyToPurchaseTarget(candidate);
			final Quantity qtyToPurchase = qtyToPurchaseTarget.min(qtyToPurchaseRemaining);
			candidate.setQtyToPurchase(qtyToPurchase);
			candidate.setDateRequired(purchaseDatePromised);
			candidate.setProfitInfo(profitInfo);

			candidatesChanged.add(candidate);
			qtyToPurchaseRemaining = qtyToPurchaseRemaining.subtract(qtyToPurchase);
		}

		//
		// If there is no remaining qty to purchase then ZERO all the remaining purchase candidates lines
		if (qtyToPurchaseRemaining.signum() <= 0)
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
			lastCandidate.setQtyToPurchase(lastCandidate.getQtyToPurchase().add(qtyToPurchaseRemaining));
			lastCandidate.setDateRequired(purchaseDatePromised);

			qtyToPurchaseRemaining = qtyToPurchaseRemaining.toZero();
		}
		//
		// If there is remaining qty to purchase but no purchase candidate to add to then create a new candidate
		else
		{
			final PurchaseCandidate newCandidate = PurchaseCandidate.builder()
					// .salesOrderAndLineId(candidatesGroup.getSalesOrderAndLineId()) // TODO: which sales order line to pick?!
					//
					.dateRequired(purchaseDatePromised)
					// .reminderTime(reminderTime) // TODO reminder time
					//
					.orgId(candidatesGroup.getOrgId())
					.warehouseId(candidatesGroup.getWarehouseId())
					.vendorId(candidatesGroup.getVendorId())
					.vendorProductNo(candidatesGroup.getVendorProductNo())
					//
					.productId(candidatesGroup.getProductId())
					//
					.qtyToPurchase(qtyToPurchaseRemaining)
					//
					.aggregatePOs(candidatesGroup.isAggregatePOs())
					//
					.build();

			candidatesChanged.add(newCandidate);
			qtyToPurchaseRemaining = qtyToPurchaseRemaining.toZero();
		}

		return candidatesChanged;
	}

	private static List<PurchaseCandidate> getPurchaseCandidates(
			final PurchaseCandidatesGroup candidatesGroup,
			final Map<PurchaseCandidateId, PurchaseCandidate> existingPurchaseCandidatesById)
	{
		return candidatesGroup.getPurchaseCandidateIds()
				.stream()
				.map(existingPurchaseCandidatesById::get)
				.filter(Predicates.notNull())
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
		if (candidate.getSalesOrderAndLineId() != null)
		{
			return orderLineBL.getQtyToDeliver(candidate.getSalesOrderAndLineId())
					.toZeroIfNegative();
		}
		else
		{
			// TODO: handle this case
			return candidate.getQtyToPurchase().toZero();
		}
	}
}
