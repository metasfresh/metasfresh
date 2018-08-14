package de.metas.contracts.refund;

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

/**
 * Supposed to be used by {@link InvoiceCandidateRepository} only;
 * For the outside world, {@link InvoiceCandidateRepository} is the only source of {@link InvoiceCandidate} instances.
 */
class InvoiceCandidateFactory
{
	private final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;

	public InvoiceCandidateFactory(
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository)
	{
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
	}

//	public <T extends InvoiceCandidate> T ofRecord(@NonNull final I_C_Invoice_Candidate record)
//	{
//		final Optional<T> recordOrNull = ofNullableRecord(record);
//
//		final Supplier<? extends AdempiereException> exceptionSupplier = //
//				() -> new AdempiereException(
//						"Unable to create and InvoiceCandidate instance for the given I_C_Invoice_Candidate record")
//								.appendParametersToMessage()
//								.setParameter("record", record);
//
//		return recordOrNull.orElseThrow(exceptionSupplier);
//	}

//	@SuppressWarnings("unchecked")
//	public <T extends InvoiceCandidate> Optional<T> ofNullableRecord(@Nullable final I_C_Invoice_Candidate record)
//	{
//		if (record == null)
//		{
//			return Optional.empty();
//		}
//
//		final Optional<RefundInvoiceCandidate> o = assignmentToRefundCandidateRepository
//				.getRefundInvoiceCandidateRepository()
//				.ofNullableRefundRecord(record);
//		if (o.isPresent())
//		{
//			return Optional.of((T)o.get());
//		}
//		return Optional.of((T)ofAssignableRecord(record));
//	}
}
