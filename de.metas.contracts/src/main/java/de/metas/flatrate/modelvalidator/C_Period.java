package de.metas.flatrate.modelvalidator;

/*
 * #%L
 * de.metas.contracts
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


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.service.ICalendarBL;
import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.model.I_C_Flatrate_Transition;

@Validator(I_C_Period.class)
public class C_Period
{
	/**
	 * Note (task 07392): not checking when a new period is created, because things might *not yet* be correct..e.g. when creating the first period of a new year, the year is not yet complete and therefore the
	 * check might fail.
	 * 
	 * @param period
	 */
	@ModelChange(timings = { /* ModelValidator.TYPE_AFTER_NEW, */ModelValidator.TYPE_AFTER_CHANGE })
	public void checkCPeriod(final I_C_Period period)
	{
		final I_C_Year year = period.getC_Year();

		final I_C_Calendar calendar = year.getC_Calendar();

		final List<I_C_Flatrate_Transition> transitions = Services.get(IFlatrateDAO.class).retrieveTransitionsForCalendar(calendar);

		if (transitions.isEmpty())
		{
			// do nothing
		}

		else
		{
			Services.get(ICalendarBL.class).checkCorrectCalendar(calendar);
		}
	}
}
