package de.metas.payment.paymentterm;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_PaymentTerm;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

public enum CalculationMethod implements ReferenceListAwareEnum
{
	BaseLineDatePlusXDays(X_C_PaymentTerm.CALCULATIONMETHOD_BaseLineDatePlusXDays), // BLDX
	BaseLineDatePlusXDaysAndThenEndOfMonth(X_C_PaymentTerm.CALCULATIONMETHOD_BaseLineDatePlusXDaysAndThenEndOfMonth), // BLDXE
	EndOfTheMonthOfBaselineDatePlusXDays(X_C_PaymentTerm.CALCULATIONMETHOD_EndOfTheMonthOfBaselineDatePlusXDays) // EBLDX
	;

	@Getter
	private final String code;

	CalculationMethod(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static CalculationMethod ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static Optional<CalculationMethod> optionalOfCode(@Nullable final String code)
	{
		return Optional.ofNullable(ofNullableCode(code));
	}

	public static CalculationMethod ofCode(@NonNull final String code)
	{
		final CalculationMethod type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + CalculationMethod.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final CalculationMethod type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, CalculationMethod> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), CalculationMethod::getCode);

}
