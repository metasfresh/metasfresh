package de.metas.allocation.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.C_AllocationLine_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Check;
import de.metas.util.Services;

public class AllocationBL implements IAllocationBL
{
	@Override
	public C_AllocationHdr_Builder newBuilder()
	{
		return new C_AllocationHdr_Builder();
	}

	@Override
	public I_C_AllocationHdr autoAllocateAvailablePayments(final I_C_Invoice invoice)
	{
		if (invoice.isPaid())
		{
			return null;
		}
		if (!invoice.isSOTrx())
		{
			return null;
		}
		if (Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			return null;
		}

		final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
		final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final List<I_C_Payment> availablePayments = allocationDAO.retrieveAvailablePayments(invoice);

		if (availablePayments.isEmpty())
		{
			return null; // nothing to do
		}

		final BigDecimal invoiceOpenAmt = invoiceDAO.retrieveOpenAmt(invoice);

		Timestamp dateAcct = invoice.getDateAcct();
		Timestamp dateTrx = invoice.getDateInvoiced();
		C_AllocationHdr_Builder allocBuilder = newBuilder()
				.currencyId(invoice.getC_Currency_ID());

		BigDecimal sumAmt = BigDecimal.ZERO;

		for (final I_C_Payment payment : availablePayments)
		{
			final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
			final BigDecimal currentAmt = paymentDAO.getAvailableAmount(paymentId);

			sumAmt = sumAmt.add(currentAmt);

			dateAcct = TimeUtil.max(dateAcct, payment.getDateAcct());
			dateTrx = TimeUtil.max(dateTrx, payment.getDateTrx());

			Check.assume(invoice.getC_BPartner_ID() == payment.getC_BPartner_ID(), "{} and {} have the same C_BPartner_ID", invoice, payment);
			final C_AllocationLine_Builder lineBuilder = allocBuilder.addLine()
					.orgId(invoice.getAD_Org_ID())
					.bpartnerId(invoice.getC_BPartner_ID())
					.invoiceId(invoice.getC_Invoice_ID())
					.paymentId(payment.getC_Payment_ID());

			if (sumAmt.compareTo(invoiceOpenAmt) < 0)
			{
				allocBuilder = lineBuilder
						.amount(currentAmt)
						.lineDone();
			}
			else
			{
				// make sure the allocated amt is not bigger than the open amt of the invoice
				allocBuilder = lineBuilder
						.amount(invoiceOpenAmt.subtract(sumAmt.subtract(currentAmt)))
						.lineDone();
				break;
			}
		}

		// Set allocation dates and create it
		return allocBuilder
				.dateAcct(dateAcct)
				.dateTrx(dateTrx)
				.createAndComplete();
	}

	@Override
	public I_C_AllocationHdr autoAllocateSpecificPayment(
			org.compiere.model.I_C_Invoice invoice,
			org.compiere.model.I_C_Payment payment,
			boolean ignoreIsAutoAllocateAvailableAmt)
	{
		if (invoice.isPaid())
		{
			return null;
		}
		if (!invoice.isSOTrx())
		{
			return null;
		}
		if (Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			return null;
		}

		// payment and invoice must have same partner
		if (payment.getC_BPartner_ID() != invoice.getC_BPartner_ID())
		{
			return null;
		}

		// payment must be completed
		final DocStatus docStatus = DocStatus.ofCode(payment.getDocStatus());
		if (!docStatus.isCompleted())
		{
			return null;
		}

		// payment must be processed
		if (!payment.isProcessed())
		{
			return null;
		}

		// // Matching DocType
		if (payment.isReceipt() != invoice.isSOTrx())
		{
			return null;
		}

		if (!ignoreIsAutoAllocateAvailableAmt)
		{
			// payment must be autoallocatedAavilableAmt
			if (!payment.isAutoAllocateAvailableAmt())
			{
				return null;
			}
		}

		// payment must not be oallocated
		if (payment.isAllocated())
		{
			return null;
		}

		final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final BigDecimal invoiceOpenAmt = invoiceDAO.retrieveOpenAmt(invoice);
		final Timestamp dateAcct = TimeUtil.max(invoice.getDateAcct(), payment.getDateAcct());
		final Timestamp dateTrx = TimeUtil.max(invoice.getDateInvoiced(), payment.getDateTrx());

		C_AllocationHdr_Builder allocBuilder = newBuilder()
				.currencyId(invoice.getC_Currency_ID())
				.dateAcct(dateAcct)
				.dateTrx(dateTrx);

		BigDecimal sumAmt = BigDecimal.ZERO;

		{
			final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
			final BigDecimal currentAmt = paymentDAO.getAvailableAmount(paymentId);

			sumAmt = sumAmt.add(currentAmt);

			Check.assume(invoice.getC_BPartner_ID() == payment.getC_BPartner_ID(), "{} and {} have the same C_BPartner_ID", invoice, payment);

			final C_AllocationLine_Builder lineBuilder = allocBuilder.addLine()
					.orgId(invoice.getAD_Org_ID())
					.bpartnerId(invoice.getC_BPartner_ID())
					.invoiceId(invoice.getC_Invoice_ID())
					.paymentId(payment.getC_Payment_ID());

			if (sumAmt.compareTo(invoiceOpenAmt) < 0)
			{
				allocBuilder = lineBuilder
						.amount(currentAmt)
						.lineDone();
			}
			else
			{
				// make sure the allocated amt is not bigger than the open amt of the invoice
				allocBuilder = lineBuilder
						.amount(invoiceOpenAmt.subtract(sumAmt.subtract(currentAmt)))
						.lineDone();
			}
		}
		return allocBuilder.create(true);
	}

	@Override
	public boolean isReversal(final I_C_AllocationHdr allocationHdr)
	{
		if (allocationHdr == null)
		{
			return false;
		}
		if (allocationHdr.getReversal_ID() <= 0)
		{
			return false;
		}
		// the reversal is always younger than the original document
		return allocationHdr.getC_AllocationHdr_ID() > allocationHdr.getReversal_ID();
	}
}
