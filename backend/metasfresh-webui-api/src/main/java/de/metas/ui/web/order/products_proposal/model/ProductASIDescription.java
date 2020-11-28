package de.metas.ui.web.order.products_proposal.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode
public final class ProductASIDescription
{
	@JsonCreator
	public static ProductASIDescription ofString(final String value)
	{
		if (value == null)
		{
			return NONE;
		}

		final String valueNorm = value.trim();
		if (valueNorm.isEmpty())
		{
			return NONE;
		}

		return new ProductASIDescription(valueNorm);
	}

	public static final ProductASIDescription NONE = new ProductASIDescription("");

	private final String value;

	private ProductASIDescription(@NonNull final String value)
	{
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString()
	{
		return value;
	}
}
