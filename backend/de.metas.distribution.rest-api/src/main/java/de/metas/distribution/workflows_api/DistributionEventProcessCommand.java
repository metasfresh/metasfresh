package de.metas.distribution.workflows_api;

import de.metas.distribution.ddorder.movement.schedule.DDOrderDropToRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleCreateRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderPickFromRequest;
import de.metas.distribution.rest_api.JsonDistributionEvent;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

@Builder
class DistributionEventProcessCommand
{
	// Services
	@NonNull private final ITrxManager trxManager;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;

	// Params
	@NonNull private final DistributionJob job;
	@NonNull private final JsonDistributionEvent event;

	// State
	private DistributionJob changedJob;
	private HuId _huIdToPick; // lazy

	public DistributionJob execute()
	{
		changedJob = job;

		return trxManager.callInThreadInheritedTrx(() -> {

			if (event.getPickFrom() != null)
			{
				changedJob = processEvent_PickFrom();
			}
			if (event.getDropTo() != null)
			{
				changedJob = processEvent_DropTo();
			}

			return changedJob;
		});
	}

	private DistributionJob processEvent_PickFrom()
	{
		final DistributionJobStepId stepId = getOrCreateStep();

		final DDOrderMoveSchedule schedule = ddOrderMoveScheduleService.pickFromHU(DDOrderPickFromRequest.builder()
				.scheduleId(stepId.toScheduleId())
				.huId(getHuIdToPick())
				.build());

		final DistributionJobStep changedStep = DistributionJobLoader.toDistributionJobStep(schedule, loadingSupportServices);
		return changedJob.withChangedStep(stepId, ignored -> changedStep);
	}

	private DistributionJob processEvent_DropTo()
	{
		final DistributionJobStepId stepId = Check.assumeNotNull(event.getDistributionStepId(), "stepId must be set");

		final DDOrderMoveSchedule schedule = ddOrderMoveScheduleService.dropTo(DDOrderDropToRequest.builder()
				.scheduleId(stepId.toScheduleId())
				.build());

		final DistributionJobStep changedStep = DistributionJobLoader.toDistributionJobStep(schedule, loadingSupportServices);
		return job.withChangedStep(stepId, ignored -> changedStep);
	}

	private DistributionJobStepId getOrCreateStep()
	{
		DistributionJobStepId stepId = event.getDistributionStepId();
		if (stepId == null)
		{
			final DistributionJobLineId lineId = Check.assumeNotNull(event.getLineId(), "lineId must be set");
			final HuId huId = getHuIdToPick();
			final DistributionJobStep step = addNewStep(lineId, huId);
			return step.getId();
		}
		else
		{
			return stepId;
		}
	}

	private DistributionJobStep addNewStep(final DistributionJobLineId lineId, final HuId huId)
	{
		final DistributionJobLine line = changedJob.getLineById(lineId);
		final DDOrderMoveSchedule newSchedule = createNewSchedule(line, huId);
		final DistributionJobStep step = DistributionJobLoader.toDistributionJobStep(newSchedule, loadingSupportServices);
		changedJob = changedJob.withNewStep(line.getId(), step);
		return step;
	}

	private DDOrderMoveSchedule createNewSchedule(final DistributionJobLine line, final HuId huId)
	{
		final IHUProductStorage huStorage = handlingUnitsBL.getSingleHUProductStorage(huId);

		if (!ProductId.equals(huStorage.getProductId(), line.getProduct().getProductId()))
		{
			throw new AdempiereException("Product not matching")
					.setParameter("line", line)
					.setParameter("huStorage", huStorage);
		}

		return ddOrderMoveScheduleService.createScheduleToMove(
				DDOrderMoveScheduleCreateRequest.builder()
						.ddOrderId(changedJob.getDdOrderId())
						.ddOrderLineId(line.getId().toDDOrderLineId())
						.productId(line.getProduct().getProductId())
						.pickFromLocatorId(line.getPickFromLocator().getLocatorId())
						.pickFromHUId(huStorage.getHuId())
						.qtyToPick(huStorage.getQty())
						.isPickWholeHU(true)
						.dropToLocatorId(line.getDropToLocator().getLocatorId())
						.build()
		);
	}

	private HuId getHuIdToPick()
	{
		if (_huIdToPick == null)
		{
			final JsonDistributionEvent.PickFrom pickFrom = Check.assumeNotNull(event.getPickFrom(), "pickFrom must be set");
			final HUQRCode huQRCode = HUQRCode.fromGlobalQRCodeJsonString(Check.assumeNotNull(pickFrom.getQrCode(), "pickFrom.qrCode must be set"));
			_huIdToPick = huQRCodesService.getHuIdByQRCode(huQRCode);
		}
		return _huIdToPick;
	}
}
