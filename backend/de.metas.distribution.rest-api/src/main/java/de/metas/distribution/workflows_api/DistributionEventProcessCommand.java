package de.metas.distribution.workflows_api;

import de.metas.common.util.pair.IPair;
import de.metas.distribution.ddorder.movement.schedule.DDOrderDropToRequest;
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
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.trace.HUAccessService;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.model.I_DD_OrderLine;

import java.math.BigDecimal;
import java.util.Optional;

class DistributionEventProcessCommand
{
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
	@NonNull private final JsonDistributionEvent event;

	// State
	private DistributionJob changedJob;
	private HuId _huIdToPick; // lazy

	@Builder
	public DistributionEventProcessCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices,
			@NonNull final InventoryService inventoryService,
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final IHUPIItemProductBL hupiItemProductBL,
			@NonNull final HUAccessService huAccessService,
			@NonNull final DistributionJob job,
			@NonNull final JsonDistributionEvent event)
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
		this.event = event;
		//state
		this.changedJob = null;
		this._huIdToPick = null;
	}

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
		try (final IAutoCloseable ignored = HUContextHolder.temporarySet(handlingUnitsBL.createMutableHUContextForProcessing()))
		{
			final DistributionJobStepId stepId = getOrCreateStep();

			final DDOrderMoveSchedule schedule = ddOrderMoveScheduleService.pickFromHU(DDOrderPickFromRequest.builder()
																							   .scheduleId(stepId.toScheduleId())
																							   .huId(getHuIdToPick(changedJob.getLineByStepId(stepId)))
																							   .build());

			final DistributionJobStep changedStep = DistributionJobLoader.toDistributionJobStep(schedule, loadingSupportServices);
			return changedJob.withChangedStep(stepId, oldStep -> changedStep);
		}
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
		final DistributionJobStepId stepId = event.getDistributionStepId();
		if (stepId != null)
		{
			return stepId;
		}

		final DistributionJobLineId lineId = Check.assumeNotNull(event.getLineId(), "lineId must be set");
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
		final JsonDistributionEvent.PickFrom pickFrom = Check.assumeNotNull(event.getPickFrom(), "pickFrom must be set");
		final HUQRCode huQRCode = HUQRCode.fromGlobalQRCodeJsonString(Check.assumeNotNull(pickFrom.getQrCode(), "pickFrom.qrCode must be set"));

		final HuId sourceHuId = huQRCodesService.getHuIdByQRCode(huQRCode);
		final Quantity sourceHUQty = huAccessService
				.retrieveProductAndQty(handlingUnitsBL.getById(sourceHuId))
				.filter(productAndQty -> productAndQty.getLeft().equals(line.getProduct().getProductId()))
				.map(IPair::getRight)
				.orElseThrow(() -> new AdempiereException("Scanned HU doesn't match the line's product!"));

		return getQuantityToDistribute(pickFrom, line)
				.filter(qty -> !qty.qtyAndUomCompareToEquals(sourceHUQty))
				.map(qty -> splitQty(line, sourceHuId, qty))
				.orElse(sourceHuId);
	}

	@NonNull
	private HuId splitQty(
			@NonNull final DistributionJobLine line,
			@NonNull final HuId pickFromVHUId,
			@NonNull final Quantity qtyToDistribute)
	{
		final I_DD_OrderLine ddOrderLineRecord = loadingSupportServices.getDDOrderLineById(line.getId().toDDOrderLineId());
		final PackToHUsProducer.PackToInfo packToInfo = packToHUsProducer
				.getPackToInfoForDistribution(PackToSpec.VIRTUAL, line.getPickFromLocator().getLocatorId());

		final I_M_HU splitHU = packToHUsProducer.packToHU(
				PackToHUsProducer.PackToHURequest.builder()
						.huContext(HUContextHolder.getCurrent())
						.pickFromHUId(pickFromVHUId)
						.packToInfo(packToInfo)
						.productId(line.getProduct().getProductId())
						.qtyPicked(qtyToDistribute)
						.documentRef(TableRecordReference.of(ddOrderLineRecord))
						.checkIfAlreadyPacked(true)
						.build()).getSingleTopLevelTURecord();

		return HuId.ofRepoId(splitHU.getM_HU_ID());
	}

	@NonNull
	private Optional<Quantity> getQuantityToDistribute(
			@NonNull final JsonDistributionEvent.PickFrom pickFrom,
			@NonNull final DistributionJobLine line)
	{
		return Optional.ofNullable(pickFrom.getQtyPicked())
				.map(qtyPicked -> toQty(qtyPicked, line));
	}

	@NonNull
	private Quantity toQty(@NonNull final BigDecimal qtyPicked, @NonNull final DistributionJobLine line)
	{
		return Quantitys.of(qtyPicked, line.getQtyToMove().getUomId());
	}
}
