package de.metas.ui.web.window.datatypes;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ColorValue
{
	@JsonCreator
	public static ColorValue ofHexString(final String hexString)
	{
		return new ColorValue(hexString);
	}

	public static ColorValue ofRGB(final int red, final int green, final int blue)
	{
		return new ColorValue(red, green, blue);
	}

	String hexString;

	private ColorValue(final String hexString)
	{
		this.hexString = normalizeHexString(hexString);
	}

	private ColorValue(final int red, final int green, final int blue)
	{
		Check.assume(red >= 0 && red <= 255, "Invalid red value: {}", red);
		Check.assume(green >= 0 && green <= 255, "Invalid green value: {}", green);
		Check.assume(blue >= 0 && blue <= 255, "Invalid blue value: {}", blue);
		this.hexString = toHexString(red, green, blue);
	}

	private static String normalizeHexString(@NonNull final String hexString)
	{
		try
		{
			final int i = Integer.decode(hexString);
			final int red = i >> 16 & 0xFF;
			final int green = i >> 8 & 0xFF;
			final int blue = i & 0xFF;
			return toHexString(red, green, blue);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid color hex string: " + hexString, ex);
		}
	}

	private static String toHexString(final int red, final int green, final int blue)
	{
		return String.format("#%02x%02x%02x", red, green, blue);
	}

	@JsonValue
	public String toJson()
	{
		return hexString;
	}
}
