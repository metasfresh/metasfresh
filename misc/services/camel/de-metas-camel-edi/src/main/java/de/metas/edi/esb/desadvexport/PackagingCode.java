/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.desadvexport;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.RuntimeCamelException;

import java.util.Arrays;

public enum PackagingCode
{
	/** Pallet ISO 1 - 1/1 EURO Pallet */
	ISO1("ISO1"),

	/** Pallet ISO 1 - 1/2 EURO Pallet */
	ISO2("ISO2"),

	ONEW("ONEW"),

	RETR("RETR"),

	PACK("PACK"),

	SLPS("SLPS"),

	/** Carton */
	CART("CART"),

	BOXS("BOXS");

	@Getter
	private final String code;

	PackagingCode(@NonNull final String code)
	{
		this.code = code;
	}

	public static PackagingCode ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static PackagingCode ofCode(@NonNull final String code)
	{
		PackagingCode type = typesByCode.get(code);
		if (type == null)
		{
			throw new RuntimeCamelException("No " + PackagingCode.class + " found for code: " + code);
		}
		return type;
	}

	public static String toCodeOrNull(final PackagingCode type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, PackagingCode> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PackagingCode::getCode);
}
