package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.handlingunits.movement.HUIdAndQRCode;
import de.metas.handlingunits.movement.MoveHUCommand;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.handlingunits.picking.job.model.PickingJobStepUnpickInfo;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.stream.Stream;

public class PickingJobUnPickCommand
{
	@NonNull
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull
	private final PickingJobRepository pickingJobRepository;
	@NonNull
	private final PickingCandidateService pickingCandidateService;
	@NonNull
	private final HUQRCodesService huQRCodesService;

	//
	// Params
	@NonNull
	private final PickingJob initialPickingJob;
	@NonNull
	private final ImmutableListMultimap<PickingJobStepId, StepUnpickInstructions> unpickInstructionsMap;
	@Nullable
	private final HUQRCode unpickToHU;

	//
	// State
	private final ArrayList<PickingCandidate> unprocessedPickingCandidates = new ArrayList<>();

	@Builder
	private PickingJobUnPickCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingCandidateService pickingCandidateService,
			final @NonNull HUQRCodesService huQRCodesService,
			//
			final @NonNull PickingJob pickingJob,
			final @Nullable PickingJobStepId onlyPickingJobStepId,
			final @Nullable PickingJobStepPickFromKey onlyPickFromKey,
			final @Nullable HUQRCode unpickToHU)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingCandidateService = pickingCandidateService;
		this.huQRCodesService = huQRCodesService;

		this.initialPickingJob = pickingJob;

		this.unpickToHU = unpickToHU;

		final Stream<StepUnpickInstructions> unpickInstructionsStream;
		if (onlyPickingJobStepId != null)
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
		final PickingJob pickingJob = initialPickingJob.withChangedSteps(unpickInstructionsMap.keySet(), this::unpickStep);
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
			final PickingJobStepPickFromKey pickFromKey = unpickInstructions.getPickFromKey();
			changedStep = unpickStep(changedStep, pickFromKey);
		}

		if (changedStep.isGeneratedOnFly() && changedStep.isNothingPicked())
		{
			return null;
		}

		return changedStep;
	}

	private PickingJobStep unpickStep(
			@NonNull final PickingJobStep step,
			@NonNull final PickingJobStepPickFromKey pickFromKey)
	{
		final PickingJobStepPickFrom pickFrom = step.getPickFrom(pickFromKey);
		final PickingJobStepPickedTo pickedTo = pickFrom.getPickedTo();
		if (pickedTo == null)
		{
			return step;
		}

		for (final PickingJobStepPickedToHU pickedToHU : pickedTo.getActualPickedHUs())
		{
			final PickingCandidate unprocessedPickingCandidate = pickingCandidateService.unprocess(pickedToHU.getPickingCandidateId())
					.getSinglePickingCandidate();
			unprocessedPickingCandidates.add(unprocessedPickingCandidate);
		}

		moveToTargetHU(pickedTo.getActualPickedHUs());

		return step.reduceWithUnpickEvent(
				pickFromKey,
				PickingJobStepUnpickInfo.builder().build());
	}

	private void moveToTargetHU(@NonNull final ImmutableList<PickingJobStepPickedToHU> pickedToHUs)
	{
		if (unpickToHU == null)
		{
			return;
		}

		final ImmutableList<HUIdAndQRCode> huIdAndQRCodeList = pickedToHUs.stream()
				.map(PickingJobStepPickedToHU::getActualPickedHUId)
				.map(HUIdAndQRCode::ofHuId)
				.collect(ImmutableList.toImmutableList());

		MoveHUCommand.builder()
				.huQRCodesService(huQRCodesService)
				.husToMove(huIdAndQRCodeList)
				.targetQRCode(unpickToHU.toGlobalQRCode())
				.build()
				.execute();
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
	}
}
