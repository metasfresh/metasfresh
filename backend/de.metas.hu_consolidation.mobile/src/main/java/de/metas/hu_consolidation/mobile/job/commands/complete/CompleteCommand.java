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

	/**
	 * @return the completed job and the label print of the closed target, still to be run (see {@link HUConsolidationTargetCloser#closeTarget(HUConsolidationJob)})
	 */
	public HUConsolidationTargetCloser.CloseTargetResult execute()
	{
		if (job.isProcessed())
		{
			throw new AdempiereException("Job is already processed");
		}

		final HUConsolidationTargetCloser.CloseTargetResult closed = targetCloser.closeTarget(job);
		job = closed.getJob().withDocStatus(HUConsolidationJobStatus.Completed);
		jobRepository.save(job);
		return new HUConsolidationTargetCloser.CloseTargetResult(job, closed.getPrintLabelAction());
	}
}
