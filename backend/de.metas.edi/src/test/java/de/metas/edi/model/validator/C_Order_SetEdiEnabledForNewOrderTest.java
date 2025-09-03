/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.model.validator;

import de.metas.edi.api.IEDIInputDataSourceBL;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_C_Order;
import de.metas.util.Services;
import lombok.Setter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class C_Order_SetEdiEnabledForNewOrderTest
{
	private C_Order validator;
	private IEDIInputDataSourceBL mockEdiInputDataSourceBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		mockEdiInputDataSourceBL = new MockEDIInputDataSourceBL();
		Services.registerService(IEDIInputDataSourceBL.class, mockEdiInputDataSourceBL);

		validator = new C_Order();
	}

	@Test
	void setEdiEnabledForNewOrder_whenInputDataSourceIsEDI_shouldSetEdiEnabled()
	{
		// given
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_InputDataSource_ID(123);

		((MockEDIInputDataSourceBL)mockEdiInputDataSourceBL).setReturnValue(true);

		// when
		validator.setEdiEnabledForNewOrder(order);

		// then
		assertThat(order.isEdiEnabled()).isTrue();
	}

	@Test
	void setEdiEnabledForNewOrder_whenInputDataSourceIsNotEDI_andBuyerIsEdiInvoiceRecipient_shouldSetEdiEnabled()
	{
		// given
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_InputDataSource_ID(123);

		// Create buyer partner that is EDI invoice recipient
		final I_C_BPartner buyer = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		buyer.setC_BPartner_ID(456);
		buyer.setIsEdiInvoicRecipient(true);
		InterfaceWrapperHelper.save(buyer);

		// Create order with buyer partner as bill partner
		order.setC_BPartner_ID(456);
		order.setBill_BPartner_ID(456);

		((MockEDIInputDataSourceBL)mockEdiInputDataSourceBL).setReturnValue(false);

		// when
		validator.setEdiEnabledForNewOrder(order);

		// then
		assertThat(order.isEdiEnabled()).isTrue();
	}

	@Test
	void setEdiEnabledForNewOrder_whenInputDataSourceIsNotEDI_andBuyerIsEdiDesadvRecipient_shouldSetEdiEnabled()
	{
		// given
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_InputDataSource_ID(123);

		// Create buyer partner that is EDI invoice recipient
		final I_C_BPartner buyer = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		buyer.setC_BPartner_ID(456);
		buyer.setIsEdiDesadvRecipient(true);

		InterfaceWrapperHelper.save(buyer);

		order.setC_BPartner_ID(456);

		((MockEDIInputDataSourceBL)mockEdiInputDataSourceBL).setReturnValue(false);

		// when
		validator.setEdiEnabledForNewOrder(order);

		// then
		assertThat(order.isEdiEnabled()).isTrue();
	}

	@Test
	void setEdiEnabledForNewOrder_whenInputDataSourceIsNotEDI_andBuyerIsEdiInvoicRecipient_shouldSetEdiEnabled()
	{
		// given
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_InputDataSource_ID(123);

		// Create buyer partner that is EDI invoice recipient
		final I_C_BPartner buyer = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		buyer.setC_BPartner_ID(456);
		buyer.setIsEdiInvoicRecipient(true);

		InterfaceWrapperHelper.save(buyer);

		order.setC_BPartner_ID(456);

		((MockEDIInputDataSourceBL)mockEdiInputDataSourceBL).setReturnValue(false);

		// when
		validator.setEdiEnabledForNewOrder(order);

		// then
		assertThat(order.isEdiEnabled()).isTrue();
	}

	@Test
	void setEdiEnabledForNewOrder_whenInputDataSourceIsNotEDI_andBuyerIsNotEdiRecipient_andDeliveryPartnerIsEdiDesadvRecipient_shouldSetEdiEnabled()
	{
		// given
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_InputDataSource_ID(123);

		// Create buyer partner that is NOT EDI recipient
		final I_C_BPartner buyer = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		buyer.setC_BPartner_ID(456);
		buyer.setIsEdiInvoicRecipient(false);
		InterfaceWrapperHelper.save(buyer);

		// Create delivery partner that IS EDI DESADV recipient
		final I_C_BPartner deliveryPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		deliveryPartner.setC_BPartner_ID(789);
		deliveryPartner.setIsEdiDesadvRecipient(true);
		InterfaceWrapperHelper.save(deliveryPartner);

		// Set up order with both partners
		order.setC_BPartner_ID(456);
		order.setBill_BPartner_ID(456);
		order.setIsDropShip(true);
		order.setDropShip_BPartner_ID(789);

		((MockEDIInputDataSourceBL)mockEdiInputDataSourceBL).setReturnValue(false);

		// when
		validator.setEdiEnabledForNewOrder(order);

		// then
		assertThat(order.isEdiEnabled()).isTrue();
	}

	@Test
	void setEdiEnabledForNewOrder_whenNoEdiCriteriaMet_shouldSetEdiDisabled()
	{
		// given
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_InputDataSource_ID(123);

		// Create buyer partner that is NOT EDI recipient
		final I_C_BPartner buyer = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		buyer.setC_BPartner_ID(456);
		buyer.setIsEdiInvoicRecipient(false);
		InterfaceWrapperHelper.save(buyer);

		// Create delivery partner that is NOT EDI recipient
		final I_C_BPartner deliveryPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		deliveryPartner.setC_BPartner_ID(789);
		deliveryPartner.setIsEdiDesadvRecipient(false);
		InterfaceWrapperHelper.save(deliveryPartner);

		// Set up order with both partners
		order.setC_BPartner_ID(456);
		order.setBill_BPartner_ID(456);
		order.setIsDropShip(true);
		order.setDropShip_BPartner_ID(789);

		((MockEDIInputDataSourceBL)mockEdiInputDataSourceBL).setReturnValue(false);

		// when
		validator.setEdiEnabledForNewOrder(order);

		// then
		assertThat(order.isEdiEnabled()).isFalse();
	}

	@Test
	void setEdiEnabledForNewOrder_whenNoInputDataSource_andNoBuyerPartner_shouldSetEdiDisabled()
	{
		// given
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_InputDataSource_ID(0); // No input data source
		order.setBill_BPartner_ID(0); // No buyer partner
		order.setDropShip_BPartner_ID(0); // No delivery partner

		// when
		validator.setEdiEnabledForNewOrder(order);

		// then
		assertThat(order.isEdiEnabled()).isFalse();
	}

	@Test
	void setEdiEnabledForNewOrder_whenNegativeInputDataSourceId_shouldSetEdiDisabled()
	{
		// given
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_InputDataSource_ID(-1); // Negative input data source ID
		order.setBill_BPartner_ID(0);
		order.setDropShip_BPartner_ID(0);

		// when
		validator.setEdiEnabledForNewOrder(order);

		// then
		assertThat(order.isEdiEnabled()).isFalse();
	}

	@Test
	void setEdiEnabledForNewOrder_whenBuyerPartnerIsNull_shouldCheckDeliveryPartner()
	{
		// given
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setAD_InputDataSource_ID(123);
		order.setBill_BPartner_ID(0); // No buyer partner

		// Create delivery partner that IS EDI DESADV recipient
		final I_C_BPartner deliveryPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		deliveryPartner.setC_BPartner_ID(789);
		deliveryPartner.setIsEdiDesadvRecipient(true);
		InterfaceWrapperHelper.save(deliveryPartner);

		order.setIsDropShip(true);
		order.setDropShip_BPartner_ID(789);

		((MockEDIInputDataSourceBL)mockEdiInputDataSourceBL).setReturnValue(false);

		// when
		validator.setEdiEnabledForNewOrder(order);

		// then
		assertThat(order.isEdiEnabled()).isTrue();
	}

	// // Inner class to help with mocking
	@Setter
	private static class MockEDIInputDataSourceBL implements IEDIInputDataSourceBL
	{
		private boolean returnValue = false;

		@Override
		public boolean isEDIInputDataSource(final int inputDataSourceId)
		{
			return returnValue;
		}

	}
}
