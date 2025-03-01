package de.metas.money.grossprofit;

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
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

@Service
public class ProfitPriceActualFactory
{
	private final ImmutableList<ProfitPriceActualComponentProvider> providers;

	public ProfitPriceActualFactory(@NonNull final Optional<List<ProfitPriceActualComponentProvider>> providers)
	{
		this.providers = ImmutableList.copyOf(providers.orElseGet(ImmutableList::of));
	}

	public Money calculateProfitPriceActual(@NonNull final CalculateProfitPriceActualRequest request)
	{
		final ProfitPriceActualCalculator.ProfitPriceActualCalculatorBuilder builder = ProfitPriceActualCalculator
				.builder()
				.basePrice(request.getBaseAmount());

		for (final ProfitPriceActualComponentProvider provider : providers)
		{
			builder.profitPriceActualComponent(provider.provideForRequest(request));
		}

		final ProfitPriceActualCalculator calculator = builder.build();
		return calculator.getProfitPriceActual();
	}
}
