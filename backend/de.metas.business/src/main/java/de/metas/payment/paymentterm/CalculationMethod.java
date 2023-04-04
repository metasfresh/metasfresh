package de.metas.payment.paymentterm;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_C_PaymentTerm;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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


	public boolean isBaseLineDatePlusXDays()
	{
		return this == BaseLineDatePlusXDays;
	}

	public boolean isBaseLineDatePlusXDaysAndThenEndOfMonth()
	{
		return this == BaseLineDatePlusXDaysAndThenEndOfMonth;
	}

	public boolean isEndOfTheMonthOfBaselineDatePlusXDays()
	{
		return this == EndOfTheMonthOfBaselineDatePlusXDays;
	}
}
