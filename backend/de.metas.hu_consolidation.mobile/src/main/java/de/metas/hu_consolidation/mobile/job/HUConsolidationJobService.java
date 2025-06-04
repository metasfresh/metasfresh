package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.hu_consolidation.mobile.job.commands.abort.AbortCommand;
import de.metas.hu_consolidation.mobile.job.commands.complete.CompleteCommand;
import de.metas.hu_consolidation.mobile.job.commands.consolidate.ConsolidateCommand;
import de.metas.hu_consolidation.mobile.job.commands.consolidate.ConsolidateRequest;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HUConsolidationJobService
{
	@NonNull private final HUConsolidationJobRepository jobRepository;
	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final HUConsolidationAvailableTargetsFinder availableTargetsFinder;
	@NonNull private final HUConsolidationTargetCloser targetCloser;

	public HUConsolidationJob getJobById(final HUConsolidationJobId id)
	{
		return jobRepository.getById(id);
	}

	public List<HUConsolidationJob> getByNotProcessedAndResponsibleId(@NonNull final UserId userId)
	{
		return jobRepository.getByNotProcessedAndResponsibleId(userId);
	}
	
	public ImmutableSet<PickingSlotId> getInUsePickingSlotIds()
	{
		return jobRepository.getInUsePickingSlotIds();
	}


	public HUConsolidationJob startJob(
			@NonNull final HUConsolidationJobReference reference,
			@NonNull final UserId responsibleId)
	{
		return jobRepository.create(reference, responsibleId);
	}

	public HUConsolidationJob complete(@NonNull final HUConsolidationJob job)
	{
		return CompleteCommand.builder()
				.jobRepository(jobRepository)
				.targetCloser(targetCloser)
				//
				.job(job)
				//
				.build().execute();
	}

	public HUConsolidationJob assignJob(@NonNull final HUConsolidationJobId jobId, @NonNull final UserId callerId)
	{
		return jobRepository.updateById(jobId, job -> job.withResponsibleId(callerId));
	}

	public void abort(@NonNull final HUConsolidationJobId jobId, @NonNull final UserId callerId)
	{
		AbortCommand.builder()
				.jobRepository(jobRepository)
				.targetCloser(targetCloser)
				//
				.jobId(jobId)
				.callerId(callerId)
				//
				.build().execute();
	}

	public void abortAll(@NonNull final UserId callerId)
	{
		// TODO
	}

	public List<HUConsolidationTarget> getAvailableTargets(@NonNull final HUConsolidationJob job)
	{
		return availableTargetsFinder.getAvailableTargets(job.getCustomerId());
	}

	public HUConsolidationJob setTarget(@NonNull final HUConsolidationJobId jobId, @Nullable final HUConsolidationTarget target, @NonNull UserId callerId)
	{
		return jobRepository.updateById(jobId, job -> {
			job.assertUserCanEdit(callerId);
			return job.withCurrentTarget(target);
		});
	}

	public HUConsolidationJob closeTarget(@NonNull final HUConsolidationJobId jobId, @NonNull final UserId callerId)
	{
		return jobRepository.updateById(jobId, job -> {
			job.assertUserCanEdit(callerId);
			return targetCloser.closeTarget(job);
		});
	}

	public HUConsolidationJob consolidate(@NonNull final ConsolidateRequest request)
	{
		return ConsolidateCommand.builder()
				.jobRepository(jobRepository)
				.huQRCodesService(huQRCodesService)
				.pickingSlotService(pickingSlotService)
				.request(request)
				.build()
				.execute();
	}

}
