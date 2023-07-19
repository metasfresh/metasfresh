package de.metas.acct.open_items.handlers;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.accounts.BPartnerCustomerAccountType;
import de.metas.acct.accounts.BPartnerVendorAccountType;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.acct.open_items.FAOpenItemsHandler;
import de.metas.acct.open_items.FAOpenItemsHandlerMatchingKey;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.PaymentAllocationLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@VisibleForTesting
public class BPartnerOIHandler implements FAOpenItemsHandler
{
	private static final @NonNull AccountConceptualName V_Liability = BPartnerVendorAccountType.V_Liability.getAccountConceptualName();
	private static final @NonNull AccountConceptualName V_Prepayment = BPartnerVendorAccountType.V_Prepayment.getAccountConceptualName();
	private static final @NonNull AccountConceptualName C_Receivable = BPartnerCustomerAccountType.C_Receivable.getAccountConceptualName();
	private static final @NonNull AccountConceptualName C_Prepayment = BPartnerCustomerAccountType.C_Prepayment.getAccountConceptualName();

	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);

	@Override
	public @NonNull Set<FAOpenItemsHandlerMatchingKey> getMatchers()
	{
		return ImmutableSet.of(
				FAOpenItemsHandlerMatchingKey.of(V_Liability, I_C_Invoice.Table_Name),
				FAOpenItemsHandlerMatchingKey.of(V_Liability, I_C_AllocationHdr.Table_Name),
				FAOpenItemsHandlerMatchingKey.of(V_Prepayment, I_C_AllocationHdr.Table_Name),
				FAOpenItemsHandlerMatchingKey.of(V_Prepayment, I_C_Payment.Table_Name),
				//
				FAOpenItemsHandlerMatchingKey.of(C_Receivable, I_C_Invoice.Table_Name),
				FAOpenItemsHandlerMatchingKey.of(C_Receivable, I_C_AllocationHdr.Table_Name),
				FAOpenItemsHandlerMatchingKey.of(C_Prepayment, I_C_AllocationHdr.Table_Name),
				FAOpenItemsHandlerMatchingKey.of(C_Prepayment, I_C_Payment.Table_Name)
		);
	}

	@Override
	public Optional<FAOpenItemTrxInfo> computeTrxInfo(final FAOpenItemTrxInfoComputeRequest request)
	{
		switch (request.getTableName())
		{
			case I_C_Invoice.Table_Name:
				return computeTrxInfoFromInvoice(request);
			case I_C_AllocationHdr.Table_Name:
				return computeTrxInfoFromAllocation(request);
			case I_C_Payment.Table_Name:
				return computeTrxInfoFromPayment(request);
			default:
				return Optional.empty();
		}
	}

	private Optional<FAOpenItemTrxInfo> computeTrxInfoFromInvoice(final FAOpenItemTrxInfoComputeRequest request)
	{
		final AccountConceptualName accountConceptualName = request.getAccountConceptualName();

		if (AccountConceptualName.equals(accountConceptualName, V_Liability)
				|| AccountConceptualName.equals(accountConceptualName, C_Receivable))
		{
			final InvoiceId invoiceId = InvoiceId.ofRepoId(request.getRecordId());
			return Optional.of(FAOpenItemTrxInfo.opening(FAOpenItemKey.invoice(invoiceId, accountConceptualName)));
		}
		else
		{
			return Optional.empty();
		}
	}

	private Optional<FAOpenItemTrxInfo> computeTrxInfoFromAllocation(final FAOpenItemTrxInfoComputeRequest request)
	{
		final PaymentAllocationLineId paymentAllocationLineId = PaymentAllocationLineId.ofRepoId(request.getRecordId(), request.getLineId());
		final AccountConceptualName accountConceptualName = request.getAccountConceptualName();

		if (accountConceptualName == null)
		{
			return Optional.empty();
		}
		else if (accountConceptualName.isAnyOf(V_Liability, C_Receivable))
		{
			// TODO handle the case when we have invoice-to-invoice allocation
			return allocationBL.getInvoiceId(paymentAllocationLineId)
					.map(invoiceId -> FAOpenItemTrxInfo.clearing(FAOpenItemKey.invoice(invoiceId, accountConceptualName)));
		}
		else if (accountConceptualName.isAnyOf(V_Prepayment, C_Prepayment))
		{
			// TODO handle the case when we have payment-to-payment allocation
			return allocationBL.getPaymentId(paymentAllocationLineId)
					.map(paymentId -> FAOpenItemTrxInfo.clearing(FAOpenItemKey.payment(paymentId, accountConceptualName)));
		}
		else
		{
			return Optional.empty();
		}
	}

	private Optional<FAOpenItemTrxInfo> computeTrxInfoFromPayment(final FAOpenItemTrxInfoComputeRequest request)
	{
		final PaymentId paymentId = PaymentId.ofRepoId(request.getRecordId());
		final AccountConceptualName accountConceptualName = request.getAccountConceptualName();

		if (accountConceptualName == null)
		{
			return Optional.empty();
		}
		else if (accountConceptualName.isAnyOf(V_Prepayment, C_Prepayment))
		{
			return Optional.of(FAOpenItemTrxInfo.opening(FAOpenItemKey.payment(paymentId, accountConceptualName)));
		}
		else
		{
			return Optional.empty();
		}
	}

	@Override
	public void onGLJournalLineCompleted(final SAPGLJournalLine line) {updateDocumentAllocatedAndPaidFlags(line);}

	@Override
	public void onGLJournalLineReactivated(final SAPGLJournalLine line) {updateDocumentAllocatedAndPaidFlags(line);}

	private void updateDocumentAllocatedAndPaidFlags(final SAPGLJournalLine line)
	{
		final FAOpenItemTrxInfo openItemTrxInfo = line.getOpenItemTrxInfo();
		if (openItemTrxInfo == null)
		{
			// shall not happen
			return;
		}

		final AccountConceptualName accountConceptualName = openItemTrxInfo.getAccountConceptualName();
		if (accountConceptualName == null)
		{
			return;
		}

		if (accountConceptualName.isAnyOf(V_Liability, C_Receivable))
		{
			openItemTrxInfo.getKey().getInvoiceId().ifPresent(invoiceBL::scheduleUpdateIsPaid);
		}
		else if (accountConceptualName.isAnyOf(V_Prepayment, C_Prepayment))
		{
			openItemTrxInfo.getKey().getPaymentId().ifPresent(paymentBL::scheduleUpdateIsAllocated);
		}
	}
}
