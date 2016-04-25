package de.schaeffer.pay.service.impl;

/*
 * #%L
 * de.metas.banking.ait
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import de.schaeffer.pay.exception.TelecashUserException;
import de.schaeffer.pay.service.impl.TelecashBL.TelecashData;

/**
 * Makes a call to telecash using the a test account
 * 
 * @author ts
 * 
 */
public class TeleCashBLIntT
{

	private static final TelecashData connectionData =
			new TelecashData(
					"https://test.ipg-online.com/ipgapi/services",
					"12022222583", "xpfhyRZ7cR",
					TelecashBL.TELECASH_TRUSTSTORE_RESOURCE, TelecashBL.TELECASH_TRUSTSTORE_PW,
					"/de/schaeffer/pay/telecash/WS12022222583._.1.ks", "kEx2lgFAUF"
			);

	@Test
	public void testValidate()
	{
		final TelecashBL telecashBL = new TelecashBL();

		final String result = telecashBL.validateCard(connectionData, "4111 1111 1111 1111", "12", "12", "234");

		Assert.assertEquals("", result);
	}

	@Test
	public void testSale()
	{
		final TelecashBL telecashBL = new TelecashBL();

		String result = null;
		try
		{
			result = telecashBL.performSale(connectionData, "4111 1111 1111 1111", "12", "12", "234", "Mustermann", BigDecimal.ONE, "978");
		}
		catch (TelecashUserException e)
		{
			Assert.fail(e.getMessage());
		}

		Assert.assertNotNull(result);
	}

	public static void main(String... args)
	{
		final TeleCashBLIntT teleCashBLIntT = new TeleCashBLIntT();

		teleCashBLIntT.testValidate();
		teleCashBLIntT.testSale();
	}

}
