package de.metas.adempiere.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Test;

import de.metas.adempiere.model.I_C_CountryArea_Assign;
import de.metas.adempiere.service.ICountryAreaBL;
import mockit.Expectations;
import mockit.Mocked;

public class CountryAreaBLTest
{
	@Mocked
	I_C_CountryArea_Assign assignment1;

	@Mocked
	I_C_CountryArea_Assign assignment2;

	CountryAreaBL countryAreaBL = new CountryAreaBL();

	@Test
	public void testServiceLoadedOK()
	{
		AdempiereTestHelper.get().init();
		Services.get(ICountryAreaBL.class);
	}

	@Test
	public void isTimeConflictTest() throws Exception
	{
		setAssignment(assignment1, "2010-01-01", null);
		setAssignment(assignment2, "2010-05-01", null);
		Assert.assertTrue(countryAreaBL.isTimeConflict(assignment1, assignment2));

		setAssignment(assignment1, "2010-01-01", "2010-04-01");
		setAssignment(assignment2, "2010-05-01", null);
		Assert.assertFalse(countryAreaBL.isTimeConflict(assignment1, assignment2));

		setAssignment(assignment1, "2010-01-01", "2010-06-01");
		setAssignment(assignment2, "2010-05-01", null);
		Assert.assertTrue(countryAreaBL.isTimeConflict(assignment1, assignment2));

		setAssignment(assignment2, "2010-01-01", "2010-04-01");
		setAssignment(assignment1, "2010-05-01", null);
		Assert.assertFalse(countryAreaBL.isTimeConflict(assignment1, assignment2));

		setAssignment(assignment2, "2010-01-01", "2010-06-01");
		setAssignment(assignment1, "2010-05-01", null);
		Assert.assertTrue(countryAreaBL.isTimeConflict(assignment1, assignment2));

		setAssignment(assignment1, "2010-01-01", "2010-04-01");
		setAssignment(assignment2, "2010-05-01", "2010-07-01");
		Assert.assertFalse(countryAreaBL.isTimeConflict(assignment1, assignment2));

		setAssignment(assignment1, "2010-01-01", "2010-06-01");
		setAssignment(assignment2, "2010-05-01", "2010-08-01");
		Assert.assertTrue(countryAreaBL.isTimeConflict(assignment1, assignment2));
	}

	private void setAssignment(final I_C_CountryArea_Assign assignment, String validFromStr, String validToStr) throws ParseException
	{
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		final Timestamp validFrom = validFromStr == null ? null : TimeUtil.asTimestamp(df.parse(validFromStr));
		final Timestamp validTo = validToStr == null ? null : TimeUtil.asTimestamp(df.parse(validToStr));

		new Expectations()
		{
			{
				assignment.getValidFrom();
				minTimes = 0;
				result = validFrom;
				
				assignment.getValidTo();
				minTimes = 0;
				result = validTo;
			}
		};
	}
}
