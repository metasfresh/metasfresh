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
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.organization.OrgId;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Delivery_Planning_Alloc;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Repository Tables: M_Delivery_Planning_Alloc, M_Delivery_Planning, M_ShipperTransportation
 * Repository Cluster: DeliveryPlanningAllocRepository (sole owner of M_Delivery_Planning_Alloc),
 * DeliveryPlanningRepository, DeliveryInstructionRepository
 * <p>
 * Writes {@code M_Delivery_Planning.IsAllocated} and {@code IsReadyForReceipt} as the one deliberate exception
 * to single-table ownership: both columns are this table's mirrors, and folding their {@code EXISTS} into the
 * {@code UPDATE}'s own {@code SET} clause is what keeps
 * the flags are now derived and stored by DeliveryPlanningAllocService, which may call several
 * repositories - this one no longer writes M_Delivery_Planning at all.
 */
@Repository
public class DeliveryPlanningAllocRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * {@code M_ShippingPackage_ID} is mandatory here and uniquely indexed, so {@link DeliveryInstructionService}
	 * creates the shipping package first.
	 */
	public DeliveryPlanningAllocId create(
			@NonNull final OrgId orgId,
			@NonNull final DeliveryPlanningId deliveryPlanningId,
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final ShippingPackageId shippingPackageId)
	{
		final I_M_Delivery_Planning_Alloc allocRecord = newInstance(I_M_Delivery_Planning_Alloc.class);
		allocRecord.setAD_Org_ID(orgId.getRepoId());
		allocRecord.setM_Delivery_Planning_ID(deliveryPlanningId.getRepoId());
		allocRecord.setM_ShipperTransportation_ID(deliveryInstructionId.getRepoId());
		allocRecord.setM_ShippingPackage_ID(shippingPackageId.getRepoId());
		saveRecord(allocRecord);

		// IsAllocated and IsReadyForReceipt are kept in step by the M_Delivery_Planning_Alloc interceptor
		// (AFTER_NEW) that the saveRecord above fires - not by an inline call here

		return DeliveryPlanningAllocId.ofRepoId(allocRecord.getM_Delivery_Planning_Alloc_ID());
	}

	/**
	 * A multimap for shape symmetry with the instruction-side lookup, NOT because a planning can hold several
	 * active allocations: {@code M_Delivery_Planning_Alloc_Planning_UQ} permits at most one
	 * ({@code UNIQUE (M_Delivery_Planning_ID) WHERE IsActive='Y'}), so each key carries exactly one value.
	 */
	public ImmutableListMultimap<DeliveryPlanningId, DeliveryPlanningAlloc> getByDeliveryPlanningIds(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		return queryAllocationsByPlanningIds(deliveryPlanningIds)
				.create()
				.stream()
				.map(DeliveryPlanningAllocRepository::toDeliveryPlanningAlloc)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						DeliveryPlanningAlloc::getDeliveryPlanningId,
						alloc -> alloc));
	}

	public ImmutableList<DeliveryPlanningAlloc> getAllocationsOfInstruction(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryActiveAllocationsByInstructionId(deliveryInstructionId)
				.create()
				.stream()
				.map(DeliveryPlanningAllocRepository::toDeliveryPlanningAlloc)
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableSet<ShipperTransportationId> getAllocatedInstructionIdsOf(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return getByDeliveryPlanningIds(ImmutableList.of(deliveryPlanningId))
				.values()
				.stream()
				.map(DeliveryPlanningAlloc::getDeliveryInstructionId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Stable order: a rejection that names these ids has to read the same on two identical runs.
	 */
	public ImmutableSet<DeliveryPlanningId> getAllocatedPlanningIds(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryActiveAllocationsByInstructionId(deliveryInstructionId)
				.create()
				.stream()
				.map(allocRecord -> DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Deactivates rather than deletes, so the record of what was once planned survives. An already-deactivated
	 * allocation is left alone: it records an instruction the planning was taken off earlier.
	 */
	public DeactivatedAllocations deactivateByPlanningIds(
			@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds,
			@NonNull final Instant removedAt)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return DeactivatedAllocations.NONE;
		}

		return deactivate(queryAllocationsByPlanningIds(deliveryPlanningIds).create().list(), removedAt);
	}

	/**
	 * {@code IsActive='N'} also releases both partial unique indexes on the allocation, so the plannings can be
	 * allocated again afterwards.
	 */
	public DeactivatedAllocations deactivateByInstructionId(
			@NonNull final ShipperTransportationId deliveryInstructionId,
			@NonNull final Instant removedAt)
	{
		return deactivate(queryActiveAllocationsByInstructionId(deliveryInstructionId).create().list(), removedAt);
	}

	/**
	 * The single choke point every path that ends an allocation's active life routes through - which is why
	 * {@code DateRemoved} is stamped here and nowhere else.
	 */
	private DeactivatedAllocations deactivate(
			@NonNull final List<I_M_Delivery_Planning_Alloc> allocRecords,
			@NonNull final Instant removedAt)
	{
		final ImmutableSet.Builder<ShippingPackageId> deactivatedShippingPackageIds = ImmutableSet.builder();
		final ImmutableSet.Builder<DeliveryPlanningId> deallocatedPlanningIds = ImmutableSet.builder();
		final ImmutableSet.Builder<ShipperTransportationId> touchedDeliveryInstructionIds = ImmutableSet.builder();
		for (final I_M_Delivery_Planning_Alloc allocRecord : allocRecords)
		{
			allocRecord.setIsActive(false);
			allocRecord.setDateRemoved(TimeUtil.asTimestamp(removedAt));
			saveRecord(allocRecord);

			deactivatedShippingPackageIds.add(ShippingPackageId.ofRepoId(allocRecord.getM_ShippingPackage_ID()));
			deallocatedPlanningIds.add(DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()));
			touchedDeliveryInstructionIds.add(ShipperTransportationId.ofRepoId(allocRecord.getM_ShipperTransportation_ID()));
		}

		// IsAllocated and IsReadyForReceipt are kept in step by the M_Delivery_Planning_Alloc interceptor
		// (AFTER_CHANGE on IsActive) that the setIsActive(false) + saveRecord above fires - not by an inline call here

		return DeactivatedAllocations.builder()
				.shippingPackageIds(deactivatedShippingPackageIds.build())
				.deallocatedPlanningIds(deallocatedPlanningIds.build())
				.touchedDeliveryInstructionIds(touchedDeliveryInstructionIds.build())
				.build();
	}

	@Value
	@Builder
	public static class DeactivatedAllocations
	{
		public static final DeactivatedAllocations NONE = DeactivatedAllocations.builder()
				.shippingPackageIds(ImmutableSet.of())
				.deallocatedPlanningIds(ImmutableSet.of())
				.touchedDeliveryInstructionIds(ImmutableSet.of())
				.build();

		@NonNull ImmutableSet<ShippingPackageId> shippingPackageIds;
		@NonNull ImmutableSet<DeliveryPlanningId> deallocatedPlanningIds;
		@NonNull ImmutableSet<ShipperTransportationId> touchedDeliveryInstructionIds;
	}

	/**
	 * Asked of the allocation table, NOT of the denormalised {@code M_Delivery_Planning.ReleaseNo} mirror: a
	 * mirror left saying "allocated" with no allocation row behind it would refuse forever.
	 */
	public boolean hasActiveAllocation(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return queryAllocationsByPlanningIds(ImmutableList.of(deliveryPlanningId))
				.create()
				.anyMatch();
	}

	/**
	 * Filters to {@code IsActive='N'} here rather than trusting the caller's prior check, so a concurrently
	 * inserted live row is left in place and the {@code NO ACTION} foreign key refuses the delete loudly.
	 */
	public void deleteAllocationsFor(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_IsActive, false)
				.addInArrayFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIds)
				.create()
				.delete();
	}

	/**
	 * NOT filtered by {@code IsActive}: a retired allocation names the very instruction whose history the
	 * retirement exists to keep.
	 */
	public Optional<ShipperTransportationId> getInstructionIdByShippingPackageId(@NonNull final ShippingPackageId shippingPackageId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShippingPackage_ID, shippingPackageId)
				.create()
				.firstOnlyOptional(I_M_Delivery_Planning_Alloc.class)
				.map(allocRecord -> ShipperTransportationId.ofRepoId(allocRecord.getM_ShipperTransportation_ID()));
	}

	private IQueryBuilder<I_M_Delivery_Planning_Alloc> queryAllocationsByPlanningIds(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIds);
	}

	private IQueryBuilder<I_M_Delivery_Planning_Alloc> queryActiveAllocationsByInstructionId(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShipperTransportation_ID, deliveryInstructionId)
				.orderBy().addColumnAscending(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_Alloc_ID).endOrderBy();
	}

	private static DeliveryPlanningAlloc toDeliveryPlanningAlloc(@NonNull final I_M_Delivery_Planning_Alloc allocRecord)
	{
		return DeliveryPlanningAlloc.builder()
				.id(DeliveryPlanningAllocId.ofRepoId(allocRecord.getM_Delivery_Planning_Alloc_ID()))
				.deliveryPlanningId(DeliveryPlanningId.ofRepoId(allocRecord.getM_Delivery_Planning_ID()))
				.deliveryInstructionId(ShipperTransportationId.ofRepoId(allocRecord.getM_ShipperTransportation_ID()))
				.shippingPackageId(ShippingPackageId.ofRepoId(allocRecord.getM_ShippingPackage_ID()))
				.build();
	}
}
