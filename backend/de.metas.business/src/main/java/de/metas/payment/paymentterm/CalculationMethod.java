package de.metas.payment.paymentterm;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_PaymentTerm;

@AllArgsConstructor
public enum CalculationMethod implements ReferenceListAwareEnum
{
	BaseLineDatePlusXDays(X_C_PaymentTerm.CALCULATIONMETHOD_BaseLineDatePlusXDays), // BLDX
	BaseLineDatePlusXDaysAndThenEndOfMonth(X_C_PaymentTerm.CALCULATIONMETHOD_BaseLineDatePlusXDaysAndThenEndOfMonth), // BLDXE
	EndOfTheMonthOfBaselineDatePlusXDays(X_C_PaymentTerm.CALCULATIONMETHOD_EndOfTheMonthOfBaselineDatePlusXDays) // EBLDX
	;

	private static final ReferenceListAwareEnums.ValuesIndex<CalculationMethod> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@NonNull
	public static CalculationMethod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@NonNull
	public static CalculationMethod ofName(@NonNull final String name)
	{
		try
		{
			return CalculationMethod.valueOf(name);
		}
		catch (final Throwable t)
		{
			throw new AdempiereException("No " + CalculationMethod.class + " found for name: " + name)
					.appendParametersToMessage()
					.setParameter("AdditionalErrorMessage", t.getMessage());
		}
	}
}
