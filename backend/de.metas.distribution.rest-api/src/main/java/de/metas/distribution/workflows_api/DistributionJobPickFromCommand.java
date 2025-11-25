package de.metas.distribution.workflows_api;

import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleCreateRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderPickFromRequest;
import de.metas.distribution.rest_api.JsonDistributionEvent;
import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.trace.HUAccessService;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;

import javax.annotation.Nullable;

public class DistributionJobPickFromCommand
{
	private static final AdMessageKey NOT_ENOUGH_QTY = AdMessageKey.of("de.metas.distribution.workflows_api.NotEnoughQty");
	private static final AdMessageKey PRODUCT_DOES_NOT_MATCH = AdMessageKey.of("de.metas.distribution.workflows_api.ProductDoesNotMatch");

	// Services
	@NonNull private final ITrxManager trxManager;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final PackToHUsProducer packToHUsProducer;
	@NonNull private final HUAccessService huAccessService;

	// Params
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
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices,
			@NonNull final InventoryService inventoryService,
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final IHUPIItemProductBL hupiItemProductBL,
			@NonNull final HUAccessService huAccessService,
			//
			@NonNull final DistributionJob job,
			@Nullable final DistributionJobLineId lineId,
			@Nullable final DistributionJobStepId stepId,
			@Nullable final JsonDistributionEvent.PickFrom pickFrom)
	{
		this.trxManager = trxManager;
		this.huQRCodesService = huQRCodesService;
		this.handlingUnitsBL = handlingUnitsBL;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.loadingSupportServices = loadingSupportServices;
		this.huAccessService = huAccessService;
		this.packToHUsProducer = PackToHUsProducer.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huPIItemProductBL(hupiItemProductBL)
				.uomConversionBL(uomConversionBL)
				.inventoryService(inventoryService)
				.build();
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
		try (final IAutoCloseable ignored = HUContextHolder.temporarySet(handlingUnitsBL.createMutableHUContextForProcessing()))
		{
			final DistributionJobStepId stepId = getOrCreateStep();

			final DDOrderMoveSchedule schedule = ddOrderMoveScheduleService.pickFromHU(DDOrderPickFromRequest.builder()
					.scheduleId(stepId.toScheduleId())
					.huId(getHuIdToPick(changedJob.getLineByStepId(stepId)))
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
		final IHUProductStorage huStorage = handlingUnitsBL.getSingleHUProductStorage(huId);

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
		final IHUQRCode huQRCode = huQRCodesService.parse(Check.assumeNotNull(pickFrom.getQrCode(), "pickFrom.qrCode must be set"));
		final HuId sourceHuId;
		if (huQRCode instanceof HUQRCode)
		{
			sourceHuId = huQRCodesService.getHuIdByQRCode((HUQRCode)huQRCode);
		}
		else
		{
			throw new AdempiereException("Invalid QRCode: " + huQRCode);
		}

		final Quantity sourceHUQty = huAccessService.retrieveProductQty(sourceHuId, line.getProductId())
				.orElseThrow(() -> new AdempiereException(PRODUCT_DOES_NOT_MATCH));

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
}
