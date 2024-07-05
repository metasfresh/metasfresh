package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.LUExtractTUsRequest;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
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
import de.metas.handlingunits.picking.job.model.HUInfo;
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
import de.metas.handlingunits.picking.job.model.PickingTarget;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsGetRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
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
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
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
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class PickingJobPickCommand
{
	//
	// Services
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final IHUShipmentScheduleBL shipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final PackToHUsProducer packToHUsProducer;
	@NonNull private final HUReservationService huReservationService;
	@NonNull private final PickingConfigRepositoryV2 pickingConfigRepo;

	//
	// Params
	@NonNull private final PickingJobLineId _lineId;
	@NonNull private final PickingUnit pickingUnit;
	@NonNull private final PickingJobStepPickFromKey stepPickFromKey;
	@Nullable private final IHUQRCode pickFromHUQRCode;
	@NonNull private final Quantity qtyToPickCUs;
	@Nullable private final QtyTU qtyToPickTUs;
	@Nullable private final QtyRejectedWithReason qtyRejectedCUs;
	@Nullable private final Quantity catchWeight;
	private final boolean isPickWholeTU;
	private final boolean checkIfAlreadyPacked;
	private final boolean createInventoryForMissingQty;

	private final boolean isSetBestBeforeDate;
	private final LocalDate bestBeforeDate;

	private final boolean isSetLotNo;
	private final String lotNo;

	private final boolean isCloseTarget;

	//
	// State
	@NonNull private PickingJob _pickingJob;
	@Nullable private PickingJobStepId _stepId;
	@NonNull private final HashMap<ShipmentScheduleId, ShipmentScheduleInfo> shipmentSchedulesCache = new HashMap<>();

	private final static AdMessageKey HU_CANNOT_BE_PICKED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG");

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
		validateCatchWeight(catchWeightBD, pickFromHUQRCode);

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
			this.qtyToPickTUs = QtyTU.ofBigDecimal(qtyToPickBD);
			final HUPIItemProduct packingInfo = line.getPackingInfo();
			this.qtyToPickCUs = packingInfo.computeQtyCUsOfQtyTUs(this.qtyToPickTUs);

			if (qtyRejectedReasonCode != null)
			{
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
			throw new AdempiereException("Picking unit not supported: " + pickingUnit);
		}

		this.catchWeight = line.getCatchUomId() != null && catchWeightBD != null
				? Quantitys.create(catchWeightBD, line.getCatchUomId())
				: null;
		if (this.catchWeight != null && !this.catchWeight.isPositive())
		{
			throw new AdempiereException("Catch Weight shall be positive");
		}

		this.isSetBestBeforeDate = isSetBestBeforeDate;
		this.bestBeforeDate = bestBeforeDate;
		this.isSetLotNo = isSetLotNo;
		this.lotNo = lotNo;

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
				// NOTE: because, in case of alternatives, we don't know which is the qty scheduled to pick
				// we cannot calculate the qtyRejected
				throw new AdempiereException("Cannot calculate QtyRejected in case of alternatives");
			}
		}
		else
		{
			return line.getQtyToPick().subtract(qtyPicked);
		}
	}

	public PickingJob execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		_pickingJob.assertNotProcessed();

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

	private PickingJob getPickingJob() {return _pickingJob;}

	private PickingJobStep getStep()
	{
		final PickingJobStepId stepId = getStepId(); // IMPORTANT: don't inline this because getStepId() changes the current "_pickingJob"
		return _pickingJob.getStepById(stepId);
	}

	private Optional<PickingJobStep> getStepIfExists()
	{
		return getStepIdIfExists().map(_pickingJob::getStepById);
	}

	private PickingJobLineId getLineId() {return _lineId;}

	private PickingJobLine getLine() {return _pickingJob.getLineById(getLineId());}

	private void addStep(@NonNull final PickingJob.AddStepRequest request)
	{
		_pickingJob = _pickingJob.withNewStep(request);
	}

	private void changeStep(@NonNull final UnaryOperator<PickingJobStep> stepMapper)
	{
		_pickingJob = _pickingJob.withChangedStep(getStepId(), stepMapper);
	}

	private Optional<PickingTarget> getPickingLUTarget()
	{
		return _pickingJob.getPickTarget();
	}

	private void setPickingLUTarget(@NonNull final PickingTarget pickingLUTarget)
	{
		_pickingJob = _pickingJob.withPickTarget(pickingLUTarget);
	}

	private void setPickingLUTarget(@NonNull final LUTUResult.LU lu)
	{
		final HuId luId = lu.getId();
		final HUQRCode qrCode = getQRCode(lu);
		setPickingLUTarget(PickingTarget.ofExistingHU(luId, qrCode));
	}

	private void updatePickingLUTarget(@NonNull final LUTUResult result)
	{
		final PickingTarget pickingTarget = getPickingLUTarget().orElse(null);
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
		this._pickingJob = pickingJobService.closePickTarget(this._pickingJob);
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
		final IHUQRCode pickFromHUQRCode = Check.assumeNotNull(this.pickFromHUQRCode, "HU QR code shall be provided");

		if (pickFromHUQRCode instanceof HUQRCode)
		{
			return (HUQRCode)pickFromHUQRCode;
		}
		else if (pickFromHUQRCode instanceof LMQRCode)
		{
			final LMQRCode lmQRCode = (LMQRCode)pickFromHUQRCode;
			final String lotNumber = lmQRCode.getLotNumber();
			if (lotNumber == null)
			{
				throw new AdempiereException("L+M QR code does not contain external lot number");
			}

			return handlingUnitsBL.getFirstHuIdByExternalLotNo(lotNumber)
					.map(huQRCodesService::getQRCodeByHuId)
					.orElseThrow(() -> new AdempiereException("No HU associated with external lot number: " + lotNumber));
		}
		else
		{
			throw new AdempiereException("HU QR code not supported: " + pickFromHUQRCode);
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
				step.getPackToSpec(),
				getPickingLUTarget().orElse(null),
				getPickingJob().getDeliveryBPLocationId(),
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

		updatePickingLUTarget(packedHUs);

		if (packedHUs.isEmpty())
		{
			throw new AdempiereException("Cannot pack to HUs from " + pickFromHU + " using " + packToInfo + ", qtyToPick=" + qtyToPickCUs);
		}
		else if (packedHUs.getQtyTUs().isOne())
		{
			final LUTUResult.TU tu = packedHUs.getSingleTU();
			updateHUWeightFromCatchWeight(tu, productId);
			updateOtherHUAttributes(tu);

			final Quantity qtyPicked = isPickWholeTU ? getStorageQty(tu, productId) : qtyToPickCUs;

			addShipmentScheduleQtyPicked(tu, qtyPicked);

			return ImmutableList.of(toSinglePickingJobStepPickedToHU(tu, qtyPicked, catchWeight, pickFrom));
		}
		else
		{
			if (catchWeight != null)
			{
				throw new AdempiereException("Cannot apply catch weight when receiving more than one HU");
			}

			final IHUStorageFactory huStorageFactory = HUContextHolder.getCurrent().getHUStorageFactory();
			final I_C_UOM uom = qtyToPickCUs.getUOM();
			final ImmutableList.Builder<PickingJobStepPickedToHU> result = ImmutableList.builder();
			for (final LUTUResult.TU tu : packedHUs.getAllTUs())
			{
				updateOtherHUAttributes(tu);

				final Quantity qtyPicked = huStorageFactory.getStorage(tu.toHU()).getQuantity(productId, uom);

				addShipmentScheduleQtyPicked(tu, qtyPicked);

				result.addAll(toPickingJobStepPickedToHU(tu, qtyPicked, pickFrom));
			}

			return result.build();
		}
	}

	private void updateHUWeightFromCatchWeight(final LUTUResult.TU tu, final ProductId productId)
	{
		if (catchWeight == null)
		{
			return;
		}

		final PackedHUWeightNetUpdater weightUpdater = new PackedHUWeightNetUpdater(uomConversionBL, HUContextHolder.getCurrent(), productId, catchWeight);
		weightUpdater.updatePackToHU(tu.toHU());
	}

	private void updateOtherHUAttributes(final LUTUResult.TU tu)
	{
		if (!isSetBestBeforeDate && !isSetLotNo)
		{
			return;
		}

		final IAttributeStorage huAttributes = HUContextHolder.getCurrent().getHUAttributeStorageFactory().getAttributeStorage(tu.toHU());
		huAttributes.setSaveOnChange(true);

		if (isSetBestBeforeDate)
		{
			huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
		}
		if (isSetLotNo)
		{
			huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNo);
		}
	}

	private void addShipmentScheduleQtyPicked(@NonNull LUTUResult.TU tu, @NonNull final Quantity qtyPicked)
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
			@NonNull final LUTUResult.TU tu,
			@NonNull final Quantity qtyPicked,
			@Nullable final Quantity catchWeight,
			@NonNull final PickingJobStepPickFrom pickFrom)
	{
		return PickingJobStepPickedToHU.builder()
				.pickFromHUId(pickFrom.getPickFromHUId())
				.actualPickedHU(getSingleTUInfo(tu))
				.qtyPicked(qtyPicked)
				.catchWeight(catchWeight)
				.build();
	}

	private List<PickingJobStepPickedToHU> toPickingJobStepPickedToHU(
			@NonNull final LUTUResult.TU tu,
			@NonNull final Quantity qtyPicked,
			@NonNull final PickingJobStepPickFrom pickFrom)
	{

		final List<HUQRCode> huQRCodes = huQRCodesService.getOrCreateQRCodesByHuId(tu.getId());
		if (huQRCodes.size() != tu.getQtyTU().toInt())
		{
			throw new AdempiereException("Expected " + tu.getQtyTU() + " QR Codes but got only " + huQRCodes.size());
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

		final PickingTarget targetLU = getPickingLUTarget().orElse(null);
		final LUTUResult result;

		if (handlingUnitsBL.isLoadingUnit(pickFromHU))
		{
			if (targetLU == null)
			{
				final TUsList topLevelTUs = huTransformService.luExtractTUs(LUExtractTUsRequest.builder()
						.sourceLU(pickFromHU)
						.qtyTU(qtyToPickTUs)
						.keepSourceLuAsParent(false)
						.build());
				InterfaceWrapperHelper.setThreadInheritedTrxName(topLevelTUs.toHURecords()); // workaround because the returned HUs have trxName=null
				result = LUTUResult.ofTopLevelTUs(topLevelTUs);
			}
			else
			{
				throw new UnsupportedOperationException(); // TODO impl
			}
		}
		else if (qtyToPickTUs.isOne() && targetLU == null && handlingUnitsBL.isTransportUnit(pickFromHU))
		{
			final I_M_HU packedTU = huTransformService.splitOutTURecord(pickFromHU);
			result = LUTUResult.ofSingleTopLevelTU(packedTU);
		}
		else
		{
			if (targetLU == null)
			{
				final TUsList topLevelTUs = huTransformService.husToNewTUs(
						HUTransformService.HUsToNewTUsRequest.builder()
								.sourceHU(pickFromHU)
								.qtyTU(qtyToPickTUs)
								.expectedProductId(productId)
								.build());
				result = LUTUResult.ofTopLevelTUs(topLevelTUs);
			}
			else if (targetLU.isExistingLU())
			{
				final I_M_HU lu = handlingUnitsBL.getById(targetLU.getLuId());
				result = huTransformService.tuToExistingLU(pickFromHU, qtyToPickTUs, lu);
			}
			else if (targetLU.isNewLU())
			{
				result = huTransformService.tuToNewLU(pickFromHU, qtyToPickTUs, targetLU.getLuPIIdNotNull());
			}
			else
			{
				throw new AdempiereException("Unknown target LU: " + targetLU);
			}
		}

		if (result.getQtyTUs().compareTo(qtyToPickTUs) != 0)
		{
			throw new AdempiereException("Not enough TUs found") // TODO trl
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

	private static void validateCatchWeight(final @Nullable BigDecimal catchWeightBD, @Nullable final IHUQRCode pickFromHUQRCode)
	{
		if (pickFromHUQRCode instanceof LMQRCode)
		{
			validateCatchWeightForLMQRCode(catchWeightBD, (LMQRCode)pickFromHUQRCode);
		}
	}

	private static void validateCatchWeightForLMQRCode(final @Nullable BigDecimal catchWeightBD, @NonNull final LMQRCode pickFromHUQRCode)
	{
		if (catchWeightBD == null)
		{
			throw new AdempiereException("catchWeightBD must be present when picking via LMQRCode")
					.appendParametersToMessage()
					.setParameter("LMQRCode", pickFromHUQRCode);
		}

		if (pickFromHUQRCode.getWeightInKg().compareTo(catchWeightBD) != 0)
		{
			throw new AdempiereException("catchWeightBD must match the LMQRCode.Weight")
					.appendParametersToMessage()
					.setParameter("LMQRCode", pickFromHUQRCode)
					.setParameter("catchWeightBD", catchWeightBD);
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

	private HUInfo getSingleTUInfo(@NonNull final LUTUResult.TU tu)
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

	public Quantity getStorageQty(@NonNull final LUTUResult.TU tu, @NonNull final ProductId productId)
	{
		final IHUStorageFactory huStorageFactory = HUContextHolder.getCurrent().getHUStorageFactory();
		return huStorageFactory.getStorage(tu.toHU()).getQuantity(productId).orElseThrow(() -> new AdempiereException("No qty found for " + tu + ", " + productId));
	}

	private HUQRCode getQRCode(@NonNull final LUTUResult.LU lu)
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
		@NonNull WarehouseId warehouseId;
		@NonNull BPartnerId bpartnerId;
		@NonNull Optional<OrderLineId> salesOrderLineId;

		@NonNull ProductId productId;
		@NonNull AttributeSetInstanceId asiId;
		@NonNull Optional<ShipmentAllocationBestBeforePolicy> bestBeforePolicy;

		@NonNull I_M_ShipmentSchedule record;
	}
}
