package de.metas.cucumber.stepdefs.costing;

import de.metas.costrevaluation.CostRevaluationId;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import org.compiere.model.I_M_CostRevaluation;

/**
 * Stores {@link I_M_CostRevaluation} cost-revaluation document headers by identifier for cross-step reference.
 */
public class M_CostRevaluation_StepDefData extends StepDefData<I_M_CostRevaluation>
		implements StepDefDataGetIdAware<CostRevaluationId, I_M_CostRevaluation>
{
	public M_CostRevaluation_StepDefData()
	{
		super(I_M_CostRevaluation.class);
	}

	@Override
	public CostRevaluationId extractIdFromRecord(final I_M_CostRevaluation record)
	{
		return CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID());
	}
}
