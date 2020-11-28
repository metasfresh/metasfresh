package de.metas.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.uom.UomId;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ProductPriceTest
{
	@Test
	public void test_withValueAndUomId()
	{
		final ProductId productId = ProductId.ofRepoId(1);
		final CurrencyId currencyId = CurrencyId.ofRepoId(1);
		final UomId uomId1 = UomId.ofRepoId(1);
		final UomId uomId2 = UomId.ofRepoId(2);

		final ProductPrice price = ProductPrice.builder()
				.productId(productId)
				.money(Money.of(100, currencyId))
				.uomId(uomId1)
				.build();

		assertThat(price
				.withValueAndUomId(new BigDecimal(101), uomId2)
				.withValueAndUomId(new BigDecimal(100), uomId1))
						.isEqualTo(price);
	}
}
