package de.metas.camel.inventory;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode
@ToString
final class ProductCodesToExclude
{
	public static ProductCodesToExclude ofCommaSeparatedString(@Nullable final String string)
	{
		if (string == null || string.isBlank())
		{
			return EMPTY;
		}

		final List<String> exclusionList = Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(string);
		if (exclusionList.isEmpty())
		{
			return EMPTY;
		}

		return new ProductCodesToExclude(ImmutableSet.copyOf(exclusionList));
	}

	public static final ProductCodesToExclude EMPTY = new ProductCodesToExclude(ImmutableSet.of());

	private final ImmutableSet<String> exclusionList;

	private ProductCodesToExclude(@NonNull final ImmutableSet<String> exclusionList)
	{
		this.exclusionList = exclusionList;
	}

	public boolean isProductCodeExcluded(@NonNull final String productCode)
	{
		return !exclusionList.isEmpty()
				&& exclusionList.contains(productCode);
	}
}
