package de.metas.handlingunits.picking.job.service.commands;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
			// Optional subset selector — when both set, reverse only matching product+qty HUs (LIFO)
			final @Nullable ProductId productId,
			final @Nullable Quantity qtyToUnpick)
	{
		this.shipmentScheduleService = shipmentScheduleService;
		this.pickingJobRepository = pickingJobRepository;
		this.pickingCandidateService = pickingCandidateService;
		this.huService = huService;

		this.initialPickingJob = pickingJob;
		this.lineId = lineId;

		this.unpickToHU = unpickToHU;

		// productId and qtyToUnpick form a coupled pair: both-or-neither.
		// Guard the half-set case, which would otherwise silently fall through and unpick the wrong set of HUs.
		Check.assume(
				(productId == null) == (qtyToUnpick == null),
				"productId and qtyToUnpick must both be set or both be null; got productId={}, qtyToUnpick={}",
				productId, qtyToUnpick);

		final Stream<StepUnpickInstructions> unpickInstructionsStream;
		if (productId != null && qtyToUnpick != null)
		{
			unpickInstructionsStream = buildSubsetUnpickInstructions(pickingJob, productId, qtyToUnpick);
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

	/**
	 * Selects whole packed HUs (LIFO) across all steps whose product matches {@code productId},
	 * summing their {@code qtyPicked} until exactly {@code qtyToUnpick} is reached.
	 * If the requested qty cannot be met by whole-HU boundaries, throws an {@link AdempiereException}.
	 */
	private static Stream<StepUnpickInstructions> buildSubsetUnpickInstructions(
			@NonNull final PickingJob pickingJob,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyToUnpick)
	{
		final List<CandidateHU> candidates = pickingJob.streamSteps()
				.filter(step -> ProductId.equals(step.getProductId(), productId))
				.flatMap(step -> step.getPickFromKeys().stream()
						.flatMap(pickFromKey -> {
							final PickingJobStepPickFrom pickFrom = step.getPickFrom(pickFromKey);
							if (pickFrom.getPickedTo() == null)
							{
								return Stream.<CandidateHU>empty();
							}
							return pickFrom.getPickedTo().stream()
									.map(pickedToHU -> new CandidateHU(step.getId(), pickFromKey, pickedToHU));
						}))
				// LIFO: most recently packed first
				.sorted(Comparator.comparing((CandidateHU c) -> c.getPickedToHU().getCreatedAt()).reversed())
				.collect(Collectors.toList());

		// Fix 1: Fail-fast UOM guard — every candidate's qtyPicked must share the same UOM as qtyToUnpick.
		// UOM conversion is not supported for partial unpick; a mismatch here would cause a cryptic
		// internal Quantity assertion further down the greedy selection loop.
		for (final CandidateHU candidate : candidates)
		{
			final Quantity huQty = candidate.getPickedToHU().getQtyPicked();
			if (!huQty.getUomId().equals(qtyToUnpick.getUomId()))
			{
				throw new AdempiereException("qtyToUnpick UOM " + qtyToUnpick.getUomId()
						+ " does not match packed HU UOM " + huQty.getUomId()
						+ " — UOM conversion is not supported for partial unpick");
			}
		}

		Quantity remaining = qtyToUnpick;
		// Fix 2: LinkedHashMap preserves LIFO-grouped insertion order (candidates are sorted LIFO above).
		final Map<StepPickFromKey, List<PickingJobStepPickedToHU>> selectedByStepPickFrom = new LinkedHashMap<>();

		for (final CandidateHU candidate : candidates)
		{
			if (remaining.isZero())
			{
				break;
			}

			final Quantity huQty = candidate.getPickedToHU().getQtyPicked();
			if (huQty.compareTo(remaining) <= 0)
			{
				// Take the whole HU
				final StepPickFromKey key = new StepPickFromKey(candidate.getStepId(), candidate.getPickFromKey());
				selectedByStepPickFrom.computeIfAbsent(key, k -> new ArrayList<>()).add(candidate.getPickedToHU());
				remaining = remaining.subtract(huQty);
			}
			// else: this HU's qty exceeds remaining — skip (no splitting)
		}

		if (!remaining.isZero())
		{
			// Fallback: if the requested qty cannot be met by summing whole-HU boundaries,
			// try to take the partial qty from the LAST (most-recently-packed) candidate
			// whose qty exceeds remaining.  The execution phase will split that VHU.
			final Quantity remainingFinal = remaining; // effectively final capture for the lambda
			final CandidateHU partialCandidate = candidates.stream()
					.filter(c -> c.getPickedToHU().getQtyPicked().compareTo(remainingFinal) > 0)
					.findFirst() // candidates are LIFO-sorted → first = most recent
					.orElse(null);

			if (partialCandidate == null)
			{
				throw new AdempiereException("Cannot unpick the requested quantity " + qtyToUnpick
						+ " for product " + productId
						+ " because it cannot be met by summing whole packed HU boundaries."
						+ " Remaining qty that could not be matched: " + remaining);
			}

			// Build a partial-split instruction: one HU with more qty than needed.
			final StepPickFromKey partialKey = new StepPickFromKey(partialCandidate.getStepId(), partialCandidate.getPickFromKey());
			selectedByStepPickFrom.computeIfAbsent(partialKey, k -> new ArrayList<>()).add(partialCandidate.getPickedToHU());

			return Stream.concat(
					selectedByStepPickFrom.entrySet().stream()
							.filter(e -> !e.getKey().equals(partialKey))
							.map(entry -> StepUnpickInstructions.builder()
									.stepId(entry.getKey().getStepId())
									.pickFromKey(entry.getKey().getPickFromKey())
									.pickedToHUsToUnpick(ImmutableList.copyOf(entry.getValue()))
									.build()),
					Stream.of(StepUnpickInstructions.builder()
							.stepId(partialKey.getStepId())
							.pickFromKey(partialKey.getPickFromKey())
							.pickedToHUsToUnpick(ImmutableList.of(partialCandidate.getPickedToHU()))
							.partialQtyToUnpick(remainingFinal)
							.build())
			);
		}

		return selectedByStepPickFrom.entrySet().stream()
				.map(entry -> StepUnpickInstructions.builder()
						.stepId(entry.getKey().getStepId())
						.pickFromKey(entry.getKey().getPickFromKey())
						// invariant: non-empty — only non-empty lists are put into selectedByStepPickFrom
						.pickedToHUsToUnpick(ImmutableList.copyOf(entry.getValue()))
						.build());
	}

	@Value
	private static class CandidateHU
	{
		@NonNull PickingJobStepId stepId;
		@NonNull PickingJobStepPickFromKey pickFromKey;
		@NonNull PickingJobStepPickedToHU pickedToHU;
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
			@NonNull final StepUnpickInstructions instructions)
	{
		final PickingJobStepPickFromKey pickFromKey = instructions.getPickFromKey();
		final Quantity partialQtyToUnpick = instructions.getPartialQtyToUnpick();

		if (partialQtyToUnpick != null)
		{
			// Partial-split path: the selected packed HU has MORE qty than we want to unpick.
			// 1. Get the single packed HU that will be split.
			final ImmutableList<PickingJobStepPickedToHU> pickedToHUsToUnpick = instructions.getPickedToHUsToUnpick();
			Check.assume(pickedToHUsToUnpick != null && pickedToHUsToUnpick.size() == 1,
					"Partial unpick must target exactly one packed HU; got {}", pickedToHUsToUnpick);
			final PickingJobStepPickedToHU originalPickedHU = pickedToHUsToUnpick.get(0);
			final I_M_HU originalHURecord = huService.getById(originalPickedHU.getActualPickedHU().getId());

			// 2. Split the VHU: cuToNewCU takes `partialQtyToUnpick` out of the original HU
			//    and returns it as a new standalone HU.  The original HU's storage is reduced.
			final HUTransformService huTransformService = newHUTransformService();
			final List<I_M_HU> splitOffHUs = huTransformService.cuToNewCU(originalHURecord, partialQtyToUnpick);
			Check.assumeNotEmpty(splitOffHUs, "cuToNewCU must return at least one HU");

			// 3. Compute the qty remaining in the original HU after the split.
			final Quantity originalQty = originalPickedHU.getQtyPicked();
			final Quantity remainingInOriginal = originalQty.subtract(partialQtyToUnpick);

			// 4. Unpick only the split-off HUs: delete shipment sched records and set Active.
			final ImmutableSet<HUIdAndQRCode> splitOffIdAndQRCode = splitOffHUs.stream()
					.map(hu -> HUIdAndQRCode.ofHuId(HuId.ofRepoId(hu.getM_HU_ID())))
					.collect(ImmutableSet.toImmutableSet());
			final List<I_M_HU> splitOffTopLevelHUs = extractToTopLevelHUs(splitOffIdAndQRCode);
			shipmentScheduleService.deleteByTopLevelHUsAndShipmentScheduleId(splitOffTopLevelHUs, step.getScheduleId().getShipmentScheduleId());
			changeHUStatusFromPickedToActive(splitOffTopLevelHUs);
			moveToTargetHUIfNeeded(splitOffIdAndQRCode);

			// 5. Update the in-memory model: reduce the original HU's tracked qty.
			return step.reduceWithPartialUnpickEvent(
					pickFromKey,
					originalPickedHU.getActualPickedHU().getId(),
					remainingInOriginal);
		}
		else
		{
			// Whole-HU path (original behaviour)
			final PickingJobStepPickFrom pickFrom = step.getPickFrom(pickFromKey);
			final List<PickingJobStepPickedToHU> pickedToHUs;
			if (instructions.getPickedToHUsToUnpick() != null)
			{
				// Subset path: use only the explicitly selected HUs
				pickedToHUs = instructions.getPickedToHUsToUnpick();
			}
			else
			{
				// Whole-step path (original behaviour)
				pickedToHUs = pickFrom.getPickedTo() != null
						? pickFrom.getPickedTo().getActualPickedHUs()
						: ImmutableList.of();
			}
			if (pickedToHUs.isEmpty())
			{
				return step;
			}

			final ImmutableSet<HUIdAndQRCode> huIdAndQRCodeList = extractHuIdAndQRCodes(pickedToHUs);

			final List<I_M_HU> topLevelHUs = extractToTopLevelHUs(huIdAndQRCodeList);
			shipmentScheduleService.deleteByTopLevelHUsAndShipmentScheduleId(topLevelHUs, step.getScheduleId().getShipmentScheduleId());
			changeHUStatusFromPickedToActive(topLevelHUs);

			moveToTargetHUIfNeeded(huIdAndQRCodeList);

			return step.reduceWithUnpickEvent(
					pickFromKey,
					PickingJobStepUnpickInfo.ofUnpickedHUs(pickedToHUs)
			);
		}
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
	private static class StepUnpickInstructions
	{
		@NonNull PickingJobStepId stepId;
		@NonNull PickingJobStepPickFromKey pickFromKey;
		/**
		 * When present: reverse only these specific packed HUs (subset path).
		 * When absent: reverse all packed HUs for this step/pickFrom (whole-step path).
		 */
		@Nullable ImmutableList<PickingJobStepPickedToHU> pickedToHUsToUnpick;
		/**
		 * When non-null, {@code pickedToHUsToUnpick} contains exactly ONE entry whose VHU has MORE qty
		 * than {@code qtyToUnpick}. The execution phase must split that VHU first (taking exactly
		 * {@code qtyToUnpick} out of it) and unpick only the split-off portion, leaving the remainder
		 * in the packed list with a reduced qty.
		 */
		@Nullable Quantity partialQtyToUnpick;
	}
}
