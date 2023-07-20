/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.bpartner.service;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.bpartner.repository.MembershipContractRepository;
import de.metas.order.OrderId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class MembershipOrderService
{
	private final MembershipContractRepository membershipContractRepo;

	public MembershipOrderService(@NonNull final MembershipContractRepository membershipContractRepo)
	{
		this.membershipContractRepo = membershipContractRepo;
	}

	public void createMembershipContractOrders(@NonNull MembershipOrderCreateRequest request)
	{
		final ImmutableSet<OrderId> orderIds = membershipContractRepo
				.retrieveMembershipOrderIds(request.getOrgId(), request.getStartDate());

		for (final OrderId orderId : orderIds)
		{
			MembershipOrderCreateCommand.builder()
					.existingMembershipOrderId(orderId)
					.productId(request.getProductId())
					.conditionsId(request.getConditionsID())
					.build()
					.execute();
		}
	}

}
