package de.metas.distribution.mobileui.job.service.commands.pick_from;

import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleCreateRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.movement.schedule.commands.pick_from.DDOrderPickFromRequest;
import de.metas.distribution.mobileui.external_services.hu.DistributionHUService;
import de.metas.distribution.mobileui.external_services.warehouse.DistributionWarehouseService;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobLine;
import de.metas.distribution.mobileui.job.model.DistributionJobLineId;
import de.metas.distribution.mobileui.job.model.DistributionJobStep;
import de.metas.distribution.mobileui.job.model.DistributionJobStepId;
import de.metas.distribution.mobileui.job.service.DistributionJobLoader;
import de.metas.distribution.mobileui.job.service.DistributionJobLoaderSupportingServices;
import de.metas.distribution.mobileui.rest_api.json.JsonDistributionEvent;
import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

import javax.annotation.Nullable;
import java.util.Optional;

public class DistributionJobPickFromCommand
{
	private static final AdMessageKey NOT_ENOUGH_QTY = AdMessageKey.of("de.metas.distribution.workflows_api.NotEnoughQty");

	// Services
	@NonNull private final ITrxManager trxManager;
	@NonNull private final DistributionWarehouseService warehouseService;
	@NonNull private final DistributionHUService huService;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final PackToHUsProducer packToHUsProducer;

	// Params
	@NonNull private final UserId userId;
	@NonNull private final DistributionJob job;
	@Nullable private final DistributionJobLineId lineId;
	@Nullable private final DistributionJobStepId stepId;
	@Nullable private final JsonDistributionEvent.PickFrom pickFrom;

	// State
	private DistributionJob changedJob;
	private HuId _huIdToPick; // lazy

	@Builder
	public DistributionJobPickFromCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final DistributionWarehouseService warehouseService,
			@NonNull final DistributionHUService huService,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices,
			//
			@NonNull final UserId userId,
			@NonNull final DistributionJob job,
			@Nullable final DistributionJobLineId lineId,
			@Nullable final DistributionJobStepId stepId,
			@Nullable final JsonDistributionEvent.PickFrom pickFrom)
	{
		// services
		this.trxManager = trxManager;
		this.warehouseService = warehouseService;
		this.huService = huService;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.loadingSupportServices = loadingSupportServices;
		this.packToHUsProducer = huService.newPackToHUsProducer();

		// params
		this.userId = userId;
		this.job = job;
		this.lineId = lineId;
		this.stepId = stepId;
		this.pickFrom = pickFrom;

		//state
		this.changedJob = null;
		this._huIdToPick = null;
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
			final DistributionJobStepId stepId = getOrCreateStep();

			final DDOrderMoveSchedule schedule = ddOrderMoveScheduleService.pickFromHU(DDOrderPickFromRequest.builder()
					.scheduleId(stepId.toScheduleId())
					.huId(getHuIdToPick(changedJob.getLineByStepId(stepId)))
					.inTransitLocatorId(getInTransitLocatorId().orElse(null))
					.build());

			final DistributionJobStep changedStep = DistributionJobLoader.toDistributionJobStep(schedule, loadingSupportServices);
			return changedJob.withChangedStep(stepId, changedStep);
		}
	}

	private DistributionJobStepId getOrCreateStep()
	{
		if (stepId != null)
		{
			return stepId;
		}

		final DistributionJobLineId lineId = Check.assumeNotNull(this.lineId, "lineId must be set");
		final HuId huId = getHuIdToPick(job.getLineById(lineId));
		final DistributionJobStep step = addNewStep(lineId, huId);
		return step.getId();

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
		final IHUProductStorage huStorage = huService.getSingleHUProductStorage(huId);

		if (!ProductId.equals(huStorage.getProductId(), line.getProductId()))
		{
			throw new AdempiereException("Product not matching")
					.setParameter("line", line)
					.setParameter("huStorage", huStorage);
		}

		return ddOrderMoveScheduleService.createScheduleToMove(
				DDOrderMoveScheduleCreateRequest.builder()
						.ddOrderId(changedJob.getDdOrderId())
						.ddOrderLineId(line.getDDOrderLineId())
						.productId(line.getProductId())
						.pickFromLocatorId(line.getPickFromLocatorId())
						.pickFromHUId(huStorage.getHuId())
						.qtyToPick(huStorage.getQty())
						.isPickWholeHU(true)
						.dropToLocatorId(line.getDropToLocatorId())
						.build()
		);
	}

	@NonNull
	private HuId getHuIdToPick(@NonNull final DistributionJobLine line)
	{
		if (_huIdToPick == null)
		{
			_huIdToPick = resolveHuIdToPick(line);
		}
		return _huIdToPick;
	}

	@NonNull
	private HuId resolveHuIdToPick(@NonNull final DistributionJobLine line)
	{
		final JsonDistributionEvent.PickFrom pickFrom = Check.assumeNotNull(this.pickFrom, "pickFrom must be set");
		final ScannedCode pickFromScannedCode = ScannedCode.ofString(Check.assumeNotNull(pickFrom.getQrCode(), "pickFrom.qrCode must be set"));
		final HuId sourceHuId = huService.resolveHUId(pickFromScannedCode);

		final Quantity sourceHUQty = huService.getProductQuantity(sourceHuId, line.getProductId());

		final Quantity qtyToPick = pickFrom.getQtyPicked(line.getUOM()).orElse(null);
		if (qtyToPick == null)
		{
			// no qty to pick specified by user => pick the whole HU
			return sourceHuId;
		}
		else if (qtyToPick.compareTo(sourceHUQty) > 0)
		{
			throw new AdempiereException(NOT_ENOUGH_QTY, sourceHUQty);
		}
		else if (qtyToPick.compareTo(sourceHUQty) == 0)
		{
			// scanned HU has exactly the required qty
			return sourceHuId;
		}
		else
		{
			return splitQty(line, sourceHuId, qtyToPick);
		}
	}

	@NonNull
	private HuId splitQty(
			@NonNull final DistributionJobLine line,
			@NonNull final HuId pickFromVHUId,
			@NonNull final Quantity qty)
	{
		final PackToHUsProducer.PackToInfo packToInfo = packToHUsProducer.extractPackToInfo(PackToSpec.VIRTUAL, line.getPickFromLocatorId())
				.withPackForShipping(false);

		final I_M_HU splitHU = packToHUsProducer.packToHU(
				PackToHUsProducer.PackToHURequest.builder()
						.huContext(HUContextHolder.getCurrent())
						.pickFromHUId(pickFromVHUId)
						.packToInfo(packToInfo)
						.productId(line.getProductId())
						.qtyPicked(qty)
						.documentRef(line.getDDOrderLineId().toTableRecordReference())
						.checkIfAlreadyPacked(true)
						.build()).getSingleTopLevelTURecord();

		return HuId.ofRepoId(splitHU.getM_HU_ID());
	}

	private Optional<LocatorId> getInTransitLocatorId()
	{
		return warehouseService.getTrolleyByUserId(userId).map(LocatorQRCode::getLocatorId);
	}
}
