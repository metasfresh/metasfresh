/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.calendar;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

class PeriodRepoTest
{

	private PeriodRepo periodRepo;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		periodRepo = new PeriodRepo();
	}

	@Test
	void getPeriodId()
	{
		// given
		final I_C_Year yearRecord = newInstance(I_C_Year.class);
		yearRecord.setC_Calendar_ID(10);
		saveRecord(yearRecord);

		final I_C_Period periodRecord = newInstance(I_C_Period.class);
		periodRecord.setC_Year_ID(yearRecord.getC_Year_ID());
		periodRecord.setStartDate(Timestamp.valueOf("2016-01-01 00:00:10.0"));
		periodRecord.setEndDate(Timestamp.valueOf("2016-12-31 23:59:59.99"));
		saveRecord(periodRecord);

		// when
		final PeriodId periodId = periodRepo.getPeriodId(periodRecord.getC_Period_ID());
		
		// then
		assertThat(periodId.getRepoId()).isEqualTo(periodRecord.getC_Period_ID());
		assertThat(periodId.getYearId().getRepoId()).isEqualTo(yearRecord.getC_Year_ID());
	}

	@Test
	void getById()
	{
		// given
		final I_C_Year yearRecord = newInstance(I_C_Year.class);
		yearRecord.setC_Calendar_ID(10);
		saveRecord(yearRecord);

		final I_C_Period periodRecord = newInstance(I_C_Period.class);
		periodRecord.setC_Year_ID(yearRecord.getC_Year_ID());
		periodRecord.setStartDate(Timestamp.valueOf("2016-01-01 00:00:10.0"));
		periodRecord.setEndDate(Timestamp.valueOf("2016-12-31 23:59:59.99"));
		saveRecord(periodRecord);
		
		final PeriodId periodId = periodRepo.getPeriodId(periodRecord.getC_Period_ID());
		
		// when
		final Period period = periodRepo.getById(periodId);
		
		assertThat(period.getId()).isEqualTo(periodId);
	}
}