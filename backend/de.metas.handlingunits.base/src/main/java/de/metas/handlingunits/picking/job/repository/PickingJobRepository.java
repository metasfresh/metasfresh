package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
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

	public List<PickingJob> getDraftJobsByPickerId(@NonNull final UserId pickerId, @NonNull final PickingJobLoaderSupportingServices loadingSupportServices)
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

	private IQuery<I_M_Picking_Job> queryDraftJobsByPickerId(final @NonNull UserId pickerId)
	{
		return queryBL
				.createQueryBuilder(I_M_Picking_Job.class)
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_DocStatus, PickingJobDocStatus.Drafted.getCode())
				.addEqualsFilter(I_M_Picking_Job.COLUMNNAME_Picking_User_ID, pickerId)
				.create();
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
		return getDraftJobsByPickerId(pickerId, loadingSupportServices)
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

}
