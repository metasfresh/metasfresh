package de.metas.order.costs;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_Cost_Type;

@AllArgsConstructor
public enum CostDistributionMethod implements ReferenceListAwareEnum
{
	Amount(X_C_Cost_Type.COSTDISTRIBUTIONMETHOD_Amount),
	Quantity(X_C_Cost_Type.COSTDISTRIBUTIONMETHOD_Quantity);

	@Getter @NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<CostDistributionMethod> index = ReferenceListAwareEnums.index(values());

	public static CostDistributionMethod ofCode(@NonNull final String code) {return index.ofCode(code);}
}
