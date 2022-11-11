package de.metas.manufacturing.order.weighting;

import de.metas.uom.UomId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PPOrderWeightingRunService
{
	private final PPOrderWeightingRunRepository ppOrderWeightingRunRepository;

	public PPOrderWeightingRunService(
			@NonNull final PPOrderWeightingRunRepository ppOrderWeightingRunRepository)
	{
		this.ppOrderWeightingRunRepository = ppOrderWeightingRunRepository;
	}

	public PPOrderWeightingRun getById(@NonNull final PPOrderWeightingRunId id)
	{
		return ppOrderWeightingRunRepository.getById(id);
	}

	public void process(final PPOrderWeightingRunId id)
	{
		ppOrderWeightingRunRepository.updateById(id, PPOrderWeightingRun::process);
	}

	public void unprocess(final PPOrderWeightingRunId id)
	{
		ppOrderWeightingRunRepository.updateById(id, PPOrderWeightingRun::unprocess);
	}

	public int getNextLineNo(final PPOrderWeightingRunId weightingRunId)
	{
		return ppOrderWeightingRunRepository.getNextLineNo(weightingRunId);
	}

	public void updateFromChecks(final Collection<PPOrderWeightingRunId> ids)
	{
		// NOTE: we usually expect only one element here, so it's OK to iterate
		for (final PPOrderWeightingRunId id : ids)
		{
			ppOrderWeightingRunRepository.updateById(id, PPOrderWeightingRun::updateToleranceExceededFlag);
		}
	}

	public UomId getUomId(final PPOrderWeightingRunId id)
	{
		return ppOrderWeightingRunRepository.getUomId(id);
	}
}
