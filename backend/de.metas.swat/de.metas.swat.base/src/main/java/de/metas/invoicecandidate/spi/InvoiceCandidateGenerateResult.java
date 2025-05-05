package de.metas.invoicecandidate.spi;

import com.google.common.collect.ImmutableList;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

import javax.annotation.Nullable;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Result of {@link IInvoiceCandidateHandler#createCandidatesFor(InvoiceCandidateGenerateRequest)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public final class InvoiceCandidateGenerateResult
{
	public static InvoiceCandidateGenerateResult of(
			@NonNull final IInvoiceCandidateHandler handler,
			@NonNull final List<? extends I_C_Invoice_Candidate> invoiceCandidates)
	{
		return new InvoiceCandidateGenerateResult(handler, invoiceCandidates);
	}

	public static InvoiceCandidateGenerateResult of(
			@NonNull final IInvoiceCandidateHandler handler,
			@Nullable final I_C_Invoice_Candidate invoiceCandidate)
	{
		if (invoiceCandidate == null)
		{
			final List<I_C_Invoice_Candidate> invoiceCandidates = ImmutableList.of();
			return new InvoiceCandidateGenerateResult(handler, invoiceCandidates);
		}
		return new InvoiceCandidateGenerateResult(handler, ImmutableList.of(invoiceCandidate));
	}

	public static InvoiceCandidateGenerateResult of(@NonNull final IInvoiceCandidateHandler handler)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = ImmutableList.of();
		return new InvoiceCandidateGenerateResult(handler, invoiceCandidates);
	}

	private final IInvoiceCandidateHandler handler;
	private final List<I_C_Invoice_Candidate> invoiceCandidates;

	private InvoiceCandidateGenerateResult(
			@NonNull final IInvoiceCandidateHandler handler,
			@NonNull final List<? extends I_C_Invoice_Candidate> invoiceCandidates)
	{
		this.handler = handler;
		this.invoiceCandidates = ImmutableList.copyOf(invoiceCandidates);
	}

	public IInvoiceCandidateHandler getHandler()
	{
		return handler;
	}

	public List<I_C_Invoice_Candidate> getC_Invoice_Candidates()
	{
		return invoiceCandidates;
	}
}
