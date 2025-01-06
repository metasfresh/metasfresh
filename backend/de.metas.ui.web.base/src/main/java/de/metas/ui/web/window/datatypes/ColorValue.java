package de.metas.ui.web.window.datatypes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.util.Check;
import de.metas.util.MFColor;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

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
	public static final ColorValue RED = new ColorValue("#FF0000");
	public static final ColorValue GREEN = new ColorValue("#00FF00");

	public static ColorValue ofRGB(final int red, final int green, final int blue)
	{
		return ofHexString(toHexString(red, green, blue));
	}

	@JsonCreator
	public static ColorValue ofHexString(final String hexString)
	{
		return interner.intern(new ColorValue(hexString));
	}

	private static Interner<ColorValue> interner = Interners.newStrongInterner();

	static
	{
		interner.intern(RED);
		interner.intern(GREEN);
	}

	String hexString;

	private ColorValue(final String hexString)
	{
		this.hexString = normalizeHexString(hexString);
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

	public static String toHexString(final int red, final int green, final int blue)
	{
		return MFColor.toHexString(red, green, blue);
	}

	@JsonValue
	public String toJson()
	{
		return hexString;
	}
}
