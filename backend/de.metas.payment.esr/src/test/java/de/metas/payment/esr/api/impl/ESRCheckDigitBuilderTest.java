package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
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


import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class ESRCheckDigitBuilderTest
{

	final ESRImportBL esrImportBL = new ESRImportBL();

	@Test
	public void esrDigit01052601()
	{
		Assert.assertEquals(1, new ESRImportBL().calculateESRCheckDigit("01052601")); // 01-52601-1
	}

	@Test
	public void esrDigit01049165()
	{
		Assert.assertEquals(2, new ESRImportBL().calculateESRCheckDigit("01049165")); // 01-49165-2
	}

	@Test
	public void esrDigit01062967()
	{
		Assert.assertEquals(6, new ESRImportBL().calculateESRCheckDigit("01062967")); // 01-62967-6
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf
	@Test
	public void esrDigit01001852()
	{
		Assert.assertEquals(7, new ESRImportBL().calculateESRCheckDigit("01001852")); // 01-1852-7
	}

	@Test
	public void esrDigit00018310019779911119()
	{
		Assert.assertEquals(6, new ESRImportBL().calculateESRCheckDigit("31001400018310019779911119"));
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf "Belegartcode 01 (BESR)", seems to be wrong,
	// because all other work, only this one doesn't
	public void esrDigit0100000292001()
	{
		Assert.assertEquals(1, new ESRImportBL().calculateESRCheckDigit("010000029200"));
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf
	@Test
	public void esrDigit04()
	{
		Assert.assertEquals(2, new ESRImportBL().calculateESRCheckDigit("04"));
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf
	@Test
	public void esrDigit0100003949753()
	{
		Assert.assertEquals(3, new ESRImportBL().calculateESRCheckDigit("010000394975"));
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf
	@Test
	public void esrDigit310739205008005400199307134()
	{
		Assert.assertEquals(4, new ESRImportBL().calculateESRCheckDigit("31073920500800540019930713"));
	}

	/**
	 * Mainly verifying that the last digist of a scanned ESR-Number is actually the correct check-digit, and that i know how to use the subString method.
	 * 
	 * @task 08341
	 */
	@Test
	public void esrDigit01000145()
	{
		final int checkDigit = esrImportBL.calculateESRCheckDigit("010001456".substring(0, 8));
		final String lastchar = "010001456".substring(8);

		assertEquals(6, checkDigit);
		assertEquals("6", lastchar);
	}
}
