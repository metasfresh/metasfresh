/*
 * #%L
 * de.metas.deliveryplanning.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.deliveryplanning.DeliveryPlanningAllocRepository.DeactivatedAllocations;
import de.metas.shipping.MPackageCreateRequest;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.shipping.mpackage.PackageId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Everything about a delivery instruction that spans MORE THAN ONE aggregate, and therefore cannot live in a
 * repository: allocating a planning to an instruction touches
 * {@code M_Delivery_Planning_Alloc}, {@code M_ShippingPackage}, {@code M_Package} and {@code M_Delivery_Planning}
 * in one operation, and a repository must never call another repository. This class is where that composition
 * happens, over {@link DeliveryInstructionRepository}, {@link DeliveryPlanningAllocRepository},
 * {@link DeliveryPlanningRepository} and {@link MPackageRepository}.
 * <p>
 * Single-aggregate reads and writes are NOT re-exposed here - a caller that only needs the instruction header, or
 * only the allocations, injects that repository itself.
 */
@Service
@RequiredArgsConstructor
public class DeliveryInstructionService
{
	@NonNull private final DeliveryPlanningRepository deliveryPlanningRepository;
	@NonNull private final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	@NonNull private final DeliveryInstructionRepository deliveryInstructionRepository;
	@NonNull private final MPackageRepository mPackageRepository;

	/**
	 * Creates ONE delivery instruction from the given request, with the request's own planning already allocated
	 * to it.
	 */
	public I_M_ShipperTransportation generateDeliveryInstruction(@NonNull final DeliveryInstructionCreateRequest request)
	{
		final I_M_ShipperTransportation deliveryInstructionRecord = deliveryInstructionRepository.create(request);

		createAllocations(deliveryInstructionRecord, ImmutableList.of(toAllocCreateRequest(request)), null);

		return deliveryInstructionRecord;
	}

	private static DeliveryPlanningAllocCreateRequest toAllocCreateRequest(@NonNull final DeliveryInstructionCreateRequest request)
	{
		return DeliveryPlanningAllocCreateRequest.builder()
				.deliveryPlanningId(request.getDeliveryPlanningId())
				.shippingPackage(DeliveryPlanningAllocCreateRequest.ShippingPackageData.builder()
						.productId(request.getProductId())
						.uomId(request.getQtyLoaded().getUomId())
						.batchNo(request.getBatchNo())
						.orderLineId(request.getOrderLineId())
						.orderId(request.getOrderId())
						.toBeFetched(request.isToBeFetched())
						.build())
				// the header's ETD/ETA/LoadingTime/DeliveryTime are already set by the repository above, directly
				// from this same request, before this method calls createAllocations with resolvedDates=null - so
				// this single-request list has nothing left to contribute to the fill-if-empty defaulting
				.headerDateCandidate(DeliveryPlanningAllocCreateRequest.HeaderDateCandidate.none())
				.build();
	}

	/**
	 * Allocates the given delivery plannings to the given delivery instruction, each with its own
	 * {@code M_ShippingPackage}. The allocations are created in the order of {@code requests}, so their ids
	 * follow that order - a caller that wants a particular order hands them over sorted.
	 *
	 * @param resolvedDates the instruction header's date fields, written verbatim; {@code null} leaves the
	 * 		header's current dates untouched.
	 */
	public ImmutableList<DeliveryPlanningAllocId> createAllocations(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests,
			@Nullable final DeliveryInstructionDates resolvedDates)
	{
		return createAllocations(deliveryInstructionRepository.getById(deliveryInstructionId), requests, resolvedDates);
	}

	/**
	 * Creates the allocations and leaves the instruction header's dates exactly as they are.
	 */
	public ImmutableList<DeliveryPlanningAllocId> createAllocations(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests)
	{
		return createAllocations(deliveryInstructionId, requests, null);
	}

	/**
	 * Package-private for the caller that already holds the instruction record, so it is not loaded twice.
	 */
	ImmutableList<DeliveryPlanningAllocId> createAllocations(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests,
			@Nullable final DeliveryInstructionDates resolvedDates)
	{
		// BEFORE the packages are built: the M_Package seeds its ShipDate from the instruction's ETA, so a date
		// written now reaches this add's packages instead of only the next one's.
		if (resolvedDates != null)
		{
			deliveryInstructionRepository.updateDates(deliveryInstructionRecord, resolvedDates);
		}

		final ImmutableList.Builder<DeliveryPlanningAllocId> allocIds = ImmutableList.builder();
		for (final DeliveryPlanningAllocCreateRequest request : requests)
		{
			allocIds.add(createAllocation(deliveryInstructionRecord, request));
		}

		// DeliveredState (Task Q9): ONCE per batch call, not once per request - every request here targets the
		// SAME instruction (the method's single deliveryInstructionRecord parameter), so recomputing inside the
		// loop above would cost one query round trip per row for a result that only the LAST iteration's answer
		// survives. Combine's 3-planning case measured this: per-row would have tripled combine's getByIds calls
		// (2 -> 5); once here keeps it at the pre-existing 2 (see DeliveryPlanningBatchLoadingTest).
		recomputeDeliveredState(ShipperTransportationId.ofRepoId(deliveryInstructionRecord.getM_ShipperTransportation_ID()));

		return allocIds.build();
	}

	/**
	 * The three-table composition an allocation is: the {@code M_Package} first (it is a reference of the
	 * shipping package, owned by {@link MPackageRepository}), then the instruction's {@code M_ShippingPackage}
	 * line, then the allocation row itself - {@code M_ShippingPackage_ID} is mandatory on the allocation and
	 * uniquely indexed, so the package has to exist first.
	 */
	private DeliveryPlanningAllocId createAllocation(
			@NonNull final I_M_ShipperTransportation deliveryInstructionRecord,
			@NonNull final DeliveryPlanningAllocCreateRequest request)
	{
		// Shipper_BPartner_ID / Shipper_Location_ID are a mandatory PAIR on the instruction, which is why the
		// location is built from both: BPartnerLocationId is a composite and has no meaning without its partner.
		final int shipperBPartnerId = deliveryInstructionRecord.getShipper_BPartner_ID();

		final PackageId packageId = mPackageRepository.create(MPackageCreateRequest.builder()
				.shipperId(ShipperId.ofRepoIdOrNull(deliveryInstructionRecord.getM_Shipper_ID()))
				.shipDate(deliveryInstructionRecord.getETA())
				.bpartnerId(BPartnerId.ofRepoIdOrNull(shipperBPartnerId))
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(shipperBPartnerId, deliveryInstructionRecord.getShipper_Location_ID()))
				.build());

		final ShippingPackageId shippingPackageId = deliveryInstructionRepository.createShippingPackage(
				deliveryInstructionRecord, request.getShippingPackage(), packageId);

		// DeliveredState (Task Q9) is recomputed once per BATCH by the caller (createAllocations), not here per
		// row - see that method's note on why.

		return deliveryPlanningAllocRepository.create(
				OrgId.ofRepoIdOrAny(deliveryInstructionRecord.getAD_Org_ID()),
				request.getDeliveryPlanningId(),
				ShipperTransportationId.ofRepoId(deliveryInstructionRecord.getM_ShipperTransportation_ID()),
				shippingPackageId);
	}

	/**
	 * Deactivates - rather than deletes - the given plannings' ACTIVE allocations and the shipping packages they
	 * point at, so the record of what was once planned survives. A deactivated allocation is left alone: it
	 * records an instruction the planning was taken off earlier, which is not what the caller is undoing.
	 *
	 * @return the planning ids ACTUALLY deactivated - a subset of the input when one had no active allocation.
	 */
	public ImmutableSet<DeliveryPlanningId> deactivateAllocations(
			@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds,
			@NonNull final Instant removedAt)
	{
		return afterDeactivation(deliveryPlanningAllocRepository.deactivateByPlanningIds(deliveryPlanningIds, removedAt))
				.getDeallocatedPlanningIds();
	}

	/**
	 * On void or cancel of the delivery instruction: the allocations and their shipping packages are deactivated
	 * rather than deleted. {@code IsActive='N'} also releases both partial unique indexes on the allocation, so
	 * the plannings can be allocated again afterwards.
	 */
	public ImmutableSet<DeliveryPlanningId> deactivateAllocations(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final Instant removedAt)
	{
		return afterDeactivation(deliveryPlanningAllocRepository.deactivateByInstructionId(deliveryInstructionId, removedAt))
				.getDeallocatedPlanningIds();
	}

	/**
	 * The other two aggregates a deactivation reaches: the shipping packages the retired allocations pointed at
	 * go inactive with them, and every instruction that lost an allocation recomputes its {@code DeliveredState}
	 * ONCE - deduplicated across allocations, since a deactivation by planning ids can span several instructions.
	 */
	private DeactivatedAllocations afterDeactivation(@NonNull final DeactivatedAllocations deactivated)
	{
		deliveryInstructionRepository.deactivateShippingPackages(deactivated.getShippingPackageIds());

		for (final ShipperTransportationId deliveryInstructionId : deactivated.getTouchedDeliveryInstructionIds())
		{
			recomputeDeliveredState(deliveryInstructionId);
		}

		return deactivated;
	}

	/**
	 * Takes every planning off the given instruction: their allocations and shipping packages are deactivated,
	 * their {@code ReleaseNo} and instruction reference are cleared, and the packages behind the JUST-DEACTIVATED
	 * allocations - never the instruction's whole package set - lose their order-line reference. A planning
	 * removed earlier left a retired package still carrying this instruction's id, and re-querying by instruction
	 * id would wipe its {@code C_OrderLine_ID} too.
	 *
	 * @return the planning ids whose allocation was deactivated.
	 */
	public ImmutableSet<DeliveryPlanningId> unlinkDeliveryPlannings(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final Instant removedAt)
	{
		final DeactivatedAllocations deactivated = afterDeactivation(
				deliveryPlanningAllocRepository.deactivateByInstructionId(deliveryInstructionId, removedAt));

		deliveryPlanningRepository.clearInstructionReferenceOfInstruction(deliveryInstructionId);

		deliveryInstructionRepository.unlinkShippingPackages(deactivated.getShippingPackageIds());

		return deactivated.getDeallocatedPlanningIds();
	}

	/**
	 * Recomputes {@code M_ShipperTransportation.DeliveredState} for every delivery instruction the given planning
	 * is currently ACTIVELY allocated to (spec &sect; 5.7, Task Q9) - the entry point
	 * {@code interceptor/M_InOut#afterComplete}/{@code #afterReverseCorrect} routes through after a receipt or
	 * shipment completes or is reversed, since that is the write that can change ONE planning's
	 * {@code IsDelivered} and therefore every instruction it sits on.
	 */
	public void recomputeDeliveredStateForAllocatedInstructions(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		for (final ShipperTransportationId deliveryInstructionId : deliveryPlanningAllocRepository.getAllocatedInstructionIdsOf(deliveryPlanningId))
		{
			recomputeDeliveredState(deliveryInstructionId);
		}
	}

	/**
	 * Recomputes and stores {@code M_ShipperTransportation.DeliveredState} for ONE delivery instruction, from
	 * {@link DeliveryPlanningList#getDeliveredState()} over its currently ACTIVE allocations - the single
	 * derivation every write point that can change which plannings are delivered, or which plannings are
	 * actively allocated to the instruction, routes through (rule 6, Task Q9): {@link #createAllocations},
	 * {@link #afterDeactivation} and {@link #recomputeDeliveredStateForAllocatedInstructions}. An instruction
	 * with no active allocation is {@code NotDelivered} - the same vacuous case the ADD COLUMN DEFAULT already
	 * gives a freshly-created instruction, so this is never a special case, only the general one.
	 * <p>
	 * Composed rather than owned by either repository: the state is a property of the INSTRUCTION, derived from
	 * the PLANNINGS its allocations name - three tables, one per repository.
	 */
	public void recomputeDeliveredState(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		final ImmutableList<DeliveryPlanningAlloc> allocations = deliveryPlanningAllocRepository.getAllocationsOfInstruction(deliveryInstructionId);

		final DeliveryInstructionDeliveredState deliveredState;
		if (allocations.isEmpty())
		{
			deliveredState = DeliveryInstructionDeliveredState.NotDelivered;
		}
		else
		{
			final ImmutableSet<DeliveryPlanningId> allocatedPlanningIds = allocations.stream()
					.map(DeliveryPlanningAlloc::getDeliveryPlanningId)
					.collect(ImmutableSet.toImmutableSet());

			deliveredState = deliveryPlanningRepository.getDeliveredStatePlannings(allocatedPlanningIds).getDeliveredState();
		}

		deliveryInstructionRepository.setDeliveredState(deliveryInstructionId, deliveredState);
	}

	/**
	 * Makes every delivery instruction the given planning is ACTIVELY allocated to refresh its
	 * {@code M_ShippingPackage} line in an already-open WebUI document (Task Q14, TC11) - the four quantity
	 * figures on that line are a {@code ColumnSQL} read-through of this planning, so a change here changes what
	 * the line must show.
	 * <p>
	 * The reason a hand-written invalidation is needed at all, and why the request is rooted at the INSTRUCTION
	 * rather than at the package, is spelled out on {@link DeliveryInstructionLineCacheInvalidation}. Broadcast
	 * on transaction commit (not immediately), the same way {@code de.metas.acct.interceptor.GL_JournalLine}
	 * pushes a line change up to its {@code GL_Journal} document: the frontend must re-read committed data.
	 * <p>
	 * Cost: one {@code SELECT} over {@code M_Delivery_Planning_Alloc} per quantity-changing save of a planning,
	 * and nothing at all for a planning that is on no instruction (the overwhelmingly common case while a
	 * planning is still being planned) - {@code requestForAllocationsOrNull} returns {@code null} and no
	 * broadcast is sent.
	 */
	public void invalidateDeliveryInstructionLinesFor(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		final CacheInvalidateMultiRequest request = DeliveryInstructionLineCacheInvalidation.requestForAllocationsOrNull(
				deliveryPlanningAllocRepository.getAllocationsByPlanningId(ImmutableList.of(deliveryPlanningId)).values());
		if (request == null)
		{
			return;
		}

		CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(ITrx.TRXNAME_ThreadInherited, request);
	}

	/**
	 * Stamps the given plannings' {@code ReleaseNo}, instruction reference and date fields from the given delivery
	 * instruction, overwriting whatever they carried - a move off another instruction requires it, or two records
	 * would disagree about where the cargo is.
	 */
	public void updateDeliveryPlanningsFromInstruction(
			@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds,
			@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		deliveryPlanningRepository.updateDeliveryPlanningsFromInstruction(
				deliveryPlanningIds,
				deliveryInstructionRepository.getById(deliveryInstructionId));
	}

	/**
	 * The delivery instructions the given planning is currently allocated to, as records.
	 */
	public Iterator<I_M_ShipperTransportation> retrieveForDeliveryPlanning(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryInstructionRepository.iterateByIds(deliveryPlanningAllocRepository.getAllocatedInstructionIdsOf(deliveryPlanningId));
	}

	/**
	 * Whether the given planning sits on a COMPLETED delivery instruction.
	 */
	public boolean hasCompleteDeliveryInstruction(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryInstructionRepository.hasCompletedAmong(deliveryPlanningAllocRepository.getAllocatedInstructionIdsOf(deliveryPlanningId));
	}
}
