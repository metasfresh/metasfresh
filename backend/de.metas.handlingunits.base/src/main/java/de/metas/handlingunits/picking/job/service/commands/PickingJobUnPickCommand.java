package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.job.model.HUInfo;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedInfo;
import de.metas.handlingunits.picking.job.model.PickingJobStepUnpickInfo;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

public class PickingJobUnPickCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull private final PickingCandidateService pickingCandidateService;

	//
	// Params
	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final ImmutableList<PickingJobStep> initialSteps;

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
			final @Nullable PickingJobStepId onlyPickingJobStepId)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
		this.pickingCandidateService = pickingCandidateService;

		this.initialPickingJob = pickingJob;
		if (onlyPickingJobStepId != null)
		{
			this.initialSteps = ImmutableList.of(initialPickingJob.getStepById(onlyPickingJobStepId));
		}
		else
		{
			this.initialSteps = initialPickingJob.streamSteps()
					.filter(PickingJobStep::isPicked)
					.collect(ImmutableList.toImmutableList());
		}
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();

		if (!initialSteps.isEmpty())
		{
			initialSteps.forEach(PickingJobStep::assertPicked);

			return trxManager.callInThreadInheritedTrx(this::executeInTrx);
		}
		else
		{
			return initialPickingJob;
		}
	}

	private PickingJob executeInTrx()
	{
		final ImmutableSet<PickingJobStepId> stepIds = initialSteps.stream().map(PickingJobStep::getId).collect(ImmutableSet.toImmutableSet());
		final PickingJob pickingJob = initialPickingJob.withChangedSteps(stepIds, this::unpickStep);
		pickingJobRepository.save(pickingJob);

		pickingCandidateService.deleteDraftPickingCandidates(unprocessedPickingCandidates);

		stepIds.forEach(stepId -> pickingJobHUReservationService.reservePickFromHU(pickingJob, stepId));

		return pickingJob;
	}

	private PickingJobStep unpickStep(final PickingJobStep step)
	{
		final PickingJobStepPickedInfo picked = step.getPicked();
		if (picked == null)
		{
			return step;
		}

		final PickingCandidate unprocessedPickingCandidate = pickingCandidateService.unprocess(picked.getPickingCandidateId())
				.getSinglePickingCandidate();
		unprocessedPickingCandidates.add(unprocessedPickingCandidate);

		// NOTE: Pick From HU might be changed as a result of unprocessing in case the original Pick From HU was destroyed
		final HuId pickFromHUId = Objects.requireNonNull(unprocessedPickingCandidate.getPickFrom().getHuId());

		return step.reduceWithUnpickEvent(PickingJobStepUnpickInfo.builder()
				.pickFromHU(HUInfo.builder()
						.id(pickFromHUId)
						.barcode(HUBarcode.ofHuId(pickFromHUId))
						.build())
				.build());
	}
}
