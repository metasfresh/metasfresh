package de.metas.contracts.refund;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment_Aggregate_V;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.util.Services;
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
 * Provides access to {@link I_C_Invoice_Candidate_Assignment_Aggregate_V}.
 * Note that that view is there for performance and plays not really any role in the domain model.
 *
 */
@Service
public class AssignmentAggregateService
{
	private final RefundConfigRepository refundConfigRepository;

	public AssignmentAggregateService(@NonNull final RefundConfigRepository refundConfigRepository)
	{
		this.refundConfigRepository = refundConfigRepository;
	}

	public BigDecimal retrieveMoneyAmount(@NonNull final InvoiceCandidateId refundCandidateId)
	{
		if (Adempiere.isUnitTestMode())
		{
			return retrieveMoneyAmountUnitTestMode(refundCandidateId);
		}
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_C_Invoice_Candidate_Assignment_Aggregate_V> aggregates = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment_Aggregate_V.class)
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment_Aggregate_V.COLUMN_C_Invoice_Candidate_Term_ID, refundCandidateId)
				.create() // note: the view has no IsActive column, but its whereclause filters by isactive
				.list(I_C_Invoice_Candidate_Assignment_Aggregate_V.class);

		final BigDecimal newMoneyAmount = aggregates
				.stream()
				.map(I_C_Invoice_Candidate_Assignment_Aggregate_V::getAssignedMoneyAmount)
				.reduce(ZERO, BigDecimal::add);
		return newMoneyAmount;
	}

	private BigDecimal retrieveMoneyAmountUnitTestMode(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_C_Invoice_Candidate_Assignment> assignmentRecords = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID, invoiceCandidateId)
				.create()
				.list();

		BigDecimal result = ZERO;
		for (final I_C_Invoice_Candidate_Assignment assignmentRecord : assignmentRecords)
		{
			result = result.add(assignmentRecord.getAssignedMoneyAmount());
		}

		return result;
	}

	public Map<RefundConfig, BigDecimal> retrieveAssignedQuantities(
			@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		if (Adempiere.isUnitTestMode())
		{
			return retrieveAssignedQuantityUnitTestMode(invoiceCandidateId);
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_C_Invoice_Candidate_Assignment_Aggregate_V> aggregates = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment_Aggregate_V.class)
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment_Aggregate_V.COLUMN_C_Invoice_Candidate_Term_ID, invoiceCandidateId)
				.create() // note: the view has no IsActive column, but its whereclause filters by isactive
				.list(I_C_Invoice_Candidate_Assignment_Aggregate_V.class);

		final ImmutableMap.Builder<RefundConfig, BigDecimal> result = ImmutableMap.builder();
		for (final I_C_Invoice_Candidate_Assignment_Aggregate_V aggregate : aggregates)
		{
			final RefundConfigId refundConfigId = RefundConfigId.ofRepoId(aggregate.getC_Flatrate_RefundConfig_ID());
			final RefundConfig refundConfig = refundConfigRepository.getById(refundConfigId);

			result.put(
					refundConfig,
					aggregate.getAssignedQuantity());
		}

		return result.build();
	}

	private Map<RefundConfig, BigDecimal> retrieveAssignedQuantityUnitTestMode(
			@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_C_Invoice_Candidate_Assignment> assignmentRecords = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID, invoiceCandidateId)
				.create()
				.list();

		final Map<RefundConfig, BigDecimal> result = new HashMap<>();
		for (final I_C_Invoice_Candidate_Assignment assignmentRecord : assignmentRecords)
		{
			final RefundConfigId refundConfigId = RefundConfigId.ofRepoId(assignmentRecord.getC_Flatrate_RefundConfig_ID());
			final RefundConfig refundConfig = refundConfigRepository.getById(refundConfigId);

			final BigDecimal assignedQuantity = assignmentRecord.isAssignedQuantityIncludedInSum()
					? assignmentRecord.getAssignedQuantity()
					: ZERO;
			result.merge(refundConfig, assignedQuantity, (oldVal, newVal) -> oldVal.add(newVal));
		}

		return result;
	}
}
