package de.metas.handlingunits.picking.job.service.commands.unpick;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsIdAndCaption;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.movement.HUIdAndQRCode;
import de.metas.handlingunits.movement.MoveHUCommand;
import de.metas.handlingunits.movement.MoveHURequestItem;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.PickingJobStepUnpickInfo;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PickingJobUnPickCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobShipmentScheduleService shipmentScheduleService;
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingCandidateService pickingCandidateService;
	@NonNull private final PickingJobHUService huService;

	//
	// Params
	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final PickingJobLineId lineId;
	@NonNull private final ImmutableListMultimap<PickingJobStepId, StepUnpickInstructions> unpickInstructionsMap;
	@Nullable private final HUQRCode unpickToHU;

	//
	// State
	private final ArrayList<PickingCandidate> unprocessedPickingCandidates = new ArrayList<>();

	@Builder
	private PickingJobUnPickCommand(
			final @NonNull PickingJobShipmentScheduleService shipmentScheduleService,
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingCandidateService pickingCandidateService,
			final @NonNull PickingJobHUService huService,
			//
			final @NonNull PickingJob pickingJob,
			final @NonNull PickingJobLineId lineId,
			final @Nullable PickingJobStepId onlyPickingJobStepId,
			final @Nullable PickingJobStepPickFromKey onlyPickFromKey,
			final @Nullable HUQRCode unpickToHU,
			final @Nullable ProductId productId,
			final @Nullable BigDecimal qtyToUnpick)
	{
		this.shipmentScheduleService = shipmentScheduleService;
		this.pickingJobRepository = pickingJobRepository;
		this.pickingCandidateService = pickingCandidateService;
		this.huService = huService;

		this.initialPickingJob = pickingJob;
		this.lineId = lineId;

		this.unpickToHU = unpickToHU;

		if (productId == null ^ qtyToUnpick == null)
		{
			throw new AdempiereException("UNPICK must have either both productId and qty set, or neither; got productId="
					+ productId + ", qty=" + qtyToUnpick)
					.markAsUserValidationError();
		}

		final Stream<StepUnpickInstructions> unpickInstructionsStream;
		if (productId != null && qtyToUnpick != null)
		{
			final Quantity qtyToUnpickResolved = resolveUnpickQty(pickingJob, productId, qtyToUnpick);
			unpickInstructionsStream = selectLifoUnpickInstructions(pickingJob, productId, qtyToUnpickResolved);
		}
		else if (onlyPickingJobStepId != null)
		{
			Check.assumeNotNull(onlyPickFromKey, "onlyPickFromKey shall be set when onlyPickingJobStepId is set");
			initialPickingJob.getStepById(onlyPickingJobStepId).getPickFrom(onlyPickFromKey).assertPicked();

			unpickInstructionsStream = Stream.of(
					StepUnpickInstructions.builder()
							.stepId(onlyPickingJobStepId)
							.pickFromKey(onlyPickFromKey)
							.build()
			);
		}
		else
		{
			unpickInstructionsStream = initialPickingJob.streamSteps()
					.flatMap(step -> step.getPickFromKeys().stream()
							.filter(pickFromKey -> step.getPickFrom(pickFromKey).isPicked())
							.map(pickFromKey -> StepUnpickInstructions.builder()
									.stepId(step.getId())
									.pickFromKey(pickFromKey)
									.build()));
		}

		this.unpickInstructionsMap = unpickInstructionsStream.collect(ImmutableListMultimap.toImmutableListMultimap(
				StepUnpickInstructions::getStepId,
				unpickInstructions -> unpickInstructions));
	}

	@NonNull
	private static Quantity resolveUnpickQty(
			@NonNull final PickingJob pickingJob,
			@NonNull final ProductId productId,
			@NonNull final BigDecimal qtyBD)
	{
		final Quantity referenceQty = pickingJob.streamSteps()
				.filter(step -> ProductId.equals(step.getProductId(), productId))
				.flatMap(step -> step.getPickFromKeys().stream()
						.map(key -> step.getPickFrom(key).getQtyPicked().orElse(null))
						.filter(Objects::nonNull))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No packed qty found for product " + productId + " in picking job " + pickingJob.getId()));

		return Quantity.of(qtyBD, referenceQty.getUOM());
	}

	@VisibleForTesting
	static Stream<StepUnpickInstructions> selectLifoUnpickInstructions(
			@NonNull final PickingJob pickingJob,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToUnpick)
	{
		final List<CandidateHU> candidates = collectPackedCandidatesNewestFirst(pickingJob, productId);
		assertUniformUom(candidates, qtyToUnpick);
		assertWithinPackedQty(candidates, qtyToUnpick, productId);
		return greedilyTakeNewestFirst(candidates, qtyToUnpick).toUnpickInstructions();
	}

	private static List<CandidateHU> collectPackedCandidatesNewestFirst(
			@NonNull final PickingJob pickingJob,
			@NonNull final ProductId productId)
	{
		return pickingJob.streamSteps()
				.filter(step -> ProductId.equals(step.getProductId(), productId))
				.flatMap(step -> step.getPickFromKeys().stream()
						.flatMap(pickFromKey -> {
							final PickingJobStepPickFrom pickFrom = step.getPickFrom(pickFromKey);
							if (pickFrom.getPickedTo() == null)
							{
								return Stream.empty();
							}
							return pickFrom.getPickedTo().stream()
									.map(pickedToHU -> new CandidateHU(step.getId(), pickFromKey, pickedToHU));
						}))
				.sorted(CandidateHU.ORDERBY_Created_DESC)
				.collect(Collectors.toList());
	}

	private static void assertUniformUom(
			@NonNull final List<CandidateHU> candidates,
			@NonNull final Quantity qtyToUnpick)
	{
		for (final CandidateHU candidate : candidates)
		{
			if (!candidate.getQtyPicked().getUomId().equals(qtyToUnpick.getUomId()))
			{
				throw new AdempiereException("qtyToUnpick UOM " + qtyToUnpick.getUomId()
						+ " does not match packed HU UOM " + candidate.getQtyPicked().getUomId()
						+ " — UOM conversion is not supported for partial unpick");
			}
		}
	}

	private static void assertWithinPackedQty(
			@NonNull final List<CandidateHU> candidates,
			@NonNull final Quantity qtyToUnpick,
			@NonNull final ProductId productId)
	{
		final Quantity totalPacked = candidates.stream()
				.map(CandidateHU::getQtyPicked)
				.reduce(Quantity::add)
				.orElseGet(qtyToUnpick::toZero);
		if (qtyToUnpick.compareTo(totalPacked) > 0)
		{
			throw new AdempiereException("Requested unpick qty " + qtyToUnpick
					+ " exceeds the total packed qty " + totalPacked
					+ " for product " + productId);
		}
	}

	private static LifoSelection greedilyTakeNewestFirst(
			@NonNull final List<CandidateHU> candidates,
			@NonNull final Quantity qtyToUnpick)
	{
		Quantity remaining = qtyToUnpick;
		final Map<StepPickFromKey, List<PickingJobStepPickedToHU>> wholeHUsByStepPickFrom = new LinkedHashMap<>();
		final Map<StepPickFromKey, BoundarySplit> boundaryByStepPickFrom = new LinkedHashMap<>();

		for (final CandidateHU candidate : candidates)
		{
			if (remaining.isZero())
			{
				break;
			}

			final Quantity huQty = candidate.getQtyPicked();
			final StepPickFromKey key = new StepPickFromKey(candidate.getStepId(), candidate.getPickFromKey());
			if (huQty.compareTo(remaining) <= 0)
			{
				wholeHUsByStepPickFrom.computeIfAbsent(key, k -> new ArrayList<>()).add(candidate.getPickedToHU());
				remaining = remaining.subtract(huQty);
			}
			else
			{
				boundaryByStepPickFrom.put(key, new BoundarySplit(candidate.getPickedToHU(), remaining));
				remaining = remaining.toZero();
				break;
			}
		}

		Check.assume(remaining.isZero(),
				"remaining must be zero after selection; got remaining={}, qtyToUnpick={}",
				remaining, qtyToUnpick);

		return new LifoSelection(wholeHUsByStepPickFrom, boundaryByStepPickFrom);
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();

		if (!unpickInstructionsMap.isEmpty())
		{
			return trxManager.callInThreadInheritedTrx(this::executeInTrx);
		}
		else
		{
			return initialPickingJob;
		}
	}

	private PickingJob executeInTrx()
	{
		PickingJob pickingJob = initialPickingJob.withChangedSteps(unpickInstructionsMap.keySet(), this::unpickStep);
		pickingJob = reinitializePickingTargetIfDestroyed(pickingJob);
		pickingJobRepository.save(pickingJob);

		pickingCandidateService.deleteDraftPickingCandidates(unprocessedPickingCandidates);

		return pickingJob;
	}

	@Nullable
	private PickingJobStep unpickStep(@NonNull final PickingJobStep step)
	{
		final ImmutableList<StepUnpickInstructions> unpickInstructionsList = this.unpickInstructionsMap.get(step.getId());

		PickingJobStep changedStep = step;
		for (final StepUnpickInstructions unpickInstructions : unpickInstructionsList)
		{
			changedStep = unpickStep(changedStep, unpickInstructions);
		}

		if (changedStep.isGeneratedOnFly() && changedStep.isNothingPicked())
		{
			return null;
		}

		return changedStep;
	}

	private PickingJobStep unpickStep(
			@NonNull final PickingJobStep step,
			@NonNull final StepUnpickInstructions unpickInstructions)
	{
		final PickingJobStepPickFromKey pickFromKey = unpickInstructions.getPickFromKey();
		final PickingJobStepPickFrom pickFrom = step.getPickFrom(pickFromKey);

		final List<PickingJobStepPickedToHU> wholeHUsToUnpick = resolveWholeHUsToUnpick(unpickInstructions, pickFrom);
		final PickingJobStepPickedToHU boundaryHu = unpickInstructions.getBoundaryHuToSplit();
		final Quantity boundarySplitQty = unpickInstructions.getBoundarySplitQty();

		if (wholeHUsToUnpick.isEmpty() && boundaryHu == null)
		{
			return step;
		}

		final ShipmentScheduleId shipmentScheduleId = step.getScheduleId().getShipmentScheduleId();

		// Before extraction detaches anything: is the pick-to row TU-keyed (bare-TU pick → use the reduce path
		// below) or CU/LU-keyed (extracted-CU delete still matches → unchanged)?
		final HuId pickToTuId = resolvePickToTuId(wholeHUsToUnpick, boundaryHu);
		final boolean isPickToBareTU = pickToTuId != null && !huService.hasLoadingUnit(pickToTuId);

		final PickingJobStepUnpickInfo.PickingJobStepUnpickInfoBuilder unpickInfoBuilder = PickingJobStepUnpickInfo.builder()
				.unpickedHUs(wholeHUsToUnpick);

		final List<I_M_HU> extractedTopLevelHUs = new ArrayList<>(unpickWholeHUs(wholeHUsToUnpick));
		extractedTopLevelHUs.addAll(unpickBoundaryHU(step, boundaryHu, boundarySplitQty, unpickInfoBuilder));

		// Single reduce-vs-delete dispatch for the whole step: a bare-TU pick-to row is TU-keyed, so its
		// schedule-side rows are reduced once for the step's total unpicked qty; otherwise the extracted
		// top-level HUs' CU/LU-keyed rows are deleted.
		if (isPickToBareTU)
		{
			final Quantity totalUnpickedQty = computeTotalUnpickedQty(wholeHUsToUnpick, boundaryHu, boundarySplitQty);
			shipmentScheduleService.reduceQtyPickedForPickToTU(shipmentScheduleId, pickToTuId, totalUnpickedQty);
		}
		else
		{
			shipmentScheduleService.deleteByTopLevelHUsAndShipmentScheduleId(extractedTopLevelHUs, shipmentScheduleId);
		}

		return step.reduceWithUnpickEvent(pickFromKey, unpickInfoBuilder.build());
	}

	/**
	 * Resolves the TU that this step's pick-to CU(s) are packed into, using any one of the step's picked-to CUs
	 * (all CUs of one step share that step's single pick-to TU). Returns {@code null} when there is no pick-to CU
	 * at all, or the CU has no TU parent (e.g. a standalone/top-level CU pick).
	 */
	@Nullable
	private HuId resolvePickToTuId(
			@NonNull final List<PickingJobStepPickedToHU> wholeHUsToUnpick,
			@Nullable final PickingJobStepPickedToHU boundaryHu)
	{
		final HuId representativeCuId = !wholeHUsToUnpick.isEmpty()
				? wholeHUsToUnpick.get(0).getActualPickedHUId()
				: boundaryHu != null ? boundaryHu.getActualPickedHUId() : null;
		return representativeCuId != null ? huService.getParentTransportUnitId(representativeCuId) : null;
	}

	@NonNull
	private static Quantity computeTotalUnpickedQty(
			@NonNull final List<PickingJobStepPickedToHU> wholeHUsToUnpick,
			@Nullable final PickingJobStepPickedToHU boundaryHu,
			@Nullable final Quantity boundarySplitQty)
	{
		Quantity total = wholeHUsToUnpick.stream()
				.map(PickingJobStepPickedToHU::getQtyPicked)
				.reduce(Quantity::add)
				.orElse(null);

		if (boundaryHu != null && boundarySplitQty != null)
		{
			total = total != null ? total.add(boundarySplitQty) : boundarySplitQty;
		}

		Check.assumeNotNull(total, "at least one of wholeHUsToUnpick or boundarySplitQty must be present when resolving the pick-to TU's total unpicked qty");
		return total;
	}

	private static List<PickingJobStepPickedToHU> resolveWholeHUsToUnpick(
			@NonNull final StepUnpickInstructions unpickInstructions,
			@NonNull final PickingJobStepPickFrom pickFrom)
	{
		if (unpickInstructions.getPickedToHUsToUnpick() != null)
		{
			// Subset path: use only the explicitly selected whole CUs
			return unpickInstructions.getPickedToHUsToUnpick();
		}
		else
		{
			// Whole-step path (original behaviour)
			return pickFrom.getPickedTo() != null
					? pickFrom.getPickedTo().getActualPickedHUs()
					: ImmutableList.of();
		}
	}

	/**
	 * Pure HU operation: extracts the given whole picked-to HUs to top level and returns them. Does NOT touch
	 * any {@code M_ShipmentSchedule_QtyPicked} row — the caller ({@link #unpickStep}) does the single
	 * reduce-vs-delete dispatch for the whole step.
	 *
	 * @return the extracted top-level HUs (empty if {@code wholeHUsToUnpick} is empty)
	 */
	@NonNull
	private List<I_M_HU> unpickWholeHUs(@NonNull final List<PickingJobStepPickedToHU> wholeHUsToUnpick)
	{
		if (wholeHUsToUnpick.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList = extractHuIdAndQRCodes(wholeHUsToUnpick);

		final List<I_M_HU> topLevelHUs = extractToTopLevelHUs(huIdAndQRCodeList);
		changeHUStatusFromPickedToActive(topLevelHUs);

		// Move the EXTRACTED top-level HUs, not the original picked-CU references: extracting an aggregate
		// CU splits out a new TU and relocates its QR assignment onto that TU, so the original (huId,QR)
		// pairs are stale and the move's re-extraction would fail the QR-assignment assertion.
		moveToTargetHUIfNeeded(toCurrentHuIdAndQRCodes(topLevelHUs));

		return topLevelHUs;
	}

	/**
	 * Pure HU operation: carves the boundary split qty into a new CU, extracts it to top level and returns it.
	 * Does NOT touch any {@code M_ShipmentSchedule_QtyPicked} row — the caller ({@link #unpickStep}) does the
	 * single reduce-vs-delete dispatch for the whole step. Still fills {@code unpickInfoBuilder}.
	 *
	 * @return the carved top-level HU (empty if there is no boundary HU to split)
	 */
	@NonNull
	private List<I_M_HU> unpickBoundaryHU(
			@NonNull final PickingJobStep step,
			@Nullable final PickingJobStepPickedToHU boundaryHu,
			@Nullable final Quantity boundarySplitQty,
			@NonNull final PickingJobStepUnpickInfo.PickingJobStepUnpickInfoBuilder unpickInfoBuilder)
	{
		if (boundaryHu == null || boundarySplitQty == null)
		{
			return ImmutableList.of();
		}

		final HuId boundaryVhuId = boundaryHu.getActualPickedHUId();
		final Quantity boundaryHuQty = boundaryHu.getQtyPicked();
		final Quantity remainderQty = boundaryHuQty.subtract(boundarySplitQty);

		// Carve a NEW CU of exactly `boundarySplitQty` from the boundary VHU; the source keeps the remainder.
		final I_M_HU carvedCU = newHUTransformService().huToNewSingleCU(
				HUTransformService.HUsToNewCUsRequest.builder()
						.sourceHU(huService.getById(boundaryVhuId))
						.productId(step.getProductId())
						.qtyCU(boundarySplitQty)
						.build());
		final HuId carvedCUId = HuId.ofRepoId(carvedCU.getM_HU_ID());

		// Delete the carved CU's allocation before re-activating it — the source VHU's allocation then covers the physical remainder.
		final HUQRCode carvedQRCode = huService.getQRCodeByHuId(carvedCUId);
		final ImmutableSet<HUIdAndQRCode> carvedHuIdAndQRCode = ImmutableSet.of(
				HUIdAndQRCode.builder().huId(carvedCUId).huQRCode(carvedQRCode).build());
		final List<I_M_HU> carvedTopLevelHUs = extractToTopLevelHUs(carvedHuIdAndQRCode);
		changeHUStatusFromPickedToActive(carvedTopLevelHUs);
		moveToTargetHUIfNeeded(carvedHuIdAndQRCode);

		unpickInfoBuilder
				.huToReduce(boundaryHu)
				.reducedQtyPicked(remainderQty);

		return carvedTopLevelHUs;
	}

	private void moveToTargetHUIfNeeded(final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList)
	{
		if (unpickToHU == null)
		{
			return;
		}

		huService.newMoveHUCommandBuilder()
				.requestItems(huIdAndQRCodeList.stream().map(MoveHURequestItem::ofHUIdAndQRCode).collect(ImmutableSet.toImmutableSet()))
				.targetQRCode(unpickToHU.toScannedCode())
				.build()
				.execute();
	}

	private List<I_M_HU> extractToTopLevelHUs(@NonNull final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList)
	{
		final Set<HuId> topLevelHUIds = newHUTransformService().extractToTopLevel(huIdAndQRCodeList);
		return huService.getByIds(topLevelHUIds);
	}

	private ImmutableSet<HUIdAndQRCode> toCurrentHuIdAndQRCodes(@NonNull final Collection<I_M_HU> hus)
	{
		return hus.stream()
				.map(hu -> {
					final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
					return HUIdAndQRCode.builder()
							.huId(huId)
							.huQRCode(huService.getQRCodeByHuId(huId))
							.build();
				})
				.collect(ImmutableSet.toImmutableSet());
	}

	private static ImmutableSet<HUIdAndQRCode> extractHuIdAndQRCodes(final @NonNull List<PickingJobStepPickedToHU> pickedToHUs)
	{
		return pickedToHUs.stream()
				.map(PickingJobStepPickedToHU::getActualPickedHU)
				.map(HUInfo::toHUIdAndQRCode)
				.collect(ImmutableSet.toImmutableSet());
	}

	private void changeHUStatusFromPickedToActive(final Collection<I_M_HU> topLevelHUs)
	{
		topLevelHUs.forEach(this::changeHUStatusFromPickedToActive);
	}

	private void changeHUStatusFromPickedToActive(final I_M_HU topLevelHU)
	{
		if (X_M_HU.HUSTATUS_Picked.equals(topLevelHU.getHUStatus()))
		{
			huService.setHUStatusActive(topLevelHU);
		}
	}

	private HUTransformService newHUTransformService()
	{
		return huService.newHUTransformService(getAllowedReservedVhuIds());
	}

	/**
	 * Collects all reserved VHU IDs from all steps being un-picked into a single set.
	 * This intentionally gives the {@link de.metas.handlingunits.allocation.transfer.HUTransformService}
	 * wider permission than strictly necessary (i.e. all steps rather than just the current one),
	 * because the same service instance is reused for the entire un-pick batch and all steps
	 * are being reversed in the same transaction.
	 */
	private ImmutableSet<HuId> getAllowedReservedVhuIds()
	{
		return unpickInstructionsMap.keySet().stream()
				.map(HUReservationDocRef::ofPickingJobStepId)
				.flatMap(docRef -> huService.getVHUIdsByDocumentRef(docRef).stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private PickingJob reinitializePickingTargetIfDestroyed(final PickingJob pickingJob)
	{
		if (isLineLevelPickTarget(pickingJob))
		{
			return pickingJob.withLuPickingTarget(lineId, this::reinitializeLUPickingTarget);
		}
		else
		{
			return pickingJob.withLuPickingTarget(null, this::reinitializeLUPickingTarget);
		}
	}

	private boolean isLineLevelPickTarget(final PickingJob pickingJob) {return pickingJob.isLineLevelPickTarget();}

	@Nullable
	private LUPickingTarget reinitializeLUPickingTarget(@Nullable final LUPickingTarget luPickingTarget)
	{
		if (luPickingTarget == null)
		{
			return null;
		}

		final HuId luId = luPickingTarget.getLuId();
		if (luId == null)
		{
			return luPickingTarget;
		}

		final I_M_HU lu = huService.getById(luId);
		if (!huService.isDestroyedOrEmptyStorage(lu))
		{
			return luPickingTarget;
		}

		final HuPackingInstructionsIdAndCaption luPI = huService.getEffectivePackingInstructionsIdAndCaption(lu);
		return LUPickingTarget.ofPackingInstructions(luPI);
	}
}
