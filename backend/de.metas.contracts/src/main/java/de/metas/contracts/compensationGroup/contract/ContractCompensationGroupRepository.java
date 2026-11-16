package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

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
 * schema), this one is scoped to contract-created groups (see {@link OrderGroupRepository#createContractCreatedGroupsQueryBuilder}),
 * except for {@link #retrieveActiveRegularOrderLines}, which reads every regular line
 * of the order regardless of group. The groups' invoice candidates are handled by
 * {@code InvoiceCandidateGroupRepository}.
 * <p>
 * Persistence primitives for a contract-created {@code C_Order_CompensationGroup}: stamping the term it came
 * from, the position on its discount lines, and the plumbing {@link ContractCompensationGroupService} needs to remove one again.
 */
@Repository
public class ContractCompensationGroupRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/** @return the {@link GroupId}s of {@code orderId}'s contract-created groups */
	public List<GroupId> retrieveContractGroupIds(@NonNull final OrderId orderId)
	{
		return OrderGroupRepository.createContractCreatedGroupsQueryBuilder()
				.addEqualsFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_ID, orderId)
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
	 * Sets the given position ({@code C_OrderLine.C_Project_ID}) on the given group's compensation (discount) lines,
	 * so that each line carries the position its group's goods are on (also when {@code projectId} is {@code null}).
	 */
	public void setProjectOnCompensationLines(@NonNull final GroupId groupId, @Nullable final ProjectId projectId)
	{
		setProjectOnCompensationLines(groupId, projectId, compensationLine -> true);
	}

	/**
	 * Like {@link #setProjectOnCompensationLines(GroupId, ProjectId)}, but only on the compensation lines accepted by {@code lineFilter}.
	 */
	public void setProjectOnCompensationLines(
			@NonNull final GroupId groupId,
			@Nullable final ProjectId projectId,
			@NonNull final Predicate<I_C_OrderLine> lineFilter)
	{
		final OrderId orderId = OrderGroupRepository.extractOrderIdFromGroupId(groupId);
		final List<I_C_OrderLine> compensationLines = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID, groupId.getOrderCompensationGroupId())
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, true)
				.create()
				.list(I_C_OrderLine.class);

		for (final I_C_OrderLine compensationLine : compensationLines)
		{
			if (ProjectId.equals(ProjectId.ofRepoIdOrNull(compensationLine.getC_Project_ID()), projectId) || !lineFilter.test(compensationLine))
			{
				continue;
			}
			compensationLine.setC_Project_ID(ProjectId.toRepoId(projectId));
			InterfaceWrapperHelper.save(compensationLine);
		}
	}

	/** @return the given group's active goods lines: neither compensation (discount) lines nor packing material lines */
	public List<I_C_OrderLine> retrieveActiveGoodsLines(@NonNull final GroupId groupId)
	{
		final OrderId orderId = OrderGroupRepository.extractOrderIdFromGroupId(groupId);
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID, groupId.getOrderCompensationGroupId())
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, false)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsPackagingMaterial, false)
				.create()
				.list(I_C_OrderLine.class);
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
		final IQuery<I_C_Order_CompensationGroup> contractGroupsQuery = OrderGroupRepository.createContractCreatedGroupsQueryBuilder().create();

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
	 * that separately via {@code OrderGroupCompensationUtils.isNotInGroup(...)}), ordered by {@code Line}, then {@code C_OrderLine_ID}
	 */
	public List<I_C_OrderLine> retrieveActiveRegularOrderLines(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, false)
				// in line order: the positions' groups, and so their Bonus lines, follow the order of their goods lines
				.orderBy(I_C_OrderLine.COLUMNNAME_Line)
				.orderBy(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
				.create()
				.list(I_C_OrderLine.class);
	}
}
