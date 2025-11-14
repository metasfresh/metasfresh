/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.bpartner.effective;

import de.metas.lang.SOTrx;
import de.metas.pricing.PricingSystemId;
import de.metas.util.StringUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_PricingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class BPartnerEffectiveBLTest
{
	private BPartnerEffectiveBL bpartnerEffectiveBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
        bpartnerEffectiveBL = BPartnerEffectiveBL.newInstanceForUnitTesting();
	}

	@Test
	public void getEffectiveValueTest()
	{
		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);
		pricingSystem.setM_PricingSystem_ID(1);
		saveRecord(pricingSystem);

		final I_C_BP_Group bpGroupParent = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		bpGroupParent.setC_BP_Group_ID(2);
		bpGroupParent.setIsAutoInvoice(StringUtils.ofBoolean(false));
		saveRecord(bpGroupParent);

		final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class);
		bpGroup.setC_BP_Group_ID(3);
		bpGroup.setParent_BP_Group_ID(bpGroupParent.getC_BP_Group_ID());
		bpGroup.setIsAutoInvoice(StringUtils.ofBoolean(true));
		bpGroup.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		saveRecord(bpGroup);

		final I_C_BPartner partner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		partner.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		saveRecord(partner);

		final BPartnerEffective bPartnerEffective = bpartnerEffectiveBL.getByRecord(partner);
		assertThat(bPartnerEffective.getPaymentRule(SOTrx.SALES).isOnCredit()).isTrue();
		assertThat(bPartnerEffective.isAutoInvoice).isTrue();
		assertThat(bPartnerEffective.getPricingSystemId(SOTrx.SALES)).isEqualTo(PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID()));
	}
}
