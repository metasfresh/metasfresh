package de.metas.handlingunits.picking.job.service.commands;

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
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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

		if ((productId == null) != (qtyToUnpick == null))
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

	@Value
	private static class LifoSelection
	{
		@NonNull Map<StepPickFromKey, List<PickingJobStepPickedToHU>> wholeHUsByStepPickFrom;
		@NonNull Map<StepPickFromKey, BoundarySplit> boundaryByStepPickFrom;

		Stream<StepUnpickInstructions> toUnpickInstructions()
		{
			final ImmutableSet<StepPickFromKey> allKeys = ImmutableSet.<StepPickFromKey>builder()
					.addAll(wholeHUsByStepPickFrom.keySet())
					.addAll(boundaryByStepPickFrom.keySet())
					.build();

			return allKeys.stream()
					.map(key -> {
						final BoundarySplit boundary = boundaryByStepPickFrom.get(key);
						return StepUnpickInstructions.builder()
								.stepId(key.getStepId())
								.pickFromKey(key.getPickFromKey())
								.pickedToHUsToUnpick(ImmutableList.copyOf(wholeHUsByStepPickFrom.getOrDefault(key, ImmutableList.of())))
								.boundaryHuToSplit(boundary != null ? boundary.getPickedToHU() : null)
								.boundarySplitQty(boundary != null ? boundary.getQtyToCarve() : null)
								.build();
					});
		}
	}

	@Value
	private static class BoundarySplit
	{
		@NonNull PickingJobStepPickedToHU pickedToHU;
		@NonNull Quantity qtyToCarve;
	}

	@Value
	private static class CandidateHU
	{
		static final Comparator<CandidateHU> ORDERBY_Created_DESC =
				Comparator.comparing(CandidateHU::getCreatedAt).reversed();

		@NonNull PickingJobStepId stepId;
		@NonNull PickingJobStepPickFromKey pickFromKey;
		@NonNull PickingJobStepPickedToHU pickedToHU;

		Quantity getQtyPicked() {return pickedToHU.getQtyPicked();}

		Instant getCreatedAt() {return pickedToHU.getCreatedAt();}
	}

	@Value
	private static class StepPickFromKey
	{
		@NonNull PickingJobStepId stepId;
		@NonNull PickingJobStepPickFromKey pickFromKey;
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

		final List<PickingJobStepPickedToHU> wholeHUsToUnpick;
		if (unpickInstructions.getPickedToHUsToUnpick() != null)
		{
			// Subset path: use only the explicitly selected whole CUs
			wholeHUsToUnpick = unpickInstructions.getPickedToHUsToUnpick();
		}
		else
		{
			// Whole-step path (original behaviour)
			wholeHUsToUnpick = pickFrom.getPickedTo() != null
					? pickFrom.getPickedTo().getActualPickedHUs()
					: ImmutableList.of();
		}

		final PickingJobStepPickedToHU boundaryHu = unpickInstructions.getBoundaryHuToSplit();
		final Quantity boundarySplitQty = unpickInstructions.getBoundarySplitQty();

		if (wholeHUsToUnpick.isEmpty() && boundaryHu == null)
		{
			return step;
		}

		final ShipmentScheduleId shipmentScheduleId = step.getScheduleId().getShipmentScheduleId();

		//
		// 1) Whole CUs: detach, return to Active, move into the scanned target (existing behaviour).
		if (!wholeHUsToUnpick.isEmpty())
		{
			final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList = extractHuIdAndQRCodes(wholeHUsToUnpick);

			final List<I_M_HU> topLevelHUs = extractToTopLevelHUs(huIdAndQRCodeList);
			shipmentScheduleService.deleteByTopLevelHUsAndShipmentScheduleId(topLevelHUs, shipmentScheduleId);
			changeHUStatusFromPickedToActive(topLevelHUs);

			// Move the EXTRACTED top-level HUs, not the original picked-CU references: extracting an aggregate
			// CU splits out a new TU and relocates its QR assignment onto that TU, so the original (huId,QR)
			// pairs are stale and the move's re-extraction would fail the QR-assignment assertion.
			moveToTargetHUIfNeeded(toCurrentHuIdAndQRCodes(topLevelHUs));
		}

		//
		// 2) Boundary CU: physically carve the exact remainder out of it, move the carved CU into the
		//    scanned target (Active), decrement the original VHU's picked-qty allocation, keep the rest packed.
		final PickingJobStepUnpickInfo.PickingJobStepUnpickInfoBuilder unpickInfoBuilder = PickingJobStepUnpickInfo.builder()
				.unpickedHUs(wholeHUsToUnpick);
		if (boundaryHu != null && boundarySplitQty != null)
		{
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
			final HUQRCode carvedQRCode = huService.getHuQRCodesService().getQRCodeByHuId(carvedCUId);
			final ImmutableSet<HUIdAndQRCode> carvedHuIdAndQRCode = ImmutableSet.of(
					HUIdAndQRCode.builder().huId(carvedCUId).huQRCode(carvedQRCode).build());
			final List<I_M_HU> carvedTopLevelHUs = extractToTopLevelHUs(carvedHuIdAndQRCode);
			shipmentScheduleService.deleteByTopLevelHUsAndShipmentScheduleId(carvedTopLevelHUs, shipmentScheduleId);
			changeHUStatusFromPickedToActive(carvedTopLevelHUs);
			moveToTargetHUIfNeeded(carvedHuIdAndQRCode);

			unpickInfoBuilder
					.huToReduce(boundaryHu)
					.reducedQtyPicked(remainderQty);
		}

		return step.reduceWithUnpickEvent(pickFromKey, unpickInfoBuilder.build());
	}

	private void moveToTargetHUIfNeeded(final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList)
	{
		if (unpickToHU == null)
		{
			return;
		}

		MoveHUCommand.builder()
				.huQRCodesService(huService.getHuQRCodesService())
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
							.huQRCode(huService.getHuQRCodesService().getQRCodeByHuId(huId))
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
		return HUTransformService.builder()
				.huQRCodesService(huService.getHuQRCodesService())
				.allowedReservedVhuIds(getAllowedReservedVhuIds())
				.build();
	}

	/**
	 * Collects all reserved VHU IDs from all steps being un-picked into a single set.
	 * This intentionally gives the {@link de.metas.handlingunits.allocation.transfer.HUTransformService}
	 * wider permission than strictly necessary (i.e. all steps rather than just the current one),
	 * because the same service instance is reused for the entire un-pick batch and all steps
	 * are being reversed in the same transaction.
	 * <p>
	 * The resulting {@link HUTransformService} instance is single-use: it is created by
	 * {@link #newHUTransformService()} for this un-pick batch only and must not be reused
	 * for unrelated operations, as it would carry over the wider VHU exemption.
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

	//
	//
	//

	@Value
	@Builder
	@VisibleForTesting
	static class StepUnpickInstructions
	{
		@NonNull PickingJobStepId stepId;
		@NonNull PickingJobStepPickFromKey pickFromKey;
		/**
		 * When present: reverse only these specific packed HUs entirely (subset path, whole-CU portion).
		 * When absent: reverse all packed HUs for this step/pickFrom (whole-step path).
		 * May be an empty list when only a boundary split happens (see {@link #boundaryHuToSplit}).
		 */
		@Nullable ImmutableList<PickingJobStepPickedToHU> pickedToHUsToUnpick;

		/**
		 * Subset path only: the boundary CU that must be physically split because only part of its qty is
		 * being removed. {@code null} when the requested qty lands exactly on whole-CU boundaries.
		 */
		@Nullable PickingJobStepPickedToHU boundaryHuToSplit;

		/** The qty to carve out of {@link #boundaryHuToSplit} (the rest stays packed). */
		@Nullable Quantity boundarySplitQty;
	}
}
