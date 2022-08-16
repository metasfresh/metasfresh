package de.metas.costing;

import de.metas.money.CurrencyId;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

public class CostAmountTest
{
	private final CurrencyId currencyId1 = CurrencyId.ofRepoId(123);
	private final CurrencyId currencyId2 = CurrencyId.ofRepoId(4);

	private CostAmount newCostAmount(final String amountStr)
	{
		return newCostAmount(amountStr, currencyId1);
	}

	private CostAmount newCostAmount(final String amountStr, final CurrencyId currencyId)
	{
		return CostAmount.of(new BigDecimal(amountStr), currencyId);
	}

	@Test
	void testEquals()
	{
		assertThat(newCostAmount("10.000000")).isEqualTo(newCostAmount("10"));
		assertThat(newCostAmount("10.00001000000")).isEqualTo(newCostAmount("10.00001"));
	}

	@Test
	void compareToEquals()
	{
		assertThat(newCostAmount("10.000000").compareToEquals(newCostAmount("10"))).isTrue();
		assertThat(newCostAmount("1").compareToEquals(newCostAmount("2"))).isFalse();
		assertThatThrownBy(() -> newCostAmount("1", currencyId1).compareToEquals(newCostAmount("1", currencyId2)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Amount has invalid currency");
	}
}
