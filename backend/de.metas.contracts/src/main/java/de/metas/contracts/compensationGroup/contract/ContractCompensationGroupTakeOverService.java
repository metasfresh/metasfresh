package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import de.metas.order.OrderId;
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
 * Computes, for a drop-ship purchase order, which of the vendor's take-over records apply and how many percent
 * of the linked sales order's contract discount lines each takes over.
 */
@Service
@RequiredArgsConstructor
public class ContractCompensationGroupTakeOverService
{
	@NonNull private final ContractCompensationGroupSettingsRepository settingsRepository;
	@NonNull private final ContractCompensationGroupRepository contractGroupRepository;

	/**
	 * @return one result per take-over record of {@code settings} whose listed customer products appear on the linked
	 * sales order's contract discount lines (summedPercent = arithmetic sum of their nominal percentages, not compounded);
	 * empty unless {@code purchaseOrder} is a drop-ship order linked to a sales order. Records summing to 0 are dropped.
	 */
	public List<TakeOverResult> computeTakeOvers(
			@NonNull final I_C_Order purchaseOrder,
			@NonNull final ContractCompensationGroupSettings settings)
	{
		if (!purchaseOrder.isDropShip() || purchaseOrder.getLink_Order_ID() <= 0)
		{
			return ImmutableList.of();
		}

		final List<LinkedContractDiscountLine> salesOrderLines = contractGroupRepository
				.linkedSalesOrderContractDiscountLines(OrderId.ofRepoId(purchaseOrder.getLink_Order_ID()));

		return settingsRepository.getTakeOverRecords(settings.getSettingsId())
				.stream()
				.map(record -> new TakeOverResult(record, sumListedPercent(record, salesOrderLines)))
				.filter(result -> !result.getSummedPercent().isZero())
				.collect(ImmutableList.toImmutableList());
	}

	private static Percent sumListedPercent(final TakeOverRecord record, final List<LinkedContractDiscountLine> salesOrderLines)
	{
		Percent sum = Percent.ZERO;
		for (final LinkedContractDiscountLine line : salesOrderLines)
		{
			if (record.getListedCustomerProductIds().contains(line.getDiscountProductId()))
			{
				sum = sum.add(line.getNominalPercentage());
			}
		}
		return sum;
	}
}
