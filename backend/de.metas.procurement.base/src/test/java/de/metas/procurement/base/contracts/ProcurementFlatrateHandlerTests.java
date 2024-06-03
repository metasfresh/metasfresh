package de.metas.procurement.base.contracts;

import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.procurement.base.PMMContractBuilder;
import lombok.Getter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

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

public class ProcurementFlatrateHandlerTests
{
	ProcurementFlatrateHandler procurementFlatrateHandler;
	MockedPMMContractBuilder pmmContractBuilder;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new ModularContractSettingsDAO());
		SpringContextHolder.registerJUnitBean(new ModularContractLogDAO());
		procurementFlatrateHandler = new ProcurementFlatrateHandler()
		{
			@Override
			PMMContractBuilder newPMMContractBuilder(final de.metas.procurement.base.model.I_C_Flatrate_Term term)
			{
				return pmmContractBuilder = new MockedPMMContractBuilder(term);
			}
		};
	}

	/**
	 * Sets up an old flatrate term and a new one. the old one has three entries with different {@code FlatrateAmtPerUOM} values.
	 * Then verifies that the {@link PMMContractBuilder} is called with the correct parameters. 
	 */
	@Test
	public void testEntryWithNullValue()
	{
		final I_C_Period period1 = InterfaceWrapperHelper.newInstance(I_C_Period.class);
		period1.setStartDate(Timestamp.valueOf("2016-01-01 00:00:00"));
		period1.setEndDate(Timestamp.valueOf("2016-01-31 23:59:59"));
		InterfaceWrapperHelper.save(period1);

		final I_C_Period period2 = InterfaceWrapperHelper.newInstance(I_C_Period.class);
		period2.setStartDate(Timestamp.valueOf("2016-02-01 00:00:00"));
		period2.setEndDate(Timestamp.valueOf("2016-02-28 23:59:59"));
		InterfaceWrapperHelper.save(period2);

		final I_C_Period period3 = InterfaceWrapperHelper.newInstance(I_C_Period.class);
		period3.setStartDate(Timestamp.valueOf("2016-03-01 00:00:00"));
		period3.setEndDate(Timestamp.valueOf("2016-03-31 23:59:59"));
		InterfaceWrapperHelper.save(period3);

		final I_C_Flatrate_Term oldTerm = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class);
		oldTerm.setEndDate(Timestamp.valueOf("2016-12-31 23:59:59"));
		InterfaceWrapperHelper.save(oldTerm);

		final I_C_Flatrate_DataEntry entry1 = InterfaceWrapperHelper.newInstance(I_C_Flatrate_DataEntry.class);
		entry1.setC_Flatrate_Term(oldTerm);
		entry1.setC_Period(period1);
		entry1.setFlatrateAmtPerUOM(null);
		InterfaceWrapperHelper.save(entry1);

		final I_C_Flatrate_DataEntry entry2 = InterfaceWrapperHelper.newInstance(I_C_Flatrate_DataEntry.class);
		entry2.setC_Flatrate_Term(oldTerm);
		entry2.setC_Period(period2);
		entry2.setFlatrateAmtPerUOM(new BigDecimal("0.00"));
		InterfaceWrapperHelper.save(entry2);

		final I_C_Flatrate_DataEntry entry3 = InterfaceWrapperHelper.newInstance(I_C_Flatrate_DataEntry.class);
		entry3.setC_Flatrate_Term(oldTerm);
		entry3.setC_Period(period3);
		entry3.setFlatrateAmtPerUOM(new BigDecimal("1.23"));
		InterfaceWrapperHelper.save(entry3);

		final I_C_Flatrate_Term newTerm = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class);
		newTerm.setStartDate(Timestamp.valueOf("2017-01-01 00:00:00"));
		InterfaceWrapperHelper.save(newTerm);

		procurementFlatrateHandler.afterSaveOfNextTermForPredecessor(newTerm, oldTerm);

		final HashMap<Date, BigDecimal> flatrateAmtPerUOMByDayExpected = new HashMap<>();
		flatrateAmtPerUOMByDayExpected.put(Timestamp.valueOf("2017-01-01 00:00:00"), null);
		flatrateAmtPerUOMByDayExpected.put(Timestamp.valueOf("2017-02-01 00:00:00"), new BigDecimal("0.00"));
		flatrateAmtPerUOMByDayExpected.put(Timestamp.valueOf("2017-03-01 00:00:00"), new BigDecimal("1.23"));
		assertThat(pmmContractBuilder.getFlatrateAmtPerUOMByDay())
				.isEqualTo(flatrateAmtPerUOMByDayExpected);
	}

	//
	//
	//
	//
	//

	private static class MockedPMMContractBuilder extends PMMContractBuilder
	{
		@Getter
		private final HashMap<Date, BigDecimal> flatrateAmtPerUOMByDay = new HashMap<>();

		public MockedPMMContractBuilder(final de.metas.procurement.base.model.I_C_Flatrate_Term term)
		{
			super(term);
		}

		@Override
		public PMMContractBuilder setFlatrateAmtPerUOM(final Date date, final BigDecimal flatrateAmtPerUOM)
		{
			flatrateAmtPerUOMByDay.put(date, flatrateAmtPerUOM);
			return super.setFlatrateAmtPerUOM(date, flatrateAmtPerUOM);
		}

		@Override
		public de.metas.procurement.base.model.I_C_Flatrate_Term build()
		{
			return null;
		}
	}
}
