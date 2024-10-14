package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.picking.QtyRejectedWithReason;
import de.metas.handlingunits.picking.candidate.commands.PackToHUsProducer;
import de.metas.handlingunits.picking.candidate.commands.PickHUResult;
import de.metas.handlingunits.picking.candidate.commands.ProcessPickingCandidatesRequest;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class PickingJobPickCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	@NonNull private final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingCandidateService pickingCandidateService;

<<<<<<< HEAD
	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final PickingJobStep initialStep;
	@NonNull private final PickingJobStepPickFromKey pickFromKey;
	@NonNull private final Quantity qtyToPick;
	@Nullable private final QtyRejectedWithReason qtyRejected;
=======
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
	private final static AdMessageKey PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG");
	private final static AdMessageKey NEGATIVE_CATCH_WEIGHT_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NEGATIVE_CATCH_WEIGHT_ERROR_MSG");
	private final static AdMessageKey QTY_REJECTED_ALTERNATIVES_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.QTY_REJECTED_ALTERNATIVES_ERROR_MSG");
	private final static AdMessageKey NO_QR_CODE_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NO_QR_CODE_ERROR_MSG");
	private final static AdMessageKey L_M_QR_CODE_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG");
	private final static AdMessageKey QR_CODE_EXTERNAL_LOT_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_EXTERNAL_LOT_ERROR_MSG");
	private final static AdMessageKey QR_CODE_NOT_SUPPORTED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.QR_CODE_NOT_SUPPORTED_ERROR_MSG");
	private final static AdMessageKey CANNOT_PACK_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.CANNOT_PACK_ERROR_MSG");
	private final static AdMessageKey INVALID_NUMBER_QR_CODES_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.INVALID_NUMBER_QR_CODES_ERROR_MSG");
	private final static AdMessageKey UNKNOWN_TARGET_LU_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.UNKNOWN_TARGET_LU_ERROR_MSG");
	private final static AdMessageKey NOT_ENOUGH_TUS_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG");
	private final static AdMessageKey CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG");
	private final static AdMessageKey CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG");
	private final static AdMessageKey NO_QTY_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.NO_QTY_ERROR_MSG");
>>>>>>> 87370c761fe (MobileUI Picking - User Error Handling (#19112) (#19121))

	@Builder
	private PickingJobPickCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingCandidateService pickingCandidateService,
			//
			final @NonNull PickingJob pickingJob,
			final @NonNull PickingJobStepId pickingJobStepId,
			final @Nullable PickingJobStepPickFromKey pickFromKey,
			final @NonNull BigDecimal qtyToPickBD,
			final @Nullable BigDecimal qtyRejectedBD,
			final @Nullable QtyRejectedReasonCode qtyRejectedReasonCode)
	{
		Check.assumeGreaterOrEqualToZero(qtyToPickBD, "qtyToPickBD");

		this.pickingJobRepository = pickingJobRepository;
		this.pickingCandidateService = pickingCandidateService;

		this.initialPickingJob = pickingJob;
		this.initialStep = initialPickingJob.getStepById(pickingJobStepId);
		this.pickFromKey = pickFromKey != null ? pickFromKey : PickingJobStepPickFromKey.MAIN;

		final I_C_UOM uom = initialStep.getUOM();
		this.qtyToPick = Quantity.of(qtyToPickBD, uom);

		if (qtyRejectedReasonCode != null)
		{
			final Quantity qtyRejected = qtyRejectedBD != null
					? Quantity.of(qtyRejectedBD, uom)
					: computeQtyRejected(this.initialStep, this.pickFromKey, this.qtyToPick);

			this.qtyRejected = QtyRejectedWithReason.of(qtyRejected, qtyRejectedReasonCode);
		}
		else
		{
<<<<<<< HEAD
			this.qtyRejected = null;
		}
=======
			throw new AdempiereException(PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG)
					.appendParametersToMessage()
					.setParameter("PickingUnit", pickingUnit);
		}

		this.catchWeight = line.getCatchUomId() != null && catchWeightBD != null
				? Quantitys.create(catchWeightBD, line.getCatchUomId())
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
>>>>>>> 87370c761fe (MobileUI Picking - User Error Handling (#19112) (#19121))
	}

	private static Quantity computeQtyRejected(
			@NonNull PickingJobStep step,
			@NonNull final PickingJobStepPickFromKey pickFromKey,
			@NonNull Quantity qtyPicked)
	{
		if (pickFromKey.isMain())
		{
<<<<<<< HEAD
			return step.getQtyToPick().subtract(qtyPicked);
=======
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
>>>>>>> 87370c761fe (MobileUI Picking - User Error Handling (#19112) (#19121))
		}
		else
		{
			// NOTE: because, in case of alternatives, we don't know which is the qty scheduled to pick
			// we cannot calculate the qtyRejected
			throw new AdempiereException("Cannot calculate QtyRejected in case of alternatives");
		}

	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		initialStep.getPickFrom(pickFromKey).assertNotPicked();

		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		final ImmutableList<PickedToHU> pickedHUs = createAndProcessPickingCandidate();

		final PickingJob pickingJob = initialPickingJob.withChangedStep(
				initialStep.getId(),
				step -> updateStepFromPickingCandidate(step, pickFromKey, pickedHUs));

		pickingJobRepository.save(pickingJob);

		return pickingJob;
	}

	private ImmutableList<PickedToHU> createAndProcessPickingCandidate()
	{
		final ImmutableList<PickedToHU> pickedToHUs = splitOutPickToHUs();
		if (!pickedToHUs.isEmpty())
		{
			for (final PickedToHU pickedToHU : pickedToHUs)
			{
				final PickHUResult pickResult = pickingCandidateService.pickHU(PickRequest.builder()
						.shipmentScheduleId(initialStep.getShipmentScheduleId())
						.pickFrom(PickFrom.ofHuId(pickedToHU.getActuallyPickedToHUId()))
						.packToSpec(pickedToHU.getPickToSpecUsed())
						.qtyToPick(pickedToHU.getQtyPicked())
						.pickingSlotId(initialPickingJob.getPickingSlotId().orElse(null))
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
		else
		{
<<<<<<< HEAD
			return ImmutableList.of();
=======
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
		else if (pickFromHUQRCode instanceof LMQRCode)
		{
			final LMQRCode lmQRCode = (LMQRCode)pickFromHUQRCode;
			final String lotNumber = lmQRCode.getLotNumber();
			if (lotNumber == null)
			{
				throw new AdempiereException(L_M_QR_CODE_ERROR_MSG);
			}

			return handlingUnitsBL.getFirstHuIdByExternalLotNo(lotNumber)
					.map(huQRCodesService::getQRCodeByHuId)
					.orElseThrow(() -> new AdempiereException(QR_CODE_EXTERNAL_LOT_ERROR_MSG)
							.appendParametersToMessage()
							.setParameter("LotNumber", lotNumber));
		}
		else
		{
			throw new AdempiereException(QR_CODE_NOT_SUPPORTED_ERROR_MSG)
					.appendParametersToMessage()
					.setParameter("QRCode", pickFromHUQRCode);
>>>>>>> 87370c761fe (MobileUI Picking - User Error Handling (#19112) (#19121))
		}
	}

	private ImmutableList<PickedToHU> splitOutPickToHUs()
	{
		if (qtyToPick.isZero())
		{
			return ImmutableList.of();
		}

		final PackToHUsProducer packToHUsProducer = PackToHUsProducer.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.huPIItemProductBL(huPIItemProductBL)
				.huCapacityBL(huCapacityBL)
				.build();

		final ProductId productId = initialStep.getProductId();
		final PackToSpec packToSpec = initialStep.getPackToSpec();
		final PickingJobStepPickFrom pickFrom = initialStep.getPickFrom(pickFromKey);

		final PackToHUsProducer.PackToInfo packToInfo = packToHUsProducer.extractPackToInfo(
				packToSpec,
				initialPickingJob.getDeliveryBPLocationId(),
				pickFrom.getPickFromLocator().getId());

		trxManager.assertThreadInheritedTrxExists();
		final IHUContext huContext = handlingUnitsBL.createMutableHUContextForProcessing();
		final HUInfo pickFromHU = pickFrom.getPickFromHU();
		final List<I_M_HU> packedHUs = packToHUsProducer.packToHU(
				huContext,
				pickFromHU.getId(),
				packToInfo,
				productId,
				qtyToPick,
				initialStep.getId().toTableRecordReference(),
				true);

		if (packedHUs.isEmpty())
		{
<<<<<<< HEAD
			throw new AdempiereException("Cannot pack to HUs from " + pickFromHU + " using " + packToInfo + ", qtyToPick=" + qtyToPick);
=======
			throw new AdempiereException(CANNOT_PACK_ERROR_MSG, pickFromHU, packToInfo, qtyToPickCUs);
>>>>>>> 87370c761fe (MobileUI Picking - User Error Handling (#19112) (#19121))
		}
		else if (packedHUs.size() == 1)
		{
			final I_M_HU packedHU = packedHUs.get(0);
			return ImmutableList.of(PickedToHU.builder()
					.pickedFromHUId(pickFromHU.getId())
					.actuallyPickedToHUId(HuId.ofRepoId(packedHU.getM_HU_ID()))
					.pickToSpecUsed(packToSpec)
					.qtyPicked(qtyToPick)
					.build());
		}
		else
		{
			final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
			final ImmutableList.Builder<PickedToHU> result = ImmutableList.builder();
			for (final I_M_HU packedHU : packedHUs)
			{
				final Quantity qtyPicked = huStorageFactory.getStorage(packedHU).getQuantity(productId, qtyToPick.getUOM());
				result.add(PickedToHU.builder()
						.pickedFromHUId(pickFromHU.getId())
						.actuallyPickedToHUId(HuId.ofRepoId(packedHU.getM_HU_ID()))
						.pickToSpecUsed(packToSpec)
						.qtyPicked(qtyPicked)
						.build());
			}

			return result.build();
		}
	}

	private PickingJobStep updateStepFromPickingCandidate(
			@NonNull final PickingJobStep step,
			@NonNull final PickingJobStepPickFromKey pickFromKey,
			@NonNull final ImmutableList<PickedToHU> pickedHUs)
	{
		final PickingJobStepPickedTo picked = toPickingJobStepPickedTo(pickedHUs);

		return step.reduceWithPickedEvent(pickFromKey, picked);
	}

	private PickingJobStepPickedTo toPickingJobStepPickedTo(@NonNull final ImmutableList<PickedToHU> pickedHUs)
	{
		return PickingJobStepPickedTo.builder()
				.qtyRejected(qtyRejected)
				.actualPickedHUs(pickedHUs.stream()
						.map(PickingJobPickCommand::toPickingJobStepPickedToHU)
						.collect(ImmutableList.toImmutableList())
				)
				.build();
	}

	private static PickingJobStepPickedToHU toPickingJobStepPickedToHU(final PickedToHU pickedHU)
	{
		return PickingJobStepPickedToHU.builder()
				.pickFromHUId(pickedHU.getPickedFromHUId())
				.actualPickedHUId(pickedHU.getActuallyPickedToHUId())
				.qtyPicked(pickedHU.getQtyPicked())
				.pickingCandidateId(Objects.requireNonNull(pickedHU.getPickingCandidateId()))
				.build();
	}

	private static ImmutableSet<PickingCandidateId> extractPickingCandidateIds(final ImmutableList<PickedToHU> pickedToHUs)
	{
<<<<<<< HEAD
		return pickedToHUs
				.stream()
				.map(PickedToHU::getPickingCandidateId)
				.peek(Objects::requireNonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

=======

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

		final PickingTarget pickingTarget = getPickingLUTarget().orElse(null);
		final LUTUResult result;

		if (handlingUnitsBL.isLoadingUnit(pickFromHU))
		{
			final HUTransformService.TargetLU targetLU = PickingTarget.apply(pickingTarget, new PickingTarget.CaseMapper<HUTransformService.TargetLU>()
			{
				@Override
				public HUTransformService.TargetLU noLU() {return HUTransformService.TargetLU.NONE;}

				@Override
				public HUTransformService.TargetLU newLU(final HuPackingInstructionsId luPackingInstructionsId) {return HUTransformService.TargetLU.ofNewLU(handlingUnitsBL.getPI(luPackingInstructionsId));}

				@Override
				public HUTransformService.TargetLU existingLU(final HuId luId) {return HUTransformService.TargetLU.ofExistingLU(handlingUnitsBL.getById(luId));}
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
			throw new AdempiereException(CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG)
					.appendParametersToMessage()
					.setParameter("LMQRCode", pickFromHUQRCode);
		}

		if (pickFromHUQRCode.getWeightInKg().compareTo(catchWeightBD) != 0)
		{
			throw new AdempiereException(CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG)
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
>>>>>>> 87370c761fe (MobileUI Picking - User Error Handling (#19112) (#19121))
	//
	// -------------------------------------
	//
	@Data
	@Builder
	private static class PickedToHU
	{
		@NonNull final HuId pickedFromHUId;

		@NonNull final PackToSpec pickToSpecUsed;
		@NonNull final HuId actuallyPickedToHUId;

		@NonNull final Quantity qtyPicked;

		@Nullable PickingCandidateId pickingCandidateId;
	}
}
