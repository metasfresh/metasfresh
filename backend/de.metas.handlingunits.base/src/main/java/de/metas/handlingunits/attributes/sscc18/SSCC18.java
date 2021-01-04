package de.metas.handlingunits.attributes.sscc18;

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

import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.ObjectUtils;

/**
 * Immutable POJO for Serial Shipping Container Code (SSCC-18)
 * <p>
 * See <a href="http://mdn.morovia.com/kb/Serial-Shipping-Container-Code-SSCC18-10601.html">SSCC18</a>
 */
@Value
public class SSCC18
{

	/**
	 * Extended digit and assigned by the company
	 */
	int extensionDigit;

	/**
	 * The manufacturer code (or company code) is assigned by GS1 (formerly known as UCC/EAN organization)
	 */
	String manufacturerCode;

	/**
	 * Identifies this merchandise container and assigned by the manufacturer.
	 * <p>
	 * The manufacturer should not reuse the serial number within a certain time frame, for example, 1 year.
	 * <p>
	 * The company code normally comprises 7 or 8 digits. Therefore, the serial number can be 9 or 8 digits.
	 */
	String serialNumber;

	/**
	 * SSCC-18 check digit to ensure that the data is correctly entered. It is part of the data encoded into the barcode. A receiving system is required to validate this check digit, so you should get
	 * it correct at the first place.
	 */
	int checkDigit;

	public SSCC18(final int extensionDigit,
			@NonNull final String manufacturerCode,
			@NonNull final String serialNumber,
			final int checkDigit)
	{
		this.extensionDigit = extensionDigit;
		this.manufacturerCode = manufacturerCode.trim();
		this.serialNumber = serialNumber.trim();
		this.checkDigit = checkDigit;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public int getExtensionDigit()
	{
		return extensionDigit;
	}

	public String getManufacturerCode()
	{
		return manufacturerCode;
	}

	public String getSerialNumber()
	{
		return serialNumber;
	}

	public int getCheckDigit()
	{
		return checkDigit;
	}

	/**
	 * @return SSCC18 string representation
	 */
	public String asString()
	{
		return getExtensionDigit()
				+ getManufacturerCode()
				+ getSerialNumber()
				+ getCheckDigit();
	}
}
