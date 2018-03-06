package de.metas.invoicecandidate.spi;

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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;

import com.google.common.collect.ImmutableList;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.product.IProductBL;
import lombok.NonNull;

/**
 * Simple abstract base class that implements {@link #setHandlerRecord(I_C_ILCandHandler)} and {@link #setNetAmtToInvoice(I_C_Invoice_Candidate)}.
 *
 * @author ts
 *
 */
public abstract class AbstractInvoiceCandidateHandler implements IInvoiceCandidateHandler
{
	private I_C_ILCandHandler record;

	@Override
	public final I_C_ILCandHandler getHandlerRecord()
	{
		return record;
	}

	@Override
	public final void setHandlerRecord(final I_C_ILCandHandler record)
	{
		this.record = record;
	}

	@Override
	public List<InvoiceCandidateGenerateRequest> expandRequest(final InvoiceCandidateGenerateRequest request)
	{
		return ImmutableList.of(request);
	}

	@Override
	public Object getModelForInvoiceCandidateGenerateScheduling(final Object model)
	{
		return model;
	}

	@Override
	public void setNetAmtToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		// task 08507: ic.getQtyToInvoice() is already the "effective". Qty even if QtyToInvoice_Override is set, the system will decide what to invoice (e.g. based on RnvoiceRule and QtDdelivered)
		// and update QtyToInvoice accordingly, possibly to a value that is different from QtyToInvoice_Override. Therefore we don't use invoiceCandBL.getQtyToInvoice(ic), but the getter directly
		final BigDecimal qtyToInvoice = ic.getQtyToInvoice();
		final BigDecimal netAmtToInvoice = computeNetAmtUsingQty(ic, qtyToInvoice);

		ic.setNetAmtToInvoice(netAmtToInvoice);
		ic.setSplitAmt(BigDecimal.ZERO);
	}

	@Override
	public void setLineNetAmt(@NonNull final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final BigDecimal openQty = invoiceCandBL.computeOpenQty(ic);
		final BigDecimal netAmtToInvoice = computeNetAmtUsingQty(ic, openQty);

		ic.setLineNetAmt(netAmtToInvoice);
	}

	private BigDecimal computeNetAmtUsingQty(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final BigDecimal qty)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		final int precision = invoiceCandBL.getPrecisionFromCurrency(ic);

		final BigDecimal priceActual = invoiceCandBL.getPriceActual(ic);
		final BigDecimal qtyToInvoice = invoiceCandBL.convertToPriceUOM(
				qty,
				ic);

		return qtyToInvoice.multiply(priceActual).setScale(precision, RoundingMode.HALF_UP);
	}

	/**
	 * Returns the AD_User_InCharge_ID set in this handler's {@link I_C_Invoice_Candidate} record.
	 */
	@Override
	public int getAD_User_InCharge_ID(final I_C_Invoice_Candidate ic)
	{
		return getHandlerRecord().getAD_User_InCharge_ID();
	}

	/**
	 * Default implementation. Might be overridden.
	 *
	 * @returns {@link OnInvalidateForModelAction#REVALIDATE}.
	 */
	@Override
	public OnInvalidateForModelAction getOnInvalidateForModelAction()
	{
		return OnInvalidateForModelAction.REVALIDATE;
	}

	/**
	 * Checks if the underlying product is a service which will never ever be received.
	 *
	 * @param ic
	 * @return true if we deal with a service which will never ever be received
	 * @task http://dewiki908/mediawiki/index.php/08408_Transporte_auf_Rechnungsstellung_sofort_setzen_in_Rechnungsdispo_%28107611160033%29
	 */
	protected final boolean isNotReceivebleService(final I_C_Invoice_Candidate ic)
	{
		final I_M_Product product = ic.getM_Product();

		// If no product, consider it as a non receivable service (maybe it's a charge?!)
		if (product == null)
		{
			return true;
		}

		// If the product is not a service
		// => return false
		if (!Services.get(IProductBL.class).isService(product))
		{
			return false;
		}

		// NOTE: at this point i think we shall extend the business logic and check when a service will be received and when not
		// but for now we are considering that it will never ever be received
		return true;
	}

	/**
	 * Sets delivery data from first shipment/receipt that was created for this invoice candidate.
	 *
	 * @param ic invoice candidate
	 * @param firstInOut first shipment/receipt or <code>null</code>
	 */
	protected final void setDeliveredDataFromFirstInOut(final I_C_Invoice_Candidate ic, final I_M_InOut firstInOut)
	{
		ic.setM_InOut(firstInOut);

		if (firstInOut == null)
		{
			ic.setDeliveryDate(null);
			ic.setFirst_Ship_BPLocation(null);
		}
		else
		{
			ic.setDeliveryDate(firstInOut.getMovementDate());
			ic.setFirst_Ship_BPLocation_ID(firstInOut.getC_BPartner_Location_ID());
		}
	}
}
