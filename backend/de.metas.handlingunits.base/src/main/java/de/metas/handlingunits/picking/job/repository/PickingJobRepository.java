package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.dao.ValueRestriction;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Step;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.model.PickingJobReferenceQuery;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.order.OrderId;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;
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
		return new PickingJobCreateRepoHelper(loadingSupportServices).createPickingJob(request);
	}

	public PickingJobStepId newPickingJobStepId()
	{
		final int repoId = DB.getNextID(ClientId.METASFRESH.getRepoId(), I_M_Picking_Job_Step.Table_Name, ITrx.TRXNAME_None);
		return PickingJobStepId.ofRepoId(repoId);
	}

	public void save(final PickingJob pickingJob)
	{
		PickingJobLoaderAndSaver.forSaving().save(pickingJob);
	}

	public List<PickingJob> getDraftJobsByPickerId(@NonNull final ValueRestriction<UserId> pickerId, @NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		final Set<PickingJobId> pickingJobIds = queryBuilderDraftJobsByPickerId(pickerId)
				.create()
				.listIds(PickingJobId::ofRepoId);

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

	@NonNull
	public Stream<PickingJobReference> streamDraftPickingJobReferences(
			@NonNull final PickingJobReferenceQuery query,
			@NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
	{
		final IQueryBuilder<I_M_Picking_Job> queryBuilder = queryBuilderDraftJobsByPickerId(ValueRestriction.equalsTo(query.getPickerId()));
		final Set<BPartnerId> onlyCustomerIds = query.getOnlyBPartnerIds(); 
		if (!onlyCustomerIds.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_M_Picking_Job.COLUMNNAME_C_BPartner_ID, onlyCustomerIds);
		}

		final WarehouseId warehouseId = query.getWarehouseId();
		if (warehouseId != null)
		{
			final IQuery<I_C_Order> warehouseQuery = queryBL.createQueryBuilder(I_C_Order.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Order.COLUMNNAME_M_Warehouse_ID, warehouseId)
					.create();

			queryBuilder.addInSubQueryFilter(I_M_Picking_Job.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, warehouseQuery);
		}

		final Set<PickingJobId> pickingJobIds = queryBuilder
				.create()
				.listIds(PickingJobId::ofRepoId);

		if (pickingJobIds.isEmpty())
		{
			return Stream.empty();
		}

		return PickingJobLoaderAndSaver.forLoading(loadingSupportServices)
				.loadByIds(pickingJobIds)
				.stream()
				.map(PickingJobRepository::toPickingJobReference);
	}

	private static PickingJobReference toPickingJobReference(final PickingJob pickingJob)
	{
		return PickingJobReference.builder()
				.pickingJobId(pickingJob.getId())
				.salesOrderDocumentNo(pickingJob.getSalesOrderDocumentNo())
				.customerId(pickingJob.getCustomerId())
				.customerName(pickingJob.getCustomerName())
				.deliveryDate(pickingJob.getDeliveryDate())
				.shipmentScheduleIds(pickingJob.getShipmentScheduleIds())
				.deliveryLocationId(pickingJob.getDeliveryBPLocationId())
				.handoverLocationId(pickingJob.getHandoverLocationId())
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
}
