package de.metas.dunning.spi.impl;

/*
 * #%L
 * de.metas.dunning
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

import de.metas.dunning.DunningTestBase;
import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.impl.PlainDunningContext;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.invoice.api.impl.DunnableDocBuilder;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseDunnableSourceTest extends DunningTestBase
{
	private AbstractDunnableSource source;
	// private I_C_Dunning dunning;
	private I_C_DunningLevel dunningLevel1_10;

	@NonNull
	public static LocalDateAndOrgId now(@NonNull final OrgId orgId)
	{
		return LocalDateAndOrgId.ofLocalDate(LocalDate.now(), orgId);
	}

	@Override
	protected void createMasterData()
	{
		source = new MockedDunnableSource();

		final I_C_Dunning dunning = createDunning("TestDunning");
		dunningLevel1_10 = createDunningLevel(dunning,
				10, // DaysBetweenDunning
				10, // DaysAfterDue
				0 // InterestPercent
		);
		dunningLevel1_10.setIsShowAllDue(false);
	}

	@Test
	public void isEligible_ZeroOpenAmt()
	{
		final PlainDunningContext dunningContext = createPlainDunningContext((Date)null, null); // date and level not required

		final IDunnableDoc dunnable = mkDunnableDocBuilder()
				.setOpenAmt(BigDecimal.ZERO)
				.create();

		assertThat(source.isEligible(dunningContext, dunnable)).as("Invalid for " + dunnable).isFalse();
	}

	@Test
	public void isEligible_DaysAfterDue()
	{
		// Setup Context
		final PlainDunningContext dunningContext = createPlainDunningContext((Date)null, dunningLevel1_10);

		final IDunnableDoc dunnable_due5 = mkDunnableDocBuilder()
				.setOpenAmt(BigDecimal.valueOf(100))
				.setDaysDue(5)
				.create();
		assertThat(source.isEligible(dunningContext, dunnable_due5)).as("Invalid for " + dunnable_due5).isFalse();

		final IDunnableDoc dunnable_due10 = mkDunnableDocBuilder()
				.setOpenAmt(BigDecimal.valueOf(100))
				.setDaysDue(10)
				.create();
		assertThat(source.isEligible(dunningContext, dunnable_due10)).as("Invalid for " + dunnable_due10).isTrue();

		final IDunnableDoc dunnable_due11 = mkDunnableDocBuilder()
				.setOpenAmt(BigDecimal.valueOf(100))
				.setDaysDue(11)
				.create();
		assertThat(source.isEligible(dunningContext, dunnable_due11)).as("Invalid for " + dunnable_due11).isTrue();

		final IDunnableDoc dunnable_due15 = mkDunnableDocBuilder()
				.setOpenAmt(BigDecimal.valueOf(100))
				.setDaysDue(15)
				.create();
		assertThat(source.isEligible(dunningContext, dunnable_due15)).as("Invalid for " + dunnable_due15).isTrue();
	}

	@Test
	public void isEligible_InDispute()
	{
		// Setup Context
		final PlainDunningContext dunningContext = createPlainDunningContext((Date)null, dunningLevel1_10);

		final IDunnableDoc dunnable_due15_inDispute = mkDunnableDocBuilder()
				.setOpenAmt(BigDecimal.valueOf(100))
				.setDaysDue(15)
				.setInDispute(true)
				.create();
		assertThat(source.isEligible(dunningContext, dunnable_due15_inDispute)).as("Invalid for " + dunnable_due15_inDispute).isFalse();
	}

	@Test
	public void isEligible_NotDue()
	{
		// Setup Context
		final PlainDunningContext dunningContext = createPlainDunningContext((Date)null, dunningLevel1_10);

		final IDunnableDoc dunnable1 = mkDunnableDocBuilder()
				.setOpenAmt(BigDecimal.valueOf(100))
				.setDaysDue(-1)
				.setInDispute(false)
				.create();

		dunningLevel1_10.setIsShowAllDue(false); // this flag does not matter because document is not due
		assertThat(source.isEligible(dunningContext, dunnable1)).as("Invalid for " + dunnable1).isFalse();

		dunningLevel1_10.setIsShowAllDue(true); // this flag does not matter because document is not due
		assertThat(source.isEligible(dunningContext, dunnable1)).as("Invalid for " + dunnable1).isFalse();
	}

	@Test
	public void isEligible_Due_But_ShowAllDue()
	{
		// Setup Context
		final PlainDunningContext dunningContext = createPlainDunningContext((Date)null, dunningLevel1_10);

		final IDunnableDoc dunnable1 = mkDunnableDocBuilder()
				.setOpenAmt(BigDecimal.valueOf(100))
				.setDaysDue(5)
				.setInDispute(false)
				.create();
		dunningLevel1_10.setIsShowAllDue(false);
		assertThat(source.isEligible(dunningContext, dunnable1)).as("Invalid for " + dunnable1).isFalse();

		dunningLevel1_10.setIsShowAllDue(true);
		assertThat(source.isEligible(dunningContext, dunnable1)).as("Invalid for " + dunnable1).isTrue();
	}

	private DunnableDocBuilder mkDunnableDocBuilder()
	{
		return new DunnableDocBuilder()
				.setTableName(I_C_Invoice.Table_Name)
				.setRecord_ID(1)
				.setC_BPartner_ID(1)
				.setC_BPartner_Location_ID(1)
				.setC_Currency_ID(currencyEUR.getRepoId())
				.setTotalAmt(BigDecimal.ZERO)
				.setOpenAmt(BigDecimal.ZERO)
				.setDueDate(now(OrgId.MAIN));
	}
}
