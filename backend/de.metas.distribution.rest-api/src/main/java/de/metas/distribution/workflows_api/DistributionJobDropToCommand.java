package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.distribution.ddorder.movement.schedule.DDOrderDropToRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleId;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;

import javax.annotation.Nullable;
import java.util.List;

public class DistributionJobDropToCommand
{
	// Services
	@NonNull private final ITrxManager trxManager;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;

	// Params
	@NonNull private final DistributionJob jobInitial;
	@Nullable private final DistributionJobStepId stepId;
	@Nullable private final ScannedCode dropToQRCode;

	@Builder
	public DistributionJobDropToCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices,
			@NonNull final LocatorScannedCodeResolverService locatorScannedCodeResolver,
			//
			@NonNull final DistributionJob job,
			@Nullable final DistributionJobStepId stepId,
			@Nullable final ScannedCode dropToQRCode)
	{
		this.trxManager = trxManager;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.loadingSupportServices = loadingSupportServices;
		this.locatorScannedCodeResolver = locatorScannedCodeResolver;
		this.jobInitial = job;
		this.stepId = stepId;
		this.dropToQRCode = dropToQRCode;
	}

	public DistributionJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private DistributionJob executeInTrx()
	{
		DistributionJob job = jobInitial;

		final ImmutableSet<DDOrderMoveScheduleId> scheduleIds = stepId != null
				? ImmutableSet.of(job.getStepById(stepId).getScheduleId())
				: job.getInTransitScheduleIds();
		if (scheduleIds.isEmpty())
		{
			throw new AdempiereException("Nothing to move");
		}

		final LocatorId dropToLocatorId = dropToQRCode != null ? resolveLocatorId(dropToQRCode) : null;

		final List<DDOrderMoveSchedule> schedules = ddOrderMoveScheduleService.dropTo(
				DDOrderDropToRequest.builder()
						.scheduleIds(scheduleIds)
						.dropToLocatorId(dropToLocatorId)
						.build()
		);

		final ImmutableMap<DistributionJobStepId, DDOrderMoveSchedule> schedulesByStepId = Maps.uniqueIndex(schedules, schedule -> DistributionJobStepId.ofScheduleId(schedule.getId()));

		job = job.withChangedSteps(step -> {
			final DDOrderMoveSchedule schedule = schedulesByStepId.get(step.getId());
			return schedule != null
					? DistributionJobLoader.toDistributionJobStep(schedule, loadingSupportServices)
					: step;
		});

		return job;
	}

	private LocatorId resolveLocatorId(@NonNull final ScannedCode scannedCode)
	{
		return locatorScannedCodeResolver.resolve(scannedCode).getLocatorId();
	}
}
