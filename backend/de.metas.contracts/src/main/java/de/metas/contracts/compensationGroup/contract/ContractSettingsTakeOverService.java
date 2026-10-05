package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import java.util.List;

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
 * Computes, for a drop-ship purchase order, which of the vendor's take-overs apply and how many percent
 * of the linked sales order's contract discount lines each takes over.
 */
@Service
@RequiredArgsConstructor
public class ContractSettingsTakeOverService
{
	@NonNull private final ContractSettingsTakeOverRepository takeOverRepository;
	@NonNull private final OrderGroupRepository orderGroupRepository;

	/**
	 * @return one match per take-over of {@code settings} whose listed customer products appear on the linked
	 * sales order's contract-created percentage discount lines (summedPercent = arithmetic sum of their nominal percentages,
	 * not compounded); empty unless {@code purchaseOrder} is a drop-ship <b>purchase</b> order linked to a sales order.
	 * Take-overs summing to 0 are dropped.
	 */
	public List<ContractSettingsTakeOverMatch> computeMatches(
			@NonNull final I_C_Order purchaseOrder,
			@NonNull final ContractCompensationGroupSettings settings)
	{
		final OrderId linkedSalesOrderId = OrderId.ofRepoIdOrNull(purchaseOrder.getLink_Order_ID());
		if (purchaseOrder.isSOTrx() || !purchaseOrder.isDropShip() || linkedSalesOrderId == null)
		{
			return ImmutableList.of();
		}

		final ImmutableList<GroupCompensationLine> salesOrderDiscountLines = orderGroupRepository.retrieveContractCreatedGroupsByOrderId(linkedSalesOrderId)
				.stream()
				.flatMap(group -> group.getCompensationLines().stream())
				.filter(ContractSettingsTakeOverService::isPercentDiscount)
				.collect(ImmutableList.toImmutableList());

		return takeOverRepository.getBySettingsId(settings.getSettingsId())
				.stream()
				.map(takeOver -> computeMatch(takeOver, salesOrderDiscountLines))
				.filter(match -> !match.getSummedPercent().isZero())
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean isPercentDiscount(@NonNull final GroupCompensationLine line)
	{
		return line.getType() == GroupCompensationType.Discount && line.getAmtType() == GroupCompensationAmtType.Percent;
	}

	private static ContractSettingsTakeOverMatch computeMatch(
			@NonNull final ContractSettingsTakeOver takeOver,
			@NonNull final List<GroupCompensationLine> salesOrderDiscountLines)
	{
		Percent sum = Percent.ZERO;
		final ImmutableSet.Builder<ProductId> takenOverProductIds = ImmutableSet.builder();
		for (final GroupCompensationLine line : salesOrderDiscountLines)
		{
			if (takeOver.getListedCustomerProductIds().contains(line.getProductId()) && !line.getPercentage().isZero())
			{
				sum = sum.add(line.getPercentage());
				takenOverProductIds.add(line.getProductId());
			}
		}
		return new ContractSettingsTakeOverMatch(takeOver, sum, takenOverProductIds.build());
	}
}
