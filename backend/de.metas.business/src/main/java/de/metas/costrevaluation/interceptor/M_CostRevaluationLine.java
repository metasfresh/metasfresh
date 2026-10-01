package de.metas.costrevaluation.interceptor;

import de.metas.costrevaluation.CostRevaluation;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.costrevaluation.CostRevaluationService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
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

	/**
	 * In a draft manual revaluation, {@code DeltaAmt = CurrentQty × (NewCostPrice − CurrentCostPrice)}; a new {@code NewCostPrice} also discards the line's evaluation.
	 */
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_M_CostRevaluationLine.COLUMNNAME_CurrentQty,
					I_M_CostRevaluationLine.COLUMNNAME_CurrentCostPrice,
					I_M_CostRevaluationLine.COLUMNNAME_NewCostPrice })
	void updateDeltaAmt(@NonNull final I_M_CostRevaluationLine record, @NonNull final ModelChangeType changeType)
	{
		final CostRevaluation costRevaluation = costRevaluationService.getById(CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID()));
		if (costRevaluation.getRevaluationSource().isCopyFromCostElement()
				|| !costRevaluation.getDocStatus().isDraftedOrInProgress())
		{
			return;
		}

		record.setDeltaAmt(record.getCurrentQty().multiply(record.getNewCostPrice().subtract(record.getCurrentCostPrice())));

		if (changeType.isChange() && InterfaceWrapperHelper.isValueChanged(record, I_M_CostRevaluationLine.COLUMNNAME_NewCostPrice))
		{
			record.setIsRevaluated(false);
			costRevaluationService.deleteDetailsByLineId(extractLineId(record));
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	void beforeDelete(final I_M_CostRevaluationLine record)
	{
		costRevaluationService.deleteDetailsByLineId(extractLineId(record));
	}

	@NonNull
	private static CostRevaluationLineId extractLineId(@NonNull final I_M_CostRevaluationLine record)
	{
		return CostRevaluationLineId.ofRepoId(record.getM_CostRevaluation_ID(), record.getM_CostRevaluationLine_ID());
	}
}
