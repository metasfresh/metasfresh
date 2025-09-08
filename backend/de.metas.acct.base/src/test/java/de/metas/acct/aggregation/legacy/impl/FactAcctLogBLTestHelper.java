/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.acct.aggregation.legacy.impl;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.model.X_C_Period;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public class FactAcctLogBLTestHelper
{
	public static I_C_Year createYear(final int year)
	{
		final I_C_Year record = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Year.class, ITrx.TRXNAME_None);
		record.setFiscalYear(String.valueOf(year));
		InterfaceWrapperHelper.save(record);
		return record;
	}

	public static I_C_Period createPeriod(final I_C_Year year, final Month month)
	{
		final I_C_Period period = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Period.class, ITrx.TRXNAME_None);
		period.setC_Year_ID(year.getC_Year_ID());
		period.setName(year.getFiscalYear() + "-" + month);
		period.setPeriodNo(month.getValue());
		period.setPeriodType(X_C_Period.PERIODTYPE_StandardCalendarPeriod);

		final int yearInt = Integer.parseInt(year.getFiscalYear());

		final YearMonth yearMonth = YearMonth.of(yearInt, month);
		final LocalDate dateStart = yearMonth.atDay(1);
		final LocalDate dateEnd = yearMonth.atEndOfMonth();
		period.setStartDate(TimeUtil.asTimestamp(dateStart));
		period.setEndDate(TimeUtil.asTimestamp(dateEnd));

		InterfaceWrapperHelper.save(period);
		return period;
	}

}
