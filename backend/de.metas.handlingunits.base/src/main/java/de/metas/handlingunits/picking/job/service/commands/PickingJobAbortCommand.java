package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.stream.Stream;

public class PickingJobAbortCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;

	@NonNull private final ImmutableList<PickingJob> initialPickingJobs;

	@Builder
	private PickingJobAbortCommand(
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJobLockService pickingJobLockService,
			final @NonNull PickingJobSlotService pickingSlotService,
			final @NonNull PickingJobHUReservationService pickingJobHUReservationService,
			//
			final @Singular @NonNull List<PickingJob> pickingJobs)
	{
		Check.assumeNotEmpty(pickingJobs, "no picking jobs provided");

		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingSlotService = pickingSlotService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;

		this.initialPickingJobs = ImmutableList.copyOf(pickingJobs);
	}

	public PickingJob executeAndGetSingleResult()
	{
		if (initialPickingJobs.size() != 1)
		{
			throw new AdempiereException("Only one picking job expected");
		}

		final ImmutableList<PickingJob> result = execute();
		return CollectionUtils.singleElement(result);
	}

	public ImmutableList<PickingJob> execute()
	{
		initialPickingJobs.forEach(PickingJob::assertNotProcessed);
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private ImmutableList<PickingJob> executeInTrx()
	{
		final ImmutableList.Builder<PickingJob> result = ImmutableList.builder();
		for (final PickingJob initialPickingJob : initialPickingJobs)
		{
			final PickingJob pickingJob = execute(initialPickingJob);
			result.add(pickingJob);
		}
		return result.build();
	}

	private PickingJob execute(@NonNull final PickingJob initialPickingJob)
	{
		//noinspection OptionalGetWithoutIsPresent
		return Stream.of(initialPickingJob)
				.sequential()
				.map(this::releasePickingSlotAndSave)
				// NOTE: abort is not "reversing", so we don't have to reverse (unpick) what we picked.
				// Even more, imagine that those picked things are already phisically splitted out.
				//.map(this::unpickAllStepsAndSave)
				.peek(pickingJobHUReservationService::releaseAllReservations)
				.peek(pickingJobLockService::unlockShipmentSchedules)
				.map(this::markAsVoidedAndSave)
				.findFirst()
				.get();
	}

	private PickingJob markAsVoidedAndSave(PickingJob pickingJob)
	{
		pickingJob = pickingJob.withDocStatus(PickingJobDocStatus.Voided);
		pickingJobRepository.save(pickingJob);
		return pickingJob;
	}

	// private PickingJob unpickAllStepsAndSave(final PickingJob pickingJob)
	// {
	// 	return PickingJobUnPickCommand.builder()
	// 			.pickingJobRepository(pickingJobRepository)
	// 			.pickingCandidateService(pickingCandidateService)
	// 			.pickingJob(pickingJob)
	// 			.build()
	// 			.execute();
	// }

	private PickingJob releasePickingSlotAndSave(PickingJob pickingJob)
	{
		final PickingJobId pickingJobId = pickingJob.getId();
		final PickingSlotId pickingSlotId = pickingJob.getPickingSlotId().orElse(null);
		if (pickingSlotId != null)
		{
			pickingJob = pickingJob.withPickingSlot(null);
			pickingJobRepository.save(pickingJob);

			pickingSlotService.release(pickingSlotId, pickingJobId);
		}

		return pickingJob;
	}
}
