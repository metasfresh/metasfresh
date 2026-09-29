package de.metas.costrevaluation.interceptor;

import de.metas.common.util.time.SystemTime;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.document.engine.DocStatus;
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

import java.util.Objects;

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
		// Default only on the UI path: a non-UI caller that omits the mandatory DateAcct must fail instead of silently posting today.
		if (record.getDateAcct() == null && InterfaceWrapperHelper.isUIAction(record))
		{
			record.setDateAcct(SystemTime.asDayTimestamp());
		}

		// Forward-only default on all paths: revaluate from the posting date unless the user picks an earlier date.
		if (record.getEvaluationStartDate() == null)
		{
			record.setEvaluationStartDate(record.getDateAcct());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE })
	void beforeChange(@NonNull final I_M_CostRevaluation record, @NonNull final ModelChangeType type)
	{
		if (type.isChange())
		{
			moveEvaluationStartDateAlongWithDateAcct(record);
		}

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

	/**
	 * Keeps a defaulted EvaluationStartDate (i.e. equal to the previous DateAcct) in step when the user changes DateAcct on a
	 * not yet processed document, so that moving the posting date does not silently turn the revaluation retrospective.
	 * An EvaluationStartDate that the user set on its own is left untouched.
	 */
	private static void moveEvaluationStartDateAlongWithDateAcct(@NonNull final I_M_CostRevaluation record)
	{
		if (!DocStatus.ofNullableCodeOrUnknown(record.getDocStatus()).isDraftedOrInProgress()
				|| !InterfaceWrapperHelper.isValueChanged(record, I_M_CostRevaluation.COLUMNNAME_DateAcct))
		{
			return;
		}

		final I_M_CostRevaluation recordOld = InterfaceWrapperHelper.createOld(record, I_M_CostRevaluation.class);
		final boolean evaluationStartDateWasDefaulted = Objects.equals(recordOld.getEvaluationStartDate(), recordOld.getDateAcct());
		final boolean evaluationStartDateChangedByUser = !Objects.equals(record.getEvaluationStartDate(), recordOld.getEvaluationStartDate());
		if (evaluationStartDateWasDefaulted && !evaluationStartDateChangedByUser)
		{
			record.setEvaluationStartDate(record.getDateAcct());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	void beforeDelete(final I_M_CostRevaluation record)
	{
		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID());
		costRevaluationService.deleteLinesAndDetailsByRevaluationId(costRevaluationId);
	}
}
