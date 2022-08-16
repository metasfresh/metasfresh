package de.metas.costing;

import java.math.BigDecimal;

import de.metas.money.CurrencyId;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
	private final CurrencyId currencyId = CurrencyId.ofRepoId(123);

	private CostAmount newCostAmount(final String amountStr)
	{
		return CostAmount.of(new BigDecimal(amountStr), currencyId);
	}

	@Test
	public void testEquals()
	{
		assertThat(newCostAmount("10.000000")).isEqualTo(newCostAmount("10"));
		assertThat(newCostAmount("10.00001000000")).isEqualTo(newCostAmount("10.00001"));
	}
}
