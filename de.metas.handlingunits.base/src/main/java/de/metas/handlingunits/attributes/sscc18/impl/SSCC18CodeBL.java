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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.ToString;

@ToString
@Service
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

	private final NextSerialNumberProvider nextSerialNumberProvider;
	/** for debugging */
	private boolean hasCustomNextSerialNumberProvider;

	public SSCC18CodeBL()
	{
		this.hasCustomNextSerialNumberProvider = false;

		this.nextSerialNumberProvider = orgId -> {
			final String sscc18SerialNumberStr = Services.get(IDocumentNoBuilderFactory.class)
					.forTableName(SSCC18_SERIALNUMBER_SEQUENCENAME, ClientId.METASFRESH.getRepoId(), orgId.getRepoId())
					.build();
			return Integer.parseInt(sscc18SerialNumberStr);
		};
	}

	/** Then unit testing, you can use this constructor to register an instances to services where you provide the next serial number. */
	@VisibleForTesting
	public SSCC18CodeBL(@NonNull final NextSerialNumberProvider nextSerialNumberProvider)
	{
		this.hasCustomNextSerialNumberProvider = true;
		this.nextSerialNumberProvider = nextSerialNumberProvider;
	}

	protected String getManufacturerCode(@NonNull final OrgId orgId)
	{
		final String manufacturerCode_SysConfig = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_ManufacturerCode, null, ClientId.METASFRESH.getRepoId(), orgId.getRepoId());
		return manufacturerCode_SysConfig;
	}

	/**
	 * @return true if the check digit is correct, false otherwise
	 */
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

		for (int i = 1; i <= stringSSCC18ToVerify.length(); i++)
		{
			// odd
			if (i % 2 != 0)
			{
				sumOdd += Integer.parseInt(Character.toString(stringSSCC18ToVerify.charAt(i - 1)));
			}

			else
			{
				sumEven += Integer.parseInt(Character.toString(stringSSCC18ToVerify.charAt(i - 1)));
			}
		}

		int result = 3 * sumOdd + sumEven;

		result = (10 - result % 10) % 10;
		return result;
	}

	@Override
	public SSCC18 generate(@NonNull final OrgId orgId)
	{
		return generate(orgId, nextSerialNumberProvider.provideNextSerialNumber(orgId));
	}

	@Override
	public SSCC18 generate(@NonNull final OrgId orgId, final int serialNumber)
	{
		Check.assume(serialNumber > 0, "serialNumber > 0");

		//
		// Retrieve and validate ManufacturerCode
		final String manufacturerCode_SysConfig = getManufacturerCode(orgId);
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
			finalSerialNumber = StringUtils.lpadZero(serialNumberStr, 8, "Manufacturer code size shoult be 8");

			finalManufacturerCode = manufacturerCode_SysConfig;
		}
		else if (manufacturerCodeSize == 7)
		{
			finalSerialNumber = StringUtils.lpadZero(serialNumberStr, 9, "Manufacturer code size shoult be 9");

			finalManufacturerCode = manufacturerCode_SysConfig;
		}
		// manufacturer code smaller than 7
		else
		{
			finalSerialNumber = StringUtils.lpadZero(serialNumberStr, 9, "Manufacturer code size shoult be " + 9);

			finalManufacturerCode = StringUtils.lpadZero(manufacturerCode_SysConfig, 7, "Manufacturer code size shoult be " + 7);
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

	@FunctionalInterface
	public interface NextSerialNumberProvider
	{
		int provideNextSerialNumber(OrgId orgId);
	}
}
