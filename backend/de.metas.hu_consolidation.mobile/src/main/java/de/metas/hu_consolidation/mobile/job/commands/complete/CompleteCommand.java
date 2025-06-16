package de.metas.hu_consolidation.mobile.job.commands.complete;

import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobRepository;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobStatus;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTargetCloser;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

public class CompleteCommand
{
	// services
	@NonNull private final HUConsolidationJobRepository jobRepository;
	@NonNull private final HUConsolidationTargetCloser targetCloser;

	// params
	@NonNull private HUConsolidationJob job;

	@Builder
	private CompleteCommand(
			@NonNull final HUConsolidationJobRepository jobRepository,
			@NonNull final HUConsolidationTargetCloser targetCloser,
			//
			@NonNull final HUConsolidationJob job)
	{
		this.jobRepository = jobRepository;
		this.targetCloser = targetCloser;
		this.job = job;
	}

	public HUConsolidationJob execute()
	{
		if (job.isProcessed())
		{
			throw new AdempiereException("Job is already processed");
		}

		job = targetCloser.closeTarget(job);
		job = job.withDocStatus(HUConsolidationJobStatus.Completed);
		jobRepository.save(job);
		return job;
	}
}
