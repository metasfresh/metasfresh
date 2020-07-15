package org.adempiere.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.Properties;

import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.organization.OrgId;
import de.metas.util.Services;

public class SysConfigBLTests
{
	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private ISysConfigBL sysConfigBL;

	private Properties ctx;
	private ClientId adClientId;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		sysConfigBL = Services.get(ISysConfigBL.class);

		this.ctx = Env.getCtx();
		this.adClientId = ClientId.ofRepoId(1);
		Env.setContext(ctx, "#AD_Client_ID", adClientId.getRepoId());
	}

	@Test
	public void testSetGet_String()
	{
		testSetGet("String1", "Value1", 0);
		testSetGet("String2", "Value2", 1);
	}

	private void testSetGet(final String name, final String value, final int AD_Org_ID)
	{
		//
		// Test on same org
		{
			final OrgId adOrgIdToUse = OrgId.ofRepoIdOrAny(AD_Org_ID);

			sysConfigBL.setValue(name, value, adClientId, adOrgIdToUse);

			final String valueGet = sysConfigBL.getValue(name,
					adClientId.getRepoId(),
					adOrgIdToUse.getRepoId());

			Assert.assertEquals("Invalid value for " + name + ", AD_Org_ID=" + adOrgIdToUse,
					value, // expected
					valueGet// actual
			);
		}

		//
		// Test: set the value on AD_Org_ID=0, get value using given Org
		{
			sysConfigBL.setValue(name, value, adClientId, OrgId.ANY);
			final String valueGet = sysConfigBL.getValue(name,
					adClientId.getRepoId(),
					OrgId.ANY.getRepoId());

			Assert.assertEquals("Invalid value for " + name + ", AD_Org_ID=" + OrgId.ANY,
					value, // expected
					valueGet// actual
			);

			// Test with any other org:
			final String valueGet_Org = sysConfigBL.getValue(name,
					adClientId.getRepoId(),
					AD_Org_ID);
			Assert.assertEquals("Invalid value for " + name + ", AD_Org_ID=" + AD_Org_ID,
					value, // expected
					valueGet_Org// actual
			);

		}

	}
}
