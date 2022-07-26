package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;

import java.util.Collection;

public class PickingJobReleaseAllCommand
{
	@NonNull
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull
	private final PickingJobLockService pickingJobLockService;
	@NonNull
	private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull
	private final Collection<PickingJob> draftPickingJobs;

	@Builder
	private PickingJobReleaseAllCommand(
			final @NonNull PickingJobLockService pickingJobLockService,
			final @NonNull PickingJobHUReservationService pickingJobHUReservationService,
			//
			final @NonNull Collection<PickingJob> pickingJobs)
	{
		this.pickingJobLockService = pickingJobLockService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;

		this.draftPickingJobs = pickingJobs;
	}

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::executeInTrx);
	}

	private void executeInTrx()
	{
		for (final PickingJob job : draftPickingJobs)
		{
			pickingJobHUReservationService.releaseAllReservations(job);
			pickingJobLockService.unlockShipmentSchedules(job);
		}
	}

}
