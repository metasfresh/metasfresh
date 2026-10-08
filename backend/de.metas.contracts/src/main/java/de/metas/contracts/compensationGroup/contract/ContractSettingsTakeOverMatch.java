package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;

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
 * contract discount line (never empty).
 */
@Value
public class ContractSettingsTakeOverMatch
{
	@NonNull ContractSettingsTakeOver takeOver;
	@NonNull ImmutableList<TakenOverPercentage> takenOverPercentages;

	public ContractSettingsTakeOverMatch(
			@NonNull final ContractSettingsTakeOver takeOver,
			@NonNull final ImmutableList<TakenOverPercentage> takenOverPercentages)
	{
		Check.assumeNotEmpty(takenOverPercentages, "takenOverPercentages is not empty");
		this.takeOver = takeOver;
		this.takenOverPercentages = takenOverPercentages;
	}

	public Percent getSummedPercent()
	{
		return takenOverPercentages.stream()
				.map(TakenOverPercentage::getPercent)
				.reduce(Percent.ZERO, Percent::add);
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
