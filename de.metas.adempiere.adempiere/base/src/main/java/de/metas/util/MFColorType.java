package de.metas.util;

import java.util.NoSuchElementException;

import org.compiere.model.X_AD_Color;

import lombok.Getter;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public enum MFColorType
{
	FLAT(X_AD_Color.COLORTYPE_NormalFlat), //
	GRADIENT(X_AD_Color.COLORTYPE_Gradient), //
	LINES(X_AD_Color.COLORTYPE_Line), //
	TEXTURE(X_AD_Color.COLORTYPE_TexturePicture) //
	;

	@Getter
	private final String code;

	MFColorType(final String code)
	{
		this.code = code;
	}

	public static MFColorType ofCode(final String code)
	{
		for (final MFColorType colorType : values())
		{
			if (colorType.getCode().equals(code))
			{
				return colorType;
			}
		}

		throw new NoSuchElementException("No " + MFColorType.class + " found for code=" + code);
	}
}