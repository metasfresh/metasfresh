/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.tax;

import com.google.common.collect.ImmutableMap;
import de.metas.common.pricing.v2.productprice.TaxCategory;
import de.metas.common.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@Value
public class TaxCategoryProvider
{
	@NonNull
	@Getter(AccessLevel.NONE)
	ImmutableMap<BigDecimal, TaxCategory> taxRate2Category;

	@Builder
	public TaxCategoryProvider(@Nullable final String normalRates, @Nullable final String reducedRates)
	{
		final ImmutableMap.Builder<BigDecimal, TaxCategory> taxRate2CategoryBuilder = ImmutableMap.builder();

		if (Check.isNotBlank(normalRates))
		{
			Arrays.stream(Objects.requireNonNull(normalRates).split(","))
					.map(String::strip)
					.map(BigDecimal::new)
					.forEach(rate -> taxRate2CategoryBuilder.put(rate, TaxCategory.NORMAL));
		}

		if (Check.isNotBlank(reducedRates))
		{
			Arrays.stream(Objects.requireNonNull(reducedRates).split(","))
					.map(String::strip)
					.map(BigDecimal::new)
					.forEach(rate -> taxRate2CategoryBuilder.put(rate, TaxCategory.REDUCED));
		}

		this.taxRate2Category = taxRate2CategoryBuilder.build();
	}

	@Nullable
	public TaxCategory getTaxCategory(@NonNull final BigDecimal taxRate)
	{
		return taxRate2Category.get(taxRate);
	}
}
