package de.metas.invoicecandidate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;

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

/**
 * Invoice candidates testing helper.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class InvoiceCandidatesTestHelper
{
	/**
	 * Creates all missing invoice candidates
	 * 
	 * Helper method to invoke {@link IInvoiceCandidateHandler#retrieveAllModelsWithMissingCandidates(Properties, int, String)} and then
	 * {@link IInvoiceCandidateHandler#createCandidatesFor(InvoiceCandidateGenerateRequest)} for retrieved models.
	 * 
	 * @param handler
	 * @param ctx
	 * @param limit
	 * @param trxName
	 * @return invoice candidates
	 */
	public static List<I_C_Invoice_Candidate> createMissingCandidates(final IInvoiceCandidateHandler handler, final Properties ctx, final int limit, final String trxName)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidatesAll = new ArrayList<>();
		final Iterator<? extends Object> models = handler.retrieveAllModelsWithMissingCandidates(ctx, limit, trxName);
		while (models.hasNext())
		{
			final Object model = models.next();
			final InvoiceCandidateGenerateResult result = handler.createCandidatesFor(InvoiceCandidateGenerateRequest.of(handler, model));
			invoiceCandidatesAll.addAll(result.getC_Invoice_Candidates());
		}

		return invoiceCandidatesAll;
	}

}
