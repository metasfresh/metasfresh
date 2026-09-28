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
		// UI-path defaulting safety net (docs/coding-rules/architecture.md §3, "Callout vs interceptor"):
		// guard with isUIAction so ONLY the WebUI path is defaulted. Prevents: a backdated REST/import/OLCand
		// caller that omits DateAcct being silently posted with today's date (wrong accounting period) — without
		// the guard it fails loud instead (DateAcct is mandatory, so the null save is rejected). On the WebUI
		// path this branch is a redundant net anyway: the AD_Column default @#Date@ already populates DateAcct
		// at document-init, before this beforeNew fires.
		if (record.getDateAcct() == null && InterfaceWrapperHelper.isUIAction(record))
		{
			record.setDateAcct(SystemTime.asDayTimestamp());
		}

		// Forward-only default: needs NO isUIAction guard (data-integrity invariant on all write paths) — it
		// derives from the caller-supplied DateAcct, never from wall-clock, so defaulting EvaluationStartDate to
		// the posting date is the correct forward-only behaviour on every path (a revaluation restates nothing
		// already posted unless the user explicitly picks an earlier EvaluationStartDate).
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
