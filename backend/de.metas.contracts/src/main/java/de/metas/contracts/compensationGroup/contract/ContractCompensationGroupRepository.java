package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.FlatrateTermId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ForUpdate;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Repository Tables: C_Order_CompensationGroup, C_OrderLine
 * <p>
 * Repository Cluster: ContractCompensationGroupRepository, {@link OrderGroupRepository} — both
 * write these two tables; {@link OrderGroupRepository} is the generic compensation-group repo (any
 * schema), this one is scoped to contract-created groups (identified by {@code C_Flatrate_Term_ID}
 * being set), except for {@link #retrieveActiveRegularOrderLines}, which reads every regular line
 * of the order regardless of group.
 * <p>
 * Persistence primitives for a contract-created {@code C_Order_CompensationGroup}: stamping the term it came
 * from, and the plumbing {@link ContractCompensationGroupService} needs to remove one again.
 */
@Repository
public class ContractCompensationGroupRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** Stamps the group header with the contract term it was (re)created from. */
	public void setFlatrateTerm(@NonNull final GroupId groupId, @NonNull final FlatrateTermId termId)
	{
		final I_C_Order_CompensationGroup groupRecord = load(groupId.getOrderCompensationGroupId(), I_C_Order_CompensationGroup.class);
		groupRecord.setC_Flatrate_Term_ID(termId.getRepoId());
		saveRecord(groupRecord);
	}

	/** @return the {@link GroupId}s of {@code orderId}'s contract-created groups (those with a {@code C_Flatrate_Term_ID} set) */
	public List<GroupId> retrieveContractGroupIds(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_Order_CompensationGroup.class)
				.addEqualsFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_ID, orderId)
				.addCompareFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Flatrate_Term_ID, Operator.GREATER, 0)
				.create()
				.listIds()
				.stream()
				.map(orderCompensationGroupId -> OrderGroupRepository.createGroupId(orderId, orderCompensationGroupId))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * @return {@code true} if any compensation (discount) line of the given order's contract-created groups
	 * carries a non-zero {@code QtyInvoiced} — the condition reactivation is refused for.
	 */
	public boolean hasInvoicedContractGroupLines(@NonNull final OrderId orderId)
	{
		final List<Integer> contractGroupRepoIds = retrieveContractGroupIds(orderId)
				.stream()
				.map(GroupId::getOrderCompensationGroupId)
				.collect(ImmutableList.toImmutableList());
		if (contractGroupRepoIds.isEmpty())
		{
			return false;
		}

		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addInArrayFilter(I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID, contractGroupRepoIds)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, true)
				.addCompareFilter(I_C_OrderLine.COLUMNNAME_QtyInvoiced, Operator.NOT_EQUAL, BigDecimal.ZERO)
				.create()
				.anyMatch();
	}

	/**
	 * Locks the order's invoice candidates in the order in which the invoice-candidate recompute takes them
	 * ({@code InvoiceCandDAO.fetchInvalidInvoiceCandidates}: IsFreightCost, IsManual, C_Invoice_Candidate_ID).
	 * <p>
	 * Removing a group touches its candidates in a different order (discount line first, then the regular lines); without locking them
	 * upfront, a recompute running at the same time deadlocks with it and one of the two candidate updates is lost.
	 */
	public void lockInvoiceCandidatesOfOrder(@NonNull final OrderId orderId)
	{
		queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, orderId)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_IsFreightCost)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_IsManual)
				.orderBy(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID)
				.create()
				.setForUpdate(ForUpdate.FOR_NO_KEY_UPDATE)
				.listIds();
	}

	/**
	 * Removes the group reference from the group's not yet invoiced invoice candidates, also from those its order lines don't reach
	 * (e.g. a candidate committed by another transaction after its order line was ungrouped), so that the group header can be deleted.
	 * Invoiced candidates keep it; the reactivation is refused for them before (see {@link #hasInvoicedContractGroupLines}).
	 */
	public void ungroupNotInvoicedInvoiceCandidates(@NonNull final GroupId groupId)
	{
		queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID, OrderGroupRepository.extractOrderIdFromGroupId(groupId))
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Order_CompensationGroup_ID, groupId.getOrderCompensationGroupId())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced, BigDecimal.ZERO)
				.create()
				.update(invoiceCandidate -> {
					invoiceCandidate.setC_Order_CompensationGroup_ID(-1);
					return IQueryUpdater.MODEL_UPDATED;
				});
	}

	/** Deletes the given group's compensation (discount) lines, leaving its regular lines ungrouped so {@link OrderGroupRepository#retrieveGroupIfExists} can rebuild it. */
	public void deleteCompensationLines(@NonNull final GroupId groupId)
	{
		final OrderId orderId = OrderGroupRepository.extractOrderIdFromGroupId(groupId);
		queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID, groupId.getOrderCompensationGroupId())
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, true)
				.create()
				.delete();
	}

	/**
	 * @return a filter matching {@code C_OrderLine}s that ARE contract-created compensation (discount) lines —
	 * {@code IsGroupCompensationLine=Y} whose group carries a {@code C_Flatrate_Term_ID}. Callers needing the
	 * opposite (e.g. {@code MainValidator}, to keep such a line from being copied onto a purchase order) call
	 * {@link IQueryFilter#negate()} on the result directly — the composite filter built here is pure SQL
	 * ({@code EqualsFilter} + {@code InSubQueryFilter}, both {@code ISqlQueryFilter}), and {@code negate()}
	 * (via {@code NotQueryFilter}) stays SQL-translatable too, so it must never be wrapped in a plain
	 * {@code IQueryFilter} adapter — that would force in-memory evaluation and let {@code InSubQueryFilter}
	 * cache a stale result for as long as the caller holds the wrapper (see the registration site's comment).
	 */
	public IQueryFilter<I_C_OrderLine> createContractCompensationLineMatcher()
	{
		final IQuery<I_C_Order_CompensationGroup> contractGroupsQuery = queryBL.createQueryBuilder(I_C_Order_CompensationGroup.class)
				.addCompareFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Flatrate_Term_ID, Operator.GREATER, 0)
				.create();

		return queryBL.createCompositeQueryFilter(I_C_OrderLine.class)
				.setJoinAnd()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, true)
				.addInSubQueryFilter(
						I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID,
						I_C_Order_CompensationGroup.COLUMNNAME_C_Order_CompensationGroup_ID,
						contractGroupsQuery);
	}

	/**
	 * @return this order's active, non-compensation regular lines (candidates for a new group — NOT filtered
	 * by group membership; a line already in a different group is still returned here, and the caller filters
	 * that separately via {@code OrderGroupCompensationUtils.isNotInGroup(...)})
	 */
	public List<I_C_OrderLine> retrieveActiveRegularOrderLines(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, false)
				.create()
				.list(I_C_OrderLine.class);
	}
}
