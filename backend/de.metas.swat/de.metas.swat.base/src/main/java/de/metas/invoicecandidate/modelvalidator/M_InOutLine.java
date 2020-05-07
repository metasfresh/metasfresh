package de.metas.invoicecandidate.modelvalidator;



/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;

@Interceptor(I_M_InOutLine.class)
@Component
public class M_InOutLine
{
	private final InvoiceCandidateRecordService invoiceCandidateRecordService;

	public M_InOutLine(@NonNull final InvoiceCandidateRecordService invoiceCandidateRecordService)
	{
		this.invoiceCandidateRecordService = invoiceCandidateRecordService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteC_InvoiceCandidate_InOutLines(final I_M_InOutLine inOutLine)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final List<I_C_InvoiceCandidate_InOutLine> iciols = invoiceCandDAO.retrieveICIOLAssociationsForInOutLineInclInactive(inOutLine);

		for (final I_C_InvoiceCandidate_InOutLine iciol : iciols)
		{
			final I_C_Invoice_Candidate ic = iciol.getC_Invoice_Candidate();

			InterfaceWrapperHelper.delete(iciol);
			invoiceCandDAO.invalidateCand(ic);
		}
	}

	/**
	 * If an M_InOutLine is deleted, then this method deletes the candidates which directly reference that line via <code>AD_Table_ID</code> and <code>Record_ID</code>.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteC_Invoice_Candidates(final I_M_InOutLine inOutLine)
	{
		Services.get(IInvoiceCandDAO.class).deleteAllReferencingInvoiceCandidates(inOutLine);
	}

	/**
	 * If the given <code>inoutline</code> does not reference an order, then the method does nothing. <br>
	 * Otherwise, it iterates that orderLine's <code>C_Invoice_Candidate</code>s and creates <code>C_InvoiceCandidate_InOutLine</code> records.
	 * <p>
	 * Also, it updates for each <code>C_Invoice_Candidate</code> of the given <code>inOutLine</code>'s order line:
	 * <ul>
	 * <li>QualityDiscountPercent
	 * <li>QualityDiscountPercent_Override to zero if <code>QualityDiscountPercent</code> was changed
	 * <li>IsInDispute to <code>true</code> if QualityDiscountPercent was changed to a value > 0
	 * </ul>
	 * Also invalidates all ICs that have the given IC's header aggregation key.
	 *
	 * @param inOutLine
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void createC_InvoiceCandidate_InOutLines(final I_M_InOutLine inOutLine)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		//
		// Get Order Line
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inOutLine.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			return; // nothing to do
		}

		//
		// Iterate all invoice candidates linked to our order line
		// * create link between invoice candidate and our inout line
		for (final I_C_Invoice_Candidate icRecord : invoiceCandDAO.retrieveInvoiceCandidatesForOrderLineId(orderLineId))
		{
			final I_C_InvoiceCandidate_InOutLine iciol = InterfaceWrapperHelper.newInstance(I_C_InvoiceCandidate_InOutLine.class, inOutLine);
			iciol.setC_Invoice_Candidate(icRecord);
			invoiceCandBL.updateICIOLAssociationFromIOL(iciol, inOutLine);

			// TODO: QtyInvoiced shall be set! It's not so critical, atm is used on on Sales side (check call hierarchy of getQtyInvoiced())
			// NOTE: when we will set it, because there can be more then one IC for one inoutLine we need to calculate this Qtys proportionally.

			//
			// (also) calculate qualityDiscountPercent taken from inoutLines (06502)
			Services.get(IInvoiceCandidateHandlerBL.class).setDeliveredData(icRecord);
			final InvoiceCandidate invoiceCandidate = invoiceCandidateRecordService.ofRecord(icRecord);
			invoiceCandidateRecordService.updateRecord(invoiceCandidate, icRecord);

			InterfaceWrapperHelper.saveRecord(icRecord);
		}

		// invalidate the candidates related to the inOutLine's order line..i'm not 100% if it's necessary, but we might need to e.g. update the
		// QtyDelivered or QtyPicked or whatever...
		// final I_C_OrderLine orderLine = Services.get(IOrderDAO.class).getOrderLineById(orderLineId);
		// Services.get(IInvoiceCandidateHandlerBL.class).invalidateCandidatesFor(orderLine);
	}
}
