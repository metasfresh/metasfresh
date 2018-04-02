package de.metas.contracts.interceptor;

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


import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.ModelValidator;
import org.compiere.model.Query;

import de.metas.adempiere.service.ICalendarBL;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.i18n.IMsgBL;

@Validator(I_C_Flatrate_Transition.class)
public class C_Flatrate_Transition
{
	private static final String MSG_TRANSAITION_ERROR_EXISTING_CO_CONDITIONS_0P = "Transition_Error_Existing_CO_Conditions";

	private static final String MSG_TRANSITION_ERROR_ENDS_WITH_CALENDAR_YEAR = "EndsWithCalendarYear";

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_CLOSE })
	public void disallowNotSupportedDocActions(final I_C_Flatrate_Transition transition)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(transition);
		final String msg = Services.get(IMsgBL.class).getMsg(ctx, MainValidator.MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P);
		throw new AdempiereException(msg);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void assertNoConditions(final I_C_Flatrate_Transition transition)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(transition);
		final String trxName = InterfaceWrapperHelper.getTrxName(transition);

		// check that this record is not referenced by a completed conditions record
		final String wc = I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Transition_ID + "=?"
				+ " AND " + I_C_Flatrate_Conditions.COLUMNNAME_DocStatus + "=?";

		final boolean hasConditions = new Query(ctx, I_C_Flatrate_Conditions.Table_Name, wc, trxName)
				.setParameters(transition.getC_Flatrate_Transition_ID(), X_C_Flatrate_Conditions.DOCSTATUS_Completed)
				.setApplyAccessFilter(true)
				.setOnlyActiveRecords(true)
				.match();

		if (hasConditions)
		{
			throw new AdempiereException(Services.get(IMsgBL.class).getMsg(ctx, MSG_TRANSAITION_ERROR_EXISTING_CO_CONDITIONS_0P));
		}
	}

	/**
	 * In order for the term to end with the calendar year, its duration needs to be one year
	 *
	 * @param transition
	 * @task 03742
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void checkIfEndsWithCalendarYear(final I_C_Flatrate_Transition transition)
	{

		final boolean endsWithCalendarYear = transition.isEndsWithCalendarYear();

		if (endsWithCalendarYear == false)
		{
			// nothing to do
			return;
		}

		/*
		 * If EndsWithCalendarYear='Y', then the selected C_Calendar_Contract_ID needs to meet the following conditions: for every C_Year of the calendar Length: DatenEnd of the C_Year's last period
		 * must be one year after DateStart of the C_Year's first period No gaps: For every period A of a given calendar, there must be either another period B with B's StartDate beeing A's EndDate
		 * plus one day or no later period (within the same calendar!) at all No overlap: for every C_Calendar and every point in time t, there may be at most one C_Period with StartDate <= t <=
		 * EndDate
		 */

		final ICalendarBL calendarBL = Services.get(ICalendarBL.class);

		final I_C_Calendar calendarContract = transition.getC_Calendar_Contract();

		calendarBL.checkCorrectCalendar(calendarContract);

		final String termDurationUnit = transition.getTermDurationUnit();

		final int termDuration = transition.getTermDuration();

		if (X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE.equals(termDurationUnit))
		{
			// Nothing to do
			return;
		}
		else
		{
			if (X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE.equals(termDurationUnit))
			{

				if (termDuration % 12 == 0)
				{
					// This is correct, nothing to do
					return;
				}
			}
		}
		// Not a suitable situation for a transition ending with calendar year
		throw new AdempiereException("@" + MSG_TRANSITION_ERROR_ENDS_WITH_CALENDAR_YEAR + "@");
	}
}

