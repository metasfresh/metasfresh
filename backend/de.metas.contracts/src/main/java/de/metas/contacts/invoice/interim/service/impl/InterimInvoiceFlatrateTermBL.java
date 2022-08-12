/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.invoice.interim.service.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.contacts.invoice.interim.InterimInvoiceFlatrateTerm;
import de.metas.contacts.invoice.interim.command.InterimInvoiceFlatrateTermCreateCommand;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceFlatrateTermBL;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceFlatrateTermDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOutLine;

import java.math.BigDecimal;

public class InterimInvoiceFlatrateTermBL implements IInterimInvoiceFlatrateTermBL
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IInterimInvoiceFlatrateTermDAO interimInvoiceOverviewDAO = Services.get(IInterimInvoiceFlatrateTermDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	@Override
	public void updateInterimInvoiceFlatrateTermForInOutLine(@NonNull final I_M_InOutLine inOutLine)
	{

		InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm = interimInvoiceOverviewDAO.getInterimInvoiceOverviewForInOutLine(inOutLine);
		if (interimInvoiceFlatrateTerm == null)
		{
			//inOutLine is not part of a contract
			return;
		}
		if (!interimInvoiceOverviewDAO.isInterimInvoiceStillUsable(interimInvoiceFlatrateTerm))
		{
			interimInvoiceFlatrateTerm = createInterimInvoiceFlatrateTerm(interimInvoiceFlatrateTerm, inOutLine);
		}

		updateInterimInvoiceOverview(interimInvoiceFlatrateTerm, inOutLine);
	}


	@Override
	public void updateInterimInvoiceFlatrateTermForInOutLine(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm, @NonNull final InOutLineId inOutLineId)
	{
		final I_M_InOutLine line = inOutDAO.getLineById(inOutLineId);
		if (line != null)
		{
			updateInterimInvoiceOverview(interimInvoiceFlatrateTerm, line);
		}
	}

	/**
	 * Update the interim and withholding ICs of an {@link InterimInvoiceFlatrateTerm} for the given {@link I_M_InOutLine}.
	 */
	private void updateInterimInvoiceOverview(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm, @NonNull final I_M_InOutLine inOutLine)
	{
		final Quantity deliveredQty = Quantitys.create(inOutLine.getMovementQty(), UomId.ofRepoId(inOutLine.getC_UOM_ID()));

		final I_C_Invoice_Candidate interimInvoiceCandidate = invoiceCandDAO.getById(interimInvoiceFlatrateTerm.getInterimInvoiceCandidateId());
		final Quantity interimQty = Quantitys.create(interimInvoiceCandidate.getQtyDelivered(), UomId.ofRepoId(interimInvoiceCandidate.getC_UOM_ID()));
		final Quantity interimQtyToInvoice = interimQty.add(deliveredQty);

		setICQtyies(interimInvoiceCandidate, interimQtyToInvoice);

		final I_C_Invoice_Candidate withholdingInvoiceCandidate = invoiceCandDAO.getById(interimInvoiceFlatrateTerm.getWithholdingInvoiceCandidateId());
		final Quantity withholdingQty = Quantitys.create(withholdingInvoiceCandidate.getQtyDelivered(), UomId.ofRepoId(withholdingInvoiceCandidate.getC_UOM_ID()));
		final Quantity withheldQtyToInvoice = withholdingQty.subtract(deliveredQty);

		setICQtyies(withholdingInvoiceCandidate, withheldQtyToInvoice);

		final Quantity totalQtyDeliveredSoFar = Quantitys.create(interimInvoiceFlatrateTerm.getQtyDelivered(), interimInvoiceFlatrateTerm.getUomId()).add(deliveredQty);

		interimInvoiceOverviewDAO.save(interimInvoiceFlatrateTerm.toBuilder()
				.qtyDelivered(totalQtyDeliveredSoFar.toBigDecimal())
				.build());
	}

	private void setICQtyies(final I_C_Invoice_Candidate invoiceCandidate, final Quantity qtyToInvoice)
	{
		final BigDecimal qtyDeliveredInICUOM = qtyToInvoice.toBigDecimal();
		invoiceCandidate.setQtyOrdered(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyEntered(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyDelivered(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyDeliveredInUOM(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyToInvoice(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyToInvoiceInUOM(qtyDeliveredInICUOM);
		invoiceCandidate.setQtyToInvoiceBeforeDiscount(qtyDeliveredInICUOM);
		invoiceCandDAO.save(invoiceCandidate);
	}

	private InterimInvoiceFlatrateTerm createInterimInvoiceFlatrateTerm(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm, final @NonNull I_M_InOutLine inOutLine)
	{
		final I_C_Flatrate_Term flatrateTerm = flatrateDAO.retrieveTerm(interimInvoiceFlatrateTerm.getFlatrateTermId());

		return InterimInvoiceFlatrateTermCreateCommand.builder()
				.ctx(InterfaceWrapperHelper.getContextAware(inOutLine).getCtx())
				.productId(ProductId.ofRepoId(inOutLine.getM_Product_ID()))
				.orderLineId(OrderLineId.ofRepoId(inOutLine.getC_OrderLine_ID()))
				.conditionsId(ConditionsId.ofRepoId(flatrateTerm.getC_Flatrate_Conditions_ID()))
				.bpartnerId(BPartnerId.ofRepoId(flatrateTerm.getBill_BPartner_ID()))
				.dateFrom(flatrateTerm.getStartDate())
				.dateTo(flatrateTerm.getEndDate())
				.build()
				.execute();
	}

}
