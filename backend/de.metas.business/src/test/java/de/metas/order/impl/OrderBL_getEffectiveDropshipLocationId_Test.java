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

package de.metas.order.impl;

import de.metas.bpartner.BPartnerLocationId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderBL_getEffectiveDropshipLocationId_Test
{
	private OrderBL orderBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orderBL = new OrderBL();
	}

	@Test
	void givenDropShipOrder_whenGetEffectiveDropshipLocationId_thenReturnDropshipLocation()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsDropShip(true);
		order.setDropShip_BPartner_ID(100);
		order.setDropShip_Location_ID(200);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);

		final BPartnerLocationId result = orderBL.getEffectiveDropshipLocationId(order);

		assertThat(result).isEqualTo(BPartnerLocationId.ofRepoId(100, 200));
	}

	@Test
	void givenDropShipValuesSetButFlagOff_whenGetEffectiveDropshipLocationId_thenReturnDropshipLocation()
	{
		// IsDropShip is irrelevant: when DropShip_* are set they are the ultimate consignee, regardless of the flag.
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsDropShip(false);
		order.setDropShip_BPartner_ID(100);
		order.setDropShip_Location_ID(200);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);

		final BPartnerLocationId result = orderBL.getEffectiveDropshipLocationId(order);

		assertThat(result).isEqualTo(BPartnerLocationId.ofRepoId(100, 200));
	}

	@Test
	void givenNoDropShipValues_whenGetEffectiveDropshipLocationId_thenReturnBPartnerLocation()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsDropShip(false);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);

		final BPartnerLocationId result = orderBL.getEffectiveDropshipLocationId(order);

		assertThat(result).isEqualTo(BPartnerLocationId.ofRepoId(10, 20));
	}

	@Test
	void givenDropShipOrderWithNoDropShipLocation_whenGetEffectiveDropshipLocationId_thenReturnBPartnerLocation()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setIsDropShip(true);
		order.setDropShip_BPartner_ID(100);
		order.setDropShip_Location_ID(0); // no dropship location
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);

		final BPartnerLocationId result = orderBL.getEffectiveDropshipLocationId(order);

		assertThat(result).isEqualTo(BPartnerLocationId.ofRepoId(10, 20));
	}
}
