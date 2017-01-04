package org.adempiere.bpartner.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.junit.Assert.assertSame;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.junit.Before;
import org.junit.Test;

import de.metas.interfaces.I_C_BPartner;
import mockit.Expectations;
import mockit.Mocked;

public class BPartnerBLTests
{
	@Mocked
	I_C_BPartner partner;

	@Mocked
	ISysConfigBL sysConfigBL;

	/**
	 * Register our mocked service implementation
	 */
	@Before
	public void init()
	{
		Services.registerService(ISysConfigBL.class, sysConfigBL);
	}

	@Test
	public void test_isAllowConsolidateInOutEffective()
	{
		boolean allowsConsolidate = true;
		boolean sysConfigValue = true;

		allowsConsolidate = false;
		{
			sysConfigValue = false;
			{
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, false, sysConfigValue, false);
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, true, sysConfigValue, false);
			}

			sysConfigValue = true;
			{
				// soTrx=false (i.e. purchase-side) and the sysconfig-value is also set to true => override the allowsConsolidate = false
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, false, sysConfigValue, false);
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, true, sysConfigValue, true);
			}
		}

		allowsConsolidate = true;
		final boolean expectedResultInthisWholeBlock = true;
		{
			sysConfigValue = false;
			{
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, false, sysConfigValue, expectedResultInthisWholeBlock);
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, true, sysConfigValue, expectedResultInthisWholeBlock);
			}

			sysConfigValue = true;
			{
				// soTrx=false (i.e. purchase-side) and the sysconfig-value is also set to true => override the allowsConsolidate = false
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, false, sysConfigValue, expectedResultInthisWholeBlock);
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, true, sysConfigValue, expectedResultInthisWholeBlock);
			}
		}
	}

	protected void verifyIsAllowConsolidateInOutEffective(final boolean partnerAllowsConsolidateInOut,
			final boolean isSOTrx,
			final boolean sysConfigValue,
			final boolean expectedResult)
	{
		partnerAllowsConsolidateInOut(partnerAllowsConsolidateInOut);
		sysConfig_AllowConsolidateInOut_ReturnsTrue(sysConfigValue);

		final BPartnerBL testee = new BPartnerBL();
		assertSame(expectedResult, testee.isAllowConsolidateInOutEffective(partner, isSOTrx));
	}

	private void partnerAllowsConsolidateInOut(final boolean partnerAllowsConsolidateInOut)
	{
		// @formatter:off
		new Expectations()
		{{
				partner.isAllowConsolidateInOut();
				result = partnerAllowsConsolidateInOut;
		}};
		// @formatter:on
	}

	private void sysConfig_AllowConsolidateInOut_ReturnsTrue(final boolean sysConfigReturnsTrue)
	{
		// @formatter:off
		new Expectations()
		{{
				sysConfigBL.getBooleanValue(BPartnerBL.SYSCONFIG_C_BPartner_SOTrx_AllowConsolidateInOut_Override, false);
				result = sysConfigReturnsTrue;
		}};
		// @formatter:on
	}
}
