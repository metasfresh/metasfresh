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
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
		assertThatCode(() -> dunningBL.validate(candidate))
				.as("Candidate shall be valid: " + candidate)
				.doesNotThrowAnyException();

		//
		// Not processed, DunningDocProcessed
		candidate.setProcessed(false);
		candidate.setIsDunningDocProcessed(true);
		assertThatThrownBy(() -> dunningBL.validate(candidate))
				.as("Candidate shall NOT be valid: " + candidate)
				.isInstanceOf(AdempiereException.class);

		//
		// Processed, not DunningDocProcessed
		candidate.setProcessed(true);
		candidate.setIsDunningDocProcessed(false);
		assertThatCode(() -> dunningBL.validate(candidate))
				.as("Candidate shall be valid: " + candidate)
				.doesNotThrowAnyException();

		//
		// Processed, DunningDocProcessed
		candidate.setProcessed(true);
		candidate.setIsDunningDocProcessed(true);
		candidate.setDunningDateEffective(null);
		assertThatThrownBy(() -> dunningBL.validate(candidate))
				.as("Candidate shall NOT be valid: " + candidate)
				.isInstanceOf(AdempiereException.class);

		//
		// Processed, DunningDocProcessed
		candidate.setProcessed(true);
		candidate.setIsDunningDocProcessed(true);
		candidate.setDunningDateEffective(SystemTime.asTimestamp());
		assertThatCode(() -> dunningBL.validate(candidate))
				.as("Candidate shall be valid: " + candidate)
				.doesNotThrowAnyException();
	}

	@Test
	public void test_validateCandidate_InactiveNotAllowed()
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setProcessed(true);
		candidate.setIsDunningDocProcessed(true);
		candidate.setDunningDateEffective(de.metas.common.util.time.SystemTime.asTimestamp());

		candidate.setIsActive(false);
		
		assertThatThrownBy(() -> dunningBL.validate(candidate))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	public void test_getSummary()
	{
		assertThat(dunningBL.getSummary(null))
				.as("summary shall be null for null")
				.isNull();

		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		InterfaceWrapperHelper.save(candidate);
		
		assertThat(dunningBL.getSummary(candidate))
				.as("Invalid summary")
				.isNotNull();
	}

	@Test
	public void test_setDunningConfigurator_AlreadyConfigured()
	{
		final IDunningConfig config1 = dunningBL.getDunningConfig();
		assertThat(config1)
				.as("config1 shall not be null")
				.isNotNull();

		final PlainDunningConfigurator configurator = new PlainDunningConfigurator();

		// this shall throw an exception because we already have a config for this dunningBL
		assertThatThrownBy(() -> dunningBL.setDunningConfigurator(configurator))
				.isInstanceOf(DunningException.class);

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

	@Test
	public void test_setDunningConfigurator_ConfiguratorReturnsNull()
	{
		final PlainDunningConfigurator configurator = new PlainDunningConfigurator();
		configurator.returnNull = true;

		dunningBL.setDunningConfigurator(configurator);

		// shall trigger an error because configurator returns null
		assertThatThrownBy(() -> dunningBL.getDunningConfig())
				.isInstanceOf(AdempiereException.class);
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

	@Test
	public void test_isExpired_NullCandidate()
	{
		assertThatThrownBy(() -> dunningBL.isExpired(null, TimeUtil.getDay(2013, 3, 10)))
				.isInstanceOf(AdempiereException.class);
	}

	private void assertExpired(final boolean expectedExpired, final Timestamp dunningGraceDate, final Timestamp dunningDate, final boolean processed)
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setDunningDate(dunningDate);
		candidate.setProcessed(processed);
		
		assertThat(dunningBL.isExpired(candidate, dunningGraceDate))
				.as("Invalid isExpired result for dunningGraceDate=" + dunningGraceDate + ", dunningDate=" + dunningDate + ", processed=" + processed)
				.isEqualTo(expectedExpired);
	}
}