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

package de.metas.invoice.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.due_date.InvoiceDueDateProviderService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.IOrderBL;
import de.metas.organization.IOrgDAO;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.payment.reservation.PaymentReservationService;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

/**
 * Unit tests for the {@link C_Invoice#validateCreditMemoReason} BEFORE_COMPLETE guard (me03#31242):
 * completing a credit memo whose (target) doc type is configured must be blocked while any line is
 * missing its {@code Line_CreditMemoReason}.
 */
class C_Invoice_CreditMemoReasonTest
{
	private static final int DOCTYPE_IN_LIST = 1000004;
	private static final int DOCTYPE_NOT_IN_LIST = 999999;
	private static final String CONFIGURED_DOCTYPE_IDS = "1000004,540901,540902,540943";

	private IInvoiceBL invoiceBL;
	private org.adempiere.service.ISysConfigBL sysConfigBL;
	private C_Invoice interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Register lightweight mocks for the ~10 service fields C_Invoice resolves via Services.get(...),
		// mirroring C_Invoice_IsPartialInvoiceTest. Only IInvoiceBL and ISysConfigBL are stubbed per test.
		Services.registerService(IOrgDAO.class, Mockito.mock(IOrgDAO.class));
		Services.registerService(IPriceListDAO.class, Mockito.mock(IPriceListDAO.class));
		Services.registerService(IPaymentDAO.class, Mockito.mock(IPaymentDAO.class));
		Services.registerService(IPaymentBL.class, Mockito.mock(IPaymentBL.class));
		Services.registerService(IAllocationBL.class, Mockito.mock(IAllocationBL.class));
		Services.registerService(IInvoiceDAO.class, Mockito.mock(IInvoiceDAO.class));
		Services.registerService(IBPartnerDAO.class, Mockito.mock(IBPartnerDAO.class));
		Services.registerService(IAllocationDAO.class, Mockito.mock(IAllocationDAO.class));
		Services.registerService(IOrderBL.class, Mockito.mock(IOrderBL.class));
		Services.registerService(IPaymentTermRepository.class, Mockito.mock(IPaymentTermRepository.class));

		invoiceBL = Mockito.mock(IInvoiceBL.class);
		sysConfigBL = Mockito.mock(org.adempiere.service.ISysConfigBL.class);
		Services.registerService(IInvoiceBL.class, invoiceBL);
		Services.registerService(org.adempiere.service.ISysConfigBL.class, sysConfigBL);

		interceptor = new C_Invoice(
				Mockito.mock(PaymentReservationService.class),
				Mockito.mock(IDocumentLocationBL.class),
				Mockito.mock(InvoiceDueDateProviderService.class));
	}

	@Test
	void creditMemo_docTypeConfigured_lineWithoutReason_blocks()
	{
		stubSysConfig(CONFIGURED_DOCTYPE_IDS);
		stubCreditMemo(true);
		stubLines(line(10, "CMF"), line(20, null)); // line 20 has no reason

		final I_C_Invoice invoice = creditMemoInvoice(DOCTYPE_IN_LIST);

		assertThatThrownBy(() -> interceptor.validateCreditMemoReason(invoice))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void creditMemo_docTypeConfigured_allLinesWithReason_passes()
	{
		stubSysConfig(CONFIGURED_DOCTYPE_IDS);
		stubCreditMemo(true);
		stubLines(line(10, "CMF"), line(20, "CMD"));

		final I_C_Invoice invoice = creditMemoInvoice(DOCTYPE_IN_LIST);

		assertThatCode(() -> interceptor.validateCreditMemoReason(invoice)).doesNotThrowAnyException();
	}

	@Test
	void sysConfigEmpty_validationDisabled_passes()
	{
		stubSysConfig(""); // feature off
		stubCreditMemo(true);
		stubLines(line(10, null)); // blank reason but feature disabled

		final I_C_Invoice invoice = creditMemoInvoice(DOCTYPE_IN_LIST);

		assertThatCode(() -> interceptor.validateCreditMemoReason(invoice)).doesNotThrowAnyException();
	}

	@Test
	void creditMemo_docTypeNotConfigured_passes()
	{
		stubSysConfig(CONFIGURED_DOCTYPE_IDS);
		stubCreditMemo(true);
		stubLines(line(10, null)); // blank reason but this doc type is not gated

		final I_C_Invoice invoice = creditMemoInvoice(DOCTYPE_NOT_IN_LIST);

		assertThatCode(() -> interceptor.validateCreditMemoReason(invoice)).doesNotThrowAnyException();
	}

	@Test
	void notACreditMemo_passes()
	{
		stubSysConfig(CONFIGURED_DOCTYPE_IDS);
		stubCreditMemo(false); // regular invoice
		// getLines is never reached; no stub needed

		final I_C_Invoice invoice = creditMemoInvoice(DOCTYPE_IN_LIST);

		assertThatCode(() -> interceptor.validateCreditMemoReason(invoice)).doesNotThrowAnyException();
	}

	/**
	 * Post-prepareIt state: the effective {@code C_DocType_ID} (not the target) is set. Covers the
	 * first branch of the dual doc-type check.
	 */
	@Test
	void creditMemo_effectiveDocTypeConfigured_lineWithoutReason_blocks()
	{
		stubSysConfig(CONFIGURED_DOCTYPE_IDS);
		stubCreditMemo(true);
		stubLines(line(10, null));

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_DocType_ID(DOCTYPE_IN_LIST); // effective doc type, target left unset
		InterfaceWrapperHelper.saveRecord(invoice);

		assertThatThrownBy(() -> interceptor.validateCreditMemoReason(invoice))
				.isInstanceOf(AdempiereException.class);
	}

	/**
	 * A credit memo with no lines has nothing to validate — completion must not be blocked.
	 */
	@Test
	void creditMemo_noLines_passes()
	{
		stubSysConfig(CONFIGURED_DOCTYPE_IDS);
		stubCreditMemo(true);
		stubLines(); // no lines

		final I_C_Invoice invoice = creditMemoInvoice(DOCTYPE_IN_LIST);

		assertThatCode(() -> interceptor.validateCreditMemoReason(invoice)).doesNotThrowAnyException();
	}

	/**
	 * A malformed SysConfig token must not abort completion: the bad token is skipped and the valid
	 * doc-type IDs still enforce the rule.
	 */
	@Test
	void malformedSysConfigToken_ignored_validIdsStillEnforce()
	{
		stubSysConfig("1000004, not-a-number");
		stubCreditMemo(true);
		stubLines(line(10, null));

		final I_C_Invoice invoice = creditMemoInvoice(DOCTYPE_IN_LIST);

		assertThatThrownBy(() -> interceptor.validateCreditMemoReason(invoice))
				.isInstanceOf(AdempiereException.class);
	}

	// -------------------------------------------------------------------------

	private void stubSysConfig(final String docTypeIdsCsv)
	{
		Mockito.when(sysConfigBL.getValue(C_Invoice.SYSCONFIG_CreditMemoReasonMandatory_DocTypeIDs, ""))
				.thenReturn(docTypeIdsCsv);
	}

	private void stubCreditMemo(final boolean isCreditMemo)
	{
		Mockito.when(invoiceBL.isCreditMemo(any(I_C_Invoice.class))).thenReturn(isCreditMemo);
	}

	private void stubLines(final de.metas.adempiere.model.I_C_InvoiceLine... lines)
	{
		Mockito.when(invoiceBL.getLines(any(InvoiceId.class))).thenReturn(ImmutableList.copyOf(lines));
	}

	private I_C_Invoice creditMemoInvoice(final int docTypeTargetId)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_DocTypeTarget_ID(docTypeTargetId);
		InterfaceWrapperHelper.saveRecord(invoice); // assigns a positive C_Invoice_ID for InvoiceId.ofRepoId
		return invoice;
	}

	private de.metas.adempiere.model.I_C_InvoiceLine line(final int lineNo, @Nullable final String reason)
	{
		final de.metas.adempiere.model.I_C_InvoiceLine invoiceLine =
				InterfaceWrapperHelper.newInstance(de.metas.adempiere.model.I_C_InvoiceLine.class);
		InterfaceWrapperHelper.setValue(invoiceLine, org.compiere.model.I_C_InvoiceLine.COLUMNNAME_Line, lineNo);
		if (reason != null)
		{
			InterfaceWrapperHelper.setValue(invoiceLine, org.compiere.model.I_C_InvoiceLine.COLUMNNAME_Line_CreditMemoReason, reason);
		}
		InterfaceWrapperHelper.saveRecord(invoiceLine);
		return invoiceLine;
	}
}
