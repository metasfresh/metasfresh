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

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.IPOLineProjectPropagator;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A component responsible for propagating a project ID from purchase order lines to their corresponding sales order lines.
 * <p>
 * This propagation ensures that when a project is associated with purchase order lines, the related sales order lines
 * (determined through {@link PurchaseCandidate}) are updated to reflect the same project ID.
 */
@Component
@RequiredArgsConstructor
public class POLineProjectPropagator implements IPOLineProjectPropagator
{
	@NonNull private final PurchaseCandidateRepository purchaseCandidateRepo;
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@Override
	public void propagateProjectId(@NonNull final ProjectId projectId, @NonNull final Collection<OrderAndLineId> purchaseOrderLineIds)
	{
		final Set<OrderAndLineId> salesOrderAndLineIds = purchaseCandidateRepo.getAllByPurchaseOrderLineIds(purchaseOrderLineIds)
				.stream()
				.map(PurchaseCandidate::getSalesOrderAndLineIdOrNull)
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		final Set<OrderId> salesOrderIds = OrderAndLineId.getOrderIds(salesOrderAndLineIds);
		final Set<OrderLineId> salesOrderLineIds = OrderAndLineId.getOrderLineIds(salesOrderAndLineIds);

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLinesByOrderIds(salesOrderIds);

		//persist the ProjectId on all related order lines
		final Set<I_C_OrderLine> updatedOrderLines = orderLines.stream()
				.filter(orderLine -> salesOrderLineIds.contains(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID())))
				.peek(orderLine -> orderLine.setC_Project_ID(projectId.getRepoId()))
				.collect(Collectors.toSet());
		InterfaceWrapperHelper.saveAll(updatedOrderLines);

	}
}
