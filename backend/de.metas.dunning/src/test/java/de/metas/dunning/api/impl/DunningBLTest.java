package de.metas.dunning.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.common.util.time.SystemTime;
import de.metas.dunning.DunningTestBase;
import de.metas.dunning.api.IDunningConfig;
import de.metas.dunning.exception.DunningException;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.impl.PlainDunningConfigurator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

public class DunningBLTest extends DunningTestBase
{
	@Test
	public void test_validateCandidate()
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setDunningDateEffective(null);

		//
		// Not processed, not DunningDocProcessed
		candidate.setProcessed(false);
		candidate.setIsDunningDocProcessed(false);
		try
		{
			dunningBL.validate(candidate);
			Assert.assertTrue("Candidate shall be valid: " + candidate, true);
		}
		catch (AdempiereException e)
		{
			Assert.fail("Candidate shall be valid: " + candidate + " but got exception: " + e.getLocalizedMessage());
		}

		//
		// Not processed, DunningDocProcessed
		candidate.setProcessed(false);
		candidate.setIsDunningDocProcessed(true);
		try
		{
			dunningBL.validate(candidate);
			Assert.fail("Candidate shall NOT be valid: " + candidate);
		}
		catch (AdempiereException e)
		{
			Assert.assertTrue("Candidate shall NOT be valid: " + candidate, true);
		}

		//
		// Processed, not DunningDocProcessed
		candidate.setProcessed(true);
		candidate.setIsDunningDocProcessed(false);
		try
		{
			dunningBL.validate(candidate);
			Assert.assertTrue("Candidate shall be valid: " + candidate, true);
		}
		catch (AdempiereException e)
		{
			Assert.fail("Candidate shall be valid: " + candidate);
		}

		//
		// Processed, DunningDocProcessed
		candidate.setProcessed(true);
		candidate.setIsDunningDocProcessed(true);
		candidate.setDunningDateEffective(null);
		try
		{
			dunningBL.validate(candidate);
			Assert.fail("Candidate shall NOT be valid: " + candidate);
		}
		catch (AdempiereException e)
		{
			Assert.assertTrue("Candidate shall NOT be valid: " + candidate, true);
		}

		//
		// Processed, DunningDocProcessed
		candidate.setProcessed(true);
		candidate.setIsDunningDocProcessed(true);
		candidate.setDunningDateEffective(SystemTime.asTimestamp());
		try
		{
			dunningBL.validate(candidate);
			Assert.assertTrue("Candidate shall be valid: " + candidate, true);
		}
		catch (AdempiereException e)
		{
			Assert.fail("Candidate shall be valid: " + candidate);
		}

	}

	@Test(expected = AdempiereException.class)
	public void test_validateCandidate_InactiveNotAllowed()
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setProcessed(true);
		candidate.setIsDunningDocProcessed(true);
		candidate.setDunningDateEffective(de.metas.common.util.time.SystemTime.asTimestamp());

		candidate.setIsActive(false);
		dunningBL.validate(candidate);
	}

	@Test
	public void test_getSummary()
	{
		Assert.assertNull("summary shall be null for null", dunningBL.getSummary(null));

		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		InterfaceWrapperHelper.save(candidate);
		Assert.assertNotNull("Invalid summary", dunningBL.getSummary(candidate));
	}

	@Test(expected = DunningException.class)
	public void test_setDunningConfigurator_AlreadyConfigured()
	{
		final IDunningConfig config1 = dunningBL.getDunningConfig();
		Assert.assertNotNull("config1 shall not be null", config1);

		final PlainDunningConfigurator configurator = new PlainDunningConfigurator();

		// this shall throw an exception because we already have a config for this dunningBL
		dunningBL.setDunningConfigurator(configurator);

		// final IDunningConfig config2 = dunningBL.getDunningConfig();
		// Assert.assertNotNull("config2 shall not be null", config2);
		// Assert.assertNotSame("config2 shall not be the same as config1 because we set again the configurator", config1, config2);
		// Assert.assertEquals("Configurator shall be called only once", 1, configurator.configureRequestCount);
		//
		// final IDunningConfig config3 = dunningBL.getDunningConfig();
		// Assert.assertNotNull("config3 shall not be null", config3);
		// Assert.assertSame("config3 shall be the same as config2 because we did NOT set again the configurator", config2, config3);
		// Assert.assertEquals("Configurator shall be called only once", 1, configurator.configureRequestCount);
	}

	@Test(expected = AdempiereException.class)
	public void test_setDunningConfigurator_ConfiguratorReturnsNull()
	{
		final PlainDunningConfigurator configurator = new PlainDunningConfigurator();
		configurator.returnNull = true;

		dunningBL.setDunningConfigurator(configurator);

		// shall trigger an error because configurator returns null
		dunningBL.getDunningConfig();
	}

	@Test
	public void test_isExpired()
	{
		// expectedExpired, DunningGraceDate, DunningDate, Processed
		assertExpired(false, TimeUtil.getDay(2013, 3, 9), TimeUtil.getDay(2013, 3, 10), false);
		assertExpired(false, TimeUtil.getDay(2013, 3, 10), TimeUtil.getDay(2013, 3, 10), false);
		assertExpired(true, TimeUtil.getDay(2013, 3, 11), TimeUtil.getDay(2013, 3, 10), false);
		assertExpired(false, null, TimeUtil.getDay(2013, 3, 10), false);

		// processed records never expire
		assertExpired(false, TimeUtil.getDay(2013, 3, 9), TimeUtil.getDay(2013, 3, 10), true);
		assertExpired(false, TimeUtil.getDay(2013, 3, 10), TimeUtil.getDay(2013, 3, 10), true);
		assertExpired(false, TimeUtil.getDay(2013, 3, 11), TimeUtil.getDay(2013, 3, 10), true);
		assertExpired(false, null, TimeUtil.getDay(2013, 3, 10), true);
	}

	private void assertExpired(final boolean expectedExpired, final Timestamp dunningGraceDate, final Timestamp dunningDate, final boolean processed)
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setDunningDate(dunningDate);
		candidate.setProcessed(processed);
		Assert.assertEquals("Invalid isExpired result for dunningGraceDate=" + dunningGraceDate + ", dunningDate=" + dunningDate + ", processed=" + processed,
				expectedExpired, dunningBL.isExpired(candidate, dunningGraceDate));
	}
}
