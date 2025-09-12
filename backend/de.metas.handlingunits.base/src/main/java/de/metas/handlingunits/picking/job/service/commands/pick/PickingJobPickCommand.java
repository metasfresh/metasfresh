package de.metas.handlingunits.picking.job.service.commands.pick;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.LUExtractTUsRequest;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.allocation.transfer.LUTUResult.LU;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TU;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TUPart;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TUsList;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.job.model.CurrentPickingTarget;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.LocatorInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.model.TUPickingTarget;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleId;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsGetRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.qrcodes.special.PickOnTheFlyQRCode;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.scannable_code.ScannedCode;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class PickingJobPickCommand
{
	private final static AdMessageKey HU_CANNOT_BE_PICKED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG");
	private final static AdMessageKey TU_CANNOT_BE_PICKED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.TU_CANNOT_BE_PICKED_ERROR_MSG");
	private final static AdMessageKey PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG");
	private final static AdMessageKey QTY_REJECTED_ALTERNATIVES_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.QTY_REJECTED_ALTERNATIVES_ERROR_MSG");
	private final static AdMessageKey NO_QR_CODE_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NO_QR_CODE_ERROR_MSG");
	private final static AdMessageKey CANNOT_PACK_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.CANNOT_PACK_ERROR_MSG");
	private final static AdMessageKey INVALID_NUMBER_QR_CODES_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.INVALID_NUMBER_QR_CODES_ERROR_MSG");
	private final static AdMessageKey UNKNOWN_TARGET_LU_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.UNKNOWN_TARGET_LU_ERROR_MSG");
	private final static AdMessageKey ERR_NotEnoughTUsFound = AdMessageKey.of("de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG");
	private final static AdMessageKey ERR_LMQ_ManualCatchWeightMustBePresent = AdMessageKey.of("de.metas.handlingunits.picking.job.CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG");
	private final static AdMessageKey NO_QTY_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NO_QTY_ERROR_MSG");

	//
	// Services
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final PackToHUsProducer packToHUsProducer;
	@NonNull private final HUReservationService huReservationService;
	@NonNull private final PickingConfigRepositoryV2 pickingConfigRepo;
	@NonNull private final InventoryService inventoryService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickFromHUQRCodeResolver pickFromHUQRCodeResolver;
	@NonNull private final PickedHUAttributesUpdater pickedHUAttributesUpdater;

	//
	// Params
	@NonNull private final PickingJobLineId _lineId;
	@NonNull private final PickingUnit pickingUnit;
	@NonNull private final PickingJobStepPickFromKey stepPickFromKey;
	@Nullable private final IHUQRCode pickFromHUQRCode;
	@NonNull private final Quantity qtyToPickCUs;
	@Nullable private final QtyTU qtyToPickTUs;
	@Nullable private final QtyRejectedWithReason qtyRejectedCUs;
	private final boolean isPickWholeTU;
	private final boolean checkIfAlreadyPacked;
	private final boolean createInventoryForMissingQty;
	private final boolean isCloseTarget;
	@NonNull private final PickAttributes _manualPickAttributes;

	//
	// State
	@NonNull private PickingJob _pickingJob;
	@Nullable private PickingJobStepId _stepId;
	@Nullable PickAttributes _pickAttributes; // lazy
	@Nullable private HUInfo _pickFromHUIdAndQRCode; // lazy
	@NonNull private final HashMap<ShipmentScheduleId, ShipmentScheduleInfo> shipmentSchedulesCache = new HashMap<>();

	@Builder
	private PickingJobPickCommand(
			final @NonNull PickingJobService pickingJobService,
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull HUQRCodesService huQRCodesService,
			final @NonNull InventoryService inventoryService,
			final @NonNull HUReservationService huReservationService,
			final @NonNull PickingConfigRepositoryV2 pickingConfigRepo,
			//
			final @NonNull PickingJob pickingJob,
			final @NonNull MobileUIPickingUserProfileRepository mobileUIPickingUserProfileRepository,
			final @NonNull PickingJobSlotService pickingSlotService,
			//
			final @NonNull PickingJobLineId pickingJobLineId,
			final @Nullable PickingJobStepId pickingJobStepId,
			final @Nullable PickingJobStepPickFromKey pickFromKey,
			final @Nullable ScannedCode pickFromQRCode,
			final @NonNull BigDecimal qtyToPickBD,
			final @Nullable BigDecimal qtyRejectedBD,
			final @Nullable QtyRejectedReasonCode qtyRejectedReasonCode,
			final @Nullable BigDecimal catchWeightBD,
			final boolean isPickWholeTU,
			final @Nullable Boolean checkIfAlreadyPacked,
			final boolean createInventoryForMissingQty,
			final boolean isSetBestBeforeDate,
			final @Nullable LocalDate bestBeforeDate,
			final boolean isSetLotNo,
			final @Nullable String lotNo,
			final boolean isCloseTarget)
	{
		Check.assumeGreaterOrEqualToZero(qtyToPickBD, "qtyToPickBD");

		this.pickingJobService = pickingJobService;
		this.pickingJobRepository = pickingJobRepository;
		this.huQRCodesService = huQRCodesService;
		this.huReservationService = huReservationService;
		this.pickingConfigRepo = pickingConfigRepo;
		this.inventoryService = inventoryService;
		this.pickingSlotService = pickingSlotService;
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		this.packToHUsProducer = PackToHUsProducer.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huPIItemProductBL(Services.get(IHUPIItemProductBL.class))
				.uomConversionBL(uomConversionBL)
				.inventoryService(inventoryService)
				.contextPickingJobId(pickingJob.getId())
				.build();
		this.pickFromHUQRCodeResolver = PickFromHUQRCodeResolver.builder()
				.productBL(Services.get(IProductBL.class))
				.handlingUnitsBL(handlingUnitsBL)
				.huQRCodesService(huQRCodesService)
				.warehouseBL(warehouseBL)
				.build();
		this.pickedHUAttributesUpdater = PickedHUAttributesUpdater.builder()
				.uomConversionBL(uomConversionBL)
				.build();

		this._pickingJob = pickingJob;
		this._lineId = pickingJobLineId;
		this._stepId = pickingJobStepId;
		this.stepPickFromKey = pickFromKey != null ? pickFromKey : PickingJobStepPickFromKey.MAIN;
		this.pickFromHUQRCode = pickFromQRCode != null ? huQRCodesService.parse(pickFromQRCode) : null;

		final PickingJobLine line = getLine();
		final PickingJobStep step = pickingJobStepId != null ? pickingJob.getStepById(pickingJobStepId) : null;
		final I_C_UOM uom = line.getUOM();
		this.isPickWholeTU = isPickWholeTU;
		this.checkIfAlreadyPacked = checkIfAlreadyPacked != null ? checkIfAlreadyPacked : true;
		this.createInventoryForMissingQty = createInventoryForMissingQty;

		this.pickingUnit = line.getPickingUnit();
		if (this.pickingUnit.isTU())
		{
			final TUPickingTarget tuPickingTarget = pickingJob.getTuPickingTargetEffective(this._lineId).orElse(null);
			if (tuPickingTarget != null)
			{
				throw new AdempiereException(TU_CANNOT_BE_PICKED_ERROR_MSG)
						.appendParametersToMessage()
						.setParameter("pickingJobId", pickingJob.getId())
						.setParameter("pickingJobLineId", this._lineId);
			}

			this.qtyToPickTUs = QtyTU.ofBigDecimal(qtyToPickBD);

			final PickingJobOptions pickingJobOptions = mobileUIPickingUserProfileRepository.getPickingJobOptions(line.getCustomerId());
			this.qtyToPickCUs = computeQtyToPickCUs(pickingJobOptions, line, qtyToPickTUs);

			if (qtyRejectedReasonCode != null)
			{
				final HUPIItemProduct packingInfo = line.getPackingInfo();

				final Quantity qtyRejectedCUs = QtyTU.optionalOfBigDecimal(qtyRejectedBD)
						.map(packingInfo::computeQtyCUsOfQtyTUs)
						.orElseGet(() -> computeQtyRejectedCUs(line, step, this.stepPickFromKey, this.qtyToPickCUs));

				this.qtyRejectedCUs = QtyRejectedWithReason.of(qtyRejectedCUs, qtyRejectedReasonCode);
			}
			else
			{
				this.qtyRejectedCUs = null;
			}
		}
		else if (this.pickingUnit.isCU())
		{
			this.qtyToPickCUs = Quantity.of(qtyToPickBD, uom);
			this.qtyToPickTUs = null;

			if (qtyRejectedReasonCode != null)
			{
				final Quantity qtyRejectedCUs = qtyRejectedBD != null
						? Quantity.of(qtyRejectedBD, uom)
						: computeQtyRejectedCUs(line, step, this.stepPickFromKey, this.qtyToPickCUs);

				this.qtyRejectedCUs = QtyRejectedWithReason.of(qtyRejectedCUs, qtyRejectedReasonCode);
			}
			else
			{
				this.qtyRejectedCUs = null;
			}
		}
		else
		{
			throw new AdempiereException(PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG)
					.appendParametersToMessage()
					.setParameter("PickingUnit", pickingUnit);
		}

		this._manualPickAttributes = PickAttributes.builder()
				.catchWeight(catchWeightBD != null && line.getCatchUomId() != null
						? Quantitys.of(catchWeightBD, line.getCatchUomId())
						: null)
				.isSetBestBeforeDate(isSetBestBeforeDate).bestBeforeDate(bestBeforeDate)
				.isSetLotNo(isSetLotNo).lotNo(lotNo)
				.build();

		this.isCloseTarget = isCloseTarget;
	}

	private static Quantity computeQtyRejectedCUs(
			@NonNull final PickingJobLine line,
			@Nullable final PickingJobStep step,
			@Nullable final PickingJobStepPickFromKey pickFromKey,
			@NonNull final Quantity qtyPicked)
	{
		if (step != null)
		{
			if (pickFromKey == null || pickFromKey.isMain())
			{
				return step.getQtyToPick().subtract(qtyPicked);
			}
			else
			{
				// NOTE: because, in case of alternatives, we don't know which is the qty scheduled to pick, 
				// we cannot calculate the qtyRejected
				throw new AdempiereException(QTY_REJECTED_ALTERNATIVES_ERROR_MSG);
			}
		}
		else
		{
			return line.getQtyToPick().subtract(qtyPicked);
		}
	}

	@NonNull
	private static Quantity computeQtyToPickCUs(
			@NonNull final PickingJobOptions pickingJobOptions,
			@NonNull final PickingJobLine line,
			@NonNull final QtyTU qtyTU)
	{
		final Quantity qtyToPickCUsBasedOnPackingInfo = line.getPackingInfo().computeQtyCUsOfQtyTUs(qtyTU);

		if (!pickingJobOptions.isConsiderSalesOrderCapacity())
		{
			return qtyToPickCUsBasedOnPackingInfo;
		}

		final boolean qtyLeftToBePickedIsLessThanComputedBasedOnPacking = line.getQtyRemainingToPick().signum() > 0
				&& (line.getQtyRemainingToPick().compareTo(qtyToPickCUsBasedOnPackingInfo) <= 0);

		return qtyLeftToBePickedIsLessThanComputedBasedOnPacking
				? line.getQtyRemainingToPick()
				: qtyToPickCUsBasedOnPackingInfo;
	}

	public PickingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		_pickingJob.assertNotProcessed();

		checkOrAllocatePickingSlot();

		validatePickFromHU();

		final List<PickingJobStepPickedToHU> pickedHUs;
		try (final IAutoCloseable ignored = HUContextHolder.temporarySet(handlingUnitsBL.createMutableHUContextForProcessing()))
		{

			pickedHUs = splitOutPickToHUs();
		}

		changeStep(step -> updateStepFromPickedHUs(step, pickedHUs));

		if (isCloseTarget)
		{
			closeLUAndTUPickingTargets();
		}

		pickingJobRepository.save(_pickingJob);

		// Try loading it again to make sure we persisted a consistent/valid model
		return pickingJobService.getById(_pickingJob.getId());
	}

	private PickingJob getPickingJob()
	{
		return _pickingJob;
	}

	private PickingJobStep getStep()
	{
		final PickingJobStepId stepId = getOrCreateStepId(); // IMPORTANT: don't inline this because getOrCreateStepId() changes the current "_pickingJob"
		return _pickingJob.getStepById(stepId);
	}

	private Optional<PickingJobStep> getStepIfExists()
	{
		return getStepIdIfExists().map(_pickingJob::getStepById);
	}

	@NonNull
	private PickingJobLineId getLineId()
	{
		return _lineId;
	}

	private PickingJobLine getLine()
	{
		return _pickingJob.getLineById(getLineId());
	}

	private ProductId getProductId() {return getLine().getProductId();}

	private void changeLine(@NonNull final UnaryOperator<PickingJobLine> lineMapper)
	{
		_pickingJob = _pickingJob.withChangedLine(getLineId(), lineMapper);
	}

	private void addStep(@NonNull final PickingJob.AddStepRequest request)
	{
		_pickingJob = _pickingJob.withNewStep(request);
	}

	private void changeStep(@NonNull final UnaryOperator<PickingJobStep> stepMapper)
	{
		_pickingJob = _pickingJob.withChangedStep(getOrCreateStepId(), stepMapper);
	}

	private void checkOrAllocatePickingSlot()
	{
		if (isLineLevelPickTarget())
		{
			changeLine(line -> {
				if (!line.isPickingSlotSet())
				{
					return line.withPickingSlot(getPickingJob().getPickingSlotNotNull());
				}
				else
				{
					return line;
				}
			});
		}
		else
		{
			getPickingJob().assertPickingSlotScanned();
		}
	}

	private boolean isLineLevelPickTarget()
	{
		return getPickingJob().isLineLevelPickTarget();
	}

	private Optional<LUPickingTarget> getLUPickingTarget()
	{
		return _pickingJob.getLuPickingTargetEffective(getLineId());
	}

	private Optional<TUPickingTarget> getTUPickingTarget()
	{
		return _pickingJob.getTuPickingTargetEffective(getLineId());
	}

	private void setPickingLUTarget(@NonNull final LUPickingTarget luPickingTarget)
	{
		if (isLineLevelPickTarget())
		{
			_pickingJob = _pickingJob.withLuPickingTarget(getLineId(), luPickingTarget);
		}
		else
		{
			_pickingJob = _pickingJob.withLuPickingTarget(null, luPickingTarget);
		}
	}

	private void setPickingLUTarget(@NonNull final LU lu)
	{
		final HuId luId = lu.getId();
		final HUQRCode qrCode = getQRCode(lu);
		setPickingLUTarget(LUPickingTarget.ofExistingHU(luId, qrCode));
	}

	private void setPickingTUTarget(@NonNull final TUPickingTarget tuPickingTarget)
	{
		if (isLineLevelPickTarget())
		{
			_pickingJob = _pickingJob.withTuPickingTarget(getLineId(), tuPickingTarget);
		}
		else
		{
			_pickingJob = _pickingJob.withTuPickingTarget(null, tuPickingTarget);
		}
	}

	private void setPickingTUTarget(@NonNull final TU tu)
	{
		final HuId tuId = tu.getId();
		final HUQRCode qrCode = getQRCode(tu);
		setPickingTUTarget(TUPickingTarget.ofExistingHU(tuId, qrCode));
	}

	private void updatePickingTarget(@NonNull final LUTUResult result)
	{
		{
			final LUPickingTarget luPickingTarget = getLUPickingTarget().orElse(null);
			if (luPickingTarget != null && luPickingTarget.isNewLU())
			{
				if (result.isSingleLU())
				{
					setPickingLUTarget(result.getSingleLU());
				}
			}
		}

		{
			final TUPickingTarget tuPickingTarget = getTUPickingTarget().orElse(null);
			if (tuPickingTarget != null && tuPickingTarget.isNewTU())
			{
				if (result.isSingleTopLevelTUOnly())
				{
					setPickingTUTarget(result.getSingleTopLevelTU());
				}
				else if (result.isSingleLU())
				{
					final LU lu = result.getSingleLU();
					if (lu.getTus().isSingleTU())
					{
						setPickingTUTarget(lu.getTus().getSingleTU());
					}
				}
			}
		}
	}

	private void closeLUAndTUPickingTargets()
	{
		if (isLineLevelPickTarget())
		{
			this._pickingJob = pickingJobService.closeLUAndTUPickingTargets(this._pickingJob, getLineId());
		}
		else
		{
			this._pickingJob = pickingJobService.closeLUAndTUPickingTargets(this._pickingJob, null);
		}
	}

	private PickingJobStepId getOrCreateStepId()
	{
		if (this._stepId == null)
		{
			this._stepId = createStep();
		}
		return this._stepId;
	}

	private Optional<PickingJobStepId> getStepIdIfExists()
	{
		return Optional.ofNullable(this._stepId);
	}

	private PickingJobStepId createStep()
	{
		final PickingJobStepId newStepId = pickingJobRepository.newPickingJobStepId();

		final PickingJobLine line = getLine();
		final HUInfo pickFromHU = getPickFromHUIdAndQRCode();
		final LocatorId pickFromLocatorId = handlingUnitsBL.getLocatorId(pickFromHU.getId());

		final PackToSpec packToSpec;
		if (pickingUnit.isTU())
		{
			packToSpec = PackToSpec.ofTUPackingInstructionsId(line.getPackingInfo().getId());
		}
		else
		{
			pickingUnit.assertIsCU();
			packToSpec = PackToSpec.VIRTUAL; // will aggregate them later
		}

		addStep(
				PickingJob.AddStepRequest.builder()
						.isGeneratedOnFly(true)
						.newStepId(newStepId)
						.lineId(line.getId())
						.qtyToPick(qtyToPickCUs)
						.pickFromLocator(LocatorInfo.builder()
								.id(pickFromLocatorId)
								.caption(warehouseBL.getLocatorNameById(pickFromLocatorId))
								.build())
						.pickFromHU(pickFromHU)
						.packToSpec(packToSpec)
						.build()
		);

		return newStepId;
	}

	@Nullable
	private Quantity getCatchWeight()
	{
		return getPickAttributes().getCatchWeight();
	}

	@NonNull
	private PickAttributes getPickAttributes()
	{
		if (this._pickAttributes == null)
		{
			this._pickAttributes = computePickAttributes();
		}
		return this._pickAttributes;
	}

	@NonNull
	private PickAttributes computePickAttributes()
	{
		PickAttributes pickAttributes = this._manualPickAttributes;
		boolean isCatchWeightRequired = false;
		if (pickFromHUQRCode != null)
		{
			isCatchWeightRequired = pickFromHUQRCode.isWeightRequired();
			pickAttributes = pickAttributes.fallbackTo(PickAttributes.ofHUQRCode(pickFromHUQRCode, getLine().getCatchUomId()));
		}

		if (isCatchWeightRequired && pickAttributes.getCatchWeight() == null)
		{
			throw new AdempiereException(ERR_LMQ_ManualCatchWeightMustBePresent)
					.appendParametersToMessage()
					.setParameter("QRCode", pickFromHUQRCode)
					.setParameter("PickAttributes", pickAttributes);
		}

		return pickAttributes;
	}

	@NonNull
	private HUInfo getPickFromHUIdAndQRCode()
	{
		if (this._pickFromHUIdAndQRCode == null)
		{
			this._pickFromHUIdAndQRCode = computePickFromHUIdAndQRCode();
		}
		return this._pickFromHUIdAndQRCode;
	}

	@NonNull
	private HUInfo computePickFromHUIdAndQRCode()
	{
		if (pickFromHUQRCode == null)
		{
			throw new AdempiereException(NO_QR_CODE_ERROR_MSG);
		}
		else if (pickFromHUQRCode instanceof PickOnTheFlyQRCode)
		{
			return createPickFromHUOnTheFly();
		}
		else
		{
			final ProductId productId = getProductId();
			final ShipmentScheduleInfo shipmentScheduleInfo = getShipmentScheduleInfo();
			final BPartnerId customerId = shipmentScheduleInfo.getBpartnerId();
			final WarehouseId warehouseId = shipmentScheduleInfo.getWarehouseId();
			return pickFromHUQRCodeResolver.resolve(pickFromHUQRCode, productId, customerId, warehouseId);
		}
	}

	private HUInfo createPickFromHUOnTheFly()
	{
		final PickingJob pickingJob = getPickingJob();
		if (!pickingJob.isAnonymousPickHUsOnTheFly())
		{
			throw new AdempiereException("Anonymous picking HUs on the fly is not allowed");
		}

		final ShipmentScheduleInfo shipmentScheduleInfo = getShipmentScheduleInfo();

		final HuId newCUId = inventoryService.createInventoryForMissingQty(CreateVirtualInventoryWithQtyReq.builder()
				.clientAndOrgId(shipmentScheduleInfo.getClientAndOrgId())
				.warehouseId(shipmentScheduleInfo.getWarehouseId())
				.productId(shipmentScheduleInfo.getProductId())
				.qty(qtyToPickCUs)
				.movementDate(SystemTime.asZonedDateTime())
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.pickingJobId(pickingJob.getId())
				.build());

		final HUQRCode huQRCode = huQRCodesService.getQRCodeByHuId(newCUId);
		return HUInfo.ofHuIdAndQRCode(newCUId, huQRCode);
	}

	private ShipmentScheduleAndJobScheduleId getScheduleId()
	{
		return getStepIfExists()
				.map(PickingJobStep::getScheduleId)
				.orElseGet(() -> getLine().getScheduleId());
	}

	private List<PickingJobStepPickedToHU> splitOutPickToHUs()
	{
		if (qtyToPickCUs.isZero() && !isPickWholeTU)
		{
			throw new AdempiereException("qtyToPickCUs shall not be zero if isPickWholeTU is false");
			// return ImmutableList.of();
		}

		final PickingJobStep step = getStep();

		final ProductId productId = step.getProductId();
		final PickingJobStepPickFrom pickFrom = step.getPickFrom(stepPickFromKey).assertNotPicked();
		final HUInfo pickFromHU = pickFrom.getPickFromHU();
		final LocatorId pickFromLocatorId = pickFrom.getPickFromLocatorId();

		final PackToHUsProducer.PackToInfo packToInfo = packToHUsProducer.extractPackToInfo(
				productId,
				step.getPackToSpec(),
				getLUPickingTarget().orElse(null),
				getTUPickingTarget().orElse(null),
				getLine().getDeliveryBPLocationId(),
				pickFromLocatorId);

		trxManager.assertThreadInheritedTrxExists();

		final LUTUResult packedHUs;
		if (pickingUnit.isTU())
		{
			final QtyTU qtyToPickTUs = Check.assumeNotNull(this.qtyToPickTUs, "qtyToPickTUs is set");
			Check.assume(qtyToPickTUs.isPositive(), "qtyToPickTUs is positive");

			final I_M_HU pickFromHURecord = handlingUnitsBL.getById(pickFromHU.getId());
			if (handlingUnitsBL.isVirtual(pickFromHURecord))
			{
				packedHUs = pickCUsAndPackTo(productId, pickFromHU.getId(), packToInfo);
			}
			else
			{
				packedHUs = pickWholeTUs(productId, pickFromHURecord, qtyToPickTUs);
			}
		}
		else
		{
			pickingUnit.assertIsCU();

			if (isPickWholeTU)
			{
				final I_M_HU pickFromHURecord = handlingUnitsBL.getById(pickFromHU.getId());
				packedHUs = pickWholeTUs(productId, pickFromHURecord, QtyTU.ONE);
			}
			else
			{
				packedHUs = pickCUsAndPackTo(productId, pickFromHU.getId(), packToInfo);
			}
		}

		updatePickingTarget(packedHUs);
		addToPickingSlotQueue(packedHUs);
		pickedHUAttributesUpdater.updateHUs(packedHUs, getPickAttributes(), productId);

		//
		// Add shipment schedule QtyPicked records
		if (packedHUs.isEmpty())
		{
			throw new AdempiereException(CANNOT_PACK_ERROR_MSG, pickFromHU, packToInfo, qtyToPickCUs);
		}
		else if (packedHUs.isSingleFullTU())
		{
			final TU tu = packedHUs.getSingleTU();
			final Quantity qtyPicked = isPickWholeTU ? getStorageQty(tu, productId) : qtyToPickCUs;
			addShipmentScheduleQtyPicked(tu, qtyPicked);

			return ImmutableList.of(toSinglePickingJobStepPickedToHU(tu, qtyPicked, getCatchWeight(), pickFrom));
		}
		else
		{
			final IHUStorageFactory huStorageFactory = HUContextHolder.getCurrent().getHUStorageFactory();
			final I_C_UOM uom = qtyToPickCUs.getUOM();
			final ImmutableList.Builder<PickingJobStepPickedToHU> result = ImmutableList.builder();
			for (final TU tu : packedHUs.getAllTUs())
			{
				if (tu.isFullTU())
				{
					final Quantity qtyPicked = huStorageFactory.getStorage(tu.toHU()).getQuantity(productId, uom);
					addShipmentScheduleQtyPicked(tu, qtyPicked);

					result.addAll(toPickingJobStepPickedToHU(tu, qtyPicked, pickFrom));
				}
				else
				{
					final ImmutableList<TUPart> cus = tu.getCUsNotEmpty();
					final List<Quantity> catchWeights = getCatchWeight() != null ? getCatchWeight().spreadEqually(cus.size()) : null;
					for (int i = 0; i < cus.size(); i++)
					{
						final TUPart cu = cus.get(i);
						final Quantity catchWeightPerCU = catchWeights != null ? catchWeights.get(i) : null;
						final Quantity qtyPicked = huStorageFactory.getStorage(cu.toHU()).getQuantity(productId, uom);
						addShipmentScheduleQtyPicked(cu, qtyPicked);

						result.addAll(toPickingJobStepPickedToHU(tu, cu, qtyPicked, catchWeightPerCU, pickFrom));
					}
				}
			}

			return result.build();
		}
	}

	private void addShipmentScheduleQtyPicked(@NonNull final TU tu, @NonNull final Quantity qtyPicked)
	{
		final IMutableHUContext huContext = HUContextHolder.getCurrent();
		final ShipmentScheduleInfo shipmentScheduleInfo = getShipmentScheduleInfo();
		final ProductId productId = shipmentScheduleInfo.getProductId();
		final boolean anonymousHuPickedOnTheFly = false;

		shipmentScheduleBL.addQtyPickedAndUpdateHU(
				shipmentScheduleInfo.getRecord(),
				CatchWeightHelper.extractQtys(
						huContext,
						productId,
						qtyPicked,
						tu.toHU()),
				tu.toHU(),
				huContext,
				anonymousHuPickedOnTheFly);
	}

	private void addShipmentScheduleQtyPicked(@NonNull final TUPart cu, @NonNull final Quantity qtyPicked)
	{
		final IMutableHUContext huContext = HUContextHolder.getCurrent();
		final ShipmentScheduleInfo shipmentScheduleInfo = getShipmentScheduleInfo();
		final ProductId productId = shipmentScheduleInfo.getProductId();
		final boolean anonymousHuPickedOnTheFly = false;

		shipmentScheduleBL.addQtyPickedAndUpdateHU(
				shipmentScheduleInfo.getRecord(),
				CatchWeightHelper.extractQtys(
						huContext,
						productId,
						qtyPicked,
						cu.toHU()),
				cu.toHU(),
				huContext,
				anonymousHuPickedOnTheFly);
	}

	private PickingJobStep updateStepFromPickedHUs(
			@NonNull final PickingJobStep step,
			@NonNull final List<PickingJobStepPickedToHU> pickedHUs)
	{
		return step.reduceWithPickedEvent(
				stepPickFromKey,
				PickingJobStepPickedTo.builder()
						.qtyRejected(qtyRejectedCUs)
						.actualPickedHUs(ImmutableList.copyOf(pickedHUs))
						.build()
		);
	}

	private PickingJobStepPickedToHU toSinglePickingJobStepPickedToHU(
			@NonNull final TU tu,
			@NonNull final Quantity qtyPicked,
			@Nullable final Quantity catchWeight,
			@NonNull final PickingJobStepPickFrom pickFrom)
	{
		return PickingJobStepPickedToHU.builder()
				.pickFromHUId(pickFrom.getPickFromHUId())
				.actualPickedHU(getSingleTUInfo(tu))
				.qtyPicked(qtyPicked)
				.catchWeight(catchWeight)
				.createdAt(SystemTime.asInstant())
				.build();
	}

	private List<PickingJobStepPickedToHU> toPickingJobStepPickedToHU(
			@NonNull final TU tu,
			@NonNull final Quantity qtyPicked,
			@NonNull final PickingJobStepPickFrom pickFrom)
	{

		final List<HUQRCode> huQRCodes = huQRCodesService.getOrCreateQRCodesByHuId(tu.getId());
		if (huQRCodes.size() != tu.getQtyTU().toInt())
		{
			throw new AdempiereException(INVALID_NUMBER_QR_CODES_ERROR_MSG, tu.getQtyTU(), huQRCodes.size());
		}

		final List<Quantity> qtyPickedPerTU = qtyPicked.spreadEqually(tu.getQtyTU().toInt());

		final PickingJobStepPickedToHU.PickingJobStepPickedToHUBuilder pickedHUTemplate = PickingJobStepPickedToHU.builder()
				.pickFromHUId(pickFrom.getPickFromHUId())
				.catchWeight(null);

		final ImmutableList.Builder<PickingJobStepPickedToHU> result = ImmutableList.builder();
		for (int i = 0; i < tu.getQtyTU().toInt(); i++)
		{
			result.add(pickedHUTemplate
					.actualPickedHU(HUInfo.builder().id(tu.getId()).qrCode(huQRCodes.get(i)).build())
					.qtyPicked(qtyPickedPerTU.get(i))
					.createdAt(SystemTime.asInstant())
					.build());
		}

		return result.build();
	}

	private List<PickingJobStepPickedToHU> toPickingJobStepPickedToHU(
			@NonNull final TU tu1,
			@NonNull final TUPart cu,
			@NonNull final Quantity qtyPicked,
			@Nullable final Quantity catchWeight,
			@NonNull final PickingJobStepPickFrom pickFrom)
	{

		final List<HUQRCode> huQRCodes = huQRCodesService.getOrCreateQRCodesByHuId(tu1.getId());
		if (huQRCodes.size() != 1)
		{
			throw new AdempiereException(INVALID_NUMBER_QR_CODES_ERROR_MSG, 1, huQRCodes.size());
		}
		final HUQRCode huQRCode = huQRCodes.get(0);

		return ImmutableList.of(
				PickingJobStepPickedToHU.builder()
						.pickFromHUId(pickFrom.getPickFromHUId())
						.actualPickedHU(HUInfo.ofHuIdAndQRCode(cu.getId(), huQRCode))
						.qtyPicked(qtyPicked)
						.catchWeight(catchWeight)
						.createdAt(SystemTime.asInstant())
						.build()
		);
	}

	private LUTUResult pickWholeTUs(
			@NonNull final ProductId productId,
			@NonNull final I_M_HU pickFromHU,
			@NonNull final QtyTU qtyToPickTUs)
	{
		final HUTransformService huTransformService = HUTransformService.newInstance();

		final LUPickingTarget pickingTarget = getLUPickingTarget().orElse(null);
		final LUTUResult result;

		if (handlingUnitsBL.isLoadingUnit(pickFromHU))
		{
			final HUTransformService.TargetLU targetLU = LUPickingTarget.apply(pickingTarget, new LUPickingTarget.CaseMapper<HUTransformService.TargetLU>()
			{
				@Override
				public HUTransformService.TargetLU noLU()
				{
					return HUTransformService.TargetLU.NONE;
				}

				@Override
				public HUTransformService.TargetLU newLU(final HuPackingInstructionsId luPackingInstructionsId)
				{
					return HUTransformService.TargetLU.ofNewLU(handlingUnitsBL.getPI(luPackingInstructionsId));
				}

				@Override
				public HUTransformService.TargetLU existingLU(final HuId luId, final HUQRCode luQRCode)
				{
					return HUTransformService.TargetLU.ofExistingLU(handlingUnitsBL.getById(luId));
				}
			});

			result = huTransformService.luExtractTUs(LUExtractTUsRequest.builder()
					.sourceLU(pickFromHU)
					.qtyTU(qtyToPickTUs)
					.targetLU(targetLU)
					.build());
			InterfaceWrapperHelper.setThreadInheritedTrxName(result.getAllTURecords()); // workaround because the returned HUs have trxName=null
		}
		else if (qtyToPickTUs.isOne() && pickingTarget == null && handlingUnitsBL.isTransportUnit(pickFromHU))
		{
			final I_M_HU packedTU = huTransformService.splitOutTURecord(pickFromHU);
			result = LUTUResult.ofSingleTopLevelTU(packedTU);
		}
		else
		{
			if (pickingTarget == null)
			{
				final TUsList topLevelTUs = huTransformService.husToNewTUs(
						HUTransformService.HUsToNewTUsRequest.builder()
								.sourceHU(pickFromHU)
								.qtyTU(qtyToPickTUs)
								.expectedProductId(productId)
								.build());
				result = LUTUResult.ofTopLevelTUs(topLevelTUs);
			}
			else if (pickingTarget.isExistingLU())
			{
				final I_M_HU lu = handlingUnitsBL.getById(pickingTarget.getLuId());
				result = huTransformService.tuToExistingLU(pickFromHU, qtyToPickTUs, lu);
			}
			else if (pickingTarget.isNewLU())
			{
				result = huTransformService.tuToNewLU(pickFromHU, qtyToPickTUs, pickingTarget.getLuPIIdNotNull());
			}
			else
			{
				throw new AdempiereException(UNKNOWN_TARGET_LU_ERROR_MSG)
						.appendParametersToMessage()
						.setParameter("pickingTarget", pickingTarget);
			}
		}

		if (result.getQtyTUs().compareTo(qtyToPickTUs) != 0)
		{
			throw new AdempiereException(ERR_NotEnoughTUsFound)
					.setParameter("qtyToPickTUs", qtyToPickTUs)
					.setParameter("actuallyPickedTUsCount", result.getQtyTUs())
					.setParameter("result", result);
		}

		handlingUnitsBL.setHUStatus(result.getAllLUAndTURecords(), X_M_HU.HUSTATUS_Picked);

		return result;
	}

	private LUTUResult pickCUsAndPackTo(
			@NonNull final ProductId productId,
			@NonNull final HuId pickFromVHUId,
			@NonNull final PackToHUsProducer.PackToInfo packToInfo)
	{
		return packToHUsProducer.packToHU(
				PackToHUsProducer.PackToHURequest.builder()
						.huContext(HUContextHolder.getCurrent())
						.pickFromHUId(pickFromVHUId)
						.packToInfo(packToInfo)
						.productId(productId)
						.qtyPicked(qtyToPickCUs)
						.catchWeight(getCatchWeight())
						.documentRef(getLineId().toTableRecordReference())
						.checkIfAlreadyPacked(checkIfAlreadyPacked)
						.createInventoryForMissingQty(createInventoryForMissingQty)
						.build()
		);
	}

	private void validatePickFromHU()
	{
		final HuId huIdToBePicked = getHuIdToBePicked();

		// Accept destroyed HUs because in case of createInventoryForMissingQty we will create a new HU out of the blue
		if (handlingUnitsBL.isDestroyed(huIdToBePicked))
		{
			return;
		}

		final ShipmentScheduleInfo shipmentScheduleInfo = getShipmentScheduleInfo();
		final WarehouseId warehouseId = shipmentScheduleInfo.getWarehouseId();
		final BPartnerId customerId = shipmentScheduleInfo.getBpartnerId();
		final PickFromHUsGetRequest request = PickFromHUsGetRequest.builder()
				.pickFromLocatorIds(warehouseBL.getLocatorIdsOfTheSamePickingGroup(warehouseId))
				.partnerId(customerId)
				.productId(shipmentScheduleInfo.getProductId())
				.asiId(shipmentScheduleInfo.getAsiId())
				.bestBeforePolicy(shipmentScheduleInfo.getBestBeforePolicy().orElseGet(() -> bpartnerBL.getBestBeforePolicy(customerId)))
				.reservationRef(Optionals.firstPresentOfSuppliers(
						() -> getStepIdIfExists().map(HUReservationDocRef::ofPickingJobStepId),
						() -> shipmentScheduleInfo.getSalesOrderLineId().map(HUReservationDocRef::ofSalesOrderLineId)
				))
				.enforceMandatoryAttributesOnPicking(true)
				.onlyHuIds(ImmutableSet.of(huIdToBePicked))
				.build();

		final PickFromHUsSupplier pickFromHUsSupplier = PickFromHUsSupplier.builder()
				.huReservationService(huReservationService)
				.considerAttributes(pickingConfigRepo.getPickingConfig().isConsiderAttributes())
				.build();

		if (!pickFromHUsSupplier.hasEligiblePickFromHUs(request))
		{
			throw new AdempiereException(HU_CANNOT_BE_PICKED_ERROR_MSG)
					.setParameter("huIdToBePicked", huIdToBePicked)
					.markAsUserValidationError();
		}
	}

	@NonNull
	private HuId getHuIdToBePicked()
	{
		final PickingJobStep step = getStepIfExists().orElse(null);
		if (step != null)
		{
			return getStep()
					.getPickFrom(stepPickFromKey)
					.assertNotPicked()
					.getPickFromHUId();
		}
		else
		{
			return getPickFromHUIdAndQRCode().getId();
		}
	}

	private ShipmentScheduleInfo getShipmentScheduleInfo()
	{
		return shipmentSchedulesCache.computeIfAbsent(getScheduleId().getShipmentScheduleId(), this::retrieveShipmentScheduleInfo);
	}

	private ShipmentScheduleInfo retrieveShipmentScheduleInfo(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(shipmentScheduleId);

		return ShipmentScheduleInfo.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(shipmentSchedule.getAD_Client_ID(), shipmentSchedule.getAD_Org_ID()))
				.warehouseId(shipmentScheduleBL.getWarehouseId(shipmentSchedule))
				.bpartnerId(shipmentScheduleBL.getBPartnerId(shipmentSchedule))
				.salesOrderLineId(Optional.ofNullable(OrderLineId.ofRepoIdOrNull(shipmentSchedule.getC_OrderLine_ID())))
				.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(shipmentSchedule.getM_AttributeSetInstance_ID()))
				.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.optionalOfNullableCode(shipmentSchedule.getShipmentAllocation_BestBefore_Policy()))
				.record(shipmentSchedule)
				.build();
	}

	private HUInfo getSingleTUInfo(@NonNull final TU tu)
	{
		tu.assertSingleTU();
		return getHUInfo(tu.getId());
	}

	private HUInfo getHUInfo(@NonNull final HuId huId)
	{
		return HUInfo.builder()
				.id(huId)
				.qrCode(huQRCodesService.getQRCodeByHuId(huId))
				.build();
	}

	public Quantity getStorageQty(@NonNull final TU tu, @NonNull final ProductId productId)
	{
		final IHUStorageFactory huStorageFactory = HUContextHolder.getCurrent().getHUStorageFactory();
		return huStorageFactory.getStorage(tu.toHU()).getQuantity(productId).orElseThrow(() -> new AdempiereException(NO_QTY_ERROR_MSG, tu, productId));
	}

	private HUQRCode getQRCode(@NonNull final LU lu) {return huQRCodesService.getQRCodeByHuId(lu.getId());}

	private HUQRCode getQRCode(@NonNull final TU tu) {return huQRCodesService.getQRCodeByHuId(tu.getId());}

	private void addToPickingSlotQueue(final LUTUResult packedHUs)
	{
		final PickingSlotId pickingSlotId = getPickingSlotId().orElse(null);
		if (pickingSlotId == null)
		{
			return;
		}

		final CurrentPickingTarget currentPickingTarget = getPickingJob().getCurrentPickingTarget();
		final LinkedHashSet<HuId> huIdsToAdd = new LinkedHashSet<>();

		for (final LU lu : packedHUs.getLus())
		{
			if (lu.isPreExistingLU())
			{
				continue;
			}

			// do not add it if is current picking target, we will add it when closing the picking target. 
			if (currentPickingTarget.matches(lu.getId()))
			{
				continue;
			}

			huIdsToAdd.add(lu.getId());
		}

		for (final TU tu : packedHUs.getTopLevelTUs())
		{
			// do not add it if is current picking target, we will add it when closing the picking target. 
			if (currentPickingTarget.matches(tu.getId()))
			{
				continue;
			}

			huIdsToAdd.add(tu.getId());
		}

		if (!huIdsToAdd.isEmpty())
		{
			pickingSlotService.addToPickingSlotQueue(pickingSlotId, huIdsToAdd);
		}
	}

	private Optional<PickingSlotId> getPickingSlotId()
	{
		return getPickingJob().getPickingSlotIdEffective(getLineId());
	}
}
