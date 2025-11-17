package de.metas.handlingunits.picking.job.service.commands.pick_all;

import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
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
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobHUService huService;

	// params
	@NonNull PickingJobId pickingJobId;
	@NonNull UserId callerId;

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::executeInTrx);
	}

	private void executeInTrx()
	{
		// _pickingJob.assertNotProcessed();

		// updateById(pickingJobId, pickingJob -> {
		// 	pickingJob.assertCanUpdate(callerId);
		// });
	}
}
