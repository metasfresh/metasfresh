package de.metas.distribution.ddorder.replenishment.alloc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.model.I_DD_OrderLine_PickingJobSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Manages the {@code DD_OrderLine_PickingJobSchedule} alloc rows: each row is one workstation assignment's share of a consolidated {@code DD_OrderLine}.
 *
 * Repository Tables: DD_OrderLine_PickingJobSchedule
 * Repository Cluster: DDOrderLineContributorRepository
 */
@Repository
public class DDOrderLineContributorRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Runs in the caller's transaction (no {@code @Transactional} of its own) so the alloc rows commit atomically with the line quantity write.
	 */
	public void replaceByLineId(@NonNull final DDOrderLineId lineId, @NonNull final List<DDOrderLineContributor> contributors)
	{
		final ImmutableSet<PickingJobScheduleId> wantedPickingJobScheduleIds = extractPickingJobScheduleIds(lineId, contributors);

		final HashMap<PickingJobScheduleId, I_DD_OrderLine_PickingJobSchedule> reusableRecords = new HashMap<>();
		final ArrayList<I_DD_OrderLine_PickingJobSchedule> obsoleteRecords = new ArrayList<>();
		for (final I_DD_OrderLine_PickingJobSchedule record : retrieveRecordsByLineId(lineId))
		{
			final PickingJobScheduleId pickingJobScheduleId = PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID());
			// The unique index is partial, so an inactive duplicate row for the pair is possible and must still be deleted.
			if (!wantedPickingJobScheduleIds.contains(pickingJobScheduleId)
					|| reusableRecords.putIfAbsent(pickingJobScheduleId, record) != null)
			{
				obsoleteRecords.add(record);
			}
		}

		// Must precede the saves: ddorderline_pjs_active_uidx is an INDEX, hence not deferrable, so a stale sibling row still
		// present would fail the reused row's save with 23505 instead of at commit.
		InterfaceWrapperHelper.deleteAll(obsoleteRecords);

		for (final DDOrderLineContributor contributor : contributors)
		{
			I_DD_OrderLine_PickingJobSchedule record = reusableRecords.remove(contributor.getPickingJobScheduleId());
			if (record == null)
			{
				record = InterfaceWrapperHelper.newInstance(I_DD_OrderLine_PickingJobSchedule.class);
			}
			updateRecord(record, lineId, contributor);
			InterfaceWrapperHelper.saveRecord(record);
		}
	}

	/**
	 * Rejects two shares of ONE assignment: the pair has a single {@code Qty} column, so whether it should carry the sum or the
	 * last share is the caller's decision, not this repository's — and collapsing it here would silently mask a broken attribution.
	 */
	private static ImmutableSet<PickingJobScheduleId> extractPickingJobScheduleIds(
			@NonNull final DDOrderLineId lineId,
			@NonNull final List<DDOrderLineContributor> contributors)
	{
		final ImmutableSet<PickingJobScheduleId> pickingJobScheduleIds = contributors.stream()
				.map(DDOrderLineContributor::getPickingJobScheduleId)
				.collect(ImmutableSet.toImmutableSet());
		if (pickingJobScheduleIds.size() != contributors.size())
		{
			throw new AdempiereException("An M_Picking_Job_Schedule_ID contributes more than once to DD_OrderLine_ID="
					+ lineId.getRepoId() + ": " + contributors);
		}

		return pickingJobScheduleIds;
	}

	private ImmutableList<I_DD_OrderLine_PickingJobSchedule> retrieveRecordsByLineId(@NonNull final DDOrderLineId lineId)
	{
		// Deliberately not filtered to active records: an inactive row must still be reconciled, not left behind as a duplicate.
		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addEqualsFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineId)
				.create()
				.listImmutable(I_DD_OrderLine_PickingJobSchedule.class);
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
		// A reused row must end up as active as a freshly inserted one would have been; see retrieveRecordsByLineId.
		record.setIsActive(true);
	}

	private static DDOrderLineContributor fromRecord(@NonNull final I_DD_OrderLine_PickingJobSchedule record)
	{
		return DDOrderLineContributor.of(
				PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID()),
				Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID())));
	}

	public ImmutableList<DDOrderLineContributor> getByLineId(@NonNull final DDOrderLineId lineId)
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

	public ImmutableList<DDOrderLineContributor> getByLineIds(@NonNull final Collection<DDOrderLineId> lineIds)
	{
		if (lineIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineIds)
				.orderBy(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.create()
				.stream()
				.map(DDOrderLineContributorRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * The per-line flavour of {@link #getPickingJobScheduleIds(Collection)}: a line with no contributor row is absent from the result.
	 */
	public ImmutableSetMultimap<DDOrderLineId, PickingJobScheduleId> getPickingJobScheduleIdsByLineId(@NonNull final Collection<DDOrderLineId> lineIds)
	{
		if (lineIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineIds)
				.orderBy(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						record -> DDOrderLineId.ofRepoId(record.getDD_OrderLine_ID()),
						record -> PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID())));
	}

	public ImmutableSet<PickingJobScheduleId> getPickingJobScheduleIds(@NonNull final Collection<DDOrderLineId> lineIds)
	{
		if (lineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

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

	public ImmutableSet<DDOrderLineId> getLineIdsByPickingJobScheduleIds(@NonNull final Collection<PickingJobScheduleId> pickingJobScheduleIds)
	{
		// Optimization only: addInArrayFilter already renders an empty IN-list as "1=0" (addInArrayOrAllFilter would render "1=1").
		if (pickingJobScheduleIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID, pickingJobScheduleIds)
				.create()
				.listDistinctAsImmutableSet(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, DDOrderLineId.class);
	}

	public IQuery<I_DD_OrderLine_PickingJobSchedule> queryAll()
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addOnlyActiveRecordsFilter()
				.create();
	}

	public IQuery<I_DD_OrderLine_PickingJobSchedule> queryByLines(@NonNull final IQuery<I_DD_OrderLine> ddOrderLinesQuery)
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
		if (lineIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_DD_OrderLine_ID, lineIds)
				.create()
				.delete();
	}

	/**
	 * {@code M_Picking_Job_Schedule_ID} is a DEFERRABLE INITIALLY DEFERRED FK, so this must run in the same transaction as the assignment delete.
	 */
	public void deleteByPickingJobScheduleIds(@NonNull final Collection<PickingJobScheduleId> pickingJobScheduleIds)
	{
		if (pickingJobScheduleIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_DD_OrderLine_PickingJobSchedule.class)
				.addInArrayFilter(I_DD_OrderLine_PickingJobSchedule.COLUMNNAME_M_Picking_Job_Schedule_ID, pickingJobScheduleIds)
				.create()
				.delete();
	}
}
