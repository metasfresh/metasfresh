package de.metas.costrevaluation.interceptor;

import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.costrevaluation.CostRevaluationService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_CostRevaluationLine.class)
@Component
class M_CostRevaluationLine
{
	private final CostRevaluationService costRevaluationService;

	M_CostRevaluationLine(@NonNull final CostRevaluationService costRevaluationService)
	{
		this.costRevaluationService = costRevaluationService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	void beforeDelete(final I_M_CostRevaluationLine record)
	{
		final CostRevaluationLineId lineId = CostRevaluationLineId.ofRepoId(record.getM_CostRevaluation_ID(), record.getM_CostRevaluationLine_ID());
		costRevaluationService.deleteDetailsByLineId(lineId);
	}
}
