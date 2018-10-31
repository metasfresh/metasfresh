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


import java.util.List;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IAggregator;
import de.metas.util.ILoggable;
import de.metas.util.IMultitonService;
import de.metas.util.Services;

/**
 * Allows a user to aggregate multiple {@link I_C_Invoice_Candidate} records and returns a result that that is suitable to create invoices.
 * <p>
 * <b>IMPORTANT:</b> Implementing classes are expected to have an internal state. Therefore they should be obtained using {@link Services#newMultiton(Class)}.
 * 
 * @author ts
 * @see IAggregator
 */
public interface IAggregationEngine extends IMultitonService
{
	/**
	 * Returns the aggregation result containing all invoice candidates that were added by preceding addIC calls.
	 *
	 * @param loggable may be <code>null</code>. If set, log important stuff to it
	 * 
	 * @return created invoice headers VOs
	 */
	List<IInvoiceHeader> aggregate();

	/**
	 * Adds an {@link I_C_Invoice_Candidate} to be aggregated.
	 * 
	 * @param ic
	 */
	IAggregationEngine addInvoiceCandidate(I_C_Invoice_Candidate ic);

	/**
	 * @param loggable may be <code>null</code>. If set, log important stuff to it.
	 */
	IAggregationEngine setLoggable(ILoggable loggable);

	/**
	 * Sets if we shall always use the default header aggregation key builder, instead of the the builder that is consfigured on invoice candidate.
	 * 
	 * This option is used when we want to do our best to put all invoice candidates on a few amount of invoices (i.e. aggregate as much as possible).
	 * 
	 * @param alwaysUseDefaultHeaderAggregationKeyBuilder
	 */
	void setAlwaysUseDefaultHeaderAggregationKeyBuilder(boolean alwaysUseDefaultHeaderAggregationKeyBuilder);
}
