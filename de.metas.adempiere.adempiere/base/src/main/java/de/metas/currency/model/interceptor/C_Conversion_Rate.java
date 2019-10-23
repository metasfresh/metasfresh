package de.metas.currency.model.interceptor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.ModelValidator;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import de.metas.util.Services;

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

@Interceptor(I_C_Conversion_Rate.class)
public class C_Conversion_Rate
{
	@Init
	public void init()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(new de.metas.currency.callout.C_Conversion_Rate());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_Conversion_Rate conversionRate)
	{
		// From - To is the same
		if (conversionRate.getC_Currency_ID() == conversionRate.getC_Currency_ID_To())
		{
			throw new AdempiereException("@C_Currency_ID@ = @C_Currency_ID@");
		}
		// Nothing to convert
		if (conversionRate.getMultiplyRate().compareTo(BigDecimal.ZERO) <= 0)
		{
			throw new AdempiereException("@MultiplyRate@ <= 0");
		}

		// Date Range Check
		final Timestamp from = conversionRate.getValidFrom();
		if (conversionRate.getValidTo() == null)
		{
			conversionRate.setValidTo(TimeUtil.getDay(2056, 12, 31));
		}
		final Timestamp to = conversionRate.getValidTo();

		if (to.before(from))
		{
			final SimpleDateFormat df = DisplayType.getDateFormat(DisplayType.Date);
			throw new AdempiereException(df.format(to) + " < " + df.format(from));
		}
	}
}
