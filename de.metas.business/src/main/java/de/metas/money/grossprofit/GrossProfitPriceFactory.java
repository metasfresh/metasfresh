package de.metas.money.grossprofit;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.money.grossprofit.GrossProfitPrice.GrossProfitPriceBuilder;
import lombok.NonNull;

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
public class GrossProfitPriceFactory
{
	private final ImmutableList<GrossProfitComponentProvider> providers;

	public GrossProfitPriceFactory(@NonNull final Optional<List<GrossProfitComponentProvider>> providers)
	{
		this.providers = ImmutableList.copyOf(providers.orElse(ImmutableList.of()));
	}

	public GrossProfitPrice createGrossProfitPrice(@NonNull final GrossProfitComputeRequest grossProfitAware)
	{
		final GrossProfitPriceBuilder builder = GrossProfitPrice
				.builder()
				.basePrice(grossProfitAware.getBaseAmount());

		for (final GrossProfitComponentProvider provider : providers)
		{
			builder.profitCompponent(provider.provideForRequest(grossProfitAware));
		}
		return builder.build();
	}

}
