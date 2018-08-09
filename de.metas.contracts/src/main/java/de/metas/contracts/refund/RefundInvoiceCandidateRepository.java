package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.InvoiceCandidateRepository.RefundInvoiceCandidateQuery;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
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

@Repository
public class RefundInvoiceCandidateRepository
{
	private RefundContractRepository refundContractRepository;

	public RefundInvoiceCandidateRepository(@NonNull final RefundContractRepository refundContractRepository)
	{
		this.refundContractRepository = refundContractRepository;
	}

	public Optional<InvoiceCandidateId> getId(@NonNull final RefundInvoiceCandidateQuery query)
	{
		final int recordId = createRefundInvoiceCandidateQuery(query)
				.firstId();

		if (recordId > 0)
		{
			Optional.of(InvoiceCandidateId.ofRepoId(recordId));
		}
		return Optional.empty();
	}

	/**
	 * Note that in the case of {@link RefundMode#PER_INDIVIDUAL_SCALE},
	 * there can be multiple refund candidates (with different quantities) for a given contract and date.
	 *
	 * @return matching candidates with the minimum {@code DateToInvoice} value, ordered by quantity.
	 */
	public List<RefundInvoiceCandidate> getRefundInvoiceCandidates(
			@NonNull final RefundInvoiceCandidateQuery query)
	{
		final List<I_C_Invoice_Candidate> records = createRefundInvoiceCandidateQuery(query)
				.list();
		if (records.isEmpty())
		{
			return ImmutableList.of();
		}

		// we need the first candidates (ordered by DateToInvoice_Effective)
		// with DateToInvoice_Effective
		// being before the contract's end date
		// being after the query's invoicableFrom
		// i.e. we need the "next" IC whose DateToInvoice is after the query's date
		//
		final ImmutableListMultimap<Timestamp, I_C_Invoice_Candidate> //
		dateToInvoice2InvoiceCandidateRecords = Multimaps.index(
				records,
				record -> (Timestamp)getValueOverrideOrValue(record, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice));
		final Timestamp minDateToInvoice = dateToInvoice2InvoiceCandidateRecords
				.keySet()
				.stream()
				.min(Comparator.naturalOrder())
				.get(); // 'records' is not empty, so there must be a min timestamp

		return dateToInvoice2InvoiceCandidateRecords
				.get(minDateToInvoice)
				.stream()
				.map(this::ofNullableRefundRecord)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.sorted(Comparator.comparing(RefundInvoiceCandidate::getAssignedQuantity))
				.collect(ImmutableList.toImmutableList());
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

		final RefundContract refundContract = retrieveRefundContractOrNull(reference);
		if (refundContract == null)
		{
			return Optional.empty();
		}

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(refundRecord.getC_Invoice_Candidate_ID());

		final BigDecimal assignedQuantity = retrieveAssignedQuantity(invoiceCandidateId);

		final Timestamp invoicableFromDate = getValueOverrideOrValue(refundRecord, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);

		final BigDecimal priceActual = getValueOverrideOrValue(refundRecord, I_C_Invoice_Candidate.COLUMNNAME_PriceActual);
		final Money money = Money.of(
				priceActual,
				CurrencyId.ofRepoId(refundRecord.getC_Currency_ID()));

		final RefundInvoiceCandidate invoiceCandidate = RefundInvoiceCandidate
				.builder()
				.id(invoiceCandidateId)
				.refundContract(refundContract)
				.refundConfig(refundContract.getRefundConfig(assignedQuantity))
				.bpartnerId(BPartnerId.ofRepoId(refundRecord.getBill_BPartner_ID()))
				.invoiceableFrom(TimeUtil.asLocalDate(invoicableFromDate))
				.assignedQuantity(Quantity.of(assignedQuantity, refundRecord.getM_Product().getC_UOM()))
				.money(money)
				.build();
		return Optional.of(invoiceCandidate);
	}

	private RefundContract retrieveRefundContractOrNull(@Nullable final TableRecordReference reference)
	{
		if (reference == null)
		{
			return null;
		}

		if (!I_C_Flatrate_Term.Table_Name.equals(reference.getTableName()))
		{
			return null;
		}

		final I_C_Flatrate_Term term = reference.getModel(I_C_Flatrate_Term.class);
		if (!X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(term.getType_Conditions()))
		{
			return null;
		}

		final RefundContract refundContract = refundContractRepository.getById(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()));
		return refundContract;
	}

	private BigDecimal retrieveAssignedQuantity(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final BigDecimal assignedQuantity = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID, invoiceCandidateId)
				.create()
				.aggregate(I_C_Invoice_Candidate_Assignment.COLUMN_AssignedQuantity, Aggregate.SUM, BigDecimal.class);
		return assignedQuantity;
	}

	private IQuery<I_C_Invoice_Candidate> createRefundInvoiceCandidateQuery(
			@NonNull final RefundInvoiceCandidateQuery query)
	{
		final RefundContract refundContract = query.getRefundContract();

		final IQueryFilter<I_C_Invoice_Candidate> dateToInvoiceEffectiveFilter = createDateToInvoiceEffectiveFilter(
				TimeUtil.asTimestamp(query.getInvoicableFrom()),
				TimeUtil.asTimestamp(refundContract.getEndDate()));

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMN_AD_Table_ID,
						getTableId(I_C_Flatrate_Term.class))
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMN_Record_ID,
						query.getRefundContract().getId().getRepoId())
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMN_Processed,
						false)
				.filter(dateToInvoiceEffectiveFilter)
				.create();
	}

	public IQueryFilter<I_C_Invoice_Candidate> createDateToInvoiceEffectiveFilter(
			@NonNull final Timestamp startDate,
			@NonNull final Timestamp endDate)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_C_Invoice_Candidate> normalFilter = queryBL
				.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_DateToInvoice_Override, null)
				.addBetweenFilter(I_C_Invoice_Candidate.COLUMN_DateToInvoice, startDate, endDate);

		final ICompositeQueryFilter<I_C_Invoice_Candidate> overrideFilter = queryBL
				.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
				.addNotNull(I_C_Invoice_Candidate.COLUMN_DateToInvoice_Override)
				.addBetweenFilter(I_C_Invoice_Candidate.COLUMN_DateToInvoice_Override, startDate, endDate);

		final ICompositeQueryFilter<I_C_Invoice_Candidate> dateToInvoiceEffectiveFilter = queryBL
				.createCompositeQueryFilter(I_C_Invoice_Candidate.class)
				.setJoinOr()
				.addFilter(normalFilter)
				.addFilter(overrideFilter);

		return dateToInvoiceEffectiveFilter;
	}
}
