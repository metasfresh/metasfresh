package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.LocalDate;
import java.util.Iterator;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Util;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
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
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository,
			@NonNull final RefundContractRepository refundContractRepository)
	{
		this.invoiceCandidateFactory = new InvoiceCandidateFactory(
				assignmentToRefundCandidateRepository);
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

	public <T extends InvoiceCandidate> T getById(@NonNull final InvoiceCandidateId id)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(id.getRepoId(), I_C_Invoice_Candidate.class);
		return invoiceCandidateFactory.ofRecord(invoiceCandidateRecord);
	}

	public AssignableInvoiceCandidate saveCandidateAssignment(@Nullable final UnassignedPairOfCandidates request)
	{
		final RefundConfig refundConfig = request.getRefundInvoiceCandidate().getRefundConfig();
		final I_C_Invoice_Candidate_Assignment assignmentRecord = loadOrCreateAssignmentRecord(
				request.getAssignableInvoiceCandidate(),
				refundConfig);

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

	private I_C_Invoice_Candidate_Assignment loadOrCreateAssignmentRecord(
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate,
			@NonNull final RefundConfig refundConfig)
	{

		final I_C_Invoice_Candidate_Assignment existingAssignment = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID, assignableInvoiceCandidate.getId())
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Flatrate_RefundConfig_ID, refundConfig.getId())
				.create()
				.firstOnly(I_C_Invoice_Candidate_Assignment.class);

		if (existingAssignment != null)
		{
			return existingAssignment;
		}

		final int repoId = assignableInvoiceCandidate.getId().getRepoId();
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

	public void save(@NonNull final RefundInvoiceCandidate invoiceCandidate)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(invoiceCandidate.getId().getRepoId(), I_C_Invoice_Candidate.class);
		final Money money = invoiceCandidate.getMoney();

		invoiceCandidateRecord.setPriceActual(money.getValue());
		invoiceCandidateRecord.setPriceEntered(money.getValue());
		invoiceCandidateRecord.setC_Currency_ID(money.getCurrencyId().getRepoId());
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
}
