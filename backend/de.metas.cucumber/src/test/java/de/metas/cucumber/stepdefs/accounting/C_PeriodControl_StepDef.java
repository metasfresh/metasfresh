/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_PeriodControl;
import org.compiere.model.X_C_PeriodControl;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class C_PeriodControl_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("^all periods are open$")
	public void all_periods_are_open()
	{
		queryBL.createQueryBuilder(I_C_PeriodControl.class)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_PeriodControl.COLUMNNAME_PeriodStatus, X_C_PeriodControl.PERIODSTATUS_Open)
				.execute();
	}

	/**
	 * Closes the accounting period that contains the given date, for every document base type; all other periods are open.
	 * The accounting schemas' automatic period control is switched off for that (with it on, the period controls are not checked).
	 * Undo with {@code the accounting periods are controlled automatically}.
	 *
	 * @cucumber.example
	 * <pre>
	 * And the period of 2024-03-06 is closed
	 * </pre>
	 */
	@And("^the period of ([^ ]+) is closed$")
	public void closePeriodOfDate(@NonNull final String date)
	{
		final Timestamp dateTS = Timestamp.valueOf(LocalDate.parse(date).atStartOfDay());
		final ImmutableSet<Integer> periodIds = ImmutableSet.copyOf(queryBL.createQueryBuilder(I_C_Period.class)
				.addCompareFilter(I_C_Period.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, dateTS)
				.addCompareFilter(I_C_Period.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, dateTS)
				.create()
				.listIds());
		assertThat(periodIds).as("C_Period of %s", date).isNotEmpty();

		setAutoPeriodControl(false);
		all_periods_are_open();
		queryBL.createQueryBuilder(I_C_PeriodControl.class)
				.addInArrayFilter(I_C_PeriodControl.COLUMNNAME_C_Period_ID, periodIds)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_PeriodControl.COLUMNNAME_PeriodStatus, X_C_PeriodControl.PERIODSTATUS_Closed)
				.execute();
		resetCaches();
	}

	/**
	 * Switches the accounting schemas' automatic period control on (the default of the test database): every period within
	 * the schema's open-history / open-future days is open, whatever its period control says.
	 *
	 * @cucumber.example
	 * <pre>
	 * And the accounting periods are controlled automatically
	 * </pre>
	 */
	@And("^the accounting periods are controlled automatically$")
	public void periodsAreControlledAutomatically()
	{
		setAutoPeriodControl(true);
		resetCaches();
	}

	private void setAutoPeriodControl(final boolean autoPeriodControl)
	{
		queryBL.createQueryBuilder(I_C_AcctSchema.class)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_AcctSchema.COLUMNNAME_AutoPeriodControl, autoPeriodControl)
				.execute();
	}

	private static void resetCaches()
	{
		CacheMgt.get().reset(I_C_AcctSchema.Table_Name);
		CacheMgt.get().reset(I_C_Period.Table_Name);
		CacheMgt.get().reset(I_C_PeriodControl.Table_Name);
	}
}
