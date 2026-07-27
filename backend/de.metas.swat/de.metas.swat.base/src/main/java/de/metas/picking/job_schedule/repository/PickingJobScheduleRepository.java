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
	 * The contributor set of one picking-replenishment product group: every active, not-yet-processed assignment on one
	 * of {@code workplaceIds} whose shipment schedule demands {@code productId}, in {@code uomId}.
	 * <p>
	 * {@code workplaceIds} are the workplaces whose effective pick-from locator IS the group's target locator, so the
	 * three parameters together express the group key {@code (product, target locator, UOM)}.
	 * <p>
	 * <b>Unordered</b> on purpose: the attribution order is keyed on the contributor's effective {@code PriorityRule}
	 * and preparation date, which live on {@code M_ShipmentSchedule}, so only the caller can establish it.
	 * <p>
	 * The {@code C_Workplace_ID} filter is what makes this lookup <b>index-served</b> — by the partial index
	 * {@code m_picking_job_schedule (c_workplace_id) WHERE Processed = 'N'} — rather than a scan of the whole
	 * open-assignment set. Any future variant of this query must keep both the workplace filter and the
	 * {@code Processed = 'N'} predicate, or it falls back to that scan.
	 */
	public ImmutableList<PickingJobSchedule> listContributorsOfGroup(
			@NonNull final ProductId productId,
			@NonNull final UomId uomId,
			@NonNull final Set<WorkplaceId> workplaceIds)
	{
		// No workplace points at that target locator, so the group has no contributor: skip the round-trip. (The
		// restriction itself is safe when empty - every addInArrayFilter overload sets defaultReturnWhenEmpty=false,
		// so an empty IN-list renders as "1=0". It is addInArrayOrAllFilter that renders "1=1"; swapping to it here
		// WOULD drop the workplace restriction and consolidate every open assignment of the product/UOM instance-wide
		// into one DD_Order, which is what this early return also documents against.)
		if (workplaceIds.isEmpty())
		{
			return ImmutableList.of();
		}

		// IsActive filtered as the shipment schedule's own repository does: a deactivated schedule carries no demand, so
		// its assignment must not keep a share of the group's line.
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
	 * Cross-entity anti-join: streams the active, not-yet-processed assignments that no live DD_Order serves yet
	 * ({@code M_Picking_Job_Schedule} LEFT-anti-JOIN {@code servedAssignmentsQuery} on
	 * {@code M_Picking_Job_Schedule_ID}).
	 * <p>
	 * Served-ness is decided by the caller-supplied set, which resolves it through the <b>contributor
	 * association</b> — not through a single-owner back-reference column on the {@code DD_Order}. A consolidated
	 * order serves several assignments, so such a column can name only one of them; every other contributor would
	 * be reported unserved here and the whole group re-planned on every watchdog pass.
	 * <p>
	 * {@code servedAssignmentsQuery} is passed in rather than built here for the same reason the DD_Order query
	 * always was: both the association and {@code DD_Order} live in modules that depend on this one, so only the
	 * caller (the DD_Order reconcile flow) can compose them. Its {@code M_Picking_Job_Schedule_ID} column names the
	 * served assignment.
	 */
	public Stream<PickingJobSchedule> streamAssignmentsNeedingDDOrder(@NonNull final IQuery<?> servedAssignmentsQuery)
	{
		final IQuery<I_M_Picking_Job_Schedule> query = queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_Processed, false)
				.addNotInSubQueryFilter(
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						// the sub-query's own FK back to M_Picking_Job_Schedule — same column name by convention
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						servedAssignmentsQuery)
				.create();
		Loggables.addLog("AssignmentsNeedingDDOrder - query: {}", query);

		return query.iterateAndStream().map(PickingJobScheduleRepository::fromRecord);
	}
}
