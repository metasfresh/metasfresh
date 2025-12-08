/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderBLTest
{
	private OrderBL orderBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orderBL = new OrderBL();
	}

	// bill-location - get Bill_Location_ID, maybe fallback to C_BPartner_Location_ID
	////////////////////////////////
	@Test
	void givenNoBillPartner_whenGetBillToLocationId_thenReturnAD_User_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);
		assertThat(orderBL.getBillToLocationId(order)).isEqualTo(BPartnerLocationAndCaptureId.ofRepoId(10, 20, 0));
	}

	@Test
	void givenSameBillPartnerWithoutLocation_whenGetBillToLocationId_thenReturnC_BPartner_Location_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);
		order.setBill_BPartner_ID(10);

		assertThat(orderBL.getBillToLocationId(order)).isEqualTo(BPartnerLocationAndCaptureId.ofRepoId(10, 20, 0));
	}

	@Test
	void givenDifferentBillPartnerWithoutLocation_whenGetBillToLocationId_thenReturnC_BPartner_Location_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);
		order.setBill_BPartner_ID(30); // Bill_BPartner_ID will be ignored! but in practice we never have a Bill_BPartner_ID without Bill_Location_ID

		assertThat(orderBL.getBillToLocationId(order)).isEqualTo(BPartnerLocationAndCaptureId.ofRepoId(10, 20, 0));
	}

	@Test
	void givenDifferentBillPartnerWithLocation_whenGetBillToLocationId_thenReturnBill_Location_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setC_BPartner_Location_ID(20);
		order.setBill_BPartner_ID(30);
		order.setBill_Location_ID(40);

		assertThat(orderBL.getBillToLocationId(order)).isEqualTo(BPartnerLocationAndCaptureId.ofRepoId(30, 40, 0));
	}

	// bill-contact - get Bill_User_ID, maybe fallback to AD_User_ID
	////////////////////////////////
	@Test
	void givenNoBillPartner_whenGetBillToContactId_thenReturnAD_User_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setAD_User_ID(20);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isEqualTo(BPartnerContactId.ofRepoId(10, 20));
	}

	@Test
	void givenNoBillPartnerAndNotContact_whenGetBillToContactId_thenReturnNull()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isNull();
	}

	@Test
	void givenSameBillPartnerWithoutContact_whenGetBillToContactId_thenReturnAD_User_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setAD_User_ID(20);
		order.setBill_BPartner_ID(10);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isEqualTo(BPartnerContactId.ofRepoId(10, 20));
	}

	@Test
	void givenDifferentBillPartnerWithoutContact_whenGetBillToContactId_thenReturnNull()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setAD_User_ID(20);
		order.setBill_BPartner_ID(30);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isNull();
	}

	@Test
	void givenDifferentBillPartnerWithContact_whenGetBillToContactId_thenReturnBill_User_ID()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_BPartner_ID(10);
		order.setAD_User_ID(20);
		order.setBill_BPartner_ID(30);
		order.setBill_User_ID(40);

		assertThat(orderBL.getBillToContactIdOrNull(order)).isEqualTo(BPartnerContactId.ofRepoId(30, 40));
	}
}