package de.metas.order.costs;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_Cost_Type;

@AllArgsConstructor
public enum CostCalculationMethod implements ReferenceListAwareEnum
{
	FixedAmount(X_C_Cost_Type.COSTCALCULATIONMETHOD_FixedAmount),
	PercentageOfAmount(X_C_Cost_Type.COSTCALCULATIONMETHOD_PercentageOfAmount);

	@Getter @NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<CostCalculationMethod> index = ReferenceListAwareEnums.index(values());

	public static CostCalculationMethod ofCode(@NonNull final String code) {return index.ofCode(code);}
}
