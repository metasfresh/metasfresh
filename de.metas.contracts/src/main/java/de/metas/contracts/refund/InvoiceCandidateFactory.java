package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TimeUtil;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyFactory;
import de.metas.product.ProductId;
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
	private final InvoiceCandidateRepository invoiceCandidateRepository;
	private final RefundContractRepository refundContractRepository;
	private final MoneyFactory moneyFactory;

	public InvoiceCandidateFactory(
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final MoneyFactory moneyFactory)
	{
		this.refundContractRepository = refundContractRepository;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.moneyFactory = moneyFactory;
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

		final Optional<RefundInvoiceCandidate> o = ofNullableRefundRecord(record);
		if (o.isPresent())
		{
			return Optional.of((T)o.get());
		}
		return Optional.of((T)ofAssignableRecord(record));

	}

	public Optional<RefundInvoiceCandidate> ofNullableRefundRecord(@Nullable final I_C_Invoice_Candidate refundRecord)
	{
		if (refundRecord == null)
		{
			return Optional.empty();
		}

		final TableRecordReference reference = refundRecord.getAD_Table_ID() > 0
				? TableRecordReference.ofReferenced(refundRecord)
				: null;

		final Optional<RefundContract> refundContract = retrieveRefundContractOrNull(reference);
		if (!refundContract.isPresent())
		{
			return Optional.empty();
		}

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(refundRecord.getC_Invoice_Candidate_ID());

		final Timestamp invoicableFromDate = getValueOverrideOrValue(refundRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);

		final BigDecimal priceActual = getValueOverrideOrValue(refundRecord, I_C_Invoice_Candidate.COLUMNNAME_PriceActual);
		final Money money = moneyFactory.forAmountAndCurrencyId(
				priceActual,
				CurrencyId.ofRepoId(refundRecord.getC_Currency_ID()));

		final RefundInvoiceCandidate invoiceCandidate = RefundInvoiceCandidate
				.builder()
				.id(invoiceCandidateId)
				.refundContract(refundContract.get())
				.bpartnerId(BPartnerId.ofRepoId(refundRecord.getBill_BPartner_ID()))
				.invoiceableFrom(TimeUtil.asLocalDate(invoicableFromDate))
				.money(money)
				.build();
		return Optional.of(invoiceCandidate);
	}

	private AssignableInvoiceCandidate ofAssignableRecord(@Nullable final I_C_Invoice_Candidate assignableRecord)
	{
		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(assignableRecord.getC_Invoice_Candidate_ID());

		final Optional<AssignmentToRefundCandidate> assignmentToRefundCandidate = //
				invoiceCandidateRepository.getAssignmentToRefundCandidate(invoiceCandidateId);

		final Timestamp invoicableFromDate = getValueOverrideOrValue(assignableRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);
		final BigDecimal moneyAmount = assignableRecord
				.getNetAmtInvoiced()
				.add(assignableRecord.getNetAmtToInvoice());

		final CurrencyId currencyId = CurrencyId.ofRepoId(assignableRecord.getC_Currency_ID());
		final Money money = moneyFactory.forAmountAndCurrencyId(moneyAmount, currencyId);

		final AssignableInvoiceCandidate invoiceCandidate = AssignableInvoiceCandidate.builder()
				.id(invoiceCandidateId)
				.assignmentToRefundCandidate(assignmentToRefundCandidate.orElse(null))
				.bpartnerId(BPartnerId.ofRepoId(assignableRecord.getBill_BPartner_ID()))
				.invoiceableFrom(TimeUtil.asLocalDate(invoicableFromDate))
				.money(money)
				.productId(ProductId.ofRepoId(assignableRecord.getM_Product_ID()))
				.build();

		return invoiceCandidate;
	}

	private Optional<RefundContract> retrieveRefundContractOrNull(@Nullable final TableRecordReference reference)
	{
		if (reference == null)
		{
			return Optional.empty();
		}

		if (!I_C_Flatrate_Term.Table_Name.equals(reference.getTableName()))
		{
			return Optional.empty();
		}

		final I_C_Flatrate_Term term = reference.getModel(I_C_Flatrate_Term.class);
		if (!X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(term.getType_Conditions()))
		{
			return Optional.empty();
		}

		final RefundContract refundContract = refundContractRepository.getById(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()));
		return Optional.of(refundContract);
	}
}
