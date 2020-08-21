/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.invoice.interceptor;

import de.metas.adempiere.model.I_C_Order;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

class C_InvoiceInterceptorTest
{
	private I_C_DocType prepayDocType;
	private I_C_DocType salesOrderDocType;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		prepayDocType = createDocType(X_C_DocType.DOCBASETYPE_SalesOrder, X_C_DocType.DOCSUBTYPE_PrepayOrder);
		salesOrderDocType = createDocType(X_C_DocType.DOCBASETYPE_SalesOrder, null);
	}

	@Test
	void canAllocateOrderDoctypePrepay()
	{
		final I_C_Payment payment = createPayment(null);
		final I_C_Order prepayOrder = createSalesOrder(null, prepayDocType, payment);

		Assertions.assertTrue(C_Invoice.canAllocateOrderPaymentToInvoice(prepayOrder));
	}

	@Test
	void canAllocateOrderDoctypeSalesOrder_WithPaymentAndExternalOrderId()
	{
		final ExternalId externalId = ExternalId.of("extId1432");
		final I_C_Payment payment = createPayment(externalId);
		final I_C_Order prepayOrder = createSalesOrder(externalId, salesOrderDocType, payment);

		Assertions.assertTrue(C_Invoice.canAllocateOrderPaymentToInvoice(prepayOrder));
	}

	@Test
	void canNotAllocateOrderDoctypeSalesOrder_MismatchExternalID()
	{
		// note: I think this case should not happen, but I've been wrong before.
		final ExternalId externalIdSO = ExternalId.of("extId1432SO");
		final ExternalId externalIdP = ExternalId.of("extId1432P");
		final I_C_Payment payment = createPayment(externalIdP);
		final I_C_Order prepayOrder = createSalesOrder(externalIdSO, salesOrderDocType, payment);

		Assertions.assertFalse(C_Invoice.canAllocateOrderPaymentToInvoice(prepayOrder));
	}

	@SuppressWarnings("ConstantConditions")
	@NonNull
	private I_C_Payment createPayment(
			@Nullable final ExternalId externalOrderId
	)
	{
		final I_C_Payment payment = newInstance(I_C_Payment.class);
		payment.setExternalOrderId(ExternalId.toValue(externalOrderId));
		payment.getExternalOrderId();
		save(payment);
		return payment;
	}

	@SuppressWarnings("ConstantConditions")
	@NonNull
	private I_C_Order createSalesOrder(
			@Nullable final ExternalId externalOrderId,
			@NonNull final I_C_DocType prepayDocType,
			@NonNull final I_C_Payment payment)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setExternalId(ExternalId.toValue(externalOrderId));
		order.setC_DocType_ID(prepayDocType.getC_DocType_ID());
		order.setIsSOTrx(true);
		order.setC_Payment_ID(payment.getC_Payment_ID());

		saveRecord(order);
		return order;
	}

	@SuppressWarnings("ConstantConditions")
	@NonNull
	protected I_C_DocType createDocType(@NonNull final String baseType, @Nullable final String subType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setDocBaseType(baseType);
		docType.setDocSubType(subType);
		saveRecord(docType);
		return docType;
	}
}
