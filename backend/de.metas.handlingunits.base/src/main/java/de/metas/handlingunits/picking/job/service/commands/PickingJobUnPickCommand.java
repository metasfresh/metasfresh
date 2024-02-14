package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
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
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
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
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull private final PickingCandidateService pickingCandidateService;

	//
	// Params
	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final ImmutableListMultimap<PickingJobStepId, StepUnpickInstructions> unpickInstructionsMap;

	//
	// State
	private final ArrayList<PickingCandidate> unprocessedPickingCandidates = new ArrayList<>();

	@Builder
	private PickingJobUnPickCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJobHUReservationService pickingJobHUReservationService,
			final @NonNull PickingCandidateService pickingCandidateService,
			//
			final @NonNull PickingJob pickingJob,
			final @Nullable PickingJobStepId onlyPickingJobStepId,
			final @Nullable PickingJobStepPickFromKey onlyPickFromKey)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
		this.pickingCandidateService = pickingCandidateService;

		this.initialPickingJob = pickingJob;

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

	private PickingJobStep unpickStep(@NonNull final PickingJobStep step)
	{
		final ImmutableList<StepUnpickInstructions> unpickInstructionsList = this.unpickInstructionsMap.get(step.getId());

		PickingJobStep changedStep = step;
		for (final StepUnpickInstructions unpickInstructions : unpickInstructionsList)
		{
			final PickingJobStepPickFromKey pickFromKey = unpickInstructions.getPickFromKey();
			changedStep = unpickStep(changedStep, pickFromKey);
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

		return step.reduceWithUnpickEvent(
				pickFromKey,
				PickingJobStepUnpickInfo.builder().build());
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
