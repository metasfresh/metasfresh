package de.metas.fresh.model.validator;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.sql.Timestamp;
import java.util.Calendar;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;

import de.metas.ordercandidate.model.I_C_OLCand;

@Validator(I_C_OLCand.class)
public class C_OLCand
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setDatePromisedToEndOfDay(final I_C_OLCand olCand)
	{
		final Timestamp datePromised = olCand.getDatePromised();
		if (datePromised == null)
		{
			return;
		}

		final Calendar datePromisedCal = TimeUtil.asCalendar(datePromised);

		//
		// If time is already set return
		if (datePromisedCal.get(Calendar.HOUR_OF_DAY) > 0
				|| datePromisedCal.get(Calendar.MINUTE) > 0)
		{
			return;
		}

		datePromisedCal.set(Calendar.HOUR_OF_DAY, 23);
		datePromisedCal.set(Calendar.MINUTE, 59);
		datePromisedCal.set(Calendar.SECOND, 59);
		datePromisedCal.set(Calendar.MILLISECOND, 999);

		final Timestamp datePromisedNew = new Timestamp(datePromisedCal.getTimeInMillis());
		olCand.setDatePromised(datePromisedNew);
	}
}
