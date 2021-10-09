package de.metas.handlingunits.attributes.sscc18.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

/*
 * #%L
 * de.metas.handlingunits.base
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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.StaticHUAssert;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.organization.OrgId;
import de.metas.util.Services;

public class SSCC18CodeBLTests
{
	private SSCC18CodeBL sscc18CodeBL;

	static int nextSSCC18SerialNumber = 0;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		sscc18CodeBL = new SSCC18CodeBL(orgId -> ++nextSSCC18SerialNumber);
	}

	public static void setManufacturerCode(final String manufacturerCode)
	{
		Services.get(ISysConfigBL.class).setValue(SSCC18CodeBL.SYSCONFIG_ManufacturerCode, manufacturerCode, ClientId.METASFRESH, OrgId.ANY);
	}

	@Test
	public void testSSCC18()
	{
		final String manufacturerCode = "0718908 ";
		final String serialNumber = "562723189";
		final int checkDigit = 6;
		final int extensionDigit = 0;

		final SSCC18 sscc18 = new SSCC18(extensionDigit, manufacturerCode, serialNumber, checkDigit);

		final boolean isCorrectCheckDigit = sscc18CodeBL.isCheckDigitValid(sscc18);
		Assert.assertTrue("Check Digit is not correct: " + sscc18, isCorrectCheckDigit);
	}

	@Test(expected = Exception.class)
	public void testSSCC18_NotCorrcetLength()
	{
		final String manufacturerCode = "07189080 ";
		final String serialNumber = "562723189";
		final int checkDigit = 6;
		final int extensionDigit = 0;

		final SSCC18 sscc18 = new SSCC18(extensionDigit, manufacturerCode, serialNumber, checkDigit);

		final boolean isCorrectCheckDigit = sscc18CodeBL.isCheckDigitValid(sscc18);
		Assert.assertTrue("Check Digit is not correct: " + sscc18, isCorrectCheckDigit);
	}

	@Test
	public void testSSCC18_NotCorrectCheckDigit()
	{
		final String manufacturerCode = "0718907 ";
		final String serialNumber = "562723189";
		final int checkDigit = 6;
		final int extensionDigit = 0;

		final SSCC18 sscc18 = new SSCC18(extensionDigit, manufacturerCode, serialNumber, checkDigit);

		final boolean isCorrectCheckDigit = sscc18CodeBL.isCheckDigitValid(sscc18);
		Assert.assertFalse("Check Digit is not correct: " + sscc18, isCorrectCheckDigit);
	}

	@Test
	public void testGenerateSSCC18value()
	{
		setManufacturerCode("00000000");

		final SSCC18 generatedSSCC18 = sscc18CodeBL.generate(OrgId.ANY, 1000000);

		Assert.assertEquals("Serial number is not correct",
				"01000000", // expected,
				generatedSSCC18.getSerialNumber() // actual
		);
	}

	@Test
	public void testGenerateSSCC18value79()
	{
		setManufacturerCode("0000000");

		final SSCC18 generatedSSCC18 = sscc18CodeBL.generate(OrgId.ANY, 1000000);

		assertThat(generatedSSCC18.asString()).hasSize(18);
		Assert.assertEquals("Serial number is not correct",
				"001000000", // expected,
				generatedSSCC18.getSerialNumber() // actual
		);
	}

	@Test
	public void testGenerateSSCC18value55()
	{
		setManufacturerCode("00000");

		final SSCC18 generatedSSCC18 = sscc18CodeBL.generate(OrgId.ANY, 1000000);

		assertThat(generatedSSCC18.asString()).hasSize(18);
		Assert.assertEquals("Serial number is not correct",
				"001000000", // expected,
				generatedSSCC18.getSerialNumber() // actual
		);
	}

	@Test
	public void testGenerateSSCC18value11()
	{
		setManufacturerCode("2");

		final SSCC18 generatedSSCC18 = sscc18CodeBL.generate(OrgId.ANY, 1000000);
		assertThat(generatedSSCC18.asString()).hasSize(18);

		Assert.assertEquals("Serial number is not correct",
				"001000000", // expected,
				generatedSSCC18.getSerialNumber() // actual
		);

		Assert.assertEquals("Manufacturer code is not correct",
				"0000002", // expected,
				generatedSSCC18.getManufacturerCode() // actual
		);
	}

	@Test
	public void testGenerateSSCC18value9DigitManufacturerCode()
	{
		setManufacturerCode("123456789");
		final SSCC18CodeBL sscc18CodeBL = new SSCC18CodeBL(orgId -> 23);
		final SSCC18 generatedSSCC18 = sscc18CodeBL.generate(OrgId.ANY);

		// then
		assertThat(generatedSSCC18.asString()).hasSize(18).isEqualTo("012345678900000234");
	}

	@Test
	public void testcheckValidSSCC18()
	{
		final String manufacturerCode = "0718908 ";
		final String serialNumber = "562723189";
		final int checkDigit = 6;
		final int extensionDigit = 0;

		final SSCC18 sscc18ToValidate = new SSCC18(extensionDigit, manufacturerCode, serialNumber, checkDigit);
		assertThat(sscc18ToValidate.asString()).hasSize(18);

		sscc18CodeBL.validate(sscc18ToValidate);
	}

	@Test(expected = AdempiereException.class)
	public void testcheckValidSSCC18_wrongCheckDigit()
	{
		final String manufacturerCode = "0718907 ";
		final String serialNumber = "562723189";
		final int checkDigit = 6;
		final int extensionDigit = 0;

		final SSCC18 sscc18ToValidate = new SSCC18(extensionDigit, manufacturerCode, serialNumber, checkDigit);
		sscc18CodeBL.validate(sscc18ToValidate);

		StaticHUAssert.assertMock("mock");
	}

	@Test(expected = AdempiereException.class)
	public void testcheckValidSSCC18_tooLongManufacturerCode()
	{
		final String manufacturerCode = "071890799 ";
		final String serialNumber = "562723189";
		final int checkDigit = 6;
		final int extensionDigit = 0;

		final SSCC18 sscc18ToValidate = new SSCC18(extensionDigit, manufacturerCode, serialNumber, checkDigit);
		sscc18CodeBL.validate(sscc18ToValidate);
	}

	@Test
	public void testcheckValidSSCC18_9digitManufacturerCode()
	{
		final String manufacturerCode = "764011946";
		final String serialNumber = "0000002";
		final int checkDigit = 4;
		final int extensionDigit = 0;

		final SSCC18 sscc18ToValidate = new SSCC18(extensionDigit, manufacturerCode, serialNumber, checkDigit);
		sscc18CodeBL.validate(sscc18ToValidate);

		assertThat(sscc18ToValidate.asString()).hasSize(18).isEqualTo("0" + manufacturerCode + serialNumber + checkDigit);
	}

	@Test(expected = AdempiereException.class)
	public void testcheckValidSSCC18_NotDigit()
	{
		final String manufacturerCode = "0718907y";
		final String serialNumber = "56272h189";
		final int checkDigit = 6;
		final int extensionDigit = 0;

		final SSCC18 sscc18ToValidate = new SSCC18(extensionDigit, manufacturerCode, serialNumber, checkDigit);
		sscc18CodeBL.validate(sscc18ToValidate);
	}

	@Test(expected = AdempiereException.class)
	public void testcheckValidSSCC18_ManufacturingCodePlusSerialNumberTooLong()
	{
		final String manufacturerCode = "12345678";
		final String serialNumber = "123456789";
		final int checkDigit = 6;
		final int extensionDigit = 0;

		final SSCC18 sscc18ToValidate = new SSCC18(extensionDigit, manufacturerCode, serialNumber, checkDigit);
		sscc18CodeBL.validate(sscc18ToValidate);
	}

	@Test
	public void testSSCC18_ComputeCheckDigit()
	{
		final Map<String, Integer> SSCC18_Codes = new HashMap<>();
		SSCC18_Codes.put("03764013001000008", 5);
		SSCC18_Codes.put("03764013001000010", 8);
		SSCC18_Codes.put("03764013001000012", 2);
		SSCC18_Codes.put("03764013001000014", 6);
		SSCC18_Codes.put("03764013001000018", 4);

		SSCC18_Codes.forEach((stringSSCC18ToVerify, expectedCheckDigit) -> {
			final int checkDigit = sscc18CodeBL.computeCheckDigit(stringSSCC18ToVerify);
			assertThat(expectedCheckDigit).isEqualTo(checkDigit);
		});
	}

}
