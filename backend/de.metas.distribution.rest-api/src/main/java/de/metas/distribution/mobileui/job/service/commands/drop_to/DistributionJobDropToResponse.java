package de.metas.distribution.mobileui.job.service.commands.drop_to;

import com.google.common.collect.Maps;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode
@ToString
public class DistributionJobDropToResponse
{
	private final Map<DistributionJobId, DistributionJob> jobsById;

	private DistributionJobDropToResponse(final Collection<DistributionJob> jobs)
	{
		this.jobsById = Maps.uniqueIndex(jobs, DistributionJob::getId);
	}

	public static DistributionJobDropToResponse ofList(final List<DistributionJob> jobs)
	{
		return new DistributionJobDropToResponse(jobs);
	}

	@NonNull
	public DistributionJob getJobById(@NonNull final DistributionJobId jobId)
	{
		final DistributionJob job = jobsById.get(jobId);
		if (job == null)
		{
			throw new AdempiereException("No job found for " + jobId);
		}
		return job;
	}
}
