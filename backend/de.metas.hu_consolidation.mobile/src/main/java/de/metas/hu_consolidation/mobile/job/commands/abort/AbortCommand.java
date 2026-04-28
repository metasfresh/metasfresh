package de.metas.hu_consolidation.mobile.job.commands.abort;

import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobId;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobRepository;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobStatus;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTargetCloser;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

public class AbortCommand
{
	// services
	@NonNull private final HUConsolidationJobRepository jobRepository;
	@NonNull private final HUConsolidationTargetCloser targetCloser;

	// params
	@NonNull private final HUConsolidationJobId jobId;
	@NonNull private final UserId callerId;

	@Builder
	private AbortCommand(
			@NonNull final HUConsolidationJobRepository jobRepository,
			@NonNull final HUConsolidationTargetCloser targetCloser,
			//
			@NonNull final HUConsolidationJobId jobId,
			@NonNull final UserId callerId)
	{
		this.jobRepository = jobRepository;
		this.targetCloser = targetCloser;
		this.jobId = jobId;
		this.callerId = callerId;
	}

	public void execute()
	{
		jobRepository.updateById(jobId, this::voidIt);
	}

	private HUConsolidationJob voidIt(final HUConsolidationJob job0)
	{
		HUConsolidationJob job = job0;
		assertCanVoid(job);
		job = targetCloser.closeAssumingNoActualTarget(job);
		job = job.withDocStatus(HUConsolidationJobStatus.Voided);
		return job;
	}

	private void assertCanVoid(@NonNull final HUConsolidationJob job)
	{
		if (job.isProcessed())
		{
			throw new AdempiereException("Job is already processed");
		}

		job.assertUserCanEdit(callerId);
	}
}
