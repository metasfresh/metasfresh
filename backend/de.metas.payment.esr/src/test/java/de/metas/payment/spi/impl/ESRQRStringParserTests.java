package de.metas.payment.spi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.compiere.util.Env;
import org.junit.jupiter.api.Test;

import de.metas.banking.payment.IPaymentString;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class ESRQRStringParserTests
{
	@Test
	public void parseNoString()
	{
		assertThrows(RuntimeException.class, () -> ESRQRStringParser.instance.parse(Env.getCtx(), null));
	}
	@Test
	public void parseEmptyString()
	{
		assertThrows(IndexOutOfBoundsException.class, () -> ESRQRStringParser.instance.parse(Env.getCtx(), ""));
	}
	@Test
	public void parseShortString()
	{
		assertThrows(IndexOutOfBoundsException.class, () -> ESRQRStringParser.instance.parse(Env.getCtx(), "01"));
	}

	@Test
	public void parseESRWithoutAmount()
	{
		final IPaymentString result = ESRQRStringParser.instance.parse(Env.getCtx(), "042>000000162591872680005352198+ 012000007>");
		assertThat(result.getReferenceNoComplete()).isEqualTo("000000162591872680005352198");
		assertThat(result.getPostAccountNo()).isEqualTo("012000007");
	}

	@Test
	public void parseESRWithAmount()
	{
		final IPaymentString result = ESRQRStringParser.instance.parse(Env.getCtx(), "0100000214000>10360022841297+ 010001456>");
		assertThat(result.getReferenceNoComplete()).isEqualTo("000000000000010360022841297");
		assertThat(result.getPostAccountNo()).isEqualTo("010001456");
	}

	@Test
	public void parseQR()
	{
		final IPaymentString result = ESRQRStringParser.instance.parse(Env.getCtx(), 
				  "SPC\n"
				+ "0200\n"
				+ "1\n"
				+ "CH4409000000909909999\n"
				+ "K\n"
				+ "example AG\n"
				+ "Street 1\n"
				+ "88000 City\n"
				+ "\n"
				+ "\n"
				+ "CH\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "999.99\n"
				+ "CHF\n"
				+ "K\n"
				+ "Customer AG\n"
				+ "Street customer 1\n"
				+ "99000 City\n"
				+ "\n"
				+ "\n"
				+ "CH\n"
				+ "QRR\n"
				+ "000000000000010360022841297\n"
				+ "\n"
				+ "EPD\n"
				+ "\n"
				);
		assertTrue(result.isQRPaymentString());
		assertThat(result.getIbanAccountNo()).isEqualTo("CH4409000000909909999");
		assertThat(result.getReferenceNoComplete()).isEqualTo("000000000000010360022841297");
		assertThat(result.getAmount()).isEqualTo(new BigDecimal("999.99"));
	}

}
