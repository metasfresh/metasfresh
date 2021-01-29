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
import de.metas.currency.CurrencyCode;
import de.metas.money.CurrencyId;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.order.model.interceptor.C_Order;
import de.metas.organization.OrgId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.rest_api.bpartner_pricelist.BpartnerPriceListServicesFacade;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PaymentRestEndpointTest
{
	public static final CurrencyCode CURRENCY_CODE_EUR = CurrencyCode.EUR;
	public static final BigDecimal PAYMENT_AMOUNT = BigDecimal.valueOf(123.456);
	public static final String TARGET_IBAN = "012345678901234";
	public static final String AD_Org_Value = "orgCode";

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
	void normalInboundFlow()
	{
		final OrgId orgId = createOrgAndBankAccount();

		final ExternalId externalOrderId = ExternalId.of("Order");
		final IdentifierString partnerIdentifier = IdentifierString.of("ext-bPartner");

		// create test data
		final I_C_Order salesOrder = createSalesOrder(orgId, externalOrderId);
		createBPartner(partnerIdentifier);

		// create JsonPaymentInfo
		final JsonInboundPaymentInfo jsonInboundPaymentInfo = JsonInboundPaymentInfo.builder()
				.orgCode(AD_Org_Value)
				.externalOrderId(externalOrderId.getValue())
				.bpartnerIdentifier(partnerIdentifier.toJson())
				.currencyCode(CURRENCY_CODE_EUR.toThreeLetterCode())
				.amount(PAYMENT_AMOUNT)
				.targetIBAN(TARGET_IBAN)
				.build();

		assertEquals(JsonInboundPaymentInfo.builder()
				.orgCode(AD_Org_Value)
				.externalOrderId("Order")
				.bpartnerIdentifier("ext-bPartner")
				.currencyCode(CURRENCY_CODE_EUR.toThreeLetterCode())
				.amount(PAYMENT_AMOUNT)
				.targetIBAN(TARGET_IBAN)
				.build(),
				jsonInboundPaymentInfo);

		// process JsonPaymentInfo
		final ResponseEntity<String> response = paymentRestEndpoint.createInboundPayment(jsonInboundPaymentInfo);
		assertNull(response.getBody());
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

		// noinspection OptionalGetWithoutIsPresent
		final I_C_Payment payment = paymentDAO.getByExternalOrderId(externalOrderId, orgId).get();

		assertEquals(0, salesOrder.getC_Payment_ID());
		assertEquals(0, payment.getC_Order_ID());
		assertEquals(externalOrderId.getValue(), payment.getExternalOrderId());

		// enable auto linking SO <-> Payment
		Services.get(ISysConfigBL.class).setValue(C_Order.AUTO_ASSIGN_TO_SALES_ORDER_BY_EXTERNAL_ORDER_ID_SYSCONFIG, true, ClientId.SYSTEM, OrgId.ANY);

		// run the "before_complete" interceptor
		new C_Order(new OrderLineDetailRepository()).linkWithPaymentByExternalOrderId(salesOrder);

		// test that SO is linked with the payment
		assertEquals(payment.getC_Payment_ID(), salesOrder.getC_Payment_ID());
		refresh(payment);
		assertEquals(payment.getC_Order_ID(), salesOrder.getC_Order_ID());
	}

	@NonNull
	private I_C_Order createSalesOrder(
			@NonNull final OrgId orgId,
			@NonNull final ExternalId externalOrderId)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setAD_Org_ID(orgId.getRepoId());
		order.setExternalId(externalOrderId.getValue());
		order.setIsSOTrx(true);

		saveRecord(order);
		return order;
	}

	private void createBPartner(@NonNull final IdentifierString bpartnerIdentifier)
	{
		final I_C_BPartner bPartner = newInstance(I_C_BPartner.class);
		bPartner.setExternalId(bpartnerIdentifier.asExternalId().getValue());

		saveRecord(bPartner);
	}

	private OrgId createOrgAndBankAccount()
	{
		final I_AD_Org orgRecord = newInstance(I_AD_Org.class);
		orgRecord.setValue(AD_Org_Value);
		saveRecord(orgRecord);

		final I_C_BPartner bPartner = newInstance(I_C_BPartner.class);
		bPartner.setAD_Org_ID(orgRecord.getAD_Org_ID());
		bPartner.setAD_OrgBP_ID(orgRecord.getAD_Org_ID());
		saveRecord(bPartner);

		createBpBankAccount(bPartner.getC_BPartner_ID());

		return OrgId.ofRepoId(orgRecord.getAD_Org_ID());
	}

	private void createBpBankAccount(final int bPartnerId)
	{
		final CurrencyId currencyId = currencyService.getCurrencyId(CURRENCY_CODE_EUR.toThreeLetterCode());
		assertNotNull(currencyId);

		final I_C_BP_BankAccount bpBankAccount = newInstance(I_C_BP_BankAccount.class);
		bpBankAccount.setIBAN(TARGET_IBAN);
		bpBankAccount.setC_BPartner_ID(bPartnerId);
		bpBankAccount.setC_Currency_ID(currencyId.getRepoId());

		saveRecord(bpBankAccount);
	}
}
