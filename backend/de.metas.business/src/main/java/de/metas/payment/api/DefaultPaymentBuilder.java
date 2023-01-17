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

package de.metas.payment.api;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentDirection;
import de.metas.payment.TenderType;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DefaultPaymentBuilder
{
	public static DefaultPaymentBuilder newInboundReceiptBuilder()
	{
		return new DefaultPaymentBuilder()
				.direction(PaymentDirection.INBOUND);
	}

	public static DefaultPaymentBuilder newOutboundPaymentBuilder()
	{
		return new DefaultPaymentBuilder()
				.direction(PaymentDirection.OUTBOUND);
	}

	public static DefaultPaymentBuilder newBuilderOfInvoice(@NonNull final I_C_Invoice invoice)
	{
		return new DefaultPaymentBuilder()
				.invoice(invoice);
	}

	private final transient IDocTypeDAO docTypesRepo = Services.get(IDocTypeDAO.class);

	private boolean _built = false;
	private final I_C_Payment payment;

	private DefaultPaymentBuilder()
	{
		payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		payment.setProcessed(false);
		payment.setDocStatus(DocStatus.Drafted.getCode());
		payment.setDocAction(IDocument.ACTION_Complete);
	}

	/**
	 * Creates and completes the payment
	 *
	 * @return payment
	 */
	public I_C_Payment createAndProcess()
	{
		return createAndProcess(IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	/**
	 * Creates and processes the payment.
	 *
	 * @return payment
	 */
	@SuppressWarnings("SameParameterValue")
	private I_C_Payment createAndProcess(final String docAction, final String expectedDocStatus)
	{
		final I_C_Payment payment = createDraft();

		payment.setDocAction(docAction);
		Services.get(IDocumentBL.class).processEx(payment, docAction, expectedDocStatus);

		return payment;
	}

	/**
	 * Creates the draft payment.
	 *
	 * @return draft payment
	 */
	public I_C_Payment createDraft()
	{
		final I_C_Payment payment = createNoSave();
		InterfaceWrapperHelper.save(payment);
		return payment;
	}

	/**
	 * Creates the payment but it does not save it.
	 *
	 * @return payment (not saved!)
	 */
	public I_C_Payment createNoSave()
	{
		markAsBuilt();

		// note: the only reason why we are calling the "...OrNull" method is because some unit tests are failing.
		final DocTypeId docTypeId = getDocTypeIdOrNull();
		payment.setC_DocType_ID(DocTypeId.toRepoId(docTypeId));

		return payment;
	}

	private DocTypeId getDocTypeIdOrNull()
	{
		final DocBaseType docBaseType = payment.isReceipt() ? DocBaseType.ARReceipt : DocBaseType.APPayment;

		return docTypesRepo.getDocTypeIdOrNull(DocTypeQuery.builder()
				.docBaseType(docBaseType)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(payment.getAD_Client_ID())
				.adOrgId(payment.getAD_Org_ID())
				.build());
	}

	private void assertNotBuilt()
	{
		Check.assume(!_built, "payment already built");
	}

	private void markAsBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	public final DefaultPaymentBuilder adOrgId(@NonNull final OrgId adOrgId)
	{
		assertNotBuilt();
		payment.setAD_Org_ID(adOrgId.getRepoId());
		return this;
	}

	private DefaultPaymentBuilder direction(@NonNull final PaymentDirection direction)
	{
		assertNotBuilt();
		payment.setIsReceipt(direction.isReceipt());
		return this;
	}

	public final DefaultPaymentBuilder orgBankAccountId(@Nullable final BankAccountId orgBankAccountId)
	{
		assertNotBuilt();
		if (orgBankAccountId == null)
		{
			payment.setC_BP_BankAccount_ID(0);
		}
		else
		{
			payment.setC_BP_BankAccount_ID(orgBankAccountId.getRepoId());
		}
		return this;
	}

	public final DefaultPaymentBuilder accountNo(final String accountNo)
	{
		assertNotBuilt();
		payment.setAccountNo(accountNo);
		return this;
	}

	/**
	 * DateTrx should be the same as Line.DateAcct, and not Line.StatementDate, in case of a BankStatementLine.
	 */
	public final DefaultPaymentBuilder dateAcct(@Nullable final LocalDate dateAcct)
	{
		assertNotBuilt();
		payment.setDateAcct(TimeUtil.asTimestamp(dateAcct));
		return this;
	}

	/**
	 * DateTrx should be the same as Line.DateAcct, and not Line.StatementDate, in case of a BankStatementLine.
	 */
	public final DefaultPaymentBuilder dateTrx(@Nullable final LocalDate dateTrx)
	{
		assertNotBuilt();
		payment.setDateTrx(TimeUtil.asTimestamp(dateTrx));
		return this;
	}

	public final DefaultPaymentBuilder bpartnerId(@NonNull final BPartnerId bpartnerId)
	{
		assertNotBuilt();
		payment.setC_BPartner_ID(bpartnerId.getRepoId());
		return this;
	}

	public final DefaultPaymentBuilder payAmt(@Nullable final BigDecimal payAmt)
	{
		assertNotBuilt();
		payment.setPayAmt(payAmt);
		return this;
	}

	public final DefaultPaymentBuilder discountAmt(@Nullable final BigDecimal discountAmt)
	{
		assertNotBuilt();
		payment.setDiscountAmt(discountAmt);
		return this;
	}

	public final DefaultPaymentBuilder writeoffAmt(@Nullable final BigDecimal writeoffAmt)
	{
		assertNotBuilt();
		payment.setWriteOffAmt(writeoffAmt);
		return this;
	}

	public final DefaultPaymentBuilder currencyId(@NonNull final CurrencyId currencyId)
	{
		assertNotBuilt();
		payment.setC_Currency_ID(currencyId.getRepoId());
		return this;
	}

	public final DefaultPaymentBuilder paymentCurrencyContext(@NonNull final PaymentCurrencyContext paymentCurrencyContext)
	{
		assertNotBuilt();
		payment.setC_ConversionType_ID(CurrencyConversionTypeId.toRepoId(paymentCurrencyContext.getCurrencyConversionTypeId()));
		if(paymentCurrencyContext.isFixedConversionRate())
		{
			Check.assumeEquals(payment.getC_Currency_ID(), paymentCurrencyContext.getPaymentCurrencyId().getRepoId(), "{} shall match payment currency", paymentCurrencyContext);
			payment.setCurrencyRate(paymentCurrencyContext.getCurrencyRate());
			payment.setSource_Currency_ID(paymentCurrencyContext.getSourceCurrencyId().getRepoId());
		}
		return this;
	}

	public final DefaultPaymentBuilder tenderType(@NonNull final TenderType tenderType)
	{
		assertNotBuilt();
		payment.setTenderType(tenderType.getCode());
		return this;
	}

	/**
	 * Sets the following fields using the given <code>invoice</code>:
	 * <ul>
	 * <li>C_Invoice_ID
	 * <li>C_BPartner_ID
	 * <li>C_Currency_ID
	 * <li>IsReceipt: set from the invoice's <code>SOTrx</code> (negated if the invoice is a credit memo)
	 * </ul>
	 */
	private DefaultPaymentBuilder invoice(@NonNull final I_C_Invoice invoice)
	{
		adOrgId(OrgId.ofRepoId(invoice.getAD_Org_ID()));
		invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
		bpartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()));
		currencyId(CurrencyId.ofRepoId(invoice.getC_Currency_ID()));

		final SOTrx soTrx = SOTrx.ofBoolean(invoice.isSOTrx());
		final boolean creditMemo = Services.get(IInvoiceBL.class).isCreditMemo(invoice);
		direction(PaymentDirection.ofSOTrxAndCreditMemo(soTrx, creditMemo));

		return this;
	}

	public final DefaultPaymentBuilder invoiceId(@NonNull final InvoiceId invoiceId)
	{
		assertNotBuilt();
		payment.setC_Invoice_ID(invoiceId.getRepoId());
		return this;
	}

	public final DefaultPaymentBuilder description(final String description)
	{
		assertNotBuilt();
		payment.setDescription(description);
		return this;
	}

	public final DefaultPaymentBuilder externalId(@Nullable final ExternalId externalId)
	{
		assertNotBuilt();
		if (externalId != null)
		{
			payment.setExternalId(externalId.getValue());
		}
		return this;
	}

	public final DefaultPaymentBuilder orderId(@Nullable final OrderId orderId)
	{
		assertNotBuilt();
		if (orderId != null)
		{
			payment.setC_Order_ID(orderId.getRepoId());
		}
		return this;
	}

	public final DefaultPaymentBuilder orderExternalId(@Nullable final String orderExternalId)
	{
		assertNotBuilt();
		if (Check.isNotBlank(orderExternalId))
		{
			payment.setExternalOrderId(orderExternalId);
		}
		return this;
	}

	public final DefaultPaymentBuilder isAutoAllocateAvailableAmt(final boolean isAutoAllocateAvailableAmt)
	{
		assertNotBuilt();
		payment.setIsAutoAllocateAvailableAmt(isAutoAllocateAvailableAmt);
		return this;
	}
}
