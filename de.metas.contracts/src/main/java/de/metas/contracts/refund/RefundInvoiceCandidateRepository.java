package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	private final RefundContractRepository refundContractRepository;
	private final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory;

	public RefundInvoiceCandidateRepository(
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory)
	{
		this.refundContractRepository = refundContractRepository;
		this.refundInvoiceCandidateFactory = refundInvoiceCandidateFactory;
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
	 * Note that in the case of {@link RefundMode#APPLY_TO_EXCEEDING_QTY},
	 * there can be multiple refund candidates (with different refund configs and assigned quantities) for a given contract and date.
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

	public RefundInvoiceCandidate getById(InvoiceCandidateId id)
	{
		final I_C_Invoice_Candidate refundRecord = load(id, I_C_Invoice_Candidate.class);
		return refundInvoiceCandidateFactory.ofRecord(refundRecord);
	}

	public RefundInvoiceCandidate ofRecord(@Nullable final I_C_Invoice_Candidate refundRecord)
	{
		return refundInvoiceCandidateFactory.ofRecord(refundRecord);
	}

	public Optional<RefundInvoiceCandidate> ofNullableRefundRecord(@Nullable final I_C_Invoice_Candidate refundRecord)
	{
		return refundInvoiceCandidateFactory.ofNullableRefundRecord(refundRecord);
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

	public RefundInvoiceCandidate save(@NonNull final RefundInvoiceCandidate refundCandidate)
	{
		final I_C_Invoice_Candidate record;
		if (refundCandidate.getId() == null)
		{
			record = newInstance(I_C_Invoice_Candidate.class);
		}
		else
		{
			record = load(refundCandidate.getId(), I_C_Invoice_Candidate.class);
		}

		record.setBill_BPartner_ID(refundCandidate.getBpartnerId().getRepoId());
		record.setDateToInvoice(TimeUtil.asTimestamp(refundCandidate.getInvoiceableFrom()));

		final Money money = refundCandidate.getMoney();
		record.setPriceActual(money.getValue());
		record.setPriceEntered(money.getValue());
		record.setC_Currency_ID(money.getCurrencyId().getRepoId());

		final RefundContract refundContract = refundCandidate.getRefundContract();
		final RefundContract savedRefundContract = refundContractRepository.save(refundContract);
		record.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));
		record.setRecord_ID(savedRefundContract.getId().getRepoId());

		saveRecord(record);

		return refundCandidate
				.toBuilder()
				.id(InvoiceCandidateId.ofRepoId(record.getC_Invoice_Candidate_ID()))
				.refundContract(savedRefundContract)
				.build();
	}

	@Value
	public static final class RefundInvoiceCandidateQuery
	{
		RefundContract refundContract;

		LocalDate invoicableFrom;

		@Builder
		private RefundInvoiceCandidateQuery(
				@NonNull final RefundContract refundContract,
				@Nullable final LocalDate invoicableFrom)
		{
			Check.errorIf(
					invoicableFrom != null && invoicableFrom.isBefore(refundContract.getStartDate()),
					"The given invoicableFrom needs to be after the given refundContract's startDate; invoicableFrom={}; refundContract={}",
					invoicableFrom, refundContract);
			Check.errorIf(
					invoicableFrom != null && invoicableFrom.isAfter(refundContract.getEndDate()),
					"The given invoicableFrom needs to be before the given refundContract's endDate; invoicableFrom={}; refundContract={}",
					invoicableFrom, refundContract);

			this.refundContract = refundContract;
			this.invoicableFrom = Util.coalesce(invoicableFrom, refundContract.getStartDate());
		}
	}

}
