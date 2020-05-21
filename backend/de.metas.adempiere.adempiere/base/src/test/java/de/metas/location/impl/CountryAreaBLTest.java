package de.metas.location.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.time.LocalDate;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_CountryArea_Assign;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.location.ICountryAreaBL;
import de.metas.util.Services;

public class CountryAreaBLTest
{
	private CountryAreaBL countryAreaBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		countryAreaBL = (CountryAreaBL)Services.get(ICountryAreaBL.class);
	}

	private I_C_CountryArea_Assign createAssignment(final String validFromStr, final String validToStr)
	{
		final Timestamp validFrom = validFromStr == null ? null : TimeUtil.asTimestamp(LocalDate.parse(validFromStr));
		final Timestamp validTo = validToStr == null ? null : TimeUtil.asTimestamp(LocalDate.parse(validToStr));

		final I_C_CountryArea_Assign assignment = newInstance(I_C_CountryArea_Assign.class);
		assignment.setValidFrom(validFrom);
		assignment.setValidTo(validTo);

		return assignment;
	}

	@Test
	public void isTimeConflictTest()
	{
		{
			final I_C_CountryArea_Assign assignment1 = createAssignment("2010-01-01", null);
			final I_C_CountryArea_Assign assignment2 = createAssignment("2010-05-01", null);
			assertThat(countryAreaBL.isTimeConflict(assignment1, assignment2)).isTrue();
		}

		{
			final I_C_CountryArea_Assign assignment1 = createAssignment("2010-01-01", "2010-04-01");
			final I_C_CountryArea_Assign assignment2 = createAssignment("2010-05-01", null);
			assertThat(countryAreaBL.isTimeConflict(assignment1, assignment2)).isFalse();
		}

		{
			final I_C_CountryArea_Assign assignment1 = createAssignment("2010-01-01", "2010-06-01");
			final I_C_CountryArea_Assign assignment2 = createAssignment("2010-05-01", null);
			assertThat(countryAreaBL.isTimeConflict(assignment1, assignment2)).isTrue();
		}

		{
			final I_C_CountryArea_Assign assignment2 = createAssignment("2010-01-01", "2010-04-01");
			final I_C_CountryArea_Assign assignment1 = createAssignment("2010-05-01", null);
			assertThat(countryAreaBL.isTimeConflict(assignment1, assignment2)).isFalse();
		}

		{
			final I_C_CountryArea_Assign assignment2 = createAssignment("2010-01-01", "2010-06-01");
			final I_C_CountryArea_Assign assignment1 = createAssignment("2010-05-01", null);
			assertThat(countryAreaBL.isTimeConflict(assignment1, assignment2)).isTrue();
		}

		{
			final I_C_CountryArea_Assign assignment1 = createAssignment("2010-01-01", "2010-04-01");
			final I_C_CountryArea_Assign assignment2 = createAssignment("2010-05-01", "2010-07-01");
			assertThat(countryAreaBL.isTimeConflict(assignment1, assignment2)).isFalse();
		}

		{
			final I_C_CountryArea_Assign assignment1 = createAssignment("2010-01-01", "2010-06-01");
			final I_C_CountryArea_Assign assignment2 = createAssignment("2010-05-01", "2010-08-01");
			assertThat(countryAreaBL.isTimeConflict(assignment1, assignment2)).isTrue();
		}
	}
}
