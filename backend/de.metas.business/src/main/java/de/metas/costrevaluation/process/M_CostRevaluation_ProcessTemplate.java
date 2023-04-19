package de.metas.costrevaluation.process;

import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

abstract class M_CostRevaluation_ProcessTemplate extends JavaProcess implements IProcessPrecondition
{
	protected final CostRevaluationService costRevaluationService = SpringContextHolder.instance.getBean(CostRevaluationService.class);

	protected final ProcessPreconditionsResolution acceptIfDraft(final @NonNull IProcessPreconditionsContext context)
	{
		final CostRevaluationId costRevaluationId = getCostRevaluationId(context);
		return costRevaluationService.isDraftedDocument(costRevaluationId)
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("Already in progress/finished.");
	}

	protected final ProcessPreconditionsResolution acceptIfHasActiveLines(final @NonNull IProcessPreconditionsContext context)
	{
		final CostRevaluationId costRevaluationId = getCostRevaluationId(context);
		return costRevaluationService.hasActiveLines(costRevaluationId)
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("No active lines found");
	}

	protected final ProcessPreconditionsResolution acceptIfDoesNotHaveActiveLines(final @NonNull IProcessPreconditionsContext context)
	{
		final CostRevaluationId costRevaluationId = getCostRevaluationId(context);
		return !costRevaluationService.hasActiveLines(costRevaluationId)
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("Active lines found");
	}

	protected final CostRevaluationId getCostRevaluationId()
	{
		return CostRevaluationId.ofRepoId(getRecord_ID());
	}

	@NonNull
	private static CostRevaluationId getCostRevaluationId(final @NonNull IProcessPreconditionsContext context)
	{
		return CostRevaluationId.ofRepoId(context.getSingleSelectedRecordId());
	}

}
