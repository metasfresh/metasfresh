package de.metas.contracts.refund;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.refund.InvoiceCandidateRepository.AssociationRequest;
import de.metas.contracts.refund.InvoiceCandidateRepository.InvoiceCandidateQuery;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class InvoiceCandidateAssociationService
{
	private final RefundContractRepository refundContractRepository;

	private final InvoiceCandidateRepository invoiceCandidateRepository;

	private InvoiceCandidateAssociationService(
			@NonNull final RefundContractRepository refundConfigRepository,
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository)
	{
		this.refundContractRepository = refundConfigRepository;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
	}

	public void associateWithRefundCandidateIfFeasible(@NonNull final InvoiceCandidate invoiceCandidate)
	{
		final Optional<FlatrateTermId> flatrateTermId = refundContractRepository.getIdByInvoiceCandidate(invoiceCandidate);
		if (!flatrateTermId.isPresent())
		{
			return; // nothing to do
		}

		final InvoiceCandidateQuery query = InvoiceCandidateQuery.builder()
				.flatrateTermId(flatrateTermId.get())
				.invoicableFrom(invoiceCandidate.getInvoiceableFrom())
				.build();
		final Optional<InvoiceCandidate> contractInvoiceCandidate = invoiceCandidateRepository.get(query);
		if(!contractInvoiceCandidate.isPresent())
		{
			return; // nothing to do
		}

		final AssociationRequest request = AssociationRequest
				.builder()
				.candidateToAssign(invoiceCandidate.getId())
				.contractCandidate(contractInvoiceCandidate.get().getId())
				.build();
		invoiceCandidateRepository.associateCandidates(request);
	}
}
