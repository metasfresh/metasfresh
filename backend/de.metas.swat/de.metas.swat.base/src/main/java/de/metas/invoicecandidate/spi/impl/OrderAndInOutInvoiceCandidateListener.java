package de.metas.invoicecandidate.spi.impl;

import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateListener;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;

import java.util.List;

public final class OrderAndInOutInvoiceCandidateListener implements IInvoiceCandidateListener
{
	public static final OrderAndInOutInvoiceCandidateListener instance = new OrderAndInOutInvoiceCandidateListener();

	private OrderAndInOutInvoiceCandidateListener()
	{
	}

	@Override
	public void onBeforeClosed(@NonNull final I_C_Invoice_Candidate candidate)
	{
		if (candidate.isSOTrx())
		{
			closeShipmentSchedules(candidate);
		}
		else
		{
			closeReceiptCandidates(candidate);
		}
	}

	private void closeShipmentSchedules(final I_C_Invoice_Candidate candidate)
	{
		final IShipmentSchedulePA shipmentSchedulesRepo = Services.get(IShipmentSchedulePA.class);
		final IShipmentScheduleBL shipmentSchedulesService = Services.get(IShipmentScheduleBL.class);

		shipmentSchedulesRepo.retrieveForInvoiceCandidate(candidate)
				.stream()
				.filter(shipmentSchedule -> !shipmentSchedule.isClosed())
				.forEach(shipmentSchedulesService::closeShipmentSchedule);
	}

	private void closeReceiptCandidates(final I_C_Invoice_Candidate candidate)
	{
		// me03 #29369: skip rs close when the IC is being closed because an iter-3 Partial (cap-marked)
		// invoice completed. The IC close itself is correct (no more auto-invoicing on this IC), but the
		// receipt schedule must STAY OPEN so future receipts on the same order can still be processed.
		// A narrower gate than the previously reverted commit 26907c9a61d at
		// InvoiceCandBL.closePartiallyInvoiced_InvoiceCandidates (which broke profile2's
		// invoiceCandidateQtyToInvoiceOverride scenarios because IsPartialInvoice defaults to Y for
		// many normal invoices via doctype default).
		if (isClosingDueToPartialInvoice(candidate))
		{
			return;
		}

		final IReceiptScheduleDAO receiptSchedulesRepo = Services.get(IReceiptScheduleDAO.class);
		final IReceiptScheduleBL receiptSchedulesService = Services.get(IReceiptScheduleBL.class);

		receiptSchedulesRepo.retrieveForInvoiceCandidate(candidate)
				.stream()
				.filter(receiptSchedule -> !receiptSchedulesService.isClosed(receiptSchedule))
				.forEach(receiptSchedulesService::close);
	}

	private boolean isClosingDueToPartialInvoice(@NonNull final I_C_Invoice_Candidate candidate)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final List<I_C_InvoiceLine> invoiceLines = invoiceCandDAO.retrieveIlForIc(candidate);
		if (invoiceLines.isEmpty())
		{
			return false;
		}
		// retrieveIlForIc orders by C_InvoiceLine_ID ASC, so the last entry is the most recent.
		final I_C_InvoiceLine mostRecentInvoiceLine = invoiceLines.get(invoiceLines.size() - 1);
		final I_C_Invoice mostRecentInvoice = mostRecentInvoiceLine.getC_Invoice();
		return mostRecentInvoice != null && mostRecentInvoice.isPartialInvoice();
	}
}
