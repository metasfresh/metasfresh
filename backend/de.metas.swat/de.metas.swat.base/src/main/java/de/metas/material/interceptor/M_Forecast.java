/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.material.interceptor;

import de.metas.calendar.CalendarId;
import de.metas.calendar.ICalendarBL;
import de.metas.calendar.ICalendarDAO;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.mforecast.IForecastDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Interceptor(I_M_Forecast.class)
@Component
public class M_Forecast
{
	private static final String MSG_DOC_ACTION_NOT_ALLOWED_AFTER_COMPLETION = "M_Forecast_DocAction_Not_Allowed_After_Completion";

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IForecastDAO forecastsRepo = Services.get(IForecastDAO.class);
	private final ICalendarBL calendarBL = Services.get(ICalendarBL.class);
	private final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);
	public static final AdMessageKey MSG_CALENDAR_DOES_NOT_CONTAIN_PROMISED_DATE = AdMessageKey.of("de.metas.material.interceptor.M_Forecast.CalendarDoesNotContainPromisedDate");

	public M_Forecast()
	{
		CopyRecordFactory.enableForTableName(I_M_Forecast.Table_Name);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_CLOSE,
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID
	})
	public void preventUnsupportedDocActions(@NonNull final I_M_Forecast forecast)
	{
		final String message = msgBL.getMsg(Env.getCtx(), MSG_DOC_ACTION_NOT_ALLOWED_AFTER_COMPLETION);
		throw new AdempiereException(message);
	}

	@ModelChange(
			timings = {
					ModelValidator.TYPE_BEFORE_CHANGE,
					ModelValidator.TYPE_BEFORE_NEW
			},
			ifColumnsChanged = {
					I_M_Forecast.COLUMNNAME_DatePromised,
			})
	public void updateFieldsFromDatePromised(@NonNull final I_M_Forecast forecast)
	{
		final CalendarId calendarId = calendarBL.getOrgCalendarOrDefault(OrgId.ofRepoId(forecast.getAD_Org_ID()));
		final Timestamp datePromised = forecast.getDatePromised();
		final I_C_Period period = calendarDAO.findByCalendar(datePromised, calendarId);

		if (period == null)
		{
			final String calendarName = calendarDAO.getName(calendarId);
			throw new AdempiereException(MSG_CALENDAR_DOES_NOT_CONTAIN_PROMISED_DATE, calendarName, datePromised);
		}

		forecast.setC_Calendar_ID(calendarId.getRepoId());
		forecast.setC_Period_ID(period.getC_Period_ID());
		forecast.setC_Year_ID(period.getC_Year_ID());
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = {
			I_M_Forecast.COLUMNNAME_C_BPartner_ID,
			I_M_Forecast.COLUMNNAME_C_Period_ID,
			I_M_Forecast.COLUMNNAME_DatePromised,
			I_M_Forecast.COLUMNNAME_M_Warehouse_ID
	})
	public void updateForecastLines(@NonNull final I_M_Forecast forecast)
	{
		final List<I_M_ForecastLine> forecastLines = forecastsRepo.retrieveLinesByForecastId(forecast.getM_Forecast_ID());
		forecastLines.forEach(forecastLine -> updateForecastLineFromHeaderAndSave(forecastLine, forecast));
	}

	private void updateForecastLineFromHeaderAndSave(final I_M_ForecastLine forecastLine, final I_M_Forecast forecast)
	{
		forecastLine.setC_BPartner_ID(forecast.getC_BPartner_ID());
		forecastLine.setM_Warehouse_ID(forecast.getM_Warehouse_ID());
		forecastLine.setC_Period_ID(forecast.getC_Period_ID());
		forecastLine.setDatePromised(forecast.getDatePromised());
		saveRecord(forecastLine);
	}
}
