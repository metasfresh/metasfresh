package de.metas.manufacturing.workflows_api;

import de.metas.manufacturing.job.ManufacturingJob;
import de.metas.manufacturing.job.ManufacturingJobReference;
import de.metas.manufacturing.job.ManufacturingJobService;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.eevolution.api.PPOrderId;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class ManufacturingRestService
{
	private final ManufacturingJobService manufacturingJobService;

	public ManufacturingRestService(final ManufacturingJobService manufacturingJobService)
	{
		this.manufacturingJobService = manufacturingJobService;
	}

	public Stream<ManufacturingJobReference> streamJobReferencesForUser(final @NonNull UserId responsibleId, final @NonNull QueryLimit suggestedLimit)
	{
		return manufacturingJobService.streamJobReferencesForUser(responsibleId, suggestedLimit);
	}

	public ManufacturingJob createJob(final PPOrderId ppOrderId, final UserId responsibleId)
	{
		return manufacturingJobService.createJob(ppOrderId, responsibleId);
	}

	public ManufacturingJob getJobById(final PPOrderId ppOrderId)
	{
		return manufacturingJobService.getJobById(ppOrderId);
	}

}
