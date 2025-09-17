package de.metas.handlingunits.picking.job_schedule.repository;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_Picking_Job_Schedule;
import de.metas.handlingunits.picking.job_schedule.model.PickingJobSchedule;
import de.metas.handlingunits.picking.job_schedule.model.PickingJobScheduleId;
import de.metas.handlingunits.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleId;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Repository
public class PickingJobScheduleRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public List<PickingJobSchedule> getByIds(@NonNull final Set<PickingJobScheduleId> ids)
	{
		if (ids.isEmpty()) {return ImmutableList.of();}
		return InterfaceWrapperHelper.loadByRepoIdAwares(ids, I_M_Picking_Job_Schedule.class)
				.stream()
				.map(PickingJobScheduleRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public PickingJobSchedule create(@NonNull final PickingJobScheduleCreateRepoRequest request)
	{
		final I_M_Picking_Job_Schedule record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Schedule.class);
		record.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		record.setC_Workplace_ID(request.getWorkplaceId().getRepoId());
		record.setC_UOM_ID(request.getQtyToPick().getUomId().getRepoId());
		record.setQtyToPick(request.getQtyToPick().toBigDecimal());
		InterfaceWrapperHelper.saveRecord(record);
		return fromRecord(record);
	}

	public void save(@NonNull final PickingJobSchedule schedule)
	{
		final I_M_Picking_Job_Schedule record = InterfaceWrapperHelper.load(schedule.getId(), I_M_Picking_Job_Schedule.class);
		updateRecord(record, schedule);
		InterfaceWrapperHelper.saveRecord(record);
	}

	private static void updateRecord(@NonNull final I_M_Picking_Job_Schedule record, final @NotNull PickingJobSchedule from)
	{
		record.setM_ShipmentSchedule_ID(from.getShipmentScheduleId().getRepoId());
		record.setC_Workplace_ID(from.getWorkplaceId().getRepoId());
		record.setC_UOM_ID(from.getQtyToPick().getUomId().getRepoId());
		record.setQtyToPick(from.getQtyToPick().toBigDecimal());
	}

	private static PickingJobSchedule fromRecord(final I_M_Picking_Job_Schedule record)
	{
		return PickingJobSchedule.builder()
				.id(PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.workplaceId(WorkplaceId.ofRepoId(record.getC_Workplace_ID()))
				.qtyToPick(Quantitys.of(record.getQtyToPick(), UomId.ofRepoId(record.getC_UOM_ID())))
				.build();
	}

	public void updateByIds(@NonNull final Set<PickingJobScheduleId> ids, @NonNull final UnaryOperator<PickingJobSchedule> updater)
	{
		if (ids.isEmpty())
		{
			return;
		}

		for (final I_M_Picking_Job_Schedule record : InterfaceWrapperHelper.loadByRepoIdAwares(ids, I_M_Picking_Job_Schedule.class))
		{
			final PickingJobSchedule schedule = fromRecord(record);
			final PickingJobSchedule scheduleUpdated = updater.apply(schedule);
			if (!Objects.equals(schedule, scheduleUpdated))
			{
				if (scheduleUpdated == null)
				{
					InterfaceWrapperHelper.delete(record);
				}
				else
				{
					updateRecord(record, scheduleUpdated);
					InterfaceWrapperHelper.saveRecord(record);
				}
			}
		}
	}

	public ShipmentScheduleAndJobScheduleIdSet getIdsByShipmentScheduleIdsAndWorkplaceId(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds, @NonNull WorkplaceId workplaceId)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ShipmentScheduleAndJobScheduleIdSet.EMPTY;
		}

		return queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID, workplaceId)
				.create()
				.stream()
				.map(PickingJobScheduleRepository::extractShipmentScheduleAndJobScheduleId)
				.collect(ShipmentScheduleAndJobScheduleIdSet.collect());
	}

	private static ShipmentScheduleAndJobScheduleId extractShipmentScheduleAndJobScheduleId(final I_M_Picking_Job_Schedule record)
	{
		return ShipmentScheduleAndJobScheduleId.of(
				ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()),
				PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID())
		);
	}

	public void deleteJobSchedulesById(final @NonNull Set<PickingJobScheduleId> jobScheduleIds)
	{
		if (jobScheduleIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID, jobScheduleIds)
				.create()
				.delete();
	}

	public List<PickingJobSchedule> list(@NonNull PickingJobScheduleQuery query)
	{
		return stream(query).collect(ImmutableList.toImmutableList());
	}

	public Stream<PickingJobSchedule> stream(@NonNull PickingJobScheduleQuery query)
	{
		return toSqlQuery(query)
				.stream()
				.map(PickingJobScheduleRepository::fromRecord);
	}

	private IQuery<I_M_Picking_Job_Schedule> toSqlQuery(@NonNull PickingJobScheduleQuery query)
	{
		if (query.isAny())
		{
			throw new AdempiereException("Any query is not allowed");
		}

		final IQueryBuilder<I_M_Picking_Job_Schedule> queryBuilder = queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.orderBy(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID)
				.orderBy(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.addOnlyActiveRecordsFilter();

		if (!query.getWorkplaceIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID, query.getWorkplaceIds());
		}

		if (!query.getExcludeJobScheduleIds().isEmpty())
		{
			queryBuilder.addNotInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID, query.getExcludeJobScheduleIds());
		}

		return queryBuilder.create();
	}
}
