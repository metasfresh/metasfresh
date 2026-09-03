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
import de.metas.cache.CacheMgt;
import de.metas.organization.OrgId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.ISqlQueryUpdater;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Delivery_Planning;
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
 * Repository Tables: M_Delivery_Planning_Alloc
 * Repository Cluster: DeliveryPlanningAllocRepository (sole owner of M_Delivery_Planning_Alloc),
 * DeliveryPlanningRepository (M_Delivery_Planning), DeliveryInstructionRepository (M_ShipperTransportation and
 * its M_ShippingPackage lines), MPackageRepository (M_Package). An allocation names records of all three of the
 * other tables, but only ever by id: the records themselves are read and written by their own repository, and
 * {@link DeliveryInstructionService} composes them.
 * <p>
 * The ONE deliberate exception is {@link #refreshIsAllocated(DeliveryPlanningId)}, which writes
 * {@code M_Delivery_Planning.IsAllocated}: that column is this table's mirror, the {@code EXISTS} that derives it
 * reads this table, and folding it into the {@code UPDATE}'s own {@code SET} clause is what keeps the refresh a
 * single statement instead of a {@code SELECT} plus an {@code UPDATE}. Splitting it across two repositories would
 * cost a round trip on a path the {@code M_Delivery_Planning_Alloc} interceptor runs for every allocation write.
 */
@Repository
public class DeliveryPlanningAllocRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Creates ONE allocation row. The shipping package it points at is created first by
	 * {@link DeliveryInstructionService}, because {@code M_ShippingPackage_ID} is mandatory here and uniquely
	 * indexed.
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

		// IsAllocated is kept in step by the M_Delivery_Planning_Alloc @ModelChange interceptor (AFTER_NEW),
		// triggered by the saveRecord above - not by an inline call here, so a future write path that inserts
		// an alloc row without going through this method still keeps the mirror correct.

		return DeliveryPlanningAllocId.ofRepoId(allocRecord.getM_Delivery_Planning_Alloc_ID());
	}

	/**
	 * The ACTIVE allocations of each of the given plannings, grouped by planning - a planning without one is
	 * absent from the result. A multimap rather than a one-key-per-planning map: a planning may be allocated to
	 * more than one instruction.
	 */
	public ImmutableListMultimap<DeliveryPlanningId, DeliveryPlanningAlloc> getAllocationsByPlanningId(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
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

	/**
	 * The ACTIVE allocations the given instruction currently holds, in ONE round trip - the other direction of
	 * {@link #getAllocationsByPlanningId(Collection)}.
	 */
	public ImmutableList<DeliveryPlanningAlloc> getAllocationsOfInstruction(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		return queryActiveAllocationsByInstructionId(deliveryInstructionId)
				.create()
				.stream()
				.map(DeliveryPlanningAllocRepository::toDeliveryPlanningAlloc)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * The delivery instructions ONE planning is currently allocated to, distinct; empty for a planning on none.
	 */
	public ImmutableSet<ShipperTransportationId> getAllocatedInstructionIdsOf(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return getAllocationsByPlanningId(ImmutableList.of(deliveryPlanningId))
				.values()
				.stream()
				.map(DeliveryPlanningAlloc::getDeliveryInstructionId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * The delivery plannings the given instruction currently holds, as ids in a stable order - a rejection that
	 * names them has to read the same on two identical runs.
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
	 * Deactivates - rather than deletes - the given plannings' ACTIVE allocations, so the record of what was once
	 * planned survives. A deactivated allocation is left alone: it records an instruction the planning was taken
	 * off earlier, which is not what the caller is undoing.
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
	 * On void or cancel of the delivery instruction: the allocations are deactivated rather than deleted.
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
	 * Shared by both {@code deactivateBy...} entry points, and the single choke point every path that ends an
	 * allocation's active life routes through - which is why {@code DateRemoved} is stamped here and nowhere
	 * else. Both entry queries select ACTIVE allocations only, so the stamp is written once per allocation.
	 * <p>
	 * Cost note: each row's {@code saveRecord(allocRecord)} below fires the {@code M_Delivery_Planning_Alloc}
	 * interceptor individually, so deactivating N allocations here costs N {@code IsAllocated} {@code UPDATE}s
	 * (via {@link #refreshIsAllocated(DeliveryPlanningId)}) - e.g. voiding one delivery instruction that
	 * carries N plannings. Not batched into one {@code UPDATE ... WHERE id IN (...)} in this round: doing so
	 * would mean either re-introducing an inline call here (defeating the interceptor's structural guarantee -
	 * a future write path outside this loop would again need to remember it) or adding a transaction-scoped
	 * collector that accumulates touched planning ids and flushes one batched statement at commit. The latter
	 * is a real option if N grows large in practice, but it is a caching-like layer with its own correctness
	 * questions (multiple accumulate-then-flush cycles per transaction, ordering against other readers of
	 * {@code IsAllocated} mid-transaction) that deserves its own deliberate decision, not one folded into this
	 * correctness fix.
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

		// IsAllocated is kept in step by the M_Delivery_Planning_Alloc @ModelChange interceptor (AFTER_CHANGE
		// on IsActive), triggered by the allocRecord.setIsActive(false) + saveRecord above - not by an inline
		// call here, so a future deactivation path (a bulk fix, an import routine) still keeps the mirror correct.

		return DeactivatedAllocations.builder()
				.shippingPackageIds(deactivatedShippingPackageIds.build())
				.deallocatedPlanningIds(deallocatedPlanningIds.build())
				.touchedDeliveryInstructionIds(touchedDeliveryInstructionIds.build())
				.build();
	}

	/**
	 * What {@link #deactivate} produces: the shipping packages whose allocation was retired - the caller
	 * deactivates them through their own repository - which plannings that touched, and which instructions have
	 * to recompute their {@code DeliveredState} as a result.
	 */
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
	 * Whether the given planning is currently on a delivery instruction - asked of the allocation table, NOT of
	 * the denormalised {@code M_Delivery_Planning.ReleaseNo} mirror: a mirror left saying "allocated" with no
	 * allocation row behind it would refuse forever, and the planning could never be deleted nor planned again.
	 */
	public boolean hasActiveAllocation(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		return queryAllocationsByPlanningIds(ImmutableList.of(deliveryPlanningId))
				.create()
				.anyMatch();
	}

	/**
	 * Removes the given planning's retired allocation rows - the cleanup a delete of the planning itself owes.
	 * Filters to {@code IsActive='N'} here rather than trusting the caller's prior check, so a concurrently
	 * inserted live row is left in place and the {@code NO ACTION} foreign key refuses the delete loudly. The
	 * shipping packages are left alone: they are the instruction's own lines, and it still exists.
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
	 * The delivery instruction the given shipping package is allocated to, if any - NOT filtered by
	 * {@code IsActive}, because a retired allocation names the very instruction whose history the retirement
	 * exists to keep. {@code firstOnlyOptional}: several allocation rows for one package would be a defect.
	 */
	public Optional<ShipperTransportationId> getInstructionIdByShippingPackageId(@NonNull final ShippingPackageId shippingPackageId)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addEqualsFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_ShippingPackage_ID, shippingPackageId)
				.create()
				.firstOnlyOptional(I_M_Delivery_Planning_Alloc.class)
				.map(allocRecord -> ShipperTransportationId.ofRepoId(allocRecord.getM_ShipperTransportation_ID()));
	}

	/**
	 * Called by the {@code M_Delivery_Planning_Alloc} {@code @ModelChange} interceptor on every event that can
	 * change which planning an ACTIVE allocation points at (AFTER_NEW, AFTER_CHANGE of {@code IsActive},
	 * AFTER_DELETE) - the single place that re-derives and writes the {@code IsAllocated} mirror, so every
	 * writer of the allocation table keeps it correct automatically, including one that does not exist yet.
	 * <p>
	 * ONE SQL {@code UPDATE} per call, with the {@code EXISTS} check folded directly into that statement's
	 * {@code SET} clause (via {@link IsAllocatedFromAllocTableUpdater} below) - deliberately not a separate
	 * {@link #hasActiveAllocation(DeliveryPlanningId)} {@code SELECT} followed by a second {@code UPDATE}, and
	 * the reason this one write of a foreign table stays with the allocation table that derives it (see the
	 * class javadoc). Neither statement loads a {@code I_M_Delivery_Planning} row, so this adds no
	 * {@code DeliveryPlanningRepository#getById} / {@code #getByIds} round trip - both are
	 * batch-load-discipline-tested elsewhere (see {@code DeliveryPlanningBatchLoadingTest}).
	 * <p>
	 * Still ONE statement PER PLANNING ID, not batched across several: {@link #deactivate} calls this once per
	 * row inside its loop (one {@code saveRecord} per allocation fires the interceptor once), so voiding an
	 * instruction that carries N plannings costs N {@code UPDATE}s here - see that method's own note on why this
	 * round does not turn that into a transaction-scoped batch.
	 * <p>
	 * {@code updateDirectly} is a raw SQL {@code UPDATE} - it fires no {@code CacheMgt} reset and no
	 * interceptor - so the explicit {@link CacheMgt#reset(String, int)} below is required: this method exists
	 * precisely to cover allocation writers that touch only {@code M_Delivery_Planning_Alloc} (e.g. a future
	 * bulk fix or import routine looping {@code InterfaceWrapperHelper.save} over allocation rows without
	 * saving the planning record itself). Without the reset, a cached {@code I_M_Delivery_Planning} row - an
	 * operator's Lieferplanung window already holding it open, say - would keep showing the pre-change
	 * {@code IsAllocated} until an unrelated write on that same row happened to invalidate it, which is exactly
	 * the staleness this column could never have before it was a live {@code ColumnSQL} (5821150).
	 */
	public void refreshIsAllocated(@NonNull final DeliveryPlanningId deliveryPlanningId)
	{
		queryBL.createQueryBuilder(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId)
				.create()
				.updateDirectly(new IsAllocatedFromAllocTableUpdater());

		CacheMgt.get().reset(I_M_Delivery_Planning.Table_Name, deliveryPlanningId.getRepoId());
	}

	/**
	 * Sets {@code IsAllocated} from the same {@code EXISTS} the column's old {@code ColumnSQL} evaluated
	 * (5821150), computed in the {@code UPDATE}'s own {@code SET} clause rather than pre-fetched - an
	 * {@link ISqlQueryUpdater}, so {@link IQuery#updateDirectly} issues one raw SQL {@code UPDATE} and never
	 * calls {@link #update(I_M_Delivery_Planning)} (the load-and-save fallback for a non-SQL query engine,
	 * kept correct but never exercised against Postgres).
	 */
	private final class IsAllocatedFromAllocTableUpdater implements ISqlQueryUpdater<I_M_Delivery_Planning>
	{
		@Override
		public String getSql(final Properties ctx, final List<Object> sqlParams)
		{
			return I_M_Delivery_Planning.COLUMNNAME_IsAllocated
					+ " = (case when exists (select 1 from " + I_M_Delivery_Planning_Alloc.Table_Name
					+ " a where a." + I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID
					+ " = " + I_M_Delivery_Planning.Table_Name + "." + I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID
					+ " and a." + I_M_Delivery_Planning_Alloc.COLUMNNAME_IsActive + " = 'Y') then 'Y' else 'N' end)";
		}

		@Override
		public boolean update(final I_M_Delivery_Planning deliveryPlanningRecord)
		{
			final DeliveryPlanningId deliveryPlanningId = DeliveryPlanningId.ofRepoId(deliveryPlanningRecord.getM_Delivery_Planning_ID());
			deliveryPlanningRecord.setIsAllocated(hasActiveAllocation(deliveryPlanningId));
			return true;
		}
	}

	private IQueryBuilder<I_M_Delivery_Planning_Alloc> queryAllocationsByPlanningIds(@NonNull final Collection<DeliveryPlanningId> deliveryPlanningIds)
	{
		return queryBL.createQueryBuilder(I_M_Delivery_Planning_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_Delivery_Planning_Alloc.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIds);
	}

	/**
	 * The instruction's ACTIVE allocations, in a stable allocation-id order.
	 */
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
