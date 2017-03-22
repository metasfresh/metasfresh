package de.metas.procurement.base.contracts;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.junit.Before;
import org.junit.Test;

import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_DataEntry;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.procurement.base.PMMContractBuilder;
import de.metas.procurement.base.model.I_C_Flatrate_Term;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PMMContractBuilderTests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testBuild()
	{
		final I_C_Calendar calendar = InterfaceWrapperHelper.newInstance(I_C_Calendar.class);
		InterfaceWrapperHelper.save(calendar);
		
		final I_C_Year year = InterfaceWrapperHelper.newInstance(I_C_Year.class);
		year.setC_Calendar_ID(calendar.getC_Calendar_ID());
		InterfaceWrapperHelper.save(year);
		
		final I_C_Period period1 = InterfaceWrapperHelper.newInstance(I_C_Period.class);
		period1.setStartDate(Timestamp.valueOf("2017-01-01 00:00:00"));
		period1.setEndDate(Timestamp.valueOf("2017-01-31 23:59:59"));
		period1.setC_Year_ID(year.getC_Year_ID());
		InterfaceWrapperHelper.save(period1);

		final I_C_Period period2 = InterfaceWrapperHelper.newInstance(I_C_Period.class);
		period2.setStartDate(Timestamp.valueOf("2017-02-01 00:00:00"));
		period2.setEndDate(Timestamp.valueOf("2017-02-28 23:59:59"));
		period2.setC_Year_ID(year.getC_Year_ID());
		InterfaceWrapperHelper.save(period2);

		final I_C_Period period3 = InterfaceWrapperHelper.newInstance(I_C_Period.class);
		period3.setStartDate(Timestamp.valueOf("2017-03-01 00:00:00"));
		period3.setEndDate(Timestamp.valueOf("2017-03-31 23:59:59"));
		period3.setC_Year_ID(year.getC_Year_ID());
		InterfaceWrapperHelper.save(period3);
				
		final I_C_Flatrate_Transition transition = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class);
		transition.setC_Calendar_Contract(calendar);
		InterfaceWrapperHelper.save(transition);
		
		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class);
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);
		
		final I_C_Flatrate_Term newTerm = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class);
		newTerm.setStartDate(Timestamp.valueOf("2017-01-01 00:00:00"));
		newTerm.setEndDate(Timestamp.valueOf("2017-12-31 23:59:59"));
		newTerm.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(newTerm);
		
		final PMMContractBuilder pmmContractBuilder = PMMContractBuilder.newBuilder(newTerm);
		
		pmmContractBuilder.setFlatrateAmtPerUOM(Timestamp.valueOf("2017-01-01 00:00:00"), null);
		pmmContractBuilder.setFlatrateAmtPerUOM(Timestamp.valueOf("2017-02-01 00:00:00"), new BigDecimal("0.00"));
		pmmContractBuilder.setFlatrateAmtPerUOM(Timestamp.valueOf("2017-03-01 00:00:00"), new BigDecimal("1.23"));
		final I_C_Flatrate_Term newTermProcessed = pmmContractBuilder.build();

		final List<I_C_Flatrate_DataEntry> newEntries = Services.get(IFlatrateDAO.class).retrieveDataEntries(newTermProcessed, null, null);
		assertThat(newEntries.size(), is(3));
		assertThat(InterfaceWrapperHelper.isNull(newEntries.get(0),I_C_Flatrate_DataEntry.COLUMNNAME_FlatrateAmtPerUOM), is(true));
		assertThat(newEntries.get(1).getFlatrateAmtPerUOM(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(newEntries.get(2).getFlatrateAmtPerUOM(), comparesEqualTo(new BigDecimal("1.23")));
	}
}
