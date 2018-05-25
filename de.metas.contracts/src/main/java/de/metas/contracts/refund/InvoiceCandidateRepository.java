package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.money.MoneyFactory;
import lombok.AccessLevel;
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
public class InvoiceCandidateRepository
{
	@VisibleForTesting
	@Getter(AccessLevel.PACKAGE)
	private final InvoiceCandidateFactory invoiceCandidateFactory;

	public InvoiceCandidateRepository(
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final MoneyFactory moneyFactory)
	{
		this.invoiceCandidateFactory = new InvoiceCandidateFactory(
				this,
				refundContractRepository,
				moneyFactory);
	}

	public <T extends InvoiceCandidate> T ofRecord(@NonNull final I_C_Invoice_Candidate record)
	{
		return invoiceCandidateFactory.ofRecord(record);
	}

	public boolean isRefundInvoiceCandidateRecord(@NonNull final I_C_Invoice_Candidate record)
	{
		if (record.getAD_Table_ID() != getTableId(I_C_Flatrate_Term.class))
		{
			return false;
		}

		final I_C_Flatrate_Term term = loadOutOfTrx(record.getRecord_ID(), I_C_Flatrate_Term.class);
		final boolean recordIsARefundCandidate = X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(term.getType_Conditions());

		return recordIsARefundCandidate;
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

	public Optional<RefundInvoiceCandidate> getRefundInvoiceCandidate(
			@NonNull final RefundInvoiceCandidateQuery query)
	{
		final I_C_Invoice_Candidate recordOrNull = createRefundInvoiceCandidateQuery(query)
				.first();

		return invoiceCandidateFactory
				.ofNullableRefundRecord(recordOrNull)
				.map(RefundInvoiceCandidate::cast);
	}

	public <T extends InvoiceCandidate> T getById(@NonNull final InvoiceCandidateId id)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(id.getRepoId(), I_C_Invoice_Candidate.class);
		return invoiceCandidateFactory.ofRecord(invoiceCandidateRecord);
	}

	private IQuery<I_C_Invoice_Candidate> createRefundInvoiceCandidateQuery(
			@NonNull final RefundInvoiceCandidateQuery query)
	{
		final RefundContract refundContract = query.getRefundContract();

		// we need the first candidate (ordered by DateToInvoice_Effective)
		// with DateToInvoice_Effective
		// being before the contract's end date
		// being after the query's invoicableFrom
		//
		// i.e. we need the "next" IC whose DateToInvoice is after the query's date
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
				.orderBy()
				.addColumnAscending(I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Effective)
				.endOrderBy()
				.create();
	}

	public AssignableInvoiceCandidate saveCandidateAssignment(@Nullable final UnassignedPairOfCandidates request)
	{
		final I_C_Invoice_Candidate_Assignment assignmentRecord = loadOrCreateAssignmentRecord(request.getAssignableInvoiceCandidate());

		final RefundInvoiceCandidate refundInvoiceCandidate = request.getRefundInvoiceCandidate();

		assignmentRecord.setC_Invoice_Candidate_Term_ID(refundInvoiceCandidate.getId().getRepoId());
		assignmentRecord.setC_Flatrate_Term_ID(refundInvoiceCandidate.getRefundContract().getId().getRepoId());
		assignmentRecord.setAssignedAmount(request.getMoneyToAssign().getValue());
		saveRecord(assignmentRecord);

		final AssignmentToRefundCandidate assignmentToRefundCandidate = //
				new AssignmentToRefundCandidate(refundInvoiceCandidate, request.getMoneyToAssign());
		return request
				.getAssignableInvoiceCandidate()
				.toBuilder()
				.assignmentToRefundCandidate(assignmentToRefundCandidate)
				.build();
	}

	private I_C_Invoice_Candidate_Assignment loadOrCreateAssignmentRecord(@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate)
	{
		final int repoId = assignableInvoiceCandidate.getId().getRepoId();

		final I_C_Invoice_Candidate_Assignment existingAssignment = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID, repoId)
				.create()
				.firstOnly(I_C_Invoice_Candidate_Assignment.class);

		if (existingAssignment != null)
		{
			return existingAssignment;
		}
		final I_C_Invoice_Candidate_Assignment newAssignment = newInstance(I_C_Invoice_Candidate_Assignment.class);
		newAssignment.setC_Invoice_Candidate_Assigned_ID(repoId);
		return newAssignment;
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

	public void deleteAssignments(@Nullable final DeleteAssignmentsRequest request)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Invoice_Candidate_Assignment> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class);

		if (request.isOnlyActive())
		{
			queryBuilder.addOnlyActiveRecordsFilter();
		}

		final ICompositeQueryFilter<I_C_Invoice_Candidate_Assignment> invoiceCandidateIDsOrFilter = queryBL
				.createCompositeQueryFilter(I_C_Invoice_Candidate_Assignment.class)
				.setJoinOr();

		final InvoiceCandidateId removeForContractCandidateId = request.getRemoveForRefundCandidateId();
		if (removeForContractCandidateId != null)
		{
			invoiceCandidateIDsOrFilter.addEqualsFilter(
					I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID,
					removeForContractCandidateId.getRepoId());
		}
		final InvoiceCandidateId removeForAssignedCandidateId = request.getRemoveForAssignedCandidateId();
		if (removeForAssignedCandidateId != null)
		{
			invoiceCandidateIDsOrFilter.addEqualsFilter(
					I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
					removeForAssignedCandidateId.getRepoId());
		}

		queryBuilder
				.filter(invoiceCandidateIDsOrFilter)
				.create()
				.delete();
	}

	/**
	 *
	 * Note: {@link DeleteAssignmentsRequestBuilder#removeForAssignedCandidateId(InvoiceCandidateId)}
	 * and {@link DeleteAssignmentsRequestBuilder#removeForRefundCandidateId}
	 * are {@code OR}ed and at least one of them has to be no-null.
	 *
	 */
	@Value
	public static final class DeleteAssignmentsRequest
	{
		InvoiceCandidateId removeForRefundCandidateId;
		InvoiceCandidateId removeForAssignedCandidateId;

		boolean onlyActive;

		@Builder
		private DeleteAssignmentsRequest(
				@Nullable final InvoiceCandidateId removeForRefundCandidateId,
				@Nullable final InvoiceCandidateId removeForAssignedCandidateId,
				@Nullable final Boolean onlyActive)
		{
			Check.errorIf(
					removeForRefundCandidateId == null
							&& removeForAssignedCandidateId == null,
					"At least one of the two invoiceCandidateId needs to be not-null");

			this.onlyActive = Util.coalesce(onlyActive, true);

			this.removeForRefundCandidateId = removeForRefundCandidateId;
			this.removeForAssignedCandidateId = removeForAssignedCandidateId;
		}
	}

	public Optional<AssignmentToRefundCandidate> getAssignmentToRefundCandidate(
			@NonNull final InvoiceCandidateId assignableInvoiceCandidateId)
	{
		final I_C_Invoice_Candidate_Assignment assignmentRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
						assignableInvoiceCandidateId.getRepoId())
				.create()
				.firstOnly(I_C_Invoice_Candidate_Assignment.class); // we have a UC on this

		if (assignmentRecord == null)
		{
			return Optional.empty();
		}

		final I_C_Invoice_Candidate refundRecord = load(
				assignmentRecord.getC_Invoice_Candidate_Term_ID(),
				I_C_Invoice_Candidate.class);

		final Optional<RefundInvoiceCandidate> refundCandidate = invoiceCandidateFactory.ofNullableRefundRecord(refundRecord);
		if (!refundCandidate.isPresent())
		{
			return Optional.empty();
	}

		final Money assignedMoney = Money.of(
				assignmentRecord.getAssignedAmount(),
				refundCandidate.get().getMoney().getCurrency());

		final AssignmentToRefundCandidate assignmentToRefundCandidate = new AssignmentToRefundCandidate(
				refundCandidate.get(),
				assignedMoney);
		return Optional.of(assignmentToRefundCandidate);
	}

	public void save(@NonNull final RefundInvoiceCandidate invoiceCandidate)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(invoiceCandidate.getId().getRepoId(), I_C_Invoice_Candidate.class);
		final Money money = invoiceCandidate.getMoney();

		invoiceCandidateRecord.setPriceActual(money.getValue());
		invoiceCandidateRecord.setPriceEntered(money.getValue());
		invoiceCandidateRecord.setC_Currency_ID(money.getCurrency().getId().getRepoId());
		saveRecord(invoiceCandidateRecord);
	}

	public Iterator<AssignableInvoiceCandidate> getAllAssigned(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID,
						refundInvoiceCandidate.getId().getRepoId())
				.andCollect(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
						I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(invoiceCandidateFactory::ofRecord)
				.map(AssignableInvoiceCandidate::cast)
				.iterator();
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

	public RefundInvoiceCandidate createRefundInvoiceCandidate(
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate,
			@NonNull final FlatrateTermId contractId)
	{
		return invoiceCandidateFactory.createRefundInvoiceCandidate(assignableInvoiceCandidate, contractId);
	}
}
