package de.metas.distribution.mobileui.job.service.commands.unpick;

import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobStepId;
import de.metas.distribution.mobileui.external_services.hu.DistributionHUService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.StringUtils;
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
	@NonNull private final HUQRCodesService huQRCodesService;

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
			@NonNull final HUQRCodesService huQRCodesService,
			//
			@NonNull final DistributionJob job,
			@NonNull final DistributionJobStepId stepId,
			@Nullable final String unpickToTargetQRCode)
	{
		this.trxManager = trxManager;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.huService = huService;
		this.huQRCodesService = huQRCodesService;
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
			// Any supported HU label may identify the unpick target - a metasfresh global QR code, or the
			// plain M_HU.Value / ExternalBarcode printed on the unit. Resolving through the shared bridge
			// keeps this in step with the picking module's unpack target, which accepts the same two
			// kinds (every other scanned-code type identifies goods or an intent rather than one specific
			// unit, and is rejected). A skipped target scan stays legal (unpick to the floor), so the
			// blank-tolerant Optional chain is preserved.
			ddOrderMoveScheduleService.unpick(
					stepId.toScheduleId(),
					StringUtils.trimBlankToOptional(unpickToTargetQRCode)
							.map(ScannedCode::ofString)
							.map(huQRCodesService::getQRCodeByScannedCode)
							.orElse(null));
			return changedJob.removeStep(stepId);
		}
	}
}
