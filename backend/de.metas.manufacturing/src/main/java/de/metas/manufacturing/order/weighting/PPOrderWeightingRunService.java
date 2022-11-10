package de.metas.manufacturing.order.weighting;

import lombok.NonNull;
import org.springframework.stereotype.Service;

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
		throw new UnsupportedOperationException(); // TODO
	}

	public int getNextLineNo(final PPOrderWeightingRunId weightingRunId)
	{
		return ppOrderWeightingRunRepository.getNextLineNo(weightingRunId);
	}

	public void updateFromChecks(final PPOrderWeightingRunId weightingRunId)
	{
		// TODO
		// * assert not processed
		// * retrieve lines
		// * check them and compute IsToleranceExeeded
	}
}
