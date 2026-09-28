package de.metas.costrevaluation.interceptor;

import de.metas.common.util.time.SystemTime;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_CostRevaluation.class)
@Component
class M_CostRevaluation
{
	private static final AdMessageKey MSG_DeleteLinesFirstError = AdMessageKey.of("M_CostRevaluation.DeleteLinesFirstError");
	private final CostRevaluationService costRevaluationService;

	M_CostRevaluation(@NonNull final CostRevaluationService costRevaluationService)
	{
		this.costRevaluationService = costRevaluationService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	void beforeNew(@NonNull final I_M_CostRevaluation record)
	{
		// Safety net: DateAcct is mandatory, but we can't rely on its AD_Column/AD_Field default having been
		// applied by the caller (WebUI, REST, import) before this interceptor fires. Without this, an unset
		// DateAcct would make the EvaluationStartDate default below silently fall back to NULL (retrospective).
		if (record.getDateAcct() == null)
		{
			record.setDateAcct(SystemTime.asDayTimestamp());
		}

		// Forward-only default: a new manual revaluation restates nothing already posted unless the user
		// explicitly picks an earlier EvaluationStartDate. Default it to the posting date (DateAcct) when unset.
		if (record.getEvaluationStartDate() == null)
		{
			record.setEvaluationStartDate(record.getDateAcct());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE })
	void beforeChange(@NonNull final I_M_CostRevaluation record, @NonNull final ModelChangeType type)
	{
		if (type.isChange()
				&& InterfaceWrapperHelper.isValueChanged(record,
				I_M_CostRevaluation.COLUMNNAME_C_AcctSchema_ID,
				I_M_CostRevaluation.COLUMNNAME_M_CostElement_ID,
				I_M_CostRevaluation.COLUMNNAME_EvaluationStartDate)
				&& costRevaluationService.hasActiveLines(CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID()))
		)
		{
			throw new AdempiereException(MSG_DeleteLinesFirstError);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	void beforeDelete(final I_M_CostRevaluation record)
	{
		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID());
		costRevaluationService.deleteLinesAndDetailsByRevaluationId(costRevaluationId);
	}
}
