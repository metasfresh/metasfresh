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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.deliveryplanning.DeliveryPlanningAllocRepository.DeactivatedAllocations;
import de.metas.document.engine.DocStatus;
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
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryInstructionService
{
	@NonNull private final DeliveryPlanningRepository deliveryPlanningRepository;
	@NonNull private final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	@NonNull private final DeliveryInstructionRepository deliveryInstructionRepository;
	@NonNull private final MPackageRepository mPackageRepository;

	/** The delivery instruction header. */
	public I_M_ShipperTransportation getById(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return deliveryInstructionRepository.getById(deliveryInstructionId);
	}

	/** The given delivery instruction's {@code DocStatus}. */
	public DocStatus getDocStatus(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return deliveryInstructionRepository.getDocStatus(deliveryInstructionId);
	}

	/** The given delivery instructions' {@code DocStatus}, in ONE query. */
	public ImmutableMap<ShipperTransportationId, DocStatus> getDocStatuses(@NonNull final Collection<ShipperTransportationId> deliveryInstructionIds)
	{
		return deliveryInstructionRepository.getDocStatuses(deliveryInstructionIds);
	}

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
				// the header's dates are already set by the repository above, from this same request, so this
				// single-request list has nothing left to contribute to the fill-if-empty defaulting
				.headerDateCandidate(DeliveryPlanningAllocCreateRequest.HeaderDateCandidate.none())
				.build();
	}

	/**
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

	public ImmutableList<DeliveryPlanningAllocId> createAllocations(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final List<DeliveryPlanningAllocCreateRequest> requests)
	{
		return createAllocations(deliveryInstructionId, requests, null);
	}

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

		// ONCE per batch call, not once per request: every request here targets the SAME instruction, so recomputing
		// inside the loop above would cost a round trip per row for a result only the last iteration's survives
		recomputeDeliveredState(ShipperTransportationId.ofRepoId(deliveryInstructionRecord.getM_ShipperTransportation_ID()));

		return allocIds.build();
	}

	/**
	 * The {@code M_Package} first, then the instruction's {@code M_ShippingPackage} line, then the allocation row:
	 * {@code M_ShippingPackage_ID} is mandatory on the allocation and uniquely indexed, so the package must exist first.
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
				.shipDate(TimeUtil.asInstant(deliveryInstructionRecord.getETA()))
				.bpartnerId(BPartnerId.ofRepoIdOrNull(shipperBPartnerId))
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(shipperBPartnerId, deliveryInstructionRecord.getShipper_Location_ID()))
				.build());

		final ShippingPackageId shippingPackageId = deliveryInstructionRepository.createShippingPackage(
				deliveryInstructionRecord, request.getShippingPackage(), packageId);

		// DeliveredState is recomputed once per BATCH by the caller (createAllocations), not here per
		// row - see that method's note on why.

		return deliveryPlanningAllocRepository.create(
				OrgId.ofRepoIdOrAny(deliveryInstructionRecord.getAD_Org_ID()),
				request.getDeliveryPlanningId(),
				ShipperTransportationId.ofRepoId(deliveryInstructionRecord.getM_ShipperTransportation_ID()),
				shippingPackageId);
	}

	/**
	 * Deactivates rather than deletes, so the record of what was once planned survives. An already-deactivated
	 * allocation is left alone: it records an instruction the planning was taken off earlier.
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
	 * {@code IsActive='N'} also releases both partial unique indexes on the allocation, so the plannings can be
	 * allocated again afterwards.
	 */
	public ImmutableSet<DeliveryPlanningId> deactivateAllocations(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final Instant removedAt)
	{
		return afterDeactivation(deliveryPlanningAllocRepository.deactivateByInstructionId(deliveryInstructionId, removedAt))
				.getDeallocatedPlanningIds();
	}

	/**
	 * Every instruction that lost an allocation recomputes its {@code DeliveredState} ONCE - deduplicated, since a
	 * deactivation by planning ids can span several instructions.
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
	 * The packages that lose their order-line reference are those behind the JUST-DEACTIVATED allocations, never
	 * the instruction's whole package set: a planning removed earlier left a retired package still carrying this
	 * instruction's id, and re-querying by instruction id would wipe its {@code C_OrderLine_ID} too.
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

	public void recomputeDeliveredStateForAllocatedInstructions(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		for (final ShipperTransportationId deliveryInstructionId : deliveryPlanningAllocRepository.getAllocatedInstructionIdsOf(deliveryPlanningId))
		{
			recomputeDeliveredState(deliveryInstructionId);
		}
	}

	/**
	 * An instruction with no active allocation is {@code NotDelivered} - the same vacuous case a freshly-created
	 * one starts in, so this is never a special case.
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
	 * The instruction's {@code M_ShippingPackage} line is a {@code ColumnSQL} read-through of the planning, so an
	 * already-open document must be told to re-read it - see {@link DeliveryInstructionLineCacheInvalidation} for
	 * why a hand-written invalidation is needed and why it is rooted at the INSTRUCTION. Broadcast on commit, not
	 * immediately: the frontend must re-read committed data.
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

	public Iterator<I_M_ShipperTransportation> retrieveForDeliveryPlanning(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryInstructionRepository.iterateByIds(deliveryPlanningAllocRepository.getAllocatedInstructionIdsOf(deliveryPlanningId));
	}

	public boolean hasCompleteDeliveryInstruction(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return deliveryInstructionRepository.hasCompletedAmong(deliveryPlanningAllocRepository.getAllocatedInstructionIdsOf(deliveryPlanningId));
	}
}
