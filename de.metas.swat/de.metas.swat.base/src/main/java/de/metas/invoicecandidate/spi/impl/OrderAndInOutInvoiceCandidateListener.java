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

public class OrderAndInOutInvoiceCandidateListener extends InvoiceCandidateListenerAdapter
{
	public static final OrderAndInOutInvoiceCandidateListener instance = new OrderAndInOutInvoiceCandidateListener();

	private OrderAndInOutInvoiceCandidateListener()
	{
		super();
	}

	@Override
	public void onBeforeClosed(I_C_Invoice_Candidate candidate)
	{
		final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

		// Sales invoice candidates
		if (candidate.isSOTrx())
		{

			final Set<I_M_ShipmentSchedule> shipmentSchedules = Services.get(IShipmentSchedulePA.class).retrieveForInvoiceCandidate(candidate);

			for (final I_M_ShipmentSchedule shipmentSchedule : shipmentSchedules)
			{
				Services.get(IShipmentScheduleBL.class).closeShipmentSchedule(shipmentSchedule);
			}
		}

		// Purchase invoice candidates
		else
		{
			// close all the linked receipt schedules (the ones the candidate was based on)

			final Set<I_M_ReceiptSchedule> receiptSchedules = Services.get(IReceiptScheduleDAO.class).retrieveForInvoiceCandidate(candidate);
			for (final I_M_ReceiptSchedule receiptSchedule : receiptSchedules)
			{
				// do not try to close already closed receipt schedules
				if (receiptScheduleBL.isClosed(receiptSchedule))
				{
					continue;
				}

				receiptScheduleBL.close(receiptSchedule);
			}
		}

	}

}
