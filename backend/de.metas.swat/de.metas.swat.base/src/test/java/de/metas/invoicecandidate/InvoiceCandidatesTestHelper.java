package de.metas.invoicecandidate;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lock.api.LockOwner;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	 * Helper method to invoke {@link IInvoiceCandidateHandler#retrieveAllModelsWithMissingCandidates(QueryLimit)} and then
	 * {@link IInvoiceCandidateHandler#createCandidatesFor(InvoiceCandidateGenerateRequest)} for retrieved models.
	 *
	 * @return invoice candidates
	 */
	public static List<I_C_Invoice_Candidate> createMissingCandidates(@NonNull final IInvoiceCandidateHandler handler, @NonNull final QueryLimit limit)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidatesAll = new ArrayList<>();
		final Iterator<?> models = handler.retrieveAllModelsWithMissingCandidates(limit);
		while (models.hasNext())
		{
			final Object model = models.next();
			final LockOwner lockOwner = LockOwner.newOwner("InvoiceCandidatesTestHelper" + "#generateInvoiceCandidates");
			final InvoiceCandidateGenerateResult result = handler.createCandidatesFor(InvoiceCandidateGenerateRequest.of(handler, model, lockOwner));
			invoiceCandidatesAll.addAll(result.getC_Invoice_Candidates());
		}

		return invoiceCandidatesAll;
	}

}
