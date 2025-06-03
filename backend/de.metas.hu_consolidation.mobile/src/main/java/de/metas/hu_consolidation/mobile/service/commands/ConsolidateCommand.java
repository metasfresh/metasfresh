package de.metas.hu_consolidation.mobile.service.commands;

import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

public class ConsolidateCommand
{
	@NonNull private final HUConsolidationJob job;

	@Builder
	public ConsolidateCommand(
			@NonNull final ConsolidateRequest request)
	{
		this.job = request.getJob();
	}

	public HUConsolidationJob execute()
	{
		final HUConsolidationTarget target = job.getCurrentTarget();
		if (target == null)
		{
			throw new AdempiereException("No target was set for job " + job);
		}

		// TODO implement

		return job;
	}
}
