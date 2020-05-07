package de.metas.ordercandidate;

/*
 * #%L
 * de.metas.fresh.base
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
import java.util.Calendar;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.ordercandidate.model.I_C_OLCand;

public class OLCandTest
{
	private I_C_OLCand olCand1;
	private I_C_OLCand olCand2;
	private I_C_OLCand olCand3;

	private final Timestamp date_2014_05_07_time_00 = createDate(2014, 5, 7, 0, 0, 0, 0);
	private final Timestamp date_2014_05_07_time_random = createDate(2014, 5, 7, 13, 0, 0, 0);
	private final Timestamp date_2014_05_07_time_2359 = createDate(2014, 5, 7, 23, 59, 59, 999);

	private de.metas.fresh.model.validator.C_OLCand mvCand;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(true);

		mvCand = new de.metas.fresh.model.validator.C_OLCand();

		olCand1 = setupC_OLCand();
		olCand1.setDatePromised(date_2014_05_07_time_00);
		olCand2 = setupC_OLCand();
		olCand2.setDatePromised(date_2014_05_07_time_random);
		olCand3 = setupC_OLCand();
		olCand3.setDatePromised(date_2014_05_07_time_2359);

	}

	private static final Timestamp createDate(final int year, final int month, final int day, final int hour, final int min, final int sec, final int milisec)
	{
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month + 1);
		cal.set(Calendar.DAY_OF_WEEK, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, milisec);

		return new Timestamp(cal.getTimeInMillis());
	}

	@Test
	public void test_OLCand_DatePromised_FullDay_withTi00()
	{
		mvCand.setDatePromisedToEndOfDay(olCand1);
		// date changes to 23:59
		Assert.assertEquals(olCand1.getDatePromised(), date_2014_05_07_time_2359);
	}

	@Test
	public void test_OLCand_DatePromised_FullDay_withTime13_00()
	{
		mvCand.setDatePromisedToEndOfDay(olCand2);
		// date stays the same
		Assert.assertEquals(olCand2.getDatePromised(), date_2014_05_07_time_random);
	}

	@Test
	public void test_OLCand_DatePromised_FullDay_withTime23_59()
	{
		mvCand.setDatePromisedToEndOfDay(olCand3);
		// date stays the same
		Assert.assertEquals(olCand3.getDatePromised(), date_2014_05_07_time_2359);
	}

	private I_C_OLCand setupC_OLCand()
	{
		final I_C_OLCand olcand = InterfaceWrapperHelper.create(Env.getCtx(), I_C_OLCand.class, ITrx.TRXNAME_None);
		olcand.setAD_User_EnteredBy_ID(1);
		olcand.setAD_InputDataSource_ID(2);
		olcand.setC_BPartner_ID(3);
		olcand.setC_BPartner_Location_ID(4);

		InterfaceWrapperHelper.save(olcand);
		return olcand;
	}
}
