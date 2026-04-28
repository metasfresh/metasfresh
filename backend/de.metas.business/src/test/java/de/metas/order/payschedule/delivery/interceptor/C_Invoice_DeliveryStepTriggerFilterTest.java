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

package de.metas.order.payschedule.delivery.interceptor;

import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.payschedule.delivery.OrderPayScheduleDeliveryService;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the trigger-filter logic of {@link C_Invoice_DeliveryStep}.
 *
 * <p>AC #26: verifies the four-gate filter:
 * <ul>
 *   <li>Sales invoice → not eligible.
 *   <li>Proforma invoice (IsFinancial=N) → not eligible.
 *   <li>APC (purchase credit memo) → not eligible.
 *   <li>API (financial purchase invoice) → eligible.
 * </ul>
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@ExtendWith(MockitoExtension.class)
class C_Invoice_DeliveryStepTriggerFilterTest
{
	@Mock private OrderPayScheduleDeliveryService deliveryService;
	@Mock private ProformaOrderAllocRepository proformaAllocRepo;
	@Mock private IInvoiceBL invoiceBL;

	private C_Invoice_DeliveryStep interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IInvoiceBL.class, invoiceBL);
		interceptor = new C_Invoice_DeliveryStep(deliveryService, proformaAllocRepo);
	}

	// -----------------------------------------------------------------------
	// Cases that should NOT pass the filter
	// -----------------------------------------------------------------------

	/**
	 * A sales (AR) invoice is not eligible — only purchase invoices drive the Delivery step.
	 * The isSOTrx guard fires before getInvoiceDocBaseType is called.
	 */
	@Test
	void salesInvoice_notEligible()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setIsSOTrx(true);
		invoice.setIsFinancial(true);
		// No stub for getInvoiceDocBaseType — the guard returns early on isSOTrx

		assertThat(interceptor.isFinancialPurchaseNonCreditMemoInvoice(invoice)).isFalse();
	}

	/**
	 * A proforma purchase invoice has {@code IsFinancial=N} and must be excluded.
	 * Proformas are the trigger for iter-2 but must not create a cycle in iter-3.
	 */
	@Test
	void proformaInvoice_notEligible()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setIsSOTrx(false);
		invoice.setIsFinancial(false);   // proforma invoices have IsFinancial='N'

		// getInvoiceDocBaseType is not called when IsFinancial='N' (guard returns early)
		assertThat(interceptor.isFinancialPurchaseNonCreditMemoInvoice(invoice)).isFalse();
	}

	/**
	 * An APC (Accounts-Payable Credit Memo / purchase credit note) must be excluded.
	 * AC #26: credit memos do not trigger the delivery step.
	 */
	@Test
	void apcCreditMemo_notEligible()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setIsSOTrx(false);
		invoice.setIsFinancial(true);

		when(invoiceBL.getInvoiceDocBaseType(any())).thenReturn(InvoiceDocBaseType.VendorCreditMemo);

		assertThat(interceptor.isFinancialPurchaseNonCreditMemoInvoice(invoice)).isFalse();
	}

	// -----------------------------------------------------------------------
	// Case that SHOULD pass the filter
	// -----------------------------------------------------------------------

	/**
	 * A regular AP financial purchase invoice (API / VendorInvoice) is eligible.
	 */
	@Test
	void apiFinancialPurchaseInvoice_eligible()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setIsSOTrx(false);
		invoice.setIsFinancial(true);

		when(invoiceBL.getInvoiceDocBaseType(any())).thenReturn(InvoiceDocBaseType.VendorInvoice);

		assertThat(interceptor.isFinancialPurchaseNonCreditMemoInvoice(invoice)).isTrue();
	}
}
