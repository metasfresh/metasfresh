package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.ean13.EAN13;
import de.metas.ean13.EAN13ProductCode;
import de.metas.gs1.GTIN;
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
import de.metas.handlingunits.allocation.transfer.LUTUResult.TUsList;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.picking.candidate.commands.PackedHUWeightNetUpdater;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
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
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsGetRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class PickingJobPickCommand
{
	private final static AdMessageKey HU_CANNOT_BE_PICKED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG");
	private final static AdMessageKey TU_CANNOT_BE_PICKED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.TU_CANNOT_BE_PICKED_ERROR_MSG");
	private final static AdMessageKey PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG");
	private final static AdMessageKey NEGATIVE_CATCH_WEIGHT_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NEGATIVE_CATCH_WEIGHT_ERROR_MSG");
	private final static AdMessageKey QTY_REJECTED_ALTERNATIVES_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.QTY_REJECTED_ALTERNATIVES_ERROR_MSG");
	private final static AdMessageKey NO_QR_CODE_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NO_QR_CODE_ERROR_MSG");
	private final static AdMessageKey L_M_QR_CODE_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG");
	private final static AdMessageKey QR_CODE_EXTERNAL_LOT_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_EXTERNAL_LOT_ERROR_MSG");
	private final static AdMessageKey QR_CODE_PRODUCT_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_PRODUCT_ERROR_MSG");
	private final static AdMessageKey CANNOT_PACK_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.CANNOT_PACK_ERROR_MSG");
	private final static AdMessageKey INVALID_NUMBER_QR_CODES_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.INVALID_NUMBER_QR_CODES_ERROR_MSG");
	private final static AdMessageKey UNKNOWN_TARGET_LU_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.UNKNOWN_TARGET_LU_ERROR_MSG");
	private final static AdMessageKey NOT_ENOUGH_TUS_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG");
	private final static AdMessageKey CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG");
	private final static AdMessageKey CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG");
	private final static AdMessageKey NO_QTY_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NO_QTY_ERROR_MSG");
	//
	// Services
	@NonNull
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	@NonNull
	private final IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	@NonNull
	private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull
	private final PickingJobService pickingJobService;
	@NonNull
	private final PickingJobRepository pickingJobRepository;
	@NonNull
	private final HUQRCodesService huQRCodesService;
	@NonNull
	private final PackToHUsProducer packToHUsProducer;
	@NonNull
	private final HUReservationService huReservationService;
	@NonNull
	private final PickingConfigRepositoryV2 pickingConfigRepo;
	//
	// Params
	@NonNull
	private final PickingJobLineId _lineId;
	@NonNull
	private final PickingUnit pickingUnit;
	@NonNull
	private final PickingJobStepPickFromKey stepPickFromKey;
	@Nullable
	private final IHUQRCode pickFromHUQRCode;
	@NonNull
	private final Quantity qtyToPickCUs;
	@Nullable
	private final QtyTU qtyToPickTUs;
	@Nullable
	private final QtyRejectedWithReason qtyRejectedCUs;
	@Nullable
	private final Quantity catchWeight;
	private final boolean isPickWholeTU;
	private final boolean checkIfAlreadyPacked;
	private final boolean createInventoryForMissingQty;
	private final boolean isSetBestBeforeDate;
	private final LocalDate bestBeforeDate;
	private final boolean isSetLotNo;
	private final String lotNo;
	private final boolean isCloseTarget;
	@NonNull
	private final HashMap<ShipmentScheduleId, ShipmentScheduleInfo> shipmentSchedulesCache = new HashMap<>();
	//
	// State
	@NonNull
	private PickingJob _pickingJob;
	@Nullable
	private PickingJobStepId _stepId;

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
			final @NonNull PickingJobLineId pickingJobLineId,
			final @Nullable PickingJobStepId pickingJobStepId,
			final @Nullable PickingJobStepPickFromKey pickFromKey,
			final @Nullable IHUQRCode pickFromHUQRCode,
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
		this.packToHUsProducer = PackToHUsProducer.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huPIItemProductBL(Services.get(IHUPIItemProductBL.class))
				.uomConversionBL(uomConversionBL)
				.inventoryService(inventoryService)
				.contextPickingJobId(pickingJob.getId())
				.build();

		this._pickingJob = pickingJob;
		this._lineId = pickingJobLineId;
		this._stepId = pickingJobStepId;
		this.stepPickFromKey = pickFromKey != null ? pickFromKey : PickingJobStepPickFromKey.MAIN;
		this.pickFromHUQRCode = pickFromHUQRCode;

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

		this.catchWeight = line.getCatchUomId() != null && catchWeightBD != null
				? Quantitys.of(catchWeightBD, line.getCatchUomId())
				: null;
		if (this.catchWeight != null && !this.catchWeight.isPositive())
		{
			throw new AdempiereException(NEGATIVE_CATCH_WEIGHT_ERROR_MSG);
		}

		this.isSetBestBeforeDate = isSetBestBeforeDate;
		this.bestBeforeDate = bestBeforeDate;
		this.isSetLotNo = isSetLotNo;
		this.lotNo = lotNo;

		this.isCloseTarget = isCloseTarget;

		validateQRCode(pickFromHUQRCode, line.getProductId(), catchWeightBD);
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
				// NOTE: because, in case of alternatives, we don't know which is the qty scheduled to pick
				// we cannot calculate the qtyRejected
				throw new AdempiereException(QTY_REJECTED_ALTERNATIVES_ERROR_MSG);
			}
		}
		else
		{
			return line.getQtyToPick().subtract(qtyPicked);
		}
	}

	@Nullable
	private static LocalDate computeBestBeforeDateFromChildren(final IAttributeStorage huAttributes)
	{
		final HashSet<LocalDate> childValues = new HashSet<>();
		for (final IAttributeStorage childAttributes : huAttributes.getChildAttributeStorages(true))
		{
			if (childAttributes.hasAttribute(AttributeConstants.ATTR_BestBeforeDate))
			{
				childValues.add(childAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate));
			}
		}

		return childValues.size() == 1 ? childValues.iterator().next() : null;
	}

	@Nullable
	private static String computeLotNoFromChildren(final IAttributeStorage huAttributes)
	{
		final HashSet<String> childValues = new HashSet<>();
		for (final IAttributeStorage childAttributes : huAttributes.getChildAttributeStorages(true))
		{
			if (childAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber))
			{
				childValues.add(StringUtils.trimBlankToNull(childAttributes.getValueAsString(AttributeConstants.ATTR_LotNumber)));
			}
		}

		return childValues.size() == 1 ? childValues.iterator().next() : null;
	}

	private static void validateQRCodeForLMQRCode(@NonNull final LMQRCode pickFromHUQRCode, @Nullable final BigDecimal catchWeightBD)
	{
		if (catchWeightBD == null)
		{
			throw new AdempiereException(CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG)
					.appendParametersToMessage()
					.setParameter("LMQRCode", pickFromHUQRCode);
		}

		if (pickFromHUQRCode.getWeightInKgNotNull().compareTo(catchWeightBD) != 0)
		{
			throw new AdempiereException(CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG)
					.appendParametersToMessage()
					.setParameter("pickFromHUQRCode", pickFromHUQRCode)
					.setParameter("catchWeightBD", catchWeightBD);
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
			closePickingLUTarget();
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
		final PickingJobStepId stepId = getStepId(); // IMPORTANT: don't inline this because getStepId() changes the current "_pickingJob"
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
		_pickingJob = _pickingJob.withChangedStep(getStepId(), stepMapper);
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

	private void updatePickingTarget(@NonNull final LUTUResult result)
	{
		final LUPickingTarget pickingTarget = getLUPickingTarget().orElse(null);
		if (pickingTarget != null && pickingTarget.isNewLU())
		{
			if (result.isSingleLU())
			{
				setPickingLUTarget(result.getSingleLU());
			}
		}
	}

	private void closePickingLUTarget()
	{
		if (isLineLevelPickTarget())
		{
			this._pickingJob = pickingJobService.closeLUPickingTarget(this._pickingJob, getLineId());
		}
		else
		{
			this._pickingJob = pickingJobService.closeLUPickingTarget(this._pickingJob, null);
		}
	}

	private PickingJobStepId getStepId()
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
		final HUQRCode pickFromHUQRCode = getPickFromHUQRCode();
		final HuId pickFromHUId = huQRCodesService.getHuIdByQRCode(pickFromHUQRCode);
		final LocatorId pickFromLocatorId = handlingUnitsBL.getLocatorId(pickFromHUId);

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
						.pickFromHU(HUInfo.builder()
								.id(pickFromHUId)
								.qrCode(pickFromHUQRCode)
								.build())
						.packToSpec(packToSpec)
						.build()
		);

		return newStepId;
	}

	@NonNull
	private HUQRCode getPickFromHUQRCode()
	{
		final IHUQRCode pickFromHUQRCode = Optional.ofNullable(this.pickFromHUQRCode)
				.orElseThrow(() -> new AdempiereException(NO_QR_CODE_ERROR_MSG));

		if (pickFromHUQRCode instanceof HUQRCode)
		{
			return (HUQRCode)pickFromHUQRCode;
		}
		else
		{
			final String lotNumber = pickFromHUQRCode.getLotNumber().orElseThrow(() -> new AdempiereException(L_M_QR_CODE_ERROR_MSG));

			return handlingUnitsBL.getFirstHuIdByExternalLotNo(lotNumber)
					.map(huQRCodesService::getQRCodeByHuId)
					.orElseThrow(() -> new AdempiereException(QR_CODE_EXTERNAL_LOT_ERROR_MSG)
							.appendParametersToMessage()
							.setParameter("LotNumber", lotNumber));
		}
	}

	private ShipmentScheduleId getShipmentScheduleId()
	{
		return getStepIfExists()
				.map(PickingJobStep::getShipmentScheduleId)
				.orElseGet(() -> getLine().getShipmentScheduleId());
	}

	private List<PickingJobStepPickedToHU> splitOutPickToHUs()
	{
		if (qtyToPickCUs.isZero() && !isPickWholeTU)
		{
			return ImmutableList.of();
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
		// TODO: add top level HUs from packedHUs (where isPreExistingLU=false) to picking slot queue?

		if (packedHUs.isEmpty())
		{
			throw new AdempiereException(CANNOT_PACK_ERROR_MSG, pickFromHU, packToInfo, qtyToPickCUs);
		}
		else if (packedHUs.getQtyTUs().isOne())
		{
			updateHUWeightFromCatchWeight(packedHUs.getAllTUs(), productId);
			final TU tu = packedHUs.getSingleTU();
			updateOtherHUAttributes(tu);

			final Quantity qtyPicked = isPickWholeTU ? getStorageQty(tu, productId) : qtyToPickCUs;
			addShipmentScheduleQtyPicked(tu, qtyPicked);

			return ImmutableList.of(toSinglePickingJobStepPickedToHU(tu, qtyPicked, catchWeight, pickFrom));
		}
		else
		{
			updateHUWeightFromCatchWeight(packedHUs.getAllTUs(), productId);
			updateOtherHUAttributes(packedHUs);

			final IHUStorageFactory huStorageFactory = HUContextHolder.getCurrent().getHUStorageFactory();
			final I_C_UOM uom = qtyToPickCUs.getUOM();
			final ImmutableList.Builder<PickingJobStepPickedToHU> result = ImmutableList.builder();
			for (final TU tu : packedHUs.getAllTUs())
			{

				final Quantity qtyPicked = huStorageFactory.getStorage(tu.toHU()).getQuantity(productId, uom);
				addShipmentScheduleQtyPicked(tu, qtyPicked);

				result.addAll(toPickingJobStepPickedToHU(tu, qtyPicked, pickFrom));
			}

			return result.build();
		}
	}

	private void updateHUWeightFromCatchWeight(final TUsList tuList, final ProductId productId)
	{
		if (catchWeight == null)
		{
			return;
		}

		final PackedHUWeightNetUpdater weightUpdater = new PackedHUWeightNetUpdater(uomConversionBL, HUContextHolder.getCurrent(), productId, catchWeight);
		weightUpdater.updatePackToHUs(tuList.toHURecords());
	}

	private void updateOtherHUAttributes(final LUTUResult result)
	{
		if (!isUpdateAttributes())
		{
			return;
		}

		result.getLus().forEach(this::updateOtherAttributes);
		result.getTopLevelTUs().forEach(this::updateOtherHUAttributes);
	}

	private void updateOtherAttributes(final LU lu)
	{
		lu.getTus().forEach(this::updateOtherHUAttributes);

		updateOtherHUAttributes(lu.toHU(), lu.isPreExistingLU());
	}

	private void updateOtherHUAttributes(final TU tu)
	{
		if (!isUpdateAttributes())
		{
			return;
		}

		updateOtherHUAttributes(tu.toHU(), false);
	}

	private void updateOtherHUAttributes(final I_M_HU hu, final boolean updateFromChildren)
	{
		if (!isUpdateAttributes())
		{
			return;
		}

		final IAttributeStorage huAttributes = HUContextHolder.getCurrent().getHUAttributeStorageFactory().getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		if (isSetBestBeforeDate)
		{
			if (updateFromChildren)
			{
				huAttributes.setValueNoPropagate(AttributeConstants.ATTR_BestBeforeDate, computeBestBeforeDateFromChildren(huAttributes));
			}
			else
			{
				huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
			}
		}
		if (isSetLotNo)
		{
			if (updateFromChildren)
			{
				huAttributes.setValueNoPropagate(AttributeConstants.ATTR_LotNumber, computeLotNoFromChildren(huAttributes));
			}
			else
			{
				huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNo);
			}
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean isUpdateAttributes()
	{
		return isSetBestBeforeDate || isSetLotNo;
	}

	private void addShipmentScheduleQtyPicked(@NonNull final TU tu, @NonNull final Quantity qtyPicked)
	{
		final IMutableHUContext huContext = HUContextHolder.getCurrent();
		final ShipmentScheduleInfo shipmentScheduleInfo = getShipmentScheduleInfo();
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleInfo.getRecord();
		final ProductId productId = shipmentScheduleInfo.getProductId();
		final boolean anonymousHuPickedOnTheFly = false;

		shipmentScheduleBL.addQtyPickedAndUpdateHU(
				shipmentSchedule,
				CatchWeightHelper.extractQtys(
						huContext,
						productId,
						qtyPicked,
						tu.toHU()),
				tu.toHU(),
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
			throw new AdempiereException(NOT_ENOUGH_TUS_ERROR_MSG)
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
						.catchWeight(catchWeight)
						.documentRef(getLineId().toTableRecordReference())
						.checkIfAlreadyPacked(checkIfAlreadyPacked)
						.createInventoryForMissingQty(createInventoryForMissingQty)
						.build()
		);
	}

	private void validateQRCode(@Nullable final IHUQRCode pickFromHUQRCode, @NonNull final ProductId productId, @Nullable final BigDecimal catchWeightBD)
	{
		if (pickFromHUQRCode instanceof LMQRCode)
		{
			validateQRCodeForLMQRCode((LMQRCode)pickFromHUQRCode, catchWeightBD);
		}
		else if (pickFromHUQRCode instanceof GS1HUQRCode)
		{
			validateQRCodeForGS1((GS1HUQRCode)pickFromHUQRCode, productId, catchWeightBD);
		}
		else if (pickFromHUQRCode instanceof EAN13HUQRCode)
		{
			final EAN13 ean13 = ((EAN13HUQRCode)pickFromHUQRCode).unbox();
			validateQRCodeForEAN13(ean13, productId, catchWeightBD);
		}
	}

	private void validateQRCodeForGS1(@NonNull final GS1HUQRCode pickFromHUQRCode, @NonNull final ProductId productId, @Nullable final BigDecimal catchWeightBD)
	{
		final GTIN gtin = pickFromHUQRCode.getGTIN().orElse(null);
		if (gtin != null)
		{
			final ProductId gs1ProductId = productBL.getProductIdByGTINNotNull(gtin, ClientId.METASFRESH);
			if (!ProductId.equals(productId, gs1ProductId))
			{
				throw new AdempiereException(QR_CODE_PRODUCT_ERROR_MSG)
						.setParameter("gtin", gtin)
						.setParameter("expected", productId)
						.setParameter("actual", gs1ProductId);
			}
		}

		if (catchWeightBD != null)
		{
			final BigDecimal gs1Weight = pickFromHUQRCode.getWeightInKg().orElse(null);
			if (gs1Weight == null)
			{
				return;
			}

			if (gs1Weight.compareTo(catchWeightBD) != 0)
			{
				throw new AdempiereException(CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG)
						.appendParametersToMessage()
						.setParameter("pickFromHUQRCode", pickFromHUQRCode)
						.setParameter("catchWeightBD", catchWeightBD);
			}
		}
	}

	private void validateQRCodeForEAN13(
			@NonNull final EAN13 ean13,
			@NonNull final ProductId expectedProductId,
			@Nullable final BigDecimal expectedCatchWeightBD)
	{
		final String expectedProductNo = productBL.getProductValue(expectedProductId);
		final EAN13ProductCode ean13ProductNo = ean13.getProductNo();
		final BPartnerId customerId = getShipmentScheduleInfo().getBpartnerId();

		if (!productBL.isValidEAN13Product(ean13, expectedProductId, customerId))
		{
			throw new AdempiereException(QR_CODE_PRODUCT_ERROR_MSG)
					.setParameter("ean13ProductNo", ean13ProductNo)
					.setParameter("expectedProductNo", expectedProductNo)
					.setParameter("expectedProductId", expectedProductId);
		}

		if (expectedCatchWeightBD != null)
		{
			final BigDecimal ean13Weight = ean13.getWeightInKg().orElse(null);
			if (ean13Weight == null)
			{
				return;
			}

			if (ean13Weight.compareTo(expectedCatchWeightBD) != 0)
			{
				throw new AdempiereException(CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG)
						.appendParametersToMessage()
						.setParameter("ean13", ean13)
						.setParameter("expectedCatchWeightBD", expectedCatchWeightBD);
			}
		}
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
		final BPartnerId bpartnerId = shipmentScheduleInfo.getBpartnerId();
		final PickFromHUsGetRequest request = PickFromHUsGetRequest.builder()
				.pickFromLocatorIds(warehouseBL.getLocatorIdsOfTheSamePickingGroup(warehouseId))
				.partnerId(bpartnerId)
				.productId(shipmentScheduleInfo.getProductId())
				.asiId(shipmentScheduleInfo.getAsiId())
				.bestBeforePolicy(shipmentScheduleInfo.getBestBeforePolicy().orElseGet(() -> bPartnerBL.getBestBeforePolicy(bpartnerId)))
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
			return huQRCodesService.getHuIdByQRCode(getPickFromHUQRCode());
		}
	}

	private ShipmentScheduleInfo getShipmentScheduleInfo()
	{
		return shipmentSchedulesCache.computeIfAbsent(getShipmentScheduleId(), this::retrieveShipmentScheduleInfo);
	}

	private ShipmentScheduleInfo retrieveShipmentScheduleInfo(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(shipmentScheduleId);

		return ShipmentScheduleInfo.builder()
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

	private HUQRCode getQRCode(@NonNull final LU lu)
	{
		return huQRCodesService.getQRCodeByHuId(lu.getId());
	}

	//
	//
	// -------------------------------------
	//
	//

	@Value
	@Builder
	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	private static class ShipmentScheduleInfo
	{
		@NonNull
		WarehouseId warehouseId;
		@NonNull
		BPartnerId bpartnerId;
		@NonNull
		Optional<OrderLineId> salesOrderLineId;

		@NonNull
		ProductId productId;
		@NonNull
		AttributeSetInstanceId asiId;
		@NonNull
		Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy;

		@NonNull
		I_M_ShipmentSchedule record;
	}
}
