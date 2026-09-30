package de.metas.invoicecandidate.compensationGroup;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/*
 * #%L
 * de.metas.swat.base
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
 * Keeps the invoice candidates' compensation group ({@code C_Order_CompensationGroup_ID}) in sync with their order line's.
 */
@Service
public class InvoiceCandidateGroupService
{
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final InvoiceCandidateGroupRepository groupsRepo;
	private final InvoiceCandidateGroupCompensationChangesHandler groupChangesHandler;

	public InvoiceCandidateGroupService(@NonNull final InvoiceCandidateGroupRepository groupsRepo)
	{
		this.groupsRepo = groupsRepo;
		this.groupChangesHandler = InvoiceCandidateGroupCompensationChangesHandler.builder()
				.groupsRepo(groupsRepo)
				.build();
	}

	public static InvoiceCandidateGroupService newInstanceForUnitTesting()
	{
		return SpringContextHolder.getBeanOrSupply(
				InvoiceCandidateGroupService.class,
				() -> new InvoiceCandidateGroupService(new InvoiceCandidateGroupRepository(new GroupCompensationLineCreateRequestFactory())));
	}

	/**
	 * Moves the order line's invoice candidates to the order line's (new) group, or out of any group if {@code newGroupId} is {@code null}.
	 * Candidates that are not processed always follow; processed ones are left as-is, with one exception for contract-created groups:
	 * <p>
	 * A processed candidate with nothing invoiced follows if its old or its new group is contract-created:
	 * reactivating an order closes its candidates, and completing it again regroups its order lines <em>before</em>
	 * it reopens them, so skipping every processed candidate would leave such a candidate outside the rebuilt contract group.
	 * Non-contract groups keep skipping all processed candidates.
	 */
	public void syncGroupReferenceFromOrderLine(@NonNull final OrderLineId orderLineId, @Nullable final GroupId newGroupId)
	{
		final int newOrderCompensationGroupId = newGroupId != null ? newGroupId.getOrderCompensationGroupId() : -1;

		final ImmutableList<I_C_Invoice_Candidate> candidatesToMove = groupsRepo.retrieveInvoiceCandidatesOfOrderLine(orderLineId)
				.stream()
				.filter(ic -> !Objects.equals(extractGroupIdOrNull(ic), newGroupId))
				.collect(ImmutableList.toImmutableList());
		if (candidatesToMove.isEmpty())
		{
			return;
		}

		final ImmutableSet<GroupId> contractCreatedGroupIds = retrieveContractCreatedGroupIdsOfClosedNotInvoiced(candidatesToMove, newGroupId);

		for (final I_C_Invoice_Candidate ic : candidatesToMove)
		{
			if (ic.isProcessed() && !isClosedNotInvoicedCandidateOfContractGroup(ic, newGroupId, contractCreatedGroupIds))
			{
				continue;
			}

			ic.setC_Order_CompensationGroup_ID(newOrderCompensationGroupId);
			invoiceCandDAO.save(ic);

			// group change alone does not trigger the group recompute
			groupChangesHandler.onInvoiceCandidateChanged(ic);
		}
	}

	/**
	 * @return which of the old groups of the closed, not invoiced candidates and the new group are contract-created, in one query;
	 * empty (no query) if there is no such candidate
	 */
	private ImmutableSet<GroupId> retrieveContractCreatedGroupIdsOfClosedNotInvoiced(
			@NonNull final List<I_C_Invoice_Candidate> candidates,
			@Nullable final GroupId newGroupId)
	{
		final List<I_C_Invoice_Candidate> closedNotInvoiced = candidates.stream()
				.filter(InvoiceCandidateGroupService::isClosedNotInvoiced)
				.collect(ImmutableList.toImmutableList());
		if (closedNotInvoiced.isEmpty())
		{
			return ImmutableSet.of();
		}

		final ImmutableSet.Builder<GroupId> groupIdsToCheck = ImmutableSet.builder();
		closedNotInvoiced.stream()
				.map(InvoiceCandidateGroupService::extractGroupIdOrNull)
				.filter(Objects::nonNull)
				.forEach(groupIdsToCheck::add);
		if (newGroupId != null)
		{
			groupIdsToCheck.add(newGroupId);
		}

		return groupsRepo.retrieveContractCreatedGroupIds(groupIdsToCheck.build());
	}

	private static boolean isClosedNotInvoicedCandidateOfContractGroup(
			@NonNull final I_C_Invoice_Candidate ic,
			@Nullable final GroupId newGroupId,
			@NonNull final ImmutableSet<GroupId> contractCreatedGroupIds)
	{
		if (!isClosedNotInvoiced(ic))
		{
			return false;
		}

		final GroupId oldGroupId = extractGroupIdOrNull(ic);
		return (oldGroupId != null && contractCreatedGroupIds.contains(oldGroupId))
				|| (newGroupId != null && contractCreatedGroupIds.contains(newGroupId));
	}

	private static boolean isClosedNotInvoiced(@NonNull final I_C_Invoice_Candidate ic)
	{
		return ic.isProcessed() && ic.getQtyInvoiced().signum() == 0;
	}

	@Nullable
	private static GroupId extractGroupIdOrNull(@NonNull final I_C_Invoice_Candidate ic)
	{
		final int orderCompensationGroupId = ic.getC_Order_CompensationGroup_ID();
		return orderCompensationGroupId > 0
				? OrderGroupRepository.createGroupId(OrderId.ofRepoId(ic.getC_Order_ID()), orderCompensationGroupId)
				: null;
	}
}
