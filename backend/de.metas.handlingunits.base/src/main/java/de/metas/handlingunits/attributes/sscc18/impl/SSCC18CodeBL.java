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
	public static final String SYSCONFIG_ExtensionDigit = "de.metas.handlingunit.GS1ExtensionDigit";

	/**
	 * Manufacturer code as assigne by GS1. Consists of 7 or 8 digits. For the system default it is 0000000 (7 zeros)
	 */
	public static final String SYSCONFIG_ManufacturerCode = "de.metas.handlingunit.GS1ManufacturerCode";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final NextSerialNumberProvider nextSerialNumberProvider;
	/** for debugging */
	private final boolean hasCustomNextSerialNumberProvider;

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

	private int getExtensionDigit(@NonNull final OrgId orgId)
	{
		final int extensionDigit_SysConfig = sysConfigBL.getIntValue(SYSCONFIG_ExtensionDigit, 0,
				ClientId.METASFRESH.getRepoId(),
				orgId.getRepoId());

		return extensionDigit_SysConfig;
	}

	private String getManufacturerCode(@NonNull final OrgId orgId)
	{
		final String manufacturerCode_SysConfig = sysConfigBL.getValue(SYSCONFIG_ManufacturerCode, null,
				ClientId.METASFRESH.getRepoId(),
				orgId.getRepoId());

		return manufacturerCode_SysConfig;
	}

	/**
	 * @return true if the check digit is correct, false otherwise
	 */
	@Override
	public boolean isCheckDigitValid(@NonNull final SSCC18 sscc18)
	{
		final int result = computeCheckDigit(sscc18);

		final int checkDigit = sscc18.getCheckDigit();

		if (checkDigit == result % 10)
		{
			return true;
		}

		return false;
	}

	private int computeCheckDigit(final SSCC18 sscc18)
	{
		final int extentionDigit = sscc18.getExtensionDigit();
		final String manufacturerCode = sscc18.getManufacturerCode().trim();
		final String serialNumber = sscc18.getSerialNumber().trim();

		final String stringSSCC18ToVerify = extentionDigit
				+ manufacturerCode
				+ serialNumber;

		final int result = computeCheckDigit(stringSSCC18ToVerify);
		return result;
	}

	@Override
	public int computeCheckDigit(final String stringSSCC18ToVerify)
	{
		Check.assume(stringSSCC18ToVerify.length() == 17,
				"The given SSCC18-String={} needs to have 17 digits, but has {}", stringSSCC18ToVerify, stringSSCC18ToVerify.length());

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
		Check.assumeNotEmpty(manufacturerCode_SysConfig, "Manufacturer code may not be empty; orgId={}", orgId);

		final String manufacturerCode;
		if (manufacturerCode_SysConfig.length() < 7)
		{
			manufacturerCode = StringUtils.lpadZero(getManufacturerCode(orgId), 7, "lpad the manufacturerCode to at least 7");
		}
		else
		{
			manufacturerCode = manufacturerCode_SysConfig;
		}

		Check.assume(StringUtils.isNumber(manufacturerCode), "Manufacturer code {} need to be a number; orgId={}", manufacturerCode, orgId);

		validateManufacturerCode(manufacturerCode);

		final String serialNumberStr = String.valueOf(serialNumber);
		final int serialNumberTargetSize = validateSerialNumber(manufacturerCode, serialNumberStr);

		final String finalSerialNumber = StringUtils.lpadZero(serialNumberStr, serialNumberTargetSize, "");

		final int extensionDigit = getExtensionDigit(orgId);
		final int checkDigit = computeCheckDigit(extensionDigit + manufacturerCode + finalSerialNumber);

		return new SSCC18(extensionDigit, manufacturerCode, finalSerialNumber, checkDigit);
	}

	@Override
	public void validate(final SSCC18 sscc18ToValidate)
	{
		final String manufactCode = sscc18ToValidate.getManufacturerCode().trim();
		final String serialNumber = sscc18ToValidate.getSerialNumber().trim();

		validateManufacturerCode(manufactCode);

		final int digitsAvailableForSerialNumber = validateSerialNumber(manufactCode, serialNumber);
		Check.errorIf(serialNumber.length() > digitsAvailableForSerialNumber, "With a {}-digit manufactoring code={}, the serial number={} may only have {} digits", manufactCode.length(), manufactCode, serialNumber, digitsAvailableForSerialNumber);

		final int computeCheckDigit = computeCheckDigit(sscc18ToValidate);
		Check.errorUnless(sscc18ToValidate.getCheckDigit() == computeCheckDigit, "The check digit of SSCC18={} is not valid; It needs to be={}", sscc18ToValidate.asString(), computeCheckDigit);
		Check.errorUnless(isCheckDigitValid(sscc18ToValidate), "Check digit is not valid");
	}

	private int validateSerialNumber(final String manufactCode, final String serialNumber)
	{
		final int digitsAvailableForSerialNumber = computeLenghtOfSerialNumber(manufactCode);
		Check.errorUnless(StringUtils.isNumber(serialNumber), "The serial number " + serialNumber + " is not a number");
		return digitsAvailableForSerialNumber;
	}

	private int computeLenghtOfSerialNumber(final String manufactCode)
	{
		return 18 - 1/* extension-digit */ - manufactCode.length() - 1;
	}

	private void validateManufacturerCode(@NonNull final String manufactCode)
	{
		Check.errorUnless(StringUtils.isNumber(manufactCode), "The manufacturer code " + manufactCode + " is not a number");
		Check.errorIf(manufactCode.length() > 9, "The manufacturer code " + manufactCode + " is too long");
	}

	@FunctionalInterface
	public interface NextSerialNumberProvider
	{
		int provideNextSerialNumber(OrgId orgId);
	}
}
