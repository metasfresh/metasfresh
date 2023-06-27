package de.metas.costrevaluation.process;

import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

public class M_CostRevaluation_Run extends M_CostRevaluation_ProcessTemplate
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return context.acceptIfSingleSelection()
				.and(() -> acceptIfDraft(context))
				.and(() -> acceptIfHasActiveLines(context))
				.toInternal();
	}

	@Override
	protected String doIt()
	{
		costRevaluationService.createDetails(getCostRevaluationId());
		return MSG_OK;
	}
}
