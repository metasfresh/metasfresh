package de.metas.currency;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class CurrencyCodeTest
{
	@Test
	public void testEUR()
	{
		assertThat(CurrencyCode.ofThreeLetterCode(CurrencyCode.EUR.toThreeLetterCode()))
				.isSameAs(CurrencyCode.EUR);
		assertThat(CurrencyCode.EUR.isEuro()).isTrue();
	}

	@Test
	public void testCHF()
	{
		assertThat(CurrencyCode.ofThreeLetterCode(CurrencyCode.CHF.toThreeLetterCode()))
				.isSameAs(CurrencyCode.CHF);

		assertThat(CurrencyCode.CHF.isEuro()).isFalse();
		assertThat(CurrencyCode.CHF.isCHF()).isTrue();
	}

	@Test
	public void testEquals()
	{
		final CurrencyCode ron1 = CurrencyCode.ofThreeLetterCode("RON");
		final CurrencyCode ron2 = CurrencyCode.ofThreeLetterCode("RON");
		assertThat(ron1).isNotSameAs(ron2);
		assertThat(ron1).isEqualTo(ron2);
	}

}
