package de.metas.invoicecandidate.api;

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

import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IAggregator;

import java.util.List;
import java.util.Set;

/**
 * Immutable Invoice Line Aggregation Request to be used on {@link IAggregator}.
 * 
 * NOTE: the request object is already valid (i.e. invoice candidate is not null, C_InvoiceCandidate_InOutLine is matching the invoice candidate etc).
 */
public interface IInvoiceLineAggregationRequest
{
	/**
	 * @return invoice candidate; never return null;
	 */
	I_C_Invoice_Candidate getC_Invoice_Candidate();

	/**
	 * @return IC-IOL association; could also be <code>null</code>; the IC-IOL will always match the {@link #getC_Invoice_Candidate()}.
	 */
	I_C_InvoiceCandidate_InOutLine getC_InvoiceCandidate_InOutLine();

	/**
	 * 
	 * NOTE: basically this shall be always empty because everything which is related to line aggregation
	 * shall be configured from aggregation definition,
	 * but we are also leaving this door open in case we need to implement some quick/hot fixes.
	 * 
	 * @return additional aggregation key elements to be used when we are aggregating the on line level.
	 */
	List<Object> getLineAggregationKeyElements();

	/** @return invoice line product attributes that shall land into invoice line */
	Set<IInvoiceLineAttribute> getAttributesFromInoutLines();

	/**
	 * Specify if, when the aggregation is done and if {@link #getC_InvoiceCandidate_InOutLine()} is not <code>null</code> the full remaining <code>QtyToInvoice</code> of the invoice candidate shall
	 * be allocated to the <code>icIol</code>'s invoice line, or not. If <code>false</code>, then the maximum qty to be allocated is the delivered qty.
	 * <p>
	 * Note that in each aggregation, we assume that there is exactly one request with this method returning <code>true</code>, in order to make sure that the invoice candidate's
	 * qtyToInvoice is actually invoiced.
	 */
	boolean isAllocateRemainingQty();
}
