package de.metas.payment.spi.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.metas.banking.payment.PaymentString;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ESRCreaLogixStringParserTests
{
	@Test
	public void parseRegular()
	{
		final PaymentString result = ESRCreaLogixStringParser.instance.parse("042>000000162591872680005352198+ 012000007>");
		assertThat(result.getReferenceNoComplete()).isEqualTo("000000162591872680005352198");
		assertThat(result.getPostAccountNo()).isEqualTo("012000007");
	}

	@Test
	public void parseShorter()
	{
		final PaymentString result = ESRCreaLogixStringParser.instance.parse("0100000214000>10360022841297+ 010001456>");
		assertThat(result.getReferenceNoComplete()).isEqualTo("000000000000010360022841297");
		assertThat(result.getPostAccountNo()).isEqualTo("010001456");
	}

}
