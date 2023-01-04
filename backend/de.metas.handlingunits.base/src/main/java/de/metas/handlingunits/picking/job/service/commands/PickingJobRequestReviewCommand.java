package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

public class PickingJobRequestReviewCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJob initialPickingJob;

	@Builder
	public PickingJobRequestReviewCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJob pickingJob)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.initialPickingJob = pickingJob;

	}

	public PickingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		initialPickingJob.assertNotProcessed();
		if (!initialPickingJob.getProgress().isDone())
		{
			throw new AdempiereException("Picking shall be DONE on all steps in order to request review");
		}

		final PickingJob pickingJob = initialPickingJob.withIsReadyToReview();
		pickingJobRepository.save(pickingJob);

		return pickingJob;
	}

}
