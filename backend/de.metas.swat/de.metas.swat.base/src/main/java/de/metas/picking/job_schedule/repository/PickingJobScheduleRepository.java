package de.metas.picking.job_schedule.repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleCollection;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.product.ProductId;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/**
 * Repository Tables: M_Picking_Job_Schedule (query owner);
 * M_ShipmentSchedule (sub-query filter only — see {@link #listContributorsOfGroup(ProductId, UomId, Set)})
 * Repository Cluster: PickingJobScheduleRepository
 */
@Repository
public class PickingJobScheduleRepository
{
	@NonNull private static final AdMessageKey UPDATE_OF_PROCESSED_NOT_ALLOWED = AdMessageKey.of("UPDATE_OF_PROCESSED_NOT_ALLOWED");
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * PostgreSQL/JDBC caps bind parameters at {@code Short.MAX_VALUE} (32767) — a 2-byte slot per parameter. This
	 * constant caps the number of {@code M_ShipmentSchedule_ID} values folded into a single {@code IN (...)} filter,
	 * with headroom below that hard limit (mirrors the rationale of {@code org.adempiere.ad.persistence.TableModelLoader.MAX_IDS_PER_QUERY}).
	 * NOTE: this bounds only the {@code M_ShipmentSchedule_ID} IN-list. It does not budget for other bind parameters
	 * in the same statement (e.g. a large {@code C_Workplace_ID}/exclude filter). No current caller combines those with
	 * a near-cap shipment-schedule set; a future one would have to account for the combined bind-parameter count.
	 */
	@VisibleForTesting static final int MAX_SHIPMENT_SCHEDULE_IDS_PER_QUERY = 30000;

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

	/** Public only for a caller that ALREADY holds the record (a model interceptor); everybody else goes through {@link #getById}. */
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
		return stream(query, MAX_SHIPMENT_SCHEDULE_IDS_PER_QUERY);
	}

	@VisibleForTesting
	Stream<PickingJobSchedule> stream(@NonNull final PickingJobScheduleQuery query, final int maxIdsPerChunk)
	{
		final ImmutableSet<ShipmentScheduleId> onlyShipmentScheduleIds = query.getOnlyShipmentScheduleIds();
		if (onlyShipmentScheduleIds.size() <= maxIdsPerChunk)
		{
			return toSqlQuery(query, onlyShipmentScheduleIds)
					.stream()
					.map(PickingJobScheduleRepository::fromRecord);
		}

		// Each chunk is a separate statement, so under READ COMMITTED there is no cross-chunk MVCC snapshot consistency for id
		// sets > MAX_SHIPMENT_SCHEDULE_IDS_PER_QUERY. Acceptable here: callers group the result by M_ShipmentSchedule_ID (order-
		// and snapshot-independent).
		final Iterable<List<ShipmentScheduleId>> partitions = Iterables.partition(onlyShipmentScheduleIds, maxIdsPerChunk);
		return Streams.stream(partitions)
				.flatMap(chunk -> toSqlQuery(query, chunk)
						.stream()
						.map(PickingJobScheduleRepository::fromRecord));
	}

	public boolean anyMatch(@NonNull final PickingJobScheduleQuery query)
	{
		// Not chunked: the only caller passes a single id. If a caller ever needs a large set here, chunk it as stream() does
		// (see the deferred unbounded-parameter audit).
		return toSqlQuery(query, query.getOnlyShipmentScheduleIds()).anyMatch();
	}

	private IQuery<I_M_Picking_Job_Schedule> toSqlQuery(@NonNull final PickingJobScheduleQuery query, @NonNull final Collection<ShipmentScheduleId> shipmentScheduleIds)
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

		if (!shipmentScheduleIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds);
		}

		if (query.getIsProcessed() != null)
		{
			queryBuilder.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_Processed, query.getIsProcessed());
		}

		return queryBuilder.create();
	}

	/**
	 * Unordered on purpose (the caller establishes attribution order via {@code PriorityRule}/prep-date); the {@code C_Workplace_ID} filter
	 * is what keeps this index-served by the partial index on {@code (c_workplace_id) WHERE Processed = 'N'} — a future variant must keep both filters.
	 */
	public ImmutableList<PickingJobSchedule> listContributorsOfGroup(
			@NonNull final ProductId productId,
			@NonNull final UomId uomId,
			@NonNull final Set<WorkplaceId> workplaceIds)
	{
		// Empty workplaceIds already renders "1=0" via addInArrayFilter — do not swap to addInArrayOrAllFilter (renders "1=1"),
		// which would drop the workplace restriction and consolidate every open assignment instance-wide.
		if (workplaceIds.isEmpty())
		{
			return ImmutableList.of();
		}

		// Deactivated schedules carry no demand, so their assignment must not keep a share of the group's line.
		final IQuery<I_M_ShipmentSchedule> shipmentSchedulesOfProduct = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID, productId)
				.create();

		return queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_Processed, false)
				.addInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID, workplaceIds)
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_C_UOM_ID, uomId)
				.addInSubQueryFilter(
						I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID,
						I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID,
						shipmentSchedulesOfProduct)
				.create()
				.stream()
				.map(PickingJobScheduleRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Anti-join against the caller-supplied {@code servedAssignmentsQuery} (its {@code M_Picking_Job_Schedule_ID} column) — passed in because
	 * the contributor association and {@code DD_Order} live in modules that depend on this one.
	 */
	public Stream<PickingJobSchedule> streamAssignmentsNeedingDDOrder(@NonNull final IQuery<?> servedAssignmentsQuery)
	{
		return streamAssignments(queryAssignmentsNeedingDDOrder(servedAssignmentsQuery, null));
	}

	/**
	 * The bounded flavour: {@code onlyAssignmentIds} is pushed into the query, so the DB — not the caller — does the restricting.
	 */
	public Stream<PickingJobSchedule> streamAssignmentsNeedingDDOrder(
			@NonNull final IQuery<?> servedAssignmentsQuery,
			@NonNull final Set<PickingJobScheduleId> onlyAssignmentIds)
	{
		if (onlyAssignmentIds.isEmpty())
		{
			return Stream.empty();
		}

		return streamAssignments(queryAssignmentsNeedingDDOrder(servedAssignmentsQuery, onlyAssignmentIds));
	}

	private IQuery<I_M_Picking_Job_Schedule> queryAssignmentsNeedingDDOrder(
			@NonNull final IQuery<?> servedAssignmentsQuery,
			@Nullable final Set<PickingJobScheduleId> onlyAssignmentIds)
	{
		final IQueryBuilder<I_M_Picking_Job_Schedule> queryBuilder = queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_Processed, false)
				.addNotInSubQueryFilter(
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						// the sub-query's own FK back to M_Picking_Job_Schedule — same column name by convention
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						servedAssignmentsQuery);

		if (onlyAssignmentIds != null)
		{
			queryBuilder.addInArrayFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID, onlyAssignmentIds);
		}

		return queryBuilder.create();
	}

	private static Stream<PickingJobSchedule> streamAssignments(@NonNull final IQuery<I_M_Picking_Job_Schedule> query)
	{
		Loggables.addLog("AssignmentsNeedingDDOrder - query: {}", query);

		return query.iterateAndStream().map(PickingJobScheduleRepository::fromRecord);
	}
}
