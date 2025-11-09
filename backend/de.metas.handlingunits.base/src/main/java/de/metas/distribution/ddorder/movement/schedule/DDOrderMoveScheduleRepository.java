package de.metas.distribution.ddorder.movement.schedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_DD_OrderLine_HU_Candidate;
import de.metas.handlingunits.model.I_DD_Order_MoveSchedule;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Repository
public class DDOrderMoveScheduleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private DDOrderMoveScheduleLoaderAndSaver newLoaderAndSaver()
	{
		return DDOrderMoveScheduleLoaderAndSaver.builder()
				.queryBL(queryBL)
				.build();
	}

	public DDOrderMoveSchedule getById(@NonNull final DDOrderMoveScheduleId id)
	{
		return newLoaderAndSaver().loadById(id);
	}

	public List<DDOrderMoveSchedule> getByIds(@NonNull final Set<DDOrderMoveScheduleId> ids)
	{
		if (ids.isEmpty()) {return ImmutableList.of();}
		return newLoaderAndSaver().loadByIds(ids);
	}

	public IQueryFilter<I_M_HU> getHUsNotAlreadyScheduledToMoveFilter()
	{
		final ICompositeQueryFilter<I_M_HU> filter = queryBL.createCompositeQueryFilter(I_M_HU.class);

		// HU Filter: only those which were not already assigned to a DD_OrderLine
		{
			final IQuery<I_DD_OrderLine_HU_Candidate> scheduledInProgress = queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED, DDOrderMoveScheduleStatus.IN_PROGRESS)
					.create();
			filter.addNotInSubQueryFilter(I_M_HU.COLUMN_M_HU_ID, I_DD_OrderLine_HU_Candidate.COLUMN_M_HU_ID, scheduledInProgress);
		}

		return filter;
	}

	public ImmutableList<DDOrderMoveSchedule> createScheduleToMoveBulk(@NonNull final List<DDOrderMoveScheduleCreateRequest> requests)
	{
		return newLoaderAndSaver().createSchedulesToMove(requests);
	}

	public DDOrderMoveSchedule createScheduleToMove(@NonNull final DDOrderMoveScheduleCreateRequest request)
	{
		return newLoaderAndSaver().createScheduleToMove(request);
	}

	public DDOrderMoveSchedule updateById(final DDOrderMoveScheduleId id, Consumer<DDOrderMoveSchedule> updater)
	{
		return newLoaderAndSaver().updateById(id, updater);
	}

	public List<DDOrderMoveSchedule> updateByIds(final Set<DDOrderMoveScheduleId> ids, Consumer<DDOrderMoveSchedule> updater)
	{
		return newLoaderAndSaver().updateByIds(ids, updater);
	}

	/**
	 * @implNote pls keep in sync with toDDOrderMoveSchedule.
	 */
	public void save(@NonNull final DDOrderMoveSchedule schedule)
	{
		newLoaderAndSaver().save(schedule);
	}

	public final List<HuId> retrieveHUIdsScheduledButNotMovedYet(@NonNull final DDOrderLineId ddOrderLineId)
	{
		return queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED)
				.orderBy(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID)
				.create()
				.listDistinct(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID, HuId.class);
	}

	public void removeNotStarted(@NonNull final DDOrderLineId ddOrderLineId)
	{
		final ImmutableSet<DDOrderMoveScheduleId> scheduleIds = queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED)
				.create()
				.idsAsSet(DDOrderMoveScheduleId::ofRepoId);

		deleteByScheduleIds(scheduleIds);
	}

	public void deleteNotStarted(@NonNull final DDOrderMoveScheduleId scheduleId)
	{
		getById(scheduleId).assertNotStarted();
		deleteByScheduleIds(ImmutableSet.of(scheduleId));
	}

	public void removeNotStarted(@NonNull final DDOrderId ddOrderId)
	{
		final ImmutableSet<DDOrderMoveScheduleId> scheduleIds = queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_ID, ddOrderId)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_Status, DDOrderMoveScheduleStatus.NOT_STARTED)
				.create()
				.idsAsSet(DDOrderMoveScheduleId::ofRepoId);

		deleteByScheduleIds(scheduleIds);
	}

	private void deleteByScheduleIds(final Set<DDOrderMoveScheduleId> scheduleIds)
	{
		if (scheduleIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addInArrayFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_Order_MoveSchedule_ID, scheduleIds)
				.create()
				.delete();

		queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addInArrayFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_MoveSchedule_ID, scheduleIds)
				.create()
				.delete();
	}

	public void removeFromHUsScheduledToMoveList(@NonNull final DDOrderLineId ddOrderLineId, @NonNull final Set<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return;
		}

		//
		// Create the query to select the lines we want to remove
		queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addInArrayFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.delete();
	}

	public boolean isScheduledToMove(@NonNull final HuId huId)
	{
		// TODO: only not processed ones

		return queryBL.createQueryBuilder(I_DD_OrderLine_HU_Candidate.class)
				.addEqualsFilter(I_DD_OrderLine_HU_Candidate.COLUMNNAME_M_HU_ID, huId)
				.create()
				.anyMatch();
	}

	public ImmutableList<DDOrderMoveSchedule> getSchedules(final DDOrderId ddOrderId)
	{
		return newLoaderAndSaver().loadByDDOrderId(ddOrderId);
	}

	public boolean hasInProgressSchedules(@NonNull final DDOrderLineId ddOrderLineId)
	{
		return queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_OrderLine_ID, ddOrderLineId)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_Status, DDOrderMoveScheduleStatus.IN_PROGRESS)
				.create()
				.anyMatch();
	}

	public boolean hasInProgressSchedules(final DDOrderId ddOrderId)
	{
		return queryBL.createQueryBuilder(I_DD_Order_MoveSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_DD_Order_ID, ddOrderId)
				.addEqualsFilter(I_DD_Order_MoveSchedule.COLUMNNAME_Status, DDOrderMoveScheduleStatus.IN_PROGRESS)
				.create()
				.anyMatch();
	}
}
