package de.metas.handlingunits.attributes.sscc18.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.SSCC18;

public class SSCC18CodeBL implements ISSCC18CodeBL
{
	/**
	 * Manufacturer code consists of 7 or 8 digits. For the system default it is 0000000 (7 zeros)
	 */
	public static final String SYSCONFIG_ManufacturerCode = "de.metas.handlingunit.GS1ManufacturerCode";

	/**
	 * The extended digit in SSCC18. Usually 0 (the package type - a carton)
	 */
	private final int EXTENDED_DIGIT = 0;

	protected String getManufacturerCode(final Properties ctx)
	{
		final String manufacturerCode_SysConfig = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_ManufacturerCode, null, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		return manufacturerCode_SysConfig;
	}

	@Override
	public boolean isCheckDigitValid(final SSCC18 sscc18)
	{

		final int extentionDigit = sscc18.getExtensionDigit();
		final String manufacturerCode = sscc18.getManufacturerCode().trim();
		final String serialNumber = sscc18.getSerialNumber().trim();
		final int checkDigit = sscc18.getCheckDigit();

		final String stringSSCC18ToVerify = extentionDigit
				+ manufacturerCode
				+ serialNumber;

		final int result = computeCheckDigit(stringSSCC18ToVerify);

		if (checkDigit == result % 10)
		{
			return true;
		}

		return false;
	}

	@Override
	public int computeCheckDigit(final String stringSSCC18ToVerify)
	{
		Check.assume(stringSSCC18ToVerify.length() == 17, "Incorrect SSCC18");

		int sumOdd = 0;
		int sumEven = 0;

		System.out.println("stringSSCC18ToVerify");
		for (int i = 1; i <= stringSSCC18ToVerify.length(); i++)
		{
			System.out.println("Pos " + i+ "value " + stringSSCC18ToVerify.charAt(i-1));
			// odd
			if (i % 2 != 0)
			{
				sumOdd += Integer.parseInt(Character.toString(stringSSCC18ToVerify.charAt(i-1)));
			}

			else
			{
				sumEven += Integer.parseInt(Character.toString(stringSSCC18ToVerify.charAt(i-1)));
			}
		}

		int result = 3 * sumOdd + sumEven;

		result = (10 - result % 10) % 10;
		return result;
	}

	@Override
	public SSCC18 generate(final Properties ctx, final int serialNumber)
	{
		Check.assume(serialNumber > 0, "serialNumber > 0");

		//
		// Retrieve and validate ManufacturerCode
		final String manufacturerCode_SysConfig = getManufacturerCode(ctx);
		Check.assume(StringUtils.isNumber(manufacturerCode_SysConfig), "Manufacturer code {} is not a number", manufacturerCode_SysConfig);
		final int manufacturerCodeSize = manufacturerCode_SysConfig.length();
		Check.assume(manufacturerCodeSize <= 8, "Manufacturer code too long: {}", manufacturerCode_SysConfig);

		//
		// Validate serialNumber and adjust serialNumber and manufacturerCode paddings
		final String serialNumberStr = String.valueOf(serialNumber);
		final int serialNumberSize = serialNumberStr.length();
		Check.assume(serialNumberSize <= 9, "Serial number too long: {}", serialNumberStr);
		final String finalManufacturerCode;
		final String finalSerialNumber;
		if (manufacturerCodeSize == 8)
		{
			Check.assume(serialNumberSize <= 8, "Serial number too long: {}", serialNumberStr);
			finalSerialNumber = Util.lpadZero(serialNumberStr, 8, "Manufacturer code size shoult be 8");

			finalManufacturerCode = manufacturerCode_SysConfig;
		}
		else if (manufacturerCodeSize == 7)
		{
			finalSerialNumber = Util.lpadZero(serialNumberStr, 9, "Manufacturer code size shoult be 9");

			finalManufacturerCode = manufacturerCode_SysConfig;
		}
		// manufacturer code smaller than 7
		else
		{
			finalSerialNumber = Util.lpadZero(serialNumberStr, 9, "Manufacturer code size shoult be " + 9);

			finalManufacturerCode = Util.lpadZero(manufacturerCode_SysConfig, 7, "Manufacturer code size shoult be " + 7);
		}

		final int checkDigit = computeCheckDigit(EXTENDED_DIGIT + finalManufacturerCode + finalSerialNumber);

		return new SSCC18(EXTENDED_DIGIT, finalManufacturerCode, finalSerialNumber, checkDigit);
	}

	@Override
	public void validate(final SSCC18 sscc18ToValidate)
	{
		final String manufactCode = sscc18ToValidate.getManufacturerCode().trim();
		final String serialNumber = sscc18ToValidate.getSerialNumber().trim();

		Check.assume(StringUtils.isNumber(manufactCode), "The manufacturer code " + manufactCode + " is not a number");
		Check.assume(manufactCode.length() <= 8, "The manufacturer code " + manufactCode + "is too long");

		Check.assume(StringUtils.isNumber(serialNumber), "The serial number " + serialNumber + " is not a number");
		Check.assume(serialNumber.length() <= 9, "The serial number " + serialNumber + "is too long");

		Check.assume((serialNumber + manufactCode).length() == 16, "Manufacturer code + serial number must be 16");
		Check.assume(isCheckDigitValid(sscc18ToValidate), "Check digit is not valid");
	}

	@Override
	public String toString(final SSCC18 sscc18, final boolean humanReadable)
	{
		Check.assumeNotNull(sscc18, "sscc18 not null");

		if (!humanReadable)
		{
			return sscc18.getExtensionDigit()
					+ sscc18.getManufacturerCode()
					+ sscc18.getSerialNumber()
					+ sscc18.getCheckDigit();
		}
		else
		{
			throw new IllegalStateException("Not implemented");
		}
	}
}
