package de.metas.manufacturing.order.weighting.run;

import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

	public SeqNo getNextLineNo(final PPOrderWeightingRunId weightingRunId)
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

	public PPOrderWeightingRunCheckId addRunCheck(
			@NonNull PPOrderWeightingRunId weightingRunId,
			@NonNull final BigDecimal weightBD)
	{
		PPOrderWeightingRun weightingRun = getById(weightingRunId);
		final Quantity weight = Quantity.of(weightBD, weightingRun.getUOM());
		final SeqNo lineNo = weightingRun.getNextLineNo();
		final OrgId orgId = weightingRun.getOrgId();
		final PPOrderWeightingRunCheckId weightingRunCheckId = ppOrderWeightingRunRepository.addRunCheck(weightingRunId, lineNo, weight, orgId);

		weightingRun = getById(weightingRunId);
		weightingRun.updateToleranceExceededFlag();
		ppOrderWeightingRunRepository.save(weightingRun);

		return weightingRunCheckId;
	}
}
