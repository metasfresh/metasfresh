package de.metas.invoicecandidate.spi.impl;

import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateListener;
import de.metas.util.Services;
import lombok.NonNull;

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
		final IReceiptScheduleDAO receiptSchedulesRepo = Services.get(IReceiptScheduleDAO.class);
		final IReceiptScheduleBL receiptSchedulesService = Services.get(IReceiptScheduleBL.class);

		receiptSchedulesRepo.retrieveForInvoiceCandidate(candidate)
				.stream()
				.filter(receiptSchedule -> !receiptSchedulesService.isClosed(receiptSchedule))
				.forEach(receiptSchedulesService::close);
	}
}
