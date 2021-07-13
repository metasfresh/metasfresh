package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.product.IProductDAO;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
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
public class AssignmentToRefundCandidateRepository
{
	@Getter
	private final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	public AssignmentToRefundCandidateRepository(
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository)
	{
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
	}

	public List<AssignmentToRefundCandidate> getAssignmentsByAssignableCandidateId(@NonNull final InvoiceCandidateId assignableCandidateId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_C_Invoice_Candidate_Assignment> assignmentRecords = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
						assignableCandidateId.getRepoId())
				.create()
				.list(I_C_Invoice_Candidate_Assignment.class); // we might have multiple records with different C_Flatrate_RefundConfig_ID

		final ImmutableList.Builder<AssignmentToRefundCandidate> result = ImmutableList.builder();
		for (final I_C_Invoice_Candidate_Assignment assignmentRecord : assignmentRecords)
		{
			final AssignmentToRefundCandidate assignmentToRefundCandidate = ofRecordOrNull(assignmentRecord);
			if (assignmentToRefundCandidate != null)
			{
				result.add(assignmentToRefundCandidate);
			}
		}
		return result.build();
	}

	public AssignmentToRefundCandidate ofRecordOrNull(@NonNull final I_C_Invoice_Candidate_Assignment assignmentRecord)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final I_C_Invoice_Candidate refundRecord = load(
				assignmentRecord.getC_Invoice_Candidate_Term_ID(),
				I_C_Invoice_Candidate.class);

		final Optional<RefundInvoiceCandidate> refundCandidate = refundInvoiceCandidateRepository.ofNullableRefundRecord(refundRecord);
		if (!refundCandidate.isPresent())
		{
			return null;
		}

		final Money baseMoney = Money.of(
				assignmentRecord.getBaseMoneyAmount(),
				refundCandidate.get().getMoney().getCurrencyId());

		final Money assignedMoney = Money.of(
				assignmentRecord.getAssignedMoneyAmount(),
				refundCandidate.get().getMoney().getCurrencyId());

		final I_M_Product product = productDAO.getById(refundRecord.getM_Product_ID());
		final I_C_UOM productUom = uomDAO.getById(product.getC_UOM_ID());

		final Quantity assignedQuantity = Quantity.of(assignmentRecord.getAssignedQuantity(), productUom);

		final AssignmentToRefundCandidate assignmentToRefundCandidate = new AssignmentToRefundCandidate(
				RefundConfigId.ofRepoId(assignmentRecord.getC_Flatrate_RefundConfig_ID()),
				InvoiceCandidateId.ofRepoId(assignmentRecord.getC_Invoice_Candidate_Assigned_ID()),
				refundCandidate.get(),
				baseMoney,
				assignedMoney,
				assignedQuantity,
				assignmentRecord.isAssignedQuantityIncludedInSum());
		return assignmentToRefundCandidate;
	}

	public AssignmentToRefundCandidate save(@NonNull final AssignmentToRefundCandidate assignmentToRefundCandidate)
	{
		final RefundInvoiceCandidate refundInvoiceCandidate = assignmentToRefundCandidate.getRefundInvoiceCandidate();

		final I_C_Invoice_Candidate_Assignment assignmentRecord = newInstance(I_C_Invoice_Candidate_Assignment.class);

		assignmentRecord.setC_Invoice_Candidate_Assigned_ID(assignmentToRefundCandidate.getAssignableInvoiceCandidateId().getRepoId());
		assignmentRecord.setC_Flatrate_RefundConfig_ID(assignmentToRefundCandidate.getRefundConfigId().getRepoId());
		assignmentRecord.setC_Invoice_Candidate_Term_ID(refundInvoiceCandidate.getId().getRepoId());
		assignmentRecord.setC_Flatrate_Term_ID(refundInvoiceCandidate.getRefundContract().getId().getRepoId());
		assignmentRecord.setBaseMoneyAmount(assignmentToRefundCandidate.getMoneyBase().toBigDecimal());
		assignmentRecord.setAssignedMoneyAmount(assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate().toBigDecimal());
		assignmentRecord.setAssignedQuantity(assignmentToRefundCandidate.getQuantityAssigendToRefundCandidate().toBigDecimal());
		assignmentRecord.setIsAssignedQuantityIncludedInSum(assignmentToRefundCandidate.isUseAssignedQtyInSum());

		saveRecord(assignmentRecord);

		return assignmentToRefundCandidate;
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

		final InvoiceCandidateId removeForRefundCandidateId = request.getRemoveForRefundCandidateId();
		if (removeForRefundCandidateId != null)
		{
			invoiceCandidateIDsOrFilter.addEqualsFilter(
					I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID,
					removeForRefundCandidateId.getRepoId());
		}
		final InvoiceCandidateId removeForAssignedCandidateId = request.getRemoveForAssignedCandidateId();
		if (removeForAssignedCandidateId != null)
		{
			invoiceCandidateIDsOrFilter.addEqualsFilter(
					I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
					removeForAssignedCandidateId.getRepoId());
		}

		if (!request.getRefundConfigIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Flatrate_RefundConfig_ID, request.getRefundConfigIds());
		}

		queryBuilder
				.filter(invoiceCandidateIDsOrFilter)
				.create()
				.delete();
	}

	/**
	 * Note: {@link DeleteAssignmentsRequestBuilder#removeForAssignedCandidateId(InvoiceCandidateId)}
	 * and {@link DeleteAssignmentsRequestBuilder#removeForRefundCandidateId}
	 * are {@code OR}ed and at least one of them has to be no-null.
	 *
	 * RefundConfigIds is optional. If set at least one is specified, then only assignments with a matching refund config Id are deleted.
	 *
	 * OnlyActive is optional and {@code true} by default.
	 */
	@Value
	public static final class DeleteAssignmentsRequest
	{
		InvoiceCandidateId removeForRefundCandidateId;
		InvoiceCandidateId removeForAssignedCandidateId;

		List<RefundConfigId> refundConfigIds;

		boolean onlyActive;

		@Builder
		private DeleteAssignmentsRequest(
				@Nullable final InvoiceCandidateId removeForRefundCandidateId,
				@Nullable final InvoiceCandidateId removeForAssignedCandidateId,
				@Singular final List<RefundConfigId> refundConfigIds,
				@Nullable final Boolean onlyActive)
		{
			Check.errorIf(
					removeForRefundCandidateId == null
							&& removeForAssignedCandidateId == null,
					"At least one of the two invoiceCandidateId needs to be not-null");

			this.onlyActive = CoalesceUtil.coalesce(onlyActive, true);

			this.removeForRefundCandidateId = removeForRefundCandidateId;
			this.removeForAssignedCandidateId = removeForAssignedCandidateId;
			this.refundConfigIds = refundConfigIds;
		}
	}
}
