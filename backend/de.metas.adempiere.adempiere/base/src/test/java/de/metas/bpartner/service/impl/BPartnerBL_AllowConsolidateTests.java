package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.service.ClientId;

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

import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.interfaces.I_C_BPartner;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.user.UserRepository;
import de.metas.util.Services;

public class BPartnerBL_AllowConsolidateTests
{
	private ISysConfigBL sysConfigBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		sysConfigBL = Services.get(ISysConfigBL.class);
	}

	private void verifyIsAllowConsolidateInOutEffective(
			final boolean partnerAllowsConsolidateInOut,
			final SOTrx soTrx,
			final boolean sysConfigValue,
			final boolean expectedResult)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setAllowConsolidateInOut(partnerAllowsConsolidateInOut);

		setAllowConsolidateInOutSysConfigValue(sysConfigValue);

		final BPartnerBL testee = new BPartnerBL(new UserRepository());
		assertThat(testee.isAllowConsolidateInOutEffective(partner, soTrx)).isEqualTo(expectedResult);
	}

	private void setAllowConsolidateInOutSysConfigValue(final boolean sysConfigReturnsTrue)
	{
		sysConfigBL.setValue(BPartnerBL.SYSCONFIG_C_BPartner_SOTrx_AllowConsolidateInOut_Override, sysConfigReturnsTrue, ClientId.SYSTEM, OrgId.ANY);
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
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, SOTrx.PURCHASE, sysConfigValue, false);
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, SOTrx.SALES, sysConfigValue, false);
			}

			sysConfigValue = true;
			{
				// soTrx=false (i.e. purchase-side) and the sysconfig-value is also set to true => override the allowsConsolidate = false
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, SOTrx.PURCHASE, sysConfigValue, false);
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, SOTrx.SALES, sysConfigValue, true);
			}
		}

		allowsConsolidate = true;
		final boolean expectedResultInthisWholeBlock = true;
		{
			sysConfigValue = false;
			{
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, SOTrx.PURCHASE, sysConfigValue, expectedResultInthisWholeBlock);
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, SOTrx.SALES, sysConfigValue, expectedResultInthisWholeBlock);
			}

			sysConfigValue = true;
			{
				// soTrx=false (i.e. purchase-side) and the sysconfig-value is also set to true => override the allowsConsolidate = false
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, SOTrx.PURCHASE, sysConfigValue, expectedResultInthisWholeBlock);
				verifyIsAllowConsolidateInOutEffective(allowsConsolidate, SOTrx.SALES, sysConfigValue, expectedResultInthisWholeBlock);
			}
		}
	}
}
