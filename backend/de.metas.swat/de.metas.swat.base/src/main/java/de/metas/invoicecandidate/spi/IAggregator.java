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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceLineAggregationRequest;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.aggregator.standard.DefaultAggregator;

/**
 * Implementors of this interface are called to aggregate invoice candidates.<br>
 * They are registered in the table <code>C_Invoice_Candidate_Agg</code>.
 * 
 * Input/Output:
 * <ul>
 * <li>INPUT: invoice candidates ({@link I_C_Invoice_Candidate})
 * <li>OUTPUT: list of {@link IInvoiceCandAggregate}s
 * </ul>
 * 
 * <b>IMPORTANT: Don't persist anything in an aggregator.</b> Reason: in case the invoice generation fails later, the stored record will not be rolled back, but will be left.
 * 
 * @see IAggregationBL
 * @see DefaultAggregator
 * 
 */
public interface IAggregator
{
	/**
	 * Set the context and trxName that the implementor can use.<br>
	 * Implementors can assume that this method is called right after the implementors default constructor has been invoked.
	 * 
	 * @param ctx
	 * @param trxName
	 */
	void setContext(Properties ctx, String trxName);

	/**
	 * Adds another invoice candidate to this aggregator.<br>
	 * It is assumed that all invoice candidates added to a single aggregator instance have the same "invoice-header-properties" (e.g. C_Currency_ID, C_BPartner_ID).
	 * 
	 * @param request
	 */
	void addInvoiceCandidate(IInvoiceLineAggregationRequest request);

	/**
	 * Aggregates the invoice candidates that have been added and returns the result
	 * 
	 * @return created invoice candidate aggregate VOs
	 */
	List<IInvoiceCandAggregate> aggregate();
}
