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


import java.math.BigDecimal;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Do nothing implementation of {@link IInvoiceCandidatesChangesChecker}.
 *
 * @author tsa
 *
 */
/* package */final class NullInvoiceCandidatesChangesChecker implements IInvoiceCandidatesChangesChecker
{
	public static final transient NullInvoiceCandidatesChangesChecker instance = new NullInvoiceCandidatesChangesChecker();

	private NullInvoiceCandidatesChangesChecker()
	{
	}

	@Override
	public IInvoiceCandidatesChangesChecker setBeforeChanges(Iterable<I_C_Invoice_Candidate> candidates)
	{
		return this;
	}

	@Override
	public void assertNoChanges(Iterable<I_C_Invoice_Candidate> candidates)
	{
	}

	@Override
	public IInvoiceCandidatesChangesChecker setTotalNetAmtToInvoiceChecksum(BigDecimal totalNetAmtToInvoiceChecksum)
	{
		return this;
	}

}
