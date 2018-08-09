package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import de.metas.bpartner.BPartnerId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
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

	public <T extends InvoiceCandidate> T ofRecord(@NonNull final I_C_Invoice_Candidate record)
	{
		final Optional<T> recordOrNull = ofNullableRecord(record);

		final Supplier<? extends AdempiereException> exceptionSupplier = //
				() -> new AdempiereException(
						"Unable to create and InvoiceCandidate instance for the given I_C_Invoice_Candidate record")
								.appendParametersToMessage()
								.setParameter("record", record);

		return recordOrNull.orElseThrow(exceptionSupplier);
	}

	@SuppressWarnings("unchecked")
	public <T extends InvoiceCandidate> Optional<T> ofNullableRecord(@Nullable final I_C_Invoice_Candidate record)
	{
		if (record == null)
		{
			return Optional.empty();
		}

		final Optional<RefundInvoiceCandidate> o = assignmentToRefundCandidateRepository
				.getRefundInvoiceCandidateRepository()
				.ofNullableRefundRecord(record);
		if (o.isPresent())
		{
			return Optional.of((T)o.get());
		}
		return Optional.of((T)ofAssignableRecord(record));
	}

	private AssignableInvoiceCandidate ofAssignableRecord(@Nullable final I_C_Invoice_Candidate assignableRecord)
	{
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(assignableRecord.getC_Invoice_Candidate_ID());

		final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = //
				assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(invoiceCandidateId);

		final Timestamp invoicableFromDate = getValueOverrideOrValue(assignableRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);
		final BigDecimal moneyAmount = assignableRecord
				.getNetAmtInvoiced()
				.add(assignableRecord.getNetAmtToInvoice());

		final CurrencyId currencyId = CurrencyId.ofRepoId(assignableRecord.getC_Currency_ID());
		final Money money = Money.of(moneyAmount, currencyId);

		final Quantity quantity = Quantity.of(
				assignableRecord.getQtyToInvoice().add(assignableRecord.getQtyInvoiced()),
				assignableRecord.getM_Product().getC_UOM());

		final AssignableInvoiceCandidate invoiceCandidate = AssignableInvoiceCandidate.builder()
				.id(invoiceCandidateId)
				.assignmentsToRefundCandidates(assignmentsToRefundCandidates)
				.bpartnerId(BPartnerId.ofRepoId(assignableRecord.getBill_BPartner_ID()))
				.invoiceableFrom(TimeUtil.asLocalDate(invoicableFromDate))
				.money(money)
				.quantity(quantity)
				.productId(ProductId.ofRepoId(assignableRecord.getM_Product_ID()))
				.build();

		return invoiceCandidate;
	}





}
