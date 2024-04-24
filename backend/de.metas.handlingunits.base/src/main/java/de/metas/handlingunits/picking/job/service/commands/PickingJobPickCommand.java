package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.picking.candidate.commands.PackedHUWeightNetUpdater;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesRequest;
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
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHU;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsGetRequest;
import de.metas.handlingunits.picking.plan.generator.pickFromHUs.PickFromHUsSupplier;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PickingJobPickCommand
{
	private final static AdMessageKey HU_CANNOT_BE_PICKED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.HU_CANNOT_BE_PICKED_ERROR_MSG");
	//
	// Services
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final PackToHUsProducer packToHUsProducer;
	@NonNull private final HUReservationService huReservationService;
	@NonNull private final PickingConfigRepositoryV2 pickingConfigRepo;

	//
	// Sysconfigs
	private static final String SYSCONFIG_PickCUsFromCUs = "PickingJobPickCommand.PickCUsFromCUs";

	//
	// Params
	@NonNull private final PickingJobLineId lineId;
	@NonNull private final PickingUnit pickingUnit;
	@NonNull private final PickingJobStepPickFromKey stepPickFromKey;
	@Nullable private final IHUQRCode pickFromHUQRCode;
	@NonNull private final Quantity qtyToPickCUs;
	@Nullable private final QtyTU qtyToPickTUs;
	@Nullable private final QtyRejectedWithReason qtyRejectedCUs;
	@Nullable private final Quantity catchWeight;
	private final boolean isPickWholeTU;
	private final boolean isPickCUsFromCUs;
	private final boolean checkIfAlreadyPacked;
	private final boolean createInventoryForMissingQty;

	private final boolean isSetBestBeforeDate;
	private final LocalDate bestBeforeDate;

	private final boolean isSetLotNo;
	private final String lotNo;

	//
	// State
	@NonNull private PickingJob pickingJob;
	@Nullable private PickingJobStepId stepId;

	@Builder
	private PickingJobPickCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingCandidateService pickingCandidateService,
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
			final @Nullable String lotNo)
	{
		Check.assumeGreaterOrEqualToZero(qtyToPickBD, "qtyToPickBD");
		validateCatchWeight(catchWeightBD, pickFromHUQRCode);

		this.pickingJobRepository = pickingJobRepository;
		this.pickingCandidateService = pickingCandidateService;
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

		this.pickingJob = pickingJob;
		this.lineId = pickingJobLineId;
		this.stepId = pickingJobStepId;
		this.stepPickFromKey = pickFromKey != null ? pickFromKey : PickingJobStepPickFromKey.MAIN;
		this.pickFromHUQRCode = pickFromHUQRCode;

		final PickingJobLine line = pickingJob.getLineById(lineId);
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

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		// NOTE: because picking CUs into TU is not performant at all and because this feature was not requested in this issue,
		// we decided to turn it off and pick CUs to CUs and later, on shipment, to aggregate CUs to TU (and TUs to LU).
		this.isPickCUsFromCUs = sysConfigBL.getBooleanValue(SYSCONFIG_PickCUsFromCUs, true);
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

	public void execute()
	{
		pickingJob.assertNotProcessed();
		trxManager.runInThreadInheritedTrx(this::executeInTrx);
	}

	private void executeInTrx()
	{
		validatePickedHU();

		createStepIfNeeded();

		final ImmutableList<PickedToHU> pickedHUs = createAndProcessPickingCandidate();

		final PickingJobStepId stepId = Check.assumeNotNull(this.stepId, "step exists");
		pickingJob = pickingJob.withChangedStep(
				stepId,
				step -> updateStepFromPickingCandidate(step, pickedHUs));

		pickingJobRepository.save(pickingJob);
	}

	private void createStepIfNeeded()
	{
		if (stepId != null)
		{
			return;
		}

		final PickingJobStepId newStepId = pickingJobRepository.newPickingJobStepId();

		final PickingJobLine line = pickingJob.getLineById(lineId);
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

		pickingJob = pickingJob.withNewStep(
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

		this.stepId = newStepId;
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

	private ImmutableList<PickedToHU> createAndProcessPickingCandidate()
	{
		final ImmutableList<PickedToHU> pickedToHUs = splitOutPickToHUs();
		if (pickedToHUs.isEmpty())
		{
			return ImmutableList.of();
		}

		final ShipmentScheduleId shipmentScheduleId = getShipmentScheduleId();
		final PickingSlotId pickingSlotId = pickingJob.getPickingSlotId().orElse(null);
		for (final PickedToHU pickedToHU : pickedToHUs)
		{
			final PickHUResult pickResult = pickingCandidateService.pickHU(PickRequest.builder()
					.shipmentScheduleId(shipmentScheduleId)
					.pickFrom(PickFrom.ofHuId(pickedToHU.getActuallyPickedToHUId()))
					.packToSpec(pickedToHU.getPickToSpecUsed())
					.qtyToPick(pickedToHU.getQtyPicked())
					.pickingSlotId(pickingSlotId)
					.autoReview(true)
					.build());

			pickedToHU.setPickingCandidateId(pickResult.getPickingCandidateId());
		}

		pickingCandidateService.process(ProcessPickingCandidatesRequest.builder()
				.pickingCandidateIds(extractPickingCandidateIds(pickedToHUs))
				.alwaysPackEachCandidateInItsOwnHU(true)
				.build());

		return pickedToHUs;
	}

	private ShipmentScheduleId getShipmentScheduleId()
	{
		if (stepId != null)
		{
			return pickingJob.getStepById(stepId).getShipmentScheduleId();
		}
		else
		{
			return Check.assumeNotNull(pickingJob.getLineById(lineId).getShipmentScheduleId(), "line has shipment schedule set");
		}
	}

	private ImmutableList<PickedToHU> splitOutPickToHUs()
	{
		if (qtyToPickCUs.isZero())
		{
			return ImmutableList.of();
		}

		final PickingJobStepId stepId = Check.assumeNotNull(this.stepId, "step exists");
		final PickingJobStep step = pickingJob.getStepById(stepId);
		step.getPickFrom(stepPickFromKey).assertNotPicked();

		final ProductId productId = step.getProductId();
		final PickingJobStepPickFrom pickFrom = step.getPickFrom(stepPickFromKey);
		final HUInfo pickFromHU = pickFrom.getPickFromHU();
		final LocatorId pickFromLocatorId = pickFrom.getPickFromLocatorId();

		final PackToHUsProducer.PackToInfo packToInfo = packToHUsProducer.extractPackToInfo(
				isPickCUsFromCUs ? PackToSpec.VIRTUAL : step.getPackToSpec(),
				pickingJob.getDeliveryBPLocationId(),
				pickFromLocatorId);

		trxManager.assertThreadInheritedTrxExists();
		final IHUContext huContext = handlingUnitsBL.createMutableHUContextForProcessing();
		final List<I_M_HU> packedHUs;
		if (pickingUnit.isTU())
		{
			final QtyTU qtyToPickTUs = Check.assumeNotNull(this.qtyToPickTUs, "qtyToPickTUs is set");
			Check.assume(qtyToPickTUs.isPositive(), "qtyToPickTUs is positive");

			final I_M_HU pickFromHURecord = handlingUnitsBL.getById(pickFromHU.getId());
			if (handlingUnitsBL.isVirtual(pickFromHURecord))
			{
				packedHUs = pickCUsAndPackTo(productId, huContext, pickFromHU.getId(), packToInfo);
			}
			else
			{
				packedHUs = pickWholeTUs(productId, huContext, pickFromHURecord, qtyToPickTUs);
			}
		}
		else
		{
			pickingUnit.assertIsCU();

			if (isPickWholeTU)
			{
				final I_M_HU pickFromHURecord = handlingUnitsBL.getById(pickFromHU.getId());
				packedHUs = pickWholeTUs(productId, huContext, pickFromHURecord, QtyTU.ONE);
			}
			else
			{
				packedHUs = pickCUsAndPackTo(productId, huContext, pickFromHU.getId(), packToInfo);
			}
		}

		if (packedHUs.isEmpty())
		{
			throw new AdempiereException("Cannot pack to HUs from " + pickFromHU + " using " + packToInfo + ", qtyToPick=" + qtyToPickCUs);
		}
		else if (packedHUs.size() == 1)
		{
			final I_M_HU packedHU = packedHUs.get(0);
			updateHUWeightFromCatchWeight(packedHU, huContext, productId);
			updateOtherHUAttributes(packedHU, huContext);

			final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
			final Quantity pickedQty = isPickWholeTU
					? huStorageFactory.getStorage(packedHU).getQuantity(productId).orElseThrow(() -> new AdempiereException("Qty not found!"))
					: qtyToPickCUs;

			return ImmutableList.of(PickedToHU.builder()
					.pickedFromHU(pickFromHU)
					.pickFromLocatorId(pickFromLocatorId)
					.actuallyPickedToHUId(HuId.ofRepoId(packedHU.getM_HU_ID()))
					.pickToSpecUsed(PackToSpec.ofGenericPackingInstructionsId(handlingUnitsBL.getEffectivePackingInstructionsId(packedHU)))
					.qtyPicked(pickedQty)
					.build());
		}
		else
		{
			if (catchWeight != null)
			{
				throw new AdempiereException("Cannot apply catch weight when receiving more than one HU");
			}

			final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
			final ImmutableList.Builder<PickedToHU> result = ImmutableList.builder();
			for (final I_M_HU packedHU : packedHUs)
			{
				updateOtherHUAttributes(packedHU, huContext);

				final Quantity qtyPicked = huStorageFactory.getStorage(packedHU).getQuantity(productId, qtyToPickCUs.getUOM());
				result.add(PickedToHU.builder()
						.pickedFromHU(pickFromHU)
						.pickFromLocatorId(pickFromLocatorId)
						.actuallyPickedToHUId(HuId.ofRepoId(packedHU.getM_HU_ID()))
						.pickToSpecUsed(PackToSpec.ofGenericPackingInstructionsId(handlingUnitsBL.getEffectivePackingInstructionsId(packedHU)))
						.qtyPicked(qtyPicked)
						.build());
			}

			return result.build();
		}
	}

	private void updateHUWeightFromCatchWeight(final I_M_HU hu, final IHUContext huContext, final ProductId productId)
	{
		final PackedHUWeightNetUpdater weightUpdater = new PackedHUWeightNetUpdater(uomConversionBL, huContext, productId, catchWeight);
		weightUpdater.updatePackToHU(hu);
	}

	private void updateOtherHUAttributes(final I_M_HU hu, final IHUContext huContext)
	{
		if (!isSetBestBeforeDate && !isSetLotNo)
		{
			return;
		}

		final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
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

	private PickingJobStep updateStepFromPickingCandidate(
			@NonNull final PickingJobStep step,
			@NonNull final ImmutableList<PickedToHU> pickedHUs)
	{
		final PickingJobStepPickedTo picked = toPickingJobStepPickedTo(pickedHUs, step.getProductId());
		return step.reduceWithPickedEvent(stepPickFromKey, picked);
	}

	private PickingJobStepPickedTo toPickingJobStepPickedTo(@NonNull final ImmutableList<PickedToHU> pickedHUs, @NonNull final ProductId pickedProductId)
	{
		return PickingJobStepPickedTo.builder()
				.qtyRejected(qtyRejectedCUs)
				.actualPickedHUs(pickedHUs.stream()
						.map(PickingJobPickCommand::toPickingJobStepPickedToHU)
						.collect(ImmutableList.toImmutableList())
				)
				.productId(pickedProductId)
				.build();
	}

	private List<I_M_HU> pickWholeTUs(
			@NonNull final ProductId productId,
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU pickFromHU,
			@NonNull final QtyTU qtyToPickTUs)
	{
		final HUTransformService huTransformService = HUTransformService.newInstance(huContext);

		//final I_M_HU pickFromHU = handlingUnitsBL.getById(pickFromHUId);
		final List<I_M_HU> packedHUs;
		if (qtyToPickTUs.isOne() && HUType.TransportUnit == handlingUnitsBL.getHUUnitType(pickFromHU))
		{
			final I_M_HU packedHU = huTransformService.splitOutTURecord(pickFromHU);
			packedHUs = ImmutableList.of(packedHU);
		}
		else
		{
			packedHUs = huTransformService.husToNewTUs(
					HUTransformService.HUsToNewTUsRequest.builder()
							.sourceHU(pickFromHU)
							.qtyTU(qtyToPickTUs)
							.expectedProductId(productId)
							.build());
		}

		if (packedHUs.size() != qtyToPickTUs.toInt())
		{
			throw new AdempiereException("Not enough TUs found") // TODO trl
					.setParameter("qtyToPickTUs", qtyToPickTUs)
					.setParameter("packedHUs.size", packedHUs.size())
					.setParameter("packedHUs", packedHUs);
		}

		handlingUnitsBL.setHUStatus(packedHUs, huContext, X_M_HU.HUSTATUS_Picked);

		return packedHUs;
	}

	private List<I_M_HU> pickCUsAndPackTo(
			@NonNull final ProductId productId,
			@NonNull final IHUContext huContext,
			@NonNull final HuId pickFromHUId,
			@NonNull final PackToHUsProducer.PackToInfo packToInfo)
	{
		return packToHUsProducer.packToHU(
				huContext,
				pickFromHUId,
				packToInfo,
				productId,
				qtyToPickCUs,
				catchWeight,
				lineId.toTableRecordReference(),
				checkIfAlreadyPacked,
				createInventoryForMissingQty);
	}

	private static PickingJobStepPickedToHU toPickingJobStepPickedToHU(final PickedToHU pickedHU)
	{
		return PickingJobStepPickedToHU.builder()
				.pickFromHUId(pickedHU.getPickedFromHU().getId())
				.actualPickedHUId(pickedHU.getActuallyPickedToHUId())
				.qtyPicked(pickedHU.getQtyPicked())
				.pickingCandidateId(Objects.requireNonNull(pickedHU.getPickingCandidateId()))
				.build();
	}

	private static ImmutableSet<PickingCandidateId> extractPickingCandidateIds(final ImmutableList<PickedToHU> pickedToHUs)
	{
		return pickedToHUs
				.stream()
				.map(PickedToHU::getPickingCandidateId)
				.peek(Objects::requireNonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static void validateCatchWeight(final @Nullable BigDecimal catchWeightBD, @Nullable final IHUQRCode pickFromHUQRCode)
	{
		if (!(pickFromHUQRCode instanceof LMQRCode))
		{
			return;
		}

		if (catchWeightBD == null)
		{
			throw new AdempiereException("catchWeightBD must be present when picking via LMQRCode")
					.appendParametersToMessage()
					.setParameter("LMQRCode", pickFromHUQRCode)
					.setParameter("catchWeightBD", catchWeightBD);
		}

		if (((LMQRCode)pickFromHUQRCode).getWeightInKg().compareTo(catchWeightBD) != 0)
		{
			throw new AdempiereException("catchWeightBD must match the LMQRCode.Weight")
					.appendParametersToMessage()
					.setParameter("LMQRCode", pickFromHUQRCode)
					.setParameter("catchWeightBD", catchWeightBD);
		}
	}

	private void validatePickedHU()
	{
		final HuId huIdToBePicked = getHuIdToBePicked();

		// Accept destroyed HUs because in case of createInventoryForMissingQty we will create a new HU out of the blue
		final boolean isDestroyed = handlingUnitsBL.isDestroyed(handlingUnitsDAO.getById(huIdToBePicked));
		if (isDestroyed)
		{
			return;
		}

		final PickFromHUsSupplier pickFromHUsSupplier = PickFromHUsSupplier.builder()
				.huReservationService(huReservationService)
				.considerAttributes(pickingConfigRepo.getPickingConfig().isConsiderAttributes())
				.build();

		final ImmutableList<PickFromHU> pickFromHUS = pickFromHUsSupplier.getEligiblePickFromHUs(getPickFromHUValidateRequest(huIdToBePicked));
		if (pickFromHUS.isEmpty())
		{
			throw new AdempiereException(HU_CANNOT_BE_PICKED_ERROR_MSG)
					.setParameter("huIdToBePicked", huIdToBePicked)
					.markAsUserValidationError();
		}
	}

	@NonNull
	private HuId getHuIdToBePicked()
	{
		if (stepId != null)
		{
			final PickingJobStep step = pickingJob.getStepById(stepId);
			step.getPickFrom(stepPickFromKey).assertNotPicked();
			final PickingJobStepPickFrom pickFrom = step.getPickFrom(stepPickFromKey);
			return pickFrom.getPickFromHUId();
		}
		return huQRCodesService.getHuIdByQRCode(getPickFromHUQRCode());
	}

	@NonNull
	private PickFromHUsGetRequest getPickFromHUValidateRequest(@NonNull final HuId huId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(getShipmentScheduleId());
		final WarehouseId warehouseId = shipmentScheduleBL.getWarehouseId(shipmentSchedule);
		final BPartnerId bPartnerId = shipmentScheduleBL.getBPartnerId(shipmentSchedule);

		final Optional<HUReservationDocRef> reservationDocRef = stepId != null
				? Optional.of(HUReservationDocRef.ofPickingJobStepId(stepId))
				: Optional.ofNullable(OrderLineId.ofRepoIdOrNull(shipmentSchedule.getC_OrderLine_ID())).map(HUReservationDocRef::ofSalesOrderLineId);

		return PickFromHUsGetRequest.builder()
				.pickFromLocatorIds(warehouseBL.getLocatorIdsOfTheSamePickingGroup(warehouseId))
				.partnerId(shipmentScheduleBL.getBPartnerId(shipmentSchedule))
				.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(shipmentSchedule.getM_AttributeSetInstance_ID()))
				.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.optionalOfNullableCode(shipmentSchedule.getShipmentAllocation_BestBefore_Policy())
						.orElseGet(() -> bPartnerBL.getBestBeforePolicy(bPartnerId)))
				.reservationRef(reservationDocRef)
				.enforceMandatoryAttributesOnPicking(true)
				.onlyHuIds(ImmutableSet.of(huId))
				.build();
	}

	//
	// -------------------------------------
	//
	@Data
	@Builder
	private static class PickedToHU
	{
		@NonNull final HUInfo pickedFromHU;
		@NonNull final LocatorId pickFromLocatorId;

		@NonNull final PackToSpec pickToSpecUsed;
		@NonNull final HuId actuallyPickedToHUId;

		@NonNull final Quantity qtyPicked;

		@Nullable PickingCandidateId pickingCandidateId;
	}
}
