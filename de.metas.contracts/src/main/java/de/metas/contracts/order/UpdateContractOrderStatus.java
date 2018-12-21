/**
 *
 */
package de.metas.contracts.order;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.collect.ImmutableSet;

import de.metas.contracts.IContractsDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;

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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class UpdateContractOrderStatus
{
	private final ContractOrderService contractOrderService;
	private final IOrderDAO orderDAO;
	private final IContractsDAO contractsDAO;
	private final ISubscriptionBL subscriptionBL;

	@Builder
	public UpdateContractOrderStatus(
			@NonNull final ContractOrderService contractOrderService,
			@NonNull final IOrderDAO orderDAO,
			@NonNull final IContractsDAO contractsDAO,
			@NonNull final ISubscriptionBL subscriptionBL)
	{
		this.contractOrderService = contractOrderService;
		this.orderDAO = orderDAO;
		this.contractsDAO = contractsDAO;
		this.subscriptionBL = subscriptionBL;
	}

	public void updateStatusIfNeededWhenExtendind(final I_C_Flatrate_Term term, final Set<OrderId> orderIds)
	{
		if (InterfaceWrapperHelper.isValueChanged(term, I_C_Flatrate_Term.COLUMNNAME_C_FlatrateTerm_Next_ID)
				&& term.getC_FlatrateTerm_Next_ID() > 0
				&& orderIds.size() > 1) // we set the Extended status only when an order was extended
		{
			// update order contract status to extended
			final OrderId currentContractOrderId = contractOrderService.getContractOrderId(term);
			final I_C_Order order = orderDAO.getById(currentContractOrderId, I_C_Order.class);
			contractOrderService.setOrderContractStatusAndSave(order, I_C_Order.CONTRACTSTATUS_Extended);
		}
	}

	public void updateStatusIfNeededWhenCancelling(final I_C_Flatrate_Term term, final Set<OrderId> orderIds)
	{
		if (X_C_Flatrate_Term.CONTRACTSTATUS_EndingContract.equals(term.getContractStatus())
				|| X_C_Flatrate_Term.CONTRACTSTATUS_Quit.equals(term.getContractStatus()))
		{
			// update order contract status to cancel
			final List<I_C_Order> orders = orderDAO.getByIds(orderIds, I_C_Order.class);
			for (final I_C_Order order : orders)
			{
				contractOrderService.setOrderContractStatusAndSave(order, I_C_Order.CONTRACTSTATUS_Cancelled);
			}
		}
	}

	public void updateStausIfNeededWhenVoiding(@NonNull final I_C_Flatrate_Term term)
	{
		final OrderId orderId = contractOrderService.getContractOrderId(term);
		if (orderId == null
				|| !X_C_Flatrate_Term.CONTRACTSTATUS_Voided.equals(term.getContractStatus()))
		{
			return;
		}

		final OrderId parentOrderId = contractOrderService.retrieveLinkedFollowUpContractOrder(orderId);
		final ImmutableSet<OrderId> orderIds = parentOrderId == null ? ImmutableSet.of(orderId) : ImmutableSet.of(orderId, parentOrderId);
		final List<I_C_Order> orders = orderDAO.getByIds(orderIds, I_C_Order.class);

		final I_C_Order contractOrder = orders.get(0);
		setContractStatusForCurrentOrder(contractOrder, term);
		setContractStatusForParentOrderIfNeeded(orders);
	}

	private void setContractStatusForCurrentOrder(@NonNull final I_C_Order contractOrder, @NonNull final I_C_Flatrate_Term term)
	{
		// set status for the current order
		final List<I_C_Flatrate_Term> terms = contractsDAO.retrieveFlatrateTermsForOrderId(OrderId.ofRepoId(contractOrder.getC_Order_ID()));
		final boolean anyActiveTerms = terms
				.stream()
				.anyMatch(currentTerm -> term.getC_Flatrate_Term_ID() != currentTerm.getC_Flatrate_Term_ID()
						&& subscriptionBL.isActiveTerm(currentTerm));

		contractOrderService.setOrderContractStatusAndSave(contractOrder, anyActiveTerms ? I_C_Order.CONTRACTSTATUS_Active : I_C_Order.CONTRACTSTATUS_Cancelled);
	}

	private void setContractStatusForParentOrderIfNeeded(final List<I_C_Order> orders)
	{
		if (orders.size() == 1) // means that the order does not have parent
		{
			return;
		}

		final I_C_Order contractOrder = orders.get(0);
		final I_C_Order parentOrder = orders.get(1);

		if (isActiveParentContractOrder(parentOrder, contractOrder))
		{
			contractOrderService.setOrderContractStatusAndSave(parentOrder, I_C_Order.CONTRACTSTATUS_Active);
		}
	}

	private boolean isActiveParentContractOrder(@NonNull final I_C_Order parentOrder, @NonNull final I_C_Order contractOrder)
	{
		return parentOrder.getC_Order_ID() != contractOrder.getC_Order_ID()  // different order from the current one
				&& !I_C_Order.CONTRACTSTATUS_Cancelled.equals(parentOrder.getContractStatus()) // current order wasn't previously cancelled, although shall not be possible this
				&& I_C_Order.CONTRACTSTATUS_Cancelled.equals(contractOrder.getContractStatus()); // current order was cancelled
	}

}
