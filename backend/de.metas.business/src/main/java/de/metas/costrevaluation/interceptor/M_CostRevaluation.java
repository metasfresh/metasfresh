package de.metas.costrevaluation.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.common.util.time.SystemTime;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.costrevaluation.RevaluationSource;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_CostRevaluation.class)
@Component
@RequiredArgsConstructor
public class M_CostRevaluation
{
	private static final AdMessageKey MSG_DeleteLinesFirstError = AdMessageKey.of("M_CostRevaluation.DeleteLinesFirstError");

	@NonNull private final CostRevaluationService costRevaluationService;

	private static final ImmutableList<String> COLUMNNAMES_RequireNoLines = ImmutableList.of(
			I_M_CostRevaluation.COLUMNNAME_C_AcctSchema_ID,
			I_M_CostRevaluation.COLUMNNAME_M_CostElement_ID,
			// these two also define which lines get generated (the source path + the source element),
			// so changing either once lines exist would silently desync the lines from the header
			I_M_CostRevaluation.COLUMNNAME_RevaluationSource,
			I_M_CostRevaluation.COLUMNNAME_CopyFrom_M_CostElement_ID);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	void beforeNew(@NonNull final I_M_CostRevaluation record)
	{
		// Default only on the UI path: a non-UI caller that omits the mandatory DateAcct must fail instead of silently posting today.
		if (record.getDateAcct() == null && InterfaceWrapperHelper.isUIAction(record))
		{
			record.setDateAcct(SystemTime.asDayTimestamp());
		}

		if (isManual(record) || record.getEvaluationStartDate() == null)
		{
			record.setEvaluationStartDate(record.getDateAcct());
		}

		// a CopyFromCostElement revaluation keeps its target element choice
		if (record.getM_CostElement_ID() <= 0 && isManual(record))
		{
			costRevaluationService.findPresetCostElement(ClientId.ofRepoId(record.getAD_Client_ID()), AcctSchemaId.ofRepoIdOrNull(record.getC_AcctSchema_ID()))
					.ifPresent(costElementId -> record.setM_CostElement_ID(costElementId.getRepoId()));
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE })
	void beforeChange(@NonNull final I_M_CostRevaluation record, @NonNull final ModelChangeType type)
	{
		final boolean isManual = isManual(record);
		if (isManual
				// RevaluationSource: a draft switched away from CopyFromCostElement must drop its cut-off date
				&& InterfaceWrapperHelper.isValueChanged(record, I_M_CostRevaluation.COLUMNNAME_DateAcct, I_M_CostRevaluation.COLUMNNAME_EvaluationStartDate, I_M_CostRevaluation.COLUMNNAME_RevaluationSource))
		{
			record.setEvaluationStartDate(record.getDateAcct());
		}

		if (!type.isChange())
		{
			return;
		}

		// the evaluation start date is the cut-off date only for CopyFromCostElement; otherwise it just follows the posting date
		final ImmutableList<String> columnNamesRequiringNoLines = isManual
				? COLUMNNAMES_RequireNoLines
				: ImmutableList.<String>builder().addAll(COLUMNNAMES_RequireNoLines).add(I_M_CostRevaluation.COLUMNNAME_EvaluationStartDate).build();
		final ImmutableList<String> changedColumnNames = columnNamesRequiringNoLines.stream()
				.filter(columnName -> InterfaceWrapperHelper.isValueChanged(record, columnName))
				.collect(ImmutableList.toImmutableList());
		if (!changedColumnNames.isEmpty()
				&& costRevaluationService.hasActiveLines(CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID())))
		{
			final ITranslatableString changedFieldNames = changedColumnNames.stream()
					.map(TranslatableStrings::adElementOrMessage)
					.collect(TranslatableStrings.joining(", "));
			throw new AdempiereException(MSG_DeleteLinesFirstError, changedFieldNames);
		}
	}

	/** An unset source counts as {@code Manual}, the column's default. */
	private static boolean isManual(@NonNull final I_M_CostRevaluation record)
	{
		final RevaluationSource revaluationSource = RevaluationSource.ofNullableCode(record.getRevaluationSource());
		return revaluationSource == null || revaluationSource.isManual();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	void beforeDelete(final I_M_CostRevaluation record)
	{
		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID());
		costRevaluationService.deleteLinesAndDetailsByRevaluationId(costRevaluationId);
	}
}
