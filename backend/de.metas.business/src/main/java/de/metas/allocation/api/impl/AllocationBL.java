package de.metas.allocation.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.C_AllocationLine_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.BankAccountId;
import de.metas.banking.invoice_auto_allocation.BankAccountInvoiceAutoAllocRules;
import de.metas.banking.invoice_auto_allocation.BankAccountInvoiceAutoAllocRulesRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lang.SOTrx;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllocationBL implements IAllocationBL
{
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

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
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final List<I_C_Payment> availablePayments = getAvailablePaymentsToAutoAllocate(invoice);
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

	private List<I_C_Payment> getAvailablePaymentsToAutoAllocate(@NonNull final I_C_Invoice invoice)
	{
		final BPartnerId invoiceBPartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final SOTrx invoiceSOTrx = SOTrx.ofBoolean(invoice.isSOTrx());
		final ClientAndOrgId invoiceClientAndOrgId = ClientAndOrgId.ofClientAndOrg(invoice.getAD_Client_ID(), invoice.getAD_Org_ID());

		//
		// First all payments which have IsAutoAllocateAvailableAmt set, not already allocated and for invoice partner.
		final ArrayList<I_C_Payment> eligiblePayments = new ArrayList<>(allocationDAO.retrieveAvailablePaymentsToAutoAllocate(
				invoiceBPartnerId,
				invoiceSOTrx,
				invoiceClientAndOrgId));
		if (eligiblePayments.isEmpty())
		{
			return ImmutableList.of();
		}

		applyBankAccountInvoiceAutoAllocRules(
				eligiblePayments,
				DocTypeId.ofRepoId(invoice.getC_DocType_ID()),
				SpringContextHolder.instance.getBean(BankAccountInvoiceAutoAllocRulesRepository.class));

		return eligiblePayments;
	}

	/**
	 * Iterate eligible payments and eliminate those which does not complain to BankAccount Invoice Auto Allocation rules
	 */
	@VisibleForTesting
	static void applyBankAccountInvoiceAutoAllocRules(
			@NonNull final ArrayList<I_C_Payment> eligiblePayments,
			@NonNull final DocTypeId invoiceDocTypeId,
			@NonNull final BankAccountInvoiceAutoAllocRulesRepository bankAccountInvoiceAutoAllocRulesRepository)
	{
		if (eligiblePayments.isEmpty())
		{
			return;
		}

		final BankAccountInvoiceAutoAllocRules rules = bankAccountInvoiceAutoAllocRulesRepository.getRules();
		eligiblePayments.removeIf(payment -> {
			final BankAccountId bankAccountId = BankAccountId.ofRepoId(payment.getC_BP_BankAccount_ID());
			return !rules.isAutoAllocate(bankAccountId, invoiceDocTypeId);
		});
	}

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

		{
			final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
			final BigDecimal currentAmt = paymentDAO.getAvailableAmount(paymentId);

			Check.assume(invoice.getC_BPartner_ID() == payment.getC_BPartner_ID(), "{} and {} have the same C_BPartner_ID", invoice, payment);

			final C_AllocationLine_Builder lineBuilder = allocBuilder.addLine()
					.orgId(invoice.getAD_Org_ID())
					.bpartnerId(invoice.getC_BPartner_ID())
					.invoiceId(invoice.getC_Invoice_ID())
					.paymentId(payment.getC_Payment_ID());

			final BigDecimal amountToAllocate = currentAmt.min(invoiceOpenAmt);

			if (payment.getC_Invoice_ID() == invoice.getC_Invoice_ID())
			{
				allocBuilder = lineBuilder
						.amount(amountToAllocate)
						.discountAmt(payment.getDiscountAmt())
						.lineDone();
			}
			else
			{
				allocBuilder = lineBuilder
						.amount(amountToAllocate)
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
