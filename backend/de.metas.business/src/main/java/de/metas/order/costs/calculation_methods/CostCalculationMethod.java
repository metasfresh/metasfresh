package de.metas.order.costs.calculation_methods;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Cost_Type;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum CostCalculationMethod implements ReferenceListAwareEnum
{
	FixedAmount(X_C_Cost_Type.COSTCALCULATIONMETHOD_FixedAmount),
	PercentageOfAmount(X_C_Cost_Type.COSTCALCULATIONMETHOD_PercentageOfAmount);

	@Getter @NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<CostCalculationMethod> index = ReferenceListAwareEnums.index(values());

	public static CostCalculationMethod ofCode(@NonNull final String code) {return index.ofCode(code);}

	public interface ParamsMapper<T>
	{
		T fixedAmount(FixedAmountCostCalculationMethodParams params);

		T percentageOfAmount(PercentageCostCalculationMethodParams params);
	}

	public <T> T mapOnParams(@Nullable final CostCalculationMethodParams params, @NonNull final ParamsMapper<T> mapper)
	{
		if (this == CostCalculationMethod.FixedAmount)
		{
			return mapper.fixedAmount(castParams(params, FixedAmountCostCalculationMethodParams.class));
		}
		else if (this == CostCalculationMethod.PercentageOfAmount)
		{
			return mapper.percentageOfAmount(castParams(params, PercentageCostCalculationMethodParams.class));
		}
		else
		{
			throw new AdempiereException("Calculation method not handled: " + this);
		}
	}

	public <T extends CostCalculationMethodParams> T castParams(@Nullable final CostCalculationMethodParams params, @NonNull final Class<T> type)
	{
		if (params == null)
		{
			throw new AdempiereException("No calculation method parameters provided for " + type.getSimpleName());
		}
		return type.cast(params);
	}

	public interface CaseMapper<T>
	{
		T fixedAmount();

		T percentageOfAmount();
	}

	public <T> T map(@NonNull final CaseMapper<T> mapper)
	{
		if (this == CostCalculationMethod.FixedAmount)
		{
			return mapper.fixedAmount();
		}
		else if (this == CostCalculationMethod.PercentageOfAmount)
		{
			return mapper.percentageOfAmount();
		}
		else
		{
			throw new AdempiereException("Calculation method not handled: " + this);
		}
	}

}
