package de.metas.camel.manufacturing.order.export;

import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de-metas-camel-shipping
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

public enum MainProductOrComponent
{
	MAIN_PRODUCT("Mutter"), //
	COMPONENT("Tochter");

	@Getter
	private final String code;

	MainProductOrComponent(final String code)
	{
		this.code = code;
	}

	public static MainProductOrComponent ofCode(@NonNull final String code)
	{
		if (MAIN_PRODUCT.code.equals(code))
		{
			return MAIN_PRODUCT;
		}
		else if (COMPONENT.code.equals(code))
		{
			return COMPONENT;
		}
		else
		{
			throw new IllegalArgumentException("Unknow code: " + code);
		}
	}
}
