package de.metas.pricing.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;

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

public class PricingConditionsBreakMatchCriteriaTest
{
	@Test
	public void matchingByProduct()
	{
		final PricingConditionsBreakMatchCriteria matchCriteria = PricingConditionsBreakMatchCriteria.builder()
				.breakValue(BigDecimal.ZERO)
				.productId(ProductId.ofRepoId(1))
				.build();

		assertThat(matchCriteria.productMatches(ProductAndCategoryAndManufacturerId.of(1, 200, 300))).isTrue();
		assertThat(matchCriteria.productMatches(ProductAndCategoryAndManufacturerId.of(1, 200, -1))).isTrue();
		assertThat(matchCriteria.productMatches(ProductAndCategoryAndManufacturerId.of(2, 200, 300))).isFalse();
	}

	@Test
	public void matchingByProductCategory()
	{
		final PricingConditionsBreakMatchCriteria matchCriteria = PricingConditionsBreakMatchCriteria.builder()
				.breakValue(BigDecimal.ZERO)
				.productCategoryId(ProductCategoryId.ofRepoId(2))
				.build();

		assertThat(matchCriteria.productMatches(ProductAndCategoryAndManufacturerId.of(1, 2, 300))).isTrue();
		assertThat(matchCriteria.productMatches(ProductAndCategoryAndManufacturerId.of(1, 2, -1))).isTrue();
		assertThat(matchCriteria.productMatches(ProductAndCategoryAndManufacturerId.of(1, 200, 300))).isFalse();
	}

	@Test
	public void matchingByManufacturer()
	{
		final PricingConditionsBreakMatchCriteria matchCriteria = PricingConditionsBreakMatchCriteria.builder()
				.breakValue(BigDecimal.ZERO)
				.productManufacturerId(BPartnerId.ofRepoId(3))
				.build();

		assertThat(matchCriteria.productMatches(ProductAndCategoryAndManufacturerId.of(1, 2, 3))).isTrue();
		assertThat(matchCriteria.productMatches(ProductAndCategoryAndManufacturerId.of(1, 2, 300))).isFalse();
	}

}
