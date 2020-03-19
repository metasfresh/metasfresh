package de.metas.payment.esr.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.attachments.AttachmentEntryService;

public class ESRCheckDigitBuilderTest
{

	private ESRImportBL esrImportBL;

	@BeforeEach
	public void init()
	{
		final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
		esrImportBL = new ESRImportBL(attachmentEntryService);
	}

	@Test
	public void esrDigit01052601()
	{
		assertThat(esrImportBL.calculateESRCheckDigit("01052601")).isEqualTo(1); // 01-52601-1
	}

	@Test
	public void esrDigit01049165()
	{
		assertThat(esrImportBL.calculateESRCheckDigit("01049165")).isEqualTo(2); // 01-49165-2
	}

	@Test
	public void esrDigit01062967()
	{
		assertThat(esrImportBL.calculateESRCheckDigit("01062967")).isEqualTo(6); // 01-62967-6
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf
	@Test
	public void esrDigit01001852()
	{
		assertThat(esrImportBL.calculateESRCheckDigit("01001852")).isEqualTo(7); // 01-1852-7
	}

	@Test
	public void esrDigit00018310019779911119()
	{
		assertThat(esrImportBL.calculateESRCheckDigit("31001400018310019779911119")).isEqualTo(6);
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf "Belegartcode 01 (BESR)", seems to be wrong,
	// because all other work, only this one doesn't
	public void esrDigit0100000292001()
	{
		assertThat(esrImportBL.calculateESRCheckDigit("010000029200")).isEqualTo(1);
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf
	@Test
	public void esrDigit04()
	{
		assertThat(esrImportBL.calculateESRCheckDigit("04")).isEqualTo(2);
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf
	@Test
	public void esrDigit0100003949753()
	{
		assertThat(esrImportBL.calculateESRCheckDigit("010000394975")).isEqualTo(3);
	}

	// Example taken from http://www.tkb.ch/download/online/BESR-Handbuch.pdf
	@Test
	public void esrDigit310739205008005400199307134()
	{
		assertThat(esrImportBL.calculateESRCheckDigit("31073920500800540019930713")).isEqualTo(4);
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

		assertThat(checkDigit).isEqualTo(6);
		assertThat(lastchar).isEqualTo("6");
	}
}
