package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.dao.ValueRestriction;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.order.OrderId;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public class PickingJobRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PickingJob createNewAndGet(
			@NonNull final PickingJobCreateRepoRequest request,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return new PickingJobCreateRepoCommand(loadingSupportServices, request)
				.execute();
	}

	public void save(final PickingJob pickingJob)
	{
		PickingJobLoaderAndSaver.forSaving().save(pickingJob);
	}

	public List<PickingJob> getDraftJobsByPickerId(@NonNull final ValueRestriction<UserId> pickerId, @NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		final Set<PickingJobId> pickingJobIds = queryDraftJobsByPickerId(pickerId)
				.listIds(PickingJobId::ofRepoId);

		if (pickingJobIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadByIds(pickingJobIds);
	}

	private IQuery<I_M_Picking_Job> queryDraftJobsByPickerId(final @NonNull ValueRestriction<UserId> pickerId)
	{
		final IQueryBuilder<I_M_Picking_Job> queryBuilder = queryBL
				.createQueryBuilder(I_M_Picking_Job.class)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode());

		pickerId.appendFilter(queryBuilder, I_M_Picking_Job.COLUMNNAME_Picking_User_ID);

		return queryBuilder.create();
	}

	public PickingJob getById(
			@NonNull final PickingJobId pickingJobId,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadById(pickingJobId);
	}

	public Stream<PickingJobReference> streamDraftPickingJobReferences(
			@NonNull final UserId pickerId,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		return getDraftJobsByPickerId(ValueRestriction.equalsToOrNull(pickerId), loadingSupportServices)
				.stream()
				.map(PickingJobRepository::toPickingJobReference);
	}

	private static PickingJobReference toPickingJobReference(final PickingJob pickingJob)
	{
		return PickingJobReference.builder()
				.pickingJobId(pickingJob.getId())
				.salesOrderDocumentNo(pickingJob.getSalesOrderDocumentNo())
				.customerName(pickingJob.getCustomerName())
				.shipmentScheduleIds(pickingJob.getShipmentScheduleIds())
				.build();
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

	public boolean hasReadyToReview(@NonNull final ImmutableSet<PickingJobId> pickingJobIds)
	{
		if (pickingJobIds.isEmpty())
		{
			return false;
		}

		return queryReadyToReview(pickingJobIds).anyMatch();
	}

	public List<PickingJob> getByIsReadyToReview(
			@NonNull final ImmutableSet<PickingJobId> pickingJobIds,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		if (pickingJobIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<PickingJobId> pickingJobIdsEffective = queryReadyToReview(pickingJobIds).listIds(PickingJobId::ofRepoId);

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices).loadByIds(pickingJobIdsEffective);
	}

	private IQuery<I_M_Picking_Job> queryReadyToReview(final ImmutableSet<PickingJobId> pickingJobIds)
	{
		Check.assumeNotEmpty(pickingJobIds, "pickingJobIds is not empty");

		return queryBL.createQueryBuilder(I_M_Picking_Job.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_IsReadyToReview, true)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_IsApproved, false)
				.addInArrayFilter(I_M_Picking_Job.COLUMNNAME_M_Picking_Job_ID, pickingJobIds)
				.create();
	}
}
