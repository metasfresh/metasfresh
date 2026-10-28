package de.metas.handlingunits.picking.job.repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.dao.ValueRestriction;
import de.metas.document.DocumentNoFilter;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderId;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.InSubQueryFilter;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Owns the picking-job aggregate persistence (M_Picking_Job header + its lines / steps / picked-HUs /
 * HU-alternatives). Loading and saving of the aggregate is delegated to {@link PickingJobLoaderAndSaver}
 * (and {@link PickingJobSaver} / {@link PickingJobCreateRepoHelper}); this class also exposes read-only
 * existence / lookup queries over the same tables.
 *
 * Repository Tables: M_Picking_Job, M_Picking_Job_Line, M_Picking_Job_Step, M_Picking_Job_Step_HUAlternative, M_Picking_Job_Step_PickedHU, M_Picking_Job_HUAlternative
 * Repository Cluster: PickingJobRepository, PickingJobLoaderAndSaver, PickingJobSaver, PickingJobCreateRepoHelper, PickingJobLineCarrierServiceRepository
 */
@Repository
public class PickingJobRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static PickingJobRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		// Register PickingJobLineCarrierServiceRepository in the JUnit bean registry (a side effect of its
		// getBeanOrSupply in unit-test mode). The returned instance is intentionally discarded: PickingJobSaver
		// resolves it lazily via SpringContextHolder.getBean(...) at save time, so it only needs to be REGISTERED
		// before a save runs, not held here.
		PickingJobLineCarrierServiceRepository.newInstanceForUnitTesting();
		return new PickingJobRepository();
	}

	/**
	 * The subset of the given shipment schedules a picker is working on right now: those with an active
	 * {@link I_M_Picking_Job_Line} on a picking job that is neither Voided nor Completed.
	 */
	public ImmutableSet<ShipmentScheduleId> retrieveScheduleIdsWithActivePickingJobLine(@NonNull final Collection<ShipmentScheduleId> scheduleIds)
	{
		if (scheduleIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final IQuery<I_M_Picking_Job> inProgressJobsQuery = queryBL
				.createQueryBuilder(I_M_Picking_Job.class)
				.addOnlyActiveRecordsFilter()
				.addNotInArrayFilter(
						I_M_Picking_Job.COLUMNNAME_DocStatus,
						ImmutableList.of(PickingJobDocStatus.Voided.getCode(), PickingJobDocStatus.Completed.getCode()))
				.create();

		return queryBL
				.createQueryBuilder(I_M_Picking_Job_Line.class)
				.addInArrayFilter(I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID, scheduleIds)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_Picking_Job_Line.COLUMNNAME_M_Picking_Job_ID, I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, inProgressJobsQuery)
				.create()
				.listDistinctAsImmutableSet(I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID, ShipmentScheduleId.class);
	}

	/**
	 * Returns the IDs of all <b>Drafted</b> picking jobs that have a line for one of the given products AND
	 * one of the given shipment schedules. Used by mass printing to locate pre-existing draft jobs covering the
	 * demand it is about to pick, so they can be aborted (when abortable) before a new job is created.
	 * <p>
	 * The query is scoped to {@code Drafted} via an <i>inclusion</i> filter (rather than excluding
	 * Voided/Completed): {@link PickingJobDocStatus} has only Drafted/Completed/Voided, so this is
	 * behaviour-equivalent today but stays correct if a new doc-status is ever added.
	 */
	public ImmutableSet<PickingJobId> getDraftedPickingJobIdsByProductsAndSchedules(
			@NonNull final Set<ProductId> productIds,
			@NonNull final Set<ShipmentScheduleId> scheduleIds)
	{
		if (productIds.isEmpty() || scheduleIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final IQuery<I_M_Picking_Job> draftedJobsQuery = queryBL
				.createQueryBuilder(I_M_Picking_Job.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.create();

		return queryBL
				.createQueryBuilder(I_M_Picking_Job_Line.class)
				.addInArrayFilter(I_M_Picking_Job_Line.COLUMNNAME_M_Product_ID, productIds)
				.addInArrayFilter(I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID, scheduleIds)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_Picking_Job_Line.COLUMNNAME_M_Picking_Job_ID, I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, draftedJobsQuery)
				.create()
				.stream()
				.map(line -> PickingJobId.ofRepoId(line.getM_Picking_Job_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	public PickingJob createNewAndGet(
			@NonNull final PickingJobCreateRepoRequest request,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return new PickingJobCreateRepoHelper(loadingSupportServices).createPickingJob(request);
	}

	public PickingJobStepId newPickingJobStepId()
	{
		final int repoId = DB.getNextID(ClientId.METASFRESH.getRepoId(), I_M_Picking_Job_Step.Table_Name);
		return PickingJobStepId.ofRepoId(repoId);
	}

	public void save(@NonNull final PickingJob pickingJob)
	{
		PickingJobLoaderAndSaver.forSaving().save(pickingJob);
	}

	public PickingJob updateById(
			@NonNull PickingJobId pickingJobId,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices,
			@NonNull UnaryOperator<PickingJob> updater)
	{
		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.updateById(pickingJobId, updater);
	}

	public List<PickingJob> getDraftJobsByPickerId(@NonNull final ValueRestriction<UserId> pickerId, @NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		final Set<PickingJobId> pickingJobIds = queryBuilderDraftJobsByPickerId(pickerId)
				.create()
				.idsAsSet(PickingJobId::ofRepoId);

		if (pickingJobIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadByIds(pickingJobIds);
	}

	@NonNull
	private IQueryBuilder<I_M_Picking_Job> queryBuilderDraftJobsByPickerId(@NonNull final ValueRestriction<UserId> pickerId)
	{
		final IQueryBuilder<I_M_Picking_Job> queryBuilder = queryBL
				.createQueryBuilder(I_M_Picking_Job.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode());

		pickerId.appendFilter(queryBuilder, I_M_Picking_Job.COLUMNNAME_Picking_User_ID);

		return queryBuilder;
	}

	public PickingJob getById(
			@NonNull final PickingJobId pickingJobId,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadById(pickingJobId);
	}

	/** Batch-loads the given picking jobs in a single pass. Used by mass-printing's pre-existing-job reconciliation ({@code PickingJobService.abortAbortablePickingJobsForSchedules}) to avoid a per-job load. */
	public List<PickingJob> getByIds(
			@NonNull final Set<PickingJobId> pickingJobIds,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadByIds(pickingJobIds);
	}

	@NonNull
	public Stream<PickingJobReference> streamDraftPickingJobReferences(
			@NonNull final PickingJobReferenceQuery query,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		final IQueryBuilder<I_M_Picking_Job> queryBuilder = queryBuilderDraftJobsByPickerId(ValueRestriction.equalsToOrNull(query.getPickerId()));
		final Set<BPartnerId> onlyCustomerIds = query.getOnlyCustomerIds();
		if (!onlyCustomerIds.isEmpty())
		{
			final IQuery<I_M_Picking_Job_Line> linesQuery = queryBL.createQueryBuilder(I_M_Picking_Job_Line.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_M_Picking_Job_Line.COLUMNNAME_C_BPartner_ID, onlyCustomerIds)
					.create();

			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addInArrayFilter(I_M_Picking_Job.COLUMNNAME_C_BPartner_ID, onlyCustomerIds)
					.addInSubQueryFilter(I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, I_M_Picking_Job_Line.COLUMNNAME_M_Picking_Job_ID, linesQuery);
		}

		final WarehouseId warehouseId = query.getWarehouseId();
		final DocumentNoFilter salesOrderDocumentNo = query.getSalesOrderDocumentNo();
		if (warehouseId != null || salesOrderDocumentNo != null)
		{
			//
			// filter on C_Order
			final IQueryBuilder<I_C_Order> salesOrderQueryBuilder = queryBL.createQueryBuilder(I_C_Order.class).addOnlyActiveRecordsFilter();
			if (warehouseId != null)
			{
				salesOrderQueryBuilder.addEqualsFilter(I_C_Order.COLUMNNAME_M_Warehouse_ID, warehouseId);
			}
			if (salesOrderDocumentNo != null)
			{
				salesOrderQueryBuilder.filter(salesOrderDocumentNo.toSqlFilter(I_C_Order.COLUMN_DocumentNo));
			}
			final IQuery<I_C_Order> salesOrderQuery = salesOrderQueryBuilder.create();

			//
			// filter on M_Picking_Job_Line
			final IQueryBuilder<I_M_Picking_Job_Line> linesQueryBuilder = queryBL.createQueryBuilder(I_M_Picking_Job_Line.class)
					.addOnlyActiveRecordsFilter()
					.addInSubQueryFilter(I_M_Picking_Job_Line.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, salesOrderQuery);
			final IQuery<I_M_Picking_Job_Line> linesQuery = linesQueryBuilder.create();

			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addInSubQueryFilter(I_M_Picking_Job.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, salesOrderQuery)
					.addInSubQueryFilter(I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, I_M_Picking_Job_Line.COLUMNNAME_M_Picking_Job_ID, linesQuery);
		}

		final Set<PickingJobId> pickingJobIds = queryBuilder
				.create()
				.idsAsSet(PickingJobId::ofRepoId);

		if (pickingJobIds.isEmpty())
		{
			return Stream.empty();
		}

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.streamPickingJobReferences(pickingJobIds);
	}

	public boolean hasDraftJobsUsingPickingSlot(
			@NonNull final PickingSlotId pickingSlotId,
			@Nullable final PickingJobId excludePickingJobId)
	{
		final IQueryBuilder<I_M_Picking_Job> queryBuilder = queryBL
				.createQueryBuilder(I_M_Picking_Job.class)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_M_PickingSlot_ID, pickingSlotId);

		if (excludePickingJobId != null)
		{
			queryBuilder.addNotEqualsFilter(I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, excludePickingJobId);
		}

		return queryBuilder.create().anyMatch();
	}

	public Optional<PickingJob> getDraftBySalesOrderId(
			@NonNull final OrderId salesOrderId,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return queryBL.createQueryBuilder(I_M_Picking_Job.class)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_C_Order_ID, salesOrderId)
				.create()
				.firstIdOnlyOptional(PickingJobId::ofRepoIdOrNull)
				.map(pickingJobId -> PickingJobLoaderAndSaver.forLoading(loadingSupportServices).loadById(pickingJobId));
	}

	@NonNull
	public Map<ShipmentScheduleId, List<PickingJobId>> getPickingJobIdsByScheduleId(
			@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return queryBL.createQueryBuilder(I_M_Picking_Job_Step.class)
				.addInArrayFilter(I_M_Picking_Job_Step.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						step -> ShipmentScheduleId.ofRepoId(step.getM_ShipmentSchedule_ID()),
						Collectors.mapping(step -> PickingJobId.ofRepoId(step.getM_Picking_Job_ID()),
								Collectors.toList())));
	}

	/**
	 * A filter matching every {@link I_M_ShipmentSchedule} that is referenced -- via {@link I_M_Picking_Job_Line} OR
	 * {@link I_M_Picking_Job_Step} -- by a {@code Drafted} {@link I_M_Picking_Job}, i.e. still has an unfinished
	 * picking job. Completed/Voided jobs (and schedules with no picking job at all) do not match.
	 * <p>
	 * Expressed as two {@code IN (subquery)} predicates OR'd together (Line- and Step-referenced) so the caller can
	 * fold it into its own {@code M_ShipmentSchedule} selection query -- the offending schedules then come from a
	 * single query, with no id round-trip. Unlike an {@code IN (id, id, ...)} list a subquery join has no JDBC
	 * bind-parameter limit, so the caller's selection size is unbounded.
	 */
	@NonNull
	public IQueryFilter<I_M_ShipmentSchedule> newUnfinishedPickingScheduleFilter()
	{
		final IQuery<I_M_Picking_Job> draftedJobsQuery = queryBL
				.createQueryBuilder(I_M_Picking_Job.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.create();

		final IQuery<I_M_Picking_Job_Line> draftedJobLinesQuery = queryBL
				.createQueryBuilder(I_M_Picking_Job_Line.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_Picking_Job_Line.COLUMNNAME_M_Picking_Job_ID, I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, draftedJobsQuery)
				.create();

		final IQuery<I_M_Picking_Job_Step> draftedJobStepsQuery = queryBL
				.createQueryBuilder(I_M_Picking_Job_Step.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_Picking_Job_Step.COLUMNNAME_M_Picking_Job_ID, I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, draftedJobsQuery)
				.create();

		return queryBL.createCompositeQueryFilter(I_M_ShipmentSchedule.class)
				.setJoinOr()
				.addFilter(InSubQueryFilter.<I_M_ShipmentSchedule>builder()
						.tableName(I_M_ShipmentSchedule.Table_Name)
						.matchingColumnNames(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID)
						.subQuery(draftedJobLinesQuery)
						.build())
				.addFilter(InSubQueryFilter.<I_M_ShipmentSchedule>builder()
						.tableName(I_M_ShipmentSchedule.Table_Name)
						.matchingColumnNames(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID, I_M_Picking_Job_Step.COLUMNNAME_M_ShipmentSchedule_ID)
						.subQuery(draftedJobStepsQuery)
						.build());
	}

	@NonNull
	public List<PickingJob> getDraftedByPickingSlotId(
			@NonNull final PickingSlotId slotId,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		final ImmutableSet<PickingJobId> pickingJobIds = queryBL.createQueryBuilder(I_M_Picking_Job.class)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_M_PickingSlot_ID, slotId)
				.create()
				.idsAsSet(PickingJobId::ofRepoId);

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadByIds(pickingJobIds);
	}
}
