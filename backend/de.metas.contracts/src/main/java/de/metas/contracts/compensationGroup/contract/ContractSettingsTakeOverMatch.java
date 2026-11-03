package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;

import java.math.RoundingMode;

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
 * A take-over record together with the nominal percentages it takes over from the linked sales order, one per
 * contract discount line, and the qualifying refund contracts of its listed refund conditions (not both empty).
 */
@Value
public class ContractSettingsTakeOverMatch
{
	@NonNull ContractSettingsTakeOver takeOver;
	@NonNull ImmutableList<TakenOverPercentage> takenOverPercentages;
	@NonNull ImmutableList<TakenOverRefund> takenOverRefunds;

	public ContractSettingsTakeOverMatch(
			@NonNull final ContractSettingsTakeOver takeOver,
			@NonNull final ImmutableList<TakenOverPercentage> takenOverPercentages,
			@NonNull final ImmutableList<TakenOverRefund> takenOverRefunds)
	{
		Check.assume(!takenOverPercentages.isEmpty() || !takenOverRefunds.isEmpty(), "takenOverPercentages or takenOverRefunds is not empty");
		this.takeOver = takeOver;
		this.takenOverPercentages = takenOverPercentages;
		this.takenOverRefunds = takenOverRefunds;
	}

	/** The nominal sum of the taken-over contract discount percentages and refund percentages, unrounded. */
	public Percent getSummedPercent()
	{
		final Percent contractDiscounts = takenOverPercentages.stream()
				.map(TakenOverPercentage::getPercent)
				.reduce(Percent.ZERO, Percent::add);
		return takenOverRefunds.stream()
				.map(TakenOverRefund::getPercent)
				.reduce(contractDiscounts, Percent::add);
	}

	/** The summed percentage, rounded half up to the take-over record's decimals (only the take-over line is rounded). */
	public Percent getRoundedSummedPercent()
	{
		return Percent.of(getSummedPercent().toBigDecimal().setScale(takeOver.getPercentagePrecision(), RoundingMode.HALF_UP));
	}

	public ImmutableSet<ProductId> getTakenOverProductIds()
	{
		return takenOverPercentages.stream()
				.map(TakenOverPercentage::getCustomerDiscountProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/** The nominal percentage of one contract discount line of the linked sales order. */
	@Value(staticConstructor = "of")
	public static class TakenOverPercentage
	{
		@NonNull ProductId customerDiscountProductId;
		@NonNull Percent percent;
	}
}
