package de.metas.handlingunits.picking.job.service.commands.pick_all;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;

@Builder
public class PickingJobPickAllCommand
{
	// services
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobService pickingJobService;

	// params
	@NonNull PickingJobId pickingJobId;
	@NonNull UserId callerId;

	public PickingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		PickingJob pickingJob = pickingJobService.getById(pickingJobId);
		pickingJob.assertCanBeEditedBy(callerId);

		PickingJob pickingJobChanged = pickingJob;

		for (PickingJobLine line : pickingJob.getLines())
		{
			pickingJobChanged = pickingJobService.newPickCommand()
					.pickingJob(pickingJobChanged)
					.pickingJobLineId(line.getId())
					.qtyToPickBD(line.getQtyRemainingToPick().toBigDecimal())
					//
					.build().execute();
		}

		return pickingJobService.complete(pickingJobChanged);
	}
}
