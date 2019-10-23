package de.metas.currency.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.compiere.model.I_C_Conversion_Rate;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Callout(I_C_Conversion_Rate.class)
public class C_Conversion_Rate
{
	/** Sets DivideRate = 1 / MultiplyRate */
	@CalloutMethod(columnNames = I_C_Conversion_Rate.COLUMNNAME_MultiplyRate)
	public void setDivideRateFromMultiplyRate(final I_C_Conversion_Rate conversionRate)
	{
		final BigDecimal multiplyRate = conversionRate.getMultiplyRate();
		// Do nothing if the value is dirty
		if (multiplyRate == null)
		{
			return;
		}
		
		final BigDecimal divideRate;
		if (multiplyRate.signum() != 0)
		{
			divideRate = BigDecimal.ONE.divide(multiplyRate, 12, RoundingMode.HALF_UP);
		}
		else
		{
			divideRate = BigDecimal.ZERO;
		}

		conversionRate.setDivideRate(divideRate);
	}

	/** Sets MultiplyRate = 1 / DivideRate */
	@CalloutMethod(columnNames = I_C_Conversion_Rate.COLUMNNAME_DivideRate)
	public void setMultiplyRateFromDivideRate(final I_C_Conversion_Rate conversionRate)
	{
		final BigDecimal divideRate = conversionRate.getDivideRate();
		// Do nothing if the value is dirty
		if (divideRate == null)
		{
			return;
		}
		
		final BigDecimal multiplyRate;
		if (divideRate.signum() != 0)
		{
			multiplyRate = BigDecimal.ONE.divide(divideRate, 12, RoundingMode.HALF_UP);
		}
		else
		{
			multiplyRate = BigDecimal.ZERO;
		}

		conversionRate.setMultiplyRate(multiplyRate);
	}
}
