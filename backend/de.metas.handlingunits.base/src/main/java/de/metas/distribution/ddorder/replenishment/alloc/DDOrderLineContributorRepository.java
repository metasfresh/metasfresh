package de.metas.distribution.ddorder.replenishment.alloc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.model.I_DD_OrderLine_PickingJobSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Repository Tables: DD_OrderLine_PickingJobSchedule
 * Repository Cluster: DDOrderLineContributorRepository
 * <p>
 * Each row is one workstation assignment's share of a consolidated {@code DD_OrderLine}, i.e. the answer to
 * "which assignments does this line serve, and with how much each".
 */
@Repository
public class DDOrderLineContributorRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Rewrites the given line's complete contributor set: the line's existing rows are deleted and the given ones
	 * inserted. An empty list leaves the line with no contributors.
	 * <p>
	 * Runs in the caller's transaction and deliberately does NOT open its own — the group reconcile writes the alloc
	 * rows and the line quantity atomically, and it is the single writer of both.
	 */
	public void replaceContributors(@NonNull final DDOrderLineId lineId, @NonNull final List<DDOrderLineContributor> contributors)
	{
		deleteByLineIds(ImmutableSet.of(lineId));

		for (final DDOrderLineContributor contributor : contributors)
		{
			final I_DD_OrderLine_PickingJobSchedule record = InterfaceWrapperHelper.newInstance(I_DD_OrderLine_PickingJobSchedule.class);
			updateRecord(record, lineId, contributor);
			InterfaceWrapperHelper.saveRecord(record);
		}
	}

	private static void updateRecord(
			@NonNull final I_DD_OrderLine_PickingJobSchedule record,
			@NonNull final DDOrderLineId lineId,
			@NonNull final DDOrderLineContributor from)
	{
		record.setDD_OrderLine_ID(lineId.getRepoId());
		record.setM_Picking_Job_Schedule_ID(from.getPickingJobScheduleId().getRepoId());
		record.setQty(from.getQty().toBigDecimal());
		record.setC_UOM_ID(from.getQty().getUomId().getRepoId());
	}

	private static DDOrderLineContributor fromRecord(@NonNull final I_DD_OrderLine_PickingJobSchedule record)
	{
		return DDOrderLineContributor.of(
				PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID()),
				Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID())));
	}

	/**
	 * The line's contributors, ordered by {@code M_Picking_Job_Schedule_ID} so that callers (and their assertions)
	 * see a stable order.
	 */
	public ImmutableList<DDOrderLineContributor> getContributors(@NonNull final DDOrderLineId lineId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineId)
				.orderBy(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.create()
				.stream()
				.map(DDOrderLineContributorRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Every contributor row of the given lines, in one query. The rows are NOT grouped by line: the caller that needs
	 * this (the reconcile, summing what the frozen lines already serve per assignment) only ever aggregates across
	 * them, and keeping it flat avoids inventing a grouping nobody reads.
	 */
	public ImmutableList<DDOrderLineContributor> getContributorsOfLines(@NonNull final Collection<DDOrderLineId> lineIds)
	{
		if (lineIds.isEmpty()) {return ImmutableList.of();}

		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineIds)
				.orderBy(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.create()
				.stream()
				.map(DDOrderLineContributorRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableSet<PickingJobScheduleId> getContributorIds(@NonNull final Collection<DDOrderLineId> lineIds)
	{
		if (lineIds.isEmpty()) {return ImmutableSet.of();}

		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineIds)
				.create()
				.listDistinctAsImmutableSet(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID, PickingJobScheduleId.class);
	}

	public ImmutableSet<DDOrderLineId> getLineIdsByPickingJobScheduleId(@NonNull final PickingJobScheduleId pickingJobScheduleId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID, pickingJobScheduleId)
				.create()
				.listDistinctAsImmutableSet(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, DDOrderLineId.class);
	}

	/**
	 * The batched form of {@link #getLineIdsByPickingJobScheduleId(PickingJobScheduleId)}: every line any of the given
	 * assignments sits on, in one query. The group reconcile asks this once per pass for the whole contributor set
	 * (p95 13, max 52 contributors measured), so the per-contributor form would be one round-trip each.
	 */
	public ImmutableSet<DDOrderLineId> getLineIdsByPickingJobScheduleIds(@NonNull final Collection<PickingJobScheduleId> pickingJobScheduleIds)
	{
		// No assignment can own a line, so skip the round-trip. (The restriction itself is safe when empty:
		// addInArrayFilter renders an empty IN-list as "1=0" — it is addInArrayOrAllFilter that renders "1=1".)
		if (pickingJobScheduleIds.isEmpty()) {return ImmutableSet.of();}

		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID, pickingJobScheduleIds)
				.create()
				.listDistinctAsImmutableSet(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, DDOrderLineId.class);
	}

	/**
	 * All active alloc rows, for use as a sub-query filter (e.g. the drift watchdog's anti-join that finds
	 * {@code DD_OrderLine}s without any contributor).
	 */
	public IQuery<I_DD_OrderLine_PickingJobSchedule> queryAll()
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.create();
	}

	/**
	 * The active alloc rows sitting on the given {@code DD_OrderLine}s, for use as a sub-query filter whose
	 * {@code M_Picking_Job_Schedule_ID} column is the set of assignments those lines serve.
	 * <p>
	 * Handed the lines of the live DD_Orders, this is the drift watchdog's "already served" set — the association's
	 * answer to a question a single-owner back-reference column can only answer for one contributor of a
	 * consolidated order.
	 */
	public IQuery<I_DD_OrderLine_PickingJobSchedule> queryContributorsOfLines(@NonNull final IQuery<I_DD_OrderLine> ddOrderLinesQuery)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID,
						I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID,
						ddOrderLinesQuery)
				.create();
	}

	public void deleteByLineIds(@NonNull final Collection<DDOrderLineId> lineIds)
	{
		if (lineIds.isEmpty()) {return;}

		queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineIds)
				.create()
				.delete();
	}

	/**
	 * Deletes every alloc row of the given assignments, whichever line they sit on.
	 * <p>
	 * Needed by the {@code afterDelete} path: {@code DD_OrderLine_PickingJobSchedule.M_Picking_Job_Schedule_ID} is a
	 * DEFERRABLE INITIALLY DEFERRED foreign key to {@code M_Picking_Job_Schedule}, so an assignment that still has an
	 * alloc row cannot be deleted — the constraint fires at commit of the user's delete transaction. The rows must
	 * therefore go in that same transaction, exactly like the FK unlink on the voided DD_Order beside it.
	 */
	public void deleteByPickingJobScheduleIds(@NonNull final Collection<PickingJobScheduleId> pickingJobScheduleIds)
	{
		if (pickingJobScheduleIds.isEmpty()) {return;}

		queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID, pickingJobScheduleIds)
				.create()
				.delete();
	}
}
