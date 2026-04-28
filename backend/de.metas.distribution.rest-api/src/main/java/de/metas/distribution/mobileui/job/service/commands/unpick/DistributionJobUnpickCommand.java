package de.metas.distribution.mobileui.job.service.commands.unpick;

import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobStepId;
import de.metas.distribution.mobileui.external_services.hu.DistributionHUService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.IAutoCloseable;

import javax.annotation.Nullable;

public class DistributionJobUnpickCommand
{
	// Services
	@NonNull private final ITrxManager trxManager;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionHUService huService;

	// Params
	@NonNull private final DistributionJob job;
	@NonNull private final DistributionJobStepId stepId;
	@Nullable private final String unpickToTargetQRCode;

	// State
	private DistributionJob changedJob;

	@Builder
	public DistributionJobUnpickCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			@NonNull final DistributionHUService huService,
			//
			@NonNull final DistributionJob job,
			@NonNull final DistributionJobStepId stepId,
			@Nullable final String unpickToTargetQRCode)
	{
		this.trxManager = trxManager;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.huService = huService;
		this.job = job;
		this.stepId = stepId;
		this.unpickToTargetQRCode = unpickToTargetQRCode;

		//state
		this.changedJob = null;
	}

	public DistributionJob execute()
	{
		changedJob = job;

		return trxManager.callInThreadInheritedTrx(() -> {
			changedJob = executeInTrx();
			return changedJob;
		});
	}

	private DistributionJob executeInTrx()
	{
		try (final IAutoCloseable ignored = huService.newContext())
		{
			ddOrderMoveScheduleService.unpick(stepId.toScheduleId(), HUQRCode.fromNullableGlobalQRCodeJsonString(unpickToTargetQRCode));
			return changedJob.removeStep(stepId);
		}
	}
}
