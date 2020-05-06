package de.metas.async.spi.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.organization.OrgId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.async
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

public class SysConfigBackedSizeBasedWorkpackagePrioConfigTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper
				.get()
				.init();
	}

	/**
	 * Tests the normal case (all configured "correctly") for the following config (position=3 means third WP, queue's size=2).
	 *
	 * <pre>
	 *     1 => urgend
	 *  2 -5 => high
	 *  6-15 => medium
	 * 16-30 => low
	 *   >30 => minor
	 * </pre>
	 */
	@Test
	public void testNormalSysConfig()
	{
		final SysconfigBackedSizeBasedWorkpackagePrioConfig config = new SysconfigBackedSizeBasedWorkpackagePrioConfig(Env.getCtx(), "testInternalName", ConstantWorkpackagePrio.low());
		final String sysConfigPrefix = config.getSysConfigPrefix();

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		sysConfigBL.setValue(sysConfigPrefix + "31", "minor", ClientId.SYSTEM, OrgId.ANY);
		sysConfigBL.setValue(sysConfigPrefix + "16", "low", ClientId.SYSTEM, OrgId.ANY);
		sysConfigBL.setValue(sysConfigPrefix + "0006", "medium", ClientId.SYSTEM, OrgId.ANY); // leading zeros shall not matter
		sysConfigBL.setValue(sysConfigPrefix + "02", "HIGH", ClientId.SYSTEM, OrgId.ANY);
		sysConfigBL.setValue(sysConfigPrefix + "1", "URgENT", ClientId.SYSTEM, OrgId.ANY); // cases shall not matter

		assertThat("Priority for size=100", config.apply(100), is(ConstantWorkpackagePrio.minor()));

		//

		assertThat("Priority for position=31/size=30", config.apply(31 - 1), is(ConstantWorkpackagePrio.minor()));
		assertThat("Priority for position=30/size=29", config.apply(30 - 1), is(ConstantWorkpackagePrio.low()));

		//
		assertThat("Priority for position=16/size=15", config.apply(16 - 1), is(ConstantWorkpackagePrio.low()));
		assertThat("Priority for position=15/size=14", config.apply(15 - 1), is(ConstantWorkpackagePrio.medium()));

		// still low, because there is no correct number string for 5
		assertThat("Priority for position=6/size=5", config.apply(6 - 1), is(ConstantWorkpackagePrio.medium()));
		assertThat("Priority for position=5/size=4", config.apply(5 - 1), is(ConstantWorkpackagePrio.high()));

		assertThat("Priority for position=2/size=1", config.apply(2 - 1), is(ConstantWorkpackagePrio.high()));
		assertThat("Priority for position=1/size=0", config.apply(1 - 1), is(ConstantWorkpackagePrio.urgent()));

		// won't happen, but still works
		assertThat("Priority for position=0/size=-1", config.apply(0), is(ConstantWorkpackagePrio.urgent()));
	}

	/**
	 * Tests the some misconfig-cases for the following config (position=3 means third WP, queue's size=2).
	 *
	 * <pre>
	 *   1.2 => urgend (not an int, shall be ignored, i.e. also "high", as this is the "closest" prio we have got this size)
	 *  2-25 => high (OK)
	 * 26-50 => mediummm (wrong spelling, shall be ignored, i.e. also "high")
	 * 51-100 => low (OK)
	 *   >100 => minor (OK)
	 * </pre>
	 */
	@Test
	public void testWrongSysConfig()
	{
		final SysconfigBackedSizeBasedWorkpackagePrioConfig config = new SysconfigBackedSizeBasedWorkpackagePrioConfig(Env.getCtx(), "testInternalName", ConstantWorkpackagePrio.low());
		final String sysConfigPrefix = config.getSysConfigPrefix();

		final ISysConfigBL iSysConfigBL = Services.get(ISysConfigBL.class);
		iSysConfigBL.setValue(sysConfigPrefix + "101", "minor", ClientId.SYSTEM, OrgId.ANY);
		iSysConfigBL.setValue(sysConfigPrefix + "51", "low", ClientId.SYSTEM, OrgId.ANY);
		iSysConfigBL.setValue(sysConfigPrefix + "25", "mediummm", ClientId.SYSTEM, OrgId.ANY); // wrong string, should be skipped
		iSysConfigBL.setValue(sysConfigPrefix + "2", "high", ClientId.SYSTEM, OrgId.ANY);
		iSysConfigBL.setValue(sysConfigPrefix + "1.2", "urgent", ClientId.SYSTEM, OrgId.ANY); // not an int number, should also be skipped

		assertThat("Priority for size=101", config.apply(101 - 1), is(ConstantWorkpackagePrio.minor()));
		assertThat("Priority for size=100", config.apply(100 - 1), is(ConstantWorkpackagePrio.low()));
		assertThat("Priority for size=99", config.apply(99 - 1), is(ConstantWorkpackagePrio.low()));

		assertThat("Priority for size=51", config.apply(51 - 1), is(ConstantWorkpackagePrio.low()));
		assertThat("Priority for size=50", config.apply(50 - 1), is(ConstantWorkpackagePrio.high()));
		assertThat("Priority for size=49", config.apply(49 - 1), is(ConstantWorkpackagePrio.high())); // because there is no correct "medium", everything between 2 and 50 is high

		// still low, because the value fore 25 has a wrong prio string
		assertThat("Priority for size=26", config.apply(26 - 1), is(ConstantWorkpackagePrio.high()));
		assertThat("Priority for size=25", config.apply(25 - 1), is(ConstantWorkpackagePrio.high()));
		assertThat("Priority for size=24", config.apply(24 - 1), is(ConstantWorkpackagePrio.high()));

		assertThat("Priority for size=2", config.apply(2 - 1), is(ConstantWorkpackagePrio.high()));

		// also/still high (not urgent), because there is no correct number string for 1
		assertThat("Priority for size=1", config.apply(1 - 1), is(ConstantWorkpackagePrio.high()));
	}
}
