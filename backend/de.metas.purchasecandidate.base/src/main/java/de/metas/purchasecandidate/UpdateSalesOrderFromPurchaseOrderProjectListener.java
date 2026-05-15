/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.purchasecandidate;

import com.google.common.collect.ImmutableSet;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.PurchaseOrderProjectListener;
import de.metas.project.ProjectId;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_PO_OrderLine_Alloc;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A component responsible for propagating a project ID from purchase order lines to their corresponding sales order lines.
 * <p>
 * This propagation ensures that when a project is associated with purchase order lines, the related sales order lines
 * (determined through {@link PurchaseCandidate}) are updated to reflect the same project ID.
 * Since this is triggered via the EventBus, the environment needs to be switched to the user who triggered the event.
 */
@Component
@RequiredArgsConstructor
public class UpdateSalesOrderFromPurchaseOrderProjectListener implements PurchaseOrderProjectListener
{
	@NonNull private final PurchaseCandidateRepository purchaseCandidateRepo;
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@Override
	public void onCreated(@NonNull final ProjectCreatedEvent event)
	{
		@NonNull final ProjectId projectId = event.getProjectId();
		@NonNull final Collection<OrderAndLineId> purchaseOrderLineIds = event.getPurchaseOrderLineIds();

		Set<OrderAndLineId> salesOrderAndLineIds = purchaseCandidateRepo.getAllByPurchaseOrderLineIds(purchaseOrderLineIds)
				.stream()
				.map(PurchaseCandidate::getSalesOrderAndLineIdOrNull)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		// Fall back to C_PO_OrderLine_Alloc for PO lines not linked to a C_PurchaseCandidate.
		// Dropship POs created via CreatePOFromSOsAggregator skip the candidate flow, so the
		// allocation table is the only link between PO line and originating SO line for them.
		final Set<OrderLineId> allInputPoLineIds = purchaseOrderLineIds.stream()
				.map(OrderAndLineId::getOrderLineId)
				.collect(Collectors.toCollection(HashSet::new));

		final Set<OrderLineId> matchedPoLineIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PurchaseCandidate_Alloc.class)
				.addInArrayFilter(I_C_PurchaseCandidate_Alloc.COLUMNNAME_C_OrderLinePO_ID, allInputPoLineIds)
				.create()
				.listImmutable(I_C_PurchaseCandidate_Alloc.class)
				.stream()
				.map(alloc -> OrderLineId.ofRepoIdOrNull(alloc.getC_OrderLinePO_ID()))
				.filter(Objects::nonNull)
				.collect(Collectors.toCollection(HashSet::new));

		final Set<OrderLineId> unmatchedPoLineIds = new HashSet<>(allInputPoLineIds);
		unmatchedPoLineIds.removeAll(matchedPoLineIds);

		if (!unmatchedPoLineIds.isEmpty())
		{
			final Set<OrderLineId> extraSoLineIds = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_PO_OrderLine_Alloc.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_C_PO_OrderLine_Alloc.COLUMNNAME_C_PO_OrderLine_ID, unmatchedPoLineIds)
					.create()
					.listImmutable(I_C_PO_OrderLine_Alloc.class)
					.stream()
					.map(alloc -> OrderLineId.ofRepoIdOrNull(alloc.getC_SO_OrderLine_ID()))
					.filter(Objects::nonNull)
					.collect(Collectors.toCollection(HashSet::new));

			if (!extraSoLineIds.isEmpty())
			{
				final Set<OrderAndLineId> extraSalesOrderAndLineIds = orderDAO.retrieveOrderLinesByIds(extraSoLineIds)
						.stream()
						.map(soLine -> OrderAndLineId.ofRepoIdsOrNull(soLine.getC_Order_ID(), soLine.getC_OrderLine_ID()))
						.filter(Objects::nonNull)
						.collect(Collectors.toSet());

				salesOrderAndLineIds = ImmutableSet.<OrderAndLineId>builder()
						.addAll(salesOrderAndLineIds)
						.addAll(extraSalesOrderAndLineIds)
						.build();
			}
		}

		final Set<OrderId> salesOrderIds = OrderAndLineId.getOrderIds(salesOrderAndLineIds);
		final Set<OrderLineId> salesOrderLineIds = OrderAndLineId.getOrderLineIds(salesOrderAndLineIds);

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLinesByOrderIds(salesOrderIds);

		//persist the ProjectId on all related order lines
		final Set<I_C_OrderLine> updatedOrderLines = orderLines.stream()
				.filter(orderLine -> salesOrderLineIds.contains(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID())))
				.filter(orderLine -> ProjectId.ofRepoIdOrNull(orderLine.getC_Project_ID()) == null)
				.peek(orderLine -> orderLine.setC_Project_ID(projectId.getRepoId()))
				.collect(Collectors.toSet());
		saveAllForUserId(event, updatedOrderLines);
	}

	private void saveAllForUserId(@NonNull final ProjectCreatedEvent event, @NonNull final Set<I_C_OrderLine> updatedOrderLines)
	{
		if (updatedOrderLines.isEmpty())
		{
			return;
		}
		final I_C_OrderLine firstOrderLine = CollectionUtils.first(updatedOrderLines);
		final Properties properties = Env.copyCtx(InterfaceWrapperHelper.getCtx(firstOrderLine, true));
		Env.setLoggedUserId(properties, event.getByUserId());
		try (final IAutoCloseable ignored = Env.switchContext(properties))
		{
			InterfaceWrapperHelper.saveAll(updatedOrderLines);
		}
	}
}
