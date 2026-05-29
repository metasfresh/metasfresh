package de.metas.async.spi.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	@BeforeEach
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

		assertThat(config.apply(100)).as("Priority for size=100").isEqualTo(ConstantWorkpackagePrio.minor());

		//

		assertThat(config.apply(31 - 1)).as("Priority for position=31/size=30").isEqualTo(ConstantWorkpackagePrio.minor());
		assertThat(config.apply(30 - 1)).as("Priority for position=30/size=29").isEqualTo(ConstantWorkpackagePrio.low());

		//
		assertThat(config.apply(16 - 1)).as("Priority for position=16/size=15").isEqualTo(ConstantWorkpackagePrio.low());
		assertThat(config.apply(15 - 1)).as("Priority for position=15/size=14").isEqualTo(ConstantWorkpackagePrio.medium());

		// still low, because there is no correct number string for 5
		assertThat(config.apply(6 - 1)).as("Priority for position=6/size=5").isEqualTo(ConstantWorkpackagePrio.medium());
		assertThat(config.apply(5 - 1)).as("Priority for position=5/size=4").isEqualTo(ConstantWorkpackagePrio.high());

		assertThat(config.apply(2 - 1)).as("Priority for position=2/size=1").isEqualTo(ConstantWorkpackagePrio.high());
		assertThat(config.apply(1 - 1)).as("Priority for position=1/size=0").isEqualTo(ConstantWorkpackagePrio.urgent());

		// won't happen, but still works
		assertThat(config.apply(0)).as("Priority for position=0/size=-1").isEqualTo(ConstantWorkpackagePrio.urgent());
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

		assertThat(config.apply(101 - 1)).as("Priority for size=101").isEqualTo(ConstantWorkpackagePrio.minor());
		assertThat(config.apply(100 - 1)).as("Priority for size=100").isEqualTo(ConstantWorkpackagePrio.low());
		assertThat(config.apply(99 - 1)).as("Priority for size=99").isEqualTo(ConstantWorkpackagePrio.low());

		assertThat(config.apply(51 - 1)).as("Priority for size=51").isEqualTo(ConstantWorkpackagePrio.low());
		assertThat(config.apply(50 - 1)).as("Priority for size=50").isEqualTo(ConstantWorkpackagePrio.high());
		assertThat(config.apply(49 - 1)).as("Priority for size=49").isEqualTo(ConstantWorkpackagePrio.high()); // because there is no correct "medium", everything between 2 and 50 is high

		// still low, because the value fore 25 has a wrong prio string
		assertThat(config.apply(26 - 1)).as("Priority for size=26").isEqualTo(ConstantWorkpackagePrio.high());
		assertThat(config.apply(25 - 1)).as("Priority for size=25").isEqualTo(ConstantWorkpackagePrio.high());
		assertThat(config.apply(24 - 1)).as("Priority for size=24").isEqualTo(ConstantWorkpackagePrio.high());

		assertThat(config.apply(2 - 1)).as("Priority for size=2").isEqualTo(ConstantWorkpackagePrio.high());

		// also/still high (not urgent), because there is no correct number string for 1
		assertThat(config.apply(1 - 1)).as("Priority for size=1").isEqualTo(ConstantWorkpackagePrio.high());
	}
}