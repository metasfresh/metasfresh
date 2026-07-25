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
	 * All active alloc rows, for use as a sub-query filter (e.g. the drift watchdog's anti-join that finds
	 * {@code DD_OrderLine}s without any contributor).
	 */
	public IQuery<I_DD_OrderLine_PickingJobSchedule> queryAll()
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
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
