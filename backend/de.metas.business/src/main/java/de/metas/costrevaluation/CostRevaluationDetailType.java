package de.metas.costrevaluation;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_CostRevaluation_Detail;

@AllArgsConstructor
public enum CostRevaluationDetailType implements ReferenceListAwareEnum
{
	CurrentCostBeforeRevaluation(X_M_CostRevaluation_Detail.REVALUATIONTYPE_CurrentCostBeforeRevaluation),
	CostDetailAdjustment(X_M_CostRevaluation_Detail.REVALUATIONTYPE_CostDetailAdjustment),
	CurrentCostAfterRevaluation(X_M_CostRevaluation_Detail.REVALUATIONTYPE_CurrentCostAfterRevaluation),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<CostRevaluationDetailType> index = ReferenceListAwareEnums.index(values());

	@Getter @NonNull private final String code;

	public static CostRevaluationDetailType ofCode(@NonNull final String code) {return index.ofCode(code);}
}
