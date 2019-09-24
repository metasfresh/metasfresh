package de.metas.money;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.util.lang.Percent;

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

public class MoneyServiceTest
{
	private MoneyService moneyService;

	// private Currency currency;

	private Money zeroEuro;

	private Money seventyEuro;

	private Money oneHundretEuro;

	private Money twoHundredEuro;

	private CurrencyId currencyId;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final CurrencyRepository currencyRepository = new CurrencyRepository();
		moneyService = new MoneyService(currencyRepository);

		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		// Currency currency = currencyRepository.getById(currencyId);

		zeroEuro = Money.of(0, currencyId);
		seventyEuro = Money.of(70, currencyId);
		oneHundretEuro = Money.of(100, currencyId);
		twoHundredEuro = Money.of(200, currencyId);
	}

	@Test
	public void percentage()
	{
		final Money result = moneyService.percentage(Percent.of(80), twoHundredEuro);

		assertThat(result.getCurrencyId()).isEqualTo(currencyId);
		assertThat(result.toBigDecimal()).isEqualByComparingTo("160");
	}

	@Test
	public void percentage_zero()
	{
		final Money result = moneyService.percentage(Percent.of(0), twoHundredEuro);

		assertThat(result.getCurrencyId()).isEqualTo(currencyId);
		assertThat(result).isEqualTo(zeroEuro);
		assertThat(result.isZero()).isTrue();
	}

	/** This test shall work because we set the currency precision to >= 1. */
	@Test
	public void percentage_real_world_example()
	{
		final Money result = moneyService.percentage(Percent.of(10), Money.of(14, currencyId));

		assertThat(result.getCurrencyId()).isEqualTo(currencyId);
		assertThat(result.toBigDecimal()).isEqualByComparingTo("1.4");
	}

	@Test
	public void subtractPercent()
	{
		assertThat(moneyService.subtractPercent(Percent.of(0), oneHundretEuro)).isSameAs(oneHundretEuro);

		assertThat(moneyService.subtractPercent(Percent.of(30), oneHundretEuro)).isEqualTo(seventyEuro);

		assertThat(moneyService.subtractPercent(Percent.of(55), zeroEuro)).isSameAs(zeroEuro);

	}
}
