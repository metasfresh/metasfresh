/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.rest_api.payment;

import de.metas.adempiere.model.I_C_Order;
import de.metas.money.CurrencyId;
import de.metas.order.model.interceptor.C_Order;
import de.metas.organization.OrgId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.rest_api.bpartner_pricelist.BpartnerPriceListServicesFacade;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentRestEndpointTest
{
	public static final String CURRENCY_CODE_EUR = "EUR";
	public static final BigDecimal PAYMENT_AMOUNT = BigDecimal.valueOf(123.456);
	public static final String TARGET_IBAN = "012345678901234";

	private final CurrencyService currencyService = new CurrencyService();
	private final BpartnerPriceListServicesFacade bpartnerPriceListServicesFacade = new BpartnerPriceListServicesFacade();
	private final JsonPaymentService jsonPaymentService = new JsonPaymentService(currencyService, bpartnerPriceListServicesFacade);
	private final PaymentRestEndpoint paymentRestEndpoint = new PaymentRestEndpointImpl(jsonPaymentService);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void normalFlow()
	{
		final IdentifierString orderIdentifier = IdentifierString.of("ext-Order");
		final IdentifierString partnerIdentifier = IdentifierString.of("ext-bPartner");

		final OrgId orgId = OrgId.ANY;

		// create test data
		final I_C_Order salesOrder = createSalesOrder(orderIdentifier);
		createBPartnerAndBankAccount(partnerIdentifier);

		// create JsonPaymentInfo
		final JsonPaymentInfo jsonPaymentInfo = JsonPaymentInfo.builder()
				.externalOrderId(orderIdentifier.toJson())
				.externalBpartnerId(partnerIdentifier.toJson())
				.currencyCode(CURRENCY_CODE_EUR)
				.amount(PAYMENT_AMOUNT)
				.targetIBAN(TARGET_IBAN)
				.build();

		assertEquals(JsonPaymentInfo.builder()
						.externalOrderId("ext-Order")
						.externalBpartnerId("ext-bPartner")
						.currencyCode(CURRENCY_CODE_EUR)
						.amount(PAYMENT_AMOUNT)
						.targetIBAN(TARGET_IBAN)
						.build(),
				jsonPaymentInfo);

		// process JsonPaymentInfo
		final ResponseEntity<String> response = paymentRestEndpoint.createPayment(jsonPaymentInfo);
		assertNull(response.getBody());
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

		//noinspection OptionalGetWithoutIsPresent
		final I_C_Payment payment = paymentDAO.getByExternalOrderId(ExternalId.of(orderIdentifier.asExternalId().getValue()), orgId).get();

		assertEquals(0, salesOrder.getC_Payment_ID());
		assertEquals(0, payment.getC_Order_ID());
		assertEquals(orderIdentifier.asExternalId().getValue(), payment.getExternalOrderId());

		// enable auto linking SO <-> Payment
		Services.get(ISysConfigBL.class).setValue(C_Order.AUTO_ASSIGN_TO_SALES_ORDER_BY_EXTERNAL_ORDER_ID_SYSCONFIG, true, 0);

		// run the "before_complete" interceptor
		C_Order.INSTANCE.linkWithPaymentByExternalOrderId(salesOrder);

		// test that SO is linked with the payment
		assertEquals(payment.getC_Payment_ID(), salesOrder.getC_Payment_ID());
		InterfaceWrapperHelper.refresh(payment);
		assertEquals(payment.getC_Order_ID(), salesOrder.getC_Order_ID());
	}

	@NonNull
	private I_C_Order createSalesOrder(@NonNull final IdentifierString orderIdentifier)
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setExternalId(orderIdentifier.asExternalId().getValue());
		order.setIsSOTrx(true);

		InterfaceWrapperHelper.save(order);
		return order;
	}

	private void createBPartnerAndBankAccount(@NonNull final IdentifierString bpartnerIdentifier)
	{
		final I_C_BPartner bPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bPartner.setExternalId(bpartnerIdentifier.asExternalId().getValue());

		InterfaceWrapperHelper.save(bPartner);
		createBpBankAccount(bPartner.getC_BPartner_ID());
	}

	private void createBpBankAccount(final int bPartnerId)
	{
		final CurrencyId currencyId = currencyService.getCurrencyId(CURRENCY_CODE_EUR);
		assertNotNull(currencyId);

		final I_C_BP_BankAccount bpBankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class);
		bpBankAccount.setIBAN(TARGET_IBAN);
		bpBankAccount.setC_BPartner_ID(bPartnerId);
		bpBankAccount.setC_Currency_ID(currencyId.getRepoId());

		InterfaceWrapperHelper.save(bpBankAccount);
	}
}
