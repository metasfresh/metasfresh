package de.metas.picking.job_schedule.repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleCollection;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.eevolution.model.I_DD_Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/**
 * Repository Tables: M_Picking_Job_Schedule (query owner); DD_Order (sub-query filter only — see {@link #streamAssignmentsNeedingDDOrder})
 * Repository Cluster: PickingJobScheduleRepository
 */
@Repository
public class PickingJobScheduleRepository
{
	@NonNull private static final AdMessageKey UPDATE_OF_PROCESSED_NOT_ALLOWED = AdMessageKey.of("UPDATE_OF_PROCESSED_NOT_ALLOWED");
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static PickingJobScheduleRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(PickingJobScheduleRepository.class, PickingJobScheduleRepository::new);
	}

	public PickingJobSchedule getById(@NonNull final PickingJobScheduleId id)
	{
		return fromRecord(load(id, I_M_Picking_Job_Schedule.class));
	}

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
		record.setProcessed(false);
		InterfaceWrapperHelper.saveRecord(record);
		return fromRecord(record);
	}

	public void save(@NonNull final PickingJobSchedule schedule)
	{
		final I_M_Picking_Job_Schedule record = load(schedule.getId(), I_M_Picking_Job_Schedule.class);
		if (record.isProcessed()) {throw new AdempiereException(UPDATE_OF_PROCESSED_NOT_ALLOWED);}
		updateRecord(record, schedule);
		InterfaceWrapperHelper.saveRecord(record);
	}

	private static void updateRecord(@NonNull final I_M_Picking_Job_Schedule record, final @NotNull PickingJobSchedule from)
	{
		record.setM_ShipmentSchedule_ID(from.getShipmentScheduleId().getRepoId());
		record.setC_Workplace_ID(from.getWorkplaceId().getRepoId());
		record.setC_UOM_ID(from.getQtyToPick().getUomId().getRepoId());
		record.setQtyToPick(from.getQtyToPick().toBigDecimal());
		record.setProcessed(from.isProcessed());
	}

	/**
	 * Boundary factory: maps an already-loaded {@link I_M_Picking_Job_Schedule} record to its domain object.
	 * <p>
	 * Public so that a model interceptor that already holds the record (e.g. {@code M_Picking_Job_Schedule_DDOrderPickingInterceptor})
	 * can build the domain object without a redundant {@link #getById} reload. Callers that do NOT already hold the record
	 * must go through {@link #getById}/{@link #findByIdOrNull} rather than loading the record themselves.
	 */
	public static PickingJobSchedule fromRecord(final I_M_Picking_Job_Schedule record)
	{
		return PickingJobSchedule.builder()
				.id(PickingJobScheduleId.ofRepoId(record.getM_Picking_Job_Schedule_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.workplaceId(WorkplaceId.ofRepoId(record.getC_Workplace_ID()))
				.qtyToPick(Quantitys.of(record.getQtyToPick(), UomId.ofRepoId(record.getC_UOM_ID())))
				.active(record.isActive())
				.processed(record.isProcessed())
				.build();
	}

	@Nullable
	public PickingJobSchedule findByIdOrNull(@NonNull final PickingJobScheduleId id)
	{
		final I_M_Picking_Job_Schedule record = load(id, I_M_Picking_Job_Schedule.class);
		return record != null ? fromRecord(record) : null;
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
					if (record.isProcessed()) {throw new AdempiereException(UPDATE_OF_PROCESSED_NOT_ALLOWED);}
					updateRecord(record, scheduleUpdated);
					InterfaceWrapperHelper.saveRecord(record);
				}
			}
		}
	}

	public PickingJobScheduleCollection deleteByIdsAndReturn(final @NonNull Set<PickingJobScheduleId> jobScheduleIds)
	{
		if (jobScheduleIds.isEmpty())
		{
			return PickingJobScheduleCollection.EMPTY;
		}

		final List<I_M_Picking_Job_Schedule> records = queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID, jobScheduleIds)
				.create()
				.list();
		if (records.isEmpty())
		{
			return PickingJobScheduleCollection.EMPTY;
		}

		final PickingJobScheduleCollection deletedSchedules = records.stream()
				.map(PickingJobScheduleRepository::fromRecord)
				.collect(PickingJobScheduleCollection.collect());

		InterfaceWrapperHelper.deleteAll(records);

		return deletedSchedules;
	}

	public PickingJobScheduleCollection list(@NonNull final PickingJobScheduleQuery query)
	{
		return stream(query).collect(PickingJobScheduleCollection.collect());
	}

	public Stream<PickingJobSchedule> stream(@NonNull final PickingJobScheduleQuery query)
	{
		return toSqlQuery(query)
				.stream()
				.map(PickingJobScheduleRepository::fromRecord);
	}

	public boolean anyMatch(@NonNull final PickingJobScheduleQuery query)
	{
		return toSqlQuery(query).anyMatch();
	}

	private IQuery<I_M_Picking_Job_Schedule> toSqlQuery(@NonNull final PickingJobScheduleQuery query)
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

		if (!query.getOnlyShipmentScheduleIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID, query.getOnlyShipmentScheduleIds());
		}

		if (query.getIsProcessed() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_Processed, query.getIsProcessed());
		}

		return queryBuilder.create();
	}

	/**
	 * Cross-entity anti-join: streams active, not-yet-processed schedules that are NOT yet referenced by a completed
	 * DD_Order ({@code M_Picking_Job_Schedule LEFT-anti-JOIN DD_Order} on {@code DD_Order.M_Picking_Job_Schedule_ID}).
	 * <p>
	 * {@code completedDDOrdersQuery} is passed in by the caller (the DD_Order reconcile flow) rather than built here,
	 * so {@code DD_Order} stays a sub-query filter and not a table this repository owns.
	 */
	public Stream<PickingJobSchedule> streamAssignmentsNeedingDDOrder(@NonNull final IQuery<I_DD_Order> completedDDOrdersQuery)
	{
		final IQuery<I_M_Picking_Job_Schedule> query = queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_Processed, false)
				.addNotInSubQueryFilter(
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						I_DD_Order.COLUMNNAME_M_Picking_Job_Schedule_ID,
						completedDDOrdersQuery)
				.create();
		Loggables.addLog("AssignmentsNeedingDDOrder - query: {}", query);

		return query.iterateAndStream().map(PickingJobScheduleRepository::fromRecord);
	}
}
