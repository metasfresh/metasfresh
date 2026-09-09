/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.inout.impl;

import de.metas.bpartner.BPartnerLocationId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InOutBL_getEffectiveDropshipLocationId_Test
{
	private InOutBL inOutBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		inOutBL = new InOutBL();
	}

	@Test
	void givenDropShipInOut_whenGetEffectiveDropshipLocationId_thenReturnDropshipLocation()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		inout.setIsDropShip(true);
		inout.setDropShip_BPartner_ID(100);
		inout.setDropShip_Location_ID(200);
		inout.setC_BPartner_ID(10);
		inout.setC_BPartner_Location_ID(20);

		final BPartnerLocationId result = inOutBL.getEffectiveDropshipLocationId(inout);

		assertThat(result).isEqualTo(BPartnerLocationId.ofRepoId(100, 200));
	}

	@Test
	void givenDropShipInOutWithNoDropShipLocation_whenGetEffectiveDropshipLocationId_thenReturnBPartnerLocation()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		inout.setIsDropShip(true);
		inout.setDropShip_BPartner_ID(100);
		inout.setDropShip_Location_ID(0); // no dropship location
		inout.setC_BPartner_ID(10);
		inout.setC_BPartner_Location_ID(20);

		final BPartnerLocationId result = inOutBL.getEffectiveDropshipLocationId(inout);

		assertThat(result).isEqualTo(BPartnerLocationId.ofRepoId(10, 20));
	}

	@Test
	void givenNoDropShipFields_whenGetEffectiveDropshipLocationId_thenReturnBPartnerLocation()
	{
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class);
		inout.setDropShip_BPartner_ID(0);
		inout.setDropShip_Location_ID(0);
		inout.setC_BPartner_ID(10);
		inout.setC_BPartner_Location_ID(20);

		final BPartnerLocationId result = inOutBL.getEffectiveDropshipLocationId(inout);

		assertThat(result).isEqualTo(BPartnerLocationId.ofRepoId(10, 20));
	}
}
