package de.metas.invoicecandidate.spi.impl.aggregator.standard;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_M_InOutLine;

import com.google.common.collect.ImmutableSet;

import de.metas.inout.IInOutBL;
import de.metas.invoice.IMatchInvDAO;
import de.metas.invoicecandidate.api.IInvoiceLineAggregationRequest;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Class used for inner IC-IOL mapping
 * 
 * @author al
 */
public final class InvoiceCandidateWithInOutLine
{
	// services
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final transient IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);

	private final I_C_Invoice_Candidate ic;
	private final I_C_InvoiceCandidate_InOutLine iciol;
	private final Set<IInvoiceLineAttribute> invoiceLineAttributes;
	private final boolean allocateRemainingQty;

	public InvoiceCandidateWithInOutLine(final IInvoiceLineAggregationRequest request)
	{
		super();

		Check.assumeNotNull(request, "request not null");
		this.ic = request.getC_Invoice_Candidate();
		this.iciol = request.getC_InvoiceCandidate_InOutLine();
		this.allocateRemainingQty = request.isAllocateRemainingQty();
		this.invoiceLineAttributes = ImmutableSet.copyOf(request.getInvoiceLineAttributes());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/** @return invoice candidate; never return <code>null</code> */
	public I_C_Invoice_Candidate getC_Invoice_Candidate()
	{
		return ic;
	}

	/** @return shipment/receipt line; could be <code>null</code> */
	public I_M_InOutLine getM_InOutLine()
	{
		if (iciol == null)
		{
			return null;
		}
		final I_M_InOutLine inOutLine = iciol.getM_InOutLine();
		if (inOutLine == null)
		{
			return null;
		}
		return inOutLine;
	}

	public BigDecimal getQtyAlreadyInvoiced()
	{
		if (iciol == null)
		{
			return BigDecimal.ZERO;
		}
		return matchInvDAO.retrieveQtyInvoiced(iciol.getM_InOutLine());
	}

	public BigDecimal getQtyAlreadyShipped()
	{
		final I_M_InOutLine inOutLine = getM_InOutLine();
		if (inOutLine == null)
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal movementQty = inOutLine.getMovementQty();
		if (inOutBL.isReturnMovementType(inOutLine.getM_InOut().getMovementType()))
		{
			return movementQty.negate();
		}
		return movementQty;
	}

	public boolean isShipped()
	{
		return getM_InOutLine() != null;
	}

	public I_C_InvoiceCandidate_InOutLine getC_InvoiceCandidate_InOutLine()
	{
		return iciol;
	}

	public Set<IInvoiceLineAttribute> getInvoiceLineAttributes()
	{
		return invoiceLineAttributes;
	}

	/**
	 * Specify if, when the aggregation is done and if {@link #getC_InvoiceCandidate_InOutLine()} is not <code>null</code> the full remaining <code>QtyToInvoice</code> of the invoice candidate shall
	 * be allocated to the <code>icIol</code>'s invoice line, or not. If <code>false</code>, then the maximum qty to be allocated is the delivered qty.
	 * <p>
	 * Note that in each aggregation, we assume that there is exactly one request with {@link #isAllocateRemainingQty()} = <code>true</code>, in order to make sure that the invoice candidate's
	 * qtyToInvoice is actually invoiced.
	 */
	public boolean isAllocateRemainingQty()
	{
		return allocateRemainingQty;
	}
}
