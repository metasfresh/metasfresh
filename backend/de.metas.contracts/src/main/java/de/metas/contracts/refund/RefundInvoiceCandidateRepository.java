package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.util.TimeUtil.asTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.RefundContract.NextInvoiceDate;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.Getter;
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
	@VisibleForTesting
	@Getter
	private final RefundContractRepository refundContractRepository;

	@Getter
	private final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory;

	@VisibleForTesting
	public static RefundInvoiceCandidateRepository createInstanceForUnitTesting()
	{
		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());

		final AssignmentAggregateService assignmentAggregateService = new AssignmentAggregateService(refundConfigRepository);

		final RefundContractRepository refundContractRepository = new RefundContractRepository(refundConfigRepository);

		final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory = new RefundInvoiceCandidateFactory(
				refundContractRepository,
				assignmentAggregateService);

		return new RefundInvoiceCandidateRepository(
				refundContractRepository,
				refundInvoiceCandidateFactory);
	}

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

		// if these conditions are not me, we know that there won't be matching refund invoice candidates; but that doesn't make it an error.
		//
		final RefundContract refundContract = query.getRefundContract();
		final LocalDate invoicableFrom = query.getInvoicableFrom();

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class);

		if (invoicableFrom.isBefore(refundContract.getStartDate()) || invoicableFrom.isAfter(refundContract.getEndDate()))
		{
			// there can't be any matching refund records because invoicableFrom lies outside the contract's date range.
			return queryBuilder
					.filter(ConstantQueryFilter.of(false))
					.create();
		}

		final NextInvoiceDate nextInvoiceDate = refundContract.computeNextInvoiceDate(invoicableFrom);

		final IQueryFilter<I_C_Invoice_Candidate> dateToInvoiceEffectiveFilter = createDateToInvoiceEffectiveFilter(
				asTimestamp(invoicableFrom),
				asTimestamp(nextInvoiceDate.getDateToInvoice()));

		return queryBuilder
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID,
						getTableId(I_C_Flatrate_Term.class))
				.addEqualsFilter(
						I_C_Invoice_Candidate.COLUMNNAME_Record_ID,
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
		record.setBill_Location_ID(refundCandidate.getBpartnerLocationId().getRepoId());
		record.setDateToInvoice(asTimestamp(refundCandidate.getInvoiceableFrom()));

		record.setM_Product_ID(RefundConfigs.extractProductId(refundCandidate.getRefundConfigs()).getRepoId());

		// note that Quantity = 1 is set elsewhere, in the invoice candidate handler
		final Money money = refundCandidate.getMoney();
		record.setPriceActual(money.toBigDecimal());
		record.setPriceEntered(money.toBigDecimal());
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
				@NonNull final LocalDate invoicableFrom)
		{
			this.refundContract = refundContract;
			this.invoicableFrom = CoalesceUtil.coalesce(invoicableFrom, refundContract.getStartDate());
		}
	}
}
