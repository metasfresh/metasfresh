package de.metas.invoicecandidate.spi.impl;

import java.util.Set;

import org.adempiere.util.Services;

import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.InvoiceCandidateListenerAdapter;
import lombok.NonNull;

public class OrderAndInOutInvoiceCandidateListener extends InvoiceCandidateListenerAdapter
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
			final Set<I_M_ShipmentSchedule> shipmentSchedules = Services.get(IShipmentSchedulePA.class).retrieveForInvoiceCandidate(candidate);
			final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

			shipmentSchedules.forEach(shipmentSchedule -> shipmentScheduleBL.closeShipmentSchedule(shipmentSchedule));
		}
		else
		{
			final Set<I_M_ReceiptSchedule> receiptSchedules = Services.get(IReceiptScheduleDAO.class).retrieveForInvoiceCandidate(candidate);
			final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

			receiptSchedules.stream()
					.filter(receiptSchedule -> !receiptScheduleBL.isClosed(receiptSchedule))
					.forEach(receiptSchedule -> receiptScheduleBL.close(receiptSchedule));
		}
	}
}
